package com.cafe.main;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CheckboxExample {
        public static void main(String[] args) {
            // Tạo một JFrame (cửa sổ ứng dụng)
            JFrame frame = new JFrame("Ví dụ Checkbox");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);

            // Tạo một JPanel để chứa các thành phần
            JPanel panel = new JPanel();
            frame.add(panel);

            // Tạo một Checkbox
            JCheckBox checkBox = new JCheckBox("Chọn tùy chọn");

            // Tạo một Label để hiển thị kết quả
            JLabel label = new JLabel("Tùy chọn không được chọn.");

            // Thêm sự kiện cho checkbox
            checkBox.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        label.setText("Tùy chọn đã được chọn.");
                    } else {
                        label.setText("Tùy chọn không được chọn.");
                    }
                }
            });

            // Thêm checkbox và label vào JPanel
            panel.add(checkBox);
            panel.add(label);

            // Hiển thị cửa sổ
            frame.setVisible(true);
        }
    }
