package com.cafe.GUI;

import com.cafe.BLL.CategoryBLL;
import com.cafe.BLL.ProductBLL;
import com.cafe.DTO.Category;
import com.cafe.DTO.Ingredient;
import com.cafe.DTO.Product;
import com.cafe.custom.Button;
import com.cafe.custom.DetailBill;
import com.cafe.custom.ProductPanel;
import com.cafe.custom.RoundPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;

public class SaleGUI extends JPanel {

//    private ArrayList<Product> listProduct = new ArrayList<Product>();
      private ArrayList<Product> listDetailBill = new ArrayList<Product>();

    public ArrayList<Product> getListDetailBill() {
        return listDetailBill;
    }

    public void setListDetailBill(ArrayList<Product> listDetailBill) {
        this.listDetailBill = listDetailBill;
    }

    private ArrayList<Integer> listQuantityChoice = new ArrayList<Integer>();

    public ArrayList<Integer> getListQuantityChoice() {
        return listQuantityChoice;
    }

    public void setListQuantityChoice(ArrayList<Integer> listQuantityChoice) {
        this.listQuantityChoice = listQuantityChoice;
    }

    private CategoryBLL categoryBLL;
    private Category categoryName;
    private ArrayList<String> NameList;
    private ArrayList<String> IdList;

    public void setRoundPanel9(RoundPanel roundPanel9) {
        this.roundPanel9 = roundPanel9;
    }

    private NewJFrame1 newJFrame1;
    public RoundPanel getRoundPanel9() {
        return roundPanel9;
    }

