package de.inosofttech.example.decorator.without;

// Kaffee mit Milch, Zucker und Schokolade
public class CoffeeWithMilkAndSugarAndChocolate extends Coffee {
    @Override
    public String getDescription() {
        return super.getDescription() + " + Milch + Zucker + Schokolade";
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.5 + 0.3 + 0.7;
    }
}
