package de.inosofttech.example.testing;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceMockingTest {
    
    @Mock
    private UserRepository userRepository;
    
    private UserService userService;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }
    
    // Test 1: Verify - prüft ob eine Methode aufgerufen wurde
    @Test
    @DisplayName("Test saveUser calls repository's save method")
    public void testSaveUserCallsRepository() {
        User user = new User(1, "test@example.com", "SecurePass123");
        
        userService.saveUser(user);
        
        // Verify: Die save() Methode sollte genau 1x aufgerufen werden
        verify(userRepository, times(1)).save(user);
    }
    
    // Test 2: When/Then - Mock gibt einen Wert zurück
    @Test
    @DisplayName("Test getUser returns user from repository")
    public void testGetUserReturnsUserFromRepository() {
        User expectedUser = new User(1, "test@example.com", "SecurePass123");
        
        // When: userRepository.findById(1) aufgerufen wird
        when(userRepository.findById(1)).thenReturn(expectedUser);
        
        // Then: getUser sollte den User zurückgeben
        User actualUser = userService.getUser(1);
        
        assertEquals(expectedUser, actualUser);
        verify(userRepository, times(1)).findById(1);
    }
    
    // Test 3: When/ThenThrow - Mock wirft Exception
    @Test
    @DisplayName("Test getUser throws exception when repository fails")
    public void testGetUserThrowsExceptionWhenRepositoryFails() {
        when(userRepository.findById(1)).thenThrow(new RuntimeException("Database connection failed"));
        
        assertThrows(RuntimeException.class, () -> {
            userService.getUser(1);
        });
    }
    
    // Test 4: Verify mit ArgumentMatchers
    @Test
    @DisplayName("Test deleteUser verifies user exists")
    public void testDeleteUserVerifiesUserExists() {
        when(userRepository.exists(1)).thenReturn(true);
        
        userService.deleteUser(1);
        
        // Verify: exists() und delete() wurden aufgerufen
        verify(userRepository).exists(1);
        verify(userRepository).delete(1);
    }
    
    // Test 5: Verify - Methode wurde NICHT aufgerufen
    @Test
    @DisplayName("Test deleteUser not called when user does not exist")
    public void testDeleteUserNotCalledWhenUserDoesNotExist() {
        when(userRepository.exists(1)).thenReturn(false);
        
        assertThrows(IllegalArgumentException.class, () -> {
            userService.deleteUser(1);
        });
        
        // Verify: delete() sollte NICHT aufgerufen werden
        verify(userRepository, never()).delete(1);
    }
    
    // Test 6: Verify - Methode wurde mindestens 1x aufgerufen
    @Test
    @DisplayName("Test saveUser verifies at least once")
    public void testSaveUserVerifiesAtLeastOnce() {
        User user = new User(1, "test@example.com", "SecurePass123");
        
        userService.saveUser(user);
        userService.saveUser(user);
        
        // Verify: save() sollte mindestens 1x aufgerufen werden
        verify(userRepository, atLeastOnce()).save(user);
        
        // Oder: Genau 2x
        verify(userRepository, times(2)).save(user);
    }
    
    // Test 7: InOrder - Prüfe Aufruf-Reihenfolge
    @Test
    @DisplayName("Test method call order")
    public void testMethodCallOrder() {
        User user = new User(1, "test@example.com", "SecurePass123");
        when(userRepository.exists(1)).thenReturn(true);
        
        userService.saveUser(user);
        userService.deleteUser(1);
        
        // Prüfe Reihenfolge: save() vor delete()
        org.mockito.InOrder inOrder = inOrder(userRepository);
        inOrder.verify(userRepository).save(user);
        inOrder.verify(userRepository).exists(1);
        inOrder.verify(userRepository).delete(1);
    }
    
    // Test 8: Spy - Kombiniert echte und gemockte Behavior
    @Test
    @DisplayName("Test saveUser with null throws exception")
    public void testSaveUserWithNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userService.saveUser(null);
        });
        
        // Verify: Repository wurde NICHT aufgerufen weil Exception vorher geworfen wurde
        verify(userRepository, never()).save(any());
    }
}
