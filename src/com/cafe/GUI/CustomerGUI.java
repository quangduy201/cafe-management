package com.cafe.GUI;

import com.cafe.BLL.CustomerBLL;
import com.cafe.DTO.Customer;
import com.cafe.custom.Button;
import com.cafe.custom.DataTable;
import com.cafe.custom.RoundPanel;
import com.cafe.utils.Day;
import com.cafe.utils.Tasks;

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

public class CustomerGUI extends JPanel {
    private CustomerBLL customerBLL = new CustomerBLL();
    private int decentralizationMode;
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

    public CustomerGUI(int decentralizationMode) {
        this.decentralizationMode = decentralizationMode;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(70, 67, 67));
        initComponents();
    }

    public void initComponents() {
        List<String> columnNames = customerBLL.getCustomerDAL().getColumnNames();
        findCustomerByFace = new Button();
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
        cbbSearchFilter = new JComboBox<>(columnNames.subList(0, columnNames.size() - 1).toArray());
        rbMale = new JRadioButton("Nam", true);
        rbMaleSearch = new JRadioButton("Nam", true);
        rbFemale = new JRadioButton("Nữ");
        rbFemaleSearch = new JRadioButton("Nữ");
        rbYes = new JRadioButton("Có", true);
        rbYesSearch = new JRadioButton("Có", true);
        rbNo = new JRadioButton("Không");
        rbNoSearch = new JRadioButton("Không");
        txtSearch = new JTextField(20);
        jTextFieldsForm = new JTextField[columnNames.size() - 3];
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
        roundPanel2.setBackground(new Color(0xFFFFFF));
        roundPanel2.setPreferredSize(new Dimension(350, 680));
        roundPanel2.setAutoscrolls(true);
        customer.add(roundPanel2);

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

        dataTable = new DataTable(customerBLL.getData(), columnNames.subList(0, columnNames.size() - 1).toArray(), e -> fillForm());
        scrollPane = new JScrollPane(dataTable);
        roundPanel1.add(scrollPane);

        pnlCustomerConfiguration.setLayout(new GridLayout(7, 2, 20, 20));
        pnlCustomerConfiguration.setBackground(new Color(0xFFFFFF));
        pnlCustomerConfiguration.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
        pnlCustomerConfiguration.setPreferredSize(new Dimension(635, 350));
        roundPanel2.add(pnlCustomerConfiguration, BorderLayout.NORTH);

        int index = 0;
        for (int i = 0; i < columnNames.size() - 1; i++) {
            jLabelsForm[i] = new JLabel();
            jLabelsForm[i].setText(columnNames.get(i) + ": ");
            pnlCustomerConfiguration.add(jLabelsForm[i]);
            switch (columnNames.get(i)) {
                case "CUSTOMER_ID" -> {
                    jTextFieldsForm[index] = new JTextField(customerBLL.getAutoID());
                    jTextFieldsForm[index].setEnabled(false);
                    jTextFieldsForm[index].setBorder(null);
                    jTextFieldsForm[index].setDisabledTextColor(new Color(0x000000));
                    pnlCustomerConfiguration.add(jTextFieldsForm[index]);
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
                    pnlCustomerConfiguration.add(panel);
                }
                case "MEMBERSHIP" -> {
                    JPanel panel = new JPanel(new FlowLayout());
                    ButtonGroup buttonGroup = new ButtonGroup();
                    panel.setBackground(null);
                    rbYes.setBackground(null);
                    rbNo.setBackground(null);
                    buttonGroup.add(rbYes);
                    buttonGroup.add(rbNo);
                    panel.add(rbYes);
                    panel.add(rbNo);
                    pnlCustomerConfiguration.add(panel);
                }
                default -> {
                    jTextFieldsForm[index] = new JTextField();
                    jTextFieldsForm[index].setText(null);
                    pnlCustomerConfiguration.add(jTextFieldsForm[index]);
                    index++;
                }
            }
        }
        showImg.setLayout(new FlowLayout());
        showImg.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        showImg.setPreferredSize(new Dimension(635, 200));
        showImg.setBackground(new Color(0xFFFFFF));
        roundPanel2.add(showImg, BorderLayout.CENTER);

        btFaceSignUp = new Button();
        btFaceSignUp.setBackground(new Color(35, 166, 97));
        btFaceSignUp.setBorder(null);
        btFaceSignUp.setIcon(new ImageIcon("img/face-scanner.png"));
        btFaceSignUp.setText("Sign up your face  ");
        btFaceSignUp.setColor(new Color(240, 240, 240));
        btFaceSignUp.setColorClick(new Color(141, 222, 175));
        btFaceSignUp.setColorOver(new Color(35, 166, 97));
        btFaceSignUp.setFocusPainted(false);
        btFaceSignUp.setRadius(20);
        btFaceSignUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                faceSignUp();
            }
        });
        showImg.add(btFaceSignUp);


        findCustomerByFace.setBackground(new Color(35, 166, 97));
        findCustomerByFace.setBorder(null);
        findCustomerByFace.setIcon(new ImageIcon(new ImageIcon("img/face-scanner.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        findCustomerByFace.setText("Find Customer  ");
        findCustomerByFace.setPreferredSize(new Dimension(150, 25));
        findCustomerByFace.setColor(new Color(240, 240, 240));
        findCustomerByFace.setColorClick(new Color(141, 222, 175));
        findCustomerByFace.setColorOver(new Color(35, 166, 97));
        findCustomerByFace.setFocusPainted(false);
        findCustomerByFace.setRadius(20);
        findCustomerByFace.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                faceSignUp();
            }
        });
        search.add(findCustomerByFace);

        if (decentralizationMode > 1) {
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
                        addCustomer();
                    }
                }
            });
            mode.add(btAdd);
        }

        if (decentralizationMode == 3) {
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
                        updateCustomer();
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
                        deleteCustomer();
                    }
                }
            });
            mode.add(btDel);
        }

        if (decentralizationMode > 1) {
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
        } else {
            dataTable.setRowSelectionInterval(0, 0);
            fillForm();
        }
    }

    private void selectSearchFilter() {
        if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("GENDER")) {
            txtSearch.setVisible(false);
            radiusBtMember.setVisible(false);
            radiusBtGender.setVisible(true);
            genderSearch();
        } else if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("MEMBERSHIP")) {
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
            loadDataTable(customerBLL.findCustomers(Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString(), txtSearch.getText()));
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
                JOptionPane.showMessageDialog(this, "Customer already existed!", "Error", JOptionPane.ERROR_MESSAGE);
            else if (customerBLL.exists(Map.of("PHONE", newCustomer.getPhone())))
                JOptionPane.showMessageDialog(this, "Customer already existed!", "Error", JOptionPane.ERROR_MESSAGE);
            else if (customerBLL.addCustomer(newCustomer))
                JOptionPane.showMessageDialog(this, "Successfully added new customer!", "Notification", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Failed to add new customer!", "Error", JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(this, "Customer already existed!", "Error", JOptionPane.ERROR_MESSAGE);
            else if (valueChanged && customerBLL.exists(Map.of("PHONE", newCustomer.getPhone())))
                JOptionPane.showMessageDialog(this, "Customer already existed!", "Error", JOptionPane.ERROR_MESSAGE);
            else if (customerBLL.updateCustomer(newCustomer))
                JOptionPane.showMessageDialog(this, "Successfully updated customer!", "Notification", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Failed to update customer!", "Error", JOptionPane.ERROR_MESSAGE);
            loadDataTable(customerBLL.getCustomerList());
            dataTable.setRowSelectionInterval(selectedRow, selectedRow);
            fillForm();
        }
    }

    private void deleteCustomer() {
        if (JOptionPane.showConfirmDialog(this, "Are you sure to delete this customer?",
            "Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            Customer customer = new Customer();
            customer.setCustomerID(jTextFieldsForm[0].getText());
            if (customerBLL.deleteCustomer(customer))
                JOptionPane.showMessageDialog(this, "Successfully deleted customer!", "Notification", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Failed to delete customer!", "Error", JOptionPane.ERROR_MESSAGE);
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
        jTextFieldsForm[2].setText(customer[3]);
        jTextFieldsForm[3].setText(customer[4]);
        if (customer[5].contains("Có")) {
            rbYes.setSelected(true);
        } else {
            rbNo.setSelected(true);
        }
        jTextFieldsForm[4].setText(customer[6]);
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
                case 2 -> dateOfBirth = Day.parseDay(jTextFieldsForm[i].getText().replaceAll("/", "-"));
                case 3 -> phone = jTextFieldsForm[i].getText().replaceAll("^\\+?84", "0");
                case 4 -> dateOfSup = Day.parseDay(jTextFieldsForm[i].getText().replaceAll("/", "-"));
                default -> {
                }
            }
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
        if (!jTextFieldsForm[3].getText().matches("^(\\+?84|0)[35789]\\d{8}$")) {
            // Phone must start with "0x" or "+84x" or "84x" where "x" in {3, 5, 7, 8, 9}
            jTextFieldsForm[3].requestFocusInWindow();
            jTextFieldsForm[3].selectAll();
            JOptionPane.showMessageDialog(this, "Phone must start with \"0x\" or \"+84x\" or \"84x\"\nwhere \"x\" in {3, 5, 7, 8, 9}", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            if (!jTextFieldsForm[4].getText().matches("^\\d{4}([-/])(0?[1-9]|1[0-2])\\1(0?[1-9]|[12][0-9]|3[01])$")) {
                // Date must follow yyyy-MM-dd or yyyy/MM/dd
                throw new Exception();
            }
            Day.parseDay(jTextFieldsForm[4].getText().replaceAll("/", "-"));
        } catch (Exception exception) {
            jTextFieldsForm[4].requestFocusInWindow();
            jTextFieldsForm[4].selectAll();
            JOptionPane.showMessageDialog(this, "Date of sign up must follow one of these patterns:\n\"yyyy-MM-dd\"\n\"yyyy/MM/dd\"", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public void faceSignUp() {
        Tasks tasks = new Tasks("Camera");
        new Thread(() -> tasks.recordAndTrain(jTextFieldsForm[0].getText(), 80)).start();
    }

}
