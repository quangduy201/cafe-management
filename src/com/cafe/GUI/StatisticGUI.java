package com.cafe.GUI;

import com.cafe.custom.Header;
import com.cafe.custom.RoundPanel;
import com.cafe.custom.*;
import com.cafe.custom.ButtonStatic;
import org.apache.poi.sl.usermodel.Background;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StatisticGUI extends JPanel {
    public StatisticGUI(int decentralization) {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(70, 67, 67));
        initComponents();
    }
    private RoundPanel statistic;
    private RoundPanel[] roundPanel;
    private ButtonStatic[] btFunction;
    private Header btTest;
    public void initComponents() {
        btFunction = new ButtonStatic[5];
        roundPanel = new RoundPanel[10];
        for (int i = 0; i < roundPanel.length; i++) {
            roundPanel[i] = new RoundPanel();
        }
        for (int i = 0; i < btFunction.length; i++) {
            btFunction[i] = new ButtonStatic();
        }
        statistic = new RoundPanel();

        statistic.setBackground(new Color(70, 67, 67));
        statistic.setLayout(new BorderLayout(0,0 ));
        this.add(statistic, BorderLayout.CENTER);

        roundPanel[0].setLayout(new FlowLayout(FlowLayout.LEFT,10,0));
        roundPanel[0].setBackground(new Color(70, 67, 67));
        roundPanel[0].setPreferredSize(new Dimension(1000, 30));
        roundPanel[0].setAutoscrolls(true);
        statistic.add(roundPanel[0], BorderLayout.NORTH);

        roundPanel[1].setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
        roundPanel[1].setBackground(new Color(0xFFFFFF));
        roundPanel[1].setPreferredSize(new Dimension(1000, 640));
        roundPanel[1].setAutoscrolls(true);
        statistic.add(roundPanel[1], BorderLayout.CENTER);



        roundPanel[2].setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        roundPanel[2].setPreferredSize(new Dimension(500, 30));
        roundPanel[2].setBackground(new Color(70, 67, 67));
        roundPanel[2].setAutoscrolls(true);
        roundPanel[0].add(roundPanel[2]);

        btFunction[0].setText("Tổng quát");
        btFunction[0].setBackground(new Color(0xFFFFFF));
        btFunction[0].setPreferredSize(new Dimension(100,35));
        btFunction[0].setBorderPainted(false);
        btFunction[0].setFocusPainted(false);
        btFunction[0].setFont(new Font("Public Sans", Font.BOLD, 12));
        btFunction[0].setColor(new Color(0xFFFFFF));
        btFunction[0].setColorOver(new Color(0xB49A9A));
        btFunction[0].setRadius(15);
        btFunction[2].addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
            }
        });
        roundPanel[2].add(btFunction[0]);

        btFunction[1].setText("Chi tiết");
        btFunction[1].setBackground(new Color(0xD0CCCC));
        btFunction[1].setPreferredSize(new Dimension(100,35));
        btFunction[1].setBorderPainted(false);
        btFunction[1].setFocusPainted(false);
        btFunction[1].setFont(new Font("Public Sans", Font.BOLD, 12));
        btFunction[1].setBorderColor(new Color(0xD0CCCC));
        btFunction[1].setRadius(15);
        btFunction[0].setColor(new Color(0xFFFFFF));
        btFunction[0].setColorOver(new Color(0xB49A9A));
        btFunction[1].addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
            }
        });
        roundPanel[2].add(btFunction[1]);


        btFunction[2].setText("Theo ngày");
        btFunction[2].setBackground(new Color(0xD0CCCC));
        btFunction[2].setPreferredSize(new Dimension(100,35));
        btFunction[2].setBorderPainted(false);
        btFunction[2].setFocusPainted(false);
        btFunction[2].setFont(new Font("Public Sans", Font.BOLD, 12));
        btFunction[2].setBorderColor(new Color(0xD0CCCC));
        btFunction[2].setRadius(15);
        btFunction[2].addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
            }
        });
        roundPanel[2].add(btFunction[2]);

        btFunction[3].setText("Biểu đồ");
        btFunction[3].setBackground(new Color(0xD0CCCC));
        btFunction[3].setPreferredSize(new Dimension(100,35));
        btFunction[3].setBorderPainted(false);
        btFunction[3].setFocusPainted(false);
        btFunction[3].setFont(new Font("Public Sans", Font.BOLD, 12));
        btFunction[3].setBorderColor(new Color(0xD0CCCC));
        btFunction[3].setRadius(15);
        btFunction[3].addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
            }
        });
        roundPanel[2].add(btFunction[3]);
    }
}
