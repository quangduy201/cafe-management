package com.cafe.GUI;

import com.cafe.BLL.StaffBLL;
import com.cafe.DTO.Staff;
import com.cafe.custom.Button;
import com.cafe.custom.DataTable;
import com.cafe.custom.RoundPanel;
import com.cafe.utils.Day;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StaffGUI extends JPanel {
    private StaffBLL staffBLL = new StaffBLL();
    private int decentralizationMode;
    private DataTable dataTable;
    private RoundPanel staff;
    private RoundPanel roundPanel1;
    private RoundPanel roundPanel2;
    private RoundPanel search;
    private JScrollPane scrollPane;
    private JPanel pnlStaffConfiguration;
    private JPanel showImg;
    private JPanel mode;
    private JPanel radiusBtGender;
    private JLabel[] jLabelsForm;
    private JComboBox<Object> cbbSearchFilter;
    private JRadioButton rbMale;
    private JRadioButton rbMaleSearch;
    private JRadioButton rbFemale;
    private JRadioButton rbFemaleSearch;
    private JTextField txtSearch;
    private JTextField[] jTextFieldsForm;
    private Button btAdd;
    private Button btUpd;
    private Button btDel;
    private Button btRef;

    public StaffGUI(int decentralizationMode) {
        this.decentralizationMode = decentralizationMode;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(70, 67, 67));
        initComponents();
    }

    public void initComponents() {
        List<String> columnNames = staffBLL.getStaffDAL().getColumnNames();
        staff = new RoundPanel();
        roundPanel1 = new RoundPanel();
        roundPanel2 = new RoundPanel();
        search = new RoundPanel();
        pnlStaffConfiguration = new JPanel();
        showImg = new JPanel();
        mode = new JPanel();
        radiusBtGender = new JPanel();
        jLabelsForm = new JLabel[columnNames.size() - 1];
        cbbSearchFilter = new JComboBox<>(new String[]{"Mã nhân viên", "Tên nhân viên", "Giới tính", "Ngày sinh", "Địa chỉ", "Điện thoại", "Email", "Lương", "Ngày vào làm"});
        rbMale = new JRadioButton("Nam", true);
        rbMaleSearch = new JRadioButton("Nam", true);
        rbFemale = new JRadioButton("Nữ");
        rbFemaleSearch = new JRadioButton("Nữ");
        txtSearch = new JTextField(20);
        jTextFieldsForm = new JTextField[columnNames.size() - 2];
        btAdd = new Button();
        btUpd = new Button();
        btDel = new Button();
        btRef = new Button();

        staff.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        staff.setBackground(new Color(70, 67, 67));
        this.add(staff, BorderLayout.CENTER);

        roundPanel1.setLayout(new BorderLayout(0, 10));
        roundPanel1.setBackground(new Color(70, 67, 67));
        roundPanel1.setPreferredSize(new Dimension(635, 680));
        roundPanel1.setAutoscrolls(true);
        roundPanel1.add(new JScrollPane(dataTable), BorderLayout.CENTER);
        staff.add(roundPanel1);

        roundPanel2.setLayout(new BorderLayout());
        roundPanel2.setBackground(new Color(0xFFFFFF));
        roundPanel2.setPreferredSize(new Dimension(350, 680));
        roundPanel2.setAutoscrolls(true);
        staff.add(roundPanel2);

        search.setLayout(new FlowLayout());
        search.setBackground(new Color(0xFFFFFF));
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

        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchStaffs();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchStaffs();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchStaffs();
            }
        });
        search.add(txtSearch);

        dataTable = new DataTable(staffBLL.getData(), new String[]{"Mã nhân viên", "Tên nhân viên", "Giới tính", "Ngày sinh", "Địa chỉ", "Điện thoại", "Email", "Lương", "Ngày vào làm"}, e -> fillForm());
        scrollPane = new JScrollPane(dataTable);
        roundPanel1.add(scrollPane);

        pnlStaffConfiguration.setLayout(new GridLayout(9, 2, 20, 20));
        pnlStaffConfiguration.setBackground(new Color(0xFFFFFF));
        pnlStaffConfiguration.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
        pnlStaffConfiguration.setPreferredSize(new Dimension(635, 450));
        roundPanel2.add(pnlStaffConfiguration, BorderLayout.NORTH);

        int index = 0;
        for (int i = 0; i < columnNames.size() - 1; i++) {
            jLabelsForm[i] = new JLabel();
            pnlStaffConfiguration.add(jLabelsForm[i]);
            switch (columnNames.get(i)) {
                case "STAFF_ID" -> {
                    jLabelsForm[i].setText("Mã nhân viên: ");
                    jTextFieldsForm[index] = new JTextField(staffBLL.getAutoID());
                    jTextFieldsForm[index].setEnabled(false);
                    jTextFieldsForm[index].setBorder(null);
                    jTextFieldsForm[index].setDisabledTextColor(new Color(0x000000));
                    pnlStaffConfiguration.add(jTextFieldsForm[index]);
                    index++;
                }
                case "NAME" -> {
                    jLabelsForm[i].setText("Tên khách hàng: ");
                    jTextFieldsForm[index] = new JTextField();
                    jTextFieldsForm[index].setText(null);
                    pnlStaffConfiguration.add(jTextFieldsForm[index]);
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
                    pnlStaffConfiguration.add(panel);
                }
                case "DOB" -> {
                    jLabelsForm[i].setText("Ngày sinh: ");
                    jTextFieldsForm[index] = new JTextField();
                    jTextFieldsForm[index].setText(null);
                    pnlStaffConfiguration.add(jTextFieldsForm[index]);
                    index++;
                }
                case "ADDRESS" -> {
                    jLabelsForm[i].setText("Địa chỉ: ");
                    jTextFieldsForm[index] = new JTextField();
                    jTextFieldsForm[index].setText(null);
                    pnlStaffConfiguration.add(jTextFieldsForm[index]);
                    index++;
                }
                case "PHONE" -> {
                    jLabelsForm[i].setText("Điện thoại: ");
                    jTextFieldsForm[index] = new JTextField();
                    jTextFieldsForm[index].setText(null);
                    pnlStaffConfiguration.add(jTextFieldsForm[index]);
                    index++;
                }
                case "EMAIL" -> {
                    jLabelsForm[i].setText("Email: ");
                    jTextFieldsForm[index] = new JTextField();
                    jTextFieldsForm[index].setText(null);
                    pnlStaffConfiguration.add(jTextFieldsForm[index]);
                    index++;
                }
                case "SALARY" -> {
                    jLabelsForm[i].setText("Lương: ");
                    jTextFieldsForm[index] = new JTextField();
                    jTextFieldsForm[index].setText(null);
                    pnlStaffConfiguration.add(jTextFieldsForm[index]);
                    index++;
                }
                case "DOENTRY" -> {
                    jLabelsForm[i].setText("Ngày vào làm: ");
                    jTextFieldsForm[index] = new JTextField();
                    jTextFieldsForm[index].setText(null);
                    pnlStaffConfiguration.add(jTextFieldsForm[index]);
                    index++;
                }
                default -> {
                }
            }
        }
        showImg.setLayout(new FlowLayout());
        showImg.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        showImg.setPreferredSize(new Dimension(635, 100));
        showImg.setBackground(new Color(0xFFFFFF));
        roundPanel2.add(showImg, BorderLayout.CENTER);

        if (decentralizationMode > 1) {
            mode.setLayout(new GridLayout(2, 2, 20, 20));
            mode.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            mode.setBackground(new Color(0xFFFFFF));
            mode.setPreferredSize(new Dimension(635, 130));
            roundPanel2.add(mode, BorderLayout.SOUTH);

            btAdd.setBackground(new Color(35, 166, 97));
            btAdd.setBorder(null);
            btAdd.setIcon(new ImageIcon("img/plus.png"));
            btAdd.setText("  Thêm");
            btAdd.setColor(new Color(240, 240, 240));
            btAdd.setColorClick(new Color(141, 222, 175));
            btAdd.setColorOver(new Color(35, 166, 97));
            btAdd.setFocusPainted(false);
            btAdd.setRadius(20);
            btAdd.setEnabled(true);
            btAdd.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (btAdd.isEnabled()) {
                        addStaff();
                    }
                }
            });
            mode.add(btAdd);
        }

        if (decentralizationMode == 3) {
            btUpd.setBackground(new Color(35, 166, 97));
            btUpd.setBorder(null);
            btUpd.setIcon(new ImageIcon("img/wrench.png"));
            btUpd.setText("  Sửa");
            btUpd.setColor(new Color(240, 240, 240));
            btUpd.setColorClick(new Color(141, 222, 175));
            btUpd.setColorOver(new Color(35, 166, 97));
            btUpd.setFocusPainted(false);
            btUpd.setRadius(20);
            btUpd.setEnabled(false);
            btUpd.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (btUpd.isEnabled()) {
                        updateStaff();
                    }
                }
            });
            mode.add(btUpd);

            btDel.setBackground(new Color(35, 166, 97));
            btDel.setBorder(null);
            btDel.setIcon(new ImageIcon("img/delete.png"));
            btDel.setText("  Xoá");
            btDel.setColor(new Color(240, 240, 240));
            btDel.setColorClick(new Color(141, 222, 175));
            btDel.setColorOver(new Color(35, 166, 97));
            btDel.setFocusPainted(false);
            btDel.setRadius(20);
            btDel.setEnabled(false);
            btDel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (btDel.isEnabled()) {
                        deleteStaff();
                    }
                }
            });
            mode.add(btDel);
        }

        if (decentralizationMode > 1) {
            btRef.setBackground(new Color(35, 166, 97));
            btRef.setBorder(null);
            btRef.setIcon(new ImageIcon("img/refresh.png"));
            btRef.setText("  Làm mới");
            btRef.setColor(new Color(240, 240, 240));
            btRef.setColorClick(new Color(141, 222, 175));
            btRef.setColorOver(new Color(35, 166, 97));
            btRef.setFocusPainted(false);
            btRef.setRadius(20);
            btRef.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent mouseEvent) {
                    refreshForm();
                }
            });
            mode.add(btRef);
        } else {
            dataTable.setRowSelectionInterval(0, 0);
            fillForm();
        }
    }

    private void selectSearchFilter() {
        if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("Giới tính")) {
            txtSearch.setVisible(false);
            radiusBtGender.setVisible(true);
            genderSearch();
        } else {
            radiusBtGender.setVisible(false);
            txtSearch.setVisible(true);
            searchStaffs();
        }
    }

    private void genderSearch() {
        boolean gender = rbMaleSearch.isSelected();
        loadDataTable(staffBLL.findStaffs("GENDER", gender));
    }

    public void searchStaffs() {
        if (txtSearch.getText().isEmpty()) {
            loadDataTable(staffBLL.getStaffList());
        } else {
            String key = null;
            switch (cbbSearchFilter.getSelectedIndex()){
                case 0 -> key = "STAFF_ID";
                case 1 -> key = "NAME";
                case 3 -> key = "DOB";
                case 4 -> key = "ADDRESS";
                case 5 -> key = "PHONE";
                case 6 -> key = "EMAIL";
                case 7 -> key = "SALARY";
                case 8 -> key = "DOENTRY";
                default -> {
                }
            }
            assert key != null;
            loadDataTable(staffBLL.findStaffs(key, txtSearch.getText()));
        }
    }

    public void addStaff() {
        if (checkInput()) {
            Staff newStaff = null;
            try {
                newStaff = getForm();
            } catch (Exception ignored) {

            }
            assert newStaff != null;
            if (staffBLL.exists(newStaff))
                JOptionPane.showMessageDialog(this, "Staff already existed!", "Error", JOptionPane.ERROR_MESSAGE);
            else if (staffBLL.exists(Map.of("PHONE", newStaff.getPhone())))
                JOptionPane.showMessageDialog(this, "Staff already existed!", "Error", JOptionPane.ERROR_MESSAGE);
            else if (staffBLL.addStaff(newStaff))
                JOptionPane.showMessageDialog(this, "Successfully added new staff!", "Notification", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Failed to add new staff!", "Error", JOptionPane.ERROR_MESSAGE);
            refreshForm();
        }
    }

    public void updateStaff() {
        if (checkInput()) {
            Staff newStaff = null;
            try {
                newStaff = getForm();
            } catch (Exception ignored) {

            }
            assert newStaff != null;
            int selectedRow = dataTable.getSelectedRow();
            String currentPhone = dataTable.getValueAt(selectedRow, 5).toString();
            boolean valueChanged = !newStaff.getPhone().equals(currentPhone);
            if (staffBLL.exists(newStaff))
                JOptionPane.showMessageDialog(this, "Staff already existed!", "Error", JOptionPane.ERROR_MESSAGE);
            else if (valueChanged && staffBLL.exists(Map.of("PHONE", newStaff.getPhone())))
                JOptionPane.showMessageDialog(this, "Staff already existed!", "Error", JOptionPane.ERROR_MESSAGE);
            else if (staffBLL.updateStaff(newStaff))
                JOptionPane.showMessageDialog(this, "Successfully updated staff!", "Notification", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Failed to update staff!", "Error", JOptionPane.ERROR_MESSAGE);
            loadDataTable(staffBLL.getStaffList());
            dataTable.setRowSelectionInterval(selectedRow, selectedRow);
            fillForm();
        }
    }

    private void deleteStaff() {
        if (JOptionPane.showConfirmDialog(this, "Are you sure to delete this staff?",
            "Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            Staff staff = new Staff();
            staff.setStaffID(jTextFieldsForm[0].getText());
            if (staffBLL.deleteStaff(staff))
                JOptionPane.showMessageDialog(this, "Successfully deleted staff!", "Notification", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Failed to delete staff!", "Error", JOptionPane.ERROR_MESSAGE);
            refreshForm();
        }
    }

    public void refreshForm() {
        cbbSearchFilter.setSelectedIndex(0);
        txtSearch.setText(null);
        loadDataTable(staffBLL.getStaffList());
        jTextFieldsForm[0].setText(staffBLL.getAutoID());
        for (int i = 1; i < jTextFieldsForm.length; i++) {
            jTextFieldsForm[i].setText(null);
        }
        rbMale.setSelected(true);
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
        String[] staff = String.join(" | ", data).split(" \\| ");
        jTextFieldsForm[0].setText(staff[0]);
        jTextFieldsForm[1].setText(staff[1]);
        if (staff[2].contains("Nam")) {
            rbMale.setSelected(true);
        } else {
            rbFemale.setSelected(true);
        }
        jTextFieldsForm[2].setText(staff[3]);
        jTextFieldsForm[3].setText(staff[4]);
        jTextFieldsForm[4].setText(staff[5]);
        jTextFieldsForm[5].setText(staff[6]);
        jTextFieldsForm[6].setText(String.format("%.1f VND", Double.parseDouble(staff[7])));
        jTextFieldsForm[7].setText(staff[8]);
        btAdd.setEnabled(false);
        btUpd.setEnabled(true);
        btDel.setEnabled(true);
    }

    public Staff getForm() throws Exception {
        String staffID = null;
        String name = null;
        boolean gender;
        Day dateOfBirth = null;
        String address = null;
        String phone = null;
        String email = null;
        double salary = 0;
        Day dateOfEntry = null;
        for (int i = 0; i < jTextFieldsForm.length; i++) {
            switch (i) {
                case 0 -> staffID = jTextFieldsForm[i].getText();
                case 1 -> name = jTextFieldsForm[i].getText().toUpperCase();
                case 2 -> dateOfBirth = Day.parseDay(jTextFieldsForm[i].getText().replaceAll("/", "-"));
                case 3 -> address = jTextFieldsForm[i].getText();
                case 4 -> phone = jTextFieldsForm[i].getText().replaceAll("^\\+?84", "0");
                case 5 -> email = jTextFieldsForm[i].getText();
                case 6 -> salary = Double.parseDouble(jTextFieldsForm[i].getText().replaceAll("(\\s|VND|VNĐ)", ""));
                case 7 -> dateOfEntry = Day.parseDay(jTextFieldsForm[i].getText().replaceAll("/", "-"));
                default -> {
                }
            }
        }
        gender = rbMale.isSelected();
        return new Staff(staffID, name, gender, dateOfBirth, address, phone, email, salary, dateOfEntry, false);
    }

    public void loadDataTable(List<Staff> staffList) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);
        for (Staff staff : staffList) {
            String gender;
            gender = staff.isGender() ? "Nam" : "Nữ";
            model.addRow(new Object[]{staff.getStaffID(), staff.getName(), gender, staff.getDateOfBirth(), staff.getAddress(), staff.getPhone(), staff.getEmail(), staff.getSalary(), staff.getDateOfEntry()});
        }
    }

    public boolean checkInput() {
        for (JTextField textField : jTextFieldsForm) {
            if (textField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in information!", "Error", JOptionPane.ERROR_MESSAGE);
                textField.requestFocusInWindow();
                return false;
            }
        }
        if (!jTextFieldsForm[1].getText().matches("^[^|]+$")) {
            // Name can't contain "|"
            jTextFieldsForm[1].requestFocusInWindow();
            jTextFieldsForm[1].selectAll();
            JOptionPane.showMessageDialog(this, "Name can't contain \"|\"", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            if (!jTextFieldsForm[2].getText().matches("^\\d{4}([-/])(0?[1-9]|1[0-2])\\1(0?[1-9]|[12][0-9]|3[01])$")) {
                // Date must follow yyyy-MM-dd or yyyy/MM/dd
                throw new Exception();
            }
            Day.parseDay(jTextFieldsForm[2].getText().replaceAll("/", "-"));
        } catch (Exception exception) {
            jTextFieldsForm[2].requestFocusInWindow();
            jTextFieldsForm[2].selectAll();
            JOptionPane.showMessageDialog(this, "Date of birth must follow one of these patterns:\n\"yyyy-MM-dd\"\n\"yyyy/MM/dd\"", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!jTextFieldsForm[3].getText().matches("^[^|]+$")) {
            // Address can't contain "|"
            jTextFieldsForm[3].requestFocusInWindow();
            jTextFieldsForm[3].selectAll();
            JOptionPane.showMessageDialog(this, "Address can't contain \"|\"", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!jTextFieldsForm[4].getText().matches("^(\\+?84|0)[35789]\\d{8}$")) {
            // Phone must start with "0x" or "+84x" or "84x" where "x" in {3, 5, 7, 8, 9}
            jTextFieldsForm[4].requestFocusInWindow();
            jTextFieldsForm[4].selectAll();
            JOptionPane.showMessageDialog(this, "Phone must start with \"0x\" or \"+84x\" or \"84x\"\nwhere \"x\" in {3, 5, 7, 8, 9}", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!jTextFieldsForm[5].getText().matches("^\\w+(\\.\\w+)*@\\w+(\\.\\w+)+")) {
            // Email must follow "username@domain.name"
            jTextFieldsForm[5].requestFocusInWindow();
            jTextFieldsForm[5].selectAll();
            JOptionPane.showMessageDialog(this, "Email must follow the pattern \"username@domain.name\"", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!jTextFieldsForm[6].getText().matches("^(?=.*\\d)\\d*\\.?\\d*\\s*(VND|VNĐ)$")) {
            // Salary must be a double >= 0
            jTextFieldsForm[6].requestFocusInWindow();
            jTextFieldsForm[6].selectAll();
            JOptionPane.showMessageDialog(this, "Salary must be a non-negative real number", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            if (!jTextFieldsForm[7].getText().matches("^\\d{4}([-/])(0?[1-9]|1[0-2])\\1(0?[1-9]|[12][0-9]|3[01])$")) {
                // Date must follow yyyy-MM-dd or yyyy/MM/dd
                throw new Exception();
            }
            Day.parseDay(jTextFieldsForm[7].getText().replaceAll("/", "-"));
        } catch (Exception exception) {
            jTextFieldsForm[7].requestFocusInWindow();
            jTextFieldsForm[7].selectAll();
            JOptionPane.showMessageDialog(this, "Date of entry must follow one of these patterns:\n\"yyyy-MM-dd\"\n\"yyyy/MM/dd\"", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
