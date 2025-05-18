package services;

import data.model.Course;
import data.model.Enrollment;
import data.repository.CourseRepository;
import data.repository.EnrollmentRepository;

public class CourseService {
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public CourseService(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public Course createCourse(String title, int instructorId) {
        Course course = new Course(0, title, instructorId);
        return courseRepository.save(course);
    }

    public void assignGrade(int studentId, int courseId, String grade) {
        Enrollment enrollment = enrollmentRepository.findByStudentAndCourse(studentId, courseId);
        if (enrollment != null) {
            enrollment.setGrade(grade);
            enrollmentRepository.save(enrollment);
        } else {
            throw new RuntimeException("Enrollment not found for student: " + studentId + " and course: " + courseId);
        }
    }
}