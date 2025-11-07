package de.inosofttech.example.testing;

public class UserService {
    
    private UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    // User registrieren - wirft Exception wenn Email ungültig
    public void registerUser(String email, String password) throws IllegalArgumentException {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email darf nicht leer sein");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email ist ungültig");
        }
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Passwort muss mindestens 8 Zeichen lang sein");
        }
    }
    
    // User speichern mit Repository
    public void saveUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User darf nicht null sein");
        }
        userRepository.save(user);
    }
    
    // User abrufen mit Repository
    public User getUser(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("UserID muss größer als 0 sein");
        }
        return userRepository.findById(userId);
    }
    
    // Benutzer löschen - wirft Exception wenn User nicht existiert
    public void deleteUser(int userId) throws IllegalArgumentException {
        if (userId <= 0) {
            throw new IllegalArgumentException("UserID muss größer als 0 sein");
        }
        if (!userRepository.exists(userId)) {
            throw new IllegalArgumentException("User mit ID " + userId + " existiert nicht");
        }
        userRepository.delete(userId);
    }
    
    // Email validieren
    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.contains("@") && email.contains(".");
    }
    
    // Passwort ist stark
    public boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        boolean hasUpperCase = password.matches(".*[A-Z].*");
        boolean hasLowerCase = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*[0-9].*");
        return hasUpperCase && hasLowerCase && hasDigit;
    }
}
