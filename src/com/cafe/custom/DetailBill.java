package com.cafe.custom;

import com.cafe.DTO.Product;
import com.cafe.GUI.SaleGUI;

import javax.swing.*;
import java.awt.*;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class DetailBill extends RoundPanel {
    public DetailBill() {
        initComponents();
    }

    public JPanel getPaymentFrame() {
        return paymentFrame;
    }

    public void initComponents() {

        paymentFrame= new JPanel();
        payment_name= new JLabel();
        payment_size= new JLabel();
        payment_quantity= new JLabel();
        payment_price= new JLabel();
        payment_name1= new JLabel();
        payment_size1= new JLabel();
        payment_quantity1= new JLabel();
        payment_price1= new JLabel();
        frame_name = new RoundPanel();
        frame_price = new RoundPanel();
        payment_img = new Button();


        this.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        this.setPreferredSize(new Dimension(340,75));
        //if(frameProduct.get)
        // pictureScrollPane.add(paymentFrame[0]);
        paymentFrame.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        paymentFrame.setPreferredSize(new Dimension(340, 75));
        paymentFrame.setBackground(new Color(250,250,250));
        paymentFrame.setBorder(BorderFactory.createLineBorder(Color.black));
        this.add(paymentFrame);


        frame_name.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        frame_name.setBackground(new Color(250,250,250));
        frame_name.setPreferredSize(new Dimension(340, 30));
        paymentFrame.add(frame_name);


        frame_price.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        frame_price.setBackground(new Color(250,250,250));
        frame_price.setPreferredSize(new Dimension(340, 40));
        paymentFrame.add(frame_price);



        payment_name.setText("Tên sp:");
        payment_name.setPreferredSize(new Dimension(50, 30));
        payment_name.setHorizontalAlignment(JLabel.CENTER);
        payment_name.setFont(new Font("Times New Roman", Font.BOLD, 15));
        frame_name.add(payment_name);

        payment_name1.setPreferredSize(new Dimension(210, 30));
        payment_name1.setHorizontalAlignment(JLabel.CENTER);
        payment_name1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        frame_name.add(payment_name1);

        payment_size.setText("Size: ");
        payment_size.setPreferredSize(new Dimension(40, 30));
        payment_size.setHorizontalAlignment(JLabel.LEFT);
        payment_size.setFont(new Font("Times New Roman", Font.BOLD, 15));
        frame_name.add(payment_size);

        payment_size1.setPreferredSize(new Dimension(30, 30));
        payment_size1.setHorizontalAlignment(JLabel.LEFT);
        payment_size1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        frame_name.add(payment_size1);


        payment_quantity.setText("Số lượng:");
        payment_quantity.setPreferredSize(new Dimension(60, 40));
        payment_quantity.setHorizontalAlignment(JLabel.LEFT);
        payment_quantity.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        frame_price.add(payment_quantity);

        payment_quantity1.setPreferredSize(new Dimension(40, 40));
        payment_quantity1.setHorizontalAlignment(JLabel.CENTER);
        payment_quantity1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        frame_price.add(payment_quantity1);


        payment_price.setText("Tổng tiền:");
        payment_price.setPreferredSize(new Dimension(75, 40));
        payment_price.setHorizontalAlignment(JLabel.LEFT);
        payment_price.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        frame_price.add(payment_price);


        payment_price1.setPreferredSize(new Dimension(110, 40));
        payment_price1.setHorizontalAlignment(JLabel.LEFT);
        payment_price1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        frame_price.add(payment_price1);

        payment_img.setIcon(new ImageIcon(new ImageIcon("img/remove.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        payment_img.setFocusable(false);
        payment_img.setBorderPainted(false);
        payment_img.setFocusPainted(false);
        payment_img.setRadius(30);
        payment_img.setPreferredSize(new Dimension(30, 30));
        payment_img.setColor(new Color(0xFF0000));
        payment_img.setColorOver(new Color(0x000000));
        frame_price.add(payment_img);
    }

    public Button getPayment_img() {
        return payment_img;
    }

    public void setPayment_img(Button payment_img) {
        this.payment_img = payment_img;
    }

    private int index;
    public void setData(Product data, int quantity, int index){
        payment_name1.setText(data.getName());
        payment_size1.setText(data.getSized());
        payment_quantity1.setText(String.valueOf(quantity));
        int total = quantity * (int) Math.round(data.getCost());
        payment_price1.setText(String.valueOf(total)  + "đ");
        this.index = index;
    }


    private RoundPanel frameProduct;
    private RoundPanel frame_name;
    private RoundPanel frame_price;
    private JPanel paymentFrame;
    private JLabel payment_name;
    private JLabel payment_size;
    private JLabel payment_quantity;
    private JLabel payment_price;
    private JLabel payment_name1;
    private JLabel payment_size1;
    private JLabel payment_quantity1;
    private JLabel payment_price1;
    private Button payment_img;
}
