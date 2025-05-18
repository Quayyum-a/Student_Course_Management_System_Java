package controller;

import data.model.Course;
import data.model.User;
import services.AuthService;
import services.CourseService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainController {
    private final AuthService authService;
    private final CourseService courseService;
    private final Scanner scanner;
    private User currentUser;

    public MainController(AuthService authService, CourseService courseService) {
        this.authService = authService;
        this.courseService = courseService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            try {
                if (currentUser == null) {
                    showLoginMenu();
                } else {
                    showUserMenu();
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the scanner buffer
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private void showLoginMenu() {
        System.out.println("\n1. Register\n2. Login\n3. Exit");
        System.out.print("Choose an option: ");
        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Clear the scanner buffer
            System.out.println("Invalid input. Please enter a number.");
            return;
        }

        switch (choice) {
            case 1:
                registerUser();
                break;
            case 2:
                loginUser();
                break;
            case 3:
                System.out.println("Exiting...");
                System.exit(0);
            default:
                System.out.println("Invalid option. Try again.");
        }
    }

    private void registerUser() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (STUDENT/INSTRUCTOR): ");
        String role = scanner.nextLine().toUpperCase();
        if (!role.equals("STUDENT") && !role.equals("INSTRUCTOR")) {
            System.out.println("Invalid role. Please enter STUDENT or INSTRUCTOR.");
            return;
        }
        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();

        try {
            User user = authService.register(email, password, role, fullName);
            System.out.println("Registration successful! User ID: " + user.getId());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void loginUser() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        currentUser = authService.login(email, password);
        if (currentUser != null) {
            System.out.println("Login successful! Welcome, " + currentUser.getFullName());
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    private void showUserMenu() {
        String menuOptions = "\n";
        if ("INSTRUCTOR".equals(currentUser.getRole())) {
            menuOptions += "1. Create Course\n2. Assign Grade\n";
        } else {
            menuOptions += "1. View Available Courses\n2. View My Grades\n";
        }
        menuOptions += "3. Logout";

        System.out.println(menuOptions);
        System.out.print("Choose an option: ");

        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Clear the scanner buffer
            System.out.println("Invalid input. Please enter a number.");
            return;
        }

        if ("INSTRUCTOR".equals(currentUser.getRole())) {
            switch (choice) {
                case 1:
                    createCourse();
                    break;
                case 2:
                    assignGrade();
                    break;
                case 3:
                    currentUser = null;
                    System.out.println("Logged out.");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        } else {
            switch (choice) {
                case 1:
                    System.out.println("View Available Courses feature is not implemented yet.");
                    break;
                case 2:
                    System.out.println("View My Grades feature is not implemented yet.");
                    break;
                case 3:
                    currentUser = null;
                    System.out.println("Logged out.");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void createCourse() {
        System.out.print("Enter course title: ");
        String title = scanner.nextLine();
        if (title.trim().isEmpty()) {
            System.out.println("Course title cannot be empty.");
            return;
        }

        try {
            Course course = courseService.createCourse(title, currentUser.getId());
            System.out.println("Course created! Course ID: " + course.getId());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void assignGrade() {
        try {
            System.out.print("Enter student ID: ");
            int studentId = scanner.nextInt();
            System.out.print("Enter course ID: ");
            int courseId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter grade: ");
            String grade = scanner.nextLine();

            if (grade.trim().isEmpty()) {
                System.out.println("Grade cannot be empty.");
                return;
            }

            courseService.assignGrade(studentId, courseId, grade);
            System.out.println("Grade assigned successfully!");
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Clear the scanner buffer
            System.out.println("Invalid input. Please enter a number for IDs.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
