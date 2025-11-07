package de.inosofttech.example.testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

/**
 * Load-Tests - Testen unter Last
 * 
 * Diese Tests simulieren hohe Last und Stress auf dem System
 */
@DisplayName("Load-Tests für UserService")
public class UserServiceLoadTest {
    
    /**
     * Test 1: Viele User-Registrierungen sequenziell
     * Simuliert hunderte von User-Registrierungen
     */
    @Test
    @DisplayName("Load Test: 1000 sequenzielle Registrierungen")
    public void testSequentialRegistrations() {
        UserService userService = new UserService(new MockUserRepository());
        long startTime = System.currentTimeMillis();
        int registrationCount = 1000;
        
        for (int i = 0; i < registrationCount; i++) {
            String email = "user" + i + "@example.com";
            String password = "SecurePass" + i + "123";
            
            assertDoesNotThrow(() -> {
                userService.registerUser(email, password);
            }, "Registration " + i + " should not throw exception");
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        System.out.println("\n=== Load Test: Sequential Registrations ===");
        System.out.println("Registrations: " + registrationCount);
        System.out.println("Duration: " + duration + " ms");
        System.out.println("Average per registration: " + (duration / (double) registrationCount) + " ms");
        
        assertTrue(duration < 5000, "1000 registrations should complete in less than 5 seconds");
    }
    
    /**
     * Test 2: Parallele Registrierungen mit Threads
     * Simuliert mehrere gleichzeitige User-Registrierungen
     */
    @Test
    @DisplayName("Load Test: 100 parallele Registrierungen mit 10 Threads")
    public void testParallelRegistrations() throws InterruptedException {
        UserService userService = new UserService(new MockUserRepository());
        int threadCount = 10;
        int registrationsPerThread = 100;
        
        long startTime = System.currentTimeMillis();
        
        Thread[] threads = new Thread[threadCount];
        for (int t = 0; t < threadCount; t++) {
            final int threadId = t;
            threads[t] = new Thread(() -> {
                for (int i = 0; i < registrationsPerThread; i++) {
                    String email = "user_t" + threadId + "_" + i + "@example.com";
                    String password = "SecurePass" + threadId + i + "123";
                    
                    assertDoesNotThrow(() -> {
                        userService.registerUser(email, password);
                    });
                }
            });
            threads[t].start();
        }
        
        // Warte auf alle Threads
        for (Thread thread : threads) {
            thread.join();
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        int totalRegistrations = threadCount * registrationsPerThread;
        
        System.out.println("\n=== Load Test: Parallel Registrations ===");
        System.out.println("Total Registrations: " + totalRegistrations);
        System.out.println("Threads: " + threadCount);
        System.out.println("Duration: " + duration + " ms");
        System.out.println("Average per registration: " + (duration / (double) totalRegistrations) + " ms");
        
        assertTrue(duration < 10000, "1000 parallel registrations should complete in less than 10 seconds");
    }
    
    /**
     * Test 3: Email Validierung unter Load
     * Prüft Performance bei vielen Validierungen
     */
    @Test
    @DisplayName("Load Test: 10000 Email Validierungen")
    public void testEmailValidationLoad() {
        UserService userService = new UserService(new MockUserRepository());
        long startTime = System.currentTimeMillis();
        int validationCount = 10000;
        
        for (int i = 0; i < validationCount; i++) {
            userService.isValidEmail("user" + i + "@example.com");
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        System.out.println("\n=== Load Test: Email Validations ===");
        System.out.println("Validations: " + validationCount);
        System.out.println("Duration: " + duration + " ms");
        System.out.println("Validations per second: " + (validationCount / (duration / 1000.0)));
        
        assertTrue(duration < 1000, "10000 validations should complete in less than 1 second");
    }
    
    /**
     * Test 4: Passwort Strength Check unter Load
     */
    @Test
    @DisplayName("Load Test: 5000 Passwort Strength Checks")
    public void testPasswordStrengthLoad() {
        UserService userService = new UserService(new MockUserRepository());
        long startTime = System.currentTimeMillis();
        int checkCount = 5000;
        
        for (int i = 0; i < checkCount; i++) {
            String password = "SecurePass" + i + "123ABC";
            userService.isStrongPassword(password);
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        System.out.println("\n=== Load Test: Password Strength Checks ===");
        System.out.println("Checks: " + checkCount);
        System.out.println("Duration: " + duration + " ms");
        System.out.println("Checks per second: " + (checkCount / (duration / 1000.0)));
        
        assertTrue(duration < 1000, "5000 checks should complete in less than 1 second");
    }
    
    /**
     * Test 5: Stress Test mit vielen kurzen Operationen
     */
    @Test
    @DisplayName("Stress Test: 100000 Mixed Operations")
    public void testStressTestMixedOperations() {
        UserService userService = new UserService(new MockUserRepository());
        long startTime = System.currentTimeMillis();
        int operationCount = 100000;
        
        for (int i = 0; i < operationCount; i++) {
            int operation = i % 3;
            
            switch (operation) {
                case 0:
                    userService.isValidEmail("test@example.com");
                    break;
                case 1:
                    userService.isStrongPassword("StrongPass123");
                    break;
                case 2:
                    userService.isValidEmail("invalid");
                    break;
            }
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        System.out.println("\n=== Stress Test: Mixed Operations ===");
        System.out.println("Operations: " + operationCount);
        System.out.println("Duration: " + duration + " ms");
        System.out.println("Operations per second: " + (operationCount / (duration / 1000.0)));
        System.out.println("Average per operation: " + (duration / (double) operationCount) + " ms");
        
        assertTrue(duration < 5000, "100000 operations should complete in less than 5 seconds");
    }
    
    /**
     * Mock Repository für Tests
     */
    private static class MockUserRepository implements UserRepository {
        @Override
        public void save(User user) {}
        
        @Override
        public User findById(int id) {
            return new User(id, "test@example.com", "SecurePass123");
        }
        
        @Override
        public boolean exists(int id) {
            return id > 0;
        }
        
        @Override
        public void delete(int id) {}
    }
}
