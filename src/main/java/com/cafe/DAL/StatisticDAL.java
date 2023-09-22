package com.cafe.DAL;

import com.cafe.DTO.Statistic;
import com.cafe.utils.Day;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatisticDAL extends Manager {
    public StatisticDAL() {
        super("statistic",
            List.of("STATISTIC_ID",
                "DATE",
                "AMOUNT",
                "INGREDIENT_COST")
        );
    }

    public List<Statistic> convertToStatistics(List<List<String>> data) {
        return convert(data, row -> {
            try {
                return new Statistic(
                    row.get(0), // statisticID
                    Day.parseDay(row.get(1)), // date
                    Double.parseDouble(row.get(2)), // amount
                    Double.parseDouble(row.get(3)) // cost
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public int addStatistic(Statistic statistic) {
        try {
            return create(statistic.getStatisticID(),
                statistic.getDate(),
                statistic.getAmount(),
                statistic.getIngredientCost()
            );
        } catch (SQLException e) {
            System.out.println("Error occurred in StatisticDAL.addStatistic(): " + e.getMessage());
        }
        return 0;
    }

    public int updateStatistic(Statistic statistic) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(statistic.getStatisticID());
            updateValues.add(statistic.getDate());
            updateValues.add(statistic.getAmount());
            updateValues.add(statistic.getIngredientCost());
            return update(updateValues, "STATISTIC_ID = '" + statistic.getStatisticID() + "'");
        } catch (SQLException e) {
            System.out.println("Error occurred in StatisticDAL.updateStatistic(): " + e.getMessage());
        }
        return 0;
    }

    public int deleteStatistic(String... conditions) {
        try {
            return delete(conditions);
        } catch (SQLException e) {
            System.out.println("Error occurred in StatisticDAL.deleteStatistic(): " + e.getMessage());
        }
        return 0;
    }

    public List<Statistic> searchStatistics(String... conditions) {
        try {
            return convertToStatistics(read(conditions));
        } catch (SQLException e) {
            System.out.println("Error occurred in StatisticDAL.searchStatistics(): " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<Statistic> searchStatistics(Day start, Day end) {
        try {
            return convertToStatistics(executeQuery("""
                SELECT * FROM `statistic`
                WHERE DATE BETWEEN ? AND ?;
                """,
                start.toMySQLString(), end.toMySQLString()));
        } catch (SQLException e) {
            System.out.println("Error occurred in StatisticDAL.searchStatistics(): " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public Statistic getTheLastStatistic() {
        try {
            return convertToStatistics(executeQuery("""
                SELECT * FROM `statistic`
                ORDER BY DATE DESC
                LIMIT 1;
                """)).get(0);
        } catch (SQLException e) {
            System.out.println("Error occurred in StatisticDAL.getTheLastStatistic(): " + e.getMessage());
        }
        return new Statistic();
    }
}
