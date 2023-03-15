package com.cafe.DAL;

import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Manager extends MySQL {
    private final String tableName;
    private final List<String> columnsName;

    public Manager(String tableName, List<String> columnsName) throws SQLException {
        super();
        this.tableName = tableName;
        this.columnsName = columnsName;
    }

    public int create(Object... values) throws SQLException {
        if (values == null || values.length != columnsName.size()) {
            throw new IllegalArgumentException("Invalid number of arguments");
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

    public int update(Map<String, Object> updateValues, String... conditions) throws SQLException {
        if (updateValues == null || updateValues.isEmpty()) {
            throw new IllegalArgumentException("Update values cannot be null or empty");
        }

        String setClause = updateValues.entrySet().stream()
            .filter(entry -> entry.getValue() != null)
            .map(entry -> entry.getKey() + " = ?")
            .collect(Collectors.joining(", "));

        String query = "UPDATE `" + tableName + "` SET " + setClause;
        List<Object> values = updateValues.values().stream()
            .filter(Objects::nonNull)
            .toList();

        if (conditions != null && conditions.length > 0) {
            query += " WHERE " + String.join(" AND ", conditions);
        }

        query += ";";
        return executeUpdate(query, values.toArray());
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

    public String getAutoID(String type, int digits) throws SQLException {
        int count = 0;
        List<List<String>> data = read();

        if (data.size() == 0) {
            return type + formatNumberToString(1, digits);
        }

        List<String> lastAccount = data.get(data.size() - 1);
        String size = type + formatNumberToString(data.size(), digits);
        System.out.println(size);
        if (lastAccount.get(0).equals(size)) {
            count += data.size();
        } else {
            while (data.get(count).get(0).equals(type + formatNumberToString(count + 1, digits)))
                count++;
        }
        return type + formatNumberToString(count + 1, digits);
    }

    public String formatNumberToString(int number, int digits) {
        String n = number + "";
        int count = digits - n.length();
        return "0".repeat(count) + n;
    }
}
