package services;

import data.model.User;
import data.repository.UserRepository;
import exceptions.EmailAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String email, String password, String role, String fullName) {
        if (userRepository.findByEmail(email) != null) {
            throw new EmailAlreadyExistsException("Email already exists: " + email);
        }
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        return userRepository.save(user);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    /**
     * Find a user by email.
     * 
     * @param email the email to search for
     * @return the user with the given email, or null if not found
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
