package de.inosofttech.example.pattern.decorator.without;

/**
 * Beispiel OHNE Decorator Pattern (Vererbungs-Ansatz)
 * 
 * Dieser Ansatz nutzt Vererbung, um Funktionalität hinzuzufügen.
 * Probleme: Klassen-Explosion, unflexible Kombinationen, enge Kopplung
 */
public class MainWithoutDecorator {
    public static void main(String[] args) {
        System.out.println("=== OHNE Decorator Pattern (Vererbung) ===\n");
        
        // Einfachen Kaffee erstellen
        Coffee plainCoffee = new Coffee();
        System.out.println("1. " + plainCoffee.getDescription() + " kostet €" + plainCoffee.getCost());

        // Kaffee mit Milch - benötigt separate Klasse
        Coffee coffeeWithMilk = new CoffeeWithMilk();
        System.out.println("2. " + coffeeWithMilk.getDescription() + " kostet €" + coffeeWithMilk.getCost());

        // Kaffee mit Zucker - benötigt eine weitere separate Klasse
        Coffee coffeeWithSugar = new CoffeeWithSugar();
        System.out.println("3. " + coffeeWithSugar.getDescription() + " kostet €" + coffeeWithSugar.getCost());
        
        System.out.println("\nProbleme:");
        System.out.println("- Für die Kombination Milch + Zucker benötigen wir eine separate CoffeeWithMilkAndSugar Klasse");
        System.out.println("- Für n Zutaten benötigen wir 2^n verschiedene Klassen");
        System.out.println("- Nicht flexibel für Laufzeit-Kombinationen");
    }
}