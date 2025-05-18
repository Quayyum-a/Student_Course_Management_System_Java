package data.model;

import javax.persistence.*;

@Entity
@Table(name = "enrollments")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column
    private String grade;

    // Default constructor required by JPA
    public Enrollment() {
    }

    public Enrollment(int id, User student, Course course, String grade) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.grade = grade;
    }

    // Constructor that accepts IDs for backward compatibility
    public Enrollment(int id, int studentId, int courseId, String grade) {
        this.id = id;
        this.grade = grade;
        // Note: student and course will be set separately
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    // For backward compatibility
    public int getStudentId() {
        return student != null ? student.getId() : 0;
    }

    // For backward compatibility
    public void setStudentId(int studentId) {
        // This will be handled differently in the service layer
    }

    // For backward compatibility
    public int getCourseId() {
        return course != null ? course.getId() : 0;
    }

    // For backward compatibility
    public void setCourseId(int courseId) {
        // This will be handled differently in the service layer
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
