package de.inosofttech.example.testing;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceAssertAllTest {
    
    @Mock
    private UserRepository userRepository;
    
    private UserService userService;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }
    
    // assertAll - mehrere Assertions werden zusammen geprüft
    // Alle Assertions werden ausgeführt, auch wenn eine fehlschlägt
    @Test
    public void testEmailValidationWithAssertAll() {
        assertAll("Email Validierung Tests",
            () -> assertTrue(userService.isValidEmail("test@example.com"), "Valid email should pass"),
            () -> assertTrue(userService.isValidEmail("user.name@domain.co.uk"), "Email with dot in domain should pass"),
            () -> assertFalse(userService.isValidEmail("invalid-email"), "Email without @ should fail"),
            () -> assertFalse(userService.isValidEmail(""), "Empty email should fail"),
            () -> assertFalse(userService.isValidEmail(null), "Null email should fail")
        );
    }
    
    // assertAll - Passwort Validierung
    @Test
    public void testPasswordValidationWithAssertAll() {
        assertAll("Passwort Validierung Tests",
            () -> assertTrue(userService.isStrongPassword("StrongPass123"), "Strong password should pass"),
            () -> assertTrue(userService.isStrongPassword("MyPassword2024"), "Password with mixed case and digits should pass"),
            () -> assertFalse(userService.isStrongPassword("weak"), "Short password should fail"),
            () -> assertFalse(userService.isStrongPassword("nouppercase123"), "Password without uppercase should fail"),
            () -> assertFalse(userService.isStrongPassword("NOLOWERCASE123"), "Password without lowercase should fail"),
            () -> assertFalse(userService.isStrongPassword("NoDigitsHere"), "Password without digits should fail")
        );
    }
    
    // assertAll - User Registrierung kombiniert prüfen
    @Test
    public void testUserRegistrationValidationWithAssertAll() {
        String validEmail = "newuser@example.com";
        String validPassword = "SecurePass123";
        
        assertAll("User Registrierung Validierung",
            () -> assertTrue(userService.isValidEmail(validEmail), "Email should be valid"),
            () -> assertTrue(userService.isStrongPassword(validPassword), "Password should be strong"),
            () -> assertDoesNotThrow(() -> userService.registerUser(validEmail, validPassword), 
                "Should not throw exception with valid credentials")
        );
    }
    
    // assertAll - Mehrere verschiedene Assertions
    @Test
    public void testComplexValidationWithAssertAll() {
        String testEmail = "test@example.com";
        String testPassword = "TestPass123";
        
        assertAll("Komplexe Validierung",
            // Email Tests
            () -> assertTrue(userService.isValidEmail(testEmail), "Email should be valid"),
            () -> assertFalse(userService.isValidEmail("invalid"), "Invalid email should fail"),
            
            // Password Tests
            () -> assertTrue(userService.isStrongPassword(testPassword), "Password should be strong"),
            () -> assertFalse(userService.isStrongPassword("weak"), "Weak password should fail"),
            
            // Registration Tests
            () -> assertDoesNotThrow(() -> userService.registerUser(testEmail, testPassword), 
                "Valid registration should work"),
            
            // Exception Tests
            () -> assertThrows(IllegalArgumentException.class, 
                () -> userService.registerUser("noemail", testPassword), 
                "Invalid email should throw exception")
        );
    }
}
