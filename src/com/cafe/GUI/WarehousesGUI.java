package com.cafe.GUI;

import com.cafe.BLL.IngredientBLL;
import com.cafe.BLL.SupplierBLL;
import com.cafe.DTO.Ingredient;
import com.cafe.custom.Button;
import com.cafe.custom.DataTable;
import com.cafe.custom.RoundPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WarehousesGUI extends JPanel {
    private IngredientBLL ingredientBLL = new IngredientBLL();
    private DataTable dataTable;
    private RoundPanel wareHouses;
    private RoundPanel roundPanel1;
    private RoundPanel roundPanel2;
    private RoundPanel search;
    private JScrollPane scrollPane;
    private JPanel pnlSupplierConfiguration;
    private JPanel mode;
    private JPanel showImg;
    private JLabel jLabelsForm[];
    private JComboBox<Object> cbbSearchFilter;
    private JComboBox<Object> cbbSupplierID;
    private JComboBox<Object> cbbSupplierIDSearch;
    private JComboBox<Object> cbbUnit;
    private JComboBox<Object> cbbUnitSearch;
    private JTextField txtSearch;
    private JTextField jTextFieldsForm[];
    private com.cafe.custom.Button btAdd;
    private com.cafe.custom.Button btUpd;
    private com.cafe.custom.Button btDel;
    private Button btRef;

    public WarehousesGUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(51, 51, 51));
        initComponents();
    }

    public void initComponents() {
        List<String> columnNames = ingredientBLL.getIngredientDAL().getColumnNames();
        SupplierBLL supplierBLL = new SupplierBLL();
        List<String> suppliersID = new ArrayList<>();
        for (int i = 0; i < supplierBLL.getSupplierList().size(); i++) {
            suppliersID.add(supplierBLL.getValueByKey(supplierBLL.getSupplierList().get(i), "SUPPLIER_ID").toString());
        }
        wareHouses = new RoundPanel();
        roundPanel1 = new RoundPanel();
        roundPanel2 = new RoundPanel();
        search = new RoundPanel();
        pnlSupplierConfiguration = new JPanel();
        mode = new JPanel();
        showImg = new JPanel();
        jLabelsForm = new JLabel[columnNames.size() - 1];
        cbbSearchFilter = new JComboBox<>(columnNames.subList(0, columnNames.size() - 1).toArray());
        cbbUnit = new JComboBox<>(new String[]{"kg", "l", "bag"});
        cbbUnitSearch = new JComboBox<>(new String[]{"kg", "l", "bag"});
        cbbSupplierID = new JComboBox<>(suppliersID.toArray());
        cbbSupplierIDSearch = new JComboBox<>(suppliersID.toArray());
        txtSearch = new JTextField(20);
        jTextFieldsForm = new JTextField[columnNames.size() - 3];
        btAdd = new Button();
        btUpd = new Button();
        btDel = new Button();
        btRef = new Button();

        wareHouses.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        wareHouses.setBackground(new Color(51, 51, 51));
        this.add(wareHouses, BorderLayout.CENTER);

        roundPanel1.setLayout(new BorderLayout(0, 10));
        roundPanel1.setBackground(new Color(51, 51, 51));
        roundPanel1.setPreferredSize(new Dimension(635, 680));
        roundPanel1.setAutoscrolls(true);
        roundPanel1.add(new JScrollPane(dataTable), BorderLayout.CENTER);
        wareHouses.add(roundPanel1);

        roundPanel2.setLayout(new BorderLayout());
        roundPanel2.setBackground(new Color(0xFFFFFF));
        roundPanel2.setPreferredSize(new Dimension(350, 680));
        roundPanel2.setAutoscrolls(true);
        wareHouses.add(roundPanel2);

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
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchIngredients();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchIngredients();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchIngredients();
            }
        });
        search.add(txtSearch);
        cbbSupplierIDSearch.setVisible(false);
        cbbSupplierIDSearch.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                supplierIDSearch();
            }
        });
        search.add(cbbSupplierIDSearch);
        cbbUnitSearch.setVisible(false);
        cbbUnitSearch.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                uniSearch();
            }
        });
        search.add(cbbUnitSearch);

        dataTable = new DataTable(ingredientBLL.getData(), columnNames.subList(0, columnNames.size() - 1).toArray(), getListSelectionListener());
        scrollPane = new JScrollPane(dataTable);
        roundPanel1.add(scrollPane);

        pnlSupplierConfiguration.setLayout(new GridLayout(5, 2, 20, 20));
        pnlSupplierConfiguration.setBackground(new Color(0xFFFFFF));
        pnlSupplierConfiguration.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
        pnlSupplierConfiguration.setPreferredSize(new Dimension(635, 250));
        roundPanel2.add(pnlSupplierConfiguration, BorderLayout.NORTH);

        for (int i = 0; i < columnNames.size() - 1; i++) {
            jLabelsForm[i] = new JLabel();
            jLabelsForm[i].setText(columnNames.get(i) + ": ");
            pnlSupplierConfiguration.add(jLabelsForm[i]);
            switch (columnNames.get(i)) {
                case "INGREDIENT_ID" -> {
                    jTextFieldsForm[i] = new JTextField(ingredientBLL.getAutoID());
                    jTextFieldsForm[i].setEnabled(false);
                    jTextFieldsForm[i].setBorder(null);
                    jTextFieldsForm[i].setDisabledTextColor(new Color(0x000000));
                    pnlSupplierConfiguration.add(jTextFieldsForm[i]);
                }
                case "UNIT" -> pnlSupplierConfiguration.add(cbbUnit);
                case "SUPPLIER_ID" -> pnlSupplierConfiguration.add(cbbSupplierID);
                default -> {
                    jTextFieldsForm[i] = new JTextField();
                    jTextFieldsForm[i].setText(null);
                    pnlSupplierConfiguration.add(jTextFieldsForm[i]);
                }
            }
        }
        showImg.setLayout(new FlowLayout());
        showImg.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        showImg.setPreferredSize(new Dimension(635, 300));
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
                    addIngredient();
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
                    updateIngredient();
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
                    deleteIngredient();
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

    private void uniSearch() {
        loadDataTable(ingredientBLL.findIngredients("UNIT", Objects.requireNonNull(cbbUnitSearch.getSelectedItem()).toString()));
    }

    private void supplierIDSearch() {
        loadDataTable(ingredientBLL.findIngredients("SUPPLIER_ID", Objects.requireNonNull(cbbSupplierIDSearch.getSelectedItem()).toString()));
    }

    private void selectSearchFilter() {
        if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("UNIT")) {
            txtSearch.setVisible(false);
            cbbSupplierIDSearch.setVisible(false);
            cbbUnitSearch.setSelectedIndex(0);
            cbbUnitSearch.setVisible(true);
            uniSearch();
        } else if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("SUPPLIER_ID")) {
            txtSearch.setVisible(false);
            cbbUnitSearch.setVisible(false);
            cbbSupplierIDSearch.setSelectedIndex(0);
            cbbSupplierIDSearch.setVisible(true);
            supplierIDSearch();
        } else {
            cbbSupplierIDSearch.setVisible(false);
            cbbUnitSearch.setVisible(false);
            txtSearch.setVisible(true);
            searchIngredients();
        }
    }

    private void searchIngredients() {
        if (txtSearch.getText().isEmpty()) {
            loadDataTable(ingredientBLL.getIngredientList());
        } else {
            loadDataTable(ingredientBLL.findIngredients(Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString(), txtSearch.getText()));
        }
    }

    public ActionListener getListSelectionListener() {
        return e -> {
            DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
            String rowData = model.getDataVector().elementAt(dataTable.getSelectedRow()).toString();
            String[] ingredient = rowData.substring(1, rowData.length() - 1).split(", ");
            jTextFieldsForm[0].setText(ingredient[0]);
            jTextFieldsForm[1].setText(ingredient[1]);
            jTextFieldsForm[2].setText(ingredient[2]);
            cbbUnit.setSelectedItem(ingredient[3]);
            cbbSupplierID.setSelectedItem(ingredient[4]);
            btAdd.setEnabled(false);
            btUpd.setEnabled(true);
            btDel.setEnabled(true);
        };
    }

    private void addIngredient() {
        if (checkInput()) {
            Ingredient newIngredient;
            String ingredientID = null;
            String name = null;
            double quantity = 0;
            String unit;
            String supplierID;
            for (int i = 0; i < jTextFieldsForm.length; i++) {
                switch (i) {
                    case 0 -> ingredientID = jTextFieldsForm[i].getText();
                    case 1 -> name = jTextFieldsForm[i].getText();
                    case 2 -> quantity = Double.parseDouble(jTextFieldsForm[i].getText());
                    default -> {
                    }
                }
            }
            unit = Objects.requireNonNull(cbbUnit.getSelectedItem()).toString();
            supplierID = Objects.requireNonNull(cbbSupplierID.getSelectedItem()).toString();
            newIngredient = new Ingredient(ingredientID, name, quantity, unit, supplierID, false);
            ingredientBLL.addIngredient(newIngredient);
            refreshForm();
        }
    }

    private void updateIngredient() {
        if (checkInput()) {
            Ingredient newIngredient;
            String ingredientID = null;
            String name = null;
            double quantity = 0;
            String unit;
            String supplierID;
            for (int i = 0; i < jTextFieldsForm.length; i++) {
                switch (i) {
                    case 0 -> ingredientID = jTextFieldsForm[i].getText();
                    case 1 -> name = jTextFieldsForm[i].getText();
                    case 2 -> quantity = Double.parseDouble(jTextFieldsForm[i].getText());
                    default -> {
                    }
                }
            }
            unit = Objects.requireNonNull(cbbUnit.getSelectedItem()).toString();
            supplierID = Objects.requireNonNull(cbbSupplierID.getSelectedItem()).toString();
            newIngredient = new Ingredient(ingredientID, name, quantity, unit, supplierID, false);
            ingredientBLL.updateIngredient(newIngredient);
            loadDataTable(ingredientBLL.getIngredientList());
        }
    }

    private void deleteIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setIngredientID(jTextFieldsForm[0].getText());
        ingredientBLL.deleteIngredient(ingredient);
        refreshForm();
    }

    private void refreshForm() {
        cbbSearchFilter.setSelectedIndex(0);
        txtSearch.setText(null);
        ingredientBLL = new IngredientBLL();
        loadDataTable(ingredientBLL.getIngredientList());
        jTextFieldsForm[0].setText(ingredientBLL.getAutoID());
        for (int i = 1; i < jTextFieldsForm.length; i++) {
            jTextFieldsForm[i].setText(null);
        }
        cbbUnit.setSelectedIndex(0);
        cbbSupplierID.setSelectedIndex(0);
        btAdd.setEnabled(true);
        btUpd.setEnabled(false);
        btDel.setEnabled(false);
    }

    private void loadDataTable(List<Ingredient> ingredientList) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);
        for (Ingredient ingredient : ingredientList) {
            model.addRow(new Object[]{ingredient.getIngredientID(), ingredient.getName(), ingredient.getQuantity(), ingredient.getUnit(), ingredient.getSupplierID()});
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
            Double.parseDouble(jTextFieldsForm[2].getText());
        } catch (NumberFormatException exception) {
            jTextFieldsForm[2].setText(null);
            JOptionPane.showMessageDialog(this, "Invalid data input!", "Notification", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
}
