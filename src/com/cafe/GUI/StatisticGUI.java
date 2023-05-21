package com.cafe.GUI;

import com.cafe.BLL.BillBLL;
import com.cafe.BLL.CustomerBLL;
import com.cafe.BLL.ReceiptDetailsBLL;
import com.cafe.BLL.StatisticBLL;
import com.cafe.DTO.Bill;
import com.cafe.DTO.Customer;
import com.cafe.DTO.Statistic;
import com.cafe.custom.ButtonStatic;
import com.cafe.custom.DataTable;
import com.cafe.custom.ImageAvatar;
import com.cafe.custom.RoundPanel;
import com.cafe.utils.Day;
import com.cafe.utils.Settings;
import com.cafe.utils.VNString;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class StatisticGUI extends JPanel {
    private RoundPanel statistic;
    private RoundPanel[] roundPanel;
    private ButtonStatic cpButton;
    private ButtonStatic[] btFunction;
    private ImageAvatar[] imageAvatars;
    private JLabel[] jLabel;
    private StatisticBLL statisticBLL;
    private CustomerBLL customerBLL;
    private BillBLL billBLL;
    private ReceiptDetailsBLL receiptDetailsBLL;
    private DataTable[] dataTable;
    private JPanel[] jPanel;
    private JScrollPane[] jScrollPane;
    private JTextField[] jTextField;
    private JDateChooser[] jDateChooser;
    public StatisticGUI(int decentralization) {
        System.gc();
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(70, 67, 67));
        initComponents();
        btOverview();
    }

    public void initComponents() {
        statisticBLL = new StatisticBLL();
        customerBLL = new CustomerBLL();
        billBLL = new BillBLL();
        receiptDetailsBLL = new ReceiptDetailsBLL();
        btFunction = new ButtonStatic[5];
        roundPanel = new RoundPanel[25];
        jLabel = new JLabel[20];
        imageAvatars = new ImageAvatar[5];
        for (int i = 0; i < btFunction.length; i++) {
            btFunction[i] = new ButtonStatic();
        }
        for (int i = 0; i < 3; i++) {
            roundPanel[i] = new RoundPanel();
        }

        statistic = new RoundPanel();
        statistic.setBackground(new Color(70, 67, 67));
        statistic.setLayout(new BorderLayout(0, 0));
        this.add(statistic, BorderLayout.CENTER);

        roundPanel[0].setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        roundPanel[0].setBackground(new Color(70, 67, 67));
        roundPanel[0].setPreferredSize(new Dimension(1000, 30));
        roundPanel[0].setAutoscrolls(true);
        statistic.add(roundPanel[0], BorderLayout.NORTH);

        roundPanel[1].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
//        roundPanel[1].setBackground(new Color(240,240,240));
        roundPanel[1].setPreferredSize(new Dimension(1000, 640));
        roundPanel[1].setAutoscrolls(true);
        statistic.add(roundPanel[1], BorderLayout.CENTER);

        roundPanel[2].setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        roundPanel[2].setPreferredSize(new Dimension(500, 30));
        roundPanel[2].setBackground(new Color(70, 67, 67));
        roundPanel[2].setAutoscrolls(true);
        roundPanel[0].add(roundPanel[2]);

        BiConsumer<ButtonStatic, List<Object>> configButton = (button, properties) -> {
            button.setPreferredSize(new Dimension(100, 40));
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setFont(new Font("Public Sans", Font.BOLD, 12));
            button.setForeground(Color.BLACK);
            button.setRadius(15);
            button.setColorOver(new Color(0xB2B2B2));
            button.setText((String) properties.get(0));
            button.setColor((Color) properties.get(1));
            button.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    ((Runnable) properties.get(2)).run();
                }
            });
            roundPanel[2].add(button);
        };
        configButton.accept(btFunction[0], List.of("Tổng quát", new Color(0xFFFFFF), (Runnable) this::btOverview));
        configButton.accept(btFunction[1], List.of("Chi tiết", new Color(0x646464), (Runnable) this::btDetail));
        configButton.accept(btFunction[2], List.of("Theo ngày", new Color(0x646464), (Runnable) this::btEveryday));
        configButton.accept(btFunction[3], List.of("Biểu đồ", new Color(0x646464), (Runnable) this::btChart));
        cpButton = btFunction[0];
    }

    public void btOverview() {
        cpButton.setColor(new Color(0x646464));
        cpButton.setColorOver(new Color(0xB2B2B2));
        btFunction[0].setColor(new Color(240, 240, 240));
        btFunction[0].setColorOver(new Color(240, 240, 240));
        cpButton = btFunction[0];
        jPanel = new JPanel[1];
        roundPanel[1].removeAll();
        roundPanel[1].revalidate();
        roundPanel[1].repaint();
        for (int i = 3; i < roundPanel.length; i++) {
            roundPanel[i] = new RoundPanel();
        }
        for (int i = 0; i < jLabel.length; i++) {
            jLabel[i] = new JLabel();
        }
        for (int i = 0; i < imageAvatars.length; i++) {
            imageAvatars[i] = new ImageAvatar();
        }
        jPanel[0] = new JPanel();
        double amount = 0;
        double cost = 0;
        double profit;
        Day today = new Day();
        for (Statistic statistic1 : statisticBLL.findStatisticsBetween(new Day(1, 1, today.getYear()), today)) {
            amount += statistic1.getAmount();
            cost += statistic1.getIngredientCost();
        }
        profit = amount - cost;

        roundPanel[3].setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        roundPanel[3].setPreferredSize(new Dimension(1000, 30));
        roundPanel[3].setAutoscrolls(true);
        roundPanel[1].add(roundPanel[3]);

        roundPanel[4].setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        roundPanel[4].setPreferredSize(new Dimension(1000, 350));
        roundPanel[4].setAutoscrolls(true);
        roundPanel[1].add(roundPanel[4]);

        roundPanel[5].setLayout(new FlowLayout(FlowLayout.CENTER, 100, 20));
        roundPanel[5].setPreferredSize(new Dimension(1000, 250));
        roundPanel[5].setAutoscrolls(true);
        roundPanel[1].add(roundPanel[5]);

        BiConsumer<JLabel, List<Object>> configLabel = (label, properties) -> {
            label.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            label.setAutoscrolls(true);
            label.setPreferredSize(new Dimension((Integer) properties.get(0), (Integer) properties.get(1)));
            label.setIcon(new ImageIcon(new ImageIcon((String) properties.get(2)).getImage().getScaledInstance((Integer) properties.get(3), (Integer) properties.get(4), Image.SCALE_SMOOTH)));
        };
        configLabel.accept(jLabel[0], List.of(125, 350, "img/icons/Up_arrow.png", 125, 250));
        roundPanel[4].add(jLabel[0]);

        configLabel.accept(jLabel[1], List.of(125, 350, "img/icons/Down_arrow.png", 125, 250));
        roundPanel[4].add(jLabel[1]);

        roundPanel[6].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roundPanel[6].setPreferredSize(new Dimension(260, 250));
        roundPanel[6].setAutoscrolls(true);
        roundPanel[4].add(roundPanel[6]);

        configLabel.accept(jLabel[2], List.of(490, 350, "img/icons/chart.png", 470, 300));
        roundPanel[4].add(jLabel[2]);

        for (int i = 7; i <= 9; i++) {
            roundPanel[i].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            roundPanel[i].setBackground(new Color(112, 225, 73));
            roundPanel[i].setPreferredSize(new Dimension(200, 200));
            roundPanel[i].setAutoscrolls(true);
            roundPanel[5].add(roundPanel[i]);

            jLabel[i - 4].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            jLabel[i - 4].setForeground(Color.BLACK);
            jLabel[i - 4].setFont(new Font("Public Sans", Font.BOLD, 18));
            jLabel[i - 4].setHorizontalAlignment(SwingConstants.CENTER);
            jLabel[i - 4].setPreferredSize(new Dimension(200, 50));
            jLabel[i - 4].setAutoscrolls(true);
            roundPanel[i].add(jLabel[i - 4]);

            roundPanel[i + 3].setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
            roundPanel[i + 3].setBackground(new Color(152, 152, 152));
            roundPanel[i + 3].setPreferredSize(new Dimension(170, 140));
            roundPanel[i + 3].setAutoscrolls(true);
            roundPanel[i].add(roundPanel[i + 3]);
        }
        jLabel[3].setText("KHÁCH HÀNG");
        jLabel[4].setText("PHIẾU NHẬP HÀNG");
        jLabel[5].setText("HÓA ĐƠN");

        roundPanel[10].setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        roundPanel[10].setBackground(new Color(152, 152, 152));
        roundPanel[10].setPreferredSize(new Dimension(170, 140));
        roundPanel[10].setAutoscrolls(true);
        roundPanel[7].add(roundPanel[10]);

        roundPanel[11].setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        roundPanel[11].setBackground(new Color(152, 152, 152));
        roundPanel[11].setPreferredSize(new Dimension(170, 140));
        roundPanel[11].setAutoscrolls(true);
        roundPanel[8].add(roundPanel[11]);

        roundPanel[12].setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        roundPanel[12].setBackground(new Color(152, 152, 152));
        roundPanel[12].setPreferredSize(new Dimension(170, 140));
        roundPanel[12].setAutoscrolls(true);
        roundPanel[9].add(roundPanel[12]);

        imageAvatars[0].setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        imageAvatars[0].setPreferredSize(new Dimension(75, 75));
        imageAvatars[0].setBorderSize(2);
        imageAvatars[0].setForeground(new Color(255, 255, 255));
        imageAvatars[0].setIcon(new ImageIcon("img/icons/Customer.png"));
        imageAvatars[0].setAutoscrolls(true);
        roundPanel[10].add(imageAvatars[0]);

        jLabel[6].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        jLabel[6].setText(String.valueOf(customerBLL.getCustomerList().size()));
        jLabel[6].setForeground(new Color(255, 255, 255));
        jLabel[6].setFont(new Font("Public Sans", Font.BOLD, 25));
        jLabel[6].setHorizontalAlignment(SwingConstants.CENTER);
        jLabel[6].setPreferredSize(new Dimension(70, 140));
        jLabel[6].setAutoscrolls(true);
        roundPanel[10].add(jLabel[6]);

        imageAvatars[1].setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        imageAvatars[1].setPreferredSize(new Dimension(75, 75));
        imageAvatars[1].setBorderSize(2);
        imageAvatars[1].setIcon(new ImageIcon("img/icons/ReceivedNote.png"));
        imageAvatars[1].setForeground(new Color(255, 255, 255));
        imageAvatars[1].setAutoscrolls(true);
        roundPanel[11].add(imageAvatars[1]);

        jLabel[7].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        jLabel[7].setText(String.valueOf(receiptDetailsBLL.getReceiptDetailsList().size()));
        jLabel[7].setForeground(new Color(255, 255, 255));
        jLabel[7].setFont(new Font("Public Sans", Font.BOLD, 25));
        jLabel[7].setHorizontalAlignment(SwingConstants.CENTER);
        jLabel[7].setPreferredSize(new Dimension(70, 140));
        jLabel[7].setAutoscrolls(true);
        roundPanel[11].add(jLabel[7]);

        imageAvatars[2].setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        imageAvatars[2].setPreferredSize(new Dimension(75, 75));
        imageAvatars[2].setBorderSize(2);
        imageAvatars[2].setIcon(new ImageIcon("img/icons/Bill.png"));
        imageAvatars[2].setForeground(new Color(255, 255, 255));
        imageAvatars[2].setAutoscrolls(true);
        roundPanel[12].add(imageAvatars[2]);

        jLabel[8].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        jLabel[8].setText(String.valueOf(billBLL.getBillList().size()));
        jLabel[8].setForeground(new Color(255, 255, 255));
        jLabel[8].setFont(new Font("Public Sans", Font.BOLD, 25));
        jLabel[8].setHorizontalAlignment(SwingConstants.CENTER);
        jLabel[8].setPreferredSize(new Dimension(70, 140));
        jLabel[8].setAutoscrolls(true);
        roundPanel[12].add(jLabel[8]);

        roundPanel[13].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30));
