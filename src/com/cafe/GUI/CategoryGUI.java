package com.cafe.GUI;

import com.cafe.BLL.CategoryBLL;
import com.cafe.DTO.Category;
import com.cafe.custom.Button;
import com.cafe.custom.DataTable;
import com.cafe.custom.RoundPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;

public class CategoryGUI extends JPanel {
    private CategoryBLL categoryBLL = new CategoryBLL();
    private int decentralizationMode;
    private DataTable dataTable;
    private RoundPanel category;
    private RoundPanel roundPanel1;
    private RoundPanel roundPanel2;
    private RoundPanel search;
    private JScrollPane scrollPane;
    private JPanel pnlCategoryConfiguration;
    private JPanel showImg;
    private JPanel mode;
    private JLabel[] jLabelsForm;
    private JComboBox<Object> cbbSearchFilter;
    private JTextField txtSearch;
    private JTextField[] jTextFieldsForm;
    private Button btAdd;
    private Button btUpd;
    private Button btDel;
    private Button btRef;

    public CategoryGUI(int decentralizationMode) {
        System.gc();
        this.decentralizationMode = decentralizationMode;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(70, 67, 67));
        initComponents();
    }

    public void initComponents() {
        List<String> columnNames = categoryBLL.getCategoryDAL().getColumnNames();
        category = new RoundPanel();
        roundPanel1 = new RoundPanel();
        roundPanel2 = new RoundPanel();
        search = new RoundPanel();
        pnlCategoryConfiguration = new JPanel();
        showImg = new JPanel();
        mode = new JPanel();
        jLabelsForm = new JLabel[columnNames.size() - 1];
        cbbSearchFilter = new JComboBox<>(new String[]{"Mã thể loại", "Tên thể loại", "Số lượng"});
        txtSearch = new JTextField(20);
        jTextFieldsForm = new JTextField[columnNames.size() - 1];
        btAdd = new Button();
        btUpd = new Button();
        btDel = new Button();
        btRef = new Button();

        category.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        category.setBackground(new Color(70, 67, 67));
        this.add(category, BorderLayout.CENTER);

        roundPanel1.setLayout(new BorderLayout(0, 10));
        roundPanel1.setBackground(new Color(70, 67, 67));
        roundPanel1.setPreferredSize(new Dimension(635, 680));
        roundPanel1.setAutoscrolls(true);
        roundPanel1.add(new JScrollPane(dataTable), BorderLayout.CENTER);
        category.add(roundPanel1);

        roundPanel2.setLayout(new BorderLayout());
        roundPanel2.setBackground(new Color(0xFFFFFF));
        roundPanel2.setPreferredSize(new Dimension(350, 680));
        roundPanel2.setAutoscrolls(true);
        category.add(roundPanel2);

        search.setLayout(new FlowLayout());
        search.setBackground(new Color(0xFFFFFF));
        search.setPreferredSize(new Dimension(635, 35));
        roundPanel1.add(search, BorderLayout.NORTH);


        search.add(cbbSearchFilter);
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchCategories();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchCategories();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchCategories();
            }
        });
        search.add(txtSearch);

        dataTable = new DataTable(categoryBLL.getData(), new String[]{"Mã thể loại", "Tên thể loại", "Số lượng"}, e -> fillForm());
        scrollPane = new JScrollPane(dataTable);
        roundPanel1.add(scrollPane);

        pnlCategoryConfiguration.setLayout(new GridLayout(3, 2, 20, 20));
        pnlCategoryConfiguration.setBackground(new Color(0xFFFFFF));
        pnlCategoryConfiguration.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
        pnlCategoryConfiguration.setPreferredSize(new Dimension(635, 150));
        roundPanel2.add(pnlCategoryConfiguration, BorderLayout.NORTH);

        for (int i = 0; i < columnNames.size() - 1; i++) {
            jLabelsForm[i] = new JLabel();
            pnlCategoryConfiguration.add(jLabelsForm[i]);
            if ("CATEGORY_ID".equals(columnNames.get(i))) {
                jLabelsForm[i].setText("Mã thể loại: ");
                jTextFieldsForm[i] = new JTextField(categoryBLL.getAutoID());
                jTextFieldsForm[i].setEnabled(false);
                jTextFieldsForm[i].setBorder(null);
                jTextFieldsForm[i].setDisabledTextColor(new Color(0x000000));
                pnlCategoryConfiguration.add(jTextFieldsForm[i]);
            } else if ("QUANTITY".equals(columnNames.get(i))) {
                jLabelsForm[i].setText("Số lượng: ");
                jTextFieldsForm[i] = new JTextField(categoryBLL.getAutoID());
                jTextFieldsForm[i].setEnabled(false);
                jTextFieldsForm[i].setText("0");
                jTextFieldsForm[i].setDisabledTextColor(new Color(0x000000));
                pnlCategoryConfiguration.add(jTextFieldsForm[i]);
            } else {
                jLabelsForm[i].setText("Tên thể loại: ");
                jTextFieldsForm[i] = new JTextField();
                jTextFieldsForm[i].setText(null);
                pnlCategoryConfiguration.add(jTextFieldsForm[i]);
            }
        }
        showImg.setLayout(new FlowLayout());
        showImg.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        showImg.setPreferredSize(new Dimension(635, 250));
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
                        addCategory();
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
                        updateCategory();
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
                        deleteCategory();
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

    public void searchCategories() {
        if (txtSearch.getText().isEmpty()) {
            loadDataTable(categoryBLL.getCategoryList());
        } else {
            String key = null;
            switch (cbbSearchFilter.getSelectedIndex()) {
                case 0 -> key = "CATEGORY_ID";
                case 1 -> key = "NAME";
                case 2 -> key = "QUANTITY";
                default -> {
                }
            }
            assert key != null;
            loadDataTable(categoryBLL.findCategories(key, txtSearch.getText()));
        }
    }

    public void addCategory() {
        if (checkInput()) {
            Category newCategory = getForm();
            if (categoryBLL.exists(newCategory))
                JOptionPane.showMessageDialog(this, "Thể loại đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (categoryBLL.exists(Map.of("NAME", newCategory.getName())))
                JOptionPane.showMessageDialog(this, "Tên thể loại đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (categoryBLL.addCategory(newCategory))
                JOptionPane.showMessageDialog(this, "Thêm thể loại mới thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Thêm thể loại mới thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            refreshForm();
        }
    }

    public void updateCategory() {
        if (checkInput()) {
            Category newCategory = getForm();
            int selectedRow = dataTable.getSelectedRow();
            String currentName = dataTable.getValueAt(selectedRow, 1).toString();
            boolean valueChanged = !newCategory.getName().equals(currentName);
            if (categoryBLL.exists(newCategory))
                JOptionPane.showMessageDialog(this, "Thể loại đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (valueChanged && categoryBLL.exists(Map.of("NAME", newCategory.getName())))
                JOptionPane.showMessageDialog(this, "Tên thể loại đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (categoryBLL.updateCategory(newCategory))
                JOptionPane.showMessageDialog(this, "Sửa thể loại thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Sửa thể loại thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            loadDataTable(categoryBLL.getCategoryList());
            dataTable.setRowSelectionInterval(selectedRow, selectedRow);
            fillForm();
        }
    }

    private void deleteCategory() {
        if (JOptionPane.showOptionDialog(this,
            "Bạn có chắc chắn muốn xoá thể loại này?",
            "Xác nhận",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"Xoá", "Huỷ"},
            "Xoá") == JOptionPane.YES_OPTION) {
            Category category = new Category();
            category.setCategoryID(jTextFieldsForm[0].getText());
            if (categoryBLL.findCategories("CATEGORY_ID", category.getCategoryID()).get(0).getQuantity() > 0) {
                JOptionPane.showMessageDialog(this, "Không thể xóa thể loại có sản phẩm đang kinh doanh.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else if (categoryBLL.deleteCategory(category))
                JOptionPane.showMessageDialog(this, "Xoá thể loại thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Xoá thể loại thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            refreshForm();
        }
    }

    public void refreshForm() {
        cbbSearchFilter.setSelectedIndex(0);
        txtSearch.setText(null);
        loadDataTable(categoryBLL.getCategoryList());
        jTextFieldsForm[0].setText(categoryBLL.getAutoID());
        for (int i = 1; i < jTextFieldsForm.length; i++) {
            if (i == 2) {
                jTextFieldsForm[i].setEnabled(false);
                jTextFieldsForm[i].setText("0");
            } else {
                jTextFieldsForm[i].setText(null);
            }
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
        String[] category = String.join(" | ", data).split(" \\| ");
        for (int i = 0; i < category.length; i++) {
            if (i == 2) {
                jTextFieldsForm[i].setEnabled(false);
            }
            jTextFieldsForm[i].setText(category[i]);
        }
        btAdd.setEnabled(false);
        btUpd.setEnabled(true);
        btDel.setEnabled(true);
    }

    public Category getForm() {
        String categoryID = null;
        String name = null;
        int quantity = 0;
        for (int i = 0; i < jTextFieldsForm.length; i++) {
            switch (i) {
                case 0 -> categoryID = jTextFieldsForm[i].getText();
                case 1 -> name = jTextFieldsForm[i].getText().toUpperCase();
                case 2 -> quantity = Integer.parseInt(jTextFieldsForm[i].getText());
                default -> {
                }
            }
        }
        return new Category(categoryID, name, quantity, false);
    }

    public void loadDataTable(List<Category> categoryList) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);
        for (Category category : categoryList) {
            model.addRow(new Object[]{category.getCategoryID(), category.getName(), category.getQuantity()});
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
            JOptionPane.showMessageDialog(this, "Tên thể loại không được chứa \"|\"", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
