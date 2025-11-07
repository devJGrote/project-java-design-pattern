package de.inosofttech.example.testing;

public interface UserRepository {
    void save(User user);
    User findById(int id);
    boolean exists(int id);
    void delete(int id);
}
