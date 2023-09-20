package com.cafe.DAL;

import com.cafe.utils.Database;
import com.cafe.utils.Day;
import com.cafe.utils.Resource;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MySQL {
    public MySQL() {
    }

    public List<List<String>> executeQuery(String query, Object... values) throws SQLException {
        Connection connection = Database.getConnection();
        if (connection == null)
            return new ArrayList<>();
        Statement statement = connection.createStatement();
        String formattedQuery = formatQuery(query, values);
        List<List<String>> result = new ArrayList<>();
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
        statement.close();
        Database.closeConnection(connection);
        return result;
    }

    public int executeUpdate(String query, Object... values) throws SQLException {
        Connection connection = Database.getConnection();
        if (connection == null)
            return 0;
        Statement statement = connection.createStatement();
        String formattedQuery = formatQuery(query, values);
        int numOfRows;
        numOfRows = statement.executeUpdate(formattedQuery);
        statement.close();
        Database.closeConnection(connection);
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
