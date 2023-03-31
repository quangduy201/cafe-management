package com.cafe.GUI;

import com.cafe.BLL.CategoryBLL;
import com.cafe.BLL.ProductBLL;
import com.cafe.DTO.Product;
import com.cafe.custom.Button;
import com.cafe.custom.DataTable;
import com.cafe.custom.RoundPanel;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Objects;

public class ProductGUI extends JPanel {
    private ProductBLL productBLL = new ProductBLL();
    private DataTable dataTable;
    private RoundPanel product;
    private RoundPanel roundPanel1;
    private RoundPanel roundPanel2;
    private RoundPanel search;
    private JScrollPane scrollPane;
    private JPanel pnlProductConfiguration;
    private JPanel showImg;
    private JPanel mode;
    private JLabel jLabelsForm [];
    private JLabel imgProduct;
    private JComboBox<Object> cbbSearchFilter;
    private JComboBox<Object> cbbCategoryID;
    private JComboBox<Object> cbbSize;
    private JTextField txtSearch;
    private JTextField jTextFieldsForm [];
    private JButton btChooseImg;
    private Button btAdd;
    private Button btUpd;
    private Button btDel;
    private Button btRef;
    private String chosenImg = null;
    public ProductGUI() {
        setLayout(new BorderLayout(10,10));
        setBackground(new Color(51,51,51));
        initComponents();
    }

