package services;

import data.model.User;
import data.repository.UserRepository;
import exceptions.EmailAlreadyExistsException;

public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String email, String password, String role, String fullName) {
        // Check if email already exists
        if (userRepository.findByEmail(email) != null) {
            throw new EmailAlreadyExistsException("Email already exists: " + email);
        }
        // Create and save new user
        User user = new User(fullName, 0, email, password, role);
        return userRepository.save(user);
    }
}