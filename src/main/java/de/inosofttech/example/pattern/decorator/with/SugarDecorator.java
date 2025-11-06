package de.inosofttech.example.pattern.decorator.with;

// Zucker-Decorator
public class SugarDecorator extends CoffeeDecorator {
    public SugarDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Zucker";
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.3;
    }
}