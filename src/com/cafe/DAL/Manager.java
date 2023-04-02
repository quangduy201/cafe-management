package com.cafe.DAL;

import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;

public class Manager extends MySQL {
    private final String tableName;
    private final List<String> columnsName;

    public Manager(String tableName, List<String> columnsName) throws SQLException {
        super();
        this.tableName = tableName;
        this.columnsName = columnsName;
    }

    public String getTableName() {
        return tableName;
    }

    public List<String> getColumnsName() {
        return columnsName;
    }

    public int create(Object... values) throws SQLException {
        if (values == null || values.length != columnsName.size()) {
            throw new IllegalArgumentException("Invalid number of arguments.");
        }

        String query = "INSERT INTO `" + tableName + "` VALUES(?" + ", ?".repeat(values.length - 1) + ");";
        return executeUpdate(query, values);
    }

    public List<List<String>> read(String... conditions) throws SQLException {
        String query = "SELECT * FROM `" + tableName + "`";
//        List<Object> values = new ArrayList<>();

        if (conditions != null && conditions.length > 0) {
            query += " WHERE " + String.join(" AND ", conditions) + " AND DELETED = 0";
//            values = Arrays.asList(conditions);
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
            // not updating the ID
            List<String> columns = columnsName.subList(conditionsLength, columnsName.size() - 1);
            setClause = String.join(" = ?, ", columns) + " = ?";
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
