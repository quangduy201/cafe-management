package com.cafe.GUI;

import com.cafe.BLL.IngredientBLL;
import com.cafe.BLL.ProductBLL;
import com.cafe.BLL.RecipeBLL;
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
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecipeGUI extends JPanel {
    private RecipeBLL recipeBLL = new RecipeBLL();
    private DataTable dataTable;
    private RoundPanel recipe;
    private RoundPanel roundPanel1;
    private RoundPanel roundPanel2;
    private RoundPanel search;
    private JScrollPane scrollPane;
    private JPanel pnlRecipeConfiguration;
    private JPanel showImg;
    private JPanel mode;
    private JLabel jLabelsForm[];
    private JComboBox<Object> cbbSearchFilter;
    private JComboBox<Object> cbbProductID;
    private JComboBox<Object> cbbProductIDSearch;
    private JComboBox<Object> cbbIngredientID;
    private JComboBox<Object> cbbIngredientIDSearch;
    private JComboBox<Object> cbbUnit;
    private JComboBox<Object> cbbUnitSearch;
    private JTextField txtSearch;
    private JTextField jTextFieldsForm;
    private Button btAdd;
    private Button btUpd;
    private Button btRef;
    public RecipeGUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(70, 67, 67));
        initComponents();
    }

    public void initComponents() {
        List<String> columsName = recipeBLL.getRecipeDAL().getColumnNames();
        ProductBLL productBLL = new ProductBLL();
        IngredientBLL ingredientBLL = new IngredientBLL();
        List<String> productsID = new ArrayList<>();
        List<String> ingredientsID = new ArrayList<>();
        for (int i = 0; i < productBLL.getProductList().size(); i++) {
            productsID.add(productBLL.getValueByKey(productBLL.getProductList().get(i), "PRODUCT_ID").toString());
        }
        for (int i = 0; i < ingredientBLL.getIngredientList().size(); i++) {
            ingredientsID.add(ingredientBLL.getValueByKey(ingredientBLL.getIngredientList().get(i), "INGREDIENT_ID").toString());
        }
        recipe = new RoundPanel();
        roundPanel1 = new RoundPanel();
        roundPanel2 = new RoundPanel();
        search = new RoundPanel();
        pnlRecipeConfiguration = new JPanel();
        showImg = new JPanel();
        mode = new JPanel();
        jLabelsForm = new JLabel[columsName.size() - 1];
        cbbSearchFilter = new JComboBox<>(new String [] {columsName.get(2), columsName.get(0), columsName.get(1), columsName.get(3)});
        cbbProductID = new JComboBox<>(productsID.toArray());
        cbbProductIDSearch = new JComboBox<>(productsID.toArray());
        cbbIngredientID = new JComboBox<>(ingredientsID.toArray());
        cbbIngredientIDSearch = new JComboBox<>(ingredientsID.toArray());
        cbbUnit = new JComboBox<>(new String [] {"kg", "l", "bag"});
        cbbUnitSearch = new JComboBox<>(new String [] {"kg", "l", "bag"});
        txtSearch = new JTextField(20);
        jTextFieldsForm = new JTextField(20);
        btAdd = new Button();
        btUpd = new Button();
        btRef = new Button();

        recipe.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        recipe.setBackground(new Color(51, 51, 51));
        this.add(recipe, BorderLayout.CENTER);

        roundPanel1.setLayout(new BorderLayout(0, 10));
        roundPanel1.setBackground(new Color(51, 51, 51));
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
        cbbProductIDSearch.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                productIDSearch();
            }
        });
        search.add(cbbProductIDSearch);
        cbbIngredientIDSearch.setVisible(false);
        cbbIngredientIDSearch.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                ingredientIDSearch();
            }
        });
        search.add(cbbIngredientIDSearch);
        cbbUnitSearch.setVisible(false);
        cbbUnitSearch.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                uniSearch();
            }
        });
        search.add(cbbUnitSearch);

        dataTable = new DataTable(recipeBLL.getData(), columsName.subList(0, columsName.size() - 1).toArray(), getListSelectionListener());
        scrollPane = new JScrollPane(dataTable);
        roundPanel1.add(scrollPane);

        pnlRecipeConfiguration.setLayout(new GridLayout(4, 2, 20, 20));
        pnlRecipeConfiguration.setBackground(new Color(0xFFFFFF));
        pnlRecipeConfiguration.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
        pnlRecipeConfiguration.setPreferredSize(new Dimension(635, 200));
        roundPanel2.add(pnlRecipeConfiguration, BorderLayout.NORTH);

        for (int i = 0; i < columsName.size() - 1; i++) {
            jLabelsForm[i] = new JLabel();
            jLabelsForm[i].setText(columsName.get(i) + ": ");
            pnlRecipeConfiguration.add(jLabelsForm[i]);
            switch (columsName.get(i)) {
                case "PRODUCT_ID" -> pnlRecipeConfiguration.add(cbbProductID);
                case "INGREDIENT_ID" -> pnlRecipeConfiguration.add(cbbIngredientID);
                case "UNIT" -> pnlRecipeConfiguration.add(cbbUnit);
                default -> {
                    jTextFieldsForm = new JTextField();
                    jTextFieldsForm.setText(null);
                    pnlRecipeConfiguration.add(jTextFieldsForm);
                }
            }
        }
        showImg.setLayout(new FlowLayout());
        showImg.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        showImg.setPreferredSize(new Dimension(635, 350));
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
                    addRecipe();
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
                    updateRecipe();
                }
            }
        });
        mode.add(btUpd);

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
        loadDataTable(recipeBLL.findRecipes("UNIT", Objects.requireNonNull(cbbUnitSearch.getSelectedItem()).toString()));
    }

    private void ingredientIDSearch() {
        loadDataTable(recipeBLL.findRecipes("INGREDIENT_ID", Objects.requireNonNull(cbbIngredientIDSearch.getSelectedItem()).toString()));
    }

    private void productIDSearch() {
        loadDataTable(recipeBLL.findRecipes("PRODUCT_ID", Objects.requireNonNull(cbbProductIDSearch.getSelectedItem()).toString()));
    }
    private void selectSearchFilter() {
        if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("UNIT")) {
            txtSearch.setVisible(false);
            cbbIngredientIDSearch.setVisible(false);
            cbbProductIDSearch.setVisible(false);
            cbbUnitSearch.setSelectedIndex(0);
            cbbUnitSearch.setVisible(true);
            uniSearch();
        } else if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("INGREDIENT_ID")) {
            txtSearch.setVisible(false);
            cbbUnitSearch.setVisible(false);
            cbbProductIDSearch.setVisible(false);
            cbbIngredientIDSearch.setSelectedIndex(0);
            cbbIngredientIDSearch.setVisible(true);
            ingredientIDSearch();
        } else if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("PRODUCT_ID")) {
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

    public ActionListener getListSelectionListener() {
        return e -> {
            DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
            String rowData = model.getDataVector().elementAt(dataTable.getSelectedRow()).toString();
            String[] recipe = rowData.substring(1, rowData.length() - 1).split(", ");
            cbbProductID.setSelectedItem(recipe[0]);
            cbbIngredientID.setSelectedItem(recipe[1]);
            jTextFieldsForm.setText(recipe[2]);
            cbbUnit.setSelectedItem(recipe[3]);
            btAdd.setEnabled(false);
            btUpd.setEnabled(true);
        };
    }

    public void searchRecipes() {
        if (txtSearch.getText().isEmpty()) {
            loadDataTable(recipeBLL.getRecipeList());
        } else {
            loadDataTable(recipeBLL.findRecipes(Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString(), txtSearch.getText()));
        }
    }

    public void addRecipe() {
        if (checkInput()) {
            List<Recipe> list = recipeBLL.searchRecipes("PRODUCT_ID = '" + Objects.requireNonNull(cbbProductID.getSelectedItem()).toString() + "'",
                "INGREDIENT_ID = '" + Objects.requireNonNull(cbbIngredientID.getSelectedItem()).toString() + "'");
            if (list.size()!=0) {
                JOptionPane.showMessageDialog(this, "PRODUCT_ID and INGREDIENT_ID exists!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Recipe newRecipe;
            String productID;
            String ingredientID;
            double mass;
            String unit;
            productID = Objects.requireNonNull(cbbProductID.getSelectedItem()).toString();
            ingredientID = Objects.requireNonNull(cbbProductID.getSelectedItem()).toString();
            mass = Double.parseDouble(jTextFieldsForm.getText());
            unit = Objects.requireNonNull(cbbProductID.getSelectedItem()).toString();
            newRecipe = new Recipe(productID, ingredientID, mass, unit, false);
            recipeBLL.addRecipe(newRecipe);
            refreshForm();
        }
    }

    public void updateRecipe() {
        if (checkInput()) {
            Recipe newRecipe;
            String productID;
            String ingredientID;
            double mass;
            String unit;
            productID = Objects.requireNonNull(cbbProductID.getSelectedItem()).toString();
            ingredientID = Objects.requireNonNull(cbbProductID.getSelectedItem()).toString();
            mass = Double.parseDouble(jTextFieldsForm.getText());
            unit = Objects.requireNonNull(cbbProductID.getSelectedItem()).toString();
            newRecipe = new Recipe(productID, ingredientID, mass, unit, false);
            recipeBLL.updateRecipe(newRecipe);
            loadDataTable(recipeBLL.getRecipeList());
        }
    }

    public void refreshForm() {
        cbbSearchFilter.setSelectedIndex(0);
        txtSearch.setText(null);
        recipeBLL = new RecipeBLL();
        loadDataTable(recipeBLL.getRecipeList());
        cbbProductID.setSelectedItem(0);
        cbbIngredientID.setSelectedItem(0);
        jTextFieldsForm.setText(null);
        cbbUnit.setSelectedItem(0);
        btAdd.setEnabled(true);
        btUpd.setEnabled(false);
    }

    public void loadDataTable(List<Recipe> recipeList) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);
        for (Recipe recipe : recipeList) {
            model.addRow(new Object[]{recipe.getProductID(), recipe.getIngredientID(), recipe.getMass(), recipe.getUnit()});
        }
    }

    public boolean checkInput() {
        if (jTextFieldsForm.getText().isEmpty()) {
            System.out.println(jTextFieldsForm.getText());
            JOptionPane.showMessageDialog(this, "Please fill in information!", "Notification", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        try {
            Double.parseDouble(jTextFieldsForm.getText());
        } catch (NumberFormatException exception) {
            jTextFieldsForm.setText(null);
            JOptionPane.showMessageDialog(this, "Invalid data input!", "Notification", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }

}
