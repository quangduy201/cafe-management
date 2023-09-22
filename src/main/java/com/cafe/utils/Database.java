package com.cafe.utils;

import com.cafe.GUI.DatabaseGUI;
import com.cafe.main.CafeManagement;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private static String password = null;

    public static Connection getConnection() {
        do {
            try {
                Properties properties = Resource.loadProperties("settings/db.properties");
                String dbPort = properties.getProperty("db.port");
                String dbDatabase = properties.getProperty("db.database");
                String dbUsername = properties.getProperty("db.username");
                String dbPassword = properties.getProperty("db.password");
                if (dbDatabase == null)
                    throw new RuntimeException();
                if (password == null)
                    password = Password.decrypt(dbPassword, dbDatabase);
                String dbUrl = String.format("jdbc:mysql://localhost:%s/%s", dbPort, dbDatabase);
                return DriverManager.getConnection(dbUrl, dbUsername, password);
            } catch (RuntimeException | SQLException e) {
                password = null;
                initializeDatabase();
            }
        } while (password == null);
        return null;
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void initializeDatabase() {
        if (SwingUtilities.isEventDispatchThread()) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.\nVui lòng khởi động lại chương trình", "Lỗi", JOptionPane.ERROR_MESSAGE);
            CafeManagement.exit(1);
        }
        JOptionPane.showMessageDialog(null, "Không thể kết nối đến cơ sở dữ liệu.\nVui lòng cấu hình lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        Resource.copyResource(Settings.DATABASE_FILE, Settings.DATABASE_FILE);
        Properties properties = Resource.loadProperties(Settings.DATABASE_FILE);
        try {
            new FileOutputStream(Resource.getResourcePath(Settings.DATABASE_FILE, false)).close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        new DatabaseGUI(properties);
        do {
            properties = Resource.loadProperties(Settings.DATABASE_FILE);
        } while (properties.size() != 4 || !properties.containsKey("db.password") || properties.getProperty("db.password").isEmpty());
    }
}
