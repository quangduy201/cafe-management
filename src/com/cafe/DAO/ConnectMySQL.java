package com.cafe.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectMySQL {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/qlcafe";
    private static final String USER = "root";
    private static final String PASS = "";
    private Connection connection;

    public ConnectMySQL() {}

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public List<List<String>> executeQuery(String query, List<Object> values) throws SQLException {
        String formattedQuery = formatQuery(query, values);
        List<List<String>> result = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(formattedQuery);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (resultSet.next()) {
                List<String> row = new ArrayList<>(columnCount);
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getString(i));
                }
                result.add(row);
            }
        }
        return result;
    }

    public String formatQuery(String query, List<Object> values) {
        String stringValue;
        for (Object value : values) {
            if (value instanceof String || value instanceof Character) {
                stringValue = "'" + value + "'";
            } else {
                stringValue = value.toString();
            }
            query = query.replaceFirst("\\?", stringValue);
        }
        return query;
    }

    public static void main(String[] args) {
        try {
            ConnectMySQL connector = new ConnectMySQL();
            connector.connect();

            String query = """
                SELECT * FROM `customer`
                WHERE gender = ? AND name = ?
                """;
            List<Object> values = new ArrayList<>();
            values.add('M');
            values.add("Nguuyễn Văn A");

            List<List<String>> result = connector.executeQuery(query, values);
            for (List<String> row : result) {
                for (Object value : row) {
                    System.out.print(value + " ");
                }
                System.out.println();
            }
            connector.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
