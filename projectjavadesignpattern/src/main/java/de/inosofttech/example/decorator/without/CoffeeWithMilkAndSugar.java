package de.inosofttech.example.decorator.without;

// Kaffee mit Milch und Zucker
public class CoffeeWithMilkAndSugar extends Coffee {
    @Override
    public String getDescription() {
        return super.getDescription() + " + Milch + Zucker";
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.5 + 0.3;
    }
}
