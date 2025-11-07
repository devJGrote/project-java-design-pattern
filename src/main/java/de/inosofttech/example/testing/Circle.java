package de.inosofttech.example.testing;

public class Circle {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public static double calculateArea(double radius) {
        return Math.PI * radius * radius;
    }
    
}
