package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//---C - Create: Регистрация клиента (Create)
public class DatabaseManagerTest {
    // Регистрация клиента
    public static boolean registerClient(String name, String age, String password) {
        String sql = "INSERT INTO users (name, age, password, role) VALUES (?, ?, ?, 'Client')";
        try (Connection conn = DatabaseConnectorTest.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, age);
            pstmt.setString(3, password);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Ошибка при регистрации клиента: " + e.getMessage());
            return false;
        }
    }
    
//---R - Read: Аутентификация администратора (Read)
    public static boolean authenticateAdmin(String name, String password){
        String sql = "SELECT * FROM users WHERE name = ? AND password = ? AND role = 'Admin'";
        try (Connection conn = DatabaseConnectorTest.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Возвращает true, если администратор найден
        } catch (SQLException e) {
            System.out.println("Ошибка при аутентификации администратора: " + e.getMessage());
            return false;
        }
    }

//---R - Read: Просмотр клиентов администратором (Read)
    public static void showClients() {
        String sql = "SELECT * FROM users WHERE role = 'Client'";
        try (Connection conn = DatabaseConnectorTest.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Имя: " + rs.getString("name") + ", Возраст: " + rs.getString("age") + ", Баланс: " + rs.getInt("balance"));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при выводе данных клиентов: " + e.getMessage());
        }
    }

//---R - Read: Получение баланса клиента (Read)
    public static int getClientBalance(String name) {
        String sql = "SELECT balance FROM users WHERE name = ? AND role = 'Client'";
        try (Connection conn = DatabaseConnectorTest.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("balance");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении баланса: " + e.getMessage());
        }
        return -1;
    }

//U - Update: Обновление баланса клиента (Update)
    public static void updateClientBalance(String name, int newBalance) {
        String sql = "UPDATE users SET balance = ? WHERE name = ? AND role = 'Client'";
        try (Connection conn = DatabaseConnectorTest.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newBalance);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при обновлении баланса: " + e.getMessage());
        }

    }

//D - Delete: Удаление клиента по ID (Delete)
    public static void deleteClientById(int clientId) {
        String sql = "DELETE FROM users WHERE id = ? AND role = 'Client'";
        try (Connection conn = DatabaseConnectorTest.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, clientId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Клиент с ID = " + clientId + " успешно удален.");
            } else {
                System.out.println("Клиент с ID = " + clientId + " не найден.");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении клиента: " + e.getMessage());
        }
    }
}






