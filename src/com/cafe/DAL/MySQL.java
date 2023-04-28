package com.cafe.DAL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQL {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/cafe-management";
    private static final String USER = "root";
    private static final String PASS = "";
    private final Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
    private final Statement stmt = conn.createStatement();

    public MySQL() throws SQLException {
    }

    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public Statement getStatement() {
        return stmt;
    }

    public List<List<String>> executeQuery(String query, Object... values) throws SQLException {
        String formattedQuery = formatQuery(query, values);
        List<List<String>> result = new ArrayList<>();
        try {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(formattedQuery);
        return result;
    }

    public int executeUpdate(String query, Object... values) throws SQLException {
        String formattedQuery = formatQuery(query, values);
        System.out.println(formattedQuery);
        int numOfRows;
        try {
            numOfRows = stmt.executeUpdate(formattedQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return numOfRows;
    }

    public String formatQuery(String query, Object... values) {
        String stringValue;
        for (Object value : values) {
            if (value instanceof String || value instanceof Character) {
                stringValue = "'" + value + "'";
            } else if (value instanceof Boolean) {
                stringValue = (boolean) value ? "1" : "0";
            } else if (value instanceof Integer || value instanceof Double || value instanceof Float) {
                stringValue = value.toString();
            } else {
                stringValue = "'" + value.toString() + "'";
            }
            query = query.replaceFirst("\\?", stringValue);
        }
        return query;
    }

    public static void main(String[] args) {
        // Test MySQL and its functions
        try {
            MySQL connector = new MySQL();

            // Select customer whose gender = 1 and name = 'Nguuyễn Văn A'
            List<List<String>> result = connector.executeQuery("""
                    SELECT * FROM `customer`
                    WHERE CUSTOMER_ID = ?;
                    """,
                 "CUS001");
            for (List<String> row : result) {
                for (Object value : row) {
                    System.out.print(value + "\t");
                }
                System.out.println();
            }

            // Delete customer whose CUSTOMER_ID = 'KH002' by setting its DELETED = 1
//            int numOfRows = connector.executeUpdate("""
//                    UPDATE `customer` SET DELETED = ?
//                    WHERE CUSTOMER_ID = ?;
//                    """,
//                1, "KH002");
//            System.out.println(numOfRows + " row(s) affected");

            connector.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
