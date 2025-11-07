package de.inosofttech.example.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * BeforeAll (einmalig)
  ├─ BeforeEach
  ├─ Test 1
  ├─ AfterEach
  ├─ BeforeEach
  ├─ Test 2
  ├─ AfterEach
  └─ ... (wiederholt für alle Tests)
 * AfterAll (einmalig)
 */
@DisplayName("Circle Tests with Lifecycle Annotations")
public class CircleWithAnnotationTest {
    
    private Circle circle;
    private static int testCount = 0;
    private static long startTime;
    
    /**
     * BeforeAll wird einmalig vor ALLEN Tests dieser Klasse ausgeführt (statisch)
     * Eignet sich für teure Initialisierungen, die nur einmal durchgeführt werden müssen
     */
    @BeforeAll
    static void setUpBeforeAll() {
        System.out.println(">>> BeforeAll: Einmalige Initialisierung VOR allen Tests");
        startTime = System.currentTimeMillis();
        testCount = 0;
    }
    
    /**
     * BeforeEach wird VOR JEDEM Test ausgeführt
     * Eignet sich für die Vorbereitung von Test-Objekten
     */
    @BeforeEach
    void setUpBeforeEach() {
        System.out.println("  > BeforeEach: Test-Vorbereitung");
        circle = new Circle(5.0);
    }
    
    /**
     * AfterEach wird NACH JEDEM Test ausgeführt
     * Eignet sich für die Bereinigung nach jedem Test
     */
    @AfterEach
    void tearDownAfterEach() {
        System.out.println("  < AfterEach: Test-Bereinigung");
        testCount++;
        circle = null;
    }
    
    /**
     * AfterAll wird einmalig nach ALLEN Tests dieser Klasse ausgeführt (statisch)
     * Eignet sich für die Bereinigung von globalen Ressourcen
     */
    @AfterAll
    static void tearDownAfterAll() {
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("<<< AfterAll: Gesamte Testklasse beendet");
        System.out.println("    Anzahl Tests: " + testCount);
        System.out.println("    Gesamtdauer: " + duration + "ms");
    }
    
    @Test
    @DisplayName("Test 1: calculateArea method")
    void testCalculateArea() {
        System.out.println("      Test 1 wird ausgeführt");
        double radius = 5.0;
        double expectedArea = 78.53981633974483;
        double actualArea = Circle.calculateArea(radius);
        assertEquals(expectedArea, actualArea);
    }
    
    @Test
    @DisplayName("Test 2: getRadius method")
    void testGetRadius() {
        System.out.println("      Test 2 wird ausgeführt");
        assertEquals(5.0, circle.getRadius());
    }
    
    @Test
    @DisplayName("Test 3: setRadius method")
    void testSetRadius() {
        System.out.println("      Test 3 wird ausgeführt");
        circle.setRadius(10.0);
        assertEquals(10.0, circle.getRadius());
    }
    
    @Test
    @DisplayName("Test 4: calculateArea with zero radius")
    void testCalculateAreaWithZeroRadius() {
        System.out.println("      Test 4 wird ausgeführt");
        double expectedArea = 0.0;
        double actualArea = Circle.calculateArea(0.0);
        assertEquals(expectedArea, actualArea);
    }
    
    @Test
    @DisplayName("Test 5: Constructor")
    void testConstructor() {
        System.out.println("      Test 5 wird ausgeführt");
        Circle newCircle = new Circle(7.5);
        assertEquals(7.5, newCircle.getRadius());
    }
    
}
