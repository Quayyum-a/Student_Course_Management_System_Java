import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTest {
    private AuthService authService;
    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository = new UserRepository;
        authService = new AuthService(userRepository);
    }

    @Test
    public void testRegisterUserSuccessfully() {
        User user = authService.register("test@example.com", "password123", "STUDENT", "Test User");
        assertEquals("test@example.com", user.getEmail());
        assertEquals("Test User", user.getName());
    }
}