//        roundPanel[13].setBackground(new Color(152, 152, 152));
        roundPanel[13].setPreferredSize(new Dimension(200, 50));
        roundPanel[13].setAutoscrolls(true);
        roundPanel[6].add(roundPanel[13]);

        roundPanel[14].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roundPanel[14].setBackground(new Color(152, 152, 152));
        roundPanel[14].setPreferredSize(new Dimension(200, 40));
        roundPanel[14].setAutoscrolls(true);
        roundPanel[13].add(roundPanel[14]);

        jLabel[9].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        jLabel[9].setText("Bảng tính Doanh số");
        jLabel[9].setForeground(Color.BLACK);
        jLabel[9].setFont(new Font("Public Sans", Font.PLAIN, 14));
        jLabel[9].setHorizontalAlignment(SwingConstants.CENTER);
        jLabel[9].setAutoscrolls(true);
        roundPanel[14].add(jLabel[9]);
        jPanel[0].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        jPanel[0].setBackground(new Color(255, 255, 255));
        jPanel[0].setPreferredSize(new Dimension(260, 200));
        jPanel[0].setBorder(BorderFactory.createLineBorder(new Color(152, 152, 152)));
        jPanel[0].setAutoscrolls(true);
        roundPanel[6].add(jPanel[0]);

        roundPanel[15].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        roundPanel[15].setBackground(new Color(255, 255, 255));
        roundPanel[15].setPreferredSize(new Dimension(255, 50));
        roundPanel[15].setAutoscrolls(true);
        jPanel[0].add(roundPanel[15]);

        roundPanel[16].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roundPanel[16].setBackground(new Color(255, 255, 255));
        roundPanel[16].setPreferredSize(new Dimension(255, 40));
        roundPanel[16].setAutoscrolls(true);
        jPanel[0].add(roundPanel[16]);

        roundPanel[17].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roundPanel[17].setBackground(new Color(255, 255, 255));
        roundPanel[17].setPreferredSize(new Dimension(255, 40));
        roundPanel[17].setAutoscrolls(true);
        jPanel[0].add(roundPanel[17]);

        roundPanel[18].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roundPanel[18].setBackground(new Color(255, 255, 255));
        roundPanel[18].setPreferredSize(new Dimension(255, 20));
        roundPanel[18].setAutoscrolls(true);
        jPanel[0].add(roundPanel[18]);

        roundPanel[19].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roundPanel[19].setBackground(new Color(255, 255, 255));
        roundPanel[19].setPreferredSize(new Dimension(255, 40));
        roundPanel[19].setAutoscrolls(true);
        jPanel[0].add(roundPanel[19]);

        jLabel[10].setText("Bảng doanh thu");
        jLabel[10].setForeground(Color.BLACK);
        jLabel[10].setFont(new Font("Public Sans", Font.BOLD, 25));
        jLabel[10].setHorizontalAlignment(SwingConstants.CENTER);
        jLabel[10].setAutoscrolls(true);
        roundPanel[15].add(jLabel[10]);

        jLabel[11].setText("Doanh thu:");
        jLabel[11].setForeground(Color.BLACK);
        jLabel[11].setPreferredSize(new Dimension(80, 50));
        jLabel[11].setFont(new Font("Public Sans", Font.PLAIN, 16));
        jLabel[11].setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel[11].setAutoscrolls(true);
        roundPanel[16].add(jLabel[11]);

        jLabel[12].setText(VNString.currency(amount));
        jLabel[12].setForeground(Color.BLACK);
        jLabel[12].setPreferredSize(new Dimension(150, 50));
        jLabel[12].setFont(new Font("Public Sans", Font.PLAIN, 16));
        jLabel[12].setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel[12].setAutoscrolls(true);
        roundPanel[16].add(jLabel[12]);

        jLabel[13].setText("Chi phí:");
        jLabel[13].setForeground(Color.BLACK);
        jLabel[13].setPreferredSize(new Dimension(80, 50));
        jLabel[13].setFont(new Font("Public Sans", Font.PLAIN, 16));
        jLabel[13].setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel[13].setAutoscrolls(true);
        roundPanel[17].add(jLabel[13]);

        jLabel[14].setText(VNString.currency(cost));
        jLabel[14].setForeground(Color.BLACK);
        jLabel[14].setPreferredSize(new Dimension(150, 50));
        jLabel[14].setFont(new Font("Public Sans", Font.PLAIN, 16));
        jLabel[14].setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel[14].setAutoscrolls(true);
        roundPanel[17].add(jLabel[14]);

        jLabel[15].setText("-------------------------------");
        jLabel[15].setPreferredSize(new Dimension(200, 20));
        jLabel[15].setFont(new Font("Public Sans", Font.PLAIN, 16));
        jLabel[15].setHorizontalAlignment(SwingConstants.CENTER);
        jLabel[15].setAutoscrolls(true);
        roundPanel[18].add(jLabel[15]);

        jLabel[16].setText("Lợi nhuận:");
        jLabel[16].setForeground(Color.BLACK);
        jLabel[16].setPreferredSize(new Dimension(80, 40));
        jLabel[16].setFont(new Font("Public Sans", Font.PLAIN, 16));
        jLabel[16].setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel[16].setAutoscrolls(true);
        roundPanel[19].add(jLabel[16]);

        jLabel[17].setText(VNString.currency(profit));
        jLabel[17].setForeground(Color.BLACK);
        jLabel[17].setPreferredSize(new Dimension(150, 40));
        jLabel[17].setFont(new Font("Public Sans", Font.PLAIN, 16));
        jLabel[17].setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel[17].setAutoscrolls(true);
        roundPanel[19].add(jLabel[17]);
    }

    public void btDetail() {
        cpButton.setColor(new Color(0x646464));
        cpButton.setColorOver(new Color(0xB2B2B2));
        btFunction[1].setColor(new Color(240, 240, 240));
        btFunction[1].setColorOver(new Color(240, 240, 240));
        cpButton = btFunction[1];
        roundPanel[1].removeAll();
        roundPanel[1].revalidate();
        roundPanel[1].repaint();
        dataTable = new DataTable[3];
        jScrollPane = new JScrollPane[3];
        jPanel = new JPanel[3];
        for (int i = 3; i < roundPanel.length; i++) {
            roundPanel[i] = new RoundPanel();
        }
        for (int i = 0; i < jLabel.length; i++) {
            jLabel[i] = new JLabel();
        }
        for (int i = 0; i < imageAvatars.length; i++) {
            imageAvatars[i] = new ImageAvatar();
        }
        for (int i = 0; i < dataTable.length; i++) {
            jScrollPane[i] = new JScrollPane();
            jPanel[i] = new JPanel();
        }

        roundPanel[3].setLayout(new FlowLayout(FlowLayout.LEFT, 25, 50));
        if (Settings.theme.isDark())
            roundPanel[3].setBackground(null);
        else
            roundPanel[3].setBackground(new Color(112, 225, 73));

        roundPanel[3].setPreferredSize(new Dimension(1000, 640));
        roundPanel[3].setAutoscrolls(true);
        roundPanel[1].add(roundPanel[3]);

        roundPanel[4].setLayout(new BorderLayout(0, 10));
        roundPanel[4].setBackground(new Color(0xF8F883));
        roundPanel[4].setPreferredSize(new Dimension(300, 540));
        roundPanel[4].setAutoscrolls(true);
        roundPanel[3].add(roundPanel[4]);

        roundPanel[5].setLayout(new BorderLayout(0, 10));
        roundPanel[5].setBackground(new Color(0xF8F883));
        roundPanel[5].setPreferredSize(new Dimension(300, 540));
        roundPanel[5].setAutoscrolls(true);
        roundPanel[3].add(roundPanel[5]);

        roundPanel[6].setLayout(new BorderLayout(0, 10));
        roundPanel[6].setBackground(new Color(0xF8F883));
        roundPanel[6].setPreferredSize(new Dimension(300, 540));
        roundPanel[6].setAutoscrolls(true);
        roundPanel[3].add(roundPanel[6]);

        roundPanel[7].setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        roundPanel[7].setBackground(new Color(0xF8F883));
        roundPanel[7].setPreferredSize(new Dimension(250, 50));
        roundPanel[7].setAutoscrolls(true);
        roundPanel[4].add(roundPanel[7], BorderLayout.NORTH);

        roundPanel[8].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roundPanel[8].setBackground(new Color(0xF8F883));
        roundPanel[8].setPreferredSize(new Dimension(280, 80));
        roundPanel[8].setAutoscrolls(true);
        roundPanel[4].add(roundPanel[8], BorderLayout.CENTER);

        jPanel[0].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        jPanel[0].setBackground(new Color(108, 106, 106));
        jPanel[0].setPreferredSize(new Dimension(270, 80));
        jPanel[0].setBorder(BorderFactory.createLineBorder(new Color(25, 25, 25)));
        jPanel[0].setAutoscrolls(true);
        roundPanel[8].add(jPanel[0]);

        roundPanel[9].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roundPanel[9].setBackground(new Color(0xF8F883));
        roundPanel[9].setPreferredSize(new Dimension(280, 380));
        roundPanel[9].setAutoscrolls(true);
        roundPanel[4].add(roundPanel[9], BorderLayout.SOUTH);

        jLabel[0].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        jLabel[0].setText("KHÁCH HÀNG");
        jLabel[0].setFont(new Font("Public Sans", Font.BOLD, 18));
        jLabel[0].setHorizontalAlignment(SwingConstants.CENTER);
        jLabel[0].setPreferredSize(new Dimension(250, 50));
        jLabel[0].setAutoscrolls(true);
        roundPanel[7].add(jLabel[0]);

        imageAvatars[0].setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        imageAvatars[0].setPreferredSize(new Dimension(60, 60));
        imageAvatars[0].setBorderSize(2);
        imageAvatars[0].setForeground(new Color(255, 255, 255));
        imageAvatars[0].setIcon(new ImageIcon("img/icons/Customer.png"));
        imageAvatars[0].setAutoscrolls(true);
        jPanel[0].add(imageAvatars[0]);

        jLabel[1].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        jLabel[1].setText(String.valueOf(customerBLL.getCustomerList().size()));
        jLabel[1].setForeground(new Color(255, 255, 255));
        jLabel[1].setFont(new Font("Public Sans", Font.BOLD, 25));
        jLabel[1].setHorizontalAlignment(SwingConstants.CENTER);
        jLabel[1].setPreferredSize(new Dimension(150, 80));
        jLabel[1].setAutoscrolls(true);
        jPanel[0].add(jLabel[1]);

        roundPanel[10].setLayout(new BorderLayout(0, 0));
        roundPanel[10].setBackground(new Color(0xF8F883));
        roundPanel[10].setPreferredSize(new Dimension(270, 370));
        roundPanel[10].setAutoscrolls(true);
        roundPanel[10].add(new JScrollPane(dataTable[0]), BorderLayout.CENTER);
        roundPanel[9].add(roundPanel[10]);

        jScrollPane[0] = new JScrollPane();
        List<String> columnNames = new ArrayList<>();
        columnNames.add("Tên khách hàng");
        columnNames.add("Số lần mua");
        dataTable[0] = new DataTable(null, columnNames.subList(0, columnNames.size()).toArray(), null);
        jScrollPane[0] = new JScrollPane(dataTable[0]);
        roundPanel[10].add(jScrollPane[0]);
        DefaultTableModel model = (DefaultTableModel) dataTable[0].getModel();
        Day today = new Day();
        List<Bill> bills = billBLL.findBillsBetween(new Day(1, 1, today.getYear() - 4), today);
        Map<String, Integer> data = new HashMap<>();
        for (Bill bill : bills) {
            Integer number = data.get(bill.getCustomerID());
            int count = number == null ? 0 : number;
            data.put(bill.getCustomerID(), count + 1);
        }
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            Customer customer = customerBLL.searchCustomers("CUSTOMER_ID = '" + entry.getKey() + "'").get(0);
            model.addRow(new Object[]{customer.getName(), entry.getValue()});
        }
