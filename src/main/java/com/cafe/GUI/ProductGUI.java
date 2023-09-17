package com.cafe.GUI;

import com.cafe.BLL.CategoryBLL;
import com.cafe.BLL.ProductBLL;
import com.cafe.DTO.Category;
import com.cafe.DTO.DecentralizationDetail;
import com.cafe.DTO.Product;
import com.cafe.custom.Button;
import com.cafe.custom.DataTable;
import com.cafe.custom.RoundPanel;
import com.cafe.utils.Resource;
import com.cafe.utils.VNString;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProductGUI extends JPanel {
    private ProductBLL productBLL = new ProductBLL();
    private DecentralizationDetail decentralizationMode;
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
    private JComboBox<Object> cbbCategoryName;
    private JComboBox<Object> cbbCategoryNameSearch;
    private JComboBox<Object> cbbSize;
    private JComboBox<Object> cbbSizeSearch;
    private JTextField txtSearch;
    private JTextField[] jTextFieldsForm;
    private JButton btChooseImg;
    private Button btAdd;
    private Button btUpd;
    private Button btDel;
    private Button btRef;
    private String chosenImg = "img/products/PR001.jpg";

    public ProductGUI(DecentralizationDetail decentralizationMode) {
        System.gc();
        this.decentralizationMode = decentralizationMode;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(70, 67, 67));
        initComponents();
    }

    private List<Category> categoryList = new ArrayList<>();
    public void initComponents() {
        List<String> columnNames = productBLL.getProductDAL().getColumnNames();
        CategoryBLL categoryBLL = new CategoryBLL();
        List<Object> categoriesName = categoryBLL.getObjectsProperty("NAME", categoryBLL.getCategoryList());
        categoryList = new CategoryBLL().getCategoryList();

        product = new RoundPanel();
        roundPanel1 = new RoundPanel();
        roundPanel2 = new RoundPanel();
        search = new RoundPanel();
        pnlProductConfiguration = new JPanel();
        showImg = new JPanel();
        mode = new JPanel();
        jLabelsForm = new JLabel[columnNames.size() - 1];
        imgProduct = new JLabel();
        cbbSearchFilter = new JComboBox<>(new String[]{"Mã sản phẩm", "Tên sản phẩm", "Tên thể loại", "Size", "Giá"});
        cbbCategoryName = new JComboBox<>(categoriesName.toArray());
        cbbCategoryNameSearch = new JComboBox<>(categoriesName.toArray());
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
        roundPanel2.setPreferredSize(new Dimension(350, 680));
        roundPanel2.setAutoscrolls(true);
        product.add(roundPanel2);

        search.setLayout(new FlowLayout());
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
        cbbCategoryNameSearch.setVisible(false);
        cbbCategoryNameSearch.addItemListener(e -> categoryIDSearch());
        search.add(cbbCategoryNameSearch);
        cbbSizeSearch.setVisible(false);
        cbbSizeSearch.addItemListener(e -> sizeSearch());
        search.add(cbbSizeSearch);

        dataTable = new DataTable(null, new String[]{"Mã sản phẩm", "Tên sản phẩm", "Tên thể loại", "Size", "Giá"}, e -> fillForm());
        scrollPane = new JScrollPane(dataTable);
        roundPanel1.add(scrollPane);

        pnlProductConfiguration.setLayout(new GridLayout(6, 2, 20, 20));
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
                    jTextFieldsForm[index].setDisabledTextColor(null);
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
                    jLabelsForm[i].setText("Tên thể loại: ");
                    pnlProductConfiguration.add(cbbCategoryName);
                }
                case "SIZED" -> {
                    jLabelsForm[i].setText("Size: ");
                    pnlProductConfiguration.add(cbbSize);
                }
                case "COST" -> {
                    jLabelsForm[i].setText("Giá: ");
                    jTextFieldsForm[index] = new JTextField();
                    jTextFieldsForm[index].setText(null);
                    jTextFieldsForm[index].addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            char c = e.getKeyChar();
                            if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                                e.consume();
                            }
                        }
                    });
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
        roundPanel2.add(showImg, BorderLayout.CENTER);

        showImg.add(imgProduct);

        mode.setLayout(new GridLayout(2, 2, 20, 20));
        mode.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mode.setPreferredSize(new Dimension(635, 130));
        roundPanel2.add(mode, BorderLayout.SOUTH);

        if (decentralizationMode.isCanADD()) {
            Button.configButton(btAdd, List.of("  Thêm", "img/icons/plus.png", true, (Runnable) this::addProduct));
            mode.add(btAdd);
        }

        if (decentralizationMode.isCanEDIT()) {
            Button.configButton(btUpd, List.of("  Sửa", "img/icons/wrench.png", false, (Runnable) this::updateProduct));
            mode.add(btUpd);
        }

        if (decentralizationMode.isCanREMOVE()) {
            Button.configButton(btDel, List.of("  Xóa", "img/icons/delete.png", false, (Runnable) this::deleteProduct));
            mode.add(btDel);
        }

        if (decentralizationMode.isCanADD()) {
            Button.configButton(btRef, List.of("  Làm mới", "img/icons/refresh.png", true, (Runnable) this::refreshForm));
            mode.add(btRef);
        } else {
            dataTable.setRowSelectionInterval(0, 0);
            fillForm();
        }
        loadDataTable(productBLL.getProductList());
    }

    private void categoryIDSearch() {
        for(Category category : categoryList) {
            if (category.getName().equals(cbbCategoryNameSearch.getSelectedItem())) {
                loadDataTable(productBLL.findProducts("CATEGORY_ID", Objects.requireNonNull(category.getCategoryID())));
                break;
            }
        }
    }

    private void sizeSearch() {
        loadDataTable(productBLL.findProducts("SIZED", Objects.requireNonNull(cbbSizeSearch.getSelectedItem()).toString()));
    }

    private void selectSearchFilter() {
        if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("Tên thể loại")) {
            txtSearch.setVisible(false);
            cbbSizeSearch.setVisible(false);
            cbbCategoryNameSearch.setSelectedIndex(0);
            cbbCategoryNameSearch.setVisible(true);
            categoryIDSearch();
        } else if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().contains("Size")) {
            txtSearch.setVisible(false);
            cbbCategoryNameSearch.setVisible(false);
            cbbSizeSearch.setSelectedIndex(0);
            cbbSizeSearch.setVisible(true);
            sizeSearch();
        } else {
            cbbSizeSearch.setVisible(false);
            cbbCategoryNameSearch.setVisible(false);
            txtSearch.setVisible(true);
            searchProducts();
        }
    }

    public void searchProducts() {
        if (txtSearch.getText().isEmpty()) {
            loadDataTable(productBLL.getProductList());
        } else {
            String key = null;
            switch (cbbSearchFilter.getSelectedIndex()) {
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

    private void btnProductImageActionPerformed(ActionEvent evt) {
        JFileChooser fc = new JFileChooser();
        fc.removeChoosableFileFilter(fc.getFileFilter());
        fc.setCurrentDirectory(new File(Objects.requireNonNull(Resource.getAbsolutePath("img/products"))));
        FileFilter filter = new FileNameExtensionFilter("Images (.jpeg, .jpg, .png)", "jpeg", "jpg", "png");
        fc.setFileFilter(filter);
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            chosenImg = "img/products/" + file.getName();
            imgProduct.setIcon(Resource.loadImageIconIn(chosenImg));
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
        cbbCategoryName.setSelectedIndex(0);
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
        cbbCategoryName.setSelectedItem(product[2]);
        cbbSize.setSelectedItem(product[3]);
        jTextFieldsForm[2].setText(VNString.currency(Double.parseDouble(product[4])));
        for (Product product1 : productBLL.getProductList()) {
            if (product1.getProductID().equals(product[0])) {
                chosenImg = product1.getImage();
                break;
            }
        }
        imgProduct.setIcon(Resource.loadImageIconIn(chosenImg));
        btAdd.setEnabled(false);
        btUpd.setEnabled(true);
        btDel.setEnabled(true);
    }

    public Product getForm() {
        String productID = null;
        String name = null;
        String categoryID = null;
        String size;
        String image;
        double cost = 0;
        for (int i = 0; i < jTextFieldsForm.length; i++) {
            switch (i) {
                case 0 -> productID = jTextFieldsForm[i].getText();
                case 1 -> name = jTextFieldsForm[i].getText().toUpperCase();
                case 2 -> cost = Double.parseDouble(jTextFieldsForm[i].getText().replaceAll("\\D+", ""));
                default -> {
                }
            }
        }
        for (Category category: categoryList) {
            if (category.getName().equals(cbbCategoryName.getSelectedItem())) {
                categoryID = Objects.requireNonNull(category.getCategoryID());
                break;
            }
        }
        size = Objects.requireNonNull(cbbSize.getSelectedItem()).toString();
        image = chosenImg;
        return new Product(productID, name, categoryID, size, cost, image, false);
    }

    public void loadDataTable(List<Product> productList) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);
        for (Product product : productList) {
            for (Category category: categoryList) {
                if (category.getCategoryID().equals(product.getCategoryID())) {
                    model.addRow(new Object[]{product.getProductID(), product.getName(), category.getName(), product.getSized(), product.getCost()});
                    break;
                }
            }
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
        if (chosenImg == null) {
            JOptionPane.showMessageDialog(this, "Hình ảnh sản phẩm không được trống", "Lỗi", JOptionPane.ERROR_MESSAGE);
            btChooseImg.doClick();
            return false;
        }
        return true;
    }
}
