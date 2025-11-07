package de.inosofttech.example.testing;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;  

public class CircleTest {
    @Test
    @DisplayName("Test calculateArea method")
    public void testCalculateArea() {
        double radius = 5.0;
        double expectedArea = 78.53981633974483;
        double actualArea = Circle.calculateArea(radius);
        assertEquals(expectedArea, actualArea);
    }
    
    @Test
    @DisplayName("Test calculateArea with zero radius")
    public void testCalculateAreaWithZeroRadius() {
        double radius = 0.0;
        double expectedArea = 0.0;
        double actualArea = Circle.calculateArea(radius);
        assertEquals(expectedArea, actualArea);
    }

    @Test
    @DisplayName("Test calculateArea with one radius")
    public void testCalculateAreaWithOneRadius() {
        double radius = 1.0;
        double expectedArea = Math.PI;
        double actualArea = Circle.calculateArea(radius);
        assertEquals(expectedArea, actualArea);
    }

    @Test
    @DisplayName("Test getRadius method")
    public void testGetRadius() {
        Circle circle = new Circle(5.0);
        assertEquals(5.0, circle.getRadius());
    }

    @Test
    @DisplayName("Test setRadius method")
    public void testSetRadius() {
        Circle circle = new Circle(5.0);
        circle.setRadius(10.0);
        assertEquals(10.0, circle.getRadius());
    }

    @Test
    @DisplayName("Test Circle constructor")
    public void testConstructor() {
        double radius = 7.5;
        Circle circle = new Circle(radius);
        assertEquals(radius, circle.getRadius());
    }

}