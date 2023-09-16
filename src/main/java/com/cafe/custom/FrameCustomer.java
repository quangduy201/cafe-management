package com.cafe.custom;

import com.cafe.BLL.CustomerBLL;
import com.cafe.DTO.Customer;
import com.cafe.GUI.HomeGUI;
import com.cafe.utils.Day;
import com.cafe.utils.Resource;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class FrameCustomer extends JFrame {
    private JDateChooser[] jDateChooser;
    private JTextField[] dateTextField;
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
    private JLabel label;
    private JLabel[] label1;
    private JTextField[] jTextField;

    public FrameCustomer(String phone) {
        System.gc();
        this.phone = phone;
        initComponents();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
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
        jTextField = new JTextField[3];
        roundPanel1 = new RoundPanel[10];
        jDateChooser = new JDateChooser[2];
        dateTextField = new JTextField[2];
        for (int i = 0; i < roundPanel.length; i++) {
            roundPanel[i] = new RoundPanel();
            roundPanel1[i] = new RoundPanel();
            label1[i] = new JLabel();
        }

        for (int i = 0; i < jTextField.length; i++) {
            jTextField[i] = new JTextField();
        }

        confirm = new Button();
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
        roundPanel[0].add(roundPanel[2]);

        roundPanel[3].setPreferredSize(new Dimension(394, 634));
        roundPanel[3].setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
        roundPanel[3].setBackground(new Color(68, 150, 60));
        roundPanel[2].add(roundPanel[3]);

        BiConsumer<Button, List<Object>> configButton = (button, properties) -> {
            button.setBorder(null);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            button.setBorderColor(Color.BLACK);
            button.setForeground(Color.BLACK);
            button.setText((String) properties.get(0));
            button.setPreferredSize(new Dimension((Integer) properties.get(1), (Integer) properties.get(2)));
            button.setRadius((Integer) properties.get(3));
            button.setColor((Color) properties.get(4));
            button.setColorOver((Color) properties.get(5));
            button.setColorClick((Color) properties.get(6));
            button.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    ((Runnable) properties.get(7)).run();
                }
            });
        };
        configButton.accept(minimize, List.of("-", 40, 25, 15, new Color(0xF3F0F0), new Color(0xC4BDBD), new Color(0x676161), (Runnable) this::minimize));
        roundPanel[1].add(minimize);
        configButton.accept(exit, List.of("X", 40, 25, 15, new Color(0xFD1111), new Color(0xB04848), new Color(0xE79292), (Runnable) this::exit));
        roundPanel[1].add(exit);

        Dimension inputFieldsSize = new Dimension(210, 40);
        for (int i = 0; i < 2; i++) {
            jDateChooser[i] = new JDateChooser();
            jDateChooser[i].setDateFormatString("dd/MM/yyyy");
            jDateChooser[i].setPreferredSize(inputFieldsSize);
            jDateChooser[i].setMinSelectableDate(new Day(1, 1, 1000).toDateSafe());
            dateTextField[i] = (JTextField) jDateChooser[i].getDateEditor().getUiComponent();
            dateTextField[i].setFont(new Font("Tahoma", Font.BOLD, 14));
            int index = i;
            dateTextField[i].addActionListener(e -> {
                try {
                    Day day = Day.parseDay(dateTextField[index].getText());
                    jDateChooser[index].setDate(day.toDate());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ngày không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        roundPanel[4].setPreferredSize(new Dimension(370, 60));
        roundPanel[4].setLayout(new FlowLayout(FlowLayout.CENTER));
        roundPanel[3].add(roundPanel[4]);

        roundPanel[5].setPreferredSize(new Dimension(370, 530));
        roundPanel[5].setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        roundPanel[3].add(roundPanel[5]);

        label.setPreferredSize(new Dimension(300, 50));
        label.setFont(new Font("Tahoma", Font.BOLD, 16));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setText("THÊM KHÁCH HÀNG MỚI");
        roundPanel[4].add(label);

        for (int i = 0; i < 7; i++) {
            roundPanel1[i].setPreferredSize(new Dimension(340, 45));
            roundPanel1[i].setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
            roundPanel[5].add(roundPanel1[i]);

            label1[i].setPreferredSize(new Dimension(120, 50));
            label1[i].setFont(new Font("Times New Roman", Font.PLAIN, 16));
            label1[i].setHorizontalAlignment(SwingConstants.LEFT);
            roundPanel1[i].add(label1[i]);
        }

        roundPanel1[4].setPreferredSize(new Dimension(340, 45));
        roundPanel[5].add(roundPanel1[4]);


        label1[0].setText("Mã khách hàng:");
        label1[1].setText("Tên khách hàng:");
        label1[2].setText("Giới tính:");
        label1[3].setText("Ngày sinh:");
        label1[4].setText("Điện thoại:");
        label1[5].setText("Thành viên:");
        label1[6].setText("Ngày đăng ký:");

        jTextField[0].setFont(new Font("Times New Roman", Font.BOLD, 16));
        jTextField[0].setHorizontalAlignment(SwingConstants.CENTER);
        jTextField[0].setPreferredSize(new Dimension(210, 40));
        jTextField[0].setText(customerBLL.getAutoID());
        jTextField[0].setBorder(BorderFactory.createEmptyBorder());
        jTextField[0].setEditable(false);
        roundPanel1[0].add(jTextField[0]);

        jTextField[1].setFont(new Font("Times New Roman", Font.PLAIN, 16));
        jTextField[1].setPreferredSize(new Dimension(210, 40));
        roundPanel1[1].add(jTextField[1]);

        roundPanel1[3].add(jDateChooser[0]);

        jTextField[2].setFont(new Font("Times New Roman", Font.PLAIN, 16));
        jTextField[2].setPreferredSize(new Dimension(210, 40));
        jTextField[2].setText(phone);
        jTextField[2].addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != '+') {
                    e.consume();
                }
            }
        });
        roundPanel1[4].add(jTextField[2]);

        roundPanel1[6].add(jDateChooser[1]);

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

        configButton.accept(confirm, List.of("THÊM", 120, 45, 45, new Color(135, 255, 58), new Color(0x499D20), new Color(0x2DFF00), (Runnable) this::addCustomer));
        confirm.setIcon(Resource.loadImageIconIn("img/icons/add-user.png"));
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
                case 2 -> phone = jTextField[i].getText().replaceAll("^\\+?84", "0");
                default -> {
                }
            }
        }
        dateOfBirth = new Day(jDateChooser[0].getDate());
        if (jDateChooser[1].getDate() == null) {
            dateOfSup = new Day(1, 1, 1000);
        } else {
            dateOfSup = new Day(jDateChooser[1].getDate());
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
                JOptionPane.showMessageDialog(this, "Khách hàng đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (customerBLL.exists(Map.of("PHONE", newCustomer.getPhone())))
                JOptionPane.showMessageDialog(this, "Khách hàng đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (customerBLL.addCustomer(newCustomer)) {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng mới thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            } else
                JOptionPane.showMessageDialog(this, "Thêm khách hàng mới thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean checkInput() {
        for (JTextField textField : jTextField) {
            if (textField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                textField.requestFocusInWindow();
                return false;
            }
        }
        if (jDateChooser[0].getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày sinh!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            jDateChooser[0].requestFocusInWindow();
            return false;
        }
        if (!jTextField[1].getText().matches("^[^|]+$")) {
            // Name can't contain "|"
            jTextField[1].requestFocusInWindow();
            jTextField[1].selectAll();
            JOptionPane.showMessageDialog(this, "Tên khách hàng không được chứa \"|\"", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!jTextField[2].getText().matches("^(\\+?84|0)[35789]\\d{8}$")) {
            // Phone must start with "0x" or "+84x" or "84x" where "x" in {3, 5, 7, 8, 9}
            jTextField[2].requestFocusInWindow();
            jTextField[2].selectAll();
            JOptionPane.showMessageDialog(this, "Số điện thoại phải bắt đầu từ \"0x\" hoặc \"+84x\" hoặc \"84x\"\nvới \"x\" thuộc {3, 5, 7, 8, 9}", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
}
