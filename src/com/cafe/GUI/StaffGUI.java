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
import java.awt.event.*;
import java.util.List;
import java.util.Objects;

public class StaffGUI extends JPanel {
    private StaffBLL staffBLL = new StaffBLL();
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
    private JLabel jLabelsForm[];
    private JComboBox<Object> cbbSearchFilter;
    private JRadioButton rbMale;
    private JRadioButton rbMaleSearch;
    private JRadioButton rbFemale;
    private JRadioButton rbFemaleSearch;
    private JTextField txtSearch;
    private JTextField jTextFieldsForm[];
    private com.cafe.custom.Button btAdd;
    private com.cafe.custom.Button btUpd;
    private com.cafe.custom.Button btDel;
    private Button btRef;
    public StaffGUI() {
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
        cbbSearchFilter = new JComboBox<>(columnNames.subList(0, columnNames.size() - 1).toArray());
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
        staff.setBackground(new Color(51, 51, 51));
        this.add(staff, BorderLayout.CENTER);

        roundPanel1.setLayout(new BorderLayout(0, 10));
        roundPanel1.setBackground(new Color(51, 51, 51));
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

        cbbSearchFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectSearchFilter();
            }
        });
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
        rbMaleSearch.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                genderSearch();
            }
        });
        rbFemaleSearch.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                genderSearch();
            }
        });
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

        dataTable = new DataTable(staffBLL.getData(), columnNames.subList(0, columnNames.size() - 1).toArray(), getListSelectionListener());
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
            jLabelsForm[i].setText(columnNames.get(i) + ": ");
            pnlStaffConfiguration.add(jLabelsForm[i]);
            switch (columnNames.get(i)) {
                case "STAFF_ID" -> {
                    jTextFieldsForm[index] = new JTextField(staffBLL.getAutoID());
                    jTextFieldsForm[index].setEnabled(false);
                    jTextFieldsForm[index].setBorder(null);
                    jTextFieldsForm[index].setDisabledTextColor(new Color(0x000000));
                    pnlStaffConfiguration.add(jTextFieldsForm[index]);
                    index++;
                }
                case "GENDER" -> {
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
                default -> {
                    jTextFieldsForm[index] = new JTextField();
                    jTextFieldsForm[index].setText(null);
                    pnlStaffConfiguration.add(jTextFieldsForm[index]);
                    index++;
                }
            }
        }
        showImg.setLayout(new FlowLayout());
        showImg.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        showImg.setPreferredSize(new Dimension(635, 100));
        showImg.setBackground(new Color(0xFFFFFF));
        roundPanel2.add(showImg, BorderLayout.CENTER);

        mode.setLayout(new GridLayout(2, 2, 20, 20));
        mode.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mode.setBackground(new Color(0xFFFFFF));
        mode.setPreferredSize(new Dimension(635, 130));
        roundPanel2.add(mode, BorderLayout.SOUTH);

        btAdd.setBackground(new Color(35, 166, 97));
        btAdd.setBorder(null);
        btAdd.setIcon(new ImageIcon("img/plus.png"));
        btAdd.setText("  Add");
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
                    try {
                        addStaff();
                    } catch (Exception ignored) {

                    }
                }
            }
        });
        mode.add(btAdd);

        btUpd.setBackground(new Color(35, 166, 97));
        btUpd.setBorder(null);
        btUpd.setIcon(new ImageIcon("img/wrench.png"));
        btUpd.setText("  Update");
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
                    try {
                        updateStaff();
                    } catch (Exception ignored) {
                    }
                }
            }
        });
        mode.add(btUpd);

        btDel.setBackground(new Color(35, 166, 97));
        btDel.setBorder(null);
        btDel.setIcon(new ImageIcon("img/delete.png"));
        btDel.setText("  Delete");
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

        btRef.setBackground(new Color(35, 166, 97));
        btRef.setBorder(null);
        btRef.setIcon(new ImageIcon("img/refresh.png"));
        btRef.setText("  Refresh");
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
    }

    private void selectSearchFilter() {
        if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("GENDER")) {
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

    public ActionListener getListSelectionListener() {
        return e -> {
            DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
            String rowData = model.getDataVector().elementAt(dataTable.getSelectedRow()).toString();
            String[] staff = rowData.substring(1, rowData.length() - 1).split(", ");
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
            jTextFieldsForm[6].setText(staff[7]);
            jTextFieldsForm[7].setText(staff[8]);
            btAdd.setEnabled(false);
            btUpd.setEnabled(true);
            btDel.setEnabled(true);
        };
    }

    public void searchStaffs() {
        if (txtSearch.getText().isEmpty()) {
            loadDataTable(staffBLL.getStaffList());
        } else {
            loadDataTable(staffBLL.findStaffs(Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString(), txtSearch.getText()));
        }
    }

    public void addStaff() throws Exception {
        if (checkInput()) {
            Staff newStaff;
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
                    case 1 -> name = jTextFieldsForm[i].getText();
                    case 2 -> dateOfBirth = Day.parseDay(jTextFieldsForm[i].getText());
                    case 3 -> address = jTextFieldsForm[i].getText();
                    case 4 -> phone = jTextFieldsForm[i].getText();
                    case 5 -> email = jTextFieldsForm[i].getText();
                    case 6 -> salary = Double.parseDouble(jTextFieldsForm[i].getText());
                    case 7 -> dateOfEntry = Day.parseDay(jTextFieldsForm[i].getText());
                    default -> {
                    }
                }
            }
            gender = rbMale.isSelected();
            newStaff = new Staff(staffID, name, gender, dateOfBirth, address, phone, email, salary, dateOfEntry, false);
            staffBLL.addStaff(newStaff);
            refreshForm();
        }
    }

    public void updateStaff() throws Exception {
        if (checkInput()) {
            Staff newStaff;
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
                    case 1 -> name = jTextFieldsForm[i].getText();
                    case 2 -> dateOfBirth = Day.parseDay(jTextFieldsForm[i].getText());
                    case 3 -> address = jTextFieldsForm[i].getText();
                    case 4 -> phone = jTextFieldsForm[i].getText();
                    case 5 -> email = jTextFieldsForm[i].getText();
                    case 6 -> salary = Double.parseDouble(jTextFieldsForm[i].getText());
                    case 7 -> dateOfEntry = Day.parseDay(jTextFieldsForm[i].getText());
                    default -> {
                    }
                }
            }
            gender = rbMale.isSelected();
            newStaff = new Staff(staffID, name, gender, dateOfBirth, address, phone, email, salary, dateOfEntry, false);
            staffBLL.updateStaff(newStaff);
            loadDataTable(staffBLL.getStaffList());
        }
    }

    private void deleteStaff() {
        Staff staff = new Staff();
        staff.setStaffID(jTextFieldsForm[0].getText());
        staffBLL.deleteStaff(staff);
        refreshForm();
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
                System.out.println(textField.getText());
                JOptionPane.showMessageDialog(this, "Please fill in information!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
        }
        try {
            Day.parseDay(jTextFieldsForm[2].getText());
        } catch (Exception exception) {
            jTextFieldsForm[2].setText(null);
            JOptionPane.showMessageDialog(this, "yyyy-mm-dd", "Notification", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(jTextFieldsForm[4].getText());
        } catch (Exception exception) {
            jTextFieldsForm[4].setText(null);
            JOptionPane.showMessageDialog(this, "invalided data input!", "Notification", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        if (!jTextFieldsForm[5].getText().contains("@gmail.com")) {
            jTextFieldsForm[5].setText(null);
            JOptionPane.showMessageDialog(this, "Invalid data input!", "Notification", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        try {
            Double.parseDouble(jTextFieldsForm[6].getText());
        } catch (NumberFormatException exception) {
            jTextFieldsForm[6].setText(null);
            JOptionPane.showMessageDialog(this, "invalided data input!", "Notification", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        try {
            Day.parseDay(jTextFieldsForm[7].getText());
        } catch (Exception exception) {
            jTextFieldsForm[7].setText(null);
            JOptionPane.showMessageDialog(this, "yyyy-mm-dd", "Notification", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
}
