import controller.MainController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Main entry point for the Student Course Management System.
 * This class initializes all necessary components and starts the application.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"controller", "services", "data.repository"})
@EntityScan("data.model")
@EnableJpaRepositories("data.repository")
public class Main {
    public static void main(String[] args) {
        try {
            // Start Spring application context
            ApplicationContext context = SpringApplication.run(Main.class, args);

            // Get the MainController bean from the Spring context
            MainController controller = context.getBean(MainController.class);
            System.out.println("Welcome to the Student Course Management System!");
            controller.start();
        } catch (Exception e) {
            System.err.println("An error occurred while starting the application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
