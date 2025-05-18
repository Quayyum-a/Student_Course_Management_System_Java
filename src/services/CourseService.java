package services;

import data.model.Course;
import data.model.Enrollment;
import data.model.User;
import data.repository.CourseRepository;
import data.repository.EnrollmentRepository;
import data.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;

    public CourseService(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
    }

    public Course createCourse(String title, int instructorId) {
        User instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found with ID: " + instructorId));

        Course course = new Course();
        course.setTitle(title);
        course.setInstructor(instructor);
        return courseRepository.save(course);
    }

    public void assignGrade(int studentId, int courseId, String grade) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));

        Enrollment enrollment = enrollmentRepository.findByStudentAndCourse(student, course);
        if (enrollment != null) {
            enrollment.setGrade(grade);
            enrollmentRepository.save(enrollment);
        } else {
            throw new RuntimeException("Enrollment not found for student: " + studentId + " and course: " + courseId);
        }
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(int courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));
    }

    public List<Course> getCoursesByInstructor(User instructor) {
        return courseRepository.findByInstructor(instructor);
    }

    public Enrollment enrollStudentInCourse(User student, Course course) {
        // Check if already enrolled
        Enrollment existingEnrollment = enrollmentRepository.findByStudentAndCourse(student, course);
        if (existingEnrollment != null) {
            return existingEnrollment;
        }

        // Create new enrollment
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setGrade("Not Graded");
        return enrollmentRepository.save(enrollment);
    }

    public List<Enrollment> getEnrollmentsByStudent(User student) {
        return enrollmentRepository.findByStudent(student);
    }

    public List<Enrollment> getEnrollmentsByCourse(Course course) {
        return enrollmentRepository.findByCourse(course);
    }
}
