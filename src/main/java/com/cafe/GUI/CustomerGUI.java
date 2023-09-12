package com.cafe.GUI;

import com.cafe.BLL.CustomerBLL;
import com.cafe.DTO.Customer;
import com.cafe.DTO.DecentralizationDetail;
import com.cafe.custom.Button;
import com.cafe.custom.DataTable;
import com.cafe.custom.RoundPanel;
import com.cafe.utils.Day;
import com.cafe.utils.Resource;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

public class CustomerGUI extends JPanel {
    private CustomerBLL customerBLL = new CustomerBLL();
    private DecentralizationDetail decentralizationMode;
    private DataTable dataTable;
    private RoundPanel customer;
    private RoundPanel roundPanel1;
    private RoundPanel roundPanel2;
    private RoundPanel search;
    private JScrollPane scrollPane;
    private JPanel pnlCustomerConfiguration;
    private JPanel showImg;
    private JPanel mode;
    private JPanel radiusBtGender;
    private JPanel radiusBtMember;
    private JLabel[] jLabelsForm;
    private JDateChooser[] jDateChooser;
    private JTextField[] dateTextField;
    private JComboBox<Object> cbbSearchFilter;
    private JRadioButton rbMale;
    private JRadioButton rbMaleSearch;
    private JRadioButton rbFemale;
    private JRadioButton rbFemaleSearch;
    private JRadioButton rbYes;
    private JRadioButton rbYesSearch;
    private JRadioButton rbNo;
    private JRadioButton rbNoSearch;
    private JTextField txtSearch;
    private JTextField[] jTextFieldsForm;
    private Button btAdd;
    private Button btUpd;
    private Button btDel;
    private Button btRef;
    private Button btFaceSignUp;
    private Button findCustomerByFace;

