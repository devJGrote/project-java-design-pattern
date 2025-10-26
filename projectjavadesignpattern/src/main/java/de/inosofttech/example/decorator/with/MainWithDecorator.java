package de.inosofttech.example.decorator.with;

/**
 * Beispiel MIT Decorator Pattern (Kompositions-Ansatz)
 * 
 * Dieser Ansatz nutzt Komposition und Wrapping, um Funktionalität dynamisch hinzuzufügen.
 * Vorteile: Flexible Kombinationen, keine Klassen-Explosion, Laufzeit-Flexibilität
 */
public class MainWithDecorator {
    public static void main(String[] args) {
        System.out.println("=== MIT Decorator Pattern (Komposition) ===\n");
        
        // Einfachen Kaffee erstellen
        Coffee simpleCoffee = new SimpleCoffee();
        System.out.println("1. " + simpleCoffee.getDescription() + " kostet €" + simpleCoffee.getCost());

        // Dynamisch Milch hinzufügen durch Wrapping
        Coffee milkCoffee = new MilkDecorator(simpleCoffee);
        System.out.println("2. " + milkCoffee.getDescription() + " kostet €" + milkCoffee.getCost());

        // Dynamisch Zucker zum bereits dekorierten Kaffee hinzufügen
        Coffee sugarMilkCoffee = new SugarDecorator(milkCoffee);
        System.out.println("3. " + sugarMilkCoffee.getDescription() + " kostet €" + sugarMilkCoffee.getCost());
        
        // Dynamisch Schokolade hinzufügen - beliebig kombinierbar!
        Coffee chocolateCoffee = new ChocolateDecorator(sugarMilkCoffee);
        System.out.println("4. " + chocolateCoffee.getDescription() + " kostet €" + chocolateCoffee.getCost());
        
        System.out.println("\nVorteile:");
        System.out.println("- Flexible Kombinationen: einfach alles wrappen, was Sie brauchen");
        System.out.println("- Keine Klassen-Explosion: nur Decorators nötig, keine Kombinationen");
        System.out.println("- Dynamische Komposition zur Laufzeit");
        System.out.println("- Einfach mit neuen Decorators zu erweitern");
    }
}