package com.cafe.GUI;

import com.cafe.BLL.AccountBLL;
import com.cafe.BLL.DecentralizationBLL;
import com.cafe.BLL.StaffBLL;
import com.cafe.DTO.Account;
import com.cafe.custom.Button;
import com.cafe.custom.DataTable;
import com.cafe.custom.RoundPanel;

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

public class AccountGUI extends JPanel {
    private AccountBLL accountBLL = new AccountBLL();
    private int decentralizationMode;
    private DataTable dataTable;
    private RoundPanel account;
    private RoundPanel roundPanel1;
    private RoundPanel roundPanel2;
    private RoundPanel search;
    private JScrollPane scrollPane;
    private JPanel pnlAccountConfiguration;
    private JPanel showImg;
    private JPanel mode;
    private JLabel[] jLabelsForm;
    private JComboBox<Object> cbbSearchFilter;
    private JComboBox<Object> cbbDecentralizationID;
    private JComboBox<Object> cbbDecentralizationIDSearch;
    private JComboBox<Object> cbbStaffID;
    private JComboBox<Object> cbbStaffIDSearch;
    private JTextField txtSearch;
    private JTextField[] jTextFieldsForm;
    private Button btAdd;
    private Button btUpd;
    private Button btDel;
    private Button btRef;

