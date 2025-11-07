package de.inosofttech.example.testing.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import de.inosofttech.example.testing.Circle;

import java.util.concurrent.TimeUnit;

/**
 * JMH (Java Microbenchmark Harness) Performance-Test für Circle Klasse
 * 
 * JMH ist das Standard Benchmarking Framework für Java
 * Es kümmert sich um:
 * - JIT Compiler Warm-Up
 * - Garbage Collection
 * - CPU Cache Verhalten
 * - Dead Code Elimination
 * 
 * Ausführung:
 * 1. mvn clean package
 * 2. java -jar target/jmh-benchmarks.jar CirclePerformanceTest
 * 
 */
@BenchmarkMode(Mode.AverageTime)  // Misst die durchschnittliche Zeit
@OutputTimeUnit(TimeUnit.NANOSECONDS)  // Ausgabe in Nanosekunden
@State(Scope.Thread)  // Benchmark State pro Thread
@Fork(value = 1, jvmArgs = "-XX:+UseG1GC")  // 1 JVM Fork mit G1GC
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)  // 3 Warm-Up Iterationen
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)  // 5 Messungs-Iterationen
public class CirclePerformanceBenchmark {
    
    private Circle circle;
    private static final double[] RADII = {1.0, 5.0, 10.0, 100.0, 1000.0};
    private int radiusIndex = 0;
    
    /**
     * Setup wird vor jedem Trial ausgeführt
     */
    @Setup(Level.Trial)
    public void setup() {
        circle = new Circle(5.0);
    }
    
    /**
     * Benchmark 1: Simple calculateArea Funktion
     * JMH misst wie lange diese Methode durchschnittlich dauert
     */
    @Benchmark
    public double benchmarkCalculateArea() {
        return Circle.calculateArea(5.0);
    }
    
    /**
     * Benchmark 2: calculateArea mit verschiedenen Radien
     * Simuliert realistischere Nutzung mit variierenden Eingaben
     */
    @Benchmark
    public double benchmarkCalculateAreaVaryingRadius() {
        double result = Circle.calculateArea(RADII[radiusIndex]);
        radiusIndex = (radiusIndex + 1) % RADII.length;
        return result;
    }
    
    /**
     * Benchmark 3: getRadius Getter Performance
     * Zeigt wie schnell einfache Accessor-Methoden sind
     */
    @Benchmark
    public double benchmarkGetRadius() {
        return circle.getRadius();
    }
    
    /**
     * Benchmark 4: setRadius Setter Performance
     */
    @Benchmark
    public void benchmarkSetRadius() {
        circle.setRadius(7.5);
    }
    
    /**
     * Benchmark 5: Objekterstellung
     * Zeigt Overhead bei der Objekterstellung
     */
    @Benchmark
    public Circle benchmarkCreateCircle() {
        return new Circle(5.0);
    }
    
    /**
     * Benchmark 6: Komplexe Operation
     * Kombiniert mehrere Operationen
     */
    @Benchmark
    public double benchmarkCombinedOperations() {
        double radius = circle.getRadius();
        circle.setRadius(radius * 1.1);
        return Circle.calculateArea(radius);
    }
    
    /**
     * Main Methode - Kann Benchmarks direkt ausführen
     */
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(CirclePerformanceBenchmark.class.getSimpleName())
                .forks(1)
                .warmupIterations(3)
                .measurementIterations(5)
                .timeUnit(TimeUnit.NANOSECONDS)
                .mode(Mode.AverageTime)
                .build();
        
        new Runner(opt).run();
    }
}
