package de.inosofttech.example.testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CircleAssertionMethodsTest {
    
    @Test
    @DisplayName("Test assertEquals method")
    public void testAssertEquals() {
        double radius = 5.0;
        double expectedArea = 78.53981633974483;
        double actualArea = Circle.calculateArea(radius);
        assertEquals(expectedArea, actualArea);
    }
    
    // assertTrue - prüft ob eine Bedingung wahr ist
    @Test
    @DisplayName("Test assertTrue method")
    public void testAssertTrue() {
        double radius = 10.0;
        double area = Circle.calculateArea(radius);
        assertTrue(area > 0, "Fläche sollte größer als 0 sein");
    }
    
    @Test
    @DisplayName("Test assertFalse method")
    public void testAssertFalse() {
        double radius = 0.0;
        double area = Circle.calculateArea(radius);
        assertFalse(area > 0, "Fläche mit Radius 0 sollte nicht größer als 0 sein");
    }
    
    @Test
    @DisplayName("Test assertNotNull method")
    public void testAssertNotNull() {
        Circle circle = new Circle(5.0);
        assertNotNull(circle, "Circle-Objekt sollte nicht null sein");
    }
    
    @Test
    @DisplayName("Test assertNull method")
    public void testAssertNull() {
        Circle circle = null;
        assertNull(circle, "Circle-Objekt sollte null sein");
    }
    
    @Test
    @DisplayName("Test assertSame method")
    public void testAssertSame() {
        Circle circle1 = new Circle(5.0);
        Circle circle2 = circle1;
        assertSame(circle1, circle2, "circle1 und circle2 sollten die gleiche Referenz sein");
    }
    

    @Test
    @DisplayName("Test assertNotSame method")
    public void testAssertNotSame() {
        Circle circle1 = new Circle(5.0);
        Circle circle2 = new Circle(5.0);
        assertNotSame(circle1, circle2, "circle1 und circle2 sollten unterschiedliche Referenzen sein");
    }
}
