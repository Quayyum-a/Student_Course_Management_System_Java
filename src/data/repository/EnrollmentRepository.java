package data.repository;

import data.model.Enrollment;
import utils.DatabaseUtil;

import java.sql.*;

public class EnrollmentRepository {
    public Enrollment save(Enrollment enrollment) {
        String query = "INSERT INTO enrollments (student_id, course_id, grade) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, enrollment.getStudentId());
            stmt.setInt(2, enrollment.getCourseId());
            stmt.setString(3, enrollment.getGrade());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                enrollment.setId(rs.getInt(1));
            }
            return enrollment;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save enrollment for student: " + enrollment.getStudentId(), e);
        }
    }

    public Enrollment findByStudentAndCourse(int studentId, int courseId) {
        String query = "SELECT * FROM enrollments WHERE student_id = ? AND course_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Enrollment(
                        rs.getInt("id"),
                        rs.getInt("student_id"),
                        rs.getInt("course_id"),
                        rs.getString("grade")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find enrollment for student: " + studentId, e);
        }
    }
}