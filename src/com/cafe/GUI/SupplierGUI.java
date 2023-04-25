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
import java.util.Objects;

public class SupplierGUI extends JPanel {
    private SupplierBLL supplierBLL = new SupplierBLL();
    private DataTable dataTable;
    private RoundPanel supplier;
    private RoundPanel roundPanel1;
    private RoundPanel roundPanel2;
    private RoundPanel search;
    private JScrollPane scrollPane;
    private JPanel pnlSupplierConfiguration;
    private JPanel showImg;
    private JPanel mode;
    private JLabel jLabelsForm[];
    private JComboBox<Object> cbbSearchFilter;
    private JTextField txtSearch;
    private JTextField jTextFieldsForm[];
    private Button btAdd;
    private Button btUpd;
    private Button btDel;
    private Button btRef;

    public SupplierGUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(51, 51, 51));
        initComponents();
    }

    public void initComponents() {
        List<String> columsName = supplierBLL.getSupplierDAL().getColumnNames();
        supplier = new RoundPanel();
        roundPanel1 = new RoundPanel();
        roundPanel2 = new RoundPanel();
        search = new RoundPanel();
        pnlSupplierConfiguration = new JPanel();
        showImg = new JPanel();
        mode = new JPanel();
        jLabelsForm = new JLabel[columsName.size() - 1];
        cbbSearchFilter = new JComboBox<>(columsName.subList(0, columsName.size() - 1).toArray());
        txtSearch = new JTextField(20);
        jTextFieldsForm = new JTextField[columsName.size() - 1];
        btAdd = new Button();
        btUpd = new Button();
        btDel = new Button();
        btRef = new Button();

        supplier.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        supplier.setBackground(new Color(51, 51, 51));
        this.add(supplier, BorderLayout.CENTER);

        roundPanel1.setLayout(new BorderLayout(0, 10));
        roundPanel1.setBackground(new Color(51, 51, 51));
        roundPanel1.setPreferredSize(new Dimension(635, 680));
        roundPanel1.setAutoscrolls(true);
        roundPanel1.add(new JScrollPane(dataTable), BorderLayout.CENTER);
        supplier.add(roundPanel1);

        roundPanel2.setLayout(new BorderLayout());
        roundPanel2.setBackground(new Color(0xFFFFFF));
        roundPanel2.setPreferredSize(new Dimension(350, 680));
        roundPanel2.setAutoscrolls(true);
        supplier.add(roundPanel2);

        search.setLayout(new FlowLayout());
        search.setBackground(new Color(0xFFFFFF));
        search.setPreferredSize(new Dimension(635, 35));
        roundPanel1.add(search, BorderLayout.NORTH);


        search.add(cbbSearchFilter);
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

        dataTable = new DataTable(supplierBLL.getData(), columsName.subList(0, columsName.size() - 1).toArray(), getListSelectionListener());
        scrollPane = new JScrollPane(dataTable);
        roundPanel1.add(scrollPane);

        pnlSupplierConfiguration.setLayout(new GridLayout(6, 2, 20, 20));
        pnlSupplierConfiguration.setBackground(new Color(0xFFFFFF));
        pnlSupplierConfiguration.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
        pnlSupplierConfiguration.setPreferredSize(new Dimension(635, 300));
        roundPanel2.add(pnlSupplierConfiguration, BorderLayout.NORTH);

        for (int i = 0; i < columsName.size() - 1; i++) {
            jLabelsForm[i] = new JLabel();
            jLabelsForm[i].setText(columsName.get(i) + ": ");
            pnlSupplierConfiguration.add(jLabelsForm[i]);
            if ("SUPPLIER_ID".equals(columsName.get(i))) {
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
        showImg.setPreferredSize(new Dimension(635, 250));
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
                    addSupplier();
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

    public ActionListener getListSelectionListener() {
        return e -> {
            DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
            String rowData = model.getDataVector().elementAt(dataTable.getSelectedRow()).toString();
            String[] supplier = rowData.substring(1, rowData.length() - 1).split(", ");
            for (int i = 0; i < supplier.length; i++) {
                jTextFieldsForm[i].setText(supplier[i]);
            }
            btAdd.setEnabled(false);
            btUpd.setEnabled(true);
            btDel.setEnabled(true);
        };
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
            Supplier newSupplier;
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
                    case 2 -> phone = jTextFieldsForm[i].getText();
                    case 3 -> address = jTextFieldsForm[i].getText();
                    case 4 -> email = jTextFieldsForm[i].getText();
                    case 5 -> price = Double.parseDouble(jTextFieldsForm[i].getText());
                    default -> {
                    }
                }
            }
            newSupplier = new Supplier(supplierID, name, phone, address, email, price, false);
            supplierBLL.addSupplier(newSupplier);
            refreshForm();
        }
    }

    public void updateSupplier() {
        if (checkInput()) {
            Supplier newSupplier;
            String supplierID = null;
            String name = null;
            String phone = null;
            String address = null;
            String email = null;
            for (int i = 0; i < jTextFieldsForm.length; i++) {
                switch (i) {
                    case 0 -> supplierID = jTextFieldsForm[i].getText();
                    case 1 -> name = jTextFieldsForm[i].getText();
                    case 2 -> phone = jTextFieldsForm[i].getText();
                    case 3 -> address = jTextFieldsForm[i].getText();
                    case 4 -> email = jTextFieldsForm[i].getText();
                    default -> {
                    }
                }
            }
            newSupplier = new Supplier(supplierID, name, phone, address, email, false);
            supplierBLL.updateSupplier(newSupplier);
            loadDataTable(supplierBLL.getSupplierList());
        }
    }

    private void deleteSupplier() {
        Supplier supplier = new Supplier();
        supplier.setSupplierID(jTextFieldsForm[0].getText());
        supplierBLL.deleteSupplier(supplier);
        refreshForm();
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

    public void loadDataTable(List<Supplier> supplierList) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);
        for (Supplier supplier : supplierList) {
            model.addRow(new Object[]{supplier.getSupplierID(), supplier.getName(), supplier.getPhone(), supplier.getAddress(), supplier.getEmail(), supplier.getPrice()});
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
            Integer.parseInt(jTextFieldsForm[2].getText());
        } catch (NumberFormatException exception) {
            jTextFieldsForm[2].setText(null);
            JOptionPane.showMessageDialog(this, "Invalid data input!", "Notification", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        if (!jTextFieldsForm[4].getText().contains("@gmail.com")) {
            jTextFieldsForm[4].setText(null);
            JOptionPane.showMessageDialog(this, "Invalid data input!", "Notification", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        try {
            Double.parseDouble(jTextFieldsForm[5].getText());
        } catch (NumberFormatException exception) {
            jTextFieldsForm[5].setText(null);
            JOptionPane.showMessageDialog(this, "Invalid data input!", "Notification", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }

}
