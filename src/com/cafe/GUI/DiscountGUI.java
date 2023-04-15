package com.cafe.GUI;

import javax.swing.*;
import java.awt.*;

public class DiscountGUI extends JPanel {
    private int decentralizationMode;

    public DiscountGUI(int decentralizationMode) {
        this.decentralizationMode = decentralizationMode;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(70, 67, 67));
        initComponents();
    }

    public void initComponents() {

    }
}
