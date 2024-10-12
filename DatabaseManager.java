package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/bankdb"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = "forEXapm//88"; 

    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Подключение к базе данных успешно!");
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
        }
        return connection;
    }

    // Создание записи
    public void createRecord(String name, int age) {
        String sql = "INSERT INTO bankdb (name, age) VALUES (?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.executeUpdate();
            System.out.println("Запись успешно добавлена.");
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении записи: " + e.getMessage());
        }
    }

    // Чтение записей
    public void readRecords() {
        String sql = "SELECT * FROM users";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Age: " + rs.getInt("age"));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при чтении записей: " + e.getMessage());
        }
    }

    // Обновление записи
    public void updateRecord(int id, String name, int age) {
        String sql = "UPDATE users SET name = ?, age = ? WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
            System.out.println("Запись успешно обновлена.");
        } catch (SQLException e) {
            System.out.println("Ошибка при обновлении записи: " + e.getMessage());
        }
    }

    // Удаление записи
    public void deleteRecord(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Запись успешно удалена.");
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении записи: " + e.getMessage());
        }
    }
}
