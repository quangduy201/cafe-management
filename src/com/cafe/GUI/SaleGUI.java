package com.cafe.GUI;

import com.cafe.BLL.*;
import com.cafe.DTO.*;
import com.cafe.custom.Button;
import com.cafe.custom.*;
import com.cafe.utils.Day;
import com.cafe.utils.Tasks;
import com.cafe.utils.VNString;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class SaleGUI extends JPanel {
    private List<Product> listDetailBill = new ArrayList<>();
    private List<Integer> listQuantityChoice = new ArrayList<>();
    private List<Product> productlist = new ArrayList<>();
    private ProductBLL productBLL = new ProductBLL();
    private RecipeBLL recipeBLL = new RecipeBLL();

    private BillBLL billBLL = new BillBLL();
    private BillDetailsBLL billDetailsBLL = new BillDetailsBLL();
    private CustomerBLL customerBLL = new CustomerBLL();
    private CategoryBLL categoryBLL = new CategoryBLL();
    private List<Object> categoryNameList;
    private ProductDetailsGUI productDetailsGUI;
    private String staffID;
    private JLabel[] infoLabel;
    private JTextField[] infoTxt;
    private JComboBox<String> jComboBox;
    private JTextField txtSearch1;
    private JTextField txtSearch2;
    private JTextField[] jTextField;
    private JLabel[] jLabel;
    private RoundPanel sale;
    private RoundPanel roundPanel1;
    private RoundPanel roundPanel2;
    private RoundPanel roundPanel3;
    private RoundPanel roundPanel4;
    private RoundPanel roundPanel5;
    private RoundPanel roundPanel6;
    private RoundPanel roundPanel7;
    private RoundPanel roundPanel8;
    private RoundPanel roundPanel9;
    private RoundPanel roundPanel10;
    private RoundPanel roundPanel11;
    private RoundPanel roundPanel12;
    private RoundPanel roundPanel13;
    private RoundPanel roundPanel14;
    private RoundPanel roundPanel15;
    private JScrollPane productScrollPane2;
    private JScrollPane productScrollPane1;
    private Button btnSearch1;
    private Button btnSearch2;
    private Button btnPurchase;
    private Button btnCancel;
    private Button btnSearchByFace;
    private final ProductPanel[] productPanel = new ProductPanel[productBLL.findProductsBy(Map.of()).size()];
    private int sl = 0;

    public SaleGUI(String staffID) {
        System.gc();
        this.staffID = staffID;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(70, 67, 67));
        initComponents();
    }

    public List<Product> getListDetailBill() {
        return listDetailBill;
    }

    public void setListDetailBill(List<Product> listDetailBill) {
        this.listDetailBill = listDetailBill;
    }

    public List<Integer> getListQuantityChoice() {
        return listQuantityChoice;
    }

    public void setListQuantityChoice(List<Integer> listQuantityChoice) {
        this.listQuantityChoice = listQuantityChoice;
    }

    public RoundPanel getRoundPanel9() {
        return roundPanel9;
    }

    public void setRoundPanel9(RoundPanel roundPanel9) {
        this.roundPanel9 = roundPanel9;
    }

    public void initComponents() {
        Font fontTimesNewRoman = new Font("Times New Roman", Font.PLAIN, 14);
        roundPanel1 = new RoundPanel();
        roundPanel2 = new RoundPanel();
        roundPanel3 = new RoundPanel();
        roundPanel4 = new RoundPanel();
        roundPanel5 = new RoundPanel();
        roundPanel6 = new RoundPanel();
        roundPanel7 = new RoundPanel();
        roundPanel8 = new RoundPanel();
        roundPanel9 = new RoundPanel();
        roundPanel10 = new RoundPanel();
        roundPanel11 = new RoundPanel();
        roundPanel12 = new RoundPanel();
        roundPanel13 = new RoundPanel();
        roundPanel14 = new RoundPanel();
        roundPanel15 = new RoundPanel();

        productScrollPane2 = new JScrollPane(roundPanel9);
        productScrollPane1 = new JScrollPane(roundPanel4);

        btnSearch1 = new Button();
        btnSearch2 = new Button();
        btnPurchase = new Button();
        btnCancel = new Button();
        btnSearchByFace = new Button();

        txtSearch1 = new JTextField();
        txtSearch2 = new JTextField();

        sale = new RoundPanel();
        jComboBox = new JComboBox<>();

        sale.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        sale.setBackground(new Color(70, 67, 67));
        this.add(sale, BorderLayout.CENTER);

        roundPanel1.setPreferredSize(new Dimension(615, 670));
        roundPanel1.setAutoscrolls(true);
        sale.add(roundPanel1);

        roundPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
        roundPanel2.setPreferredSize(new Dimension(370, 670));
        roundPanel2.setAutoscrolls(true);
        sale.add(roundPanel2);

        roundPanel3.setLayout(new BorderLayout(90, 0));
        roundPanel3.setPreferredSize(new Dimension(615, 50));
        roundPanel3.setAutoscrolls(true);
        roundPanel1.add(roundPanel3);

        productScrollPane1.setPreferredSize(new Dimension(600, 600));

        roundPanel6.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        roundPanel6.setPreferredSize(new Dimension(135, 35));
        roundPanel6.setAutoscrolls(true);
        roundPanel3.add(roundPanel6, BorderLayout.WEST);

        jComboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
        jComboBox.setMaximumRowCount(10); //so luong
        jComboBox.setPreferredSize(new Dimension(120, 35));
        jComboBox.setBorder(null);
        jComboBox.setFocusable(false);
        jComboBox.addActionListener(this::pressJComboBox);
        roundPanel6.add(jComboBox);

        roundPanel5.setLayout(new FlowLayout(FlowLayout.LEFT, 23, 5));
        roundPanel5.setPreferredSize(new Dimension(300, 35));
        roundPanel5.setAutoscrolls(true);
        roundPanel3.add(roundPanel5, BorderLayout.CENTER);

        txtSearch1.setFont(fontTimesNewRoman);
        txtSearch1.setPreferredSize(new Dimension(300, 35));
        txtSearch1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        txtSearch1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changeText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changeText();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changeText();
            }
        });
        roundPanel5.add(txtSearch1);


        btnSearch1.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSearch1.setPreferredSize(new Dimension(35, 35));
        btnSearch1.setIcon(new ImageIcon("img/icons/search.png"));
        btnSearch1.setFocusPainted(false);
        roundPanel5.add(btnSearch1);

        roundPanel7.setLayout(new BorderLayout(10, 0));
        roundPanel7.setPreferredSize(new Dimension(350, 35));
        roundPanel2.add(roundPanel7);

        categoryNameList = categoryBLL.getObjectsProperty("NAME", categoryBLL.getCategoryList());
        Vector<String> comboBoxItems = new Vector<>();
        comboBoxItems.add("TẤT CẢ");
        for (Object name : categoryNameList) {
            comboBoxItems.add(name.toString());
        }
        jComboBox.setModel(new DefaultComboBoxModel<>(comboBoxItems));

        txtSearch2.setFont(fontTimesNewRoman);
        txtSearch2.setPreferredSize(new Dimension(250, 35));
        txtSearch2.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        txtSearch2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                    e.consume();
                }
            }
        });
        roundPanel7.add(txtSearch2, BorderLayout.WEST);

        btnSearchByFace.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSearchByFace.setIcon(new ImageIcon(new ImageIcon("img/icons/face-scanner.png").getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
        btnSearchByFace.setPreferredSize(new Dimension(40, 35));
        btnSearchByFace.setFocusPainted(false);
        btnSearchByFace.setColorOver(new Color(0xA6A1A1));
        btnSearchByFace.setColorClick(new Color(0x2F2F2F));
        btnSearchByFace.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                findCustomerByFace();
            }
        });
        roundPanel7.add(btnSearchByFace, BorderLayout.EAST);

        btnSearch2.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSearch2.setPreferredSize(new Dimension(35, 35));
        btnSearch2.setIcon(new ImageIcon("img/icons/search.png"));
        btnSearch2.setFocusPainted(false);
        btnSearch2.setColorOver(new Color(0xA6A1A1));
        btnSearch2.setColorClick(new Color(0x2F2F2F));
        btnSearch2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                findCustomer();
            }
        });
        roundPanel7.add(btnSearch2, BorderLayout.CENTER);

        roundPanel8.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        roundPanel8.setPreferredSize(new Dimension(350, 35));
        roundPanel2.add(roundPanel8);

        roundPanel15.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        roundPanel15.setPreferredSize(new Dimension(350, 35));
        roundPanel2.add(roundPanel15);

        infoLabel = new JLabel[2];
        infoTxt = new JTextField[2];
        for (int i = 0; i < 2; i++) {
            infoLabel[i] = new JLabel();
            infoLabel[i].setFont(fontTimesNewRoman);
            infoLabel[i].setPreferredSize(new Dimension(110, 35));
            infoTxt[i] = new JTextField();
            infoTxt[i].setFont(fontTimesNewRoman);
            infoTxt[i].setPreferredSize(new Dimension(210, 35));
            infoTxt[i].setBorder(BorderFactory.createEmptyBorder());
            infoTxt[i].setEditable(false);
            infoTxt[i].setFocusable(false);
            if (i == 0) {
                infoLabel[i].setText("Tên khách hàng: ");
                roundPanel8.add(infoLabel[i]);
                roundPanel8.add(infoTxt[i]);
            } else {
                infoLabel[i].setText("Mã nhân viên: ");
                infoTxt[i].setText(staffID);
                roundPanel15.add(infoLabel[i]);
                roundPanel15.add(infoTxt[i]);
            }
        }

        productScrollPane2.setPreferredSize(new Dimension(350, 355));
        productScrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        productScrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        roundPanel9.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        roundPanel9.setBorder(BorderFactory.createLineBorder(Color.black));
        roundPanel9.setPreferredSize(new Dimension(productScrollPane2.getWidth(), productScrollPane2.getHeight()));
        roundPanel2.add(productScrollPane2);


        roundPanel10.setLayout(new BoxLayout(roundPanel10, BoxLayout.Y_AXIS));
        roundPanel10.setPreferredSize(new Dimension(350, 100));
        roundPanel2.add(roundPanel10);

        roundPanel11.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
        roundPanel11.setPreferredSize(new Dimension(350, 40));
        roundPanel2.add(roundPanel11);

        roundPanel12.setLayout(new FlowLayout(FlowLayout.RIGHT));
        roundPanel10.add(roundPanel12);
        roundPanel13.setLayout(new FlowLayout(FlowLayout.RIGHT));
        roundPanel10.add(roundPanel13);
        roundPanel14.setLayout(new FlowLayout(FlowLayout.RIGHT));
        roundPanel10.add(roundPanel14);

        jLabel = new JLabel[3];
        jTextField = new JTextField[3];
        for (int i = 0; i < 3; i++) {
            jLabel[i] = new JLabel();
            jLabel[i].setFont(fontTimesNewRoman);
            jTextField[i] = new JTextField();
            jTextField[i].setFont(fontTimesNewRoman);
            jTextField[i].setHorizontalAlignment(JTextField.RIGHT);
            if (i == 0) {
                jLabel[i].setText("Tổng tiền: ");
                roundPanel12.add(jLabel[i]);
                roundPanel12.add(jTextField[i]);
            } else if (i == 1) {
                jLabel[i].setText("Tiền nhận: ");
                roundPanel13.add(jLabel[i]);
                roundPanel13.add(jTextField[i]);
            } else {
                jLabel[i].setText("Tiền thừa: ");
                roundPanel14.add(jLabel[i]);
                roundPanel14.add(jTextField[i]);
            }

            if (i != 1) {
                jTextField[i].setPreferredSize(new Dimension(123, 25));
                jTextField[i].setBorder(BorderFactory.createEmptyBorder());
                jTextField[i].setEditable(false);
                jTextField[i].setFocusable(false);
                jTextField[i].setText(VNString.currency(0.0));
            } else {
                jTextField[i].setPreferredSize(new Dimension(120, 25));
                jTextField[i].getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        calculate();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        calculate();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        calculate();
                    }
                });
                jTextField[i].addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        char c = e.getKeyChar();
                        if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
                            e.consume();
                        }
                    }
                });
            }
        }

        BiConsumer<Button, List<Object>> configButton = (button, properties) -> {
            button.setPreferredSize(new Dimension(135, 40));
            button.setBorderPainted(false);
            button.setRadius(15);
            button.setFocusPainted(false);
            button.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            button.setBorderColor(Color.BLACK);
            button.setForeground(Color.BLACK);
            button.setText((String) properties.get(0));
            button.setColor((Color) properties.get(1));
            button.setColorOver((Color) properties.get(2));
            button.setColorClick((Color) properties.get(3));
            button.setIcon(new ImageIcon((String) properties.get(4)));
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    ((Runnable) properties.get(5)).run();
                }
            });
        };

        configButton.accept(btnPurchase, List.of("Thanh toán", new Color(0x70E149), new Color(0x5EFF00), new Color(0x8AD242), "img/icons/plus.png", (Runnable) this::purchase));
        roundPanel11.add(btnPurchase);

        configButton.accept(btnCancel, List.of("Hủy", new Color(0xFFBD3737), new Color(0xFF0000), new Color(0xB65858), "img/icons/remove.png", (Runnable) this::cancel));
        roundPanel11.add(btnCancel);

        productScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        productScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        roundPanel4.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 5));
        roundPanel4.setBorder(BorderFactory.createLineBorder(Color.black));
        roundPanel4.setAutoscrolls(true);
        roundPanel1.add(productScrollPane1);

        roundPanel9.setAutoscrolls(true);
        Set<String> setProductNames = new HashSet<>();
        for (Product product : productBLL.getProductList()) {
            if (!setProductNames.contains(product.getName())) {
                setProductNames.add(product.getName());
                productlist.add(product);
            }
        }
        loadProducts(product -> true);
    }

    public void addProductPanel(Product product, int index) {
        RoundPanel frameProduct = new RoundPanel();
        RoundPanel frameImg = new RoundPanel();
        JTextField categoryName = new JTextField();
        JLabel productImage = new JLabel();
        JTextField[] productName = new JTextField[2];
        Color color = new Color(0xB65858);

        productPanel[sl] = new ProductPanel();
        productPanel[sl].setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
        productPanel[sl].setPreferredSize(new Dimension(185, 250));
        productPanel[sl].setBackground(color);
        productPanel[sl].setColor(color);
        productPanel[sl].setColorOver(new Color(25, 25, 25));
        productPanel[sl].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                if (productDetailsGUI != null) {
                    productDetailsGUI.dispose();
                }
                productDetailsGUI = new ProductDetailsGUI(SaleGUI.this, product, index);
                productDetailsGUI.setVisible(true);
            }
        });
        roundPanel4.add(productPanel[sl]);

        frameProduct.setPreferredSize(new Dimension(181, 246));
        frameProduct.setBackground(color);
        productPanel[sl].add(frameProduct);
        sl++;

        Font fontDialog = new Font("Dialog", Font.PLAIN, 15);
        Category category = categoryBLL.findCategoriesBy(Map.of("CATEGORY_ID", product.getCategoryID())).get(0);
        categoryName.setFont(fontDialog);
        categoryName.setPreferredSize(new Dimension(120, 20));
        categoryName.setHorizontalAlignment(JTextField.CENTER);
        categoryName.setEditable(false);
        categoryName.setFocusable(false);
        categoryName.setBorder(BorderFactory.createEmptyBorder());
        categoryName.setBackground(color);
        categoryName.setText(category.getName());
        frameProduct.add(categoryName);

        frameImg.setPreferredSize(new Dimension(181, 165));
        frameImg.setBackground(color);
        frameProduct.add(frameImg);

        ImageIcon originalImage = new ImageIcon(product.getImage());
        productImage.setIcon(new ImageIcon(originalImage.getImage().getScaledInstance(181, 165, Image.SCALE_SMOOTH)));
        frameImg.add(productImage);

        String[] name = {"", ""};
        if (product.getName().length() <= 15) {
            name[0] = product.getName();
        } else {
            String[] splitName = product.getName().split(" ");
            for (String word : splitName) {
                if ((name[0] + " " + word).length() <= 15)
                    name[0] += " " + word;
                else
                    name[1] += " " + word;
            }
        }

        Dimension textFieldSize = new Dimension(150, 20);
        for (int i = 0; i < 2; i++) {
            productName[i] = new JTextField(name[i].trim());
            productName[i].setFont(fontDialog);
            productName[i].setPreferredSize(textFieldSize);
            productName[i].setEditable(false);
            productName[i].setFocusable(false);
            productName[i].setBorder(BorderFactory.createEmptyBorder());
            productName[i].setBackground(color);
            productName[i].setHorizontalAlignment(JTextField.CENTER);
            frameProduct.add(productName[i]);
        }
    }

    public void findCustomerByFace() {
        Tasks tasks = new Tasks("Camera");
        new Thread(() -> tasks.detectAndRecognize(50.0, infoTxt[0])).start();
    }

    public void findCustomer() {
        if (txtSearch2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin vào ô tìm kiếm", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!txtSearch2.getText().matches("^(\\+?84|0)[35789]\\d{8}$")) {
            // Phone must start with "0x" or "+84x" or "84x" where "x" in {3, 5, 7, 8, 9}
            txtSearch2.requestFocusInWindow();
            txtSearch2.selectAll();
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        List<Customer> customers = customerBLL.findCustomersBy(Map.of("PHONE", txtSearch2.getText()));
        if (customers.isEmpty()) {
            if (JOptionPane.showOptionDialog(null,
                "Số điện thoại không tồn tại trong hệ thống, bạn có muốn thêm mới?",
                "Thêm khách hàng mới?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Thêm", "Huỷ"},
                "Thêm") == JOptionPane.YES_OPTION) {
                infoTxt[0].setText("");
                new FrameCustomer(txtSearch2.getText()).setVisible(true);
            }
        } else {
            infoTxt[0].setText(customers.get(0).getName());
        }
    }

    public void addProductToBill(List<Product> listDetailBill, List<Integer> listQuantityChoice) {
        roundPanel9.removeAll();
        this.listDetailBill = listDetailBill;
        if (this.listDetailBill.size() >= 5) {
            int height = 75 * this.listDetailBill.size();
            roundPanel9.setPreferredSize(new Dimension(productScrollPane2.getWidth(), height));
        }
        double totalPrice = 0.0;
        for (int i = 0; i < listDetailBill.size(); i++) {
            BillDetailPanel billDetailPanel = new BillDetailPanel();
            Product product = listDetailBill.get(i);
            billDetailPanel.setData(listDetailBill.get(i), listQuantityChoice.get(i));

            int index = i;
            billDetailPanel.getPaymentFrame().addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    new ProductDetailsGUI(SaleGUI.this, product, listQuantityChoice.get(index)).setVisible(true);
                }
            });
            billDetailPanel.getPayment_img().addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (JOptionPane.showOptionDialog(null,
                        "Bạn có chắc chắn muốn xoá sản phẩm khỏi hoá đơn này?",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new String[]{"Xoá", "Huỷ"},
                        "Xoá") == JOptionPane.YES_OPTION) {
                        updateIngredient(product.getProductID(), listQuantityChoice.get(index));
                        listDetailBill.remove(index);
                        listQuantityChoice.remove(index);
                        roundPanel9.repaint();
                        roundPanel9.revalidate();
                        addProductToBill(listDetailBill, listQuantityChoice);
                    }
                }
            });
            totalPrice += product.getCost() * listQuantityChoice.get(i);
            roundPanel9.add(billDetailPanel);
            roundPanel9.repaint();
            roundPanel9.revalidate();
        }
        jTextField[0].setText(VNString.currency(totalPrice));
        calculate();
    }

    public void purchase() {
        if (roundPanel4.getComponentCount() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để mua", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } else if (jTextField[1].getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nhập số tiền nhận từ khách hàng để thanh toán", "Lỗi", JOptionPane.ERROR_MESSAGE);
            jTextField[1].requestFocusInWindow();
            jTextField[1].selectAll();
        } else if (Double.parseDouble(jTextField[2].getText().replaceAll("[^\\d-]+", "")) > 0.0) {
            JOptionPane.showMessageDialog(this, "Không đủ tiền", "Lỗi", JOptionPane.ERROR_MESSAGE);
            jTextField[1].requestFocusInWindow();
            jTextField[1].selectAll();
        } else {
            Bill bill;
            BillDetails addbillDetails;
            String billID = billBLL.getAutoID();
            String customerID = "CUS000";
            String customerName = infoTxt[0].getText();
            if (!customerName.equals(""))
                customerID = customerBLL.findCustomersBy(Map.of("NAME", customerName)).get(0).getCustomerID();

            SimpleDateFormat formatter = new SimpleDateFormat();
            formatter.applyPattern("yyyy-MM-dd");
            String staffID = this.staffID;
            Day date = new Day();
            double received = Double.parseDouble(jTextField[1].getText());
            double excess = Double.parseDouble(jTextField[2].getText().replaceAll("\\D+", ""));
            bill = new Bill(billID, customerID, staffID, date, 0.0, received, excess, false);

            if (billBLL.exists(bill))
                JOptionPane.showMessageDialog(this, "Hoá đơn đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (billBLL.exists(Map.of("BILL_ID", bill.getBillID())))
                JOptionPane.showMessageDialog(this, "Hoá đơn đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            else if (billBLL.addBill(bill))
                JOptionPane.showMessageDialog(this, "Mua hàng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "Mua hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);

//            billBLL.updateBill(bill);
            double billDetail_total;
            double billDetail_percent;
            for (int i = 0; i < listDetailBill.size(); i++) {
                Product product = listDetailBill.get(i);
                billDetail_total = product.getCost();
                billDetail_percent = 0.0;
                addbillDetails = new BillDetails(billID, product.getProductID(), listQuantityChoice.get(i), billDetail_total, billDetail_percent);
                billDetailsBLL.addBillDetails(addbillDetails);
            }
            cancel();
        }
        cancel();
    }

    public void cancel() {
        int size = listDetailBill.size();
        for (int i = 0; i < size; i++) {
            listDetailBill.remove(0);
            listQuantityChoice.remove(0);
        }
        txtSearch2.setText("");
        infoTxt[0].setText("");
        roundPanel9.removeAll();
        roundPanel9.repaint();
        roundPanel9.revalidate();
        roundPanel9.setPreferredSize(new Dimension(productScrollPane2.getWidth(), 352));
        jTextField[0].setText(VNString.currency(0.0));
        jTextField[1].setText("");
        jTextField[2].setText(VNString.currency(0.0));
    }

    public void pressJComboBox(ActionEvent evt) {
        String categoryName = Objects.requireNonNull(jComboBox.getSelectedItem()).toString();
        String categoryID;
        if (categoryName.equals("TẤT CẢ"))
            categoryID = "";
        else
            categoryID = categoryBLL.findCategoriesBy(Map.of("NAME", categoryName)).get(0).getCategoryID();
        refreshProducts(product -> product.getCategoryID().contains(categoryID));
    }

    public void changeText() {
        String name = VNString.removeUpperCaseAccent(txtSearch1.getText().toUpperCase());
        refreshProducts(product -> VNString.removeUpperCaseAccent(product.getName()).contains(name));
    }

    public void refreshProducts(Function<Product, Boolean> condition) {
        roundPanel4.removeAll();
        roundPanel4.repaint();
        roundPanel4.revalidate();
        int size = 0;
        for (int i = 0; i < productlist.size(); i++) {
            if (condition.apply(productlist.get(i))) {
                roundPanel4.add(productPanel[i]);
                size++;
            }
        }
        Double height = 256 * Math.ceil(Double.valueOf(size) / 3);
        roundPanel4.setPreferredSize(new Dimension(productScrollPane1.getWidth(), height.intValue()));
    }

    public void loadProducts(Function<Product, Boolean> condition) {
        roundPanel4.removeAll();
        roundPanel4.repaint();
        roundPanel4.revalidate();
        int size = 0;
        for (Product product : productlist) {
            if (condition.apply(product)) {
                addProductPanel(product, size);
                size++;
            }
        }
        Double height = 256 * Math.ceil(Double.valueOf(size) / 3);
        roundPanel4.setPreferredSize(new Dimension(productScrollPane1.getWidth(), height.intValue()));
    }

    public void calculate() {
        String text = jTextField[1].getText();
        double change = 0.0;
        if (!text.isEmpty())
            change = Double.parseDouble(jTextField[0].getText().replaceAll("\\D+", "")) - Double.parseDouble(text);
        jTextField[2].setText(VNString.currency(change));
    }

    public void updateIngredient(String product_ID, int quantity) {
        List<Recipe> recipeList = recipeBLL.findRecipes("PRODUCT_ID", product_ID);
        for (Recipe recipe : recipeList) {
            for (Ingredient ingredient : ProductDetailsGUI.ingredientList) {
                if (Objects.equals(recipe.getIngredientID(), ingredient.getIngredientID())) {
                    ingredient.setQuantity(ingredient.getQuantity() + recipe.getMass() * quantity);
                }
            }
        }
    }
}
