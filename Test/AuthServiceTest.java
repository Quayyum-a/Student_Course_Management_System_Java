import data.model.User;
import data.repository.UserRepository;
import exceptions.EmailAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import services.AuthService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        authService = new AuthService(userRepository);
    }

    @Test
    public void testRegisterUserSuccessfully() {
        // Arrange
        User expectedUser = new User("Test User", 1, "test@example.com", "password123", "STUDENT");
        when(userRepository.findByEmail("test@example.com")).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        // Act
        User user = authService.register("test@example.com", "password123", "STUDENT", "Test User");

        // Assert
        assertEquals("test@example.com", user.getEmail());
        assertEquals("Test User", user.getFullName());
        verify(userRepository).findByEmail("test@example.com");
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testRegisterUserWithExistingEmail() {
        // Arrange
        User existingUser = new User("Existing User", 1, "test@example.com", "password", "STUDENT");
        when(userRepository.findByEmail("test@example.com")).thenReturn(existingUser);

        // Act & Assert
        assertThrows(EmailAlreadyExistsException.class, () -> {
            authService.register("test@example.com", "password123", "STUDENT", "Test User");
        });
        verify(userRepository).findByEmail("test@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testLoginSuccessful() {
        // Arrange
        User expectedUser = new User("Test User", 1, "test@example.com", "password123", "STUDENT");
        when(userRepository.findByEmail("test@example.com")).thenReturn(expectedUser);

        // Act
        User user = authService.login("test@example.com", "password123");

        // Assert
        assertNotNull(user);
        assertEquals("test@example.com", user.getEmail());
        verify(userRepository).findByEmail("test@example.com");
    }

    @Test
    public void testLoginFailedWrongPassword() {
        // Arrange
        User expectedUser = new User("Test User", 1, "test@example.com", "password123", "STUDENT");
        when(userRepository.findByEmail("test@example.com")).thenReturn(expectedUser);

        // Act
        User user = authService.login("test@example.com", "wrongpassword");

        // Assert
        assertNull(user);
        verify(userRepository).findByEmail("test@example.com");
    }

    @Test
    public void testLoginFailedUserNotFound() {
        // Arrange
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(null);

        // Act
        User user = authService.login("nonexistent@example.com", "password123");

        // Assert
        assertNull(user);
        verify(userRepository).findByEmail("nonexistent@example.com");
    }
}
