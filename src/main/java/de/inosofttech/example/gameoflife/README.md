# Conway's Game of Life

Eine Java-Implementierung von Conway's Game of Life - einem zellulären Automaten.

## Spielregeln

Das Game of Life folgt diesen einfachen Regeln:

1. **Unterpopulation**: Eine lebende Zelle mit weniger als 2 lebenden Nachbarn stirbt
2. **Überleben**: Eine lebende Zelle mit 2 oder 3 lebenden Nachbarn überlebt
3. **Überpopulation**: Eine lebende Zelle mit mehr als 3 lebenden Nachbarn stirbt
4. **Reproduktion**: Eine tote Zelle mit genau 3 lebenden Nachbarn wird lebendig

## Klassen

### `Cell`
Enum für den Zellzustand:
- `ALIVE` - Lebende Zelle (Symbol: 'O')
- `DEAD` - Tote Zelle (Symbol: '.')

### `Grid`
Repräsentiert das Spielfeld:
- Beliebige Größe konfigurierbar
- Ränder sind tot (kein Wrap-around)
- Methoden zum Zählen von Nachbarn und Anwenden der Regeln
- Berechnung der nächsten Generation

### `GameOfLife`
Hauptklasse der Simulation:
- Standard: 100x100 Feld
- Zufällige Initialisierung
- Schrittweise Generationen
- Logging der Generationen und Zellzahl

### `GameOfLifeMain`
Einstiegspunkt mit verschiedenen Modi:
- Interaktiver Modus (Enter für nächste Generation)
- Konfigurierbare Feldgröße
- Beispiele für verschiedene Anwendungsfälle

## Verwendung

### Standard-Simulation (100x100)
```java
GameOfLife game = new GameOfLife();
game.nextGeneration(); // Nächste Generation
```

### Custom-Größe
```java
// 50x50 Feld mit 25% initialer Lebendwahrscheinlichkeit
GameOfLife game = new GameOfLife(50, 50, 0.25);
```

### Interaktiver Modus
```java
public static void main(String[] args) {
    GameOfLifeMain.main(args);
}
```

### Mehrere Generationen automatisch
```java
GameOfLife game = new GameOfLife(100, 100, 0.3);
game.runGenerations(10); // 10 Generationen durchlaufen
```

### Kleine Grid-Visualisierung
```java
GameOfLife game = new GameOfLife(20, 20, 0.35);
game.displayGrid(); // Zeigt das Grid in der Konsole
```

## Ausführen

### Programm starten
```bash
mvn compile exec:java -Dexec.mainClass="de.inosofttech.example.gameoflife.GameOfLifeMain"
```

### Tests ausführen
```bash
mvn test -Dtest=CellTest,GridTest,GameOfLifeTest
```

## Beispiele

### Bekannte Muster
Die Implementierung unterstützt alle klassischen Game of Life Muster:
- **Blinker**: Oszillator mit Periode 2
- **Glider**: Sich bewegendes Muster
- **Still Lifes**: Stabile Muster (Block, Bienenstock, etc.)

### Logging
Die Simulation loggt automatisch:
- Initialisierung (Feldgröße)
- Jede Generation mit Anzahl lebender Zellen
- Completion-Status bei automatischen Durchläufen

## Tests

Umfassende Unit-Tests für:
- ✓ Zellzustände
- ✓ Grid-Operationen
- ✓ Nachbarzählung
- ✓ Game of Life Regeln (alle 4 Regeln)
- ✓ Generationswechsel
- ✓ Bekannte Muster (z.B. Blinker)
- ✓ Randbedingungen

Alle Tests: **22 Tests, 0 Fehler**