    public CustomerGUI(DecentralizationDetail decentralizationMode) {
        System.gc();
        this.decentralizationMode = decentralizationMode;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(70, 67, 67));
        initComponents();
    }

    public void initComponents() {
        List<String> columnNames = customerBLL.getCustomerDAL().getColumnNames();
        customer = new RoundPanel();
        roundPanel1 = new RoundPanel();
        roundPanel2 = new RoundPanel();
        search = new RoundPanel();
        pnlCustomerConfiguration = new JPanel();
        showImg = new JPanel();
        mode = new JPanel();
        radiusBtGender = new JPanel();
        radiusBtMember = new JPanel();
        jLabelsForm = new JLabel[columnNames.size() - 1];
        jDateChooser = new JDateChooser[2];
        dateTextField = new JTextField[2];
        cbbSearchFilter = new JComboBox<>(new String[]{"Mã khách hàng", "Tên khách hàng", "Giới tính", "Ngày sinh", "Điện thoại", "Thành viên", "Ngày đăng ký"});
        rbMale = new JRadioButton("Nam", true);
        rbMaleSearch = new JRadioButton("Nam", true);
        rbFemale = new JRadioButton("Nữ");
        rbFemaleSearch = new JRadioButton("Nữ");
        rbYes = new JRadioButton("Có", true);
        rbYesSearch = new JRadioButton("Có", true);
        rbNo = new JRadioButton("Không");
        rbNoSearch = new JRadioButton("Không");
        txtSearch = new JTextField(20);
        jTextFieldsForm = new JTextField[columnNames.size() - 5];
        btAdd = new Button();
        btUpd = new Button();
        btDel = new Button();
        btRef = new Button();

        customer.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        customer.setBackground(new Color(70, 67, 67));
        this.add(customer, BorderLayout.CENTER);

        roundPanel1.setLayout(new BorderLayout(0, 10));
        roundPanel1.setBackground(new Color(70, 67, 67));
        roundPanel1.setPreferredSize(new Dimension(635, 680));
        roundPanel1.setAutoscrolls(true);
        roundPanel1.add(new JScrollPane(dataTable), BorderLayout.CENTER);
        customer.add(roundPanel1);

        roundPanel2.setLayout(new BorderLayout());
        roundPanel2.setPreferredSize(new Dimension(350, 680));
        roundPanel2.setAutoscrolls(true);
        customer.add(roundPanel2);

        search.setLayout(new FlowLayout());
        search.setPreferredSize(new Dimension(635, 35));
        roundPanel1.add(search, BorderLayout.NORTH);

        cbbSearchFilter.addActionListener(e -> selectSearchFilter());
        search.add(cbbSearchFilter);

        radiusBtGender.setLayout(new FlowLayout());
        ButtonGroup group = new ButtonGroup();
        group.add(rbMaleSearch);
        group.add(rbFemaleSearch);
        radiusBtGender.add(rbMaleSearch);
        radiusBtGender.add(rbFemaleSearch);
        radiusBtGender.setVisible(false);
        radiusBtGender.setBackground(null);
        rbMaleSearch.setBackground(null);
        rbFemaleSearch.setBackground(null);
        rbMaleSearch.addItemListener(e -> genderSearch());
        rbFemaleSearch.addItemListener(e -> genderSearch());
        search.add(radiusBtGender);

        radiusBtMember.setLayout(new FlowLayout());
        ButtonGroup group1 = new ButtonGroup();
        group1.add(rbYesSearch);
        group1.add(rbNoSearch);
        radiusBtMember.add(rbYesSearch);
        radiusBtMember.add(rbNoSearch);
        radiusBtMember.setVisible(false);
        radiusBtMember.setBackground(null);
        rbYesSearch.setBackground(null);
        rbNoSearch.setBackground(null);
        rbYesSearch.addItemListener(e -> membershipSearch());
        rbNoSearch.addItemListener(e -> membershipSearch());
        search.add(radiusBtMember);

        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchCustomers();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchCustomers();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchCustomers();
            }
        });
        search.add(txtSearch);

        dataTable = new DataTable(customerBLL.getData(), new String[]{"Mã khách hàng", "Tên khách hàng", "Giới tính", "Ngày sinh", "Điện thoại", "Thành viên", "Ngày đăng ký"}, e -> fillForm());
        scrollPane = new JScrollPane(dataTable);
        roundPanel1.add(scrollPane);

        pnlCustomerConfiguration.setLayout(new GridLayout(7, 2, 20, 20));
        pnlCustomerConfiguration.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
        pnlCustomerConfiguration.setPreferredSize(new Dimension(635, 350));
        roundPanel2.add(pnlCustomerConfiguration, BorderLayout.NORTH);

        Dimension inputFieldsSize = new Dimension(200, 30);
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

        int index = 0;
        for (int i = 0; i < columnNames.size() - 1; i++) {
            jLabelsForm[i] = new JLabel();
            pnlCustomerConfiguration.add(jLabelsForm[i]);
            switch (columnNames.get(i)) {
                case "CUSTOMER_ID" -> {
                    jLabelsForm[i].setText("Mã khách hàng: ");
                    jTextFieldsForm[index] = new JTextField(customerBLL.getAutoID());
                    jTextFieldsForm[index].setEnabled(false);
                    jTextFieldsForm[index].setBorder(null);
                    jTextFieldsForm[index].setDisabledTextColor(null);
                    pnlCustomerConfiguration.add(jTextFieldsForm[index]);
                    index++;
                }
                case "NAME" -> {
                    jLabelsForm[i].setText("Tên khách hàng: ");
                    jTextFieldsForm[index] = new JTextField();
                    jTextFieldsForm[index].setText(null);
                    pnlCustomerConfiguration.add(jTextFieldsForm[index]);
                    index++;
                }
                case "GENDER" -> {
                    jLabelsForm[i].setText("Giới tính: ");
                    JPanel panel = new JPanel(new FlowLayout());
                    ButtonGroup buttonGroup = new ButtonGroup();
                    panel.setBackground(null);
                    rbMale.setBackground(null);
                    rbFemale.setBackground(null);
                    buttonGroup.add(rbMale);
                    buttonGroup.add(rbFemale);
                    panel.add(rbMale);
                    panel.add(rbFemale);
                    pnlCustomerConfiguration.add(panel);
                }
                case "DOB" -> {
                    jLabelsForm[i].setText("Ngày sinh: ");
                    pnlCustomerConfiguration.add(jDateChooser[0]);
                }
                case "PHONE" -> {
                    jLabelsForm[i].setText("Điện thoại: ");
                    jTextFieldsForm[index] = new JTextField();
                    jTextFieldsForm[index].setText(null);
                    jTextFieldsForm[index].addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            char c = e.getKeyChar();
                            if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != '+') {
                                e.consume();
                            }
                        }
                    });
                    pnlCustomerConfiguration.add(jTextFieldsForm[index]);
                    index++;
                }
                case "MEMBERSHIP" -> {
                    jLabelsForm[i].setText("Thành viên: ");
                    JPanel panel = new JPanel(new FlowLayout());
                    ButtonGroup buttonGroup = new ButtonGroup();
                    panel.setBackground(null);
                    rbYes.setBackground(null);
                    rbNo.setBackground(null);
                    buttonGroup.add(rbYes);
                    buttonGroup.add(rbNo);
                    panel.add(rbYes);
                    rbYes.addActionListener(e -> {
                        if (rbYes.isSelected()) {
                            btFaceSignUp.setEnabled(true);
                        }
                    });
                    rbNo.addActionListener(e -> {
                        if (rbNo.isSelected()) {
                            btFaceSignUp.setEnabled(false);
                        }
                    });
                    rbNo.setSelected(true);
                    panel.add(rbNo);
                    pnlCustomerConfiguration.add(panel);
                }
                case "DOSUP" -> {
                    jLabelsForm[i].setText("Ngày đăng ký: ");
                    pnlCustomerConfiguration.add(jDateChooser[1]);
                }
                default -> {
                }
            }
        }
        showImg.setLayout(new FlowLayout());
        showImg.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        showImg.setPreferredSize(new Dimension(635, 200));
        roundPanel2.add(showImg, BorderLayout.CENTER);

        BiConsumer<Button, List<Object>> configButton = (button, properties) -> {
            button.setBorder(null);
            button.setBorderPainted(false);
            button.setRadius(15);
            button.setFocusPainted(false);
            button.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            button.setBorderColor(Color.BLACK);
            button.setForeground(Color.BLACK);
            button.setText((String) properties.get(0));
            button.setColor((Color) properties.get(1));
            button.setColorOver((Color) properties.get(2));
            button.setColorClick((Color) properties.get(3));
            button.setEnabled((Boolean) properties.get(4));
            if ((Boolean) properties.get(4)) {
                button.setPreferredSize(new Dimension(150, 25));
                button.setIcon(new ImageIcon(Resource.loadImageIcon((String) properties.get(5)).getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH)));
            } else {
                button.setIcon(Resource.loadImageIcon((String) properties.get(5)));
            }
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    ((Runnable) properties.get(6)).run();
                }
            });
        };
        btFaceSignUp = new Button();
