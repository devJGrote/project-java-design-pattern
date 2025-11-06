package de.inosofttech.example.pattern.decorator.with;

// Schokoladen-Decorator
public class ChocolateDecorator extends CoffeeDecorator {
    public ChocolateDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Schokolade";
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.7;
    }
}
