# 4 Gewinnt (Connect Four)

Ein klassisches "4 Gewinnt" Spiel mit Web-Interface, wahlweise für zwei Spieler oder gegen eine KI mit verschiedenen Schwierigkeitsgraden.

## Spielregeln

- Zwei Spieler spielen abwechselnd
- Jeder Spieler wirft einen Stein in eine Spalte
- Der Stein fällt nach unten bis zum Boden oder auf einen anderen Stein
- Gewonnen hat, wer als Erster 4 Steine seiner Farbe in einer Reihe hat:
  - Horizontal (waagerecht)
  - Vertikal (senkrecht)
  - Diagonal
- Wenn das Spielfeld voll ist ohne Gewinner: Unentschieden

## Neue Features ✨

### Spielmodi
1. **2 Spieler (Lokal)** - Klassisches Spiel für zwei Personen
2. **Gegen KI** - Spiele gegen den Computer

### KI-Schwierigkeitsgrade
- **Leicht**: KI wählt zufällige Züge
- **Mittel**: KI blockiert Gewinnzüge und sucht eigene Gewinnmöglichkeiten
- **Schwer**: KI verwendet Minimax-Algorithmus für optimale Züge

### Farbauswahl
Wähle aus **4 Farben** für beide Spieler:
- 🟡 Gelb
- 🔴 Rot
- 🔵 Blau
- 🟢 Grün

## Spielfeld

- Standard: 6 Zeilen × 7 Spalten
- Kann im Code angepasst werden

## Installation & Start

### Voraussetzungen

- Java 21 oder höher
- Maven

### Projekt kompilieren

```bash
mvn clean compile
```

### Spiel starten

Es gibt zwei Modi:

#### 1. Web-Modus (empfohlen)

```bash
java -cp "target/classes;target/jmh-benchmarks.jar" de.inosofttech.example.viergewinnt.ConnectFourWebServer
```

Dann im Browser öffnen: <http://localhost:8081/connectfour.html>

#### 2. Konsolen-Modus

```bash
java -cp target/classes de.inosofttech.example.viergewinnt.ConnectFourMain
```

Wähle Option 1 für Konsolen-Modus.

## Web-Interface Features

- ✨ Moderne, responsive Benutzeroberfläche
- 🎨 Animierte Spielsteine beim Einwerfen
- 🎮 Klicke auf eine Spalte zum Spielen
- 🤖 KI mit 3 Schwierigkeitsgraden
- 🎨 4 wählbare Farben (Gelb, Rot, Blau, Grün)
- ⚙️ Konfigurationsmenü beim Start
- ⌨️ Tastatursteuerung: Zahlen 0-6 für Spalten, 'N' für neues Spiel
- 🏆 Automatische Gewinner-Erkennung mit Overlay
- 🔄 "Neues Spiel" Button zum Zurücksetzen

## Projekt-Struktur

```
de.inosofttech.example.viergewinnt/
├── Player.java              # Spieler-Klasse (Name, Farbe, Symbol)
├── Board.java               # Spielfeld-Logik
├── ConnectFourGame.java     # Hauptspiellogik & Gewinnprüfung
├── ConnectFourAI.java       # KI mit 3 Schwierigkeitsgraden
├── GameState.java           # Spielzustand für Web-API
├── ConnectFourWebServer.java # HTTP-Server
└── ConnectFourMain.java     # Einstiegspunkt

resources/public/
├── connectfour.html         # Web-Interface mit Konfiguration
└── connectfour.js           # Frontend-Logik mit KI-Unterstützung
```

## API-Endpunkte

Der Web-Server bietet folgende REST-API:

- `GET /api/state` - Aktuellen Spielzustand abrufen
- `POST /api/move` - Spielzug ausführen (JSON: `{"column": 0-6}`)
- `POST /api/config` - Neues Spiel mit Konfiguration starten
- `POST /api/aimove` - KI-Zug anfordern
- `POST /api/reset` - Spiel zurücksetzen

## KI-Implementierung

### Leicht
- Wählt zufällige gültige Spalten
- Perfekt für Anfänger

### Mittel
- Erkennt und blockiert Gewinnzüge des Gegners
- Sucht eigene Gewinnmöglichkeiten
- Bevorzugt zentrale Positionen

### Schwer
- Verwendet Minimax-Algorithmus mit Alpha-Beta-Pruning
- Berechnet optimale Züge voraus (Tiefe: 4)
- Sehr herausfordernd!

## Anpassungen

### Spielfeldgröße ändern

In `ConnectFourWebServer.java` (Zeile 26):

```java
game = new ConnectFourGame(rows, cols);
```

Beispiel für 8×8 Spielfeld:

```java
game = new ConnectFourGame(8, 8);
```

### Port ändern

In `ConnectFourWebServer.java` (Zeile 20):

```java
private static final int PORT = 8081;
```

## Spielerklassen

### Player

Repräsentiert einen Spieler mit:

- Name
- Farbe (GELB/ROT/BLAU/GRÜN)
- Symbol (Y/R/B/G für das Spielfeld)

### Board

Verwaltet das Spielfeld:

- Konfigurierbare Größe (Zeilen × Spalten)
- `dropPiece(column, symbol)` - Stein einwerfen
- `isColumnAvailable(column)` - Prüfen ob Spalte frei
- `isFull()` - Prüfen ob Spielfeld voll

### ConnectFourGame

Hauptspiellogik:

- Spielerwechsel
- Gewinnprüfung (horizontal, vertikal, diagonal)
- Unentschieden-Prüfung
- Spielzustand verwalten
- KI-Modus-Unterstützung

### ConnectFourAI

KI-Spieler mit verschiedenen Strategien:

- **EASY**: Zufällige Züge
- **MEDIUM**: Taktische Züge (Blockieren & Angreifen)
- **HARD**: Minimax mit Alpha-Beta-Pruning

## Entwickelt von

inoSoftTech Example Project

## Lizenz

Beispielprojekt für Bildungszwecke
