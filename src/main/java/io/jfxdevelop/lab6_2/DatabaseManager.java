package io.jfxdevelop.lab6_2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlserver://localhost:62699;databaseName=university;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";

    private Connection connection;

    public DatabaseManager() {
        try {
            System.out.println("Драйвер SQL Server загружен успешно.");

            connection = DriverManager.getConnection(URL);
            System.out.println("Подключение к базе данных успешно (Windows Authentication).");
        } catch (SQLException e) {
            System.err.println("Ошибка подключения к базе данных: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Student> searchStudentsByName(String namePattern) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT id, name, age, group_name FROM students WHERE name LIKE ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, "%" + namePattern + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String groupName = rs.getString("group_name");
                students.add(new Student(id, name, age, groupName));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
        }

        return students;
    }

    // Метод для поиска студентов, записанных на курс "Математика"
    public List<Student> getStudentsEnrolledInMath() {
        List<Student> students = new ArrayList<>();
        String query = """
            SELECT s.id, s.name, s.age, s.group_name
            FROM students s
            JOIN enrollments e ON s.id = e.student_id
            JOIN courses c ON e.course_id = c.id
            WHERE c.title = 'Математика';
            """;

        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String groupName = rs.getString("group_name");
                students.add(new Student(id, name, age, groupName));
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при выполнении запроса: " + e.getMessage());
        }

        return students;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Соединение с базой данных закрыто.");
            } catch (SQLException e) {
                System.err.println("Ошибка при закрытии соединения: " + e.getMessage());
            }
        }
    }
}