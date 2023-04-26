package com.cafe.GUI;

import javax.swing.*;
import java.awt.*;

public class ImportGUI extends JPanel {
    private int decentralizationMode;

    public ImportGUI(int decentralizationMode) {
        this.decentralizationMode = decentralizationMode;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(70, 67, 67));
        initComponents();
    }

    public void initComponents() {
    }
}
