package com.cafe.custom;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ToggleSwitch extends JToggleButton {
    private Color onColor;
    private Color offColor;
    private Icon onIcon;
    private Icon offIcon;

    public ToggleSwitch() {
        setPreferredSize(new Dimension(50, 30));
        setBorder(null);
        setBackground(new Color(70, 67, 67));
        setFocusable(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorderPainted(false);
        setOpaque(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        if (isSelected()) {
            g2d.setColor(onColor);
        } else {
            g2d.setColor(offColor);
        }
        g2d.fillRoundRect(0, 0, width, height, height, height);

        int handleWidth = width / 2;
        int handleHeight = height - 2;
        int handleX = isSelected() ? width - handleWidth : 0;
        if (isSelected()) {
            if (onIcon != null) {
                onIcon.paintIcon(this, g2d, handleX, 1);
            } else {
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(handleX, 1, handleWidth, handleHeight, height, height);
            }
        } else {
            if (offIcon != null) {
                offIcon.paintIcon(this, g2d, handleX, 1);
            } else {
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(handleX, 1, handleWidth, handleHeight, height, height);
            }
        }
    }

    public void setOnColor(Color color) {
        onColor = color;
    }

    public void setOffColor(Color color) {
        offColor = color;
    }

    public void setOnIcon(Icon icon) {
        onIcon = icon;
        repaint();
    }

    public void setOffIcon(Icon icon) {
        offIcon = icon;
        repaint();
    }
}

