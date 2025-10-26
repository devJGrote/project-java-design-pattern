package de.inosofttech.example.decorator.without;

// Kaffee mit Zucker und Schokolade
public class CoffeeWithSugarAndChocolate extends Coffee {
    @Override
    public String getDescription() {
        return super.getDescription() + " + Zucker + Schokolade";
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.3 + 0.7;
    }
}