//        configButton.accept(btFaceSignUp, List.of("Đăng ký gương mặt  ", new Color(240, 240, 240), new Color(141, 222, 175), new Color(35, 166, 97), false, "img/icons/face-scanner.png", (Runnable) this::faceSignUp));
        showImg.add(btFaceSignUp);

        findCustomerByFace = new Button();
//        configButton.accept(findCustomerByFace, List.of("Tìm khách hàng  ", new Color(240, 240, 240), new Color(141, 222, 175), new Color(35, 166, 97), true, "img/icons/face-scanner.png", (Runnable) this::findCustomerByFace));
        search.add(findCustomerByFace);

        mode.setLayout(new GridLayout(2, 2, 20, 20));
        mode.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mode.setPreferredSize(new Dimension(635, 130));
        roundPanel2.add(mode, BorderLayout.SOUTH);

        if (decentralizationMode.isCanADD()) {
            Button.configButton(btAdd, List.of("  Thêm", "img/icons/plus.png", true, (Runnable) this::addCustomer));
            mode.add(btAdd);
        }
        if (decentralizationMode.isCanEDIT()) {
            Button.configButton(btUpd, List.of("  Sửa", "img/icons/wrench.png", false, (Runnable) this::updateCustomer));
            mode.add(btUpd);
        }

        if (decentralizationMode.isCanREMOVE()) {
            Button.configButton(btDel, List.of("  Xóa", "img/icons/delete.png", false, (Runnable) this::deleteCustomer));
            mode.add(btDel);
        }

        if (decentralizationMode.isCanADD()) {
            Button.configButton(btRef, List.of("  Làm mới", "img/icons/refresh.png", true, (Runnable) this::refreshForm));
            mode.add(btRef);
        } else {
            dataTable.setRowSelectionInterval(0, 0);
            fillForm();
        }
    }

    private void selectSearchFilter() {
        if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("Giới tính")) {
            txtSearch.setVisible(false);
            radiusBtMember.setVisible(false);
            radiusBtGender.setVisible(true);
            genderSearch();
        } else if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("Thành viên")) {
            txtSearch.setVisible(false);
            radiusBtGender.setVisible(false);
            radiusBtMember.setVisible(true);
            membershipSearch();
        } else {
            radiusBtGender.setVisible(false);
            radiusBtMember.setVisible(false);
            txtSearch.setVisible(true);
            searchCustomers();
        }
    }

    private void genderSearch() {
        boolean gender = rbMaleSearch.isSelected();
        loadDataTable(customerBLL.findCustomers("GENDER", gender));
    }

    private void membershipSearch() {
        boolean member = rbYesSearch.isSelected();
        loadDataTable(customerBLL.findCustomers("MEMBERSHIP", member));
    }

    public void searchCustomers() {
        if (txtSearch.getText().isEmpty()) {
            loadDataTable(customerBLL.getCustomerList());
        } else {
            String key = null;
            switch (cbbSearchFilter.getSelectedIndex()) {
                case 0 -> key = "CUSTOMER_ID";
                case 1 -> key = "NAME";
                case 3 -> key = "DOB";
                case 4 -> key = "PHONE";
                case 6 -> key = "DOSUP";
                default -> {
                }
            }
            assert key != null;
            loadDataTable(customerBLL.findCustomers(key, txtSearch.getText()));
        }
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
            else if (customerBLL.addCustomer(newCustomer))
                JOptionPane.showMessageDialog(this, "Thêm khách hàng mới thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Thêm khách hàng mới thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            refreshForm();
        }
    }

    public void updateCustomer() {
        if (checkInput()) {
            Customer newCustomer = null;
            try {
                newCustomer = getForm();
            } catch (Exception ignored) {

            }
            assert newCustomer != null;
            int selectedRow = dataTable.getSelectedRow();
            String currentPhone = dataTable.getValueAt(selectedRow, 4).toString();
            boolean valueChanged = !newCustomer.getPhone().equals(currentPhone);
            if (customerBLL.exists(newCustomer))
                JOptionPane.showMessageDialog(this, "Khách hàng đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (valueChanged && customerBLL.exists(Map.of("PHONE", newCustomer.getPhone())))
                JOptionPane.showMessageDialog(this, "Khách hàng đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (customerBLL.updateCustomer(newCustomer))
                JOptionPane.showMessageDialog(this, "Sửa khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Sửa khách hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            loadDataTable(customerBLL.getCustomerList());
            dataTable.setRowSelectionInterval(selectedRow, selectedRow);
            fillForm();
        }
    }

    private void deleteCustomer() {
        if (JOptionPane.showOptionDialog(this,
            "Bạn có chắc chắn muốn xoá khách hàng này?",
            "Xác nhận",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"Xoá", "Huỷ"},
            "Xoá") == JOptionPane.YES_OPTION) {
            Customer customer = new Customer();
            customer.setCustomerID(jTextFieldsForm[0].getText());
            if (customerBLL.deleteCustomer(customer))
                JOptionPane.showMessageDialog(this, "Xoá khách hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Xoá khách hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            refreshForm();
        }
    }

    public void refreshForm() {
        cbbSearchFilter.setSelectedIndex(0);
        txtSearch.setText(null);
        loadDataTable(customerBLL.getCustomerList());
        jTextFieldsForm[0].setText(customerBLL.getAutoID());
        for (int i = 1; i < jTextFieldsForm.length; i++) {
            jTextFieldsForm[i].setText(null);
        }
        jDateChooser[0].setDate(null);
        jDateChooser[1].setDate(null);
        rbMale.setSelected(true);
        rbYes.setSelected(true);
        btAdd.setEnabled(true);
        btUpd.setEnabled(false);
        btDel.setEnabled(false);
    }

    public void fillForm() {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        Object[] rowData = model.getDataVector().elementAt(dataTable.getSelectedRow()).toArray();
        String[] data = new String[rowData.length];
        for (int i = 0; i < rowData.length; i++) {
            data[i] = rowData[i].toString();
        }
        String[] customer = String.join(" | ", data).split(" \\| ");
        jTextFieldsForm[0].setText(customer[0]);
        jTextFieldsForm[1].setText(customer[1]);
        if (customer[2].contains("Nam")) {
            rbMale.setSelected(true);
        } else {
            rbFemale.setSelected(true);
        }
        try {
            Day date = Day.parseDay(customer[3]);
            jDateChooser[0].setDate(date.toDate());
            date = Day.parseDay(customer[6]);
            jDateChooser[1].setDate(date.toDate());
        } catch (Exception ignored) {

        }
        jTextFieldsForm[2].setText(customer[4]);
        if (customer[5].contains("Có")) {
            rbYes.setSelected(true);
        } else {
            rbNo.setSelected(true);
        }
        if (rbYes.isSelected()) {
            btFaceSignUp.setEnabled(true);
        }
        if (rbNo.isSelected()) {
            btFaceSignUp.setEnabled(false);
        }
        btAdd.setEnabled(false);
        btUpd.setEnabled(true);
        btDel.setEnabled(true);
    }

    public Customer getForm() throws Exception {
        String customerID = null;
        String name = null;
        boolean gender;
        Day dateOfBirth = null;
        String phone = null;
        boolean membership;
        Day dateOfSup = null;
        for (int i = 0; i < jTextFieldsForm.length; i++) {
            switch (i) {
                case 0 -> customerID = jTextFieldsForm[i].getText();
                case 1 -> name = jTextFieldsForm[i].getText().toUpperCase();
                case 2 -> phone = jTextFieldsForm[i].getText().replaceAll("^\\+?84", "0");
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

    public void loadDataTable(List<Customer> customerList) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);
        for (Customer customer : customerList) {
            String gender;
            String member;
            gender = customer.isGender() ? "Nam" : "Nữ";
            member = customer.isMembership() ? "Có" : "Không";
            model.addRow(new Object[]{customer.getCustomerID(), customer.getName(), gender, customer.getDateOfBirth(), customer.getPhone(), member, customer.getDateOfSup()});
        }
    }

    public boolean checkInput() {
        for (JTextField textField : jTextFieldsForm) {
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
        if (!jTextFieldsForm[1].getText().matches("^[^|]+$")) {
            // Name can't contain "|"
            jTextFieldsForm[1].requestFocusInWindow();
            jTextFieldsForm[1].selectAll();
            JOptionPane.showMessageDialog(this, "Tên khách hàng không được chứa \"|\"", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!jTextFieldsForm[2].getText().matches("^(\\+?84|0)[35789]\\d{8}$")) {
            // Phone must start with "0x" or "+84x" or "84x" where "x" in {3, 5, 7, 8, 9}
            jTextFieldsForm[2].requestFocusInWindow();
            jTextFieldsForm[2].selectAll();
            JOptionPane.showMessageDialog(this, "Số điện thoại phải bắt đầu từ \"0x\" hoặc \"+84x\" hoặc \"84x\"\nvới \"x\" thuộc {3, 5, 7, 8, 9}", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }




}
