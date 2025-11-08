# 4 Gewinnt Spiel - Schnellstart

## Einfachster Start (Windows)

### Web-Version (empfohlen)

Doppelklick auf: `start-viergewinnt.bat`

Der Browser öffnet automatisch auf: <http://localhost:8081/connectfour.html>

### Konsolen-Version

Doppelklick auf: `start-viergewinnt-console.bat`

## Manueller Start

### Web-Version

```bash
mvn clean package -DskipTests
java -cp "target/classes;target/jmh-benchmarks.jar" de.inosofttech.example.viergewinnt.ConnectFourWebServer
```

Dann Browser öffnen: <http://localhost:8081/connectfour.html>

### Konsolen-Version

```bash
mvn clean compile
java -cp "target/classes;target/jmh-benchmarks.jar" de.inosofttech.example.viergewinnt.ConnectFourMain
```

Wähle Option 1 für Konsolen-Modus

## Neue Features ✨

### Spielmodi

- 👥 **2 Spieler (Lokal)** - Klassisches Spiel für zwei Personen
- 🤖 **Gegen KI** - Spiele gegen den Computer mit 3 Schwierigkeitsgraden

### KI-Schwierigkeitsgrade

- 🟢 **Leicht** - Zufällige Züge (gut für Anfänger)
- 🟡 **Mittel** - Taktische Züge (blockiert und greift an)
- 🔴 **Schwer** - Minimax-Algorithmus (sehr herausfordernd!)

### Farbauswahl

Wähle aus **4 Farben** für beide Spieler:

- 🟡 Gelb
- 🔴 Rot
- 🔵 Blau
- 🟢 Grün

## Spielregeln

- 🎮 Zwei Spieler oder Spieler gegen KI
- 🎯 Ziel: 4 Steine in einer Reihe (horizontal, vertikal oder diagonal)
- 🔄 Abwechselnd Steine in Spalten werfen
- ⚡ Steine fallen nach unten

## Steuerung

- 🖱️ **Maus**: Klicke auf eine Spalte
- ⌨️ **Tastatur**: Zahlen 0-6 für Spalten
- 🔄 **Taste N**: Neues Spiel / Konfiguration öffnen

## Weitere Informationen

Siehe: `src/main/java/de/inosofttech/example/viergewinnt/README.md`
