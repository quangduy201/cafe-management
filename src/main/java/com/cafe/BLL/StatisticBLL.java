package com.cafe.BLL;

import com.cafe.DAL.StatisticDAL;
import com.cafe.DTO.*;
import com.cafe.utils.Day;

import java.util.List;
import java.util.Map;

public class StatisticBLL extends Manager<Statistic> {
    private StatisticDAL statisticDAL;

    public StatisticBLL() {
        statisticDAL = new StatisticDAL();
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
                List<Recipe> recipeList = recipeBLL.searchRecipes("PRODUCT_ID = '" + billDetails.getProductID() + "'");
                for (Recipe recipe : recipeList) {
                    Ingredient ingredient = ingredientBLL.searchIngredients("INGREDIENT_ID = '" + recipe.getIngredientID() + "'").get(0);
                    ingredientCost += ingredient.getUnitPrice() * recipe.getMass();
                }
            }
        }
        return new Statistic(getAutoID(), day, amount, ingredientCost);
    }

    public double getMonthProfit(Day firstDay, Day lastDay) {
        List<Statistic> statistics = findStatisticsBetween(firstDay, lastDay);
        double amount = 0.0;
        double cost = 0.0;
        for (Statistic statistic : statistics) {
            amount += statistic.getAmount();
            cost += statistic.getIngredientCost();
        }
        return amount - cost;
    }

    public double getMonthIngredient(Day firstDay, Day lastDay) {
        List<Statistic> statistics = findStatisticsBetween(firstDay, lastDay);
        double cost = 0.0;
        for (Statistic statistic : statistics) {
            cost += statistic.getIngredientCost();
        }
        return cost;
    }

    public double getMonthCustomers(Day firstDay, Day lastDay) {
        List<Bill> bills = new BillBLL().findBillsBetween(firstDay, lastDay);
        return bills.size();
    }

    public double[] getMonthStatistic(int month, int year) {
        Day firstDay = new Day(1, month, year);
        Day lastDay = new Day(Day.numOfDays(month, year), month, year);
        double profit = getMonthProfit(firstDay, lastDay);
        double importCost = getMonthIngredient(firstDay, lastDay);
        double customers = getMonthCustomers(firstDay, lastDay);
        return new double[]{profit, importCost, customers};
    }

    public double[] getYearStatistic(int year) {
        double[] yearStatistic = new double[3];
        for (int i = 1; i <= 12; i++) {
            double[] monthStatistic = getMonthStatistic(i, year);
            yearStatistic[0] += monthStatistic[0];
            yearStatistic[1] += monthStatistic[1];
            yearStatistic[2] += monthStatistic[2];
        }
        return new double[]{yearStatistic[0], yearStatistic[1], yearStatistic[2]};
    }

    public void addStatisticsSinceTheLastStatistic() {
        Statistic lastStatistic = statisticDAL.getTheLastStatistic();
        Day today = new Day();
        Day nextDay = lastStatistic.getDate().after(1, 0, 0);
        int numOfDays = Day.calculateDays(lastStatistic.getDate(), today);
        for (int i = 0; i < numOfDays; ++i) {
            Statistic statistic = doStatistic(nextDay);
            if (statistic.getAmount() == 0.0 && statistic.getIngredientCost() == 0.0) {
                nextDay = nextDay.after(1, 0, 0);
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
