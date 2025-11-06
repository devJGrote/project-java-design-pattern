# Without Decorator Pattern - OHNE Decorator Pattern

## Übersicht

Dieses Package zeigt eine Implementierung **OHNE** das Decorator Pattern unter Verwendung von **reiner Vererbung**.

### ⚠️ Problem: Klassen-Explosion

Alle möglichen Kombinationen erfordern separate Klassen:

- ✅ `Coffee` - Basis-Klasse
- ✅ `CoffeeWithMilk` - Kaffee mit Milch
- ✅ `CoffeeWithSugar` - Kaffee mit Zucker
- ✅ `CoffeeWithChocolate` - Kaffee mit Schokolade
- ✅ `CoffeeWithMilkAndSugar` - Kaffee mit Milch und Zucker
- ✅ `CoffeeWithMilkAndChocolate` - Kaffee mit Milch und Schokolade
- ✅ `CoffeeWithSugarAndChocolate` - Kaffee mit Zucker und Schokolade
- ✅ `CoffeeWithMilkAndSugarAndChocolate` - Kaffee mit Milch, Zucker und Schokolade

**Total: 8 Klassen!** 😱

Für nur 3 Zutaten benötigen wir bereits 2³ = 8 Klassen!

## 📊 Nachteile dieses Ansatzes

| Problem | Beschreibung |
|---------|------------|
| **Klassen-Explosion** | 2^n Klassen für n Zutaten |
| **Code-Duplikation** | Viel wiederholter Code |
| **Schwer erweiterbar** | Neue Zutat = 4 neue Klassen |
| **Tight Coupling** | Klassen sind stark verknüpft |
| **Keine Laufzeit-Flexibilität** | Kombinationen müssen zur Compile-Zeit definiert sein |

## 🚀 Ausführen

```bash
mvn exec:java -Dexec.mainClass="de.inosofttech.example.decorator.without.MainWithoutDecorator"
```

Beispiel-Ausgabe:
```
=== OHNE Decorator Pattern (Vererbung) ===

1. Einfacher Kaffee kostet €2.0
2. Einfacher Kaffee + Milch kostet €2.5
3. Einfacher Kaffee + Zucker kostet €2.3
...
```

## 📚 Vergleich mit WITH Pattern

→ Siehe das `with` Package für eine **bessere Lösung** mit dem Decorator Pattern!
