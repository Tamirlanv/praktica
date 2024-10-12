package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//---Проверка должности пользовател
public class UserManager {
    public static boolean registerClient(String name, String age, String password){
        String sql = "INSERT INTO users (name, age, password, role) VALUES (?, ?, ?, 'Client')";
        try(Connection conn = DatabaseConnector.connect();
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
    
//---нахождение админа
    public static boolean authenticateAdmin(String name, String password){
        String sql = "SELECT * FROM users WHERE name = ? AND password = ? AND role = 'Admin'";
        try (Connection conn = DatabaseConnector.connect();
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

//---функция вывода информации клентов для АДМИНА
    public static void showClients() {
        String sql = "SELECT * FROM users WHERE role = 'Client'";
        try (Connection conn = DatabaseConnector.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", Имя: " + rs.getString("name") + ", Возраст: " + rs.getString("age") + ", Баланс: " + rs.getInt("balance"));
                }
            
        } catch (SQLException e) {
            System.out.println("Ошибка при выводе данных клиентов: " + e.getMessage());
        }
    }

//---получение данных о счёте клиента
    public static int getClientBalance(String name) {
        String sql = "SELECT balance FROM users WHERE name = ? AND role = 'Client'";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
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


    public static void updateClientBalance(String name, int newBalance) {
        String sql = "UPDATE users SET balance = ? WHERE name = ? AND role = 'Client'";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newBalance);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при обновлении баланса: " + e.getMessage());
        }

    }
}

