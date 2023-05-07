package com.cafe.GUI;

import com.cafe.BLL.CategoryBLL;
import com.cafe.BLL.DiscountBLL;
import com.cafe.BLL.DiscountDetailsBLL;
import com.cafe.BLL.ProductBLL;
import com.cafe.DTO.Discount;
import com.cafe.DTO.DiscountDetails;
import com.cafe.DTO.Product;
import com.cafe.custom.Button;
import com.cafe.custom.DataTable;
import com.cafe.custom.RoundPanel;
import com.cafe.utils.Day;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DiscountGUI extends JPanel {
    List<String> newProductID_Dis = new ArrayList<>();
    private DiscountBLL discountBLL = new DiscountBLL();
    private DiscountDetailsBLL discountDetailsBLL = new DiscountDetailsBLL();
    private ProductBLL productBLL = new ProductBLL();
    private CategoryBLL categoryBLL = new CategoryBLL();
    private int decentralizationMode;
    private Discount discountSelected;
    private JPanel discount;
    private RoundPanel[] roundPanel;
    private JLabel[] label;
    private JTextField[] jTextFields;
    private DataTable[] dataTable;
    private JComboBox<Object> jComboBox;
    private RoundPanel mode;
    private Button btAdd;
    private Button btUpd;
    private Button btDel;
    private Button btRef;
    private JTextField txtsearchDis;
    private JTextField txtsearchPro;
    private JDateChooser[] jDateChooser;
    private JTextField[] dateTextField;
    private JComboBox<Object> cbbSearchDis;
    private JComboBox<Object> cbbSearchPro;
    private JComboBox<Object> cbbStatus;
    private JComboBox<Object> cbbCategory;
    private JComboBox<Object> cbbSize;
    private JLabel labelimg;
    private JScrollPane scrollPane1;
    private JScrollPane scrollPane2;

    public DiscountGUI(int decentralizationMode) {
        this.decentralizationMode = decentralizationMode;
        this.discountSelected = null;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(70, 67, 67));
        initComponents();
    }

    public void initComponents() {
        List<String> columnNamesDis = discountBLL.getDiscountDAL().getColumnNames();
        List<String> columnNamesPro = productBLL.getProductDAL().getColumnNames();
        List<Object> categoriesID = categoryBLL.getObjectsProperty("CATEGORY_ID", categoryBLL.getCategoryList());

        jComboBox = new JComboBox<>(new String[]{"Đang áp dụng", "Ngừng áp dụng"});
        roundPanel = new RoundPanel[15];
        label = new JLabel[10];
        jTextFields = new JTextField[2];
        jDateChooser = new JDateChooser[2];
        dataTable = new DataTable[2];
        discount = new JPanel();
        labelimg = new JLabel();
        txtsearchDis = new JTextField();
        txtsearchPro = new JTextField();

        mode = new RoundPanel();
        btAdd = new Button();
        btUpd = new Button();
        btDel = new Button();
        btRef = new Button();

        cbbSearchDis = new JComboBox<>(new String[]{"Mã đợt giảm giá", "Phần trăm", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái"});
        cbbSearchPro = new JComboBox<>(new String[]{"Mã sản phẩm", "Tên sản phẩm", "Mã thể loại", "Size", "Giá cũ"});
        cbbStatus = new JComboBox<>(new String[]{"Đang áp dụng", "Ngừng áp dụng"});
        cbbCategory = new JComboBox<>(categoriesID.toArray());
        cbbSize = new JComboBox<>(new String[]{"null", "S", "M", "L"});

        for (int i = 0; i < roundPanel.length; i++) {
            roundPanel[i] = new RoundPanel();
        }

        discount.setLayout(new BorderLayout(10, 10));
        discount.setBackground(new Color(70, 67, 67));
        this.add(discount, BorderLayout.CENTER);

        roundPanel[0].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roundPanel[0].setBackground(new Color(255, 255, 255));
        roundPanel[0].setPreferredSize(new Dimension(380, 670));
        roundPanel[0].setAutoscrolls(true);
        discount.add(roundPanel[0], BorderLayout.WEST);

        roundPanel[1].setLayout(new BorderLayout(0, 10));
        roundPanel[1].setBackground(new Color(70, 67, 67));
        roundPanel[1].setPreferredSize(new Dimension(600, 670));
        roundPanel[1].setAutoscrolls(true);
        discount.add(roundPanel[1], BorderLayout.CENTER);

        roundPanel[2].setBackground(new Color(255, 255, 255));
        roundPanel[2].setPreferredSize(new Dimension(600, 310));
        roundPanel[2].setAutoscrolls(true);
        roundPanel[1].add(roundPanel[2], BorderLayout.NORTH);

        roundPanel[3].setBackground(new Color(255, 255, 255));
        roundPanel[3].setPreferredSize(new Dimension(600, 350));
        roundPanel[3].setAutoscrolls(true);
        roundPanel[1].add(roundPanel[3], BorderLayout.CENTER);

        roundPanel[4].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        roundPanel[4].setBackground(new Color(255, 255, 255));
        roundPanel[4].setPreferredSize(new Dimension(380, 200));
        roundPanel[4].setAutoscrolls(true);
        roundPanel[0].add(roundPanel[4]);

        roundPanel[5].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15));
        roundPanel[5].setBackground(new Color(255, 255, 255));
        roundPanel[5].setPreferredSize(new Dimension(380, 340));
        roundPanel[5].setAutoscrolls(true);
        roundPanel[0].add(roundPanel[5]);

        roundPanel[6].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roundPanel[6].setBackground(new Color(255, 255, 255));
        roundPanel[6].setPreferredSize(new Dimension(380, 130));
        roundPanel[6].setAutoscrolls(true);
        roundPanel[0].add(roundPanel[6]);

        roundPanel[7].setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
//        roundPanel[7].setBackground(new Color(255, 255, 255));
        roundPanel[7].setPreferredSize(new Dimension(600, 40));
        roundPanel[7].setAutoscrolls(true);
        roundPanel[2].add(roundPanel[7]);

        roundPanel[8].setLayout(new BorderLayout(0, 0));
//        roundPanel[8].setBackground(new Color(182, 24, 24));
        roundPanel[8].setPreferredSize(new Dimension(590, 255));
        roundPanel[8].setAutoscrolls(true);
        roundPanel[8].add(new JScrollPane(dataTable[0]), BorderLayout.CENTER);
        roundPanel[2].add(roundPanel[8]);

        roundPanel[9].setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
        // roundPanel[9].setBackground(new Color(182, 24, 24));
        roundPanel[9].setPreferredSize(new Dimension(600, 40));
        roundPanel[9].setAutoscrolls(true);
        roundPanel[3].add(roundPanel[9]);

        roundPanel[10].setLayout(new BorderLayout(0, 0));
//        roundPanel[10].setBackground(new Color(182, 24, 24));
        roundPanel[10].setPreferredSize(new Dimension(590, 295));
        roundPanel[10].add(new JScrollPane(dataTable[1]), BorderLayout.CENTER);
        roundPanel[10].setAutoscrolls(true);
        roundPanel[3].add(roundPanel[10]);

        labelimg.setIcon(new ImageIcon(new ImageIcon("img/black-friday.png").getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
        labelimg.setFocusable(false);
        labelimg.setPreferredSize(new Dimension(150, 150));
        roundPanel[4].add(labelimg);

        Dimension inputFieldsSize = new Dimension(200, 30);
        jComboBox.setSelectedIndex(0);
        jComboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
        jComboBox.setMaximumRowCount(2);//so luong
        jComboBox.setPreferredSize(inputFieldsSize);
        jComboBox.setBorder(null);
        jComboBox.setFocusable(false);

        jTextFields = new JTextField[2];
        jDateChooser = new JDateChooser[2];
        dateTextField = new JTextField[2];
        for (int i = 0; i < 2; i++) {
            jTextFields[i] = new JTextField();
            jTextFields[i].setFont(new Font("Times New Roman", Font.BOLD, 15));
            jTextFields[i].setPreferredSize(inputFieldsSize);
            jTextFields[i].setAutoscrolls(true);
            jDateChooser[i] = new JDateChooser();
            jDateChooser[i].setDateFormatString("dd/MM/yyyy");
            jDateChooser[i].setPreferredSize(inputFieldsSize);
            jDateChooser[i].setMinSelectableDate(new Day(1, 1, 1000).toDateSafe());
            dateTextField[i] = (JTextField) jDateChooser[i].getDateEditor().getUiComponent();
            dateTextField[i].setFont(new Font("Tahoma", Font.BOLD, 14));
            int index = i;
            dateTextField[i].addActionListener(e -> {
                try {
                    Day day = Day.parseDay(dateTextField[index].getText());
                    jDateChooser[index].setDate(day.toDate());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid date", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            });
            if (i == 0) {
                jTextFields[i].setText(discountBLL.getAutoID());
                jTextFields[i].setHorizontalAlignment(JLabel.CENTER);
                jTextFields[i].setEditable(false);
                jTextFields[i].setFocusable(false);
                jTextFields[i].setBorder(BorderFactory.createEmptyBorder());
            } else {
                jTextFields[i].setBorder(BorderFactory.createLineBorder(Color.black));
            }
        }
        for (int i = 0; i < columnNamesDis.size() - 1; i++) {
            label[i] = new JLabel();
            label[i].setFont(new Font("Times New Roman", Font.BOLD, 15));
            label[i].setHorizontalAlignment(JLabel.LEFT);
            label[i].setPreferredSize(new Dimension(160, 30));
            label[i].setAutoscrolls(true);
            roundPanel[5].add(label[i]);
            switch (columnNamesDis.get(i)) {
                case "DISCOUNT_ID" -> {
                    label[i].setText("Mã đợt giảm giá: ");
                    roundPanel[5].add(jTextFields[i]);
                }
                case "DISCOUNT_PERCENT" -> {
                    label[i].setText("Phần trăm: ");
                    roundPanel[5].add(jTextFields[i]);
                }
                case "START_DATE" -> {
                    label[i].setText("Ngày bắt đầu: ");
                    roundPanel[5].add(jDateChooser[i - 2]);
                }
                case"END_DATE" -> {
                    label[i].setText("Ngày kết thúc: ");
                    roundPanel[5].add(jDateChooser[i - 2]);
                }
                case "STATUS" -> {
                    label[i].setText("Trạng thái: ");
                    roundPanel[5].add(jComboBox);
                }
                default -> {
                }
            }
        }

        if (decentralizationMode > 1) {
            mode.setLayout(new GridLayout(2, 2, 40, 20));
            mode.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            mode.setBackground(new Color(0xFFFFFF));
            mode.setPreferredSize(new Dimension(380, 130));
            roundPanel[6].add(mode);

            btAdd.setBackground(new Color(35, 166, 97));
            btAdd.setBorder(null);
            btAdd.setIcon(new ImageIcon("img/plus.png"));
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
                        addDiscount();
                    }
                }
            });
            mode.add(btAdd);
        }

        if (decentralizationMode == 3) {
            btUpd.setBackground(new Color(35, 166, 97));
            btUpd.setBorder(null);
            btUpd.setIcon(new ImageIcon("img/wrench.png"));
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
                        updateDiscount();
                    }
                }
            });
            mode.add(btUpd);

            btDel.setBackground(new Color(35, 166, 97));
            btDel.setBorder(null);
            btDel.setIcon(new ImageIcon("img/delete.png"));
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
                        deleteDiscount();
                    }
                }
            });
            mode.add(btDel);

        }

        if (decentralizationMode > 1) {
            btRef.setBackground(new Color(35, 166, 97));
            btRef.setBorder(null);
            btRef.setIcon(new ImageIcon("img/refresh.png"));
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
        }

        roundPanel[7].add(cbbSearchDis);
        cbbSearchDis.addActionListener(e -> selectSearchDis());

        txtsearchDis.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        txtsearchDis.setPreferredSize(new Dimension(300, 35));
        txtsearchDis.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        txtsearchDis.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                searchDiscounts();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchDiscounts();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchDiscounts();
            }
        });
        roundPanel[7].add(txtsearchDis);
        cbbStatus.setVisible(false);
        cbbStatus.addItemListener(e -> statusSearch());
        roundPanel[7].add(cbbStatus);

        roundPanel[9].add(cbbSearchPro);
        cbbSearchPro.addActionListener(e -> selectSearchPro());

        txtsearchPro.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        txtsearchPro.setPreferredSize(new Dimension(300, 35));
        txtsearchPro.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        txtsearchPro.getDocument().addDocumentListener(new DocumentListener() {

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
        roundPanel[9].add(txtsearchPro);
        cbbCategory.setVisible(false);
        cbbCategory.addItemListener(e -> categoryIDSearch());
        roundPanel[9].add(cbbCategory);
        cbbSize.setVisible(false);
        cbbSize.addItemListener(e -> sizeSearch());
        roundPanel[9].add(cbbSize);

        scrollPane1 = new JScrollPane();
        scrollPane2 = new JScrollPane();

        dataTable[0] = new DataTable(discountBLL.getData(), new String[]{"Mã đợt giảm giá", "Phần trăm", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái"}, e -> fillForm1());
        scrollPane1 = new JScrollPane(dataTable[0]);
        roundPanel[8].add(scrollPane1);

        List<String> columnTablePro = new ArrayList<>(columnNamesPro.subList(0, columnNamesPro.size() - 3));
        columnTablePro.add("OLD_PRICE");
        columnTablePro.add("NEW_PRICE");
        columnTablePro.add("");

        dataTable[1] = new DataTable(productBLL.getData(), new String[]{"Mã sản phẩm", "Tên sản phẩm", "Mã thể loại", "Size", "Giá cũ", "Giá mới", ""}, e -> fillForm2(), true);
        scrollPane2 = new JScrollPane(dataTable[1]);
        roundPanel[10].add(scrollPane2);
    }

    private void selectSearchDis() {
        if (Objects.requireNonNull(cbbSearchDis.getSelectedItem()).toString().contains("Trạng thái")) {
            txtsearchDis.setVisible(false);
            cbbStatus.setSelectedIndex(0);
            cbbStatus.setVisible(true);
            statusSearch();
        } else {
            cbbStatus.setVisible(false);
            txtsearchDis.setVisible(true);
            searchDiscounts();
        }
    }

    private void selectSearchPro() {
        if (Objects.requireNonNull(cbbSearchPro.getSelectedItem()).toString().contains("Mã thể loại")) {
            txtsearchPro.setVisible(false);
            cbbSize.setVisible(false);
            cbbCategory.setSelectedIndex(0);
            cbbCategory.setVisible(true);
            categoryIDSearch();
        } else if (Objects.requireNonNull(cbbSearchPro.getSelectedItem()).toString().contains("Size")) {
            txtsearchPro.setVisible(false);
            cbbCategory.setVisible(false);
            cbbSize.setSelectedIndex(0);
            cbbSize.setVisible(true);
            sizeSearch();
        } else {
            cbbSize.setVisible(false);
            cbbCategory.setVisible(false);
            txtsearchPro.setVisible(true);
            txtsearchPro.setText(null);
            searchProducts();
        }
    }

    private void searchDiscounts() {
        if (txtsearchDis.getText().isEmpty()) {
            loadDataTableDis(discountBLL.getDiscountList());
        } else {
            String key = null;
            switch (cbbSearchDis.getSelectedIndex()){
                case 0 -> key = "DISCOUNT_ID";
                case 1 -> key = "DISCOUNT_PERCENT";
                case 2 -> key = "START_DATE";
                case 3 -> key = "END_DATE";
                default -> {
                }
            }
            assert key != null;
            loadDataTableDis(discountBLL.findDiscounts(key, txtsearchDis.getText()));
        }
    }

    private void searchProducts() {
        if (txtsearchPro.getText().isEmpty()) {
            loadDataTablePro(productBLL.getProductList());
        } else {
            String key = null;
            switch (cbbSearchPro.getSelectedIndex()){
                case 0 -> key = "PRODUCT_ID";
                case 1 -> key = "NAME";
                case 4 -> key = "COST";
                default -> {
                }
            }
            assert key != null;
            loadDataTablePro(productBLL.findProducts(key, txtsearchPro.getText()));
        }
    }

    private void statusSearch() {
        int status = Objects.requireNonNull(cbbStatus.getSelectedItem()).toString().equals("Đang áp dụng") ? 0 : 1;
        loadDataTableDis(discountBLL.findDiscounts("STATUS", status));
    }

    private void categoryIDSearch() {
        loadDataTablePro(productBLL.findProducts("CATEGORY_ID", Objects.requireNonNull(cbbCategory.getSelectedItem()).toString()));
    }

    private void sizeSearch() {
        loadDataTablePro(productBLL.findProducts("SIZED", Objects.requireNonNull(cbbSize.getSelectedItem()).toString()));
    }

    public void addDiscount() {
        if (checkInput()) {
            Discount newDiscount = null;
            try {
                newDiscount = getForm();
            } catch (Exception ignored) {

            }
            assert newDiscount != null;
//            if (discountBLL.exists(newDiscount))
//                JOptionPane.showMessageDialog(this, "Discount already existed!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            else
            if (discountBLL.addDiscount(newDiscount))
                JOptionPane.showMessageDialog(this, "Thêm đợt giảm giá mới thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Thêm đợt giảm giá mới thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            refreshForm();
        }
    }

    public void updateDiscount() {
        if (checkInput()) {
            Discount newDiscount = null;
            try {
                newDiscount = getForm();
            } catch (Exception ignored) {

            }
            assert newDiscount != null;
            int selectedRow = dataTable[0].getSelectedRow();
//            if (discountBLL.exists(newDiscount))
//                JOptionPane.showMessageDialog(this, "Discount already existed!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            else
            if (discountBLL.updateDiscount(newDiscount))
                JOptionPane.showMessageDialog(this, "Sửa đợt giảm giá thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Sửa đợt giảm giá thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);

            if (!newProductID_Dis.isEmpty()) {
                List<DiscountDetails> discountDetailsList = discountDetailsBLL.findDiscountDetails("DISCOUNT_ID", discountSelected.getDiscountID());
                List<DiscountDetails> deletedDiscountDetailsList = discountDetailsBLL.searchDiscountDetails("DISCOUNT_ID = '" + newDiscount.getDiscountID() + "'", "DELETED = 1");
                List<String> productID_Discount = new ArrayList<>();
                List<String> deletedProductID_Discount = new ArrayList<>();
                for (DiscountDetails discountDetails : discountDetailsList) {
                    productID_Discount.add(discountDetailsBLL.getValueByKey(discountDetails, "PRODUCT_ID").toString());
                }
                for (DiscountDetails discountDetails : deletedDiscountDetailsList) {
                    deletedProductID_Discount.add(discountDetailsBLL.getValueByKey(discountDetails, "PRODUCT_ID").toString());
                }

                for (String productID : productID_Discount) {
                    if (!newProductID_Dis.contains(productID)) {
                        DiscountDetails newDetails = new DiscountDetails(newDiscount.getDiscountID(), productID, false);
                        discountDetailsBLL.deleteDiscountDetails(newDetails);
                    }
                }

                for (String productID : newProductID_Dis) {
                    if (!productID_Discount.contains(productID)) {
                        DiscountDetails newDetails = new DiscountDetails(newDiscount.getDiscountID(), productID, false);
                        if (deletedProductID_Discount.contains(productID)) {
                            discountDetailsBLL.updateDiscountDetails(newDetails);
                        } else {
                            discountDetailsBLL.addDiscountDetails(newDetails);
                        }
                    }
                }
            }
            loadDataTableDis(discountBLL.getDiscountList());
            dataTable[0].setRowSelectionInterval(selectedRow, selectedRow);
        }
    }

    public void deleteDiscount() {
        if (JOptionPane.showOptionDialog(this,
            "Bạn có chắc chắn muốn xoá đợt giảm giá này?",
            "Xác nhận",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"Xoá", "Huỷ"},
            "Xoá") == JOptionPane.YES_OPTION) {
            Discount discount = new Discount();
            discount.setDiscountID(jTextFields[0].getText());
            if (discountBLL.deleteDiscount(discount))
                JOptionPane.showMessageDialog(this, "Xoá đợt giảm giá thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Xoá đợt giảm giá thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            refreshForm();
        }
    }

    public void refreshForm() {
        discountSelected = null;
        cbbSearchDis.setSelectedIndex(0);
        cbbSearchPro.setSelectedIndex(0);
        txtsearchDis.setText(null);
        txtsearchPro.setText(null);
        loadDataTableDis(discountBLL.getDiscountList());
        loadDataTablePro(productBLL.getProductList());
        jTextFields[0].setText(discountBLL.getAutoID());
        for (int i = 1; i < jTextFields.length; i++) {
            jTextFields[i].setText(null);
        }
        jDateChooser[0].setDate(null);
        jDateChooser[1].setDate(null);
        jComboBox.setSelectedIndex(0);
        btAdd.setEnabled(true);
        btUpd.setEnabled(false);
        btDel.setEnabled(false);
    }

    public void fillForm1() {
        DefaultTableModel model = (DefaultTableModel) dataTable[0].getModel();
        Object[] rowData = model.getDataVector().elementAt(dataTable[0].getSelectedRow()).toArray();
        String[] data = new String[rowData.length];
        for (int i = 0; i < rowData.length; i++) {
            data[i] = rowData[i].toString();
        }
        String[] discount = String.join(" | ", data).split(" \\| ");
        jTextFields[0].setText(discount[0]);
        jTextFields[1].setText(discount[1]);
        try {
            Day date = Day.parseDay(discount[2]);
            jDateChooser[0].setDate(date.toDate());
            date = Day.parseDay(discount[3]);
            jDateChooser[1].setDate(date.toDate());
        } catch (Exception ignored) {

        }
        jComboBox.setSelectedItem(discount[4]);
        discountSelected = discountBLL.findDiscounts("DISCOUNT_ID", discount[0]).get(0);
        cbbSearchPro.setSelectedIndex(0);

        List<DiscountDetails> discountDetailsList = discountDetailsBLL.findDiscountDetails("DISCOUNT_ID", discountSelected.getDiscountID());

        newProductID_Dis.clear();

        for (DiscountDetails discountDetails : discountDetailsList) {
            newProductID_Dis.add(discountDetailsBLL.getValueByKey(discountDetails, "PRODUCT_ID").toString());
        }

        loadDataTablePro(productBLL.getProductList());

        btAdd.setEnabled(false);
        btUpd.setEnabled(true);
        btDel.setEnabled(true);
    }

    public void fillForm2() {
        if (discountSelected != null && decentralizationMode == 3) {
            DefaultTableModel model = (DefaultTableModel) dataTable[1].getModel();
            Object[] rowData = model.getDataVector().elementAt(dataTable[1].getSelectedRow()).toArray();
            String[] data = new String[rowData.length];
            for (int i = 0; i < rowData.length; i++) {
                if (rowData[i] != null)
                    data[i] = rowData[i].toString();
            }
            String[] product = String.join(" | ", data).split(" \\| ");
            if (data[6] == null) {
                Product product1 = productBLL.findProducts("PRODUCT_ID", product[0]).get(0);
                newProductID_Dis.add(product[0]);
                double newPrice = product1.getCost() - (product1.getCost() * discountSelected.getDiscountPercent() / 100);
                model.setValueAt(newPrice, dataTable[1].getSelectedRow(), 5);
                model.setValueAt(true, dataTable[1].getSelectedRow(), 6);
            } else {
                newProductID_Dis.remove(product[0]);
                model.setValueAt(null, dataTable[1].getSelectedRow(), 5);
                model.setValueAt(null, dataTable[1].getSelectedRow(), 6);
            }
        }
    }

    public Discount getForm() throws Exception {
        String discountID;
        int discountPercent;
        Day startDay;
        Day endDay;
        int status;
        status = Objects.requireNonNull(jComboBox.getSelectedItem()).toString().equals("Đang áp dụng") ? 0 : 1;
        discountID = jTextFields[0].getText();
        discountPercent = Integer.parseInt(jTextFields[1].getText());
        startDay = new Day(jDateChooser[0].getDate());
        endDay = new Day(jDateChooser[1].getDate());
        checkStatus(discountID, status);
        return new Discount(discountID, discountPercent, startDay, endDay, status, false);
    }

    public void loadDataTableDis(List<Discount> data) {
        DefaultTableModel model = (DefaultTableModel) dataTable[0].getModel();
        model.setRowCount(0);
        for (Discount discount : data) {
            String status = discount.getStatus() == 0 ? "Đang áp dụng" : "Ngừng áp dụng";
            model.addRow(new Object[]{discount.getDiscountID(), discount.getDiscountPercent(), discount.getStartDay(), discount.getEndDay(), status});
        }
    }

    public void loadDataTablePro(List<Product> data) {
        DefaultTableModel model = (DefaultTableModel) dataTable[1].getModel();
        model.setRowCount(0);
        if (discountSelected == null) {
            for (Product product : data) {
                model.addRow(new Object[]{product.getProductID(), product.getName(), product.getCategoryID(), product.getSized(), product.getCost()});
            }
        } else {
            for (Product product : data) {
                if (newProductID_Dis.contains(product.getProductID())) {
                    double newPrice = product.getCost() - (product.getCost() * discountSelected.getDiscountPercent() / 100);
                    model.addRow(new Object[]{product.getProductID(), product.getName(), product.getCategoryID(), product.getSized(), product.getCost(), newPrice, true});
                } else {
                    model.addRow(new Object[]{product.getProductID(), product.getName(), product.getCategoryID(), product.getSized(), product.getCost(), null});
                }
            }
        }
    }

    public boolean checkInput() {
        for (JTextField textField : jTextFields) {
            if (textField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                textField.requestFocusInWindow();
                return false;
            }
        }
        if (!jTextFields[1].getText().matches("^-?\\d+$")) {
            // Discount percent must be an integer
            jTextFields[1].requestFocusInWindow();
            jTextFields[1].selectAll();
            JOptionPane.showMessageDialog(this, "Phần trăm giảm giá phải là số nguyên", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        Day startDate = new Day(jDateChooser[0].getDate());
        Day endDate = new Day(jDateChooser[1].getDate());
        if (startDate.isAfter(endDate)) {
            JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải trước ngày kết thúc", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public void checkStatus(String discountID, int status) {
        if (status == 0) {
            for (Discount discount1 : discountBLL.getDiscountList()) {
                if (!discount1.getDiscountID().contains(discountID)) {
                    discountBLL.updateDiscount(new Discount(discount1.getDiscountID(), discount1.getDiscountPercent(), discount1.getStartDay(), discount1.getEndDay(), 1, false));
                }
            }
        }
    }

}
