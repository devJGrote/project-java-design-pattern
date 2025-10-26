# Java Design Patterns - Beispielsammlung

Dieses Projekt zeigt verschiedene **Design Patterns** in Java mit praktischen Beispielen.

## 📚 Verfügbare Patterns

### 1. Decorator Pattern

Das **Decorator Pattern** ist ein strukturelles Design Pattern, das es ermöglicht, Objekten dynamisch neue Verantwortlichkeiten hinzuzufügen.

#### 📁 Package: `de.inosofttech.example.decorator`

- **WITH Pattern** (`with/`) - ✅ Moderne Implementierung mit Komposition
- **WITHOUT Pattern** (`without/`) - ❌ Traditioneller Ansatz mit Vererbung

**Beispiel: Kaffee-Bestellung**

```java
// MIT Decorator Pattern
Coffee coffee = new SimpleCoffee();                    // €2.00
coffee = new MilkDecorator(coffee);                   // +€0.50
coffee = new SugarDecorator(coffee);                  // +€0.30
coffee = new ChocolateDecorator(coffee);              // +€0.70
// Gesamt: €3.50 - Unbegrenzte Kombinationen möglich!
```

vs.

```java
// OHNE Decorator Pattern (Vererbung)
// Benötigt 8 separate Klassen für 3 Zutaten:
// Coffee, CoffeeWithMilk, CoffeeWithSugar, CoffeeWithChocolate,
// CoffeeWithMilkAndSugar, CoffeeWithMilkAndChocolate, 
// CoffeeWithSugarAndChocolate, CoffeeWithMilkAndSugarAndChocolate
```

## 🚀 Schnellstart

### Voraussetzungen

- Java 21 (oder höher)
- Maven 3.8.x

### Build

```bash
mvn clean install
```

### Decorator Pattern ausführen

```bash
# MIT Decorator Pattern (Empfohlen)
mvn exec:java -Dexec.mainClass="de.inosofttech.example.decorator.with.MainWithDecorator"

# OHNE Decorator Pattern (zum Vergleich)
mvn exec:java -Dexec.mainClass="de.inosofttech.example.decorator.without.MainWithoutDecorator"
```

## 📊 Vergleich: WITH vs WITHOUT

| Aspekt | WITHOUT (Vererbung) | WITH (Decorator) |
|--------|-------------------|------------------|
| **Klassen für 3 Zutaten** | 8 ❌ | 4 ✅ |
| **Klassen für 4 Zutaten** | 16 ❌ | 5 ✅ |
| **Flexibilität** | Gering | Maximal |
| **Wartbarkeit** | Schwierig | Einfach |
| **Code-Duplikation** | Hoch | Niedrig |

## 📖 Dokumentation

- 📄 [Decorator Pattern - Hauptdokumentation](src/main/java/de/inosofttech/example/decorator/README.md)
- 📄 [WITH Pattern - Details](src/main/java/de/inosofttech/example/decorator/with/README.md)
- 📄 [WITHOUT Pattern - Details](src/main/java/de/inosofttech/example/decorator/without/README.md)

## 🎯 Lernziele

Dieses Projekt zeigt:

1. **Unterschied zwischen Vererbung und Komposition**
   - Warum Komposition flexibler ist
   - Wie man Vererbungs-Fallen vermeidet

2. **Design Pattern in der Praxis**
   - Struktur des Decorator Patterns
   - Praktische Anwendbarkeit
   - Real-World Beispiele

3. **SOLID Prinzipien**
   - Open/Closed Principle
   - Single Responsibility Principle
   - Liskov Substitution Principle

## 💡 Real-World Anwendungen

Das Decorator Pattern wird verwendet in:

- **Java I/O Streams**: `BufferedInputStream`, `DataInputStream`, etc.
- **UI-Frameworks**: Scroll-Bars, Borders, Padding
- **Web-Frameworks**: HTTP-Interceptors, Middleware
- **Logging**: Logger mit verschiedenen Levels und Outputs

## 📁 Projektstruktur

```
projectjavadesignpattern/
├── pom.xml
├── README.md                          # Diese Datei
└── src/
    └── main/
        └── java/
            └── de/
                └── inosofttech/
                    └── example/
                        └── decorator/
                            ├── README.md              # Pattern-Dokumentation
                            ├── with/                  # ✅ Decorator Pattern
                            │   ├── Coffee.java
                            │   ├── SimpleCoffee.java
                            │   ├── CoffeeDecorator.java
                            │   ├── MilkDecorator.java
                            │   ├── SugarDecorator.java
                            │   ├── ChocolateDecorator.java
                            │   ├── MainWithDecorator.java
                            │   └── README.md
                            └── without/               # ❌ Traditioneller Ansatz
                                ├── Coffee.java
                                ├── CoffeeWithMilk.java
                                ├── CoffeeWithSugar.java
                                ├── CoffeeWithChocolate.java
                                ├── CoffeeWithMilkAndSugar.java
                                ├── CoffeeWithMilkAndChocolate.java
                                ├── CoffeeWithSugarAndChocolate.java
                                ├── CoffeeWithMilkAndSugarAndChocolate.java
                                ├── MainWithoutDecorator.java
                                └── README.md
```

## ✅ Checkliste

- [x] Decorator Pattern Implementierung
- [x] Traditioneller Ansatz zum Vergleich
- [x] Umfassende Dokumentation
- [x] Code-Beispiele
- [x] Maven-Integration
- [x] Main-Klassen zum Testen

## 🔧 Konfiguration

### Java Version

Das Projekt verwendet **Java 21** (LTS - Long-Term Support).

Zu sehen in `pom.xml`:
```xml
<maven.compiler.source>21</maven.compiler.source>
<maven.compiler.target>21</maven.compiler.target>
```

## 📝 Lizenz

Dieses Projekt ist ein Lernbeispiel und steht unter MIT Lizenz.

## 🤝 Beitragen

Verbesserungsvorschläge sind willkommen! Bitte erstellen Sie einen Pull Request.

## 📞 Kontakt

Fragen? Eröffnen Sie ein Issue im Repository!

---

**Happy Learning! 🚀**
