package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URl = "jdbc:mysql://localhost:3306/bankdb";
    private static final String USER = "root";
    private static final String PASSWORD = "forEXapm//88";

    public static Connection connect() throws SQLException{
        return DriverManager.getConnection(URl, USER, PASSWORD);
    }

}





