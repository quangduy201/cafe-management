package com.cafe.main;

import com.cafe.BLL.AccountBLL;
import com.cafe.BLL.DiscountBLL;
import com.cafe.BLL.StatisticBLL;
import com.cafe.DTO.Statistic;
import com.cafe.GUI.HomeGUI;
import com.cafe.GUI.LoginGUI;
import com.cafe.utils.Day;
import com.cafe.utils.Settings;

public class CafeManagement {
    public static LoginGUI loginGUI;
    public static HomeGUI homeGUI;
    public static Thread threadTime;
    public static StatisticBLL statisticBLL;
    public static DiscountBLL discountBLL;

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        Settings.applyTheme("light");
        statisticBLL = new StatisticBLL();
        discountBLL = new DiscountBLL();
        statisticBLL.addStatisticsSinceTheLastStatistic();
        loginGUI = new LoginGUI();
        homeGUI = new HomeGUI(new AccountBLL().searchAccounts("ACCOUNT_ID = 'AC000'").get(0));
        threadTime = new Thread(CafeManagement::updateState);
        threadTime.start();
    }

    public static void updateState() {
        while (true) {
            Day nextDay = new Day().after(1, 0, 0);
            while (true) {
                CafeManagement.homeGUI.setTime();
                Day today = new Day();
                if (today.equals(nextDay)) {
                    break;
                }
            }
            Day yesterday = new Day().before(1, 0, 0);
            Statistic statistic = statisticBLL.doStatistic(yesterday);
            if (statistic.getAmount() == 0.0 && statistic.getIngredientCost() == 0.0) {
                continue;
            }
            statisticBLL.addStatistic(statistic);
            discountBLL.checkDateDiscount(new Day());
        }
    }
}
