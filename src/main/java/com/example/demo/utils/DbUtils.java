package com.example.demo.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtils {
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(PropertyReader.getProperty("dbdriver"));
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/hybris-hometask2",
                    PropertyReader.getProperty("username"),
                    PropertyReader.getProperty("dbpassword"));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
