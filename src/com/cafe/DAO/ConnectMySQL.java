package com.cafe.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectMySQL {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/cafe-management";
    private static final String USER = "root";
    private static final String PASS = "";
    private Connection conn;

    public ConnectMySQL() {}

    public void connect() throws SQLException {
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public List<List<String>> executeQuery(String query, List<Object> values) throws SQLException {
        String formattedQuery = formatQuery(query, values);
        List<List<String>> result = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(formattedQuery);
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

    public int executeUpdate(String query, List<Object> values) throws SQLException {
        String formattedQuery = formatQuery(query, values);
        int numOfRows;
        try (Statement stmt = conn.createStatement()) {
            numOfRows = stmt.executeUpdate(formattedQuery);
        }
        return numOfRows;
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
        // Test ConnectMySQL and its functions
        try {
            ConnectMySQL connector = new ConnectMySQL();
            connector.connect();

            // Select customer whose gender = 1 and name = 'Nguuyễn Văn A'
            String query = """
                SELECT * FROM `customer`
                WHERE gender = ? AND name = ?;
                """;
            List<Object> values = new ArrayList<>();
            values.add('M');
            values.add("Nguuyễn Văn A");

            List<List<String>> result = connector.executeQuery(query, values);
            for (List<String> row : result) {
                for (Object value : row) {
                    System.out.print(value + "\t");
                }
                System.out.println();
            }
            values.clear();

            // Delete customer whose CUSTOMER_ID = 'KH002' by setting its DELETED = 1
            query = """
                UPDATE `customer` SET DELETED = ?
                WHERE CUSTOMER_ID = ?;
                """;
            values.add(1);
            values.add("KH002");

            int numsOfRows = connector.executeUpdate(query, values);
            System.out.println(numsOfRows + " row(s) affected");

            connector.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
