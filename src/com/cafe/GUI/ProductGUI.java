package com.cafe.GUI;

import com.cafe.BLL.CategoryBLL;
import com.cafe.BLL.ProductBLL;
import com.cafe.custom.Button;
import com.cafe.custom.RoundPanel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class ProductGUI extends JPanel {
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
        lblProductID = new JLabel();
        lblProductName = new JLabel();
        lblCategoryID = new JLabel();
        lblSize = new JLabel();
        lblCost = new JLabel();
        lblProductImg = new JLabel();
        lblProductImg = new JLabel();
        cbbSearchFilter = new JComboBox<>(columsName.subList(0, columsName.size() - 2).toArray());
        cbbCategoryID = new JComboBox<>(categoriesID.toArray());
        txtSearch = new JTextField(20);
        txtProductID = new JTextField();
        txtProductName = new JTextField();
        txtSize = new JTextField();
        txtCost = new JTextField();
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
        roundPanel1.add(new JScrollPane(productList), BorderLayout.CENTER);
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
        search.add(txtSearch);

        productList = new JTable(productBLL.getData(), columsName.subList(0, columsName.size() - 2).toArray()) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        scrollPane = new JScrollPane(productList);
        roundPanel1.add(scrollPane);

        pnlProductConfiguration.setLayout(new GridLayout(5, 2, 20, 20));
        pnlProductConfiguration.setBackground(new Color(0xFFFFFF));
        pnlProductConfiguration.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
        pnlProductConfiguration.setPreferredSize(new Dimension(635, 300));
        roundPanel2.add(pnlProductConfiguration, BorderLayout.NORTH);

        lblProductID.setText("Product ID:");
        pnlProductConfiguration.add(lblProductID);
        pnlProductConfiguration.add(txtProductID);
        lblProductName.setText("Product Name:");
        pnlProductConfiguration.add(lblProductName);
        pnlProductConfiguration.add(txtProductName);
        lblCategoryID.setText("Category ID:");
        pnlProductConfiguration.add(lblCategoryID);
        pnlProductConfiguration.add(cbbCategoryID);
        lblSize.setText("Size:");
        pnlProductConfiguration.add(lblSize);
        pnlProductConfiguration.add(txtSize);
        lblCost.setText("Cost:");
        pnlProductConfiguration.add(lblCost);
        pnlProductConfiguration.add(txtCost);
        lblProductImg.setText("Product Image:");
        btChooseImg.setText("Choose an image");
        btChooseImg.setFocusPainted(false);
        pnlProductConfiguration.add(lblProductImg);
        pnlProductConfiguration.add(btChooseImg);

        showImg.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        showImg.setPreferredSize(new Dimension(635, 250));
        showImg.setBackground(new Color(0xFFFFFF));
        roundPanel2.add(showImg, BorderLayout.CENTER);

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
        mode.add(btRef);

        ListSelectionModel cellSelectionModel = productList.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.println("xxx");
            }
        });
    }

    private final ProductBLL productBLL = new ProductBLL();
    private JTable productList;
    private RoundPanel product;
    private RoundPanel roundPanel1;
    private RoundPanel roundPanel2;
    private RoundPanel search;
    private JScrollPane scrollPane;
    private JPanel pnlProductConfiguration;
    private JPanel showImg;
    private JPanel mode;
    private JLabel lblProductID;
    private JLabel lblProductName;
    private JLabel lblCategoryID;
    private JLabel lblSize;
    private JLabel lblCost;
    private javax.swing.JLabel lblProductImg;
    private JComboBox<Object> cbbSearchFilter;
    private JComboBox cbbCategoryID;
    private JTextField txtSearch;
    private JTextField txtProductID;
    private JTextField txtProductName;
    private JTextField txtSize;
    private JTextField txtCost;
    private JButton btChooseImg;
    private Button btAdd;
    private Button btUpd;
    private Button btDel;
    private Button btRef;
}
