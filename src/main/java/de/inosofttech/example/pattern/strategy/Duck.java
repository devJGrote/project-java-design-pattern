package de.inosofttech.example.pattern.strategy;


public abstract class Duck {
    public Duck() {
    }

    public void swim() {
        System.out.println("Alle enten schwimmen, auch Stockenten");
    }

    public abstract void display();
}
