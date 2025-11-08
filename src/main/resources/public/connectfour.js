// Globale Variablen
let gameState = null;
const API_BASE = 'http://localhost:8081/api';
let selectedColors = {
    player1: 'gelb',
    player2: 'rot'
};
let isAIMode = false;
let isWaitingForAI = false;

// Initialisierung beim Laden der Seite
document.addEventListener('DOMContentLoaded', () => {
    // Zeige Konfiguration beim Start
    showConfig();
});

// Zeigt die Konfiguration an
function showConfig() {
    document.getElementById('configOverlay').style.display = 'flex';
}

// Versteckt die Konfiguration
function hideConfig() {
    document.getElementById('configOverlay').style.display = 'none';
}

// Wechselt die Sichtbarkeit der Schwierigkeitsauswahl
function toggleDifficulty() {
    const mode = document.getElementById('gameMode').value;
    const diffGroup = document.getElementById('difficultyGroup');
    const player2Label = document.getElementById('player2Label');
    
    if (mode === 'ai') {
        diffGroup.classList.add('visible');
        player2Label.textContent = 'KI Farbe:';
    } else {
        diffGroup.classList.remove('visible');
        player2Label.textContent = 'Spieler 2 Farbe:';
    }
}

// Farbauswahl
function selectColor(player, color) {
    const otherPlayer = player === 1 ? 2 : 1;
    
    // Verhindere dass beide Spieler die gleiche Farbe haben
    if (selectedColors[`player${otherPlayer}`] === color) {
        alert('Beide Spieler müssen unterschiedliche Farben haben!');
        return;
    }
    
    selectedColors[`player${player}`] = color;
    
    // Update UI
    const container = event.currentTarget.parentElement;
    container.querySelectorAll('.color-option').forEach(opt => {
        opt.classList.remove('selected');
    });
    event.currentTarget.classList.add('selected');
}

// Startet das Spiel mit der gewählten Konfiguration
async function startGame() {
    const mode = document.getElementById('gameMode').value;
    const difficulty = document.getElementById('difficulty').value;
    
    isAIMode = (mode === 'ai');
    
    try {
        const response = await fetch(`${API_BASE}/config`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                mode: mode,
                difficulty: difficulty,
                player1Color: selectedColors.player1,
                player2Color: selectedColors.player2
            })
        });

        const result = await response.json();
        
        if (result.success) {
            gameState = result.gameState;
            hideConfig();
            renderBoard();
            updateStatus();
        }
    } catch (error) {
        console.error('Fehler beim Starten des Spiels:', error);
        alert('Fehler beim Starten des Spiels');
    }
}

// Lädt den aktuellen Spielzustand vom Server
async function loadGameState() {
    try {
        const response = await fetch(`${API_BASE}/state`);
        gameState = await response.json();
        renderBoard();
        updateStatus();
    } catch (error) {
        console.error('Fehler beim Laden des Spielzustands:', error);
        document.getElementById('status').textContent = 'Fehler: Server nicht erreichbar';
    }
}

// Rendert das Spielfeld
function renderBoard() {
    const board = document.getElementById('board');
    board.innerHTML = '';

    const cols = gameState.cols;
    const rows = gameState.rows;
    const grid = gameState.grid;

    // Erstelle Spalten
    for (let col = 0; col < cols; col++) {
        const columnDiv = document.createElement('div');
        columnDiv.className = 'column';
        
        // Click-Handler für die gesamte Spalte (nicht im KI-Modus wenn KI am Zug)
        if (!gameState.gameOver && !isWaitingForAI) {
            columnDiv.addEventListener('click', () => makeMove(col));
            columnDiv.style.cursor = 'pointer';
        }

        // Erstelle Zellen in der Spalte
        for (let row = 0; row < rows; row++) {
            const cell = document.createElement('div');
            cell.className = 'cell';
            cell.dataset.row = row;
            cell.dataset.col = col;

            // Setze Farbe basierend auf Symbol
            const cellValue = grid[row][col];
            const colorClass = getColorClassForSymbol(cellValue);
            if (colorClass) {
                cell.classList.add(colorClass);
            }

            columnDiv.appendChild(cell);
        }

        board.appendChild(columnDiv);
    }
}

// Hilfsfunktion: Gibt die CSS-Klasse für ein Symbol zurück
function getColorClassForSymbol(symbol) {
    switch (symbol) {
        case 'Y': return 'yellow';
        case 'R': return 'red';
        case 'B': return 'blue';
        case 'G': return 'green';
        default: return null;
    }
}

