package data.repository;

import data.model.User;
import utils.DatabaseUtil;

import java.sql.*;

public class UserRepository {
    public User findByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("fullName"),
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find user by email: " + email, e);
        }
    }

    public User save(User user) {
        String query = "INSERT INTO users (fullName, email, password, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save user: " + user.getEmail(), e);
        }
    }
}