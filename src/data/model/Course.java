package data.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private User instructor;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Enrollment> enrollments = new ArrayList<>();

    // Default constructor required by JPA
    public Course() {
    }

    public Course(int id, String title, User instructor) {
        this.id = id;
        this.title = title;
        this.instructor = instructor;
    }

    // Constructor that accepts instructorId for backward compatibility
    public Course(int id, String title, int instructorId) {
        this.id = id;
        this.title = title;
        // Note: instructor will be set separately
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getInstructor() {
        return instructor;
    }

    public void setInstructor(User instructor) {
        this.instructor = instructor;
    }

    // For backward compatibility
    public int getInstructorId() {
        return instructor != null ? instructor.getId() : 0;
    }

    // For backward compatibility
    public void setInstructorId(int instructorId) {
        // This will be handled differently in the service layer
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }
}
