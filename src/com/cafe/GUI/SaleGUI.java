package com.cafe.GUI;

import com.cafe.custom.RoundPanel;

import javax.swing.*;
import java.awt.*;

public class SaleGUI extends JPanel {
    public SaleGUI() {
        setLayout(new BorderLayout(10,10));
        setBackground(new Color(51,51,51));
        initComponents();
    }

    public void initComponents() {
        roundPanel1 = new RoundPanel();
        roundPanel2 = new RoundPanel();
        sale = new RoundPanel();

        sale.setLayout(new FlowLayout(FlowLayout.CENTER,10,0));
        sale.setBackground(new Color(51,51,51));
        this.add(sale,BorderLayout.CENTER);

        roundPanel1.setBackground(new Color(0xFFFFFF));
        roundPanel1.setPreferredSize(new Dimension(635,680));
        roundPanel1.setAutoscrolls(true);
        sale.add(roundPanel1);

        roundPanel2.setBackground(new Color(0xFFFFFF));
        roundPanel2.setPreferredSize(new Dimension(350,680));
        roundPanel2.setAutoscrolls(true);
        sale.add(roundPanel2);

    }

    private RoundPanel sale;
    private RoundPanel roundPanel1;
    private RoundPanel roundPanel2;
}
