package com.cafe.GUI;

import com.cafe.BLL.IngredientBLL;
import com.cafe.BLL.ReceiptBLL;
import com.cafe.BLL.ReceiptDetailsBLL;
import com.cafe.BLL.SupplierBLL;
import com.cafe.DTO.Ingredient;
import com.cafe.DTO.Receipt;
import com.cafe.DTO.ReceiptDetails;
import com.cafe.DTO.Supplier;
import com.cafe.custom.Button;
import com.cafe.custom.*;
import com.cafe.utils.Day;
import com.cafe.utils.VNString;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IngredientGUI extends JPanel {

    private ArrayList<Ingredient> receiptDetails = new ArrayList<>();
    private ArrayList<Integer> listQuantityChoice = new ArrayList<>();
    private IngredientBLL ingredientBLL = new IngredientBLL();
    private SupplierBLL supplierBLL = new SupplierBLL();
    private int decentralizationMode;
    private String supplierID;
    private DataTable dataTable;
    private DataTable dataTable1;
    private RoundPanel ingredient;
    private RoundPanel roundPanel1;
    private RoundPanel roundPanel2;
    private RoundPanel search;
    private RoundPanel search1;
    private JScrollPane scrollPane;
    private RoundPanel pnlIngredientConfiguration;
    private JComboBox<Object> cbbSearchFilter;
    private JComboBox<Object> cbbSearchSupplier;
    private JComboBox<Object> cbbUnitSearch;
    private JTextField txtSearch;
    private JTextField txtSearch1;
    private Button btsupplier;
    private Button btreceipt;
    private Button btImport;
    private Button btCancel;
    private RoundPanel[] roundPanel;
    private JLabel[] label;
    private String staffid;
    private JScrollPane ingredientscrollPane;
    private ReceiptBLL receiptBLL = new ReceiptBLL();
    private ReceiptDetailsBLL receiptDetailsBLL = new ReceiptDetailsBLL();
    private FrameIngredient frameIngredient;

    public IngredientGUI(int decentralizationMode, String staffid) {
        this.staffid = staffid;
        this.decentralizationMode = decentralizationMode;
        this.supplierID = null;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(51, 51, 51));
        initComponents();
    }

    public ArrayList<Ingredient> getReceiptDetails() {
        return receiptDetails;
    }

    public void setReceiptDetails(ArrayList<Ingredient> receiptDetails) {
        this.receiptDetails = receiptDetails;
    }

    public ArrayList<Integer> getListQuantityChoice() {
        return listQuantityChoice;
    }

    public void setListQuantityChoice(ArrayList<Integer> listQuantityChoice) {
        this.listQuantityChoice = listQuantityChoice;
    }

    public void initComponents() {
        ingredient = new RoundPanel();
        roundPanel1 = new RoundPanel();
        roundPanel2 = new RoundPanel();
        roundPanel = new RoundPanel[15];
        label = new JLabel[15];
        for (int i = 0; i < roundPanel.length; i++) {
            roundPanel[i] = new RoundPanel();
            label[i] = new JLabel();
        }
        List<String> columnNames = ingredientBLL.getIngredientDAL().getColumnNames();
        List<String> columnName1 = supplierBLL.getSupplierDAL().getColumnNames();
        List<String> sublist2 = columnNames.subList(0, 2);
        List<String> result = new ArrayList<>(sublist2);
        List<String> sublist1 = columnNames.subList(3, columnNames.size() - 1);
        result.addAll(sublist1);
        search = new RoundPanel();
        search1 = new RoundPanel();
        pnlIngredientConfiguration = new RoundPanel();
        cbbSearchFilter = new JComboBox<>(new String[]{"Mã nguyên liệu", "Tên nguyên liệu", "Đơn vị", "Đơn giá", "Mã nhà cung cấp"});
        cbbSearchSupplier = new JComboBox<>(new String[]{"Mã nhà cung cấp", "Tên nhà cung cấp", "Điện thoại", "Địa chỉ", "Email"});
        cbbUnitSearch = new JComboBox<>(new String[]{"kg", "l", "bag"});
        txtSearch = new JTextField();
        txtSearch1 = new JTextField();
        btCancel = new Button();
        btImport = new Button();
        btsupplier = new Button();
        btreceipt = new Button();
        ingredientscrollPane = new JScrollPane(roundPanel[10]);

        ingredient.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        ingredient.setBackground(new Color(70, 67, 67));
        this.add(ingredient, BorderLayout.CENTER);

        roundPanel1.setBackground(new Color(255, 255, 255));
        roundPanel1.setPreferredSize(new Dimension(635, 670));
        roundPanel1.setAutoscrolls(true);
        ingredient.add(roundPanel1);


        roundPanel[11].setBackground(new Color(255, 255, 255));
        roundPanel[11].setPreferredSize(new Dimension(635, 50));
        roundPanel[11].setAutoscrolls(true);
        roundPanel1.add(roundPanel[11]);

        roundPanel[12].setLayout(new BorderLayout(0, 10));
        roundPanel[12].setBackground(new Color(255, 255, 255));
        roundPanel[12].setPreferredSize(new Dimension(620, 100));
        roundPanel[12].add(new JScrollPane(dataTable1), BorderLayout.CENTER);
        roundPanel[12].setAutoscrolls(true);
        roundPanel1.add(roundPanel[12]);

        roundPanel[1].setBackground(new Color(255, 255, 255));
        roundPanel[1].setPreferredSize(new Dimension(635, 50));
        roundPanel[1].setAutoscrolls(true);
        roundPanel1.add(roundPanel[1]);

        roundPanel[2].setLayout(new BorderLayout(0, 10));
        roundPanel[2].setBackground(new Color(255, 255, 255));
        roundPanel[2].setPreferredSize(new Dimension(620, 400));
        roundPanel[2].add(new JScrollPane(dataTable), BorderLayout.CENTER);
        roundPanel[2].setAutoscrolls(true);
        roundPanel1.add(roundPanel[2]);


        roundPanel2.setPreferredSize(new Dimension(350, 670));
        roundPanel2.setAutoscrolls(true);
        ingredient.add(roundPanel2);

        search.setLayout(new FlowLayout());
        search.setBackground(new Color(0xFFFFFF));
        search.setPreferredSize(new Dimension(635, 40));
        roundPanel[1].add(search, BorderLayout.NORTH);

        cbbSearchFilter.setPreferredSize(new Dimension(120, 30));
        cbbSearchFilter.addActionListener(e -> selectSearchFilter());
        search.add(cbbSearchFilter);

        txtSearch.setPreferredSize(new Dimension(200, 30));
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchIngredient();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchIngredient();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchIngredient();
            }
        });
        search.add(txtSearch);
        cbbUnitSearch.setVisible(false);
        cbbUnitSearch.addItemListener(e -> unitSearch());
        search.add(cbbUnitSearch);

        search1.setLayout(new FlowLayout());
        search1.setBackground(new Color(0xFFFFFF));
        search1.setPreferredSize(new Dimension(635, 40));
        roundPanel[11].add(search1, BorderLayout.NORTH);

        cbbSearchSupplier.setPreferredSize(new Dimension(120, 30));
        search1.add(cbbSearchSupplier);

        txtSearch1.setPreferredSize(new Dimension(200, 30));
        txtSearch1.getDocument().addDocumentListener(new DocumentListener() {
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
        search1.add(txtSearch1);

        dataTable = new DataTable(null, new String[]{"Mã nguyên liệu", "Tên nguyên liệu", "Đơn vị", "Đơn giá", "Mã nhà cung cấp"}, e -> fillForm());
        scrollPane = new JScrollPane(dataTable);
        roundPanel[2].add(scrollPane);

        dataTable1 = new DataTable(supplierBLL.getData(), new String[]{"Mã nhà cung cấp", "Tên nhà cung cấp", "Điện thoại", "Địa chỉ", "Email"}, e -> fillForm1());
        scrollPane = new JScrollPane(dataTable1);
        roundPanel[12].add(scrollPane);

        roundPanel[3].setPreferredSize(new Dimension(350, 50));
        roundPanel[3].setAutoscrolls(true);
        roundPanel2.add(roundPanel[3]);

        roundPanel[4].setPreferredSize(new Dimension(350, 90));
        roundPanel[4].setAutoscrolls(true);
        roundPanel2.add(roundPanel[4]);

        roundPanel[5].setPreferredSize(new Dimension(340, 430));
        roundPanel[5].setAutoscrolls(true);
        roundPanel2.add(roundPanel[5]);

        roundPanel[6].setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        roundPanel[6].setPreferredSize(new Dimension(330, 30));
        roundPanel[6].setAutoscrolls(true);
        roundPanel2.add(roundPanel[6]);

        roundPanel[7].setLayout(new BorderLayout());
        roundPanel[7].setPreferredSize(new Dimension(340, 40));
        roundPanel[7].setAutoscrolls(true);
        roundPanel2.add(roundPanel[7]);

        roundPanel[8].setLayout(new FlowLayout(FlowLayout.LEFT));
        roundPanel[8].setPreferredSize(new Dimension(340, 40));
        roundPanel[8].setAutoscrolls(true);
        roundPanel[4].add(roundPanel[8]);

        roundPanel[9].setLayout(new FlowLayout(FlowLayout.LEFT));
        roundPanel[9].setPreferredSize(new Dimension(340, 40));
        roundPanel[9].setAutoscrolls(true);
        roundPanel[4].add(roundPanel[9]);

        label[1].setFont(new Font("Times New Roman", Font.BOLD, 30));
        label[1].setHorizontalAlignment(JLabel.CENTER);
        label[1].setText("PHIẾU NHẬP HÀNG");
        label[1].setPreferredSize(new Dimension(400, 40));
        label[1].setAutoscrolls(true);
        roundPanel[3].add(label[1]);

        label[2].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[2].setHorizontalAlignment(JLabel.LEFT);
        label[2].setText("Mã nhập hàng:");
        label[2].setPreferredSize(new Dimension(100, 30));
        label[2].setAutoscrolls(true);
        roundPanel[8].add(label[2]);

        label[3].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[3].setHorizontalAlignment(JLabel.LEFT);
        label[3].setText(receiptBLL.getAutoID());
        label[3].setPreferredSize(new Dimension(80, 30));
        label[3].setAutoscrolls(true);
        roundPanel[8].add(label[3]);


        label[4].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[4].setHorizontalAlignment(JLabel.LEFT);
        label[4].setText("Ngày:");
        label[4].setPreferredSize(new Dimension(40, 30));
        label[4].setAutoscrolls(true);
        roundPanel[8].add(label[4]);

        LocalDate date = LocalDate.now();
        String dateString = String.valueOf(date);
        String[] dateArray = dateString.split("-");
        String day = dateArray[2];
        String month = dateArray[1];
        String year = dateArray[0];

        label[5].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[5].setHorizontalAlignment(JLabel.LEFT);
        label[5].setText(year + "-" + month + "-" + day);
        label[5].setPreferredSize(new Dimension(90, 30));
        label[5].setAutoscrolls(true);
        roundPanel[8].add(label[5]);

        label[6].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[6].setHorizontalAlignment(JLabel.LEFT);
        label[6].setText("Nhà cung cấp:");
        label[6].setPreferredSize(new Dimension(100, 30));
        label[6].setAutoscrolls(true);
        roundPanel[9].add(label[6]);

        label[7].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[7].setHorizontalAlignment(JLabel.LEFT);
        label[7].setPreferredSize(new Dimension(80, 30));
        label[7].setAutoscrolls(true);
        roundPanel[9].add(label[7]);

        label[10].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[10].setHorizontalAlignment(JLabel.LEFT);
        label[10].setText("Mã nhân viên:");
        label[10].setPreferredSize(new Dimension(90, 30));
        label[10].setAutoscrolls(true);
        roundPanel[9].add(label[10]);

        label[11].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[11].setHorizontalAlignment(JLabel.LEFT);
        label[11].setText(staffid);
        label[11].setPreferredSize(new Dimension(40, 30));
        label[11].setAutoscrolls(true);
        roundPanel[9].add(label[11]);

        label[8].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[8].setHorizontalAlignment(JLabel.LEFT);
        label[8].setText("Tổng tiền:");
        label[8].setPreferredSize(new Dimension(100, 30));
        label[8].setAutoscrolls(true);
        roundPanel[6].add(label[8]);

        label[9].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[9].setHorizontalAlignment(JLabel.RIGHT);
        label[9].setText(VNString.currency(0.0));
        label[9].setPreferredSize(new Dimension(100, 30));
        label[9].setAutoscrolls(true);
        roundPanel[6].add(label[9]);


        pnlIngredientConfiguration.setLayout(new GridLayout(6, 2, 20, 20));
        pnlIngredientConfiguration.setBackground(new Color(0xFFFFFF));
        pnlIngredientConfiguration.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
        pnlIngredientConfiguration.setPreferredSize(new Dimension(635, 300));
        roundPanel2.add(pnlIngredientConfiguration, BorderLayout.NORTH);

        btImport.setPreferredSize(new Dimension(135, 40));
        btImport.setBorderPainted(false);
        btImport.setRadius(15);
        btImport.setFocusPainted(false);
        btImport.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        btImport.setIcon(new ImageIcon(("img/plus.png")));
        btImport.setColor(new Color(0x70E149));
        btImport.setColorOver(new Color(0x5EFF00));
        btImport.setColorClick(new Color(0x8AD242));
        btImport.setText("Nhập Hàng");
        btImport.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                try {
                    pressImport();
                } catch (Exception ignored) {

                }
            }
        });
        roundPanel[7].add(btImport, BorderLayout.WEST);

        btCancel.setPreferredSize(new Dimension(135, 40));
        btCancel.setBorderPainted(false);
        btCancel.setRadius(15);
        btCancel.setFocusPainted(false);
        btCancel.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        btCancel.setIcon(new ImageIcon(("img/icons/remove.png")));
        btCancel.setColor(new Color(0xFFBD3737));
        btCancel.setColorOver(new Color(0xFF0000));
        btCancel.setColorClick(new Color(0xB65858));
        btCancel.setText("Hủy");
        btCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                pressCancel();
            }
        });
        roundPanel[7].add(btCancel, BorderLayout.EAST);


        ingredientscrollPane.setPreferredSize(new Dimension(340, 420));
        ingredientscrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        roundPanel[10].setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        roundPanel[10].setBackground(new Color(240, 240, 240));
        roundPanel[10].setBorder(BorderFactory.createLineBorder(Color.black));
        roundPanel[10].setPreferredSize(new Dimension(ingredientscrollPane.getWidth(), 420));
        roundPanel[5].add(ingredientscrollPane);

    }

    public void searchIngredient() {
        ingredientBLL.setIngredientList(ingredientBLL.findIngredients("SUPPLIER_ID", supplierID));
        if (txtSearch.getText().isEmpty()) {
            loadDataTable(ingredientBLL.getIngredientList());
        } else {
            String key = null;
            switch (cbbSearchFilter.getSelectedIndex()){
                case 0 -> key = "INGREDIENT_ID";
                case 1 -> key = "NAME";
                case 3 -> key = "UNIT_PRICE";
                default -> {
                }
            }
            loadDataTable(ingredientBLL.findIngredients(key, txtSearch.getText()));
        }
    }

    public void searchSuppliers() {
        if (txtSearch1.getText().isEmpty()) {
            loadDataTable1(supplierBLL.getSupplierList());
        } else {
            String key = null;
            switch (cbbSearchSupplier.getSelectedIndex()){
                case 0 -> key = "SUPPLIER_ID";
                case 1 -> key = "NAME";
                case 2 -> key = "PHONE";
                case 3 -> key = "ADDRESS";
                case 4 -> key = "EMAIL";
                default -> {
                }
            }
            assert key != null;
            loadDataTable1(supplierBLL.findSuppliers(key, txtSearch1.getText()));
        }
    }

    public void loadDataTable(List<Ingredient> ingredientList) {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);
        for (Ingredient ingredient : ingredientList) {
            model.addRow(new Object[]{ingredient.getIngredientID(), ingredient.getName(), ingredient.getUnit(), ingredient.getUnitPrice(), ingredient.getSupplierID()});
        }
    }

    public void loadDataTable1(List<Supplier> supplierList) {
        DefaultTableModel model = (DefaultTableModel) dataTable1.getModel();
        model.setRowCount(0);
        for (Supplier supplier : supplierList) {
            model.addRow(new Object[]{supplier.getSupplierID(), supplier.getName(), supplier.getPhone(), supplier.getAddress(), supplier.getEmail()});
        }
    }

    public void pressImport() {
        if (checkInput()) {
            Receipt newReceipt = null;
            try {
                newReceipt = getForm1();
            } catch (Exception ignored) {

            }
            assert newReceipt != null;

            if (receiptBLL.addReceipt(newReceipt))
                JOptionPane.showMessageDialog(this, "Nhập hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Nhập hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);

            if (!receiptDetails.isEmpty() && !listQuantityChoice.isEmpty()) {
                for (int i = 0; i < receiptDetails.size(); i++) {
                    ReceiptDetails newReceiptDetails = new ReceiptDetails(newReceipt.getReceiptID(), receiptDetails.get(i).getIngredientID(), listQuantityChoice.get(i));
                    receiptDetailsBLL.addReceiptDetails(newReceiptDetails);
                }
            }
            supplierID = null;
            label[7].setText(null);

            pressCancel();

            loadDataTable(new ArrayList<>());
            loadDataTable1(supplierBLL.getSupplierList());

        }
    }

    public void pressCancel() {
        label[3].setText(receiptBLL.getAutoID());
        receiptDetails = new ArrayList<>();
        listQuantityChoice = new ArrayList<>();
        roundPanel[10].removeAll();
        roundPanel[10].repaint();
        roundPanel[10].revalidate();
        label[9].setText(VNString.currency(0.0));
    }

    public RoundPanel getRoundPanel() {
        return roundPanel[10];
    }

    public void setRoundPanel(RoundPanel roundPanel) {
        this.roundPanel[10] = roundPanel;
    }

    public void addIngredient(ArrayList<Ingredient> listIngredientArray, ArrayList<Integer> listQuantityChoice) {
        this.receiptDetails = listIngredientArray;
        if (this.receiptDetails.size() > 5) {
            int tall = 80 * this.receiptDetails.size();
            roundPanel[10].setPreferredSize(new Dimension(ingredientscrollPane.getWidth(), tall));
        }
        double totalPrice = 0.0;
        for (int i = 0; i < listIngredientArray.size(); i++) {
            int vt = i;
            BillDetailPanel billDetailPanel = new BillDetailPanel();
            billDetailPanel.setIngredient(receiptDetails.get(i), listQuantityChoice.get(i));
            Ingredient ingredient = listIngredientArray.get(i);
            String[] ingredientString = new String[6];
            ingredientString[0] = ingredient.getIngredientID();
            ingredientString[1] = ingredient.getName();
            ingredientString[2] = ingredient.getUnit();
            ingredientString[3] = VNString.currency(ingredient.getUnitPrice());
            ingredientString[4] = ingredient.getSupplierID();

            int index = listQuantityChoice.get(i);

            billDetailPanel.getPaymentFrame().addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    new FrameIngredient(IngredientGUI.this, ingredientString, index).setVisible(true);
                }
            });

            billDetailPanel.getPayment_img().addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (JOptionPane.showOptionDialog(null,
                        "Bạn có chắc chắn muốn xoá nguyên liệu này?",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new String[]{"Xoá", "Huỷ"},
                        "Xoá") == JOptionPane.YES_NO_OPTION) {
                        listIngredientArray.remove(vt);
                        listQuantityChoice.remove(vt);
                        roundPanel[10].removeAll();
                        roundPanel[10].repaint();
                        roundPanel[10].revalidate();
                        addIngredient(listIngredientArray, listQuantityChoice);
                    }
                }
            });
            totalPrice += ingredient.getUnitPrice() * listQuantityChoice.get(i);
            roundPanel[10].add(billDetailPanel);
            roundPanel[10].repaint();
            roundPanel[10].revalidate();
        }
        label[9].setText(VNString.currency(totalPrice));
    }

    public void fillForm() {
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        Object[] rowData = model.getDataVector().elementAt(dataTable.getSelectedRow()).toArray();
        String[] data = new String[rowData.length];
        for (int i = 0; i < rowData.length; i++) {
            data[i] = rowData[i].toString();
        }
        if (frameIngredient != null) {
            frameIngredient.dispose();
        }
        frameIngredient = new FrameIngredient(IngredientGUI.this, data, 1);
        frameIngredient.setVisible(true);
    }

    public void fillForm1() {
        DefaultTableModel model = (DefaultTableModel) dataTable1.getModel();
        Object[] rowData = model.getDataVector().elementAt(dataTable1.getSelectedRow()).toArray();
        String[] data = new String[rowData.length];
        for (int i = 0; i < rowData.length; i++) {
            data[i] = rowData[i].toString();
        }
        label[7].setText(data[1]);
        ingredientBLL.setIngredientList(ingredientBLL.searchIngredients("DELETED = 0"));
        loadDataTable(ingredientBLL.findIngredients("SUPPLIER_ID", data[0]));
        supplierID = data[0];

        pressCancel();
    }

    private void unitSearch() {
        ingredientBLL.setIngredientList(ingredientBLL.findIngredients("SUPPLIER_ID", supplierID));
        loadDataTable(ingredientBLL.findIngredients("UNIT", Objects.requireNonNull(cbbUnitSearch.getSelectedItem()).toString()));
    }

    private void selectSearchFilter() {
        if (Objects.requireNonNull(cbbSearchFilter.getSelectedItem()).toString().equals("Đơn vị")) {
            txtSearch.setVisible(false);
            cbbUnitSearch.setSelectedIndex(0);
            cbbUnitSearch.setVisible(true);
            if (supplierID != null) {
                unitSearch();
            }
        } else {
            cbbUnitSearch.setVisible(false);
            txtSearch.setVisible(true);
            if (supplierID != null) {
                searchIngredient();
            }
        }
    }

    public Receipt getForm1() throws Exception {
        String receiptID = label[3].getText();
        String staffID = label[11].getText();
        Day dor = Day.parseDay(label[5].getText());
        double grandTotal = 0;
        return new Receipt(receiptID, staffID, dor, grandTotal, supplierID, false);
    }

    public boolean checkInput() {
        if (supplierID == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (receiptDetails.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nguyên liệu nhập!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}