    public SaleGUI(){
        categoryBLL = new CategoryBLL();
        NameList = new ArrayList<>();
        for (Category category : categoryBLL.getCategoryList()) {
            NameList.add(category.getName());
        }
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(70, 67, 67));
        initComponents();
    }

    private  Set<String> setProduct;

    private ProductBLL productBLL = new ProductBLL();

    public void initComponents(){
        ProductBLL productBLL = new ProductBLL();
        ArrayList<Product> productlist = new ArrayList<>();
        ArrayList<String> slproductname = new ArrayList<>();
        for (Product product : productBLL.getProductList()) {
            productlist.add(product);
            slproductname.add(product.getName());
        }

        setProduct = new HashSet<String>();
        for (String a : slproductname) {
            setProduct.add(a);
        }



        frameProduct = new RoundPanel[setProduct.size()];
        productPanel = new ProductPanel[setProduct.size()];
        frameImg = new RoundPanel[setProduct.size()];
        slCategorName = new JTextField[setProduct.size()];
        originalIcon = new ImageIcon[setProduct.size()];
        slProductImg = new JLabel[setProduct.size()];
        slProductname1 = new JTextField[setProduct.size()];
        slProductname2 = new JTextField[setProduct.size()];

        for (int i = 0; i < setProduct.size(); i++) {
            frameImg[i] = new RoundPanel();
            slCategorName[i] = new JTextField();
            slProductImg[i] = new JLabel();
            slProductname1[i] = new JTextField();
            slProductname2[i] = new JTextField();
            frameProduct[i] = new RoundPanel();
            productPanel[i] = new ProductPanel();
        }

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

        pictureScrollPane = new JScrollPane(roundPanel9);
        pictureScrollPane2 = new JScrollPane(roundPanel4);

        jlabel1 = new JLabel();
        jlabel2 = new JLabel();
        jlabel3 = new JLabel();
        jlabel4 = new JLabel();
        jlabel5 = new JLabel();
        jlabel6 = new JLabel();

        button1 = new Button();
        button2 = new Button();
        button3 = new Button();
        button4 = new Button();

        search = new JTextField();
        search1 = new JTextField();
        txtname = new JTextField();
        jTextField1 = new JTextField();
        jTextField2 = new JTextField();
        jTextField3 = new JTextField();
        jTextField4 = new JTextField();
        jTextField5 = new JTextField();
        jTextField6 = new JTextField();

        jTextArea1 = new JTextArea();
        jTextArea2 = new JTextArea();
        jTextArea3 = new JTextArea();

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

        pictureScrollPane2.setPreferredSize(new Dimension(600, 600));


        roundPanel6.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        roundPanel6.setPreferredSize(new Dimension(135, 35));
        roundPanel6.setAutoscrolls(true);
        roundPanel3.add(roundPanel6, BorderLayout.WEST);

        jComboBox.setFont(new Font("Dialog", 0, 12));
        jComboBox.setMaximumRowCount(10);//so luong
        jComboBox.setPreferredSize(new Dimension(120, 35));
        jComboBox.setBorder(null);
        jComboBox.setFocusable(false);
        roundPanel6.add(jComboBox);

        roundPanel5.setLayout(new FlowLayout(FlowLayout.LEFT, 23, 5));
        roundPanel5.setPreferredSize(new Dimension(300, 35));
        roundPanel5.setAutoscrolls(true);
        roundPanel3.add(roundPanel5, BorderLayout.CENTER);

        search.setFont(new Font("Times New Roman", 0, 14));
        search.setPreferredSize(new Dimension(300, 35));
        search.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        roundPanel5.add(search);
//        jComboBox.addActionListener(new event.ActionListener() {
//            public void actionPerformed(event.ActionEvent evt) {
//                jComboBoxActionPerformed(evt);
//            }
//        });

        button1.setBackground(new Color(240, 240, 240));
        button1.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        button1.setPreferredSize(new Dimension(35, 35));
        button1.setIcon(new ImageIcon(("img/search.png"))); // NOI18N
        button1.setFocusPainted(false);
        roundPanel5.add(button1);

        roundPanel7.setLayout(new BorderLayout(65, 0));
        roundPanel7.setPreferredSize(new Dimension(350, 35));
        roundPanel2.add(roundPanel7);

        roundPanel8.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        roundPanel8.setPreferredSize(new Dimension(350, 35));
        roundPanel2.add(roundPanel8);

        pictureScrollPane.setPreferredSize(new Dimension(350, 400));
        roundPanel9.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        roundPanel9.setBackground(new Color(240, 240, 240));
        roundPanel9.setBorder(BorderFactory.createLineBorder(Color.black));
        roundPanel9.setPreferredSize(new Dimension(pictureScrollPane.getWidth(), 400));
        roundPanel2.add(pictureScrollPane);
//        pictureScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
//        pictureScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

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


        Vector comboBoxItems = new Vector();
        comboBoxItems.add("Tất cả");
        for (String Name : NameList)
            comboBoxItems.add(Name);
        jComboBox.setModel(new DefaultComboBoxModel(comboBoxItems));

        search1.setFont(new Font("Times New Roman", 0, 14));
        search1.setPreferredSize(new Dimension(250, 35));
        search1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        search1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PressSearch1(evt);
            }
        });
        roundPanel7.add(search1, BorderLayout.WEST);


        button2.setBackground(new Color(240, 240, 240));
        button2.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        button2.setPreferredSize(new Dimension(35, 35));
        button2.setIcon(new ImageIcon(("img/search.png")));
        button2.setFocusPainted(false);
        button2.setColor(new Color(240, 240, 240));
        button2.setColorOver(new Color(0x544444));
        button2.setColorOver(new Color(0x2F2424));
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

            }
        });

        roundPanel7.add(button2, BorderLayout.CENTER);


        jlabel1.setFont(new Font("Times New Roman", 0, 14));
        jlabel1.setPreferredSize(new Dimension(110, 35));
        jlabel1.setText("Tên Khách Hàng: ");
        roundPanel8.add(jlabel1);

        txtname.setFont(new Font("Times New Roman", 0, 14));
        txtname.setPreferredSize(new Dimension(250, 35));
        txtname.setBorder(BorderFactory.createEmptyBorder());
        txtname.setEditable(false);
        txtname.setBackground(new Color(240, 240, 240));
        roundPanel8.add(txtname);

        jlabel2.setFont(new Font("Times New Roman", 0, 14));
        jlabel2.setText("Tổng tiền: ");
        roundPanel12.add(jlabel2);

        jlabel3.setFont(new Font("Times New Roman", 0, 14));
        jlabel3.setText("Tiền nhận: ");
        roundPanel13.add(jlabel3);

        jlabel4.setFont(new Font("Times New Roman", 0, 14));
        jlabel4.setText("Tiền thừa: ");
        roundPanel14.add(jlabel4);

        jTextField1.setFont(new Font("Times New Roman", 0, 14));
        jTextField1.setPreferredSize(new Dimension(123, 25));
        jTextField1.setEditable(false);
        jTextField1.setBorder(BorderFactory.createEmptyBorder());
        jTextField1.setBackground(new Color(240, 240, 240));
        jTextField1.setHorizontalAlignment(JTextField.RIGHT);
        jTextField1.setText("0đ");
        roundPanel12.add(jTextField1);

        jTextField2.setFont(new Font("Times New Roman", 0, 14));
        jTextField2.setPreferredSize(new Dimension(120, 25));
        jTextField2.setHorizontalAlignment(JTextField.RIGHT);
        roundPanel13.add(jTextField2);

        jTextField3.setFont(new Font("Times New Roman", 0, 14));
        jTextField3.setPreferredSize(new Dimension(123, 25));
        jTextField3.setBackground(new Color(240, 240, 240));
        jTextField3.setBorder(BorderFactory.createEmptyBorder());
        jTextField3.setHorizontalAlignment(JTextField.RIGHT);
        jTextField3.setEditable(false);
        jTextField3.setText("0đ");
        roundPanel14.add(jTextField3);

        button3.setPreferredSize(new Dimension(135, 40));
        button3.setBorderPainted(false);
        button3.setRadius(15);
        button3.setFocusPainted(false);
        button3.setFont(new Font("Times New Roman", 0, 14));
        button3.setIcon(new ImageIcon(("img/plus.png")));
        button3.setColor(new Color(0x70E149));
        button3.setColorOver(new Color(0x5EFF00));
        button3.setColorClick(new Color(0x8AD242));
        button3.setText("Thanh Toán");
        roundPanel11.add(button3);

        button4.setPreferredSize(new Dimension(135, 40));
        button4.setBorderPainted(false);
        button4.setRadius(15);
        button4.setFocusPainted(false);
        button4.setFont(new Font("Times New Roman", 0, 14));
        button4.setIcon(new ImageIcon(("img/remove.png")));
        button4.setColor(new Color(0xFFBD3737));
        button4.setColorOver(new Color(0xFF0000));
        button4.setColorClick(new Color(0xB65858));
        button4.setText("Hủy");
        roundPanel11.add(button4);

        int height = 256 * (int) ((setProduct.size() + 1) / 3);
        roundPanel4.setLayout(new FlowLayout(FlowLayout.LEFT, 7, 5));
        roundPanel4.setPreferredSize(new Dimension(pictureScrollPane2.getWidth(), height));
        roundPanel4.setBorder(BorderFactory.createLineBorder(Color.black));
        roundPanel4.setAutoscrolls(true);
        roundPanel1.add(pictureScrollPane2);

        String name1;
        String name2;
        Set<String> checkproduct = new HashSet<String>();
        int i = 0;
        for (Product a : productlist) {
            if (!checkproduct.contains(a.getName())) {
                checkproduct.add(a.getName());

                roundPanel4.add(productPanel[i]);

                productPanel[i].setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
                productPanel[i].setPreferredSize(new Dimension(185, 250));
                productPanel[i].setBackground(new Color(0xB65858));
                productPanel[i].setColor(new Color(0xB65858));
                productPanel[i].setColorOver(new Color(25, 25, 25));
                productPanel[i].addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mousePressed(java.awt.event.MouseEvent evt) {
                        if(newJFrame1 != null) {
                            newJFrame1.dispose();
                            newJFrame1 = new NewJFrame1(SaleGUI.this, a);
                            newJFrame1.setVisible(true);
                        }
                       else{
                            newJFrame1 = new NewJFrame1(SaleGUI.this, a);
                            newJFrame1.setVisible(true);
                        }
                    }
                });
                productPanel[i].add(frameProduct[i]);

                frameProduct[i].setPreferredSize(new Dimension(181, 246));
