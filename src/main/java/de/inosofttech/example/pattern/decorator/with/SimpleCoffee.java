package de.inosofttech.example.pattern.decorator.with;

// Einfache Kaffee-Implementierung
public class SimpleCoffee implements Coffee {
    @Override
    public String getDescription() {
        return "Einfacher Kaffee";
    }

    @Override
    public double getCost() {
        return 2.0;
    }
}