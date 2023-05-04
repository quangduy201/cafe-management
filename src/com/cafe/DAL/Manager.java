package com.cafe.DAL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Manager extends MySQL {
    private final String tableName;
    private final List<String> columnNames;

    public Manager(String tableName, List<String> columnNames) throws SQLException {
        super();
        this.tableName = tableName;
        this.columnNames = columnNames;
    }

    public String getTableName() {
        return tableName;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public int create(Object... values) throws SQLException {
        if (values == null || values.length != columnNames.size()) {
            throw new IllegalArgumentException("Invalid number of arguments.");
        }

        String query = "INSERT INTO `" + tableName + "` VALUES(?" + ", ?".repeat(values.length - 1) + ");";
        return executeUpdate(query, values);
    }

    public List<List<String>> read(String... conditions) throws SQLException {
        String query = "SELECT * FROM `" + tableName + "`";
        if (conditions != null && conditions.length > 0) {
            query += " WHERE " + String.join(" AND ", conditions);
        }

        query += ";";
        return executeQuery(query);
    }

    public int update(List<Object> updateValues, String... conditions) throws SQLException {
        if (updateValues == null || updateValues.isEmpty()) {
            throw new IllegalArgumentException("Update values cannot be null or empty.");
        }

        int conditionsLength = 0;
        if (conditions != null && conditions.length > 0) {
            conditionsLength = conditions.length;
        }

        String setClause;
        if (updateValues.size() == 1) {
            // only update the DELETED
            setClause = "DELETED = ?";
        } else {
            setClause = String.join(" = ?, ", columnNames) + " = ?";
        }

        String query = "UPDATE `" + tableName + "` SET " + setClause;

        if (conditionsLength > 0) {
            query += " WHERE " + String.join(" AND ", conditions);
        }
        query += ";";
        return executeUpdate(query, updateValues.toArray());
    }

    public int delete(String... conditions) throws SQLException {
        String query = "DELETE FROM `" + tableName + "`";
        List<Object> values = new ArrayList<>();

        if (conditions != null && conditions.length > 0) {
            query += " WHERE " + String.join(" AND ", conditions);
            values = Arrays.asList(conditions);
        }

        query += ";";
        return executeUpdate(query, values);
    }

    public <T> List<T> convert(List<List<String>> data, Function<List<String>, T> converter) {
        List<T> list = new ArrayList<>();
        for (List<String> row : data) {
            T object = converter.apply(row);
            list.add(object);
        }
        return list;
    }
}
