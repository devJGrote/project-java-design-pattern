package de.inosofttech.example.decorator.without;

// Kaffee mit Schokolade
public class CoffeeWithChocolate extends Coffee {
    @Override
    public String getDescription() {
        return super.getDescription() + " + Schokolade";
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.7;
    }
}
