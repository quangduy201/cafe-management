package com.cafe.BLL;

import com.cafe.DAL.StatisticDAL;
import com.cafe.DTO.*;
import com.cafe.utils.Day;

import java.util.List;
import java.util.Map;

public class StatisticBLL extends Manager<Statistic> {
    private StatisticDAL statisticDAL;

    public StatisticBLL() {
        try {
            statisticDAL = new StatisticDAL();
        } catch (Exception ignored) {

        }
    }

    public StatisticDAL getStatisticDAL() {
        return statisticDAL;
    }

    public void setStatisticDAL(StatisticDAL statisticDAL) {
        this.statisticDAL = statisticDAL;
    }

    public Statistic doStatistic(Day day) {
        BillBLL billBLL = new BillBLL();
        BillDetailsBLL billDetailsBLL = new BillDetailsBLL();
        RecipeBLL recipeBLL = new RecipeBLL();
        IngredientBLL ingredientBLL = new IngredientBLL();
        List<Bill> billList = billBLL.searchBills("DOPURCHASE = '" + day.toMySQLString() + "'");
        double amount = 0.0;
        double ingredientCost = 0.0;
        for (Bill bill : billList) {
            amount += bill.getTotal();
            List<BillDetails> billDetailsList = billDetailsBLL.searchBillDetails("BILL_ID = '" + bill.getBillID() + "'");
            for (BillDetails billDetails : billDetailsList) {
                List<Recipe> recipeList = recipeBLL.searchRecipes("RECIPE_ID = '" + billDetails.getProductID());
                for (Recipe recipe : recipeList) {
                    Ingredient ingredient = ingredientBLL.searchIngredients("INGREDIENT_ID = '" + recipe.getIngredientID() + "'").get(0);
                    ingredientCost += ingredient.getUnitPrice() * recipe.getMass();
                }
            }
        }
        return new Statistic(getAutoID(), day, amount, ingredientCost);
    }

    public double getMonthProfit(int month, int year, double extraCost) {
        Day firstDay = new Day(1, month, year);
        Day lastDay = new Day(Day.numOfDays(month, year), month, year);
        double amount = 0.0;
        double cost = extraCost;
        while (!firstDay.equals(lastDay)) {
            Statistic statistic = doStatistic(firstDay);
            amount += statistic.getAmount();
            cost += statistic.getIngredientCost();
            firstDay = firstDay.after(1, 0, 0);
        }
        return amount - cost;
    }

    public void addStatisticsSinceTheLastStatistic() {
        Statistic lastStatistic = statisticDAL.getTheLastStatistic();
        Day today = new Day();
        Day nextDay = lastStatistic.getDate().after(1, 0, 0);
        int numOfDays = Day.calculateDays(lastStatistic.getDate(), today);
        for (int i = 0; i < numOfDays; ++i) {
            Statistic statistic = doStatistic(nextDay);
            if (statistic.getAmount() == 0.0 && statistic.getIngredientCost() == 0.0) {
                continue;
            }
            if (addStatistic(statistic)) {
                System.out.println("Thêm thống kê thành công. Ngày " + nextDay);
            } else {
                System.out.println("Thêm thống kê thất bại. Ngày " + nextDay);
            }
            nextDay = nextDay.after(1, 0, 0);
        }
    }

    public boolean addStatistic(Statistic statistic) {
        return statisticDAL.addStatistic(statistic) != 0;
    }

    public boolean updateStatistic(Statistic statistic) {
        return statisticDAL.updateStatistic(statistic) != 0;
    }

    public boolean deleteStatistic(Statistic statistic) {
        return statisticDAL.deleteStatistic("STATISTIC_ID = '" + statistic.getStatisticID() + "'") != 0;
    }

    public List<Statistic> searchStatistics(String... conditions) {
        return statisticDAL.searchStatistics(conditions);
    }

    public List<Statistic> findStatisticsBetween(Day start, Day end) {
        return statisticDAL.searchStatistics(start, end);
    }

    public List<Statistic> findStatisticsBy(Map<String, Object> conditions, List<Statistic> statistics) {
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            statistics = findObjectsBy(entry.getKey(), entry.getValue(), statistics);
        return statistics;
    }

    public boolean exists(Statistic statistic) {
        return !findStatisticsBy(Map.of(
            "DATE", statistic.getDate(),
            "AMOUNT", statistic.getAmount(),
            "COST", statistic.getIngredientCost()
        ), searchStatistics()).isEmpty();
    }

    public boolean exists(Map<String, Object> conditions) {
        return !findStatisticsBy(conditions, searchStatistics()).isEmpty();
    }

    public String getAutoID() {
        return getAutoID("STAT", 4, searchStatistics());
    }

    @Override
    public Object getValueByKey(Statistic statistic, String key) {
        return switch (key) {
            case "STATISTIC_ID" -> statistic.getStatisticID();
            case "DATE" -> statistic.getDate();
            case "AMOUNT" -> statistic.getAmount();
            case "COST" -> statistic.getIngredientCost();
            default -> null;
        };
    }
}