    public AccountGUI(int decentralizationMode) {
        this.decentralizationMode = decentralizationMode;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(70, 67, 67));
        initComponents();
    }

    public void initComponents() {
        List<String> columnNames = accountBLL.getAccountDAL().getColumnNames();
        DecentralizationBLL decentralizationBLL = new DecentralizationBLL();
        StaffBLL staffBLL = new StaffBLL();
        List<Object> decentralizationsID = decentralizationBLL.getObjectsProperty("DECENTRALIZATION_ID", decentralizationBLL.getDecentralizationList());
        List<Object> staffsID = staffBLL.getObjectsProperty("STAFF_ID", staffBLL.getStaffList());

        account = new RoundPanel();
        roundPanel1 = new RoundPanel();
        roundPanel2 = new RoundPanel();
        search = new RoundPanel();
        pnlAccountConfiguration = new JPanel();
        showImg = new JPanel();
        mode = new JPanel();
        jLabelsForm = new JLabel[columnNames.size() - 1];
        cbbSearchFilter = new JComboBox<>(new String[]{"Mã tài khoản", "Tên đăng nhập", "Mật khẩu", "Mã quyền", "Mã nhân viên"});
        cbbDecentralizationID = new JComboBox<>(decentralizationsID.toArray());
        cbbDecentralizationIDSearch = new JComboBox<>(decentralizationsID.toArray());
        cbbStaffID = new JComboBox<>(staffsID.toArray());
        cbbStaffIDSearch = new JComboBox<>(staffsID.toArray());
        txtSearch = new JTextField(20);
        jTextFieldsForm = new JTextField[columnNames.size() - 3];
        btAdd = new Button();
        btUpd = new Button();
        btDel = new Button();
        btRef = new Button();

        account.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        account.setBackground(new Color(70, 67, 67));
        this.add(account, BorderLayout.CENTER);

        roundPanel1.setLayout(new BorderLayout(0, 10));
        roundPanel1.setBackground(new Color(70, 67, 67));
        roundPanel1.setPreferredSize(new Dimension(635, 680));
        roundPanel1.setAutoscrolls(true);
        roundPanel1.add(new JScrollPane(dataTable), BorderLayout.CENTER);
        account.add(roundPanel1);

        roundPanel2.setLayout(new BorderLayout());
        roundPanel2.setBackground(new Color(0xFFFFFF));
        roundPanel2.setPreferredSize(new Dimension(350, 680));
        roundPanel2.setAutoscrolls(true);
        account.add(roundPanel2);

        search.setLayout(new FlowLayout());
        search.setBackground(new Color(0xFFFFFF));
        search.setPreferredSize(new Dimension(635, 35));
        roundPanel1.add(search, BorderLayout.NORTH);


        cbbSearchFilter.addActionListener(e -> selectSearchFilter());
        search.add(cbbSearchFilter);
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchAccounts();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchAccounts();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchAccounts();
            }
        });
        search.add(txtSearch);
        cbbDecentralizationIDSearch.setVisible(false);
        cbbDecentralizationIDSearch.addItemListener(e -> decentralizationIDSearch());
        search.add(cbbDecentralizationIDSearch);
        cbbStaffIDSearch.setVisible(false);
        cbbStaffIDSearch.addItemListener(e -> staffIDSearch());
        search.add(cbbStaffIDSearch);

        dataTable = new DataTable(accountBLL.getData(), new String[]{"Mã tài khoản", "Tên đăng nhập", "Mật khẩu", "Mã quyền", "Mã nhân viên"}, e -> fillForm());
        scrollPane = new JScrollPane(dataTable);
        roundPanel1.add(scrollPane);

        pnlAccountConfiguration.setLayout(new GridLayout(5, 2, 20, 20));
        pnlAccountConfiguration.setBackground(new Color(0xFFFFFF));
        pnlAccountConfiguration.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
        pnlAccountConfiguration.setPreferredSize(new Dimension(635, 250));
        roundPanel2.add(pnlAccountConfiguration, BorderLayout.NORTH);

        for (int i = 0; i < columnNames.size() - 1; i++) {
            jLabelsForm[i] = new JLabel();
            pnlAccountConfiguration.add(jLabelsForm[i]);
            switch (columnNames.get(i)) {
                case "ACCOUNT_ID" -> {
                    jLabelsForm[i].setText("Mã tài khoản: ");
                    jTextFieldsForm[i] = new JTextField(accountBLL.getAutoID());
                    jTextFieldsForm[i].setEnabled(false);
                    jTextFieldsForm[i].setBorder(null);
                    jTextFieldsForm[i].setDisabledTextColor(new Color(0x000000));
                    pnlAccountConfiguration.add(jTextFieldsForm[i]);
                }
                case "USERNAME" -> {
                    jLabelsForm[i].setText("Tên đăng nhập: ");
                    jTextFieldsForm[i] = new JTextField();
                    jTextFieldsForm[i].setText(null);
                    pnlAccountConfiguration.add(jTextFieldsForm[i]);
                }
                case "PASSWD" -> {
                    jLabelsForm[i].setText("Mật khẩu: ");
                    jTextFieldsForm[i] = new JTextField();
                    jTextFieldsForm[i].setText(null);
                    pnlAccountConfiguration.add(jTextFieldsForm[i]);
                }
                case "DECENTRALIZATION_ID" -> {
                    jLabelsForm[i].setText("Mã quyền: ");
                    pnlAccountConfiguration.add(cbbDecentralizationID);
                }
                case "STAFF_ID" -> {
                    jLabelsForm[i].setText("Mã nhân viên: ");
                    pnlAccountConfiguration.add(cbbStaffID);
                }
                default -> {
                }
            }
        }

        showImg.setLayout(new FlowLayout());
        showImg.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        showImg.setPreferredSize(new Dimension(635, 300));
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
                        addAccount();
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
                        updateAccount();
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
                        deleteAccount();
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

    private void decentralizationIDSearch() {
        loadDataTable(accountBLL.findAccounts("DECENTRALIZATION_ID", Objects.requireNonNull(cbbDecentralizationIDSearch.getSelectedItem()).toString()));
    }

    private void staffIDSearch() {
        loadDataTable(accountBLL.findAccounts("STAFF_ID", Objects.requireNonNull(cbbStaffIDSearch.getSelectedItem()).toString()));
    }

    private void selectSearchFilter() {
        if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("Mã quyền")) {
            txtSearch.setVisible(false);
            cbbStaffIDSearch.setVisible(false);
            cbbDecentralizationIDSearch.setSelectedIndex(0);
            cbbDecentralizationIDSearch.setVisible(true);
            decentralizationIDSearch();
        } else if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("Mã nhân viên")) {
            txtSearch.setVisible(false);
            cbbDecentralizationIDSearch.setVisible(false);
            cbbStaffIDSearch.setSelectedIndex(0);
            cbbStaffIDSearch.setVisible(true);
            staffIDSearch();
        } else {
            cbbStaffIDSearch.setVisible(false);
            cbbDecentralizationIDSearch.setVisible(false);
            txtSearch.setVisible(true);
            searchAccounts();
        }
    }

    public void searchAccounts() {
        if (txtSearch.getText().isEmpty()) {
            loadDataTable(accountBLL.getAccountList());
        } else {
            String key = null;
            switch (cbbSearchFilter.getSelectedIndex()){
                case 0 -> key = "ACCOUNT_ID";
                case 1 -> key = "USERNAME";
                case 2 -> key = "PASSWD";
                default -> {
                }
            }
            assert key != null;
            loadDataTable(accountBLL.findAccounts(key, txtSearch.getText()));
        }
    }

    public void addAccount() {
        if (checkInput()) {
            Account newAccount = getForm();
            if (accountBLL.exists(newAccount))
                JOptionPane.showMessageDialog(this, "Tài khoản đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (accountBLL.exists(Map.of("USERNAME", newAccount.getUsername())))
                JOptionPane.showMessageDialog(this, "Tên tài khoản đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (accountBLL.addAccount(newAccount))
                JOptionPane.showMessageDialog(this, "Thêm tài khoản mới thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Thêm tài khoản mới không thành công!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            refreshForm();
        }
    }

    public void updateAccount() {
        if (checkInput()) {
            Account newAccount = getForm();
            int selectedRow = dataTable.getSelectedRow();
            String currentUsername = dataTable.getValueAt(selectedRow, 1).toString();
            boolean valueChanged = !newAccount.getUsername().equals(currentUsername);
            if (accountBLL.exists(newAccount))
                JOptionPane.showMessageDialog(this, "Tài khoản đã tồn tại!!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (valueChanged && accountBLL.exists(Map.of("USERNAME", newAccount.getUsername())))
                JOptionPane.showMessageDialog(this, "Tên tài khoản đã tồn tại!!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (accountBLL.updateAccount(newAccount))
                JOptionPane.showMessageDialog(this, "Sửa tài khoản thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Sửa tài khoản không thành công!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            loadDataTable(accountBLL.getAccountList());
            dataTable.changeSelection(selectedRow, 0, true, false);
            fillForm();
        }
    }

    private void deleteAccount() {
        if (JOptionPane.showOptionDialog(this,
            "Bạn có chắc chắn muốn xoá tài khoản này?",
            "Xác nhận",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"Xoá", "Huỷ"},
            "Xoá") == JOptionPane.YES_OPTION) {
            Account account = new Account();
            account.setAccountID(jTextFieldsForm[0].getText());
            if (accountBLL.deleteAccount(account))
                JOptionPane.showMessageDialog(this, "Xoá tài khoản thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Xoá tài khoản không thành công!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            refreshForm();
        }
    }

    public void refreshForm() {
        cbbSearchFilter.setSelectedIndex(0);
        txtSearch.setText(null);
        loadDataTable(accountBLL.getAccountList());
        jTextFieldsForm[0].setText(accountBLL.getAutoID());
        for (int i = 1; i < jTextFieldsForm.length; i++) {
            jTextFieldsForm[i].setText(null);
        }
        cbbDecentralizationID.setSelectedIndex(0);
        cbbStaffID.setSelectedIndex(0);
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
        String[] account = String.join(" | ", data).split(" \\| ");
        jTextFieldsForm[0].setText(account[0]);
        jTextFieldsForm[1].setText(account[1]);
        jTextFieldsForm[2].setText(account[2]);
        cbbDecentralizationID.setSelectedItem(account[3]);
        cbbStaffID.setSelectedItem(account[4]);
        btAdd.setEnabled(false);
        btUpd.setEnabled(true);
        btDel.setEnabled(true);
    }

    public Account getForm() {
        String accountID = null;
        String username = null;
        String password = null;
        String decentralizationID;
        String staffID;
        for (int i = 0; i < jTextFieldsForm.length; i++) {
            switch (i) {
                case 0 -> accountID = jTextFieldsForm[i].getText();
                case 1 -> username = jTextFieldsForm[i].getText();
                case 2 -> password = jTextFieldsForm[i].getText();
                default -> {
                }
            }
        }
        decentralizationID = Objects.requireNonNull(cbbDecentralizationID.getSelectedItem()).toString();
        staffID = Objects.requireNonNull(cbbStaffID.getSelectedItem()).toString();
        return new Account(accountID, username, password, decentralizationID, staffID, false);
    }

    public void loadDataTable(List<Account> accountList) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);
        for (Account account : accountList) {
            model.addRow(new Object[]{account.getAccountID(), account.getUsername(), account.getPassword(), account.getDecentralizationID(), account.getStaffID()});
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
        if (!jTextFieldsForm[1].getText().matches("^[^\\s|]{3,32}$")) {
            // Username can't contain " " or "|"
            jTextFieldsForm[1].requestFocusInWindow();
            jTextFieldsForm[1].selectAll();
            JOptionPane.showMessageDialog(this, "Tên tài khoản không được chứa \" \" or \"|\"", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!jTextFieldsForm[2].getText().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[^\\s|]{3,32}$")) {
            // Password can't contain " " or "|"
            // Password must contain at lease 1 lower-case, 1 upper-case and 1 number
            jTextFieldsForm[2].requestFocusInWindow();
            jTextFieldsForm[2].selectAll();
            JOptionPane.showMessageDialog(this, "Mật khẩu không được chứa \" \" or \"|\"\nMật khẩu phải chứa ít nhất 1 chữ cái thường, 1 chữ cái hoa and 1 chữ số", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
