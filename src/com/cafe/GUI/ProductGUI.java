package com.cafe.GUI;

import com.cafe.BLL.CategoryBLL;
import com.cafe.BLL.ProductBLL;
import com.cafe.DTO.Product;
import com.cafe.custom.Button;
import com.cafe.custom.DataTable;
import com.cafe.custom.RoundPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProductGUI extends JPanel {
    private ProductBLL productBLL = new ProductBLL();
    private int decentralizationMode;
    private DataTable dataTable;
    private RoundPanel product;
    private RoundPanel roundPanel1;
    private RoundPanel roundPanel2;
    private RoundPanel search;
    private JScrollPane scrollPane;
    private JPanel pnlProductConfiguration;
    private JPanel showImg;
    private JPanel mode;
    private JLabel[] jLabelsForm;
    private JLabel imgProduct;
    private JComboBox<Object> cbbSearchFilter;
    private JComboBox<Object> cbbCategoryID;
    private JComboBox<Object> cbbCategoryIDSearch;
    private JComboBox<Object> cbbSize;
    private JComboBox<Object> cbbSizeSearch;
    private JTextField txtSearch;
    private JTextField[] jTextFieldsForm;
    private JButton btChooseImg;
    private Button btAdd;
    private Button btUpd;
    private Button btDel;
    private Button btRef;
    private String chosenImg = null;

    public ProductGUI(int decentralizationMode) {
        this.decentralizationMode = decentralizationMode;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(70, 67, 67));
        initComponents();
    }

    public void initComponents() {
        List<String> columnNames = productBLL.getProductDAL().getColumnNames();
        CategoryBLL categoryBLL = new CategoryBLL();
        List<Object> categoriesID = categoryBLL.getObjectsProperty("CATEGORY_ID", categoryBLL.getCategoryList());

        product = new RoundPanel();
        roundPanel1 = new RoundPanel();
        roundPanel2 = new RoundPanel();
        search = new RoundPanel();
        pnlProductConfiguration = new JPanel();
        showImg = new JPanel();
        mode = new JPanel();
        jLabelsForm = new JLabel[columnNames.size() - 1];
        imgProduct = new JLabel();
        cbbSearchFilter = new JComboBox<>(new String[]{"Mã sản phẩm", "Tên sản phẩm", "Mã thể loại", "Size", "Giá"});
        cbbCategoryID = new JComboBox<>(categoriesID.toArray());
        cbbCategoryIDSearch = new JComboBox<>(categoriesID.toArray());
        cbbSize = new JComboBox<>(new String[]{"null", "S", "M", "L"});
        cbbSizeSearch = new JComboBox<>(new String[]{"null", "S", "M", "L"});
        txtSearch = new JTextField(20);
        jTextFieldsForm = new JTextField[columnNames.size() - 4];
        btChooseImg = new JButton();
        btAdd = new Button();
        btUpd = new Button();
        btDel = new Button();
        btRef = new Button();

        product.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        product.setBackground(new Color(70, 67, 67));
        this.add(product, BorderLayout.CENTER);

        roundPanel1.setLayout(new BorderLayout(0, 10));
        roundPanel1.setBackground(new Color(70, 67, 67));
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

        cbbSearchFilter.addActionListener(e -> selectSearchFilter());
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
        cbbCategoryIDSearch.setVisible(false);
        cbbCategoryIDSearch.addItemListener(e -> categoryIDSearch());
        search.add(cbbCategoryIDSearch);
        cbbSizeSearch.setVisible(false);
        cbbSizeSearch.addItemListener(e -> sizeSearch());
        search.add(cbbSizeSearch);

        dataTable = new DataTable(productBLL.getData(), new String[]{"Mã sản phẩm", "Tên sản phẩm", "Mã thể loại", "Size", "Giá"}, e -> fillForm());
        scrollPane = new JScrollPane(dataTable);
        roundPanel1.add(scrollPane);

        pnlProductConfiguration.setLayout(new GridLayout(6, 2, 20, 20));
        pnlProductConfiguration.setBackground(new Color(0xFFFFFF));
        pnlProductConfiguration.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
        pnlProductConfiguration.setPreferredSize(new Dimension(635, 300));
        roundPanel2.add(pnlProductConfiguration, BorderLayout.NORTH);

        int index = 0;
        for (int i = 0; i < columnNames.size() - 1; i++) {
            jLabelsForm[i] = new JLabel();
            pnlProductConfiguration.add(jLabelsForm[i]);
            switch (columnNames.get(i)) {
                case "PRODUCT_ID" -> {
                    jLabelsForm[i].setText("Mã sản phẩm: ");
                    jTextFieldsForm[index] = new JTextField(productBLL.getAutoID());
                    jTextFieldsForm[index].setEnabled(false);
                    jTextFieldsForm[index].setBorder(null);
                    jTextFieldsForm[index].setDisabledTextColor(new Color(0x000000));
                    pnlProductConfiguration.add(jTextFieldsForm[index]);
                    index++;
                }
                case "NAME" -> {
                    jLabelsForm[i].setText("Tên sản phẩm: ");
                    jTextFieldsForm[index] = new JTextField();
                    jTextFieldsForm[index].setText(null);
                    pnlProductConfiguration.add(jTextFieldsForm[index]);
                    index++;
                }
                case "CATEGORY_ID" -> {
                    jLabelsForm[i].setText("Mã thể loại: ");
                    pnlProductConfiguration.add(cbbCategoryID);
                }
                case "SIZED" -> {
                    jLabelsForm[i].setText("Size: ");
                    pnlProductConfiguration.add(cbbSize);
                }
                case "COST" -> {
                    jLabelsForm[i].setText("Giá: ");
                    jTextFieldsForm[index] = new JTextField();
                    jTextFieldsForm[index].setText(null);
                    pnlProductConfiguration.add(jTextFieldsForm[index]);
                    index++;
                }
                case "IMAGE" -> {
                    jLabelsForm[i].setText("Hình ảnh: ");
                    btChooseImg.setText("Chọn hình ảnh");
                    btChooseImg.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    btChooseImg.setFocusPainted(false);
                    btChooseImg.addActionListener(this::btnProductImageActionPerformed);
                    pnlProductConfiguration.add(btChooseImg);
                }
                default -> {
                }
            }
        }
        showImg.setLayout(new FlowLayout());
        showImg.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        showImg.setPreferredSize(new Dimension(635, 250));
        showImg.setBackground(new Color(0xFFFFFF));
        roundPanel2.add(showImg, BorderLayout.CENTER);

        showImg.add(imgProduct);

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
                        addProduct();
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
                        updateProduct();
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
                        deleteProduct();
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
            dataTable.setRowSelectionInterval(0, 0);
            fillForm();
        }
    }

    private void categoryIDSearch() {
        loadDataTable(productBLL.findProducts("CATEGORY_ID", Objects.requireNonNull(cbbCategoryIDSearch.getSelectedItem()).toString()));
    }

    private void sizeSearch() {
        loadDataTable(productBLL.findProducts("SIZED", Objects.requireNonNull(cbbSizeSearch.getSelectedItem()).toString()));
    }

    private void selectSearchFilter() {
        if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("Mã thể loại")) {
            txtSearch.setVisible(false);
            cbbSizeSearch.setVisible(false);
            cbbCategoryIDSearch.setSelectedIndex(0);
            cbbCategoryIDSearch.setVisible(true);
            categoryIDSearch();
        } else if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("Size")) {
            txtSearch.setVisible(false);
            cbbCategoryIDSearch.setVisible(false);
            cbbSizeSearch.setSelectedIndex(0);
            cbbSizeSearch.setVisible(true);
            sizeSearch();
        } else {
            cbbSizeSearch.setVisible(false);
            cbbCategoryIDSearch.setVisible(false);
            txtSearch.setVisible(true);
            searchProducts();
        }
    }

    public void searchProducts() {
        if (txtSearch.getText().isEmpty()) {
            loadDataTable(productBLL.getProductList());
        } else {
            String key = null;
            switch (cbbSearchFilter.getSelectedIndex()){
                case 0 -> key = "PRODUCT_ID";
                case 1 -> key = "NAME";
                case 4 -> key = "COST";
                default -> {
                }
            }
            assert key != null;
            loadDataTable(productBLL.findProducts(key, txtSearch.getText()));
        }
    }

    private void btnProductImageActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fc = new JFileChooser();
        fc.removeChoosableFileFilter(fc.getFileFilter());
        fc.setCurrentDirectory(new File("img"));
        FileFilter filter = new FileNameExtensionFilter("Images (.jpeg, .jpg, .png)", "jpeg", "jpg", "png");
        fc.setFileFilter(filter);
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            chosenImg = "img/products/" + file.getName();
            imgProduct.setIcon(new ImageIcon(chosenImg));
        }
    }

    public void addProduct() {
        if (checkInput()) {
            Product newProduct = getForm();
            if (productBLL.exists(newProduct))
                JOptionPane.showMessageDialog(this, "Sản phẩm đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (productBLL.exists(Map.of("NAME", newProduct.getName(), "CATEGORY_ID", newProduct.getCategoryID(), "SIZED", newProduct.getSized())))
                JOptionPane.showMessageDialog(this, "Sản phẩm đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (productBLL.addProduct(newProduct))
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm mới thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm mới thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            refreshForm();
        }
    }

    public void updateProduct() {
        if (checkInput()) {
            Product newProduct = getForm();
            int selectedRow = dataTable.getSelectedRow();
            String currentName = dataTable.getValueAt(selectedRow, 1).toString();
            String currentCategoryID = dataTable.getValueAt(selectedRow, 2).toString();
            String currentSized = dataTable.getValueAt(selectedRow, 3).toString();
            boolean valueChanged = !newProduct.getName().equals(currentName) || !newProduct.getCategoryID().equals(currentCategoryID) || !newProduct.getSized().equals(currentSized);
            if (productBLL.exists(newProduct))
                JOptionPane.showMessageDialog(this, "Sản phẩm đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (valueChanged && productBLL.exists(Map.of("NAME", newProduct.getName(), "CATEGORY_ID", newProduct.getCategoryID(), "SIZED", newProduct.getSized())))
                JOptionPane.showMessageDialog(this, "Sản phẩm đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (productBLL.updateProduct(newProduct))
                JOptionPane.showMessageDialog(this, "Sửa sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Sửa sản phẩm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            loadDataTable(productBLL.getProductList());
            dataTable.setRowSelectionInterval(selectedRow, selectedRow);
            fillForm();
        }
    }

    private void deleteProduct() {
        if (JOptionPane.showOptionDialog(this,
            "Bạn có chắc chắn muốn xoá sản phẩm này?",
            "Xác nhận",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"Xoá", "Huỷ"},
            "Xoá") == JOptionPane.YES_OPTION) {
            Product product = new Product();
            product.setProductID(jTextFieldsForm[0].getText());
            if (productBLL.deleteProduct(product))
                JOptionPane.showMessageDialog(this, "Xoá sản phẩm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Xoá sản phẩm thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            refreshForm();
        }
    }

    public void refreshForm() {
        cbbSearchFilter.setSelectedIndex(0);
        txtSearch.setText(null);
        loadDataTable(productBLL.getProductList());
        jTextFieldsForm[0].setText(productBLL.getAutoID());
        for (int i = 1; i < jTextFieldsForm.length; i++) {
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

    public void fillForm() {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        Object[] rowData = model.getDataVector().elementAt(dataTable.getSelectedRow()).toArray();
        String[] data = new String[rowData.length];
        for (int i = 0; i < rowData.length; i++) {
            data[i] = rowData[i].toString();
        }
        String[] product = String.join(" | ", data).split(" \\| ");
        jTextFieldsForm[0].setText(product[0]);
        jTextFieldsForm[1].setText(product[1]);
        cbbCategoryID.setSelectedItem(product[2]);
        cbbSize.setSelectedItem(product[3]);
        jTextFieldsForm[2].setText(String.format("%.1f VND", Double.parseDouble(product[4])));
        for (Product product1 : productBLL.getProductList()) {
            if (product1.getProductID().equals(product[0])) {
                chosenImg = product1.getImage();
                break;
            }
        }
        imgProduct.setIcon(new ImageIcon(chosenImg));
        btAdd.setEnabled(false);
        btUpd.setEnabled(true);
        btDel.setEnabled(true);
    }

    public Product getForm() {
        String productID = null;
        String name = null;
        String categoryID;
        String size;
        String image;
        double cost = 0;
        for (int i = 0; i < jTextFieldsForm.length; i++) {
            switch (i) {
                case 0 -> productID = jTextFieldsForm[i].getText();
                case 1 -> name = jTextFieldsForm[i].getText().toUpperCase();
                case 2 -> cost = Double.parseDouble(jTextFieldsForm[i].getText().replaceAll("(\\s|VND|VNĐ)", ""));
                default -> {
                }
            }
        }
        categoryID = Objects.requireNonNull(cbbCategoryID.getSelectedItem()).toString();
        size = Objects.requireNonNull(cbbSize.getSelectedItem()).toString();
        image = chosenImg;
        return new Product(productID, name, categoryID, size, cost, image, false);
    }

    public void loadDataTable(List<Product> productList) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);
        for (Product product : productList) {
            model.addRow(new Object[]{product.getProductID(), product.getName(), product.getCategoryID(), product.getSized(), product.getCost()});
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
            JOptionPane.showMessageDialog(this, "Tên sản phẩm không được chứa \"|\"", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!jTextFieldsForm[2].getText().matches("^(?=.*\\d)\\d*\\.?\\d*\\s*(VND|VNĐ)$")) {
            // Cost must be a double >= 0.0
            jTextFieldsForm[2].requestFocusInWindow();
            jTextFieldsForm[2].selectAll();
            JOptionPane.showMessageDialog(this, "Giá sản phẩm phải là số thực không âm", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (chosenImg == null) {
            JOptionPane.showMessageDialog(this, "Hình ảnh sản phẩm không được trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            btChooseImg.doClick();
            return false;
        }
        return true;
    }
}
