package com.cafe.custom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Button extends JButton {
    private boolean over;
    private Color color;
    private Color colorOver;
    private Color colorClick;
    private Color borderColor;
    private int radius = 0;

    public Button() {
        setContentAreaFilled(false);
        initComponent();
    }

    public Button(Color color) {
        this.borderColor = color;
        setContentAreaFilled(false);
        initComponent();
    }

    public static void configButton(Button button, List<Object> properties) {
        button.setBorder(null);
        button.setColor(new Color(240, 240, 240));
        button.setColorClick(new Color(141, 222, 175));
        button.setColorOver(new Color(35, 166, 97));
        button.setBorderColor(Color.BLACK);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setRadius(20);
        button.setText((String) properties.get(0));
        button.setIcon(new ImageIcon((String) properties.get(1)));
        button.setEnabled((Boolean) properties.get(2));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (button.isEnabled()) {
                    ((Runnable) properties.get(3)).run();
                }
            }
        });
    }

    public void initComponent() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(colorOver);
                over = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(color);
                over = false;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(colorClick);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (over) {
                    setBackground(colorOver);
                } else {
                    setBackground(color);
                }
            }
        });
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        setBackground(color);
    }

    public Color getColorOver() {
        return colorOver;
    }

    public void setColorOver(Color colorOver) {
        this.colorOver = colorOver;
    }

    public Color getColorClick() {
        return colorClick;
    }

    public void setColorClick(Color colorClick) {
        this.colorClick = colorClick;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Paint Border
        g2.setColor(getBorderColor());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        //Border set 2px
        g2.setColor(getBackground());
        g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, radius, radius);

        super.paintComponent(g);
    }
}
