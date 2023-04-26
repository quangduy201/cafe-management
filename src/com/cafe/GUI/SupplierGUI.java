package com.cafe.GUI;

import com.cafe.BLL.SupplierBLL;
import com.cafe.DTO.Supplier;
import com.cafe.custom.Button;
import com.cafe.custom.DataTable;
import com.cafe.custom.RoundPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SupplierGUI extends JPanel {
    private SupplierBLL supplierBLL = new SupplierBLL();
    private int decentralizationMode;
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

    private Button btsupplier;
    private Button btrecipe;

    private RoundPanel roundPanel[];

    public SupplierGUI(int decentralizationMode) {
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
        for (int i = 0;  i < roundPanel.length; i++) {
            roundPanel[i] = new RoundPanel();
        }
        List<String> columnNames = supplierBLL.getSupplierDAL().getColumnNames();
        search = new RoundPanel();
        pnlSupplierConfiguration = new RoundPanel();
        showImg = new JPanel();
        mode = new RoundPanel();
        jLabelsForm = new JLabel[columnNames.size() - 1];
        cbbSearchFilter = new JComboBox<>(columnNames.subList(0, columnNames.size() - 1).toArray());
        txtSearch = new JTextField();
        jTextFieldsForm = new JTextField[columnNames.size() - 1];
        btAdd = new Button();
        btUpd = new Button();
        btDel = new Button();
        btRef = new Button();
        btsupplier =  new Button();
        btrecipe = new Button();

        supplier.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        supplier.setBackground(new Color(70, 67, 67));
        this.add(supplier, BorderLayout.CENTER);

        roundPanel1.setBackground(new Color(255, 255, 255));
        roundPanel1.setPreferredSize(new Dimension(635, 670));
        roundPanel1.setAutoscrolls(true);
        supplier.add(roundPanel1);

        roundPanel[0].setLayout(new BorderLayout(10,0));
        roundPanel[0].setBackground(new Color(255, 255, 255));
        roundPanel[0].setPreferredSize(new Dimension(620, 40));
        roundPanel[0].setAutoscrolls(true);
        roundPanel1.add(roundPanel[0]);

        roundPanel[1].setBackground(new Color(255, 255, 255));
        roundPanel[1].setPreferredSize(new Dimension(635, 50));
        roundPanel[1].setAutoscrolls(true);
        roundPanel1.add(roundPanel[1]);

        roundPanel[2].setLayout(new BorderLayout(0, 10));
        roundPanel[2].setBackground(new Color(255, 255, 255));
        roundPanel[2].setPreferredSize(new Dimension(620, 560));
        roundPanel[2].add(new JScrollPane(dataTable), BorderLayout.CENTER);
        roundPanel[2].setAutoscrolls(true);
        roundPanel1.add(roundPanel[2]);


        roundPanel2.setLayout(new BorderLayout());
        roundPanel2.setBackground(new Color(0xFFFFFF));
        roundPanel2.setPreferredSize(new Dimension(350, 670));
        roundPanel2.setAutoscrolls(true);
        supplier.add(roundPanel2);

        search.setLayout(new FlowLayout());
        search.setBackground(new Color(0xFFFFFF));
        search.setPreferredSize(new Dimension(635, 40));
        roundPanel[1].add(search, BorderLayout.NORTH);

        cbbSearchFilter.setPreferredSize(new Dimension(120,30));
        search.add(cbbSearchFilter);

        txtSearch.setPreferredSize(new Dimension(200,30));
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

        dataTable = new DataTable(supplierBLL.getData(), columnNames.subList(0, columnNames.size() - 1).toArray(), e -> fillForm());
        scrollPane = new JScrollPane(dataTable);
        roundPanel[2].add(scrollPane);

        btsupplier.setPreferredSize(new Dimension(120, 40));
        btsupplier.setBorderPainted(false);
        btsupplier.setRadius(15);
        btsupplier.setFocusPainted(false);
        btsupplier.setFont(new Font("Times New Roman", 0, 14));
        btsupplier.setColor(new Color(0x70E149));
        btsupplier.setColorOver(new Color(0x5EFF00));
        btsupplier.setColorClick(new Color(0x8AD242));
        btsupplier.setBorderColor(new Color(70, 67, 67));
        btsupplier.setText("Nhập Hàng");
        btsupplier.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                try {

                } catch (Exception ignored) {

                }
            }
        });
        roundPanel[0].add(btsupplier,BorderLayout.WEST);

        btrecipe.setPreferredSize(new Dimension(120, 40));
        btrecipe.setBorderPainted(false);
        btrecipe.setRadius(15);
        btrecipe.setFocusPainted(false);
        btrecipe.setFont(new Font("Times New Roman", 0, 14));
        btrecipe.setColor(new Color(0x70E149));
        btrecipe.setColorOver(new Color(0x5EFF00));
        btrecipe.setColorClick(new Color(0x8AD242));
        btrecipe.setBorderColor(new Color(70, 67, 67));
        btrecipe.setText("Nhà Cung Cấp");
        btrecipe.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                try {
                } catch (Exception ignored) {

                }
            }
        });
        roundPanel[0].add(btrecipe,BorderLayout.EAST);


        pnlSupplierConfiguration.setLayout(new GridLayout(5, 2, 20, 20));
        pnlSupplierConfiguration.setBackground(new Color(0xFFFFFF));
        pnlSupplierConfiguration.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
        pnlSupplierConfiguration.setPreferredSize(new Dimension(635, 250));
        roundPanel2.add(pnlSupplierConfiguration, BorderLayout.NORTH);

        for (int i = 0; i < columnNames.size() - 1; i++) {
            jLabelsForm[i] = new JLabel();
            jLabelsForm[i].setText(columnNames.get(i) + ": ");
            pnlSupplierConfiguration.add(jLabelsForm[i]);
            if ("SUPPLIER_ID".equals(columnNames.get(i))) {
                jTextFieldsForm[i] = new JTextField(supplierBLL.getAutoID());
                jTextFieldsForm[i].setEnabled(false);
                jTextFieldsForm[i].setBorder(null);
                jTextFieldsForm[i].setDisabledTextColor(new Color(0x000000));
                pnlSupplierConfiguration.add(jTextFieldsForm[i]);
            } else {
                jTextFieldsForm[i] = new JTextField();
                jTextFieldsForm[i].setText(null);
                pnlSupplierConfiguration.add(jTextFieldsForm[i]);
            }
        }
        showImg.setLayout(new FlowLayout());
        showImg.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        showImg.setPreferredSize(new Dimension(635, 100)); // TODO:
        showImg.setPreferredSize(new Dimension(635, 300));
        showImg.setBackground(new Color(0xFFFFFF));
     //   roundPanel2.add(showImg, BorderLayout.CENTER);

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
                        addSupplier();
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
                        updateSupplier();
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
                        deleteSupplier();
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
    public void searchSuppliers() {
        if (txtSearch.getText().isEmpty()) {
            loadDataTable(supplierBLL.getSupplierList());
        } else {
            loadDataTable(supplierBLL.findSuppliers(Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString(), txtSearch.getText()));
        }
    }

    public void addSupplier() {
        if (checkInput()) {
            Supplier newSupplier = getForm();
            if (supplierBLL.exists(newSupplier))
                JOptionPane.showMessageDialog(this, "Supplier already existed!", "Error", JOptionPane.ERROR_MESSAGE);
            else if (supplierBLL.exists(Map.of("PHONE", newSupplier.getPhone())))
                JOptionPane.showMessageDialog(this, "Supplier already existed!", "Error", JOptionPane.ERROR_MESSAGE);
            else if (supplierBLL.addSupplier(newSupplier))
                JOptionPane.showMessageDialog(this, "Successfully added new supplier!", "Notification", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Failed to add new supplier!", "Error", JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(this, "Supplier already existed!", "Error", JOptionPane.ERROR_MESSAGE);
            else if (valueChanged && supplierBLL.exists(Map.of("PHONE", newSupplier.getPhone())))
                JOptionPane.showMessageDialog(this, "Supplier already existed!", "Error", JOptionPane.ERROR_MESSAGE);
            else if (supplierBLL.updateSupplier(newSupplier))
                JOptionPane.showMessageDialog(this, "Successfully updated supplier!", "Notification", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Failed to update supplier!", "Error", JOptionPane.ERROR_MESSAGE);
            loadDataTable(supplierBLL.getSupplierList());
            dataTable.setRowSelectionInterval(selectedRow, selectedRow);
            fillForm();
        }
    }

    private void deleteSupplier() {
        if (JOptionPane.showConfirmDialog(this, "Are you sure to delete this supplier?",
            "Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            Supplier supplier = new Supplier();
            supplier.setSupplierID(jTextFieldsForm[0].getText());
            if (supplierBLL.deleteSupplier(supplier))
                JOptionPane.showMessageDialog(this, "Successfully deleted supplier!", "Notification", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Failed to delete supplier!", "Error", JOptionPane.ERROR_MESSAGE);
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
        if (!jTextFieldsForm[2].getText().matches("^(\\+?84|0)[235789]\\d{8,9}$")) {
            // Phone must start with "0x", "+84x" or "84x" where "x" in {2, 3, 5, 7, 8, 9}
            jTextFieldsForm[2].requestFocusInWindow();
            jTextFieldsForm[2].selectAll();
            JOptionPane.showMessageDialog(this, "Phone must start with \"0x\" or \"+84x\" or \"84x\"\nwhere \"x\" in {3, 5, 7, 8, 9}", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!jTextFieldsForm[3].getText().matches("^[^|]+$")) {
            // Address can't contain "|"
            jTextFieldsForm[3].requestFocusInWindow();
            jTextFieldsForm[3].selectAll();
            JOptionPane.showMessageDialog(this, "Address can't contain \"|\"", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!jTextFieldsForm[4].getText().matches("^\\w+(\\.\\w+)*@\\w+(\\.\\w+)+")) {
            // Email must follow "username@domain.name"
            jTextFieldsForm[4].requestFocusInWindow();
            jTextFieldsForm[4].selectAll();
            JOptionPane.showMessageDialog(this, "Email must follow the pattern \"username@domain.name\"", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!jTextFieldsForm[5].getText().matches("^(?=.*\\d)\\d*\\.?\\d*\\s*(VND|VNĐ)$")) {
            // Price must be a double >= 0
            jTextFieldsForm[5].requestFocusInWindow();
            jTextFieldsForm[5].selectAll();
            JOptionPane.showMessageDialog(this, "Price must be a non-negative real number", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

}
