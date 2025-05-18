package utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtil {
    private static Properties properties = new Properties();
    private static boolean loaded = false;

    private static void loadProperties() {
        if (!loaded) {
            try (InputStream input = DatabaseUtil.class.getClassLoader().getResourceAsStream("utils/config.properties")) {
                if (input == null) {
                    System.err.println("Unable to find config.properties");
                    return;
                }
                properties.load(input);
                loaded = true;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        loadProperties();
        String url = properties.getProperty("db.url", "jdbc:mysql://localhost:3306/student_course_db");
        String user = properties.getProperty("db.user", "root");
        String password = properties.getProperty("db.password", "");

        return DriverManager.getConnection(url, user, password);
    }
}
