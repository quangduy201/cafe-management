package com.cafe.custom;

import com.cafe.BLL.SupplierBLL;
import com.cafe.DTO.Ingredient;
import com.cafe.DTO.Supplier;
import com.cafe.GUI.HomeGUI;
import com.cafe.GUI.IngredientGUI;
import com.cafe.main.CafeManagement;
import com.cafe.utils.Resource;
import com.cafe.utils.VNString;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.function.BiConsumer;

public class FrameIngredient extends JFrame {
    private String[] data = new String[6];
    private int index;
    private Button minus;
    private Button plus;
    private Supplier supplier;
    private Button confirm;
    private Button minimize;
    private Button exit;
    private RoundPanel[] roundPanel;
    private RoundPanel[] roundPanel1;
    private JLabel label;
    private JLabel[] label1;
    private JTextField[] jTextField;

    public FrameIngredient(String[] data, int index) {
        System.gc();
        this.index = index;
        System.arraycopy(data, 0, this.data, 0, data.length);
        initComponents();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
    }

    public void initComponents() {
        setSize(410, 610);
        setLocationRelativeTo(null);
        setUndecorated(true);

        roundPanel = new RoundPanel[11];
        label1 = new JLabel[15];
        jTextField = new JTextField[8];
        roundPanel1 = new RoundPanel[11];
        minus = new Button();
        plus = new Button();
        for (int i = 0; i < roundPanel.length; i++) {
            roundPanel[i] = new RoundPanel();
            roundPanel1[i] = new RoundPanel();
        }

        for (int i = 0; i < jTextField.length; i++) {
            jTextField[i] = new JTextField();
        }

        for (int i = 0; i < label1.length; i++) {
            label1[i] = new JLabel();
        }

        confirm = new Button();
        minimize = new Button();
        exit = new Button();
        label = new JLabel();

        roundPanel[0].setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        roundPanel[0].setBackground(new Color(68, 150, 60));
        this.add(roundPanel[0]);

        roundPanel[1].setPreferredSize(new Dimension(400, 30));
        roundPanel[1].setBackground(new Color(68, 150, 60));
        roundPanel[1].setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        roundPanel[0].add(roundPanel[1]);

        roundPanel[2].setPreferredSize(new Dimension(400, 565));
        roundPanel[2].setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
        roundPanel[0].add(roundPanel[2]);

        roundPanel[3].setPreferredSize(new Dimension(394, 559));
        roundPanel[3].setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        roundPanel[3].setBackground(new Color(68, 150, 60));
        roundPanel[2].add(roundPanel[3]);

        BiConsumer<Button, java.util.List<Object>> configButton = (button, properties) -> {
            button.setBorder(null);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            button.setBorderColor(Color.BLACK);
            button.setForeground(Color.BLACK);
            button.setText((String) properties.get(0));
            button.setPreferredSize(new Dimension((Integer) properties.get(1), (Integer) properties.get(2)));
            button.setRadius((Integer) properties.get(3));
            button.setColor((Color) properties.get(4));
            button.setColorOver((Color) properties.get(5));
            button.setColorClick((Color) properties.get(6));
            button.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    ((Runnable) properties.get(7)).run();
                }
            });
        };
        configButton.accept(minimize, java.util.List.of("-", 40, 25, 15, new Color(0xF3F0F0), new Color(0xC4BDBD), new Color(0x676161), (Runnable) this::minimize));
        roundPanel[1].add(minimize);
        configButton.accept(exit, java.util.List.of("X", 40, 25, 15, new Color(0xFD1111), new Color(0xB04848), new Color(0xE79292), (Runnable) this::exit));
        roundPanel[1].add(exit);

        roundPanel[4].setPreferredSize(new Dimension(370, 60));
        roundPanel[4].setLayout(new FlowLayout(FlowLayout.CENTER));
        roundPanel[3].add(roundPanel[4]);

        roundPanel[5].setPreferredSize(new Dimension(370, 468));
        roundPanel[5].setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        roundPanel[3].add(roundPanel[5]);

        label.setPreferredSize(new Dimension(300, 50));
        label.setFont(new Font("Tahoma", Font.BOLD, 16));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setText("NHẬP NGUYÊN LIỆU");
        roundPanel[4].add(label);

        for (int i = 0; i < 6; i++) {
            roundPanel1[i].setPreferredSize(new Dimension(340, 45));
            roundPanel1[i].setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
            roundPanel[5].add(roundPanel1[i]);

            label1[i].setPreferredSize(new Dimension(135, 50));
            label1[i].setFont(new Font("Times New Roman", Font.PLAIN, 16));
            label1[i].setHorizontalAlignment(SwingConstants.LEFT);
            roundPanel1[i].add(label1[i]);
        }

        label1[1].setPreferredSize(new Dimension(70, 50));
        label1[2].setPreferredSize(new Dimension(90, 50));

        roundPanel1[4].setPreferredSize(new Dimension(340, 45));
        roundPanel[5].add(roundPanel1[4]);

        label1[0].setText("Mã nguyên liệu:");
        label1[1].setText("Tên nguyên liệu:");
        label1[2].setText("Số lượng:");
        label1[3].setText("Đơn vị:");
        label1[4].setText("Đơn giá:");
        label1[5].setText("Tên nhà cung cấp:");

        jTextField[0].setFont(new Font("Times New Roman", Font.BOLD, 16));
        jTextField[0].setHorizontalAlignment(SwingConstants.CENTER);
        jTextField[0].setPreferredSize(new Dimension(200, 40));
        jTextField[0].setText(this.data[0]);
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

        roundPanel1[9].setPreferredSize(new Dimension(120, 45));
        roundPanel1[9].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roundPanel[5].add(roundPanel1[9]);

        supplier = new SupplierBLL()
            .searchSuppliers("SUPPLIER_ID = '" + data[4] + "'")
            .get(0);

        label1[6].setText(this.data[1]);
        label1[7].setText(this.data[2]);
        label1[8].setText(VNString.currency(Double.parseDouble(this.data[3])));
        label1[9].setText(supplier.getName());

        configButton.accept(confirm, java.util.List.of("THÊM", 120, 45, 45, new Color(135, 255, 58), new Color(0x499D20), new Color(0x2DFF00), (Runnable) this::pressConfirm));
        confirm.setIcon(Resource.loadImageIconIn("img/icons/add-user.png"));
        roundPanel1[9].add(confirm);

        roundPanel[6].setPreferredSize(new Dimension(220, 40));
        roundPanel[6].setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        roundPanel1[2].add(roundPanel[6]);

        configButton.accept(minus, java.util.List.of("-", 35, 35, 50, new Color(240, 240, 240), new Color(0x737070), new Color(0x737070), (Runnable) this::minusPress));
        minus.setFont(new Font("Tahoma", Font.BOLD, 16));
        roundPanel[6].add(minus);

        roundPanel[7].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roundPanel[7].setPreferredSize(new Dimension(50, 35));
        roundPanel[6].add(roundPanel[7]);

        label1[11].setPreferredSize(new Dimension(50, 35));
        label1[11].setText(String.valueOf(index));
        label1[11].setHorizontalAlignment(JLabel.CENTER);
        label1[11].setFont(new Font("Times New Roman", Font.BOLD, 15));
        roundPanel[7].add(label1[11]);

        configButton.accept(plus, java.util.List.of("+", 35, 35, 50, new Color(240, 240, 240), new Color(0x737070), new Color(0x737070), (Runnable) this::plusPress));
        plus.setFont(new Font("Tahoma", Font.BOLD, 16));
        roundPanel[6].add(plus);
    }

    public void minusPress() {
        int quantity = Integer.parseInt(label1[11].getText());
        if (quantity > 1) {
            quantity--;
            label1[11].setText(Integer.toString(quantity));
        }
    }

    public void plusPress() {
        int quantity = Integer.parseInt(label1[11].getText());
        if (quantity < 100) {
            quantity++;
            label1[11].setText(Integer.toString(quantity));
        }
    }

    private void minimize() {
        setState(HomeGUI.ICONIFIED);
    }

    private void exit() {
        this.dispose();
    }

    public void pressConfirm() {
        IngredientGUI ingredientGUI = (IngredientGUI) CafeManagement.homeGUI.getCurrentGUI();
        int quantity = Integer.parseInt(label1[11].getText());
        int index = ingredientGUI.findReceiptDetailsIndex(data[1]);
        if (index == -1) {
            Ingredient ingredient = new Ingredient(data[0], data[1], 0, data[2], Double.parseDouble(data[3]), data[4], false);
            ingredientGUI.getReceiptDetails().add(new Pair<>(ingredient, quantity));
        } else {
            Ingredient ingredient = ingredientGUI.getReceiptDetails().get(index).getKey();
            ingredientGUI.getReceiptDetails().set(index, new Pair<>(ingredient, quantity));
        }
        ingredientGUI.getRoundPanel().removeAll();
        ingredientGUI.addIngredient(ingredientGUI.getReceiptDetails());
        this.dispose();
    }
}
