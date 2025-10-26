package de.inosofttech.example.decorator.without;

// Kaffee mit Milch und Schokolade
public class CoffeeWithMilkAndChocolate extends Coffee {
    @Override
    public String getDescription() {
        return super.getDescription() + " + Milch + Schokolade";
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.5 + 0.7;
    }
}
