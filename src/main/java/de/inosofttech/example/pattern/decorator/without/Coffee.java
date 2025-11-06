package de.inosofttech.example.pattern.decorator.without;

// Basis-Klasse für Kaffee
public class Coffee {
    public String getDescription() {
        return "Einfacher Kaffee";
    }

    public double getCost() {
        return 2.0;
    }
}