//                frameProduct[i].setRadius(20);
                frameProduct[i].setBackground(new Color(0xB65858));

                categoryName = new CategoryBLL().searchCategories("CATEGORY_ID = '" + a.getCategoryID() + "'").get(0);

                slCategorName[i].setFont(new java.awt.Font("Dialog", 0, 15));
                slCategorName[i].setPreferredSize(new Dimension(120, 20));
                slCategorName[i].setHorizontalAlignment(JTextField.CENTER);
                slCategorName[i].setEditable(false);
                slCategorName[i].setBorder(BorderFactory.createEmptyBorder());
                slCategorName[i].setBackground(new Color(0xB65858));
                slCategorName[i].setText(categoryName.getName());
                frameProduct[i].add(slCategorName[i]);


                frameImg[i].setPreferredSize(new Dimension(181, 165));
                frameImg[i].setBackground(new Color(0xB65858));
                frameProduct[i].add(frameImg[i]);

                originalIcon[i] = new ImageIcon(a.getImage());
                slProductImg[i].setIcon(new ImageIcon(originalIcon[i].getImage().getScaledInstance(181, 165, Image.SCALE_SMOOTH)));
                frameImg[i].add(slProductImg[i]);

                name1 = "";
                name2 = "";
                if (a.getName().length() > 15) {
                    String[] listname = a.getName().split(" ");
                    name1 = name1 + listname[0];
                    for (int sl = 1; sl < listname.length - 1; sl++) {
                        if ((name1 + " " + listname[sl]).length() < 15) {
                            name1 = name1 + " " + listname[sl];
                        } else {
                            if (name2 == "") {
                                name2 = name2 + listname[sl];
                            } else name2 = name2 + " " + listname[sl];
                        }
                    }
                } else name1 = name1 + a.getName();

                slProductname1[i].setFont(new Font("Dialog", 0, 15));
                slProductname1[i].setPreferredSize(new Dimension(150, 20));
                slProductname1[i].setText(name1);
                slProductname1[i].setEditable(false);
                slProductname1[i].setBorder(BorderFactory.createEmptyBorder());
                slProductname1[i].setBackground(new Color(0xB65858));
                slProductname1[i].setHorizontalAlignment(JTextField.CENTER);
                frameProduct[i].add(slProductname1[i]);

                slProductname2[i].setFont(new Font("Dialog", 0, 15));
                slProductname2[i].setPreferredSize(new Dimension(150, 20));
                slProductname2[i].setText(name2);
                slProductname2[i].setEditable(false);
                slProductname2[i].setBorder(BorderFactory.createEmptyBorder());
                slProductname2[i].setBackground(new Color(0xB65858));
                slProductname2[i].setHorizontalAlignment(JTextField.CENTER);
                frameProduct[i].add(slProductname2[i]);
                i++;
            }
        }


          roundPanel9.setAutoscrolls(true);
