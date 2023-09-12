package com.cafe.GUI;

import com.cafe.BLL.StaffBLL;
import com.cafe.DTO.DecentralizationDetail;
import com.cafe.DTO.Staff;
import com.cafe.custom.Button;
import com.cafe.custom.DataTable;
import com.cafe.custom.RoundPanel;
import com.cafe.utils.Day;
import com.cafe.utils.VNString;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StaffGUI extends JPanel {
    private StaffBLL staffBLL = new StaffBLL();
    private DecentralizationDetail decentralizationMode;
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
    private JDateChooser[] jDateChooser;
    private JTextField[] dateTextField;
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

    public StaffGUI(DecentralizationDetail decentralizationMode) {
        System.gc();
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
        jDateChooser = new JDateChooser[2];
        dateTextField = new JTextField[2];
        cbbSearchFilter = new JComboBox<>(new String[]{"Mã nhân viên", "Tên nhân viên", "Giới tính", "Ngày sinh", "Địa chỉ", "Điện thoại", "Email", "Lương", "Ngày vào làm"});
        rbMale = new JRadioButton("Nam", true);
        rbMaleSearch = new JRadioButton("Nam", true);
        rbFemale = new JRadioButton("Nữ");
        rbFemaleSearch = new JRadioButton("Nữ");
        txtSearch = new JTextField(20);
        jTextFieldsForm = new JTextField[columnNames.size() - 4];
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
        roundPanel2.setPreferredSize(new Dimension(350, 680));
        roundPanel2.setAutoscrolls(true);
        staff.add(roundPanel2);

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
        pnlStaffConfiguration.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
        pnlStaffConfiguration.setPreferredSize(new Dimension(635, 450));
        roundPanel2.add(pnlStaffConfiguration, BorderLayout.NORTH);

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
            pnlStaffConfiguration.add(jLabelsForm[i]);
            switch (columnNames.get(i)) {
                case "STAFF_ID" -> {
                    jLabelsForm[i].setText("Mã nhân viên: ");
                    jTextFieldsForm[index] = new JTextField(staffBLL.getAutoID());
                    jTextFieldsForm[index].setEnabled(false);
                    jTextFieldsForm[index].setBorder(null);
                    jTextFieldsForm[index].setDisabledTextColor(null);
                    pnlStaffConfiguration.add(jTextFieldsForm[index]);
                    index++;
                }
                case "NAME" -> {
                    jLabelsForm[i].setText("Tên nhân viên: ");
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
                    pnlStaffConfiguration.add(jDateChooser[0]);
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
                    jTextFieldsForm[index].addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            char c = e.getKeyChar();
                            if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != '+') {
                                e.consume();
                            }
                        }
                    });
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
                    jTextFieldsForm[index].addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            char c = e.getKeyChar();
                            if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                                e.consume();
                            }
                        }
                    });
                    pnlStaffConfiguration.add(jTextFieldsForm[index]);
                    index++;
                }
                case "DOENTRY" -> {
                    jLabelsForm[i].setText("Ngày vào làm: ");
                    pnlStaffConfiguration.add(jDateChooser[1]);
                }
                default -> {
                }
            }
        }
        showImg.setLayout(new FlowLayout());
        showImg.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        showImg.setPreferredSize(new Dimension(635, 100));
        roundPanel2.add(showImg, BorderLayout.CENTER);

        mode.setLayout(new GridLayout(2, 2, 20, 20));
        mode.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mode.setPreferredSize(new Dimension(635, 130));
        roundPanel2.add(mode, BorderLayout.SOUTH);

        if (decentralizationMode.isCanADD()) {
            Button.configButton(btAdd, List.of("  Thêm", "img/icons/plus.png", true, (Runnable) this::addStaff));
            mode.add(btAdd);
        }

        if (decentralizationMode.isCanEDIT()) {
            Button.configButton(btUpd, List.of("  Sửa", "img/icons/wrench.png", false, (Runnable) this::updateStaff));
            mode.add(btUpd);
        }

        if (decentralizationMode.isCanREMOVE()) {
            Button.configButton(btDel, List.of("  Xóa", "img/icons/delete.png", false, (Runnable) this::deleteStaff));
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
            switch (cbbSearchFilter.getSelectedIndex()) {
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
                JOptionPane.showMessageDialog(this, "Nhân viên đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (staffBLL.exists(Map.of("PHONE", newStaff.getPhone())))
                JOptionPane.showMessageDialog(this, "Nhân viên đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (staffBLL.addStaff(newStaff))
                JOptionPane.showMessageDialog(this, "Thêm nhân viên mới thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Thêm nhân viên mới thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(this, "Nhân viên đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (valueChanged && staffBLL.exists(Map.of("PHONE", newStaff.getPhone())))
                JOptionPane.showMessageDialog(this, "Nhân viên đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (staffBLL.updateStaff(newStaff))
                JOptionPane.showMessageDialog(this, "Sửa nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Sửa nhân viên thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            loadDataTable(staffBLL.getStaffList());
            dataTable.setRowSelectionInterval(selectedRow, selectedRow);
            fillForm();
        }
    }

    private void deleteStaff() {
        if (JOptionPane.showOptionDialog(this,
            "Bạn có chắc chắn muốn xoá nhân viên này?",
            "Xác nhận",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"Xoá", "Huỷ"},
            "Xoá") == JOptionPane.YES_OPTION) {
            Staff staff = new Staff();
            staff.setStaffID(jTextFieldsForm[0].getText());
            if (staffBLL.deleteStaff(staff))
                JOptionPane.showMessageDialog(this, "Xoá nhân viên thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Xoá nhân viên thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
        jDateChooser[0].setDate(null);
        jDateChooser[1].setDate(null);
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
        jTextFieldsForm[2].setText(staff[4]);
        jTextFieldsForm[3].setText(staff[5]);
        jTextFieldsForm[4].setText(staff[6]);
        jTextFieldsForm[5].setText(VNString.currency(Double.parseDouble(staff[7])));
        try {
            Day date = Day.parseDay(staff[3]);
            jDateChooser[0].setDate(date.toDate());
            date = Day.parseDay(staff[8]);
            jDateChooser[1].setDate(date.toDate());
        } catch (Exception ignored) {

        }
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
                case 2 -> address = jTextFieldsForm[i].getText();
                case 3 -> phone = jTextFieldsForm[i].getText().replaceAll("^\\+?84", "0");
                case 4 -> email = jTextFieldsForm[i].getText();
                case 5 -> salary = Double.parseDouble(jTextFieldsForm[i].getText().replaceAll("\\D+", ""));
                default -> {
                }
            }
        }
        dateOfBirth = new Day(jDateChooser[0].getDate());
        dateOfEntry = new Day(jDateChooser[1].getDate());
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
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                textField.requestFocusInWindow();
                return false;
            }
        }
        if (!jTextFieldsForm[1].getText().matches("^[^|]+$")) {
            // Name can't contain "|"
            jTextFieldsForm[1].requestFocusInWindow();
            jTextFieldsForm[1].selectAll();
            JOptionPane.showMessageDialog(this, "Tên nhân viên không thể chứa \"|\"", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!jTextFieldsForm[2].getText().matches("^[^|]+$")) {
            // Address can't contain "|"
            jTextFieldsForm[2].requestFocusInWindow();
            jTextFieldsForm[2].selectAll();
            JOptionPane.showMessageDialog(this, "Địa chỉ không được chứa \"|\"", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!jTextFieldsForm[3].getText().matches("^(\\+?84|0)[35789]\\d{8}$")) {
            // Phone must start with "0x" or "+84x" or "84x" where "x" in {3, 5, 7, 8, 9}
            jTextFieldsForm[3].requestFocusInWindow();
            jTextFieldsForm[3].selectAll();
            JOptionPane.showMessageDialog(this, "Số điện thoại phải bắt đầu từ \"0x\" hoặc \"+84x\" hoặc \"84x\"\nvới \"x\" thuộc {3, 5, 7, 8, 9}", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!jTextFieldsForm[4].getText().matches("^\\w+(\\.\\w+)*@\\w+(\\.\\w+)+")) {
            // Email must follow "username@domain.name"
            jTextFieldsForm[4].requestFocusInWindow();
            jTextFieldsForm[4].selectAll();
            JOptionPane.showMessageDialog(this, "Email phải theo định dạng \"username@domain.name\"", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
