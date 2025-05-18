package data.repository;

import data.model.Course;
import utils.DatabaseUtil;

import java.sql.*;

public class CourseRepository {
    public Course save(Course course) {
        String query = "INSERT INTO courses (title, instructor_id) VALUES (?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, course.getTitle());
            stmt.setInt(2, course.getInstructorId());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                course.setId(rs.getInt(1));
            }
            return course;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save course: " + course.getTitle(), e);
        }
    }

    public Course findById(int id) {
        String query = "SELECT * FROM courses WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Course(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("instructor_id")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find course by id: " + id, e);
        }
    }
}