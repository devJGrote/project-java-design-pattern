package de.inosofttech.example.decorator.without;

// Kaffee mit Milch
public class CoffeeWithMilk extends Coffee {
    @Override
    public String getDescription() {
        return super.getDescription() + " + Milch";
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.5;
    }
}