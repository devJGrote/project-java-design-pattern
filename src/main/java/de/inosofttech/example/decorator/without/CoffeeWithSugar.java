package de.inosofttech.example.decorator.without;

// Kaffee mit Zucker
public class CoffeeWithSugar extends Coffee {
    @Override
    public String getDescription() {
        return super.getDescription() + " + Zucker";
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.3;
    }
}