package com.cafe.GUI;

import com.cafe.BLL.*;
import com.cafe.DTO.*;
import com.cafe.custom.Button;
import com.cafe.custom.*;
import com.cafe.utils.Day;
import com.cafe.utils.Resource;
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

    public StatisticGUI() {
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
        ingredient = new ArrayList<>();
        ingredientBLL = new IngredientBLL();
        receiptBLL = new ReceiptBLL();
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
            label.setIcon(new ImageIcon(Resource.loadImageIcon((String) properties.get(2)).getImage().getScaledInstance((Integer) properties.get(3), (Integer) properties.get(4), Image.SCALE_SMOOTH)));
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
        imageAvatars[0].setIcon(Resource.loadImageIcon("img/icons/Customer.png"));
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
        imageAvatars[1].setIcon(Resource.loadImageIcon("img/icons/ReceivedNote.png"));
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
        imageAvatars[2].setIcon(Resource.loadImageIcon("img/icons/Bill.png"));
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

    private IngredientBLL ingredientBLL;
    private List<Ingredient> ingredient;
    private ReceiptBLL receiptBLL;

    private ProductBLL productBLL = new ProductBLL();
    private RecipeBLL recipeBLL = new RecipeBLL();

    private BillDetailsBLL billDetailsBLL = new BillDetailsBLL();

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
        imageAvatars[0].setIcon(Resource.loadImageIcon("img/icons/Customer.png"));
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
        columnNames.add("Mã khách hàng");
        columnNames.add("Số lần mua");
        dataTable[0] = new DataTable(null, columnNames.subList(0, columnNames.size()).toArray(), null);
        jScrollPane[0] = new JScrollPane(dataTable[0]);
        roundPanel[10].add(jScrollPane[0]);
        DefaultTableModel model = (DefaultTableModel) dataTable[0].getModel();
        Day today = new Day();
        List<Bill> bills = billBLL.findBillsBetween(new Day(1, 1, today.getYear() - 4), today);
        Map<String, Integer> data = new HashMap<>();
        Map<String, Double> ingredientData = new HashMap<>();
        Map<String, Integer> productData = new HashMap<>();
        for (Bill bill : bills) {
            Integer number = data.get(bill.getCustomerID());
            int count = number == null ? 0 : number;
            data.put(bill.getCustomerID(), count + 1);
            List<BillDetails> billDetails = billDetailsBLL.findBillDetailsBy(Map.of("BILL_ID", bill.getBillID()));
            for (BillDetails billDetail : billDetails) {
//                Product product = productBLL.searchProducts("PRODUCT_ID = '" + billDetail.getProductID() + "'").get(0);
                Integer productNumber = productData.get(billDetail.getProductID());
                int productCount = productNumber == null ? 0 : productNumber;
                productData.put(billDetail.getProductID(), productCount + billDetail.getQuantity());

                List<Recipe> recipes = recipeBLL.findRecipesBy(Map.of("PRODUCT_ID", billDetail.getProductID()));
                for (Recipe recipe : recipes) {
                    Double realNumber = ingredientData.get(recipe.getIngredientID());
                    double quantity = realNumber == null ? 0.0 : realNumber;
                    ingredientData.put(recipe.getIngredientID(), quantity + (recipe.getMass() * billDetail.getQuantity()));
                }
            }
        }
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            Customer customer = customerBLL.searchCustomers("CUSTOMER_ID = '" + entry.getKey() + "'").get(0);
            model.addRow(new Object[]{customer.getCustomerID(), entry.getValue()});
        }


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
        imageAvatars[1].setIcon(Resource.loadImageIcon("img/icons/ReceivedNote.png"));
        imageAvatars[1].setAutoscrolls(true);
        jPanel[1].add(imageAvatars[1]);

        jLabel[3].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        jLabel[3].setText(String.valueOf(ingredientData.size()));
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
        columnNames1.add("Mã nguyên liệu");
        columnNames1.add("Số lượng");
        columnNames1.add("Đơn vị");
        dataTable[1] = new DataTable(null, columnNames1.subList(0, columnNames1.size()).toArray(), null);
        jScrollPane[1] = new JScrollPane(dataTable[1]);
        roundPanel[14].add(jScrollPane[1]);
        model = (DefaultTableModel) dataTable[1].getModel();
        for (Map.Entry<String, Double> entry : ingredientData.entrySet()) {
            Ingredient ingredient = ingredientBLL.searchIngredients("INGREDIENT_ID = '" + entry.getKey() + "'").get(0);
            model.addRow(new Object[]{ingredient.getIngredientID(), entry.getValue(), ingredient.getUnit()});
        }

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
        imageAvatars[2].setIcon(Resource.loadImageIcon("img/icons/Bill.png"));
        imageAvatars[2].setAutoscrolls(true);
        jPanel[2].add(imageAvatars[2]);

        jLabel[5].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        jLabel[5].setText(String.valueOf(productData.size()));
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
        columnNames2.add("Mã sản phẩm");
        columnNames2.add("Số lượng");
        columnNames2.add("Size");
        dataTable[2] = new DataTable(null, columnNames2.subList(0, columnNames2.size()).toArray(), null);
        jScrollPane[2] = new JScrollPane(dataTable[2]);
        roundPanel[18].add(jScrollPane[2]);
        model = (DefaultTableModel) dataTable[2].getModel();
        for (Map.Entry<String, Integer> entry : productData.entrySet()) {
            Product product = productBLL.searchProducts("PRODUCT_ID = '" + entry.getKey() + "'").get(0);
            model.addRow(new Object[]{product.getProductID(), entry.getValue(), product.getSized()});
        }

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
            jDateChooser[i].addPropertyChangeListener("date", evt -> loadDatatable());
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

        loadDatatable();
    }

    public void loadDatatable() {
        List<Bill> bills = billBLL.findBillsBetween(new Day(jDateChooser[0].getDate()), new Day(jDateChooser[1].getDate()));
        List<Statistic> statistics = statisticBLL.findStatisticsBetween(new Day(jDateChooser[0].getDate()), new Day(jDateChooser[1].getDate()));
        Map<String, Integer> data = new HashMap<>();
        Map<String, Double> ingredientData = new HashMap<>();
        Map<String, Integer> productData = new HashMap<>();
        for (Bill bill : bills) {
            Integer number = data.get(bill.getCustomerID());
            int count = number == null ? 0 : number;
            data.put(bill.getCustomerID(), count + 1);
            List<BillDetails> billDetails = billDetailsBLL.findBillDetailsBy(Map.of("BILL_ID", bill.getBillID()));
            for (BillDetails billDetail : billDetails) {
                Integer productNumber = productData.get(billDetail.getProductID());
                int productCount = productNumber == null ? 0 : productNumber;
                productData.put(billDetail.getProductID(), productCount + billDetail.getQuantity());
                List<Recipe> recipes = recipeBLL.findRecipesBy(Map.of("PRODUCT_ID", billDetail.getProductID()));
                for (Recipe recipe : recipes) {
                    Double realNumber = ingredientData.get(recipe.getIngredientID());
                    double quantity = realNumber == null ? 0.0 : realNumber;
                    ingredientData.put(recipe.getIngredientID(), quantity + (recipe.getMass() * billDetail.getQuantity()));
                }
            }
        }
        DefaultTableModel model = (DefaultTableModel) dataTable[0].getModel();
        model.setRowCount(0);
        for (Statistic statistic : statistics) {
            Double staticNumber = statistic.getAmount() - statistic.getIngredientCost();
            model.addRow(new Object[]{statistic.getDate(), statistic.getAmount(), statistic.getIngredientCost(), staticNumber});
        }

        model = (DefaultTableModel) dataTable[1].getModel();
        model.setRowCount(0);
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            Customer customer = customerBLL.searchCustomers("CUSTOMER_ID = '" + entry.getKey() + "'").get(0);
            model.addRow(new Object[]{customer.getName(), entry.getValue()});
        }

        model = (DefaultTableModel) dataTable[2].getModel();
        model.setRowCount(0);
        for (Map.Entry<String, Integer> entry : productData.entrySet()) {
            Product product = productBLL.searchProducts("PRODUCT_ID = '" + entry.getKey() + "'").get(0);
            System.out.println(product.toString());
            model.addRow(new Object[]{product.getName(), entry.getValue(), product.getSized()});
        }

        model = (DefaultTableModel) dataTable[3].getModel();
        model.setRowCount(0);
        for (Map.Entry<String, Double> entry : ingredientData.entrySet()) {
            Ingredient ingredient = ingredientBLL.searchIngredients("INGREDIENT_ID = '" + entry.getKey() + "'").get(0);
            model.addRow(new Object[]{ingredient.getName(), entry.getValue(), ingredient.getUnit()});
        }
    }

    private Button[] jButton = new Button[2];

    public void btChart() {
        cpButton.setColor(new Color(0x646464));
        cpButton.setColorOver(new Color(0xB2B2B2));
        btFunction[3].setColor(new Color(240, 240, 240));
        btFunction[3].setColorOver(new Color(240, 240, 240));
        dataTable = new DataTable[3];
        jScrollPane = new JScrollPane[3];
        cpButton = btFunction[3];
        jTextField = new JTextField[1];
        jTextField[0] = new JTextField();
        roundPanel[1].removeAll();
        roundPanel[1].revalidate();
        roundPanel[1].repaint();
        jButton[0] = new Button();
        jButton[1] = new Button();
        for (int i = 3; i < roundPanel.length; i++) {
            roundPanel[i] = new RoundPanel();
        }
        for (int i = 0; i < jLabel.length; i++) {
            jLabel[i] = new JLabel();
        }
        for (int i = 0; i < jScrollPane.length; i++) {
            jScrollPane[i] = new JScrollPane();
        }


        roundPanel[3].setLayout(new BorderLayout(10, 10));
        roundPanel[3].setBackground(new Color(70, 67, 67));
        roundPanel[3].setPreferredSize(new Dimension(1000, 640));
        roundPanel[3].setAutoscrolls(true);
        roundPanel[1].add(roundPanel[3]);

        roundPanel[4].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roundPanel[4].setPreferredSize(new Dimension(980, 440));
        roundPanel[4].setAutoscrolls(true);
        roundPanel[3].add(roundPanel[4], BorderLayout.NORTH);

        roundPanel[5].setLayout(new BorderLayout(0, 5));
        roundPanel[5].setPreferredSize(new Dimension(780, 170));
        roundPanel[5].add(new JScrollPane(dataTable[0]), BorderLayout.CENTER);
        roundPanel[5].setAutoscrolls(true);
        roundPanel[3].add(roundPanel[5], BorderLayout.CENTER);

//        roundPanel[6].setLayout(new BorderLayout(0, 5));
//        roundPanel[6].setPreferredSize(new Dimension(780, 170));
//        roundPanel[6].add(new JScrollPane(dataTable[1]), BorderLayout.CENTER);
//        roundPanel[6].setAutoscrolls(true);
//
//        roundPanel[7].setLayout(new BorderLayout(0, 5));
//        roundPanel[7].setPreferredSize(new Dimension(780, 170));
//        roundPanel[7].add(new JScrollPane(dataTable[2]), BorderLayout.CENTER);
//        roundPanel[7].setAutoscrolls(true);

        roundPanel[8].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        roundPanel[8].setPreferredSize(new Dimension(180, 170));
        roundPanel[8].setAutoscrolls(true);
        roundPanel[3].add(roundPanel[8], BorderLayout.EAST);
//
//        jLabel[2].setFont(new Font("Times New Roman", Font.BOLD, 20));
//        jLabel[2].setText("Doanh thu");
//        jLabel[2].setHorizontalAlignment(JLabel.CENTER);
//        jLabel[2].setPreferredSize(new Dimension(400, 30));
//        jLabel[2].setAutoscrolls(true);
//        roundPanel[9].add(jLabel[2]);


        panelShadow1 = new PanelShadow();
        chart = new CurveLineChart();
        roundPanel[4].add(panelShadow1);

        panelShadow1.setPreferredSize(new Dimension(1000, 440));
        panelShadow1.setBackground(new java.awt.Color(34, 59, 69));
        panelShadow1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelShadow1.setColorGradient(new java.awt.Color(17, 38, 47));
        panelShadow1.add(chart);

        chart.setPreferredSize(new Dimension(970, 420));
        chart.setForeground(new java.awt.Color(237, 237, 237));
        chart.setFillColor(true);

        chart.setTitle("Biểu đồ thống kê theo tháng trong năm 2023");
        chart.addLegend("Bán hàng ", Color.decode("#7b4397"), Color.decode("#dc2430"));
        chart.addLegend("Nguyên liệu", Color.decode("#e65c00"), Color.decode("#F9D423"));
        chart.addLegend("Khách hàng", Color.decode("#0099F7"), Color.decode("#F11712"));

        chart.clear();
        chart.addData(new ModelChart("January", statisticBLL.getMonthStatistic(1, 2023)));
        chart.addData(new ModelChart("February", statisticBLL.getMonthStatistic(2, 2023)));
        chart.addData(new ModelChart("March", statisticBLL.getMonthStatistic(3, 2023)));
        chart.addData(new ModelChart("April", statisticBLL.getMonthStatistic(4, 2023)));
        chart.addData(new ModelChart("May", statisticBLL.getMonthStatistic(5, 2023)));
        chart.addData(new ModelChart("June", statisticBLL.getMonthStatistic(6, 2023)));
        chart.addData(new ModelChart("July", statisticBLL.getMonthStatistic(7, 2023)));
        chart.addData(new ModelChart("August", statisticBLL.getMonthStatistic(8, 2023)));
        chart.addData(new ModelChart("September", statisticBLL.getMonthStatistic(9, 2023)));
        chart.addData(new ModelChart("October", statisticBLL.getMonthStatistic(10, 2023)));
        chart.addData(new ModelChart("November", statisticBLL.getMonthStatistic(11, 2023)));
        chart.addData(new ModelChart("December", statisticBLL.getMonthStatistic(12, 2023)));
        chart.start();


        jLabel[0].setFont(new Font("Times New Roman", Font.BOLD, 20));
        jLabel[0].setText("NĂM");
        jLabel[0].setPreferredSize(new Dimension(180, 30));
        jLabel[0].setHorizontalAlignment(JLabel.CENTER);
        jLabel[0].setAutoscrolls(true);
        roundPanel[8].add(jLabel[0]);

        jTextField[0].setFont(new Font("Times New Roman", Font.PLAIN, 16));
        jTextField[0].setPreferredSize(new Dimension(100, 35));
        roundPanel[8].add(jTextField[0]);


        jButton[0].setBorderPainted(false);
        jButton[0].setFocusPainted(false);
        jButton[0].setFont(new Font("Times New Roman", Font.PLAIN, 14));
        jButton[0].setColor(new Color(0x70E149));
        jButton[0].setColorOver(new Color(0x5EFF00));
        jButton[0].setColorClick(new Color(0x8AD242));
        jButton[0].setBorderColor(new Color(70, 67, 67));
        jButton[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                statistics();
                loadStatisticTable();
            }
        });
        jButton[0].setRadius(15);
        jButton[0].setText("Thống Kê");
        jButton[0].setForeground(Color.BLACK);
        jButton[0].setPreferredSize(new Dimension(100, 40));
        roundPanel[8].add(jButton[0]);

        jButton[1].setBorderPainted(false);
        jButton[1].setFocusPainted(false);
        jButton[1].setFont(new Font("Times New Roman", Font.PLAIN, 14));
        jButton[1].setColor(new Color(0x70E149));
        jButton[1].setColorOver(new Color(0x5EFF00));
        jButton[1].setColorClick(new Color(0x8AD242));
        jButton[1].setBorderColor(new Color(70, 67, 67));
        jButton[1].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                statisticYear();
                loadStatisticTable();
            }
        });
        jButton[1].setRadius(15);
        jButton[1].setText("Thống Kê Theo Năm");
        jButton[1].setForeground(Color.BLACK);
        jButton[1].setPreferredSize(new Dimension(170, 40));
        roundPanel[8].add(jButton[1]);

        List<String> columnName1 = new ArrayList<>();
        columnName1.add("Ngày tháng");
        columnName1.add("Doanh thu");
        columnName1.add("Chi tiêu");
        columnName1.add("Lợi nhuận");
        dataTable[0] = new DataTable(null, columnName1.subList(0, columnName1.size()).toArray(), null);
        jScrollPane[0] = new JScrollPane(dataTable[0]);
        roundPanel[5].add(jScrollPane[0], BorderLayout.CENTER);


        columnName1 = new ArrayList<>();
        columnName1.add("Mã nguyên liệu ");
        columnName1.add("Tên nguyên liệu");
        columnName1.add("Số lương");
        columnName1.add("Đơn vị");
        columnName1.add("Nhà cung cấp");
        dataTable[1] = new DataTable(null, columnName1.subList(0, columnName1.size()).toArray(), null);
        jScrollPane[1] = new JScrollPane(dataTable[1]);


        columnName1 = new ArrayList<>();
        columnName1.add("Mã khách hàng");
        columnName1.add("Tên khách hàng");
        columnName1.add("Số điện thoại");
        columnName1.add("Số lần mua");
        columnName1.add("Ngày đăng ký");
        dataTable[2] = new DataTable(null, columnName1.subList(0, columnName1.size()).toArray(), null);
        jScrollPane[2] = new JScrollPane(dataTable[2]);

        chart.getLegendItem(0).addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                roundPanel[5].removeAll();
                roundPanel[5].revalidate();
                roundPanel[5].repaint();
                roundPanel[5].add(jScrollPane[0], BorderLayout.CENTER);
            }
        });

        chart.getLegendItem(1).addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                roundPanel[5].removeAll();
                roundPanel[5].revalidate();
                roundPanel[5].repaint();
                roundPanel[5].add(jScrollPane[1], BorderLayout.CENTER);
            }
        });

        chart.getLegendItem(2).addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                roundPanel[5].removeAll();
                roundPanel[5].revalidate();
                roundPanel[5].repaint();
                roundPanel[5].add(jScrollPane[2], BorderLayout.CENTER);
            }
        });
        if (jTextField[0].getText().isEmpty()) this.today = new Day();
        else this.today = new Day(1, 1, Integer.parseInt(jTextField[0].getText()));
        loadStatisticTable();
    }

    private Day newDay;
    private Day today;

    private boolean pennant = true;

    public void statistics() {
        chart.clear();
        chart.setTitle("Biểu đồ thống kê theo tháng trong năm " + jTextField[0].getText());
        chart.addData(new ModelChart("January", statisticBLL.getMonthStatistic(1, Integer.parseInt(jTextField[0].getText()))));
        chart.addData(new ModelChart("February", statisticBLL.getMonthStatistic(2, Integer.parseInt(jTextField[0].getText()))));
        chart.addData(new ModelChart("March", statisticBLL.getMonthStatistic(3, Integer.parseInt(jTextField[0].getText()))));
        chart.addData(new ModelChart("April", statisticBLL.getMonthStatistic(4, Integer.parseInt(jTextField[0].getText()))));
        chart.addData(new ModelChart("May", statisticBLL.getMonthStatistic(5, Integer.parseInt(jTextField[0].getText()))));
        chart.addData(new ModelChart("June", statisticBLL.getMonthStatistic(6, Integer.parseInt(jTextField[0].getText()))));
        chart.addData(new ModelChart("July", statisticBLL.getMonthStatistic(7, Integer.parseInt(jTextField[0].getText()))));
        chart.addData(new ModelChart("August", statisticBLL.getMonthStatistic(8, Integer.parseInt(jTextField[0].getText()))));
        chart.addData(new ModelChart("September", statisticBLL.getMonthStatistic(9, Integer.parseInt(jTextField[0].getText()))));
        chart.addData(new ModelChart("October", statisticBLL.getMonthStatistic(10, Integer.parseInt(jTextField[0].getText()))));
        chart.addData(new ModelChart("November", statisticBLL.getMonthStatistic(11, Integer.parseInt(jTextField[0].getText()))));
        chart.addData(new ModelChart("December", statisticBLL.getMonthStatistic(12, Integer.parseInt(jTextField[0].getText()))));
        chart.start();
        if (jTextField[0].getText().isEmpty()) this.today = new Day();
        else this.today = new Day(1, 1, Integer.parseInt(jTextField[0].getText()));
        pennant = true;
    }

    public void statisticYear() {
        if (today.getYear() > 2030) newDay = new Day(1, 1, today.getYear() - 10);
        else newDay = new Day(1, 1, 2020);
        chart.clear();
        chart.setTitle("Biểu đồ thống kê theo tháng trong năm " + jTextField[0].getText());
        for (int i = 2020; i <= today.getYear(); i++) {
            chart.addData(new ModelChart(String.valueOf(i), statisticBLL.getYearStatistic(i)));
        }
        chart.start();
        pennant = false;
    }

    public void loadStatisticTable() {
        List<Bill> bills;
        List<Statistic> statistics;
        if (pennant) {
            bills = billBLL.findBillsBetween(new Day(1, 1, today.getYear()), new Day(31, 12, today.getYear()));
            statistics = statisticBLL.findStatisticsBetween(new Day(1, 1, today.getYear()), new Day(31, 12, today.getYear()));
        } else {
            bills = billBLL.findBillsBetween(new Day(1, 1, newDay.getYear()), new Day(today.getDate(), today.getMonth(), today.getYear()));
            statistics = statisticBLL.findStatisticsBetween(new Day(1, 1, newDay.getYear()), new Day(today.getDate(), today.getMonth(), today.getYear()));
        }
        Map<String, Integer> data = new HashMap<>();
        Map<String, Double> ingredientData = new HashMap<>();
        Map<String, Integer> productData = new HashMap<>();
        for (Bill bill : bills) {
            Integer number = data.get(bill.getCustomerID());
            int count = number == null ? 0 : number;
            data.put(bill.getCustomerID(), count + 1);
            List<BillDetails> billDetails = billDetailsBLL.findBillDetailsBy(Map.of("BILL_ID", bill.getBillID()));
            for (BillDetails billDetail : billDetails) {
                Integer productNumber = productData.get(billDetail.getProductID());
                int productCount = productNumber == null ? 0 : productNumber;
                productData.put(billDetail.getProductID(), productCount + billDetail.getQuantity());
                List<Recipe> recipes = recipeBLL.findRecipesBy(Map.of("PRODUCT_ID", billDetail.getProductID()));
                for (Recipe recipe : recipes) {
                    Double realNumber = ingredientData.get(recipe.getIngredientID());
                    double quantity = realNumber == null ? 0.0 : realNumber;
                    ingredientData.put(recipe.getIngredientID(), quantity + (recipe.getMass() * billDetail.getQuantity()));
                }
            }
        }
        DefaultTableModel model = (DefaultTableModel) dataTable[0].getModel();
        model.setRowCount(0);
        for (Statistic statistic : statistics) {
            Double staticNumber = statistic.getAmount() - statistic.getIngredientCost();
            model.addRow(new Object[]{statistic.getDate(), statistic.getAmount(), statistic.getIngredientCost(), staticNumber});
        }

        model = (DefaultTableModel) dataTable[1].getModel();
        model.setRowCount(0);
        for (Map.Entry<String, Double> entry : ingredientData.entrySet()) {
            Ingredient ingredient = ingredientBLL.searchIngredients("INGREDIENT_ID = '" + entry.getKey() + "'").get(0);
//            Supplier supplier = sup
            model.addRow(new Object[]{ingredient.getIngredientID(), ingredient.getName(), entry.getValue(), ingredient.getUnit(), supplierBLL.searchSuppliers("SUPPLIER_ID = '" + ingredient.getSupplierID() + "'").get(0).getName()});
        }

        model = (DefaultTableModel) dataTable[2].getModel();
        model.setRowCount(0);
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            Customer customer = customerBLL.searchCustomers("CUSTOMER_ID = '" + entry.getKey() + "'").get(0);
            model.addRow(new Object[]{customer.getCustomerID(), customer.getName(), customer.getPhone(), entry.getValue(), customer.getDateOfSup()});
        }

    }

    private SupplierBLL supplierBLL = new SupplierBLL();
    private CurveLineChart chart;
    private PanelShadow panelShadow1;

}
