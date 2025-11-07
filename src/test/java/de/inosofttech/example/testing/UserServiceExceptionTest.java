package de.inosofttech.example.testing;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceExceptionTest {
    
    @Mock
    private UserRepository userRepository;
    
    private UserService userService;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }
    
    // assertThrows - prüft ob eine Exception geworfen wird
    @Test
    public void testRegisterUserWithEmptyEmailThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser("", "SecurePassword123");
        }, "Should throw IllegalArgumentException for empty email");
    }
    
    // assertThrows mit Exception Message prüfen
    @Test
    public void testRegisterUserWithInvalidEmailThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser("invalid-email", "SecurePassword123");
        });
        assertEquals("Email ist ungültig", exception.getMessage());
    }
    
    // assertThrows mit zu kurzem Passwort
    @Test
    public void testRegisterUserWithShortPasswordThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser("user@example.com", "short");
        });
        assertTrue(exception.getMessage().contains("mindestens 8 Zeichen"));
    }
    
    // assertThrows mit null Email
    @Test
    public void testRegisterUserWithNullEmailThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(null, "SecurePassword123");
        });
    }
    
    // assertThrows beim Löschen mit ungültiger UserID
    @Test
    public void testDeleteUserWithInvalidIdThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.deleteUser(-1);
        });
        assertEquals("UserID muss größer als 0 sein", exception.getMessage());
    }
    
    // assertThrows beim Löschen mit UserID 0
    @Test
    public void testDeleteUserWithZeroIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.deleteUser(0);
        });
    }
    
    // assertDoesNotThrow - prüft ob KEINE Exception geworfen wird
    @Test
    public void testRegisterUserWithValidDataDoesNotThrowException() {
        assertDoesNotThrow(() -> {
            userService.registerUser("valid@example.com", "SecurePassword123");
        }, "Should not throw exception for valid data");
    }
    
    // assertDoesNotThrow beim Löschen mit gültiger ID
    @Test
    public void testDeleteUserWithValidIdDoesNotThrowException() {
        // Mock: User mit ID 1 existiert
        when(userRepository.exists(1)).thenReturn(true);
        
        assertDoesNotThrow(() -> {
            userService.deleteUser(1);
        });
    }
}
