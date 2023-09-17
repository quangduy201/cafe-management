package com.cafe.GUI;

import com.cafe.BLL.*;
import com.cafe.DTO.*;
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
import java.util.*;
import java.util.List;

public class RecipeGUI extends JPanel {
    private RecipeBLL recipeBLL = new RecipeBLL();
    private DecentralizationDetail decentralizationMode;
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
    private JComboBox<Object> cbbProductName;
    private JComboBox<Object> cbbProductNameSearch;
    private JComboBox<Object> cbbIngredientName;
    private JComboBox<Object> cbbIngredientNameSearch;
    private JComboBox<Object> cbbUnit;
    private JComboBox<Object> cbbUnitSearch;
    private JTextField txtSearch;
    private JTextField[] jTextFieldsForm;
    private Button btAdd;
    private Button btUpd;
    private Button btRef;


    public RecipeGUI(DecentralizationDetail decentralizationMode) {
        System.gc();
        this.decentralizationMode = decentralizationMode;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(70, 67, 67));
        initComponents();
    }
    private List<Product> productList = new ArrayList<>();
    private List<Ingredient> ingredientList = new ArrayList<>();

    public void initComponents() {
        List<String> columnNames = recipeBLL.getRecipeDAL().getColumnNames();
        ProductBLL productBLL = new ProductBLL();
        IngredientBLL ingredientBLL = new IngredientBLL();
        List<Object> productsName = productBLL.getObjectsProperty("NAME", productBLL.getProductList());
        List<Object> ingredientsName = ingredientBLL.getObjectsProperty("NAME", ingredientBLL.getIngredientList());
        productList = new ProductBLL().getProductList();
        ingredientList = new IngredientBLL().getIngredientList();

        recipe = new RoundPanel();
        roundPanel1 = new RoundPanel();
        roundPanel2 = new RoundPanel();
        search = new RoundPanel();
        pnlRecipeConfiguration = new JPanel();
        showImg = new JPanel();
        mode = new JPanel();
        jLabelsForm = new JLabel[columnNames.size() - 1];
        cbbSearchFilter = new JComboBox<>(new String[]{"Mã công thức", "Tên sản phẩm", "Tên nguyên liệu", "Định lượng", "Đơn vị"});
        cbbProductName = new JComboBox<>(productsName.toArray());
        cbbProductNameSearch = new JComboBox<>(productsName.toArray());
        cbbIngredientName = new JComboBox<>(ingredientsName.toArray());
        cbbIngredientNameSearch = new JComboBox<>(ingredientsName.toArray());
        cbbUnit = new JComboBox<>(new String[]{"kg", "l", "bag"});
        cbbUnitSearch = new JComboBox<>(new String[]{"kg", "l", "bag"});
        txtSearch = new JTextField(20);
        jTextFieldsForm = new JTextField[columnNames.size() - 4];
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
        roundPanel2.setPreferredSize(new Dimension(350, 680));
        roundPanel2.setAutoscrolls(true);
        recipe.add(roundPanel2);

        search.setLayout(new FlowLayout());
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
        cbbProductNameSearch.setVisible(false);
        cbbProductNameSearch.addItemListener(e -> productIDSearch());
        search.add(cbbProductNameSearch);
        cbbIngredientNameSearch.setVisible(false);
        cbbIngredientNameSearch.addItemListener(e -> ingredientIDSearch());
        search.add(cbbIngredientNameSearch);
        cbbUnitSearch.setVisible(false);
        cbbUnitSearch.addItemListener(e -> unitSearch());
        search.add(cbbUnitSearch);

        dataTable = new DataTable(null, new String[]{"Mã công thức", "Ten sản phẩm", "Ten nguyên liệu", "Định lượng", "Đơn vị"}, e -> fillForm());
        scrollPane = new JScrollPane(dataTable);
        roundPanel1.add(scrollPane);

        pnlRecipeConfiguration.setLayout(new GridLayout(5, 2, 20, 20));
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
                    jTextFieldsForm[index].setDisabledTextColor(null);
                    pnlRecipeConfiguration.add(jTextFieldsForm[index]);
                    index++;
                }
                case "PRODUCT_ID" -> {
                    jLabelsForm[i].setText("Tên sản phẩm: ");
                    pnlRecipeConfiguration.add(cbbProductName);
                }
                case "INGREDIENT_ID" -> {
                    jLabelsForm[i].setText("Tên nguyên liệu: ");
                    pnlRecipeConfiguration.add(cbbIngredientName);
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
        roundPanel2.add(showImg, BorderLayout.CENTER);

        mode.setLayout(new GridLayout(2, 2, 20, 20));
        mode.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mode.setPreferredSize(new Dimension(635, 130));
        roundPanel2.add(mode, BorderLayout.SOUTH);

        if (decentralizationMode.isCanADD()) {
            Button.configButton(btAdd, List.of("  Thêm", "img/icons/plus.png", true, (Runnable) this::addRecipe));
            mode.add(btAdd);
        }

        if (decentralizationMode.isCanEDIT()) {
            Button.configButton(btUpd, List.of("  Sửa", "img/icons/wrench.png", false, (Runnable) this::updateRecipe));
            mode.add(btUpd);
        }
        if (decentralizationMode.isCanADD()) {
            Button.configButton(btRef, List.of("  Làm mới", "img/icons/refresh.png", true, (Runnable) this::refreshForm));
            mode.add(btRef);
        } else {
            dataTable.setRowSelectionInterval(0, 0);
            fillForm();
        }
        refreshForm();
    }

    private void unitSearch() {
        loadDataTable(recipeBLL.findRecipes("UNIT", Objects.requireNonNull(cbbUnitSearch.getSelectedItem()).toString()));
    }

    private void ingredientIDSearch() {
        for(Ingredient ingredient : ingredientList) {
            if (ingredient.getName().equals(cbbIngredientNameSearch.getSelectedItem())) {
                loadDataTable(recipeBLL.findRecipes("INGREDIENT_ID", Objects.requireNonNull(ingredient.getIngredientID())));
                break;
            }
        }
    }

    private void productIDSearch() {
        for(Product product : productList) {
            if (product.getName().equals(cbbProductNameSearch.getSelectedItem())) {
                loadDataTable(recipeBLL.findRecipes("PRODUCT_ID", Objects.requireNonNull(product.getProductID())));
                break;
            }
        }
    }

    private void selectSearchFilter() {
        if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("Đơn vị")) {
            txtSearch.setVisible(false);
            cbbIngredientNameSearch.setVisible(false);
            cbbProductNameSearch.setVisible(false);
            cbbUnitSearch.setSelectedIndex(0);
            cbbUnitSearch.setVisible(true);
            unitSearch();
        } else if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("Tên nguyên liệu")) {
            txtSearch.setVisible(false);
            cbbUnitSearch.setVisible(false);
            cbbProductNameSearch.setVisible(false);
            cbbIngredientNameSearch.setSelectedIndex(0);
            cbbIngredientNameSearch.setVisible(true);
            ingredientIDSearch();
        } else if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("Tên sản phẩm")) {
            txtSearch.setVisible(false);
            cbbUnitSearch.setVisible(false);
            cbbIngredientNameSearch.setVisible(false);
            cbbProductNameSearch.setSelectedIndex(0);
            cbbProductNameSearch.setVisible(true);
            productIDSearch();
        } else {
            cbbIngredientNameSearch.setVisible(false);
            cbbProductNameSearch.setVisible(false);
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
            product = new ProductBLL()
                .searchProducts("NAME = '" + dataTable.getValueAt(selectedRow, 1).toString() + "'")
                .get(0);
            ingredient = new IngredientBLL()
                .searchIngredients("NAME = '" + dataTable.getValueAt(selectedRow, 2).toString() + "'")
                .get(0);
            String currentProductID = product.getProductID();
            String currentIngredientID = ingredient.getIngredientID();
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
        cbbProductName.setSelectedItem(0);
        cbbIngredientName.setSelectedItem(0);
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
        cbbProductName.setSelectedItem(recipe[1]);
        cbbIngredientName.setSelectedItem(recipe[2]);
        jTextFieldsForm[1].setText(recipe[3]);
        cbbUnit.setSelectedItem(recipe[4]);
        btAdd.setEnabled(false);
        btUpd.setEnabled(true);
    }

    public Recipe getForm() {
        String recipeID;
        String productID = null;
        String ingredientID = null;
        double mass;
        String unit;
        recipeID = jTextFieldsForm[0].getText();
        for (Ingredient ingredient : ingredientList) {
            if (ingredient.getName().equals(cbbIngredientName.getSelectedItem())) {
                ingredientID = Objects.requireNonNull(ingredient.getIngredientID());
            }
        }
        for (Product product : productList) {
            if (product.getName().equals(cbbProductName.getSelectedItem())) {
                productID = Objects.requireNonNull(product.getProductID());
            }
        }
        System.out.println(ingredientID + productID);
        mass = Double.parseDouble(jTextFieldsForm[1].getText());
        unit = Objects.requireNonNull(cbbUnit.getSelectedItem()).toString();
        return new Recipe(recipeID, productID, ingredientID, mass, unit, false);
    }

    private String productName;
    private Product product;
    private Ingredient ingredient;
    private String ingredientName;
    public void loadDataTable(List<Recipe> recipeList) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);
        for (Recipe recipe : recipeList) {
            for(Ingredient ingredient : ingredientList) {
                if (ingredient.getIngredientID().equals(recipe.getIngredientID())) {
                    ingredientName = ingredient.getName();
                    break;
                }
            }
            for(Product product : productList) {
                if (product.getProductID().equals(recipe.getProductID())) {
                    productName = product.getName();
                    break;
                }
            }
            model.addRow(new Object[]{recipe.getRecipeID(), productName, ingredientName, recipe.getMass(), recipe.getUnit()});
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
