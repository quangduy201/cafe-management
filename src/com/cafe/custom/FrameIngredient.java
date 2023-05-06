package com.cafe.custom;

import com.cafe.BLL.SupplierBLL;
import com.cafe.DTO.Ingredient;
import com.cafe.DTO.Supplier;
import com.cafe.GUI.HomeGUI;
import com.cafe.GUI.IngredientGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class FrameIngredient extends JFrame{

    private String[] data = new String[6];
    private IngredientGUI ingredientGUI;

    private int index = 0;
    public FrameIngredient(IngredientGUI ingredientGUI, String[] data, int index) {
        this.index = index;
        this.ingredientGUI = ingredientGUI;
        for (int i = 0; i < data.length; i++) {
            this.data[i] = data[i];
        }
        initComponents();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
    }


    private Button minus;
    private Button plus;
    public void initComponents() {
        setSize(410,610);
        setLocationRelativeTo(null);
        setUndecorated(true);

        roundPanel =  new RoundPanel[11];
        label1 =  new JLabel[15];
        jTextField = new JTextField[8];
        roundPanel1 = new RoundPanel[11];
        minus = new Button();
        plus = new Button();
        for( int i = 0; i < roundPanel.length; i++) {
            roundPanel[i] = new RoundPanel();
            roundPanel1[i] = new RoundPanel();
        }

        for( int i = 0; i < jTextField.length; i++) {
            jTextField[i] = new JTextField();
        }

        for( int i = 0; i < label1.length; i++) {
            label1[i] = new JLabel();
        }

        confirm = new Button();
        panel = new JPanel();
        minimize = new Button();
        exit = new Button();
        label = new JLabel();


        roundPanel[0].setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
        roundPanel[0].setBackground(new Color(68, 150, 60));
        this.add(roundPanel[0]);

        roundPanel[1].setPreferredSize(new Dimension(400,30));
        roundPanel[1].setBackground(new Color(68, 150, 60));
        roundPanel[1].setLayout(new FlowLayout(FlowLayout.RIGHT,5,5));
        roundPanel[0].add(roundPanel[1]);


        roundPanel[2].setPreferredSize(new Dimension(400,565));
        roundPanel[2].setLayout(new FlowLayout(FlowLayout.CENTER,3,3));
        roundPanel[2].setBackground(new Color(240,240,240));
        roundPanel[0].add(roundPanel[2]);

        roundPanel[3].setPreferredSize(new Dimension(394,559));
        roundPanel[3].setLayout(new FlowLayout(FlowLayout.CENTER,15,10));
        roundPanel[3].setBackground(new Color(68, 150, 60));
        roundPanel[2].add(roundPanel[3]);

        minimize.setBorder(null);
        minimize.setText("-");
        minimize.setPreferredSize(new Dimension(40, 25));
        minimize.setFocusPainted(false);
        minimize.setBackground(new Color(0xF3F0F0));
        minimize.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        minimize.setRadius(15);
        roundPanel[1].add(minimize);
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                minimize();
            }
        });
        minimize.setColor(new Color(0xF3F0F0));
        minimize.setColorOver(new Color(0xC4BDBD));
        minimize.setColorClick(new Color(0x676161));

        exit.setBorder(null);
        exit.setText("X");
        exit.setPreferredSize(new Dimension(40, 25));
        exit.setFocusPainted(false);
        exit.setBackground(new Color(0xFD1111));
        exit.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        exit.setRadius(15);
        roundPanel[1].add(exit);
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                exit();
            }
        });
        exit.setColor(new Color(0xFD1111));
        exit.setColorOver(new Color(0xB04848));
        exit.setColorClick(new Color(0xE79292));

        roundPanel[4].setPreferredSize(new Dimension(370,60));
        roundPanel[4].setLayout(new FlowLayout(FlowLayout.CENTER));
        roundPanel[3].add(roundPanel[4]);


        roundPanel[5].setPreferredSize(new Dimension(370,468));
        roundPanel[5].setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
        roundPanel[3].add(roundPanel[5]);

        label.setBackground(new Color(250, 250, 250));
        label.setPreferredSize(new Dimension(300,50));
        label.setFont(new Font("Tahoma", 1, 16));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setText("NHẬP NGUYÊN LIỆU");
        roundPanel[4].add(label);

        for(int i =0; i < 6; i++) {
            roundPanel1[i].setPreferredSize(new Dimension(340,45));
            roundPanel1[i].setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
            roundPanel[5].add(roundPanel1[i]);

            label1[i].setBackground(new Color(250, 250, 250));
            label1[i].setPreferredSize(new Dimension(135,50));
            label1[i].setFont(new Font("Times New Roman", Font.PLAIN, 16));
            label1[i].setHorizontalAlignment(SwingConstants.LEFT);
            roundPanel1[i].add(label1[i]);
        }

        label1[1].setPreferredSize(new Dimension(70,50));
        label1[2].setPreferredSize(new Dimension(90,50));


        roundPanel1[4].setPreferredSize(new Dimension(340,45));
        roundPanel[5].add(roundPanel1[4]);


        label1[0].setText("INGREDIENNT_ID:");
        label1[1].setText("NAME:");
        label1[2].setText("QUANTITY:");
        label1[3].setText("UNIT:");
        label1[4].setText("UNIT_PRICE:");
        label1[5].setText("SUPPLIERNAME:");

        jTextField[0].setFont(new Font("Times New Roman", Font.BOLD, 16));
        jTextField[0].setHorizontalAlignment(SwingConstants.CENTER);
        jTextField[0].setPreferredSize(new Dimension(200, 40));
        jTextField[0].setText(this.data[0]);
        jTextField[0].setBackground(new Color(240, 240, 240));
        jTextField[0].setBorder(BorderFactory.createEmptyBorder());
        jTextField[0].setEditable(false);
        roundPanel1[0].add(jTextField[0]);

        label1[6].setFont(new Font("Times New Roman", Font.BOLD, 16));
        label1[6].setHorizontalAlignment(SwingConstants.CENTER);
        label1[6].setPreferredSize(new Dimension(270, 40));
        roundPanel1[1].add(label1[6]);

        label1[7].setFont(new Font("Times New Roman", Font.BOLD, 16));
        label1[7].setHorizontalAlignment(SwingConstants.CENTER);
        label1[7].setPreferredSize(new Dimension(190, 40));
        roundPanel1[3].add(label1[7]);

        label1[8].setFont(new Font("Times New Roman", Font.BOLD, 16));
        label1[8].setHorizontalAlignment(SwingConstants.CENTER);
        label1[8].setPreferredSize(new Dimension(190, 40));
        roundPanel1[4].add(label1[8]);

        label1[9].setFont(new Font("Times New Roman", Font.BOLD, 16));
        label1[9].setHorizontalAlignment(SwingConstants.CENTER);
        label1[9].setPreferredSize(new Dimension(190, 40));
        roundPanel1[5].add(label1[9]);

        label1[10].setFont(new Font("Times New Roman", Font.BOLD, 16));
        label1[10].setHorizontalAlignment(SwingConstants.CENTER);
        label1[10].setPreferredSize(new Dimension(190, 40));
        roundPanel1[6].add(label1[10]);

        roundPanel1[9].setPreferredSize(new Dimension(120,45));
        roundPanel1[9].setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        roundPanel[5].add(roundPanel1[9]);

        supplier = new SupplierBLL()
            .searchSuppliers("SUPPLIER_ID = '" + data[4] + "'")
            .get(0);

        label1[6].setText(this.data[1]);
        label1[7].setText(this.data[2]);
        label1[8].setText(this.data[3]);
        label1[9].setText(supplier.getName());

        confirm.setIcon(new ImageIcon("img/add-user.png"));
        confirm.setBorderPainted(false);
        confirm.setText("THÊM");
        confirm.setFocusable(false);
        confirm.setFocusPainted(false);
        confirm.setPreferredSize(new Dimension(120, 45));
        confirm.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        confirm.setRadius(45);
        confirm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
               pressConfirm();
            }
        });
        confirm.setColor(new Color(135, 255, 58));
        confirm.setColorOver(new Color(0x499D20));
        confirm.setColorClick(new Color(0x2DFF00));
        roundPanel1[9].add(confirm);

        roundPanel[6].setPreferredSize(new Dimension(220,40));
        roundPanel[6].setLayout(new FlowLayout(FlowLayout.RIGHT,10,0));
        roundPanel1[2].add(roundPanel[6]);

        minus.setBorder(null);
        minus.setText("-");
        minus.setPreferredSize(new Dimension(35, 35));
        minus.setFocusable(false);
        minus.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 16));
        minus.setRadius(50);
        minus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                minusPress();
            }
        });
        minus.setColor(new Color(240,240,240));
        minus.setColorOver(new Color(0x737070));
        minus.setColorClick(new Color(0x737070));
        roundPanel[6].add(minus);

        roundPanel[7].setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        roundPanel[7].setBackground(new Color(250,250,250));
        roundPanel[7].setPreferredSize(new Dimension(50,35));
        roundPanel[6].add(roundPanel[7]);


        label1[11].setPreferredSize(new Dimension(50, 35));
        label1[11].setText(String.valueOf(index));
        label1[11].setHorizontalAlignment(JLabel.CENTER);
        label1[11].setFont(new Font("Times New Roman", Font.BOLD, 15));
        roundPanel[7].add(label1[11]);

        plus.setBorder(null);
        plus.setText("+");
        plus.setFocusable(false);
        plus.setPreferredSize(new Dimension(35, 35));
        plus.setFont(new Font("Tahoma", Font.PLAIN, 16));
        plus.setRadius(50);
        plus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                plusPress();
            }
        });
        plus.setColor(new Color(240,240,240));
        plus.setColorOver(new Color(0x737070));
        plus.setColorClick(new Color(0x737070));
        roundPanel[6].add(plus);
    }
    public void minusPress() {
        int a = Integer.parseInt(label1[11].getText());
        if(a > 0) {
            a--;
            label1[11].setText(Integer.toString(a));
        }
    }

    public void plusPress() {
        int a = Integer.parseInt(label1[11].getText());
        if(a < 100) {
            a++;
            label1[11].setText(Integer.toString(a));
        }
    }


    private void minimize() {
        setState(HomeGUI.ICONIFIED);
    }

    private void exit() {
        this.dispose();
    }

    private Ingredient checkOrderExits(String[] data) {
        for(int i = 0; i < ingredientGUI.getReceiptDetails().size(); i++) {
            if (data[1].equals(ingredientGUI.getReceiptDetails().get(i).getName())) {
                return ingredientGUI.getReceiptDetails().get(i);
            }
        }
        return null;
    }


    private Supplier supplier;
    public void pressConfirm() {
        int index = Integer.parseInt(label1[11].getText());

        if(checkOrderExits(data)!=null){
            //Cập nhật quantity
            int location = ingredientGUI.getReceiptDetails().indexOf(checkOrderExits(data));
            ingredientGUI.getListQuantityChoice().set(location, index);
        }else{
            Ingredient ingredient = new Ingredient(data[0],data[1],0,data[2],Double.parseDouble(data[3]),data[4],false);
            ingredientGUI.getReceiptDetails().add(ingredient);
            ingredientGUI.getListQuantityChoice().add(index);
        }
        ingredientGUI.getRoundPanel().removeAll();
        ingredientGUI.addIngredient(ingredientGUI.getReceiptDetails(), ingredientGUI.getListQuantityChoice());
        this.dispose();
    }

    private Button confirm;
    private Button minimize;
    private Button exit;
    private RoundPanel roundPanel[];
    private RoundPanel roundPanel1[];
    private JPanel panel;
    private JLabel label;
    private JLabel label1[];

    private JTextField jTextField[];

    public static void main(String[] arg) {
        new FrameCustomer("0987654321").setVisible(true);
    }
}
