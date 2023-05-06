package com.cafe.custom;

import com.cafe.BLL.CustomerBLL;
import com.cafe.DTO.Customer;
import com.cafe.GUI.HomeGUI;
import com.cafe.utils.Day;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Map;

public class FrameCustomer extends JFrame {
    private JRadioButton rbMale;
    private JRadioButton rbFemale;
    private JRadioButton rbYes;
    private JRadioButton rbNo;
    private ButtonGroup gender;
    private ButtonGroup membership;
    private CustomerBLL customerBLL;
    private String phone;
    private Button confirm;
    private Button minimize;
    private Button exit;
    private RoundPanel[] roundPanel;
    private RoundPanel[] roundPanel1;
    private JPanel panel;
    private JLabel label;
    private JLabel[] label1;
    private JTextField jTextField[];
    public FrameCustomer(String phone) {
        this.phone = phone;
        initComponents();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
    }

    public static void main(String[] arg) {
        new FrameCustomer("0987654321").setVisible(true);
    }

    public void initComponents() {
        setSize(410, 685);
        setLocationRelativeTo(null);
        setUndecorated(true);

        gender = new ButtonGroup();
        membership = new ButtonGroup();
        rbMale = new JRadioButton("Nam", true);
        rbFemale = new JRadioButton("Nữ");
        rbYes = new JRadioButton("Có", true);
        rbNo = new JRadioButton("Không");
        roundPanel = new RoundPanel[10];
        label1 = new JLabel[10];
        jTextField = new JTextField[5];
        roundPanel1 = new RoundPanel[10];
        for (int i = 0; i < roundPanel.length; i++) {
            roundPanel[i] = new RoundPanel();
            roundPanel1[i] = new RoundPanel();
            label1[i] = new JLabel();
        }

        for (int i = 0; i < jTextField.length; i++) {
            jTextField[i] = new JTextField();
        }

        confirm = new Button();
        panel = new JPanel();
        minimize = new Button();
        exit = new Button();
        label = new JLabel();

        customerBLL = new CustomerBLL();

        roundPanel[0].setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        roundPanel[0].setBackground(new Color(68, 150, 60));
        this.add(roundPanel[0]);

        roundPanel[1].setPreferredSize(new Dimension(400, 30));
        roundPanel[1].setBackground(new Color(68, 150, 60));
        roundPanel[1].setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        roundPanel[0].add(roundPanel[1]);

        roundPanel[2].setPreferredSize(new Dimension(400, 640));
        roundPanel[2].setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
        roundPanel[2].setBackground(new Color(240, 240, 240));
        roundPanel[0].add(roundPanel[2]);

        roundPanel[3].setPreferredSize(new Dimension(394, 634));
        roundPanel[3].setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
        roundPanel[3].setBackground(new Color(68, 150, 60));
        roundPanel[2].add(roundPanel[3]);

        minimize.setBorder(null);
        minimize.setText("-");
        minimize.setPreferredSize(new Dimension(40, 25));
        minimize.setFocusPainted(false);
        minimize.setBackground(new Color(0xF3F0F0));
        minimize.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        minimize.setRadius(15);
        roundPanel[1].add(minimize);
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                minimize();
            }
        });
        minimize.setColor(new Color(0xF3F0F0));
        minimize.setColorOver(new Color(0xC4BDBD));
        minimize.setColorClick(new Color(0x676161));

        exit.setBorder(null);
        exit.setText("X");
        exit.setPreferredSize(new Dimension(40, 25));
        exit.setFocusPainted(false);
        exit.setBackground(new Color(0xFD1111));
        exit.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        exit.setRadius(15);
        roundPanel[1].add(exit);
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                exit();
            }
        });
        exit.setColor(new Color(0xFD1111));
        exit.setColorOver(new Color(0xB04848));
        exit.setColorClick(new Color(0xE79292));

        roundPanel[4].setPreferredSize(new Dimension(370, 60));
        roundPanel[4].setLayout(new FlowLayout(FlowLayout.CENTER));
        roundPanel[3].add(roundPanel[4]);

        roundPanel[5].setPreferredSize(new Dimension(370, 530));
        roundPanel[5].setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        roundPanel[3].add(roundPanel[5]);

        label.setBackground(new Color(250, 250, 250));
        label.setPreferredSize(new Dimension(300, 50));
        label.setFont(new Font("Tahoma", Font.BOLD, 16));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setText("THÊM KHÁCH HÀNG MỚI");
        roundPanel[4].add(label);

        for (int i = 0; i < 7; i++) {
            roundPanel1[i].setPreferredSize(new Dimension(340, 45));
            roundPanel1[i].setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
            roundPanel[5].add(roundPanel1[i]);

            label1[i].setBackground(new Color(250, 250, 250));
            label1[i].setPreferredSize(new Dimension(120, 50));
            label1[i].setFont(new Font("Times New Roman", Font.PLAIN, 16));
            label1[i].setHorizontalAlignment(SwingConstants.LEFT);
            roundPanel1[i].add(label1[i]);
        }

        roundPanel1[4].setPreferredSize(new Dimension(340, 45));
        roundPanel[5].add(roundPanel1[4]);


        label1[0].setText("CUSTOMER_ID:");
        label1[1].setText("NAME:");
        label1[2].setText("GENDER:");
        label1[3].setText("DOB:");
        label1[4].setText("PHONE:");
        label1[5].setText("MEMBERSHIP:");
        label1[6].setText("DOSUP:");

        jTextField[0].setFont(new Font("Times New Roman", Font.BOLD, 16));
        jTextField[0].setHorizontalAlignment(SwingConstants.CENTER);
        jTextField[0].setPreferredSize(new Dimension(210, 40));
        jTextField[0].setText(customerBLL.getAutoID());
        jTextField[0].setBackground(new Color(240, 240, 240));
        jTextField[0].setBorder(BorderFactory.createEmptyBorder());
        jTextField[0].setEditable(false);
        roundPanel1[0].add(jTextField[0]);

        jTextField[1].setFont(new Font("Times New Roman", Font.PLAIN, 16));
        jTextField[1].setPreferredSize(new Dimension(210, 40));
        roundPanel1[1].add(jTextField[1]);

        jTextField[2].setFont(new Font("Times New Roman", Font.PLAIN, 16));
        jTextField[2].setPreferredSize(new Dimension(210, 40));
        roundPanel1[3].add(jTextField[2]);

        jTextField[3].setFont(new Font("Times New Roman", Font.PLAIN, 16));
        jTextField[3].setPreferredSize(new Dimension(210, 40));
        jTextField[3].setText(phone);
        roundPanel1[4].add(jTextField[3]);

        jTextField[4].setFont(new Font("Times New Roman", Font.PLAIN, 16));
        jTextField[4].setPreferredSize(new Dimension(210, 40));
        roundPanel1[6].add(jTextField[4]);

        roundPanel1[7].setPreferredSize(new Dimension(210, 40));
        roundPanel1[7].setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        roundPanel1[2].add(roundPanel1[7]);
        roundPanel1[7].add(rbMale);
        roundPanel1[7].add(rbFemale);
        gender.add(rbMale);
        gender.add(rbFemale);

        roundPanel1[8].setPreferredSize(new Dimension(210, 40));
        roundPanel1[8].setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
        roundPanel1[5].add(roundPanel1[8]);
        roundPanel1[8].add(rbYes);
        roundPanel1[8].add(rbNo);
        membership.add(rbYes);
        membership.add(rbNo);

        roundPanel1[9].setPreferredSize(new Dimension(120, 45));
        roundPanel1[9].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roundPanel[5].add(roundPanel1[9]);

        confirm.setIcon(new ImageIcon("img/icons/add-user.png"));
        confirm.setBorderPainted(false);
        confirm.setText("THÊM");
        confirm.setFocusable(false);
        confirm.setFocusPainted(false);
        confirm.setPreferredSize(new Dimension(120, 45));
        confirm.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        confirm.setRadius(45);
        confirm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                addCustomer();
            }
        });
        confirm.setColor(new Color(135, 255, 58));
        confirm.setColorOver(new Color(0x499D20));
        confirm.setColorClick(new Color(0x2DFF00));
        roundPanel1[9].add(confirm);
    }

    public Customer getForm() throws Exception {
        String customerID = null;
        String name = null;
        boolean gender;
        Day dateOfBirth = null;
        String phone = null;
        boolean membership;
        Day dateOfSup = null;
        for (int i = 0; i < jTextField.length; i++) {
            switch (i) {
                case 0 -> customerID = jTextField[i].getText();
                case 1 -> name = jTextField[i].getText().toUpperCase();
                case 2 -> dateOfBirth = Day.parseDay(jTextField[i].getText().replaceAll("/", "-"));
                case 3 -> phone = jTextField[i].getText().replaceAll("^\\+?84", "0");
                case 4 -> dateOfSup = Day.parseDay(jTextField[i].getText().replaceAll("/", "-"));
                default -> {
                }
            }
        }
        gender = rbMale.isSelected();
        membership = rbYes.isSelected();
        return new Customer(customerID, name, gender, dateOfBirth, phone, membership, dateOfSup, false);
    }

    public void addCustomer() {
        if (checkInput()) {
            Customer newCustomer = null;
            try {
                newCustomer = getForm();
            } catch (Exception ignored) {

            }
            assert newCustomer != null;
            if (customerBLL.exists(newCustomer))
                JOptionPane.showMessageDialog(this, "Customer already existed!", "Error", JOptionPane.ERROR_MESSAGE);
            else if (customerBLL.exists(Map.of("PHONE", newCustomer.getPhone())))
                JOptionPane.showMessageDialog(this, "Customer already existed!", "Error", JOptionPane.ERROR_MESSAGE);
            else if (customerBLL.addCustomer(newCustomer))
                JOptionPane.showMessageDialog(this, "Successfully added new customer!", "Notification", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Failed to add new customer!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean checkInput() {
        for (JTextField textField : jTextField) {
            if (textField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in information!", "Error", JOptionPane.ERROR_MESSAGE);
                textField.requestFocusInWindow();
                return false;
            }
        }
        if (!jTextField[1].getText().matches("^[^|]+$")) {
            // Name can't contain "|"
            jTextField[1].requestFocusInWindow();
            jTextField[1].selectAll();
            JOptionPane.showMessageDialog(this, "Name can't contain \"|\"", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            if (!jTextField[2].getText().matches("^\\d{4}([-/])(0?[1-9]|1[0-2])\\1(0?[1-9]|[12][0-9]|3[01])$")) {
                // Date must follow yyyy-MM-dd or yyyy/MM/dd
                throw new Exception();
            }
            Day.parseDay(jTextField[2].getText().replaceAll("/", "-"));
        } catch (Exception exception) {
            jTextField[2].requestFocusInWindow();
            jTextField[2].selectAll();
            JOptionPane.showMessageDialog(this, "Date of birth must follow one of these patterns:\n\"yyyy-MM-dd\"\n\"yyyy/MM/dd\"", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!jTextField[3].getText().matches("^(\\+?84|0)[35789]\\d{8}$")) {
            // Phone must start with "0x" or "+84x" or "84x" where "x" in {3, 5, 7, 8, 9}
            jTextField[3].requestFocusInWindow();
            jTextField[3].selectAll();
            JOptionPane.showMessageDialog(this, "Phone must start with \"0x\" or \"+84x\" or \"84x\"\nwhere \"x\" in {3, 5, 7, 8, 9}", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            if (!jTextField[4].getText().matches("^\\d{4}([-/])(0?[1-9]|1[0-2])\\1(0?[1-9]|[12][0-9]|3[01])$")) {
                // Date must follow yyyy-MM-dd or yyyy/MM/dd
                throw new Exception();
            }
            Day.parseDay(jTextField[4].getText().replaceAll("/", "-"));
        } catch (Exception exception) {
            jTextField[4].requestFocusInWindow();
            jTextField[4].selectAll();
            JOptionPane.showMessageDialog(this, "Date of sign up must follow one of these patterns:\n\"yyyy-MM-dd\"\n\"yyyy/MM/dd\"", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void minimize() {
        setState(HomeGUI.ICONIFIED);
    }

    private void exit() {
        this.dispose();
    }

    public void pressConfirm() {

    }
}