    public void initComponents() {
        List<String> columsName = productBLL.getProductDAL().getColumnsName();
        CategoryBLL categoryBLL = new CategoryBLL();
        List<String> categoriesID = new ArrayList<>();
        for (int i = 0; i < categoryBLL.getCategoryList().size(); i++) {
            categoriesID.add(categoryBLL.getValueByKey(categoryBLL.getCategoryList().get(i), "CATEGORY_ID").toString());
        }

        product = new RoundPanel();
        roundPanel1 = new RoundPanel();
        roundPanel2 = new RoundPanel();
        search = new RoundPanel();
        pnlProductConfiguration = new JPanel();
        showImg = new JPanel();
        mode = new JPanel();
        jLabelsForm = new JLabel[columsName.size()-1];
        imgProduct = new JLabel();
        cbbSearchFilter = new JComboBox<>(columsName.subList(0, columsName.size() - 2).toArray());
        cbbCategoryID = new JComboBox<>(categoriesID.toArray());
        cbbSize = new JComboBox<>(new String[]{"null","S","M","L"});
        txtSearch = new JTextField(20);
        jTextFieldsForm = new JTextField[columsName.size()-4];
        btChooseImg = new JButton();
        btAdd = new Button();
        btUpd = new Button();
        btDel = new Button();
        btRef = new Button();

        product.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        product.setBackground(new Color(51, 51, 51));
        this.add(product, BorderLayout.CENTER);

        roundPanel1.setLayout(new BorderLayout(0, 10));
        roundPanel1.setBackground(new Color(51, 51, 51));
        roundPanel1.setPreferredSize(new Dimension(635, 680));
        roundPanel1.setAutoscrolls(true);
        roundPanel1.add(new JScrollPane(dataTable), BorderLayout.CENTER);
        product.add(roundPanel1);

        roundPanel2.setLayout(new BorderLayout());
        roundPanel2.setBackground(new Color(0xFFFFFF));
        roundPanel2.setPreferredSize(new Dimension(350, 680));
        roundPanel2.setAutoscrolls(true);
        product.add(roundPanel2);

        search.setLayout(new FlowLayout());
        search.setBackground(new Color(0xFFFFFF));
        search.setPreferredSize(new Dimension(635, 35));
        roundPanel1.add(search, BorderLayout.NORTH);


        search.add(cbbSearchFilter);
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchProducts();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchProducts();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchProducts();
            }
        });
        search.add(txtSearch);

        dataTable = new DataTable(productBLL.getData(), columsName.subList(0, columsName.size() - 2).toArray(), getListSelectionListener());
        scrollPane = new JScrollPane(dataTable);
        roundPanel1.add(scrollPane);

        pnlProductConfiguration.setLayout(new GridLayout(6, 2, 20, 20));
        pnlProductConfiguration.setBackground(new Color(0xFFFFFF));
        pnlProductConfiguration.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
        pnlProductConfiguration.setPreferredSize(new Dimension(635, 300));
        roundPanel2.add(pnlProductConfiguration, BorderLayout.NORTH);

        int index = 0;
        for (int i=0; i<columsName.size()-1; i++) {
            jLabelsForm[i] = new JLabel();
            jLabelsForm[i].setText(columsName.get(i)+": ");
            pnlProductConfiguration.add(jLabelsForm[i]);
            switch (columsName.get(i)) {
                case "PRODUCT_ID" -> {
                    jTextFieldsForm[index] = new JTextField(productBLL.getAutoID());
                    jTextFieldsForm[index].setEnabled(false);
                    pnlProductConfiguration.add(jTextFieldsForm[index]);
                    index++;
                }
                case "CATEGORY_ID" -> pnlProductConfiguration.add(cbbCategoryID);
                case "SIZED" -> pnlProductConfiguration.add(cbbSize);
                case "IMAGE" -> {
                    btChooseImg.setText("Choose an image");
                    btChooseImg.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                    btChooseImg.setFocusPainted(false);
                    btChooseImg.addActionListener(this::btnProductImageActionPerformed);
                    pnlProductConfiguration.add(btChooseImg);
                }
                default -> {
                    jTextFieldsForm[index] = new JTextField();
                    jTextFieldsForm[index].setText(null);
                    pnlProductConfiguration.add(jTextFieldsForm[index]);
                    index++;
                }
            }
        }
        showImg.setLayout(new FlowLayout());
        showImg.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        showImg.setPreferredSize(new Dimension(635, 250));
        showImg.setBackground(new Color(0xFFFFFF));
        roundPanel2.add(showImg, BorderLayout.CENTER);

        showImg.add(imgProduct);

        mode.setLayout(new GridLayout(2,2,20,20));
        mode.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
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
            public void mouseClicked(MouseEvent e) {
                if (btAdd.isEnabled()){
                    addProduct();
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
            public void mouseClicked(MouseEvent e) {
                if (btUpd.isEnabled()){
                    updateProduct();
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
            public void mouseClicked(MouseEvent e) {
                if (btDel.isEnabled()){
                    deleteProduct();
                }
            }
        });
        mode.add(btDel);

        btRef.setBackground(new Color(35, 166, 97));
        btRef.setBorder(null);
        btRef.setIcon(new ImageIcon("img/refresh.png"));
        btRef.setText("  Refesh");
        btRef.setColor(new Color(240, 240, 240));
        btRef.setColorClick(new Color(141, 222, 175));
        btRef.setColorOver(new Color(35, 166, 97));
        btRef.setFocusPainted(false);
        btRef.setRadius(20);
        btRef.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                refeshForm();
            }
        });
        mode.add(btRef);

    }


    public ActionListener getListSelectionListener() {
        return e -> {
            String[] product = (String[]) productBLL.getData()[dataTable.getSelectedRow()];
            jTextFieldsForm[0].setText(product[0]);
            jTextFieldsForm[1].setText(product[1]);
            cbbCategoryID.setSelectedItem(product[2]);
            cbbSize.setSelectedItem(product[3]);
            jTextFieldsForm[2].setText(product[4]);
            for (Product product1: productBLL.getProductList()) {
                if (product1.getProductID().equals(product[0])) {
                    chosenImg = product1.getImage();
                    break;
                }
            }
            imgProduct.setIcon(new ImageIcon(chosenImg));
            btAdd.setEnabled(false);
            btUpd.setEnabled(true);
            btDel.setEnabled(true);
        };
    }

    public void searchProducts(){
        if (txtSearch.getText().isEmpty()){
            loadDataTable(productBLL.getProductList());
        } else {
            loadDataTable(productBLL.findProducts(Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString(), txtSearch.getText()));
        }
    }

    private void btnProductImageActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fc = new JFileChooser();
        fc.removeChoosableFileFilter(fc.getFileFilter());
        fc.setCurrentDirectory(new File("img"));
        FileFilter filter = new FileNameExtensionFilter("Images (.jpeg, .jpg, .png)","jpeg","jpg","png");
        fc.setFileFilter(filter);
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            chosenImg = "img/".concat(file.getName());
            imgProduct.setIcon(new ImageIcon(chosenImg));
        }
    }

    public void addProduct(){
        if (checkInput()){
            Product newProduct;
            String productID = null;
            String name = null;
            String categoryID;
            String size;
            String image;
            double cost = 0;
            for (int i=0; i<jTextFieldsForm.length; i++) {
                switch (i) {
                    case 0 -> productID = jTextFieldsForm[i].getText();
                    case 1 -> name = jTextFieldsForm[i].getText();
                    case 2 -> cost = Double.parseDouble(jTextFieldsForm[i].getText());
                    default -> {}
                }
            }
            categoryID = Objects.requireNonNull(cbbCategoryID.getSelectedItem()).toString();
            size = Objects.requireNonNull(cbbSize.getSelectedItem()).toString();
            image = chosenImg;
            assert size != null;
            newProduct = new Product(productID, name, categoryID, size, cost, image, "0");
            productBLL.insertProduct(newProduct);
            refeshForm();
        }
    }

    public void updateProduct(){
        if (checkInput()){
            Product newProduct;
            String productID = null;
            String name = null;
            String categoryID;
            String size;
            String image;
            double cost = 0;
            for (int i=0; i<jTextFieldsForm.length; i++) {
                switch (i) {
                    case 0 -> productID = jTextFieldsForm[i].getText();
                    case 1 -> name = jTextFieldsForm[i].getText();
                    case 2 -> cost = Double.parseDouble(jTextFieldsForm[i].getText());
                    default -> {}
                }
            }
            categoryID = Objects.requireNonNull(cbbCategoryID.getSelectedItem()).toString();
            size = Objects.requireNonNull(cbbSize.getSelectedItem()).toString();
            image = chosenImg;
            assert size != null;
            newProduct = new Product(productID, name, categoryID, size, cost, image, "0");
            productBLL.updateProduct(newProduct);
            refeshForm();
        }
    }

    private void deleteProduct() {
        productBLL.removeProduct(jTextFieldsForm[0].getText());
        refeshForm();
    }

    public void refeshForm(){
        cbbSearchFilter.setSelectedIndex(0);
        txtSearch.setText(null);
        loadDataTable(productBLL.getProductList());
        jTextFieldsForm[0].setText(productBLL.getAutoID());
        for (int i=1; i<jTextFieldsForm.length; i++) {
            jTextFieldsForm[i].setText(null);
        }
        cbbCategoryID.setSelectedIndex(0);
        cbbSize.setSelectedIndex(0);
        chosenImg = null;
        imgProduct.setIcon(null);
        btAdd.setEnabled(true);
        btUpd.setEnabled(false);
        btDel.setEnabled(false);
    }

    public void loadDataTable (List<Product> productList){
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);
        for (Product product : productList) {
            model.addRow(new Object[]{product.getProductID(), product.getName(), product.getCategoryID(), product.getSized(), product.getCost()});
        }
    }

    public boolean checkInput(){
        for (JTextField textField : jTextFieldsForm){
            if (textField.getText().isEmpty()) {
                System.out.println(textField.getText());
                JOptionPane.showMessageDialog(this, "Please fill in information!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
        }
        if (chosenImg == null) {
            JOptionPane.showMessageDialog(this, "Please choose image product!", "Notification", JOptionPane.INFORMATION_MESSAGE);
            return false;
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