// Führt einen Spielzug aus
async function makeMove(column) {
    if (gameState.gameOver || isWaitingForAI) {
        return;
    }

    try {
        const response = await fetch(`${API_BASE}/move`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ column: column })
        });

        const result = await response.json();
        
        if (result.success) {
            gameState = result.gameState;
            renderBoard();
            updateStatus();

            // Zeige Game Over Overlay wenn Spiel beendet
            if (gameState.gameOver) {
                setTimeout(() => showGameOverOverlay(), 500);
            } else if (isAIMode && gameState.player2Name === 'KI') {
                // KI ist am Zug - verzögert ausführen
                setTimeout(() => makeAIMove(), 800);
            }
        } else {
            document.getElementById('status').textContent = 'Ungültiger Zug! Spalte ist voll.';
            setTimeout(() => updateStatus(), 2000);
        }
    } catch (error) {
        console.error('Fehler beim Ausführen des Zugs:', error);
        document.getElementById('status').textContent = 'Fehler beim Ausführen des Zugs';
    }
}

// Lässt die KI einen Zug machen
async function makeAIMove() {
    if (gameState.gameOver) {
        return;
    }
    
    isWaitingForAI = true;
    updateStatus();
    
    try {
        // Hole KI-Zug vom Server
        const aiResponse = await fetch(`${API_BASE}/aimove`, {
            method: 'POST'
        });
        
        const aiResult = await aiResponse.json();
        
        if (aiResult.success && aiResult.column !== undefined) {
            // Führe den KI-Zug aus
            const response = await fetch(`${API_BASE}/move`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ column: aiResult.column })
            });

            const result = await response.json();
            
            if (result.success) {
                gameState = result.gameState;
                isWaitingForAI = false;
                renderBoard();
                updateStatus();

                // Zeige Game Over Overlay wenn Spiel beendet
                if (gameState.gameOver) {
                    setTimeout(() => showGameOverOverlay(), 500);
                }
            }
        }
    } catch (error) {
        console.error('Fehler beim KI-Zug:', error);
        isWaitingForAI = false;
    }
}

// Aktualisiert die Statusanzeige
function updateStatus() {
    const currentPlayerDiv = document.getElementById('currentPlayer');
    const statusDiv = document.getElementById('status');

    if (gameState.gameOver) {
        if (gameState.isDraw) {
            statusDiv.textContent = '🤝 Unentschieden! 🤝';
            statusDiv.className = 'status';
        } else {
            const colorClass = 'player-' + gameState.winnerColor.toLowerCase();
            statusDiv.innerHTML = `🎉 <span class="${colorClass}">${gameState.winner} (${gameState.winnerColor})</span> hat gewonnen! 🎉`;
            statusDiv.className = 'status winner';
        }
        currentPlayerDiv.style.visibility = 'hidden';
    } else {
        const colorClass = 'player-' + gameState.currentPlayerColor.toLowerCase();
        const displayText = isWaitingForAI ? 'KI denkt nach...' : 
            `Aktueller Spieler: <span class="${colorClass}">${gameState.currentPlayer} (${gameState.currentPlayerColor})</span>`;
        currentPlayerDiv.innerHTML = displayText;
        currentPlayerDiv.style.visibility = 'visible';
        statusDiv.textContent = '';
        statusDiv.className = 'status';
    }
}

// Zeigt das Game Over Overlay
function showGameOverOverlay() {
    const overlay = document.getElementById('gameOverOverlay');
    const gameOverText = document.getElementById('gameOverText');

    if (gameState.isDraw) {
        gameOverText.textContent = '🤝 Unentschieden! 🤝';
    } else {
        gameOverText.textContent = `🎉 ${gameState.winner} (${gameState.winnerColor}) hat gewonnen! 🎉`;
    }

    overlay.style.display = 'flex';
}

// Versteckt das Game Over Overlay
function hideGameOverOverlay() {
    const overlay = document.getElementById('gameOverOverlay');
    overlay.style.display = 'none';
}

// Setzt das Spiel zurück (zeigt Konfiguration erneut)
async function resetGame() {
    hideGameOverOverlay();
    showConfig();
}

// Tastatursteuerung (optional)
document.addEventListener('keydown', (event) => {
    if (gameState && !gameState.gameOver && !isWaitingForAI) {
        const key = parseInt(event.key);
        if (key >= 0 && key < gameState.cols) {
            makeMove(key);
        }
    }
    
    if (event.key === 'n' || event.key === 'N') {
        showConfig();
    }
});