//        for (Ingredient ingredient : ingredientBLL.getIngredientList()) {
//            model.addRow(new Object[]{ingredient.getIngredientID(), ingredient.getName(), ingredient.getUnit(), ingredient.getUnitPrice(), ingredient.getSupplierID()});
//        }

        roundPanel[11].setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        roundPanel[11].setBackground(new Color(0xF8F883));
        roundPanel[11].setPreferredSize(new Dimension(250, 50));
        roundPanel[11].setAutoscrolls(true);
        roundPanel[5].add(roundPanel[11], BorderLayout.NORTH);

        roundPanel[12].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roundPanel[12].setBackground(new Color(0xF8F883));
        roundPanel[12].setPreferredSize(new Dimension(280, 80));
        roundPanel[12].setAutoscrolls(true);
        roundPanel[5].add(roundPanel[12], BorderLayout.CENTER);

        jPanel[1].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        jPanel[1].setBackground(new Color(108, 106, 106));
        jPanel[1].setPreferredSize(new Dimension(270, 80));
        jPanel[1].setBorder(BorderFactory.createLineBorder(new Color(25, 25, 25)));
        jPanel[1].setAutoscrolls(true);
        roundPanel[12].add(jPanel[1]);

        roundPanel[13].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roundPanel[13].setBackground(new Color(0xF8F883));
        roundPanel[13].setPreferredSize(new Dimension(280, 380));
        roundPanel[13].setAutoscrolls(true);
        roundPanel[5].add(roundPanel[13], BorderLayout.SOUTH);

        jLabel[2].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        jLabel[2].setText("NGUYÊN LIỆU");
        jLabel[2].setFont(new Font("Public Sans", Font.BOLD, 18));
        jLabel[2].setHorizontalAlignment(SwingConstants.CENTER);
        jLabel[2].setPreferredSize(new Dimension(250, 50));
        jLabel[2].setAutoscrolls(true);
        roundPanel[11].add(jLabel[2]);

        imageAvatars[1].setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        imageAvatars[1].setPreferredSize(new Dimension(60, 60));
        imageAvatars[1].setBorderSize(2);
        imageAvatars[1].setForeground(new Color(255, 255, 255));
        imageAvatars[1].setIcon(new ImageIcon("img/icons/ReceivedNote.png"));
        imageAvatars[1].setAutoscrolls(true);
        jPanel[1].add(imageAvatars[1]);

        jLabel[3].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        jLabel[3].setText(String.valueOf(receiptDetailsBLL.getReceiptDetailsList().size()));
        jLabel[3].setForeground(new Color(255, 255, 255));
        jLabel[3].setFont(new Font("Public Sans", Font.BOLD, 25));
        jLabel[3].setHorizontalAlignment(SwingConstants.CENTER);
        jLabel[3].setPreferredSize(new Dimension(150, 80));
        jLabel[3].setAutoscrolls(true);
        jPanel[1].add(jLabel[3]);

        roundPanel[14].setLayout(new BorderLayout(0, 0));
        roundPanel[14].setBackground(new Color(0xF8F883));
        roundPanel[14].setPreferredSize(new Dimension(270, 370));
        roundPanel[14].setAutoscrolls(true);
        roundPanel[14].add(new JScrollPane(dataTable[1]), BorderLayout.CENTER);
        roundPanel[13].add(roundPanel[14]);

        jScrollPane[1] = new JScrollPane();
        List<String> columnNames1 = new ArrayList<>();
        columnNames1.add("Tên nguyên liệu");
        columnNames1.add("Số lượng");
        columnNames1.add("Đơn vị");
        dataTable[1] = new DataTable(null, columnNames1.subList(0, columnNames1.size()).toArray(), null);
        jScrollPane[1] = new JScrollPane(dataTable[1]);
        roundPanel[14].add(jScrollPane[1]);

        roundPanel[15].setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        roundPanel[15].setBackground(new Color(0xF8F883));
        roundPanel[15].setPreferredSize(new Dimension(250, 50));
        roundPanel[15].setAutoscrolls(true);
        roundPanel[6].add(roundPanel[15], BorderLayout.NORTH);

        roundPanel[16].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roundPanel[16].setBackground(new Color(0xF8F883));
        roundPanel[16].setPreferredSize(new Dimension(280, 80));
        roundPanel[16].setAutoscrolls(true);
        roundPanel[6].add(roundPanel[16], BorderLayout.CENTER);

        jPanel[2].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        jPanel[2].setBackground(new Color(108, 106, 106));
        jPanel[2].setPreferredSize(new Dimension(270, 80));
        jPanel[2].setBorder(BorderFactory.createLineBorder(new Color(25, 25, 25)));
        jPanel[2].setAutoscrolls(true);
        roundPanel[16].add(jPanel[2]);

        roundPanel[17].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roundPanel[17].setBackground(new Color(0xF8F883));
        roundPanel[17].setPreferredSize(new Dimension(280, 380));
        roundPanel[17].setAutoscrolls(true);
        roundPanel[6].add(roundPanel[17], BorderLayout.SOUTH);

        jLabel[4].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        jLabel[4].setText("SẢN PHẨM");
        jLabel[4].setFont(new Font("Public Sans", Font.BOLD, 18));
        jLabel[4].setHorizontalAlignment(SwingConstants.CENTER);
        jLabel[4].setPreferredSize(new Dimension(250, 50));
        jLabel[4].setAutoscrolls(true);
        roundPanel[15].add(jLabel[4]);

        imageAvatars[2].setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        imageAvatars[2].setPreferredSize(new Dimension(60, 60));
        imageAvatars[2].setBorderSize(2);
        imageAvatars[2].setForeground(new Color(255, 255, 255));
        imageAvatars[2].setIcon(new ImageIcon("img/icons/Bill.png"));
        imageAvatars[2].setAutoscrolls(true);
        jPanel[2].add(imageAvatars[2]);

        jLabel[5].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        jLabel[5].setText(String.valueOf(billBLL.getBillList().size()));
        jLabel[5].setForeground(new Color(255, 255, 255));
        jLabel[5].setFont(new Font("Public Sans", Font.BOLD, 25));
        jLabel[5].setHorizontalAlignment(SwingConstants.CENTER);
        jLabel[5].setPreferredSize(new Dimension(150, 80));
        jLabel[5].setAutoscrolls(true);
        jPanel[2].add(jLabel[5]);

        roundPanel[18].setLayout(new BorderLayout(0, 0));
        roundPanel[18].setBackground(new Color(0xF8F883));
        roundPanel[18].setPreferredSize(new Dimension(270, 370));
        roundPanel[18].setAutoscrolls(true);
        roundPanel[18].add(new JScrollPane(dataTable[2]), BorderLayout.CENTER);
        roundPanel[17].add(roundPanel[18]);

        jScrollPane[2] = new JScrollPane();
        List<String> columnNames2 = new ArrayList<>();
        columnNames2.add("Tên sản phẩm");
        columnNames2.add("Số lượng");
        columnNames2.add("Size");
        dataTable[2] = new DataTable(null, columnNames2.subList(0, columnNames2.size()).toArray(), null);
        jScrollPane[2] = new JScrollPane(dataTable[2]);
        roundPanel[18].add(jScrollPane[2]);
    }

    public void btEveryday() {
        cpButton.setColor(new Color(0x646464));
        cpButton.setColorOver(new Color(0xB2B2B2));
        btFunction[2].setColor(new Color(240, 240, 240));
        btFunction[2].setColorOver(new Color(240, 240, 240));
        cpButton = btFunction[2];
        jTextField = new JTextField[2];
        jDateChooser = new JDateChooser[2];
        roundPanel[1].removeAll();
        roundPanel[1].revalidate();
        roundPanel[1].repaint();
        dataTable = new DataTable[4];
        jScrollPane = new JScrollPane[4];
        for (int i = 3; i < roundPanel.length; i++) {
            roundPanel[i] = new RoundPanel();
        }
        for (int i = 0; i < jLabel.length; i++) {
            jLabel[i] = new JLabel();
        }
        for (int i = 0; i < dataTable.length; i++) {
            jScrollPane[i] = new JScrollPane();
        }

        roundPanel[3].setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
//        roundPanel[3].setBackground(new Color(112, 225, 73));
        roundPanel[3].setPreferredSize(new Dimension(900, 50));
        roundPanel[3].setAutoscrolls(true);
        roundPanel[1].add(roundPanel[3]);

        roundPanel[4].setLayout(new BorderLayout(5, 0));
        roundPanel[4].setPreferredSize(new Dimension(980, 580));
        roundPanel[4].setAutoscrolls(true);
        roundPanel[1].add(roundPanel[4]);

        jLabel[0].setFont(new Font("Times New Roman", Font.BOLD, 16));
        jLabel[0].setText("Từ ngày:");
        jLabel[0].setHorizontalAlignment(JLabel.LEFT);
        jLabel[0].setPreferredSize(new Dimension(70, 50));
        jLabel[0].setAutoscrolls(true);
        roundPanel[3].add(jLabel[0]);

        roundPanel[5].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        roundPanel[5].setPreferredSize(new Dimension(190, 50));
//        roundPane5[19].setBackground(new Color(250,250,250));
        roundPanel[5].setAutoscrolls(true);
        roundPanel[3].add(roundPanel[5]);

        jLabel[1].setFont(new Font("Times New Roman", Font.BOLD, 16));
        jLabel[1].setText("Đến ngày:");
        jLabel[1].setHorizontalAlignment(JLabel.LEFT);
        jLabel[1].setPreferredSize(new Dimension(70, 50));
        jLabel[1].setAutoscrolls(true);
        roundPanel[3].add(jLabel[1]);

        roundPanel[6].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
//        roundPanel[20].setBackground(new Color(250, 250, 250));
        roundPanel[6].setPreferredSize(new Dimension(190, 50));
        roundPanel[6].setAutoscrolls(true);
        roundPanel[3].add(roundPanel[6]);

        Day today = new Day();
        jDateChooser[0] = new JDateChooser(today.before(0, 5, 0).toDateSafe());
        jDateChooser[1] = new JDateChooser(today.toDateSafe());
        for (int i = 0; i < jTextField.length; i++) {
            jDateChooser[i].setDateFormatString("dd/MM/yyyy");
            jDateChooser[i].setPreferredSize(new Dimension(150, 30));
            jDateChooser[i].setMinSelectableDate(new Day(1, 1, 1000).toDateSafe());
            jDateChooser[i].addPropertyChangeListener("date", evt -> changeCalender());
            jTextField[i] = (JTextField) jDateChooser[i].getDateEditor().getUiComponent();
            jTextField[i].setFont(new Font("Tahoma", Font.BOLD, 14));
            jTextField[i].setHorizontalAlignment(JTextField.CENTER);
            int index = i;
            jTextField[i].addActionListener(e -> {
                try {
                    Day day = Day.parseDay(jTextField[index].getText());
                    jDateChooser[index].setDate(day.toDate());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ngày không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
        roundPanel[5].add(jDateChooser[0]);
        roundPanel[6].add(jDateChooser[1]);

        roundPanel[7].setLayout(new BorderLayout(0, 10));
        roundPanel[7].setPreferredSize(new Dimension(485, 590));
        roundPanel[7].setAutoscrolls(true);
        roundPanel[4].add(roundPanel[7], BorderLayout.WEST);

        roundPanel[8].setLayout(new BorderLayout(0, 10));
        roundPanel[8].setPreferredSize(new Dimension(485, 590));
        roundPanel[8].setAutoscrolls(true);
        roundPanel[4].add(roundPanel[8], BorderLayout.EAST);

        roundPanel[9].setLayout(new FlowLayout(FlowLayout.CENTER));
        roundPanel[9].setBackground(new Color(112, 225, 73));
        roundPanel[9].setPreferredSize(new Dimension(485, 285));
        roundPanel[9].setAutoscrolls(true);
        roundPanel[7].add(roundPanel[9], BorderLayout.NORTH);

        roundPanel[10].setLayout(new FlowLayout(FlowLayout.CENTER));
        roundPanel[10].setPreferredSize(new Dimension(485, 285));
        roundPanel[10].setBackground(new Color(112, 225, 73));
        roundPanel[10].setAutoscrolls(true);
        roundPanel[7].add(roundPanel[10], BorderLayout.SOUTH);

        roundPanel[11].setLayout(new FlowLayout(FlowLayout.CENTER));
        roundPanel[11].setPreferredSize(new Dimension(485, 285));
        roundPanel[11].setBackground(new Color(112, 225, 73));
        roundPanel[11].setAutoscrolls(true);
        roundPanel[8].add(roundPanel[11], BorderLayout.NORTH);

        roundPanel[12].setLayout(new FlowLayout(FlowLayout.CENTER));
        roundPanel[12].setPreferredSize(new Dimension(485, 285));
        roundPanel[12].setBackground(new Color(112, 225, 73));
        roundPanel[12].setAutoscrolls(true);
        roundPanel[8].add(roundPanel[12], BorderLayout.SOUTH);


        jLabel[2].setFont(new Font("Times New Roman", Font.BOLD, 20));
        jLabel[2].setText("Doanh thu");
        jLabel[2].setForeground(Color.BLACK);
        jLabel[2].setHorizontalAlignment(JLabel.CENTER);
        jLabel[2].setPreferredSize(new Dimension(400, 30));
        jLabel[2].setAutoscrolls(true);
        roundPanel[9].add(jLabel[2]);

        jLabel[3].setFont(new Font("Times New Roman", Font.BOLD, 20));
        jLabel[3].setText("Khách hàng");
        jLabel[3].setForeground(Color.BLACK);
        jLabel[3].setHorizontalAlignment(JLabel.CENTER);
        jLabel[3].setPreferredSize(new Dimension(400, 30));
        jLabel[3].setAutoscrolls(true);
        roundPanel[11].add(jLabel[3]);

        jLabel[4].setFont(new Font("Times New Roman", Font.BOLD, 20));
        jLabel[4].setText("Sản phẩm");
        jLabel[4].setForeground(Color.BLACK);
        jLabel[4].setHorizontalAlignment(JLabel.CENTER);
        jLabel[4].setPreferredSize(new Dimension(400, 30));
        jLabel[4].setAutoscrolls(true);
        roundPanel[10].add(jLabel[4]);

        jLabel[5].setFont(new Font("Times New Roman", Font.BOLD, 20));
        jLabel[5].setText("Nguyên liệu");
        jLabel[5].setForeground(Color.BLACK);
        jLabel[5].setHorizontalAlignment(JLabel.CENTER);
        jLabel[5].setPreferredSize(new Dimension(400, 30));
        jLabel[5].setAutoscrolls(true);
        roundPanel[12].add(jLabel[5]);

        roundPanel[13].setLayout(new BorderLayout(0, 0));
        roundPanel[13].setPreferredSize(new Dimension(465, 235));
        roundPanel[13].setAutoscrolls(true);
        roundPanel[13].add(new JScrollPane(dataTable[0]), BorderLayout.CENTER);
        roundPanel[9].add(roundPanel[13]);

        roundPanel[14].setLayout(new BorderLayout(0, 0));
        roundPanel[14].setPreferredSize(new Dimension(465, 235));
        roundPanel[14].add(new JScrollPane(dataTable[1]), BorderLayout.CENTER);
        roundPanel[14].setAutoscrolls(true);
        roundPanel[10].add(roundPanel[14]);

        roundPanel[15].setLayout(new BorderLayout(0, 0));
        roundPanel[15].setPreferredSize(new Dimension(465, 235));
        roundPanel[15].add(new JScrollPane(dataTable[2]), BorderLayout.CENTER);
        roundPanel[15].setAutoscrolls(true);
        roundPanel[11].add(roundPanel[15]);

        roundPanel[16].setLayout(new BorderLayout(0, 0));
        roundPanel[16].setPreferredSize(new Dimension(465, 235));
        roundPanel[16].add(new JScrollPane(dataTable[3]), BorderLayout.CENTER);
        roundPanel[16].setAutoscrolls(true);
        roundPanel[12].add(roundPanel[16]);

        jScrollPane[0] = new JScrollPane();
        List<String> columnName1 = new ArrayList<>();
        columnName1.add("Ngày tháng");
        columnName1.add("Doanh thu");
        columnName1.add("Chi tiêu");
        columnName1.add("Lợi nhuận ");
        dataTable[0] = new DataTable(null, columnName1.subList(0, columnName1.size()).toArray(), null);
        jScrollPane[0] = new JScrollPane(dataTable[0]);
        roundPanel[13].add(jScrollPane[0]);

        jScrollPane[1] = new JScrollPane();
        List<String> columnName2 = new ArrayList<>();
        columnName2.add("Tên khách hàng");
        columnName2.add("Số lần mua");
        dataTable[1] = new DataTable(null, columnName2.subList(0, columnName2.size()).toArray(), null);
        jScrollPane[1] = new JScrollPane(dataTable[1]);
        roundPanel[15].add(jScrollPane[1]);

        jScrollPane[2] = new JScrollPane();
        List<String> columnName3 = new ArrayList<>();
        columnName3.add("Tên sản phẩm");
        columnName3.add("Số lượng");
        columnName3.add("Size");
        dataTable[2] = new DataTable(null, columnName3.subList(0, columnName3.size()).toArray(), null);
        jScrollPane[2] = new JScrollPane(dataTable[2]);
        roundPanel[14].add(jScrollPane[2]);

        jScrollPane[3] = new JScrollPane();
        List<String> columnName4 = new ArrayList<>();
        columnName4.add("Tên nguyên liệu");
        columnName4.add("Số lượng");
        columnName4.add("Đơn vị");
        dataTable[3] = new DataTable(null, columnName4.subList(0, columnName4.size()).toArray(), null);
        jScrollPane[3] = new JScrollPane(dataTable[3]);
        roundPanel[16].add(jScrollPane[3]);
    }

    private void changeCalender() {
        Day start = new Day(jDateChooser[0].getDate());
        Day end = new Day(jDateChooser[1].getDate());

    }

    public void btChart() {
        cpButton.setColor(new Color(0x646464));
        cpButton.setColorOver(new Color(0xB2B2B2));
        btFunction[3].setColor(new Color(240, 240, 240));
        btFunction[3].setColorOver(new Color(240, 240, 240));
        cpButton = btFunction[3];
        roundPanel[1].removeAll();
        roundPanel[1].revalidate();
        roundPanel[1].repaint();
    }
}
