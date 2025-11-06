# With Decorator Pattern - MIT Decorator Pattern

## Übersicht

Dieses Package zeigt eine **elegante Implementierung** des Decorator Patterns unter Verwendung von **Komposition statt Vererbung**.

## ✅ Lösung: Nur 4 Klassen!

Für beliebig viele Kombinationen benötigen wir nur **eine Basis-Klasse + Decorators**:

- ✅ `Coffee` - Interface
- ✅ `SimpleCoffee` - Basis-Implementierung
- ✅ `MilkDecorator` - Decorator für Milch
- ✅ `SugarDecorator` - Decorator für Zucker
- ✅ `ChocolateDecorator` - Decorator für Schokolade

**Total: 5 Klassen** für **unbegrenzte Kombinationen!** 🎉

## 🎯 Vorteile dieses Ansatzes

| Vorteil | Beschreibung |
|---------|------------|
| **Flexible Kombinationen** | Beliebig kombinierbar zur Laufzeit |
| **Keine Klassen-Explosion** | Nur n Decorators für n Zutaten |
| **Open/Closed Principle** | Offen für Erweiterung, geschlossen für Änderung |
| **Single Responsibility** | Jede Klasse hat genau eine Aufgabe |
| **Wiederverwendbar** | Decorators in verschiedenen Kontexten einsetzbar |
| **Leicht zu warten** | Neue Zutat = 1 neue Decorator-Klasse |

## 💡 Wie es funktioniert

```java
// Basis
Coffee coffee = new SimpleCoffee();                           // €2.00
System.out.println(coffee.getDescription());                  // "Einfacher Kaffee"

// Wrapper 1: Milch hinzufügen
coffee = new MilkDecorator(coffee);                          // +€0.50
System.out.println(coffee.getDescription());                  // "Einfacher Kaffee + Milch"

// Wrapper 2: Zucker hinzufügen
coffee = new SugarDecorator(coffee);                         // +€0.30
System.out.println(coffee.getDescription());                  // "Einfacher Kaffee + Milch + Zucker"

// Wrapper 3: Schokolade hinzufügen
coffee = new ChocolateDecorator(coffee);                     // +€0.70
System.out.println(coffee.getDescription());                  // "Einfacher Kaffee + Milch + Zucker + Schokolade"
System.out.println(coffee.getCost());                         // €3.50
```

## 🚀 Ausführen

```bash
mvn exec:java -Dexec.mainClass="de.inosofttech.example.decorator.with.MainWithDecorator"
```

Beispiel-Ausgabe:
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

## 🏗️ Architektur

```
┌─────────────────────┐
│   Coffee (IF)       │
└──────────┬──────────┘
           △
           │ implements
      ┌────┴─────┐
      │           │
 SimpleCoffee  CoffeeDecorator (abstract)
               △      △      △
               │      │      │
           Milk   Sugar  Chocolate
```

## 📚 Vergleich mit WITHOUT Pattern

Siehe das `without` Package für einen Vergleich mit dem traditionellen Ansatz.
