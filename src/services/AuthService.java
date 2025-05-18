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
        if (userRepository.findByEmail(email) != null) {
            throw new EmailAlreadyExistsException("Email already exists: " + email);
        }
        User user = new User(fullName, 0, email, password, role);
        return userRepository.save(user);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}