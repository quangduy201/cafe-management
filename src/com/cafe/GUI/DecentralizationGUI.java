package com.cafe.GUI;

import com.cafe.BLL.AccountBLL;
import com.cafe.BLL.DecentralizationBLL;
import com.cafe.DTO.Account;
import com.cafe.DTO.Decentralization;
import com.cafe.custom.Button;
import com.cafe.custom.DataTable;
import com.cafe.custom.RoundPanel;
import com.cafe.main.CafeManagement;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DecentralizationGUI extends JPanel {
    private DecentralizationBLL decentralizationBLL = new DecentralizationBLL();
    private AccountBLL accountBLL = new AccountBLL();
    private int decentralizationMode;
    private DataTable dataTable;
    private RoundPanel decentralization;
    private RoundPanel roundPanel1;
    private RoundPanel roundPanel2;
    private RoundPanel search;
    private JScrollPane scrollPane;
    private JPanel pnlDecentralizationConfiguration;
    private JPanel mode;
    private JLabel[] jLabelsForm;
    private JComboBox<Object> cbbSearchFilter;
    private JComboBox<Object>[] jComboBoxForm;
    private JComboBox<Object> jComboBoxSearch;
    private JTextField txtSearch;
    private JTextField[] jTextFieldsForm;
    private Button btAdd;
    private Button btUpd;
    private Button btDel;
    private Button btRef;

    public DecentralizationGUI(int decentralizationMode) {
        System.gc();
        this.decentralizationMode = decentralizationMode;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(70, 67, 67));
        initComponents();
    }

    public void initComponents() {
        List<String> columnNames = decentralizationBLL.getDecentralizationDAL().getColumnNames();
        decentralization = new RoundPanel();
        roundPanel1 = new RoundPanel();
        roundPanel2 = new RoundPanel();
        search = new RoundPanel();
        pnlDecentralizationConfiguration = new JPanel();
        mode = new JPanel();
        jLabelsForm = new JLabel[columnNames.size() - 1];
        cbbSearchFilter = new JComboBox<>(new String[]{"Mã quyền", "Bán hàng", "Sản phẩm", "Thể loại", "Công thức", "Nhập hàng", "Nhà cung cấp", "Hoá đơn", "Nhà kho", "Tài khoản", "Nhân viên", "Khách hàng", "Giảm giá", "Phân quyền", "Tên quyền"});
        jComboBoxForm = new JComboBox[columnNames.size() - 3];
        jComboBoxSearch = new JComboBox<>(new String[]{"Không", "Xem", "Thêm", "Sửa và xóa"});
        txtSearch = new JTextField(20);
        jTextFieldsForm = new JTextField[2];
        btAdd = new Button();
        btUpd = new Button();
        btDel = new Button();
        btRef = new Button();

        decentralization.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        decentralization.setBackground(new Color(70, 67, 67));
        this.add(decentralization, BorderLayout.CENTER);

        roundPanel1.setLayout(new BorderLayout(0, 10));
        roundPanel1.setBackground(new Color(70, 67, 67));
        roundPanel1.setPreferredSize(new Dimension(635, 680));
        roundPanel1.setAutoscrolls(true);
        roundPanel1.add(new JScrollPane(dataTable), BorderLayout.CENTER);
        decentralization.add(roundPanel1);

        roundPanel2.setLayout(new BorderLayout());
        roundPanel2.setBackground(new Color(0xFFFFFF));
        roundPanel2.setPreferredSize(new Dimension(350, 680));
        roundPanel2.setAutoscrolls(true);
        decentralization.add(roundPanel2);

        search.setLayout(new FlowLayout());
        search.setBackground(new Color(0xFFFFFF));
        search.setPreferredSize(new Dimension(635, 35));
        roundPanel1.add(search, BorderLayout.NORTH);

        cbbSearchFilter.addActionListener(e -> selectSearchFilter());
        search.add(cbbSearchFilter);
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchDecentralizations();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchDecentralizations();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchDecentralizations();
            }
        });
        search.add(txtSearch);
        jComboBoxSearch.setVisible(false);
        jComboBoxSearch.addItemListener(e -> comboboxSearch());
        search.add(jComboBoxSearch);

        dataTable = new DataTable(decentralizationBLL.getData(), new String[]{"Mã quyền", "Bán hàng", "Sản phẩm", "Thể loại", "Công thức", "Nhập hàng", "Nhà cung cấp", "Hoá đơn", "Nhà kho", "Tài khoản", "Nhân viên", "Khách hàng", "Giảm giá", "Phân quyền", "Tên quyền"}, e -> fillForm());
        scrollPane = new JScrollPane(dataTable);
        roundPanel1.add(scrollPane);

        JScrollPane jScrollPane = new JScrollPane(pnlDecentralizationConfiguration);
        jScrollPane.setPreferredSize(new Dimension(600, 550));
        pnlDecentralizationConfiguration.setLayout(new GridLayout(16, 2, 20, 20));
        pnlDecentralizationConfiguration.setBackground(new Color(0xFFFFFF));
        pnlDecentralizationConfiguration.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
        pnlDecentralizationConfiguration.setPreferredSize(new Dimension(jScrollPane.getViewport().getWidth(), 700));
        roundPanel2.add(jScrollPane, BorderLayout.NORTH);

        int index1 = 0;
        int index2 = 0;
        for (int i = 0; i < columnNames.size() - 1; i++) {
            jLabelsForm[i] = new JLabel();
            pnlDecentralizationConfiguration.add(jLabelsForm[i]);
            switch (columnNames.get(i)) {
                case "DECENTRALIZATION_ID" -> {
                    jLabelsForm[i].setText("Mã quyền: ");
                    jTextFieldsForm[index1] = new JTextField(decentralizationBLL.getAutoID());
                    jTextFieldsForm[index1].setEnabled(false);
                    jTextFieldsForm[index1].setBorder(null);
                    jTextFieldsForm[index1].setDisabledTextColor(new Color(0x000000));
                    pnlDecentralizationConfiguration.add(jTextFieldsForm[index1]);
                    index1++;
                }
                case "IS_SALE" -> {
                    jLabelsForm[i].setText("Bán hàng: ");
                    jComboBoxForm[index2] = new JComboBox<>(new String[]{"Không", "Xem", "Thêm", "Sửa và xóa"});
                    jComboBoxForm[index2].setSelectedIndex(0);
                    pnlDecentralizationConfiguration.add(jComboBoxForm[index2]);
                    index2++;
                }
                case "IS_PRODUCT" -> {
                    jLabelsForm[i].setText("Sản phẩm: ");
                    jComboBoxForm[index2] = new JComboBox<>(new String[]{"Không", "Xem", "Thêm", "Sửa và xóa"});
                    jComboBoxForm[index2].setSelectedIndex(0);
                    pnlDecentralizationConfiguration.add(jComboBoxForm[index2]);
                    index2++;
                }
                case "IS_CATEGORY" -> {
                    jLabelsForm[i].setText("Thể loại: ");
                    jComboBoxForm[index2] = new JComboBox<>(new String[]{"Không", "Xem", "Thêm", "Sửa và xóa"});
                    jComboBoxForm[index2].setSelectedIndex(0);
                    pnlDecentralizationConfiguration.add(jComboBoxForm[index2]);
                    index2++;
                }
                case "IS_RECIPE" -> {
                    jLabelsForm[i].setText("Công thức: ");
                    jComboBoxForm[index2] = new JComboBox<>(new String[]{"Không", "Xem", "Thêm", "Sửa và xóa"});
                    jComboBoxForm[index2].setSelectedIndex(0);
                    pnlDecentralizationConfiguration.add(jComboBoxForm[index2]);
                    index2++;
                }
                case "IS_IMPORT" -> {
                    jLabelsForm[i].setText("Nhập hàng: ");
                    jComboBoxForm[index2] = new JComboBox<>(new String[]{"Không", "Xem", "Thêm", "Sửa và xóa"});
                    jComboBoxForm[index2].setSelectedIndex(0);
                    pnlDecentralizationConfiguration.add(jComboBoxForm[index2]);
                    index2++;
                }
                case "IS_SUPPLIER" -> {
                    jLabelsForm[i].setText("Nhà cung cấp: ");
                    jComboBoxForm[index2] = new JComboBox<>(new String[]{"Không", "Xem", "Thêm", "Sửa và xóa"});
                    jComboBoxForm[index2].setSelectedIndex(0);
                    pnlDecentralizationConfiguration.add(jComboBoxForm[index2]);
                    index2++;
                }
                case "IS_BILL" -> {
                    jLabelsForm[i].setText("Hoá đơn: ");
                    jComboBoxForm[index2] = new JComboBox<>(new String[]{"Không", "Xem", "Thêm", "Sửa và xóa"});
                    jComboBoxForm[index2].setSelectedIndex(0);
                    pnlDecentralizationConfiguration.add(jComboBoxForm[index2]);
                    index2++;
                }
                case "IS_WAREHOUSES" -> {
                    jLabelsForm[i].setText("Nhà kho: ");
                    jComboBoxForm[index2] = new JComboBox<>(new String[]{"Không", "Xem", "Thêm", "Sửa và xóa"});
                    jComboBoxForm[index2].setSelectedIndex(0);
                    pnlDecentralizationConfiguration.add(jComboBoxForm[index2]);
                    index2++;
                }
                case "IS_ACCOUNT" -> {
                    jLabelsForm[i].setText("Tài khoản: ");
                    jComboBoxForm[index2] = new JComboBox<>(new String[]{"Không", "Xem", "Thêm", "Sửa và xóa"});
                    jComboBoxForm[index2].setSelectedIndex(0);
                    pnlDecentralizationConfiguration.add(jComboBoxForm[index2]);
                    index2++;
                }
                case "IS_STAFF" -> {
                    jLabelsForm[i].setText("Nhân viên: ");
                    jComboBoxForm[index2] = new JComboBox<>(new String[]{"Không", "Xem", "Thêm", "Sửa và xóa"});
                    jComboBoxForm[index2].setSelectedIndex(0);
                    pnlDecentralizationConfiguration.add(jComboBoxForm[index2]);
                    index2++;
                }
                case "IS_CUSTOMER" -> {
                    jLabelsForm[i].setText("Khách hàng: ");
                    jComboBoxForm[index2] = new JComboBox<>(new String[]{"Không", "Xem", "Thêm", "Sửa và xóa"});
                    jComboBoxForm[index2].setSelectedIndex(0);
                    pnlDecentralizationConfiguration.add(jComboBoxForm[index2]);
                    index2++;
                }
                case "IS_DISCOUNT" -> {
                    jLabelsForm[i].setText("Giảm giá: ");
                    jComboBoxForm[index2] = new JComboBox<>(new String[]{"Không", "Xem", "Thêm", "Sửa và xóa"});
                    jComboBoxForm[index2].setSelectedIndex(0);
                    pnlDecentralizationConfiguration.add(jComboBoxForm[index2]);
                    index2++;
                }
                case "IS_DECENTRALIZE" -> {
                    jLabelsForm[i].setText("Phân quyền: ");
                    jComboBoxForm[index2] = new JComboBox<>(new String[]{"Không", "Xem", "Thêm", "Sửa và xóa"});
                    jComboBoxForm[index2].setSelectedIndex(0);
                    pnlDecentralizationConfiguration.add(jComboBoxForm[index2]);
                    index2++;
                }
                case "DECENTRALIZATION_NAME" -> {
                    jLabelsForm[i].setText("Tên quyền: ");
                    jTextFieldsForm[index1] = new JTextField();
                    jTextFieldsForm[index1].setText(null);
                    pnlDecentralizationConfiguration.add(jTextFieldsForm[index1]);
                    index1++;
                }
                default -> {
                }
            }
        }

        if (decentralizationMode > 1) {
            mode.setLayout(new GridLayout(2, 2, 20, 20));
            mode.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            mode.setBackground(new Color(0xFFFFFF));
            mode.setPreferredSize(new Dimension(635, 130));
            roundPanel2.add(mode, BorderLayout.SOUTH);

            btAdd.setBackground(new Color(35, 166, 97));
            btAdd.setBorder(null);
            btAdd.setIcon(new ImageIcon("img/icons/plus.png"));
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
                        addDecentralization();
                    }
                }
            });
            mode.add(btAdd);
        }

        if (decentralizationMode == 3) {
            btUpd.setBackground(new Color(35, 166, 97));
            btUpd.setBorder(null);
            btUpd.setIcon(new ImageIcon("img/icons/wrench.png"));
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
                        updateDecentralization();
                    }
                }
            });
            mode.add(btUpd);

            btDel.setBackground(new Color(35, 166, 97));
            btDel.setBorder(null);
            btDel.setIcon(new ImageIcon("img/icons/delete.png"));
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
                        deleteDecentralization();
                    }
                }
            });
            mode.add(btDel);
        }

        if (decentralizationMode > 1) {
            btRef.setBackground(new Color(35, 166, 97));
            btRef.setBorder(null);
            btRef.setIcon(new ImageIcon("img/icons/refresh.png"));
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
            jScrollPane.setPreferredSize(new Dimension(600, 676));
            dataTable.setRowSelectionInterval(0, 0);
            fillForm();
        }
    }

    private void comboboxSearch() {
        String key = null;
        switch (cbbSearchFilter.getSelectedIndex()){
            case 1 -> key = "IS_SALE";
            case 2 -> key = "IS_PRODUCT";
            case 3 -> key = "IS_CATEGORY";
            case 4 -> key = "IS_RECIPE";
            case 5 -> key = "IS_IMPORT";
            case 6 -> key = "IS_SUPPLIER";
            case 7 -> key = "IS_BILL";
            case 8 -> key = "IS_WAREHOUSES";
            case 9 -> key = "IS_ACCOUNT";
            case 10 -> key = "IS_STAFF";
            case 11 -> key = "IS_CUSTOMER";
            case 12 -> key = "IS_DISCOUNT";
            case 13 -> key = "IS_DECENTRALIZE";
            default -> {
            }
        }
        assert key != null;
        String value = null;
        switch (Objects.requireNonNull(jComboBoxSearch.getSelectedItem()).toString()) {
            case "Không" -> value = "0";
            case "Xem" -> value = "1";
            case "Thêm" -> value = "2";
            case "Sửa và xóa" -> value = "3";
            default -> {
            }
        }
        loadDataTable(decentralizationBLL.findDecentralizations(key, value));
    }

    private void selectSearchFilter() {
        if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("Mã quyền") || Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("Tên quyền")) {
            jComboBoxSearch.setVisible(false);
            txtSearch.setVisible(true);
            searchDecentralizations();
        } else {
            txtSearch.setVisible(false);
            jComboBoxSearch.setSelectedIndex(0);
            jComboBoxSearch.setVisible(true);
            comboboxSearch();
        }
    }

    public void searchDecentralizations() {
        if (txtSearch.getText().isEmpty()) {
            loadDataTable(decentralizationBLL.getDecentralizationList());
        } else {
            String key = null;
            switch (cbbSearchFilter.getSelectedIndex()){
                case 0 -> key = "DECENTRALIZATION_ID";
                case 14 -> key = "DECENTRALIZATION_NAME";
                default -> {
                }
            }
            assert key != null;
            loadDataTable(decentralizationBLL.findDecentralizations(key, txtSearch.getText()));
        }
    }

    public void addDecentralization() {
        if (checkInput()) {
            Decentralization newDecentralization = getForm();
            if (decentralizationBLL.exists(newDecentralization))
                JOptionPane.showMessageDialog(this, "Quyền đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (decentralizationBLL.exists(Map.of("DECENTRALIZATION_NAME", newDecentralization.getDecentralizationName())))
                JOptionPane.showMessageDialog(this, "Quyền đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (decentralizationBLL.addDecentralization(newDecentralization))
                JOptionPane.showMessageDialog(this, "Thêm quyền mới thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Thêm quyền mới thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            refreshForm();
        }
    }

    public void updateDecentralization() {
        if (checkInput()) {
            Decentralization newDecentralization = getForm();
            int selectedRow = dataTable.getSelectedRow();
            String currentDecentralizationName = dataTable.getValueAt(selectedRow, 13).toString();
            boolean valueChanged = !newDecentralization.getDecentralizationName().equals(currentDecentralizationName);
            valueChanged |= !newDecentralization.getDecentralizationName().equals(dataTable.getValueAt(selectedRow, 13).toString());
            if (decentralizationBLL.exists(newDecentralization))
                JOptionPane.showMessageDialog(this, "Quyền đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (decentralizationBLL.updateDecentralization(newDecentralization))
                JOptionPane.showMessageDialog(this, "Sửa quyền thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Sửa quyền thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            loadDataTable(decentralizationBLL.getDecentralizationList());
            dataTable.setRowSelectionInterval(selectedRow, selectedRow);
            fillForm();
            Account account = CafeManagement.homeGUI.getAccount();
            CafeManagement.homeGUI.setAccount(account);
        }
    }

    private void deleteDecentralization() {
        if (JOptionPane.showOptionDialog(this,
            "Bạn có chắc chắn muốn xoá quyền này?",
            "Xác nhận",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"Xoá", "Huỷ"},
            "Xoá") == JOptionPane.YES_OPTION) {
            Decentralization decentralization = new Decentralization();
            decentralization.setDecentralizationID(jTextFieldsForm[0].getText());
            if (!accountBLL.findAccounts("DECENTRALIZATION_ID", decentralization.getDecentralizationID()).isEmpty())
                JOptionPane.showMessageDialog(this, "Quyền đang có tài khoản không được xoá!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (decentralizationBLL.deleteDecentralization(decentralization))
                JOptionPane.showMessageDialog(this, "Xoá quyền thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Xoá quyền thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            refreshForm();
        }
    }

    public void refreshForm() {
        cbbSearchFilter.setSelectedIndex(0);
        txtSearch.setText(null);
        loadDataTable(decentralizationBLL.getDecentralizationList());
        jTextFieldsForm[0].setText(decentralizationBLL.getAutoID());
        jTextFieldsForm[1].setText(null);
        for (JComboBox<Object> objectJComboBox : jComboBoxForm) {
            objectJComboBox.setSelectedIndex(0);
        }
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
        String[] decentralization = String.join(" | ", data).split(" \\| ");
        jTextFieldsForm[0].setText(decentralization[0]);
        jTextFieldsForm[1].setText(decentralization[decentralization.length - 1]);
        int index = 0;
        for (int i = 1; i < decentralization.length - 1; i++) {
            jComboBoxForm[index].setSelectedItem(parse(decentralization[i]));
            index++;
        }
        btAdd.setEnabled(false);
        btUpd.setEnabled(true);
        btDel.setEnabled(true);
    }

    public Decentralization getForm() {
        String decentralizationID;
        List<String> args = new ArrayList<>();
        String decentralizationName;
        decentralizationID = jTextFieldsForm[0].getText();
        decentralizationName = jTextFieldsForm[1].getText();
        for (JComboBox<Object> objectJComboBox : jComboBoxForm) {
            args.add(Objects.requireNonNull(objectJComboBox.getSelectedItem()).toString());
        }
        return new Decentralization(decentralizationID, args, decentralizationName, false);
    }

    public String parse(String mode) {
        return switch (mode) {
            case "0" -> "Không";
            case "1" -> "Xem";
            case "2" -> "Thêm";
            case "3" -> "Sửa và xóa";
            default -> null;
        };
    }

    public void loadDataTable(List<Decentralization> decentralizationList) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);
        for (Decentralization decentralization : decentralizationList) {
            List<Object> objects = new ArrayList<>();
            objects.add(decentralization.getDecentralizationID());
            objects.add(decentralization.getIsSale());
            objects.add(decentralization.getIsProduct());
            objects.add(decentralization.getIsCategory());
            objects.add(decentralization.getIsRecipe());
            objects.add(decentralization.getIsImport());
            objects.add(decentralization.getIsSupplier());
            objects.add(decentralization.getIsBill());
            objects.add(decentralization.getIsWarehouses());
            objects.add(decentralization.getIsAccount());
            objects.add(decentralization.getIsStaff());
            objects.add(decentralization.getIsCustomer());
            objects.add(decentralization.getIsDiscount());
            objects.add(decentralization.getIsDecentralization());
            objects.add(decentralization.getDecentralizationName());
            model.addRow(objects.toArray());
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
            JOptionPane.showMessageDialog(this, "Tên quyền không được chứa \"|\"", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }


}
