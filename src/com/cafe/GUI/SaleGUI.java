package com.cafe.GUI;

import com.cafe.BLL.CategoryBLL;
import com.cafe.BLL.DecentralizationBLL;
import com.cafe.BLL.StaffBLL;
import com.cafe.DAL.CategoryDAL;
import com.cafe.DTO.Account;
import com.cafe.DTO.Category;
import com.cafe.DTO.Decentralization;
import com.cafe.custom.Button;
import com.cafe.custom.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static javax.swing.ScrollPaneConstants.UPPER_RIGHT_CORNER;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

public class SaleGUI extends JPanel {

    private CategoryBLL categoryBLL;
    private ArrayList<String> NameList;
    public SaleGUI() {
        categoryBLL = new CategoryBLL();
        NameList = new ArrayList<>();
        for (Category category : categoryBLL.getCategoryList()) {
            NameList.add(category.getName());
        }
        setLayout(new BorderLayout(10,10));
        setBackground(new Color(70, 67, 67));
        initComponents();
        Vector comboBoxItems = new Vector();
        comboBoxItems.add("Tất cả");
        for(String Name: NameList)
            comboBoxItems.add(Name);
        jComboBox.setModel(new DefaultComboBoxModel(comboBoxItems));
    }

    public void initComponents() {
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


        pictureScrollPane = new JScrollPane(
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        jlabel1 = new JLabel();
        jlabel2 = new JLabel();
        jlabel3 = new JLabel();
        jlabel4 = new JLabel();

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

        sale = new RoundPanel();
        jComboBox = new JComboBox<>();

        sale.setLayout(new FlowLayout(FlowLayout.CENTER,10,0));
        sale.setBackground(new Color(70, 67, 67));
        this.add(sale,BorderLayout.CENTER);

        roundPanel1.setPreferredSize(new Dimension(615,670));
        roundPanel1.setAutoscrolls(true);
        sale.add(roundPanel1);

        roundPanel2.setLayout(new FlowLayout(FlowLayout.CENTER,5,10));
        roundPanel2.setPreferredSize(new Dimension(370,670));
        roundPanel2.setAutoscrolls(true);
        sale.add(roundPanel2);

        roundPanel3.setLayout(new BorderLayout(90,0));
        roundPanel3.setPreferredSize(new Dimension(615,50));
        roundPanel3.setAutoscrolls(true);
        roundPanel1.add(roundPanel3);

        roundPanel4.setPreferredSize(new Dimension(600,600));
        roundPanel4.setBorder(BorderFactory.createLineBorder(Color.black));
        roundPanel4.setAutoscrolls(true);
        roundPanel1.add(roundPanel4);

        roundPanel6.setLayout(new FlowLayout(FlowLayout.LEFT,10,5));
        roundPanel6.setPreferredSize(new Dimension(135,35));
        roundPanel6.setAutoscrolls(true);
        roundPanel3.add(roundPanel6,BorderLayout.WEST);

        jComboBox.setFont(new java.awt.Font("Dialog", 0, 12));
        jComboBox.setMaximumRowCount(10);//so luong
        jComboBox.setPreferredSize(new Dimension(120,35));
        jComboBox.setBorder(null);
        jComboBox.setFocusable(false);
        roundPanel6.add(jComboBox);

        roundPanel5.setLayout(new FlowLayout(FlowLayout.LEFT,23,5));
        roundPanel5.setPreferredSize(new Dimension(300,35));
        roundPanel5.setAutoscrolls(true);
        roundPanel3.add(roundPanel5,BorderLayout.CENTER);

        search.setFont(new java.awt.Font("Times New Roman", 0, 14));
        search.setPreferredSize(new Dimension(300,35));
        search.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        roundPanel5.add(search);
//        jComboBox.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jComboBoxActionPerformed(evt);
//            }
//        });

        button1.setBackground(new java.awt.Color(240, 240, 240));
        button1.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        button1.setPreferredSize(new Dimension(35,35));
        button1.setIcon(new ImageIcon(("img/search.png"))); // NOI18N
        button1.setFocusPainted(false);
        roundPanel5.add(button1);

        roundPanel7.setLayout(new BorderLayout(65,0));
        roundPanel7.setPreferredSize(new Dimension(350,35));
        roundPanel2.add(roundPanel7);

        roundPanel8.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        roundPanel8.setPreferredSize(new Dimension(350,35));
        roundPanel2.add(roundPanel8);


        pictureScrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        pictureScrollPane.setPreferredSize(new Dimension(18,400));
//        roundPanel9.add(pictureScrollPane,BorderLayout.CENTER);
        pictureScrollPane.setViewportView(roundPanel9);

        roundPanel9.setLayout(new GridLayout(1,1,0,0));
        roundPanel9.setBackground(new Color(25,25,25));
        roundPanel9.setBorder(BorderFactory.createLineBorder(Color.black));
        roundPanel9.setAutoscrolls(true);
        roundPanel9.setPreferredSize(new Dimension(350,400));
        roundPanel2.add(roundPanel9);


        roundPanel10.setLayout(new BoxLayout(roundPanel10,BoxLayout.Y_AXIS));
        roundPanel10.setPreferredSize(new Dimension(350,100));
        roundPanel2.add(roundPanel10);

        roundPanel11.setLayout(new FlowLayout(FlowLayout.CENTER,30,0));
        roundPanel11.setPreferredSize(new Dimension(350,40));
        roundPanel2.add(roundPanel11);

        roundPanel12.setLayout(new FlowLayout(FlowLayout.RIGHT));
        roundPanel10.add(roundPanel12);
        roundPanel13.setLayout(new FlowLayout(FlowLayout.RIGHT));
        roundPanel10.add(roundPanel13);
        roundPanel14.setLayout(new FlowLayout(FlowLayout.RIGHT));
        roundPanel10.add(roundPanel14);

        search1.setFont(new java.awt.Font("Times New Roman", 0, 14));
        search1.setPreferredSize(new Dimension(250,35));
        search1.setBorder(BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        roundPanel7.add(search1,BorderLayout.WEST);

        button2.setBackground(new java.awt.Color(240, 240, 240));
        button2.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        button2.setPreferredSize(new Dimension(35,35));
        button2.setIcon(new ImageIcon(("img/search.png")));
        button2.setFocusPainted(false);
        roundPanel7.add(button2,BorderLayout.CENTER);


        jlabel1.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jlabel1.setPreferredSize(new Dimension(110,35));
        jlabel1.setText("Tên Khách Hàng: ");
        roundPanel8.add(jlabel1);

        txtname.setFont(new java.awt.Font("Times New Roman", 0, 14));
        txtname.setPreferredSize(new Dimension(250,35));
        txtname.setBorder(BorderFactory.createEmptyBorder());
        txtname.setEditable(false);
        txtname.setBackground(new Color(240,240,240));
        roundPanel8.add(txtname);

        jlabel2.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jlabel2.setText("Tổng tiền: ");
        roundPanel12.add(jlabel2);

        jlabel3.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jlabel3.setText("Tiền nhận: ");
        roundPanel13.add(jlabel3);

        jlabel4.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jlabel4.setText("Tiền thừa: ");
        roundPanel14.add(jlabel4);

        jTextField1.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jTextField1.setPreferredSize(new Dimension(123,25));
        jTextField1.setEditable(false);
        jTextField1.setBorder(BorderFactory.createEmptyBorder());
        jTextField1.setBackground(new Color(240,240,240));
        jTextField1.setHorizontalAlignment(JTextField.RIGHT);
        jTextField1.setText("0đ");
        roundPanel12.add(jTextField1);

        jTextField2.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jTextField2.setPreferredSize(new Dimension(120,25));
        jTextField2.setHorizontalAlignment(JTextField.RIGHT);
        roundPanel13.add(jTextField2);

        jTextField3.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jTextField3.setPreferredSize(new Dimension(123,25));
        jTextField3.setBackground(new Color(240,240,240));
        jTextField3.setBorder(BorderFactory.createEmptyBorder());
        jTextField3.setHorizontalAlignment(JTextField.RIGHT);
        jTextField3.setEditable(false);
        jTextField3.setText("0đ");
        roundPanel14.add(jTextField3);

        button3.setPreferredSize(new Dimension(135,40));
        button3.setBorderPainted(false);
        button3.setRadius(15);
        button3.setFocusPainted(false);
        button3.setFont(new java.awt.Font("Times New Roman", 0, 14));
        button3.setIcon(new ImageIcon(("img/plus.png")));
        button3.setColor(new Color(0x70E149));
        button3.setColorOver(new Color(0x5EFF00));
        button3.setColorClick(new Color(0x8AD242));
        button3.setText("Thanh Toán");
        roundPanel11.add(button3);

        button4.setPreferredSize(new Dimension(135,40));
        button4.setBorderPainted(false);
        button4.setRadius(15);
        button4.setFocusPainted(false);
        button4.setFont(new java.awt.Font("Times New Roman", 0, 14));
        button4.setIcon(new ImageIcon(("img/remove.png")));
        button4.setColor(new Color(0xFFBD3737));
        button4.setColorOver(new Color(0xFF0000));
        button4.setColorClick(new Color(0xB65858));
        button4.setText("Hủy");
        roundPanel11.add(button4);

        roundPanel15.setPreferredSize(new Dimension(250,600));
        roundPanel15.setBackground(new Color(0xB65858));
        roundPanel9.add(roundPanel15);
    }

    private JComboBox<String> jComboBox;
    private JTextField search;
    private JTextField search1;
    private JTextField txtname;
    private JTextField jTextField1;
    private JTextField jTextField2;
    private JTextField jTextField3;


    private RoundPanel sale;
    private RoundPanel roundPanel1;
    private RoundPanel roundPanel2;


    private JScrollPane pictureScrollPane;

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;

    private JLabel jlabel1;
    private JLabel jlabel2;
    private JLabel jlabel3;
    private JLabel jlabel4;
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
