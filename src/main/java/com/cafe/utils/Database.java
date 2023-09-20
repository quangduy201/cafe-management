package com.cafe.utils;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    public static Connection getConnection() {
        Properties properties;
        try {
            properties = Resource.loadProperties("database/db.properties");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String dbUrl = properties.getProperty("db.url");
        String dbUsername = properties.getProperty("db.username");
        String dbPassword = properties.getProperty("db.password");
        try {
            return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.\nVui lòng cấu hình lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null)
                connection.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
