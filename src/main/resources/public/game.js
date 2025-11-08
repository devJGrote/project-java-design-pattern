// Game of Life Web Client
let canvas, ctx;
let cellSize = 5;
let isAutoPlaying = false;
let autoPlayInterval = null;

// Initialize canvas and start game
window.onload = function() {
    canvas = document.getElementById('gameCanvas');
    ctx = canvas.getContext('2d');
    
    initGame();
    updateSpeedDisplay();
    updateProbDisplay();
};

// Initialize new game
async function initGame() {
    const width = parseInt(document.getElementById('width').value);
    const height = parseInt(document.getElementById('height').value);
    const probability = parseFloat(document.getElementById('probability').value) / 100;
    
    try {
        const response = await fetch(`/api/init?width=${width}&height=${height}&probability=${probability}`, {
            method: 'POST'
        });
        const state = await response.json();
        updateCanvas(state);
        updateStats(state);
    } catch (error) {
        console.error('Error initializing game:', error);
    }
}

// Get current state
async function getState() {
    try {
        const response = await fetch('/api/state');
        const state = await response.json();
        updateCanvas(state);
        updateStats(state);
    } catch (error) {
        console.error('Error getting state:', error);
    }
}

// Next generation
async function nextGeneration() {
    try {
        const response = await fetch('/api/next', {
            method: 'POST'
        });
        const state = await response.json();
        updateCanvas(state);
        updateStats(state);
    } catch (error) {
        console.error('Error getting next generation:', error);
    }
}

// Run multiple generations
async function runMultiple() {
    try {
        const response = await fetch('/api/run?count=10', {
            method: 'POST'
        });
        const state = await response.json();
        updateCanvas(state);
        updateStats(state);
    } catch (error) {
        console.error('Error running generations:', error);
    }
}

// Reset game
async function resetGame() {
    pauseAutoPlay();
    try {
        const response = await fetch('/api/reset', {
            method: 'POST'
        });
        const state = await response.json();
        updateCanvas(state);
        updateStats(state);
    } catch (error) {
        console.error('Error resetting game:', error);
    }
}

// Start auto-play
function startAutoPlay() {
    if (isAutoPlaying) return;
    
    isAutoPlaying = true;
    const speed = parseInt(document.getElementById('speed').value);
    
    autoPlayInterval = setInterval(() => {
        nextGeneration();
    }, speed);
}

// Pause auto-play
function pauseAutoPlay() {
    isAutoPlaying = false;
    if (autoPlayInterval) {
        clearInterval(autoPlayInterval);
        autoPlayInterval = null;
    }
}

// Update canvas with game state
function updateCanvas(state) {
    if (!state || !state.cells || state.cells.length === 0) {
        return;
    }
    
    const width = state.width;
    const height = state.height;
    
    // Calculate cell size to fit canvas
    const maxCanvasWidth = Math.min(1200, window.innerWidth - 100);
    const maxCanvasHeight = Math.min(800, window.innerHeight - 400);
    
    cellSize = Math.floor(Math.min(maxCanvasWidth / width, maxCanvasHeight / height));
    cellSize = Math.max(1, cellSize); // Minimum 1 pixel
    
    canvas.width = width * cellSize;
    canvas.height = height * cellSize;
    
    // Clear canvas
    ctx.fillStyle = '#1a1a1a';
    ctx.fillRect(0, 0, canvas.width, canvas.height);
    
    // Draw cells
    for (let row = 0; row < height; row++) {
        for (let col = 0; col < width; col++) {
            if (state.cells[row][col]) {
                // Alive cell - gradient effect
                const gradient = ctx.createLinearGradient(
                    col * cellSize, row * cellSize,
                    (col + 1) * cellSize, (row + 1) * cellSize
                );
                gradient.addColorStop(0, '#00ff88');
                gradient.addColorStop(1, '#00cc66');
                
                ctx.fillStyle = gradient;
                ctx.fillRect(col * cellSize, row * cellSize, cellSize - 1, cellSize - 1);
            }
        }
    }
    
    // Draw grid for small cell sizes
    if (cellSize >= 3) {
        ctx.strokeStyle = '#333';
        ctx.lineWidth = 0.5;
        
        for (let i = 0; i <= width; i++) {
            ctx.beginPath();
            ctx.moveTo(i * cellSize, 0);
            ctx.lineTo(i * cellSize, canvas.height);
            ctx.stroke();
        }
        
        for (let i = 0; i <= height; i++) {
            ctx.beginPath();
            ctx.moveTo(0, i * cellSize);
            ctx.lineTo(canvas.width, i * cellSize);
            ctx.stroke();
        }
    }
}

// Update statistics display
function updateStats(state) {
    document.getElementById('generation').textContent = state.generation || 0;
    document.getElementById('aliveCount').textContent = state.aliveCount || 0;
    document.getElementById('gridSize').textContent = `${state.width || 0}×${state.height || 0}`;
}

// Update speed display
function updateSpeedDisplay() {
    const speed = document.getElementById('speed').value;
    document.getElementById('speedDisplay').textContent = speed + 'ms';
    
    // Update interval if auto-playing
    if (isAutoPlaying) {
        pauseAutoPlay();
        startAutoPlay();
    }
}

// Update probability display
function updateProbDisplay() {
    const prob = document.getElementById('probability').value;
    document.getElementById('probDisplay').textContent = prob + '%';
}

// Handle canvas click (optional: toggle cells)
canvas.addEventListener('click', function(event) {
    const rect = canvas.getBoundingClientRect();
    const x = event.clientX - rect.left;
    const y = event.clientY - rect.top;
    
    const col = Math.floor(x / cellSize);
    const row = Math.floor(y / cellSize);
    
    console.log(`Clicked cell: (${row}, ${col})`);
    // Could implement toggle cell functionality here
});

// Keyboard shortcuts
document.addEventListener('keydown', function(event) {
    switch(event.key) {
        case ' ':
            event.preventDefault();
            if (isAutoPlaying) {
                pauseAutoPlay();
            } else {
                startAutoPlay();
            }
            break;
        case 'n':
            event.preventDefault();
            nextGeneration();
            break;
        case 'r':
            event.preventDefault();
            resetGame();
            break;
    }
});
