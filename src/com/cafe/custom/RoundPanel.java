package com.cafe.custom;

import javax.swing.*;
import java.awt.*;

public class RoundPanel extends JPanel {

    public RoundPanel() {
        setOpaque(false);
    }

    @Override
    public void paint(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.dispose();
        super.paint(graphics);
    }
}