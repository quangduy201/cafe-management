package com.cafe.GUI;

import com.cafe.BLL.IngredientBLL;
import com.cafe.BLL.ProductBLL;
import com.cafe.BLL.RecipeBLL;
import com.cafe.DTO.Recipe;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RecipeGUI extends JPanel {
    private RecipeBLL recipeBLL = new RecipeBLL();
    private int decentralizationMode;
    private DataTable dataTable;
    private RoundPanel recipe;
    private RoundPanel roundPanel1;
    private RoundPanel roundPanel2;
    private RoundPanel search;
    private JScrollPane scrollPane;
    private JPanel pnlRecipeConfiguration;
    private JPanel showImg;
    private JPanel mode;
    private JLabel[] jLabelsForm;
    private JComboBox<Object> cbbSearchFilter;
    private JComboBox<Object> cbbProductID;
    private JComboBox<Object> cbbProductIDSearch;
    private JComboBox<Object> cbbIngredientID;
    private JComboBox<Object> cbbIngredientIDSearch;
    private JComboBox<Object> cbbUnit;
    private JComboBox<Object> cbbUnitSearch;
    private JTextField txtSearch;
    private JTextField[] jTextFieldsForm;
    private Button btAdd;
    private Button btUpd;
    private Button btRef;

    public RecipeGUI(int decentralizationMode) {
        System.gc();
        this.decentralizationMode = decentralizationMode;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(70, 67, 67));
        initComponents();
    }

    public void initComponents() {
        List<String> columnNames = recipeBLL.getRecipeDAL().getColumnNames();
        ProductBLL productBLL = new ProductBLL();
        IngredientBLL ingredientBLL = new IngredientBLL();
        List<Object> productsID = productBLL.getObjectsProperty("PRODUCT_ID", productBLL.getProductList());
        List<Object> ingredientsID = ingredientBLL.getObjectsProperty("INGREDIENT_ID", ingredientBLL.getIngredientList());

        recipe = new RoundPanel();
        roundPanel1 = new RoundPanel();
        roundPanel2 = new RoundPanel();
        search = new RoundPanel();
        pnlRecipeConfiguration = new JPanel();
        showImg = new JPanel();
        mode = new JPanel();
        jLabelsForm = new JLabel[columnNames.size() - 1];
        cbbSearchFilter = new JComboBox<>(new String[]{"Mã công thức", "Mã sản phẩm", "Mã nguyên liệu", "Định lượng", "Đơn vị"});
        cbbProductID = new JComboBox<>(productsID.toArray());
        cbbProductIDSearch = new JComboBox<>(productsID.toArray());
        cbbIngredientID = new JComboBox<>(ingredientsID.toArray());
        cbbIngredientIDSearch = new JComboBox<>(ingredientsID.toArray());
        cbbUnit = new JComboBox<>(new String [] {"kg", "l", "bag"});
        cbbUnitSearch = new JComboBox<>(new String [] {"kg", "l", "bag"});
        txtSearch = new JTextField(20);
        jTextFieldsForm = new JTextField [columnNames.size()-4];
        btAdd = new Button();
        btUpd = new Button();
        btRef = new Button();

        recipe.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        recipe.setBackground(new Color(70, 67, 67));
        this.add(recipe, BorderLayout.CENTER);

        roundPanel1.setLayout(new BorderLayout(0, 10));
        roundPanel1.setBackground(new Color(70, 67, 67));
        roundPanel1.setPreferredSize(new Dimension(635, 680));
        roundPanel1.setAutoscrolls(true);
        roundPanel1.add(new JScrollPane(dataTable), BorderLayout.CENTER);
        recipe.add(roundPanel1);

        roundPanel2.setLayout(new BorderLayout());
        roundPanel2.setBackground(new Color(0xFFFFFF));
        roundPanel2.setPreferredSize(new Dimension(350, 680));
        roundPanel2.setAutoscrolls(true);
        recipe.add(roundPanel2);

        search.setLayout(new FlowLayout());
        search.setBackground(new Color(0xFFFFFF));
        search.setPreferredSize(new Dimension(635, 35));
        roundPanel1.add(search, BorderLayout.NORTH);

        cbbSearchFilter.addActionListener(e -> selectSearchFilter());
        search.add(cbbSearchFilter);
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchRecipes();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchRecipes();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchRecipes();
            }
        });
        search.add(txtSearch);
        cbbProductIDSearch.setVisible(false);
        cbbProductIDSearch.addItemListener(e -> productIDSearch());
        search.add(cbbProductIDSearch);
        cbbIngredientIDSearch.setVisible(false);
        cbbIngredientIDSearch.addItemListener(e -> ingredientIDSearch());
        search.add(cbbIngredientIDSearch);
        cbbUnitSearch.setVisible(false);
        cbbUnitSearch.addItemListener(e -> unitSearch());
        search.add(cbbUnitSearch);

        dataTable = new DataTable(recipeBLL.getData(), new String[]{"Mã công thức", "Mã sản phẩm", "Mã nguyên liệu", "Định lượng", "Đơn vị"}, e -> fillForm());
        scrollPane = new JScrollPane(dataTable);
        roundPanel1.add(scrollPane);

        pnlRecipeConfiguration.setLayout(new GridLayout(5, 2, 20, 20));
        pnlRecipeConfiguration.setBackground(new Color(0xFFFFFF));
        pnlRecipeConfiguration.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
        pnlRecipeConfiguration.setPreferredSize(new Dimension(635, 250));
        roundPanel2.add(pnlRecipeConfiguration, BorderLayout.NORTH);

        int index = 0;
        for (int i = 0; i < columnNames.size() - 1; i++) {
            jLabelsForm[i] = new JLabel();
            pnlRecipeConfiguration.add(jLabelsForm[i]);
            switch (columnNames.get(i)) {
                case "RECIPE_ID" -> {
                    jLabelsForm[i].setText("Mã công thức: ");
                    jTextFieldsForm[index] = new JTextField(recipeBLL.getAutoID());
                    jTextFieldsForm[index].setEnabled(false);
                    jTextFieldsForm[index].setBorder(null);
                    jTextFieldsForm[index].setDisabledTextColor(new Color(0x000000));
                    pnlRecipeConfiguration.add(jTextFieldsForm[index]);
                    index++;
                }
                case "PRODUCT_ID" -> {
                    jLabelsForm[i].setText("Mã sản phẩm: ");
                    pnlRecipeConfiguration.add(cbbProductID);
                }
                case "INGREDIENT_ID" -> {
                    jLabelsForm[i].setText("Mã nguyên liệu: ");
                    pnlRecipeConfiguration.add(cbbIngredientID);
                }
                case "MASS" -> {
                    jLabelsForm[i].setText("Định lượng: ");
                    jTextFieldsForm[index] = new JTextField();
                    jTextFieldsForm[index].setText(null);
                    jTextFieldsForm[index].addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            char c = e.getKeyChar();
                            if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != '.') {
                                e.consume();
                            }
                        }
                    });
                    pnlRecipeConfiguration.add(jTextFieldsForm[index]);
                    index++;
                }
                case "UNIT" -> {
                    jLabelsForm[i].setText("Đơn vị: ");
                    pnlRecipeConfiguration.add(cbbUnit);
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
                        addRecipe();
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
                        updateRecipe();
                    }
                }
            });
            mode.add(btUpd);
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
            dataTable.setRowSelectionInterval(0, 0);
            fillForm();
        }
    }

    private void unitSearch() {
        loadDataTable(recipeBLL.findRecipes("UNIT", Objects.requireNonNull(cbbUnitSearch.getSelectedItem()).toString()));
    }

    private void ingredientIDSearch() {
        loadDataTable(recipeBLL.findRecipes("INGREDIENT_ID", Objects.requireNonNull(cbbIngredientIDSearch.getSelectedItem()).toString()));
    }

    private void productIDSearch() {
        loadDataTable(recipeBLL.findRecipes("PRODUCT_ID", Objects.requireNonNull(cbbProductIDSearch.getSelectedItem()).toString()));
    }
    private void selectSearchFilter() {
        if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("Đơn vị")) {
            txtSearch.setVisible(false);
            cbbIngredientIDSearch.setVisible(false);
            cbbProductIDSearch.setVisible(false);
            cbbUnitSearch.setSelectedIndex(0);
            cbbUnitSearch.setVisible(true);
            unitSearch();
        } else if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("Mã nguyên liệu")) {
            txtSearch.setVisible(false);
            cbbUnitSearch.setVisible(false);
            cbbProductIDSearch.setVisible(false);
            cbbIngredientIDSearch.setSelectedIndex(0);
            cbbIngredientIDSearch.setVisible(true);
            ingredientIDSearch();
        } else if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("Mã sản phẩm")) {
            txtSearch.setVisible(false);
            cbbUnitSearch.setVisible(false);
            cbbIngredientIDSearch.setVisible(false);
            cbbProductIDSearch.setSelectedIndex(0);
            cbbProductIDSearch.setVisible(true);
            productIDSearch();
        } else {
            cbbIngredientIDSearch.setVisible(false);
            cbbProductIDSearch.setVisible(false);
            cbbUnitSearch.setVisible(false);
            txtSearch.setVisible(true);
            searchRecipes();
        }
    }

    public void searchRecipes() {
        if (txtSearch.getText().isEmpty()) {
            loadDataTable(recipeBLL.getRecipeList());
        } else {
            String key = null;
            switch (cbbSearchFilter.getSelectedIndex()) {
                case 0 -> key = "RECIPE_ID";
                case 3 -> key = "MASS";
                default -> {
                }
            }
            assert key != null;
            loadDataTable(recipeBLL.findRecipes(key, txtSearch.getText()));
        }
    }

    public void addRecipe() {
        if (checkInput()) {
            Recipe newRecipe = getForm();
            if (recipeBLL.exists(newRecipe))
                JOptionPane.showMessageDialog(this, "Công thức đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (recipeBLL.exists(Map.of("PRODUCT_ID", newRecipe.getProductID(), "INGREDIENT_ID", newRecipe.getIngredientID())))
                JOptionPane.showMessageDialog(this, "Công thức đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (recipeBLL.addRecipe(newRecipe))
                JOptionPane.showMessageDialog(this, "Thêm công thức mới thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Thêm công thức mới thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            refreshForm();
        }
    }

    public void updateRecipe() {
        if (checkInput()) {
            Recipe newRecipe = getForm();
            int selectedRow = dataTable.getSelectedRow();
            String currentProductID = dataTable.getValueAt(selectedRow, 1).toString();
            String currentIngredientID = dataTable.getValueAt(selectedRow, 2).toString();
            boolean valueChanged = !newRecipe.getProductID().equals(currentProductID) || !newRecipe.getIngredientID().equals(currentIngredientID);
            if (recipeBLL.exists(newRecipe))
                JOptionPane.showMessageDialog(this, "Công thức đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (valueChanged && recipeBLL.exists(Map.of("PRODUCT_ID", newRecipe.getProductID(), "INGREDIENT_ID", newRecipe.getIngredientID())))
                JOptionPane.showMessageDialog(this, "Công thức đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (recipeBLL.updateRecipe(newRecipe))
                JOptionPane.showMessageDialog(this, "Sửa công thức thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Sửa công thức thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            loadDataTable(recipeBLL.getRecipeList());
            dataTable.setRowSelectionInterval(selectedRow, selectedRow);
            fillForm();
        }
    }

    public void refreshForm() {
        cbbSearchFilter.setSelectedIndex(0);
        txtSearch.setText(null);
        loadDataTable(recipeBLL.getRecipeList());
        jTextFieldsForm[0].setText(recipeBLL.getAutoID());
        jTextFieldsForm[1].setText(null);
        cbbProductID.setSelectedItem(0);
        cbbIngredientID.setSelectedItem(0);
        cbbUnit.setSelectedItem(0);
        btAdd.setEnabled(true);
        btUpd.setEnabled(false);
    }

    public void fillForm() {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        Object[] rowData = model.getDataVector().elementAt(dataTable.getSelectedRow()).toArray();
        String[] data = new String[rowData.length];
        for (int i = 0; i < rowData.length; i++) {
            data[i] = rowData[i].toString();
        }
        String[] recipe = String.join(" | ", data).split(" \\| ");
        jTextFieldsForm[0].setText(recipe[0]);
        cbbProductID.setSelectedItem(recipe[1]);
        cbbIngredientID.setSelectedItem(recipe[2]);
        jTextFieldsForm[1].setText(recipe[3]);
        cbbUnit.setSelectedItem(recipe[4]);
        btAdd.setEnabled(false);
        btUpd.setEnabled(true);
    }

    public Recipe getForm() {
        String recipeID;
        String productID;
        String ingredientID;
        double mass;
        String unit;
        recipeID = jTextFieldsForm[0].getText();
        productID = Objects.requireNonNull(cbbProductID.getSelectedItem()).toString();
        ingredientID = Objects.requireNonNull(cbbIngredientID.getSelectedItem()).toString();
        mass = Double.parseDouble(jTextFieldsForm[1].getText());
        unit = Objects.requireNonNull(cbbUnit.getSelectedItem()).toString();
        return new Recipe(recipeID, productID, ingredientID, mass, unit, false);
    }

    public void loadDataTable(List<Recipe> recipeList) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);
        for (Recipe recipe : recipeList) {
            model.addRow(new Object[]{recipe.getRecipeID(), recipe.getProductID(), recipe.getIngredientID(), recipe.getMass(), recipe.getUnit()});
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
        if (!jTextFieldsForm[1].getText().matches("^(?=.*\\d)\\d*\\.?\\d*$")) {
            // Mass must be a double >= 0.0
            jTextFieldsForm[1].requestFocusInWindow();
            jTextFieldsForm[1].selectAll();
            JOptionPane.showMessageDialog(this, "Định lượng phải là số thực không âm", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

}
