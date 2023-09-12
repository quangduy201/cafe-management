package com.cafe.GUI;

import com.cafe.BLL.SupplierBLL;
import com.cafe.DTO.DecentralizationDetail;
import com.cafe.DTO.Supplier;
import com.cafe.custom.Button;
import com.cafe.custom.DataTable;
import com.cafe.custom.RoundPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;

public class SupplierGUI extends JPanel {
    private SupplierBLL supplierBLL = new SupplierBLL();
    private DecentralizationDetail decentralizationMode;
    private DataTable dataTable;
    private RoundPanel supplier;
    private RoundPanel roundPanel1;
    private RoundPanel roundPanel2;
    private RoundPanel search;
    private JScrollPane scrollPane;
    private RoundPanel pnlSupplierConfiguration;
    private JPanel showImg;
    private RoundPanel mode;
    private JLabel[] jLabelsForm;
    private JComboBox<Object> cbbSearchFilter;
    private JTextField txtSearch;
    private JTextField[] jTextFieldsForm;
    private Button btAdd;
    private Button btUpd;
    private Button btDel;
    private Button btRef;

    private RoundPanel[] roundPanel;

    public SupplierGUI(DecentralizationDetail decentralizationMode) {
        System.gc();
        this.decentralizationMode = decentralizationMode;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(51, 51, 51));
        initComponents();
    }

    public void initComponents() {
        supplier = new RoundPanel();
        roundPanel1 = new RoundPanel();
        roundPanel2 = new RoundPanel();
        roundPanel = new RoundPanel[10];
        for (int i = 0; i < roundPanel.length; i++) {
            roundPanel[i] = new RoundPanel();
        }
        List<String> columnNames = supplierBLL.getSupplierDAL().getColumnNames();
        search = new RoundPanel();
        pnlSupplierConfiguration = new RoundPanel();
        showImg = new JPanel();
        mode = new RoundPanel();
        jLabelsForm = new JLabel[columnNames.size() - 1];
        cbbSearchFilter = new JComboBox<>(new String[]{"Mã nhà cung cấp", "Tên nhà cung cấp", "Điện thoại", "Địa chỉ", "Email"});
        txtSearch = new JTextField();
        jTextFieldsForm = new JTextField[columnNames.size() - 1];
        btAdd = new Button();
        btUpd = new Button();
        btDel = new Button();
        btRef = new Button();

        supplier.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        supplier.setBackground(new Color(70, 67, 67));
        this.add(supplier, BorderLayout.CENTER);

        roundPanel1.setPreferredSize(new Dimension(635, 670));
        roundPanel1.setAutoscrolls(true);
        supplier.add(roundPanel1);

        roundPanel[1].setPreferredSize(new Dimension(635, 50));
        roundPanel[1].setAutoscrolls(true);
        roundPanel1.add(roundPanel[1]);

        roundPanel[2].setLayout(new BorderLayout(0, 10));
        roundPanel[2].setPreferredSize(new Dimension(620, 560));
        roundPanel[2].add(new JScrollPane(dataTable), BorderLayout.CENTER);
        roundPanel[2].setAutoscrolls(true);
        roundPanel1.add(roundPanel[2]);


        roundPanel2.setLayout(new BorderLayout());
        roundPanel2.setPreferredSize(new Dimension(350, 670));
        roundPanel2.setAutoscrolls(true);
        supplier.add(roundPanel2);

        search.setLayout(new FlowLayout());
        search.setPreferredSize(new Dimension(635, 40));
        roundPanel[1].add(search, BorderLayout.NORTH);

        cbbSearchFilter.setPreferredSize(new Dimension(120, 30));
        search.add(cbbSearchFilter);

        txtSearch.setPreferredSize(new Dimension(200, 30));
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchSuppliers();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchSuppliers();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchSuppliers();
            }
        });
        search.add(txtSearch);

        dataTable = new DataTable(supplierBLL.getData(), new String[]{"Mã nhà cung cấp", "Tên nhà cung cấp", "Điện thoại", "Địa chỉ", "Email"}, e -> fillForm());
        scrollPane = new JScrollPane(dataTable);
        roundPanel[2].add(scrollPane);


        pnlSupplierConfiguration.setLayout(new GridLayout(5, 2, 20, 20));
        pnlSupplierConfiguration.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
        pnlSupplierConfiguration.setPreferredSize(new Dimension(635, 250));
        roundPanel2.add(pnlSupplierConfiguration, BorderLayout.NORTH);

        for (int i = 0; i < columnNames.size() - 1; i++) {
            jLabelsForm[i] = new JLabel();
            pnlSupplierConfiguration.add(jLabelsForm[i]);
            jTextFieldsForm[i] = new JTextField();
            jTextFieldsForm[i].setText(null);
            pnlSupplierConfiguration.add(jTextFieldsForm[i]);
            if ("SUPPLIER_ID".equals(columnNames.get(i))) {
                jLabelsForm[i].setText("Mã nhà cung cấp: ");
                jTextFieldsForm[i].setText(supplierBLL.getAutoID());
                jTextFieldsForm[i].setEnabled(false);
                jTextFieldsForm[i].setBorder(null);
                jTextFieldsForm[i].setDisabledTextColor(null);
            } else {
                if ("NAME".equals(columnNames.get(i))) {
                    jLabelsForm[i].setText("Tên nhà cung cấp: ");
                }
                if ("PHONE".equals(columnNames.get(i))) {
                    jLabelsForm[i].setText("Điện thoại: ");
                    jTextFieldsForm[i].addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            char c = e.getKeyChar();
                            if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != '+') {
                                e.consume();
                            }
                        }
                    });
                }
                if ("ADDRESS".equals(columnNames.get(i))) {
                    jLabelsForm[i].setText("Địa chỉ: ");
                }
                if ("EMAIL".equals(columnNames.get(i))) {
                    jLabelsForm[i].setText("Email: ");
                }

            }
        }
        showImg.setLayout(new FlowLayout());
        showImg.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        showImg.setPreferredSize(new Dimension(635, 100)); // TODO:
        showImg.setPreferredSize(new Dimension(635, 300));
        showImg.setBackground(new Color(0xFFFFFF));
        //   roundPanel2.add(showImg, BorderLayout.CENTER);

        mode.setLayout(new GridLayout(2, 2, 20, 20));
        mode.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mode.setPreferredSize(new Dimension(635, 130));
        roundPanel2.add(mode, BorderLayout.SOUTH);

        if (decentralizationMode.isCanADD()) {
            Button.configButton(btAdd, List.of("  Thêm", "img/icons/plus.png", true, (Runnable) this::addSupplier));
            mode.add(btAdd);
        }

        if (decentralizationMode.isCanEDIT()) {
            Button.configButton(btUpd, List.of("  Sửa", "img/icons/wrench.png", false, (Runnable) this::updateSupplier));
            mode.add(btUpd);
        }

        if (decentralizationMode.isCanREMOVE()) {
            Button.configButton(btDel, List.of("  Xóa", "img/icons/delete.png", false, (Runnable) this::deleteSupplier));
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

    public void searchSuppliers() {
        if (txtSearch.getText().isEmpty()) {
            loadDataTable(supplierBLL.getSupplierList());
        } else {
            String key = null;
            switch (cbbSearchFilter.getSelectedIndex()) {
                case 0 -> key = "SUPPLIER_ID";
                case 1 -> key = "NAME";
                case 2 -> key = "PHONE";
                case 3 -> key = "ADDRESS";
                case 4 -> key = "EMAIL";
                default -> {
                }
            }
            assert key != null;
            loadDataTable(supplierBLL.findSuppliers(key, txtSearch.getText()));
        }
    }

    public void addSupplier() {
        if (checkInput()) {
            Supplier newSupplier = getForm();
            if (supplierBLL.exists(newSupplier))
                JOptionPane.showMessageDialog(this, "Nhà cung cấp đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (supplierBLL.exists(Map.of("PHONE", newSupplier.getPhone())))
                JOptionPane.showMessageDialog(this, "Nhà cung cấp đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (supplierBLL.addSupplier(newSupplier))
                JOptionPane.showMessageDialog(this, "Thêm nhà cung cấp mới thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Thêm nhà cung cấp mới thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            refreshForm();
        }
    }

    public void updateSupplier() {
        if (checkInput()) {
            Supplier newSupplier = getForm();
            int selectedRow = dataTable.getSelectedRow();
            String currentPhone = dataTable.getValueAt(selectedRow, 2).toString();
            boolean valueChanged = !newSupplier.getPhone().equals(currentPhone);
            if (supplierBLL.exists(newSupplier))
                JOptionPane.showMessageDialog(this, "Nhà cung cấp đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (valueChanged && supplierBLL.exists(Map.of("PHONE", newSupplier.getPhone())))
                JOptionPane.showMessageDialog(this, "Nhà cung cấp đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (supplierBLL.updateSupplier(newSupplier))
                JOptionPane.showMessageDialog(this, "Sửa nhà cung cấp thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Sửa nhà cung cấp thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            loadDataTable(supplierBLL.getSupplierList());
            dataTable.setRowSelectionInterval(selectedRow, selectedRow);
            fillForm();
        }
    }

    private void deleteSupplier() {
        if (JOptionPane.showOptionDialog(this,
            "Bạn có chắc chắn muốn xoá nhà cung cấp này?",
            "Xác nhận",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"Xoá", "Huỷ"},
            "Xoá") == JOptionPane.YES_OPTION) {
            Supplier supplier = new Supplier();
            supplier.setSupplierID(jTextFieldsForm[0].getText());
            if (supplierBLL.deleteSupplier(supplier))
                JOptionPane.showMessageDialog(this, "Xoá nhà cung cấp thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Xoá nhà cung cấp thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            refreshForm();
        }
    }

    public void refreshForm() {
        cbbSearchFilter.setSelectedIndex(0);
        txtSearch.setText(null);
        loadDataTable(supplierBLL.getSupplierList());
        jTextFieldsForm[0].setText(supplierBLL.getAutoID());
        for (int i = 1; i < jTextFieldsForm.length; i++) {
            jTextFieldsForm[i].setText(null);
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
        String[] supplier = String.join(" | ", data).split(" \\| ");
        for (int i = 0; i < jTextFieldsForm.length; i++) {
            jTextFieldsForm[i].setText(supplier[i]);
        }
        btAdd.setEnabled(false);
        btUpd.setEnabled(true);
        btDel.setEnabled(true);
    }

    public Supplier getForm() {
        String supplierID = null;
        String name = null;
        String phone = null;
        String address = null;
        String email = null;
        double price = 0;
        for (int i = 0; i < jTextFieldsForm.length; i++) {
            switch (i) {
                case 0 -> supplierID = jTextFieldsForm[i].getText();
                case 1 -> name = jTextFieldsForm[i].getText();
                case 2 -> phone = jTextFieldsForm[i].getText().replaceAll("^\\+?84", "0");
                case 3 -> address = jTextFieldsForm[i].getText();
                case 4 -> email = jTextFieldsForm[i].getText();
                case 5 -> price = Double.parseDouble(jTextFieldsForm[i].getText().replaceAll("(\\s|VND|VNĐ)", ""));
                default -> {
                }
            }
        }
        return new Supplier(supplierID, name, phone, address, email, false);
    }

    public void loadDataTable(List<Supplier> supplierList) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);
        for (Supplier supplier : supplierList) {
            model.addRow(new Object[]{supplier.getSupplierID(), supplier.getName(), supplier.getPhone(), supplier.getAddress(), supplier.getEmail()});
        }
    }

    public boolean checkInput() {
        for (JTextField textField : jTextFieldsForm) {
            if (textField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điên đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                textField.requestFocusInWindow();
                return false;
            }
        }
        if (!jTextFieldsForm[1].getText().matches("^[^|]+$")) {
            // Name can't contain "|"
            jTextFieldsForm[1].requestFocusInWindow();
            jTextFieldsForm[1].selectAll();
            JOptionPane.showMessageDialog(this, "Tên nhà cung cấp không được chứa \"|\"", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!jTextFieldsForm[2].getText().matches("^(\\+?84|0)[235789]\\d{8,9}$")) {
            // Phone must start with "0x", "+84x" or "84x" where "x" in {2, 3, 5, 7, 8, 9}
            jTextFieldsForm[2].requestFocusInWindow();
            jTextFieldsForm[2].selectAll();
            JOptionPane.showMessageDialog(this, "Số điện thoại phải bắt đầu từ \"0x\" hoặc \"+84x\" hoặc \"84x\"\nvới \"x\" thuộc {3, 5, 7, 8, 9}", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!jTextFieldsForm[3].getText().matches("^[^|]+$")) {
            // Address can't contain "|"
            jTextFieldsForm[3].requestFocusInWindow();
            jTextFieldsForm[3].selectAll();
            JOptionPane.showMessageDialog(this, "Địa chỉ không được chứa \"|\"", "Lỗi", JOptionPane.ERROR_MESSAGE);
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
