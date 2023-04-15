package com.cafe.GUI;
import com.cafe.custom.Button;

import com.cafe.custom.RoundPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BillGUI extends JPanel {
    public BillGUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(70, 67, 67));
        initComponents();
    }

    private RoundPanel roundPanel[];
    private JLabel label[];

    private JScrollPane jScrollPane[];

    private Button button[];
    public void initComponents() {
        roundPanel = new RoundPanel[20];
        label = new JLabel[20];
        button = new Button[3];
        jScrollPane = new JScrollPane[2];
        jScrollPane[0] = new JScrollPane();
        jScrollPane[1] = new JScrollPane();
        button[0] = new Button();
        button[1] = new Button();
        button[2] = new Button();
        for (int i = 0; i < roundPanel.length; i++) {
            roundPanel[i] = new RoundPanel();
            label[i] = new JLabel();
        }

        roundPanel[0].setLayout(new FlowLayout(FlowLayout.CENTER,20,0));
        roundPanel[0].setBackground(new Color(70, 67, 67));
        this.add(roundPanel[0],BorderLayout.CENTER);

        roundPanel[1].setLayout(new FlowLayout(FlowLayout.CENTER,0,20));
        roundPanel[1].setPreferredSize(new Dimension(560, 670));
        roundPanel[1].setBackground(new Color(70, 67, 67));
        roundPanel[1].setAutoscrolls(true);
        roundPanel[0].add(roundPanel[1]);

        roundPanel[2].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
//        roundPanel[2].setBackground(new Color(145, 0, 0));
        roundPanel[2].setPreferredSize(new Dimension(410, 670));
        roundPanel[2].setAutoscrolls(true);
        roundPanel[0].add(roundPanel[2]);

        roundPanel[3].setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        roundPanel[3].setPreferredSize(new Dimension(560, 40));
        roundPanel[3].setBackground(new Color(70, 67, 67));
        roundPanel[3].setAutoscrolls(true);
        roundPanel[1].add(roundPanel[3]);

        roundPanel[4].setPreferredSize(new Dimension(560, 530));
        roundPanel[4].setAutoscrolls(true);
        roundPanel[1].add(roundPanel[4]);

        roundPanel[5].setLayout(new BorderLayout(240,0));
        roundPanel[5].setPreferredSize(new Dimension(560, 40));
        roundPanel[5].setBackground(new Color(70, 67, 67));
        roundPanel[5].setAutoscrolls(true);
        roundPanel[1].add(roundPanel[5]);

        roundPanel[6].setPreferredSize(new Dimension(410, 40));
        roundPanel[6].setAutoscrolls(true);
        roundPanel[2].add(roundPanel[6]);

        roundPanel[7].setPreferredSize(new Dimension(410, 30));
        roundPanel[7].setAutoscrolls(true);
        roundPanel[2].add(roundPanel[7]);

        roundPanel[8].setPreferredSize(new Dimension(410, 30));
        roundPanel[8].setAutoscrolls(true);
        roundPanel[2].add(roundPanel[8]);

        roundPanel[9].setPreferredSize(new Dimension(410, 30));
        roundPanel[9].setAutoscrolls(true);
        roundPanel[2].add(roundPanel[9]);

        roundPanel[10].setPreferredSize(new Dimension(410, 30));
        roundPanel[10].setAutoscrolls(true);
        roundPanel[2].add(roundPanel[10]);

        roundPanel[11].setPreferredSize(new Dimension(410, 370));
        roundPanel[11].setBackground(new Color(255, 0, 0));
        roundPanel[11].setAutoscrolls(true);
        roundPanel[2].add(roundPanel[11]);

        roundPanel[12].setPreferredSize(new Dimension(410, 30));
        roundPanel[12].setBackground(new Color(255, 0, 0));
        roundPanel[12].setAutoscrolls(true);
        roundPanel[2].add(roundPanel[12]);

        roundPanel[13].setPreferredSize(new Dimension(410, 30));
        roundPanel[13].setBackground(new Color(255, 0, 0));
        roundPanel[13].setAutoscrolls(true);
        roundPanel[2].add(roundPanel[13]);

        roundPanel[14].setPreferredSize(new Dimension(410, 30));
        roundPanel[14].setBackground(new Color(255, 0, 0));
        roundPanel[14].setAutoscrolls(true);
        roundPanel[2].add(roundPanel[14]);

        label[0].setFont(new Font("Times New Roman", Font.BOLD, 20));
        label[0].setHorizontalAlignment(JLabel.CENTER);
        label[0].setText("Danh Sách Phiếu Nhập Hàng");
        label[0].setPreferredSize(new Dimension(400, 50));
        label[0].setBackground(new Color(255, 0, 0));
        label[0].setForeground(Color.white);
        label[0].setAutoscrolls(true);
        roundPanel[3].add(label[0]);

//        pictureScrollPane.setPreferredSize(new Dimension(350, 355));
//        pictureScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//        roundPanel9.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
//        roundPanel9.setBackground(new Color(240, 240, 240));
//        roundPanel9.setBorder(BorderFactory.createLineBorder(Color.black));
//        roundPanel9.setPreferredSize(new Dimension(pictureScrollPane.getWidth(), 355));
//        roundPanel2.add(pictureScrollPane);

        roundPanel[15].setLayout(new BorderLayout(5,0));
        roundPanel[15].setBackground(new Color(70, 67, 67));
        roundPanel[15].setPreferredSize(new Dimension(170, 40));
        roundPanel[15].setAutoscrolls(true);
        roundPanel[5].add(roundPanel[15],BorderLayout.WEST);


        button[0].setPreferredSize(new Dimension(80, 40));
        button[0].setBorderPainted(false);
        button[0].setRadius(15);
        button[0].setFocusPainted(false);
        button[0].setFont(new Font("Times New Roman", 0, 14));
        button[0].setColor(new Color(0x70E149));
        button[0].setColorOver(new Color(0x5EFF00));
        button[0].setColorClick(new Color(0x8AD242));
        button[0].setBorderColor(new Color(70, 67, 67));
        button[0].setText("Bán");
        button[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                try {
                    pressadd();
                } catch (Exception ignored) {

                }
            }
        });
        roundPanel[15].add(button[0],BorderLayout.WEST);

        button[1].setPreferredSize(new Dimension(80, 40));
        button[1].setBorderPainted(false);
        button[1].setRadius(15);
        button[1].setFocusPainted(false);
        button[1].setFont(new Font("Times New Roman", 0, 14));
        button[1].setColor(new Color(0x70E149));
        button[1].setColorOver(new Color(0x5EFF00));
        button[1].setColorClick(new Color(0x8AD242));
        button[1].setBorderColor(new Color(70, 67, 67));
        button[1].setText("Thêm");
        button[1].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                try {
                    pressimporting();
                } catch (Exception ignored) {

                }
            }
        });
        roundPanel[15].add(button[1],BorderLayout.EAST);


        button[2].setPreferredSize(new Dimension(160, 40));
        button[2].setBorderPainted(false);
        button[2].setRadius(15);
        button[2].setFocusPainted(false);
        button[2].setFont(new Font("Times New Roman", 0, 14));
        button[2].setColor(new Color(240,240,240));
        button[2].setColorOver(new Color(0x5EFF00));
        button[2].setColorClick(new Color(0x8AD242));
        button[2].setBorderColor(new Color(70, 67, 67));
        button[2].setIcon(new ImageIcon("img/folder.png"));
        button[2].setText("Xuất Exxcel");
        button[2].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                try {
                    pressExxcel();
                } catch (Exception ignored) {

                }
            }
        });
        roundPanel[5].add(button[2],BorderLayout.EAST);

        label[1].setFont(new Font("Times New Roman", Font.BOLD, 30));
        label[1].setHorizontalAlignment(JLabel.CENTER);
        label[1].setText("Hóa Đơn");
        label[1].setPreferredSize(new Dimension(400, 40));
        label[1].setAutoscrolls(true);
        roundPanel[6].add(label[1]);

        label[2].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[2].setHorizontalAlignment(JLabel.LEFT);
        label[2].setText("BILL_ID:");
        label[2].setPreferredSize(new Dimension(130, 30));
        label[2].setAutoscrolls(true);
        roundPanel[7].add(label[2]);

        label[3].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[3].setHorizontalAlignment(JLabel.LEFT);
        label[3].setPreferredSize(new Dimension(250, 30));
        label[3].setAutoscrolls(true);
        roundPanel[7].add(label[3]);

        label[4].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[4].setHorizontalAlignment(JLabel.LEFT);
        label[4].setText("Tên Khách Hàng:");
        label[4].setPreferredSize(new Dimension(130, 30));
        label[4].setAutoscrolls(true);
        roundPanel[8].add(label[4]);

        label[5].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[5].setHorizontalAlignment(JLabel.LEFT);
        label[5].setPreferredSize(new Dimension(250, 30));
        label[5].setAutoscrolls(true);
        roundPanel[8].add(label[5]);

        label[6].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[6].setHorizontalAlignment(JLabel.LEFT);
        label[6].setText("STAFF_ID:");
        label[6].setPreferredSize(new Dimension(130, 30));
        label[6].setAutoscrolls(true);
        roundPanel[9].add(label[6]);

        label[7].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[7].setHorizontalAlignment(JLabel.LEFT);
        label[7].setPreferredSize(new Dimension(250, 30));
        label[7].setAutoscrolls(true);
        roundPanel[9].add(label[7]);

        label[8].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[8].setHorizontalAlignment(JLabel.LEFT);
        label[8].setText("Thời Gian:");
        label[8].setPreferredSize(new Dimension(130, 30));
        label[8].setAutoscrolls(true);
        roundPanel[10].add(label[8]);

        label[9].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[9].setHorizontalAlignment(JLabel.LEFT);
        label[9].setPreferredSize(new Dimension(250, 30));
        label[9].setAutoscrolls(true);
        roundPanel[10].add(label[9]);

    }


    public void pressadd() {

    }

    public void pressimporting() {

    }

    public void pressExxcel() {

    }
}
