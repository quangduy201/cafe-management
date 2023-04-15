package com.cafe.custom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProductPanel extends JPanel{
    public ProductPanel() {
        setOpaque(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered (MouseEvent e) {
                if(!pressover) {
                    setBackground(colorOver);
                    over = true;
                }
            }

            @Override
            public void mouseExited (MouseEvent e) {
                if(!pressover) {
                    setBackground(color);
                    over = false;
                }
            }

            @Override
            public void mouseReleased (MouseEvent e) {
                if(!pressover) {
                    if (over) {
                        setBackground(colorOver);
                    } else {
                        setBackground(color);
                    }
                }
            }
        });
    }

    private boolean over = false;

    public boolean isOver() {
        return over;
    }

    public boolean isPressover() {
        return pressover;
    }

    public void setPressover(boolean pressover) {
        this.pressover = pressover;
    }

    private boolean pressover;
    private Color color;
    private Color colorOver;


    public void setOver(boolean over) {
        this.over = over;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColorOver() {
        return colorOver;
    }

    public void setColorOver(Color colorOver) {
        this.colorOver = colorOver;
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
