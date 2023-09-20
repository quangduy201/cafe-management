package com.cafe.GUI;

import com.cafe.BLL.AccountBLL;
import com.cafe.BLL.DecentralizationBLL;
import com.cafe.BLL.StaffBLL;
import com.cafe.DTO.Account;
import com.cafe.DTO.Decentralization;
import com.cafe.DTO.DecentralizationDetail;
import com.cafe.DTO.Staff;
import com.cafe.custom.Button;
import com.cafe.custom.DataTable;
import com.cafe.custom.RoundPanel;
import com.cafe.utils.OTP;
import com.cafe.utils.Password;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AccountGUI extends JPanel {
    private AccountBLL accountBLL = new AccountBLL();
    private DecentralizationDetail decentralizationMode;
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

    private RoundPanel rpPassWord;
    private Button btpassWord;

    public AccountGUI(DecentralizationDetail decentralizationMode) {
        System.gc();
        this.decentralizationMode = decentralizationMode;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(70, 67, 67));
        initComponents();
    }
    private List<Decentralization> decentralizationList = new ArrayList<>();
    private List<Staff> staffList = new ArrayList<>();

    public void initComponents() {
        List<String> columnNames = accountBLL.getAccountDAL().getColumnNames();
        DecentralizationBLL decentralizationBLL = new DecentralizationBLL();
        StaffBLL staffBLL = new StaffBLL();
        List<Object> decentralizationName = decentralizationBLL.getObjectsProperty("DECENTRALIZATION_NAME", decentralizationBLL.getDecentralizationList());
        List<Object> staffsName = staffBLL.getObjectsProperty("NAME", staffBLL.getStaffList());
        decentralizationList = new DecentralizationBLL().getDecentralizationList();
        staffList = new StaffBLL().getStaffList();

        account = new RoundPanel();
        roundPanel1 = new RoundPanel();
        roundPanel2 = new RoundPanel();
        search = new RoundPanel();
        pnlAccountConfiguration = new JPanel();
        showImg = new JPanel();
        mode = new JPanel();
        jLabelsForm = new JLabel[columnNames.size() - 1];
        cbbSearchFilter = new JComboBox<>(new String[]{"Mã tài khoản", "Tên đăng nhập", "Tên quyền", "Tên nhân viên"});
        cbbDecentralizationID = new JComboBox<>(decentralizationName.toArray());
        cbbDecentralizationIDSearch = new JComboBox<>(decentralizationName.toArray());
        cbbStaffID = new JComboBox<>(staffsName.toArray());
        cbbStaffIDSearch = new JComboBox<>(staffsName.toArray());
        txtSearch = new JTextField(20);
        jTextFieldsForm = new JTextField[columnNames.size() - 3];
        btAdd = new Button();
        btUpd = new Button();
        btDel = new Button();
        btRef = new Button();
        rpPassWord = new RoundPanel();
        btpassWord = new Button();

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
        roundPanel2.setPreferredSize(new Dimension(350, 680));
        roundPanel2.setAutoscrolls(true);
        account.add(roundPanel2);

        search.setLayout(new FlowLayout());
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

        dataTable = new DataTable(null, new String[]{"Mã tài khoản", "Tên đăng nhập", "Tên quyền", "Tên nhân viên"}, e -> fillForm());
        scrollPane = new JScrollPane(dataTable);
        roundPanel1.add(scrollPane);

        pnlAccountConfiguration.setLayout(new GridLayout(5, 2, 20, 20));
        pnlAccountConfiguration.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
        pnlAccountConfiguration.setPreferredSize(new Dimension(635, 250));
        roundPanel2.add(pnlAccountConfiguration, BorderLayout.NORTH);

        for (int i = 0; i < columnNames.size() - 1; i++) {
            switch (columnNames.get(i)) {
                case "ACCOUNT_ID" -> {
                    jLabelsForm[i] = new JLabel();
                    pnlAccountConfiguration.add(jLabelsForm[i]);
                    jLabelsForm[i].setText("Mã tài khoản: ");
                    jTextFieldsForm[i] = new JTextField(accountBLL.getAutoID());
                    jTextFieldsForm[i].setEnabled(false);
                    jTextFieldsForm[i].setBorder(null);
                    jTextFieldsForm[i].setDisabledTextColor(null);
                    pnlAccountConfiguration.add(jTextFieldsForm[i]);
                }
                case "USERNAME" -> {
                    jLabelsForm[i] = new JLabel();
                    pnlAccountConfiguration.add(jLabelsForm[i]);
                    jLabelsForm[i].setText("Tên đăng nhập: ");
                    jTextFieldsForm[i] = new JTextField();
                    jTextFieldsForm[i].setText(null);
                    pnlAccountConfiguration.add(jTextFieldsForm[i]);
                }
                case "DECENTRALIZATION_ID" -> {
                    jLabelsForm[i] = new JLabel();
                    pnlAccountConfiguration.add(jLabelsForm[i]);
                    jLabelsForm[i].setText("Tên quyền: ");
                    pnlAccountConfiguration.add(cbbDecentralizationID);
                }
                case "STAFF_ID" -> {
                    jLabelsForm[i] = new JLabel();
                    pnlAccountConfiguration.add(jLabelsForm[i]);
                    jLabelsForm[i].setText("Tên nhân viên: ");
                    pnlAccountConfiguration.add(cbbStaffID);
                }
                default -> {
                }
            }
        }

//
//        btpassWord.setText("Đặt lại mật khẩu");
//        btpassWord.setPreferredSize(new Dimension(200, 50));
//        rpPassWord.add(btpassWord);

        rpPassWord.setLayout(new BorderLayout());
        rpPassWord.setPreferredSize(new Dimension(635, 300));
        rpPassWord.setAutoscrolls(true);
        roundPanel2.add(rpPassWord, BorderLayout.CENTER);

        mode.setLayout(new GridLayout(2, 2, 20, 20));
        mode.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mode.setPreferredSize(new Dimension(635, 130));
        roundPanel2.add(mode, BorderLayout.SOUTH);

        if (decentralizationMode.isCanADD()) {
            Button.configButton(btAdd, List.of("  Thêm", "img/icons/plus.png", true, (Runnable) this::addAccount));
            mode.add(btAdd);
        }

        if (decentralizationMode.isCanEDIT()) {
            Button.configButton(btUpd, List.of("  Sửa", "img/icons/wrench.png", false, (Runnable) this::updateAccount));
            mode.add(btUpd);
        }

        if (decentralizationMode.isCanREMOVE()) {
            Button.configButton(btDel, List.of("  Xóa", "img/icons/delete.png", false, (Runnable) this::deleteAccount));
            mode.add(btDel);
        }

        if (decentralizationMode.isCanADD()) {
            Button.configButton(btRef, List.of("  Làm mới", "img/icons/refresh.png", true, (Runnable) this::refreshForm));
            mode.add(btRef);
        } else {
            dataTable.setRowSelectionInterval(0, 0);
            fillForm();
        }
        loadDataTable(accountBLL.getAccountList());
    }

    private void decentralizationIDSearch() {
        for(Decentralization decentralization : decentralizationList) {
            if (decentralization.getDecentralizationName().equals(cbbDecentralizationIDSearch.getSelectedItem())) {
                loadDataTable(accountBLL.findAccounts("DECENTRALIZATION_ID", Objects.requireNonNull(decentralization.getDecentralizationID())));
            }
        }
    }

    private void staffIDSearch() {
        for(Staff staff : staffList) {
            if (staff.getName().equals(cbbStaffIDSearch.getSelectedItem())) {
                loadDataTable(accountBLL.findAccounts("STAFF_ID", Objects.requireNonNull(staff.getStaffID())));
            }
        }
    }

    private void selectSearchFilter() {
        if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("Tên quyền")) {
            txtSearch.setVisible(false);
            cbbStaffIDSearch.setVisible(false);
            cbbDecentralizationIDSearch.setSelectedIndex(0);
            cbbDecentralizationIDSearch.setVisible(true);
            decentralizationIDSearch();
        } else if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("Tên nhân viên")) {
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
            switch (cbbSearchFilter.getSelectedIndex()) {
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
            String randomPassword = Password.generateRandomPassword(8);
            newAccount.setPassword("first" + Password.hashPassword(randomPassword));
            if (accountBLL.exists(newAccount))
                JOptionPane.showMessageDialog(this, "Tài khoản đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (accountBLL.exists(Map.of("USERNAME", newAccount.getUsername())))
                JOptionPane.showMessageDialog(this, "Tên tài khoản đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (accountBLL.addAccount(newAccount)) {
                JOptionPane.showMessageDialog(this, "Thêm tài khoản mới thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                Staff staff = new StaffBLL().findStaffsBy(Map.of("STAFF_ID", newAccount.getStaffID())).get(0);
                OTP.sendPassword(staff.getEmail(), randomPassword);
            }
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
        for (int i = 1; i < jTextFieldsForm.length - 1; i++) {
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
        cbbDecentralizationID.setSelectedItem(account[2]);
        cbbStaffID.setSelectedItem(account[3]);
        btAdd.setEnabled(false);
        btUpd.setEnabled(true);
        btDel.setEnabled(true);
    }

    public Account getForm() {
        String accountID = null;
        String username = null;
        String password = null;
        String decentralizationID = null;
        String staffID = null;
        for (int i = 0; i < jTextFieldsForm.length; i++) {
            switch (i) {
                case 0 -> accountID = jTextFieldsForm[i].getText();
                case 1 -> username = jTextFieldsForm[i].getText();
//                case 2 -> password = jTextFieldsForm[i].getText();
                default -> {
                }
            }
        }
        for (Staff staff : staffList) {
            if (staff.getName().equals(cbbStaffID.getSelectedItem())) {
                staffID = staff.getStaffID();
            }
        }

        for (Decentralization decentralization : decentralizationList) {
            if (decentralization.getDecentralizationName().equals(cbbDecentralizationID.getSelectedItem())) {
                decentralizationID = decentralization.getDecentralizationID();
            }
        }
        return new Account(accountID, username, password, decentralizationID, staffID, false);
    }

    private String decentralizationName;
    private String staffName;
    public void loadDataTable(List<Account> accountList) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);
        for (Account account : accountList) {
            for (Decentralization decentralization: decentralizationList) {
                if (decentralization.getDecentralizationID().equals(account.getDecentralizationID())) {
                    decentralizationName = decentralization.getDecentralizationName();
                }
            }
            for (Staff staff: staffList) {
                if (staff.getStaffID().equals(account.getStaffID())) {
                    staffName = staff.getName();
                }
            }
            model.addRow(new Object[]{account.getAccountID(), account.getUsername(), decentralizationName, staffName});
        }
    }

    public boolean checkInput() {
        for (int i = 0; i < jTextFieldsForm.length - 1; ++i) {
            if (jTextFieldsForm[i].getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                jTextFieldsForm[i].requestFocusInWindow();
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
        return true;
    }
}
