package data.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Column(name = "full_name")
    private String fullName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Course> instructorCourses = new ArrayList<>();

    // Default constructor required by JPA
    public User() {
    }

    public User(String fullName, int id, String email, String password, String role) {
        this.fullName = fullName;
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Course> getInstructorCourses() {
        return instructorCourses;
    }

    public void setInstructorCourses(List<Course> instructorCourses) {
        this.instructorCourses = instructorCourses;
    }
}
