package com.cafe.custom;

import com.cafe.BLL.IngredientBLL;
import com.cafe.BLL.ProductBLL;
import com.cafe.BLL.SupplierBLL;
import com.cafe.DTO.*;
import com.cafe.utils.Resource;
import com.cafe.utils.VNString;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class BillDetailPanel extends RoundPanel {
    private JLabel ingredientname1;
    private int index;
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

    public BillDetailPanel() {
        System.gc();
        initComponents();
    }

    public JPanel getPaymentFrame() {
        return paymentFrame;
    }

    public void initComponents() {
        paymentFrame = new JPanel();
        payment_name = new JLabel();
        payment_size = new JLabel();
        payment_quantity = new JLabel();
        payment_price = new JLabel();
        payment_name1 = new JLabel();
        payment_size1 = new JLabel();
        payment_quantity1 = new JLabel();
        payment_price1 = new JLabel();
        frame_name = new RoundPanel();
        frame_price = new RoundPanel();
        payment_img = new Button();
        ingredientname1 = new JLabel();


        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.setPreferredSize(new Dimension(340, 75));

        paymentFrame.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        paymentFrame.setPreferredSize(new Dimension(340, 75));
        paymentFrame.setBorder(BorderFactory.createLineBorder(Color.black));
        this.add(paymentFrame);


        frame_name.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        frame_name.setPreferredSize(new Dimension(340, 30));
        paymentFrame.add(frame_name);


        frame_price.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        frame_price.setPreferredSize(new Dimension(340, 40));
        paymentFrame.add(frame_price);


        payment_name.setText("Tên sp:");
        payment_name.setPreferredSize(new Dimension(50, 30));
        payment_name.setHorizontalAlignment(JLabel.LEFT);
        payment_name.setFont(new Font("Times New Roman", Font.BOLD, 15));
        frame_name.add(payment_name);

        payment_name1.setPreferredSize(new Dimension(210, 30));
        payment_name1.setHorizontalAlignment(JLabel.CENTER);
        payment_name1.setFont(new Font("Times New Roman", Font.BOLD, 15));
        frame_name.add(payment_name1);

        payment_size.setText("Size: ");
        payment_size.setPreferredSize(new Dimension(35, 30));
        payment_size.setHorizontalAlignment(JLabel.LEFT);
        payment_size.setFont(new Font("Times New Roman", Font.BOLD, 15));
        frame_name.add(payment_size);

        payment_size1.setPreferredSize(new Dimension(35, 30));
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
        payment_price1.setHorizontalAlignment(JLabel.CENTER);
        payment_price1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        frame_price.add(payment_price1);

        payment_img.setIcon(new ImageIcon(Resource.loadImageIcon("img/icons/remove.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        payment_img.setFocusable(false);
        payment_img.setBorderPainted(false);
        payment_img.setFocusPainted(false);
        payment_img.setRadius(30);
        payment_img.setPreferredSize(new Dimension(30, 30));
        payment_img.setColor(new Color(0xFF0000));
        payment_img.setColorOver(new Color(0x000000));
        frame_price.add(payment_img);

        ingredientname1.setPreferredSize(new Dimension(180, 30));
        ingredientname1.setHorizontalAlignment(JLabel.LEFT);
        ingredientname1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
    }

    public Button getPayment_img() {
        return payment_img;
    }

    public void setPayment_img(Button payment_img) {
        this.payment_img = payment_img;
    }

    public void setData(Product data, int quantity) {
        payment_name1.setText(data.getName());
        payment_size1.setText(data.getSized());
        payment_quantity1.setText(String.valueOf(quantity));
        double total = quantity * data.getCost();
        payment_price1.setText(VNString.currency(total));
    }

    public void setBill(BillDetails billDetails) {
        this.setPreferredSize(new Dimension(340, 75));
        paymentFrame.setPreferredSize(new Dimension(340, 75));
        Product data = new ProductBLL()
            .findProductsBy(Map.of("PRODUCT_ID", billDetails.getProductID()))
            .get(0);
        payment_name1.setText(data.getName());
        payment_size1.setText(data.getSized());
        payment_quantity1.setText(String.valueOf(billDetails.getQuantity()));
        double total = billDetails.getQuantity() * data.getCost();
        payment_price1.setText(VNString.currency(total));
        frame_price.remove(payment_img);
    }

    public void setReceipt(ReceiptDetails receiptDetails) {
        this.setPreferredSize(new Dimension(340, 70));
        paymentFrame.setPreferredSize(new Dimension(340, 70));
        payment_name.setText("Tên nl:");
        payment_name1.setPreferredSize(new Dimension(270, 30));
        frame_name.remove(payment_size);
        frame_name.remove(payment_size1);
        frame_price.remove(payment_img);
        payment_quantity.setText("Số lượng:");
        payment_price.setText("Giá tiền:");
        paymentFrame.add(ingredientname1);


        frame_price.setPreferredSize(new Dimension(340, 30));
        frame_name.setPreferredSize(new Dimension(340, 20));
        payment_quantity1.setPreferredSize(new Dimension(100, 40));
        payment_price.setPreferredSize(new Dimension(55, 40));
        payment_price1.setPreferredSize(new Dimension(60, 40));
        Ingredient ingredient = new IngredientBLL()
            .findIngredientsBy(Map.of("INGREDIENT_ID", receiptDetails.getIngredientID()))
            .get(0);
        payment_name1.setText(ingredient.getName());
        payment_quantity1.setText(receiptDetails.getQuantity() + ingredient.getUnit());
        payment_price1.setText(VNString.currency(receiptDetails.getQuantity() * ingredient.getUnitPrice()));
    }

    public void setIngredient(Ingredient ingredient, int index) {
        this.setPreferredSize(new Dimension(340, 65));
        paymentFrame.setPreferredSize(new Dimension(340, 65));
        payment_name.setText("Tên nl:");
        payment_name.setPreferredSize(new Dimension(50, 25));
        payment_name1.setPreferredSize(new Dimension(270, 25));
        frame_name.remove(payment_size);
        frame_name.remove(payment_size1);
        frame_name.remove(payment_size1);
        payment_quantity.setText("Số lượng:");
        payment_price.setText("Giá tiền:");

        frame_price.setPreferredSize(new Dimension(340, 40));
        frame_name.setPreferredSize(new Dimension(340, 20));
        payment_quantity1.setPreferredSize(new Dimension(50, 40));
        payment_price.setPreferredSize(new Dimension(55, 40));
        payment_price1.setPreferredSize(new Dimension(100, 40));

        Supplier supplier = new SupplierBLL()
            .findSuppliersBy(Map.of("SUPPLIER_ID", ingredient.getSupplierID()))
            .get(0);

        payment_name1.setText(ingredient.getName());
        payment_quantity1.setText(index + ingredient.getUnit());
        payment_price1.setText(VNString.currency(index * ingredient.getUnitPrice()));
        ingredientname1.setText(supplier.getName());
    }
}