//
    }


    public void addBill(ArrayList<Product> listDetailBill, ArrayList<Integer> listQuantityChoice) {
          double totalPrice = 0;
          for(int e = 0; e < listDetailBill.size();e++){
            DetailBill detailBill = new DetailBill();
            detailBill.setData(listDetailBill.get(e), listQuantityChoice.get(e), e);
            Product product = listDetailBill.get(e);

            int index = e;

            detailBill.getPaymentFrame().addMouseListener(new MouseAdapter(){
                @Override
                public void mousePressed(MouseEvent e) {
                    new NewJFrame1( SaleGUI.this, product, listQuantityChoice.get(index)).setVisible(true);
                }
            });

            detailBill.getPayment_img().addMouseListener(new MouseAdapter(){
                @Override
                public void mousePressed(MouseEvent e) {
                    if(JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa loại sản phẩm này?", "Warnning", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION){
                        listDetailBill.remove(index);
                        listQuantityChoice.remove(index);
                        roundPanel9.removeAll();
                        roundPanel9.repaint();
                        roundPanel9.revalidate();
                        addBill(listDetailBill, listQuantityChoice);
                    }
                }
            });
//
            totalPrice += product.getCost()*listQuantityChoice.get(e);
            roundPanel9.add(detailBill);
            roundPanel9.repaint();
            roundPanel9.revalidate();
          }
        jTextField1.setText(String.valueOf(totalPrice) + "đ");
    }

    public void PressSearch1(java.awt.event.ActionEvent evt) {
        roundPanel9.removeAll();
        if(jComboBox.getSelectedItem().toString().equals("Tất cả")) {
            list_Product = product_BUS.readProductOnBusiness();
            set_Grid_Layout_for_Panel_And_Load_Product(list_Product);
        }else {
            String categoryName = jComboBox.getSelectedItem().toString();
            list_Product = productBUS.readProductByCategoryName(categoryName);
            set_Grid_Layout_for_Panel_And_Load_Product(list_Product);
        }
    }


    private JComboBox<String> jComboBox;
    private JTextField search;
    private JTextField search1;
    private JTextField txtname;
    private JTextField jTextField1;
    private JTextField jTextField2;
    private JTextField jTextField3;
    private JTextField jTextField4;
    private JTextField jTextField5;
    private JTextField jTextField6;


    private ProductPanel productPanel[];
    private RoundPanel frameProduct[];

    private ImageIcon originalIcon[];
    private RoundPanel frameImg[];
    private JTextField slCategorName[];

    private JLabel slProductImg[];

    private JTextField slProductname1[];
    private JTextField slProductname2[];


    private RoundPanel frame_Img[];

    private JLabel frame_ProductImg[];

    private JTextArea jTextArea1;
    private JTextArea jTextArea2;
    private JTextArea jTextArea3;


    private RoundPanel sale;
    private RoundPanel roundPanel1;
    private RoundPanel roundPanel2;


    private JScrollPane pictureScrollPane;
    private JScrollPane pictureScrollPane2;

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;

    private JLabel jlabel1;
    private JLabel jlabel2;
    private JLabel jlabel3;
    private JLabel jlabel4;
    private JLabel jlabel5;

    private JLabel jlabel6;


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


}
