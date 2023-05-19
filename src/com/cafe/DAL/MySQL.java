package com.cafe.DAL;

import com.cafe.utils.Day;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MySQL {
    private static final Connection conn;
    private static Statement stmt;

    static {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("database/db.properties")) {
            properties.load(input);
        } catch (Exception ignored) {

        }
        String dbUrl = properties.getProperty("db.url");
        String dbUsername = properties.getProperty("db.username");
        String dbPassword = properties.getProperty("db.password");
        try {
            conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public MySQL() throws SQLException {
        stmt = conn.createStatement();
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
        return result;
    }

    public int executeUpdate(String query, Object... values) throws SQLException {
        String formattedQuery = formatQuery(query, values);
        int numOfRows;
        numOfRows = stmt.executeUpdate(formattedQuery);
        return numOfRows;
    }

    public String formatQuery(String query, Object... values) {
        String stringValue;
        for (Object value : values) {
            if (value instanceof Day) {
                stringValue = "'" + ((Day) value).toMySQLString() + "'";
            } else if (value instanceof String || value instanceof Character) {
                stringValue = "'" + value + "'";
            } else if (value instanceof Boolean) {
                stringValue = (boolean) value ? "1" : "0";
            } else if (value instanceof Number) {
                stringValue = value.toString();
            } else {
                stringValue = "'" + value.toString() + "'";
            }
            query = query.replaceFirst("\\?", stringValue);
        }
        return query;
    }
}
