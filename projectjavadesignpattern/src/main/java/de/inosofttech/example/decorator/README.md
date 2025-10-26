# Decorator Design Pattern - Example

## Übersicht

Das **Decorator Pattern** ist ein strukturelles Design Pattern, das es ermöglicht, Objekten dynamisch neue Funktionalität hinzuzufügen. Dieses Package zeigt zwei verschiedene Ansätze zur Implementierung dieser Funktionalität:

1. **WITHOUT Decorator Pattern** - Der traditionelle Vererbungs-Ansatz
2. **WITH Decorator Pattern** - Der moderne Kompositions-Ansatz

---

## 📂 Verzeichnisstruktur

```
decorator/
├── with/                             # ✅ Decorator Pattern (Komposition)
│   ├── Coffee.java                  # Interface
│   ├── SimpleCoffee.java            # Basis-Implementierung
│   ├── CoffeeDecorator.java         # Abstrakte Basis-Klasse
│   ├── MilkDecorator.java           # Milch-Decorator
│   ├── SugarDecorator.java          # Zucker-Decorator
│   ├── ChocolateDecorator.java      # Schokoladen-Decorator
│   ├── MainWithDecorator.java       # Demo
│   └── README.md                    # Dokumentation
├── without/                          # ❌ Traditioneller Ansatz (Vererbung)
│   ├── Coffee.java                  # Basis-Klasse
│   ├── CoffeeWithMilk.java
│   ├── CoffeeWithSugar.java
│   ├── CoffeeWithChocolate.java
│   ├── CoffeeWithMilkAndSugar.java
│   ├── CoffeeWithMilkAndChocolate.java
│   ├── CoffeeWithSugarAndChocolate.java
│   ├── CoffeeWithMilkAndSugarAndChocolate.java    # Klassen-Explosion!
│   ├── MainWithoutDecorator.java    # Demo
│   └── README.md                    # Dokumentation
└── README.md                         # Diese Datei
```

---

## 🔴 WITHOUT Decorator Pattern (Vererbungs-Ansatz)

### Problem: Klassen-Explosion (Class Explosion)

Wenn wir nur Vererbung verwenden, müssen wir für jede Kombination von Eigenschaften eine separate Klasse erstellen.

```java
Coffee plain = new Coffee();                    // Basis
Coffee withMilk = new CoffeeWithMilk();        // Klasse 1
Coffee withSugar = new CoffeeWithSugar();      // Klasse 2
Coffee withBoth = new CoffeeWithMilkAndSugar(); // Klasse 3 ❌ NOTWENDIG!
```

### Nachteile ❌

| Problem | Erklärung |
|---------|-----------|
| **Klassen-Explosion** | Für n Zutaten benötigen wir 2^n verschiedene Klassen |
| **Keine Flexibilität** | Neue Kombinationen erfordern neue Klassen |
| **Tight Coupling** | Klassen sind stark miteinander verknüpft |
| **Verletzung des DRY-Prinzips** | Code-Duplikation in ähnlichen Klassen |
| **Schwer zu warten** | Bei Änderungen müssen viele Klassen angepasst werden |
| **Keine Runtime-Flexibilität** | Kombinationen müssen zur Compile-Zeit definiert sein |

### Beispiel-Ausgabe:

```
=== OHNE Decorator Pattern (Vererbung) ===

1. Einfacher Kaffee kostet €2.0
2. Einfacher Kaffee + Milch kostet €2.5
3. Einfacher Kaffee + Zucker kostet €2.3

Probleme:
- Für die Kombination Milch + Zucker benötigen wir eine separate CoffeeWithMilkAndSugar Klasse
- Für n Zutaten benötigen wir 2^n verschiedene Klassen
- Nicht flexibel für Laufzeit-Kombinationen
```

---

## 🟢 WITH Decorator Pattern (Kompositions-Ansatz)

### Lösung: Dynamische Komposition

Das Decorator Pattern löst diese Probleme durch Komposition statt Vererbung:

```java
Coffee plain = new SimpleCoffee();                           // Basis
Coffee withMilk = new MilkDecorator(plain);                 // Wrapper 1
Coffee withMilkAndSugar = new SugarDecorator(withMilk);     // Wrapper 2 ✅
```

### Vorteile ✅

| Vorteil | Erklärung |
|---------|-----------|
| **Flexible Kombinationen** | Beliebige Kombinationen durch Wrapping möglich |
| **Keine Klassen-Explosion** | Nur n Decorator-Klassen für n Zutaten benötigt |
| **Open/Closed Principle** | Offen für Erweiterungen, geschlossen für Änderungen |
| **Single Responsibility** | Jede Decorator-Klasse hat genau eine Verantwortung |
| **Runtime-Flexibilität** | Kombinationen können zur Laufzeit definiert werden |
| **Einfache Wartung** | Neue Decorators ohne Änderung bestehenden Codes |
| **Keine Code-Duplikation** | Jede Logik nur einmal implementiert |

### Beispiel Ausgabe WITH

