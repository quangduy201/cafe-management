package com.cafe.GUI;

import com.cafe.custom.DataTable;
import com.cafe.custom.RoundPanel;

import javax.swing.*;
import java.awt.*;

public class DiscountGUI extends JPanel {
    private int decentralizationMode;

    private RoundPanel roundPanel[];
    private JLabel label[];
    private JTextField jTextFields[];
    private DataTable dataTable[];
    private JPanel discount;

    private JLabel labelimg;
    public DiscountGUI(int decentralizationMode) {
        this.decentralizationMode = decentralizationMode;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(70, 67, 67));
        initComponents();
    }
    public void initComponents() {
        roundPanel = new RoundPanel[15];
        label = new JLabel[10];
        jTextFields = new JTextField[10];
        dataTable = new DataTable[2];
        discount = new JPanel();
        labelimg = new JLabel();
//        dataTable[1] = new DataTable();
//        dataTable[2] = new DataTable();

        for (int i = 0; i < roundPanel.length; i++) {
            roundPanel[i] = new RoundPanel();
        }

        for (int i = 0; i < label.length; i++) {
            label[i] = new JLabel();
            jTextFields[i] = new JTextField();
         }

        discount.setLayout(new BorderLayout( 10, 10));
        discount.setBackground(new Color(70, 67, 67));
        this.add(discount, BorderLayout.CENTER);

        roundPanel[0].setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        roundPanel[0].setBackground(new Color(255, 255, 255));
        roundPanel[0].setPreferredSize(new Dimension(380, 670));
        roundPanel[0].setAutoscrolls(true);
        discount.add(roundPanel[0], BorderLayout.WEST);

        roundPanel[1].setLayout(new BorderLayout(0,10));
        roundPanel[1].setBackground(new Color(70, 67, 67));
        roundPanel[1].setPreferredSize(new Dimension(600, 670));
        roundPanel[1].setAutoscrolls(true);
        discount.add(roundPanel[1], BorderLayout.CENTER);

        roundPanel[2].setBackground(new Color(255, 255, 255));
        roundPanel[2].setPreferredSize(new Dimension(600, 310));
        //roundPanel[2].add(new JScrollPane(dataTable), BorderLayout.CENTER);
        roundPanel[2].setAutoscrolls(true);
        roundPanel[1].add(roundPanel[2],BorderLayout.NORTH);

        roundPanel[3].setBackground(new Color(255, 255, 255));
        roundPanel[3].setPreferredSize(new Dimension(600, 350));
        //roundPanel[2].add(new JScrollPane(dataTable), BorderLayout.CENTER);
        roundPanel[3].setAutoscrolls(true);
        roundPanel[1].add(roundPanel[3], BorderLayout.CENTER);

        roundPanel[4].setLayout(new FlowLayout(FlowLayout.CENTER,0,20));
        roundPanel[4].setBackground(new Color(255, 255, 255));
        roundPanel[4].setPreferredSize(new Dimension(380, 200));
        //roundPanel[2].add(new JScrollPane(dataTable), BorderLayout.CENTER);
        roundPanel[4].setAutoscrolls(true);
        roundPanel[0].add(roundPanel[4]);

        roundPanel[5].setLayout(new FlowLayout(FlowLayout.CENTER,0,15));
        roundPanel[5].setBackground(new Color(255, 255, 255));
        roundPanel[5].setPreferredSize(new Dimension(380, 370));
        //roundPanel[2].add(new JScrollPane(dataTable), BorderLayout.CENTER);
        roundPanel[5].setAutoscrolls(true);
        roundPanel[0].add(roundPanel[5]);

        roundPanel[6].setBackground(new Color(182, 24, 24));
        roundPanel[6].setPreferredSize(new Dimension(380, 100));
        //roundPanel[2].add(new JScrollPane(dataTable), BorderLayout.CENTER);
        roundPanel[6].setAutoscrolls(true);
        roundPanel[0].add(roundPanel[6]);

        roundPanel[7].setBackground(new Color(182, 24, 24));
        roundPanel[7].setPreferredSize(new Dimension(600, 40));
        //roundPanel[2].add(new JScrollPane(dataTable), BorderLayout.CENTER);
        roundPanel[7].setAutoscrolls(true);
        roundPanel[2].add(roundPanel[7]);

        roundPanel[8].setBackground(new Color(182, 24, 24));
        roundPanel[8].setPreferredSize(new Dimension(600, 260));
        //roundPanel[2].add(new JScrollPane(dataTable), BorderLayout.CENTER);
        roundPanel[8].setAutoscrolls(true);
        roundPanel[2].add(roundPanel[8]);

        roundPanel[9].setBackground(new Color(182, 24, 24));
        roundPanel[9].setPreferredSize(new Dimension(600, 40));
        //roundPanel[2].add(new JScrollPane(dataTable), BorderLayout.CENTER);
        roundPanel[9].setAutoscrolls(true);
        roundPanel[3].add(roundPanel[9]);

        roundPanel[10].setBackground(new Color(182, 24, 24));
        roundPanel[10].setPreferredSize(new Dimension(600, 300));
        //roundPanel[2].add(new JScrollPane(dataTable), BorderLayout.CENTER);
        roundPanel[10].setAutoscrolls(true);
        roundPanel[3].add(roundPanel[10]);

        labelimg.setIcon(new ImageIcon(new ImageIcon("img/black-friday.png").getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
        labelimg.setFocusable(false);
        labelimg.setPreferredSize(new Dimension(150, 150));
        roundPanel[4].add(labelimg);

//        for (int i = 0 ; i < 6; i++) {
//            label[i].setFont(new Font("Times New Roman", Font.BOLD, 15));
//            label[i].setHorizontalAlignment(JLabel.LEFT);
//            label[i].setPreferredSize(new Dimension(130, 40));
//            label[i].setAutoscrolls(true);
//            roundPanel[5].add(label[i]);
//
//            jTextFields[i].setFont(new Font("Times New Roman", Font.BOLD, 15));
//            jTextFields[i].setPreferredSize(new Dimension(200, 30));
//            jTextFields[i].setAutoscrolls(true);
//            roundPanel[5].add(jTextFields[i]);
//        }

        label[0].setFont(new Font("Times New Roman", Font.BOLD, 15));
        label[0].setHorizontalAlignment(JLabel.LEFT);
        label[0].setPreferredSize(new Dimension(130, 40));
        label[0].setAutoscrolls(true);
        roundPanel[5].add(label[0]);

        jTextFields[0].setFont(new Font("Times New Roman", Font.BOLD, 15));
        jTextFields[0].setHorizontalAlignment(JLabel.CENTER);
        jTextFields[0].setBorder(BorderFactory.createEmptyBorder());
        jTextFields[0].setEditable(false);
        jTextFields[0].setFocusable(false);
        jTextFields[0].setPreferredSize(new Dimension(200, 30));
        jTextFields[0].setAutoscrolls(true);
        roundPanel[5].add(jTextFields[0]);


        label[1].setFont(new Font("Times New Roman", Font.BOLD, 15));
        label[1].setHorizontalAlignment(JLabel.LEFT);
        label[1].setPreferredSize(new Dimension(130, 40));
        label[1].setAutoscrolls(true);
        roundPanel[5].add(label[1]);

        jTextFields[1].setFont(new Font("Times New Roman", Font.BOLD, 15));
        jTextFields[1].setHorizontalAlignment(JLabel.CENTER);
        jTextFields[1].setBorder(BorderFactory.createLineBorder(Color.black));
        jTextFields[1].setPreferredSize(new Dimension(200, 30));
        jTextFields[1].setAutoscrolls(true);
        roundPanel[5].add(jTextFields[1]);

        label[2].setFont(new Font("Times New Roman", Font.BOLD, 15));
        label[2].setHorizontalAlignment(JLabel.LEFT);
        label[2].setPreferredSize(new Dimension(130, 40));
        label[2].setAutoscrolls(true);
        roundPanel[5].add(label[2]);





        label[0].setText("Mã khuyến mãi:");
        label[1].setText("Giá trị (%):");
        label[2].setText("Ngày bắt đầu:");
        label[3].setText("Ngày kết thúc:");
        label[4].setText("Trạng thái:");



    }
}
