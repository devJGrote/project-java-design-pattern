package de.inosofttech.example.pattern.decorator.with;

// Milch-Decorator
public class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Milch";
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.5;
    }
}