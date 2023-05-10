package com.cafe.DTO;

import com.cafe.utils.Day;

public class Statistic {
    private String statisticID;
    private Day date;
    private double amount;
    private double ingredientCost;

    public Statistic() {
    }

    public Statistic(String statisticID, Day date, double amount, double ingredientCost) {
        this.statisticID = statisticID;
        this.date = date;
        this.amount = amount;
        this.ingredientCost = ingredientCost;
    }

    public String getStatisticID() {
        return statisticID;
    }

    public void setStatisticID(String statisticID) {
        this.statisticID = statisticID;
    }

    public Day getDate() {
        return date;
    }

    public void setDate(Day date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getIngredientCost() {
        return ingredientCost;
    }

    public void setIngredientCost(double ingredientCost) {
        this.ingredientCost = ingredientCost;
    }

    @Override
    public String toString() {
        return statisticID + " | " +
            date + " | " +
            amount + " | " +
            ingredientCost;
    }
}