```
=== MIT Decorator Pattern (Komposition) ===

1. Einfacher Kaffee kostet €2.0
2. Einfacher Kaffee + Milch kostet €2.5
3. Einfacher Kaffee + Milch + Zucker kostet €2.8
4. Einfacher Kaffee + Milch + Zucker + Schokolade kostet €3.5

Vorteile:
- Flexible Kombinationen: einfach alles wrappen, was Sie brauchen
- Keine Klassen-Explosion: nur Decorators nötig, keine Kombinationen
- Dynamische Komposition zur Laufzeit
- Einfach mit neuen Decorators zu erweitern
```

---

## 🏗️ Architektur-Vergleich

### WITHOUT Pattern (Inheritance)

```
        Coffee (Base)
         △    △    △
         │    │    │
    ┌────┼────┼────┘
    │    │    │
    │    │    └─── CoffeeWithChocolate
    │    │
    │    └─── CoffeeWithSugar
    │         │    △
    │         │    │
    │         └─── CoffeeWithSugarAndChocolate
    │
    ├─── CoffeeWithMilk
    │    │    △         △
    │    │    │         │
    │    └─── ┤         │
    │         │    ┌────┴─ CoffeeWithMilkAndChocolate
    │         │    │
    │         └─── CoffeeWithMilkAndSugar
    │              │    △
    │              │    │
    │              └─── CoffeeWithMilkAndSugarAndChocolate
```

**Problem:** Für 3 Zutaten = 8 Klassen! Für 4 Zutaten = 16 Klassen! 😱

### WITH Pattern (Composition)

```
           ┌─ Coffee (Interface)
           │
           ├─ SimpleCoffee (implements)
           │
           ├─ CoffeeDecorator (abstract, implements)
           │      △
           │      │ extends
           ├──────┼────────────────┐
           │      │                │
        Milk  Sugar           Chocolate
       Decorator Decorator    Decorator
```

**Vorteil:** Für 3 Zutaten = 5 Klassen! Unbegrenzte Kombinationen! 🎉

---

## 💡 Wann verwende ich welches Pattern?

### ✅ Decorator Pattern verwenden, wenn:

- ✓ Sie Funktionalität dynamisch zur Laufzeit hinzufügen müssen
- ✓ Sie viele verschiedene Kombinationen haben können
- ✓ Sie vermeiden wollen, dass die Klassenhierarchie zu tief/komplex wird
- ✓ Sie Decorators in verschiedenen Kontexten verwenden möchten
- ✓ Single Responsibility Principle wichtig ist

### ❌ Vererbung verwenden, wenn:

- ✓ Sie eine klare "is-a" Beziehung haben
- ✓ Es nur wenige, fest definierte Varianten gibt
- ✓ Simplizität wichtiger als Flexibilität ist

---

## 🔧 Praktische Anwendungsbeispiele

### Real-World Use Cases für Decorator Pattern:

1. **Java I/O Streams**
   ```java
   InputStream fis = new FileInputStream("file.txt");
   InputStream bis = new BufferedInputStream(fis);           // Decorator 1
   InputStream dis = new DataInputStream(bis);               // Decorator 2
   ```

2. **UI-Komponenten**
   - Fenster mit Scroll-Bars
   - Buttons mit verschiedenen Styles
   - Text-Felder mit Validierung

3. **Konfiguration**
   - Logger mit verschiedenen Levels
   - HTTP-Requests mit verschiedenen Headers

---

## 🚀 Wie man es ausführt

### Beide Varianten testen:

```bash
# Traditioneller Vererbungs-Ansatz
mvn exec:java -Dexec.mainClass="de.inosofttech.example.decorator.without.MainWithoutDecorator"

# Decorator Pattern Ansatz
mvn exec:java -Dexec.mainClass="de.inosofttech.example.decorator.with.MainWithDecorator"
```

---

## 📚 Zusammenfassung

| Aspekt | WITHOUT (Vererbung) | WITH (Decorator) |
|--------|-------------------|------------------|
| **Klassen für 3 Zutaten** | 2³ = 8 Klassen | 3 + 1 = 4 Klassen |
| **Klassen für 4 Zutaten** | 2⁴ = 16 Klassen! | 4 + 1 = 5 Klassen |
| **Klassen-Wachstum** | Exponentiell 📈 | Linear 📊 |
| **Flexibilität** | Keine ❌ | Maximal ✅ |
| **Runtime-Erweiterung** | Unmöglich ❌ | Möglich ✅ |
| **Code-Duplikation** | Hoch | Niedrig |
| **Wartbarkeit** | Schwierig | Einfach |
| **Skalierbarkeit** | Schlecht | Gut |
| **SOLID Prinzipien** | Verletzt | Einhält ✅ |

**Fazit:** Das Decorator Pattern ist **exponentiell besser** bei vielen möglichen Kombinationen und bietet **maximal Flexibilität**!

---

## 📖 Weitere Ressourcen

- [Design Patterns: Elements of Reusable Object-Oriented Software](https://en.wikipedia.org/wiki/Design_Patterns)
- [Java Decorator Pattern Tutorial](https://www.geeksforgeeks.org/decorator-pattern/)
- [Refactoring Guru - Decorator Pattern](https://refactoring.guru/design-patterns/decorator)
