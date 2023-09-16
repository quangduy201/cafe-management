package com.cafe.GUI;

import com.cafe.BLL.IngredientBLL;
import com.cafe.BLL.ProductBLL;
import com.cafe.BLL.RecipeBLL;
import com.cafe.DTO.Ingredient;
import com.cafe.DTO.Product;
import com.cafe.DTO.Recipe;
import com.cafe.custom.Button;
import com.cafe.custom.RoundPanel;
import com.cafe.utils.Resource;
import com.cafe.utils.VNString;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.List;
import java.util.*;
import java.util.function.BiConsumer;

public class ProductDetailsGUI extends JFrame {
    private static IngredientBLL ingredientBLL = new IngredientBLL();
    public static List<Ingredient> ingredientList = ingredientBLL.getIngredientList();
    private RecipeBLL recipeBLL = new RecipeBLL();
    private int countProduct = 0;
    private Vector<String> sizeList;
    private Vector<String> costList;
    private RoundPanel frame;
    private Button minimize;
    private Button exit;
    private Button plus;
    private Button minus;
    private Button confirm;
    private JLabel text_product;
    private RoundPanel[] roundPanel;
    private RoundPanel frameImg;
    private ImageIcon originalIcon;
    private JLabel slProductImg;
    private JLabel[] label;
    private SaleGUI saleGUI;
    private JComboBox<String> comboBoxProductSize;
    private int quantity;
    private Product newProduct;
    private Product getProduct;

    public ProductDetailsGUI(SaleGUI saleGUI, Product product, int quantity) {
        System.gc();
        this.newProduct = product;
        this.saleGUI = saleGUI;
        this.quantity = quantity;
        initComponents();
        sizeList = new Vector<>();
        costList = new Vector<>();
        List<Product> products = new ProductBLL().findProductsBy(Map.of("NAME", product.getName()));
        for (Product product1 : products) {
            sizeList.add(product1.getSized());
            costList.add(VNString.currency(product1.getCost()));
        }
        comboBoxProductSize.setModel(new DefaultComboBoxModel<>(sizeList));
        label[4].setText(costList.get(0));
        label[5].setText("1");
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
    }

    public void initComponents() {
        setSize(650, 540);
        setLocationRelativeTo(null);
        setUndecorated(true);

        frame = new RoundPanel();
        exit = new Button();
        minimize = new Button();
        text_product = new JLabel();
        roundPanel = new RoundPanel[12];
        label = new JLabel[12];
        frameImg = new RoundPanel();
        originalIcon = new ImageIcon();
        confirm = new Button();
        slProductImg = new JLabel();
        minus = new Button();
        plus = new Button();
        comboBoxProductSize = new JComboBox<>();

        for (int i = 0; i < roundPanel.length; i++) {
            roundPanel[i] = new RoundPanel();
            label[i] = new JLabel();
        }

        frame.setLayout(new FlowLayout());
        frame.setBackground(new Color(68, 150, 60));
        this.add(frame);

        roundPanel[0].setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        roundPanel[0].setBackground(new Color(68, 150, 60));
        roundPanel[0].setPreferredSize(new Dimension(650, 30));
        frame.add(roundPanel[0]);

        roundPanel[1].setLayout(new FlowLayout(FlowLayout.CENTER));
        roundPanel[1].setBackground(new Color(68, 150, 60));
        roundPanel[1].setPreferredSize(new Dimension(650, 50));
        //roundPanel[1].setBackground(new Color(145, 0, 0));
        frame.add(roundPanel[1]);

        roundPanel[2].setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        roundPanel[2].setPreferredSize(new Dimension(310, 210));
        roundPanel[2].setBackground(new Color(240, 240, 240));
        frame.add(roundPanel[2]);

        roundPanel[3].setLayout(new FlowLayout(FlowLayout.CENTER));
        roundPanel[3].setBackground(new Color(68, 150, 60));
        roundPanel[3].setPreferredSize(new Dimension(650, 170));
//        roundPanel[3].setBackground(new Color(145, 0, 0));
        frame.add(roundPanel[3]);

        roundPanel[4].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roundPanel[4].setBackground(new Color(68, 150, 60));
        roundPanel[4].setPreferredSize(new Dimension(650, 50));
        frame.add(roundPanel[4]);

        BiConsumer<Button, List<Object>> configButton = (button, properties) -> {
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
        configButton.accept(minimize, List.of("-", 45, 25, 15, new Color(0xF3F0F0), new Color(0xC4BDBD), new Color(0x676161), (Runnable) this::minimize));
        roundPanel[0].add(minimize);
        configButton.accept(exit, List.of("X", 45, 25, 15, new Color(0xFD1111), new Color(0xB04848), new Color(0xE79292), (Runnable) this::exit));
        roundPanel[0].add(exit);

        text_product.setText("CHI TIẾT MUA HÀNG");
        //text_product.setBackground(new Color(240,240,240));
        text_product.setForeground(new Color(240, 240, 240));
        text_product.setFont(new Font("Times New Roman", Font.BOLD, 25));
        text_product.setPreferredSize(new Dimension(260, 40));
        roundPanel[1].add(text_product);

        frameImg.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        frameImg.setPreferredSize(new Dimension(300, 200));
        frameImg.setBackground(new Color(240, 240, 240));
        roundPanel[2].add(frameImg);

        originalIcon = Resource.loadImageIconIn(newProduct.getImage());
        slProductImg.setIcon(new ImageIcon(originalIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH)));
        frameImg.add(slProductImg);

        roundPanel[5].setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        roundPanel[5].setBackground(new Color(68, 150, 60));
        roundPanel[5].setPreferredSize(new Dimension(650, 50));
        roundPanel[3].add(roundPanel[5]);

        roundPanel[6].setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        roundPanel[6].setBackground(new Color(68, 150, 60));
        roundPanel[6].setPreferredSize(new Dimension(650, 50));
        roundPanel[3].add(roundPanel[6]);

        roundPanel[7].setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        roundPanel[7].setBackground(new Color(68, 150, 60));
        roundPanel[7].setPreferredSize(new Dimension(650, 50));
        roundPanel[3].add(roundPanel[7]);

        label[0].setText("Tên sản phẩm\t:");
        label[0].setPreferredSize(new Dimension(130, 35));
        label[0].setFont(new Font("Times New Roman", Font.BOLD, 17));
        label[0].setForeground(Color.BLACK);
        roundPanel[5].add(label[0]);

        label[1].setText("Giá\t\t\t:");
        label[1].setPreferredSize(new Dimension(130, 35));
        label[1].setFont(new Font("Times New Roman", Font.BOLD, 17));
        label[1].setForeground(Color.BLACK);
        roundPanel[6].add(label[1]);

        label[2].setText("Số lượng\t\t:");
        label[2].setPreferredSize(new Dimension(130, 35));
        label[2].setFont(new Font("Times New Roman", Font.BOLD, 17));
        label[2].setForeground(Color.BLACK);
        roundPanel[7].add(label[2]);

        roundPanel[8].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roundPanel[8].setBackground(new Color(240, 240, 240));
        roundPanel[8].setPreferredSize(new Dimension(400, 35));
        roundPanel[5].add(roundPanel[8]);

        label[3].setText(newProduct.getName());
        label[3].setPreferredSize(new Dimension(400, 35));
        label[3].setHorizontalAlignment(JLabel.CENTER);
        label[3].setFont(new Font("Times New Roman", Font.BOLD, 15));
        label[3].setForeground(Color.BLACK);
        roundPanel[8].add(label[3]);

        roundPanel[9].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roundPanel[9].setBackground(new Color(240, 240, 240));
        roundPanel[9].setPreferredSize(new Dimension(150, 35));
        roundPanel[6].add(roundPanel[9]);

        label[4].setPreferredSize(new Dimension(150, 35));
        label[4].setHorizontalAlignment(JLabel.CENTER);
        label[4].setFont(new Font("Times New Roman", Font.BOLD, 15));
        label[4].setForeground(Color.BLACK);
        roundPanel[9].add(label[4]);

        configButton.accept(minus, List.of("-", 35, 35, 50, new Color(240, 240, 240), new Color(0x737070), new Color(0x737070), (Runnable) this::minusPress));
        minus.setFont(new Font("Tahoma", Font.PLAIN, 16));
        roundPanel[7].add(minus);

        roundPanel[10].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roundPanel[10].setBackground(new Color(240, 240, 240));
        roundPanel[10].setPreferredSize(new Dimension(50, 35));
        roundPanel[7].add(roundPanel[10]);

        label[5].setPreferredSize(new Dimension(50, 35));
        label[5].setHorizontalAlignment(JLabel.CENTER);
        label[5].setFont(new Font("Times New Roman", Font.BOLD, 15));
        label[5].setForeground(Color.BLACK);
        roundPanel[10].add(label[5]);

        configButton.accept(plus, List.of("+", 35, 35, 50, new Color(240, 240, 240), new Color(0x737070), new Color(0x737070), (Runnable) this::plusPress));
        plus.setFont(new Font("Tahoma", Font.PLAIN, 16));
        roundPanel[7].add(plus);

        label[6].setText("Size:");
        label[6].setPreferredSize(new Dimension(50, 35));
        label[6].setFont(new Font("Times New Roman", Font.BOLD, 17));
        label[6].setForeground(Color.BLACK);
        roundPanel[7].add(label[6]);

        comboBoxProductSize.setFont(new Font("Dialog", Font.PLAIN, 12));
        comboBoxProductSize.setMaximumRowCount(10);//so luong
        comboBoxProductSize.setPreferredSize(new Dimension(100, 35));
        comboBoxProductSize.setBorder(null);
        comboBoxProductSize.setFocusable(false);
        comboBoxProductSize.setBackground(new Color(240, 240, 240));
        comboBoxProductSize.setForeground(Color.BLACK);
        comboBoxProductSize.addActionListener(e -> selectSize());
        roundPanel[7].add(comboBoxProductSize);

        configButton.accept(confirm, List.of("Xác nhận", 150, 50, 45, new Color(240, 240, 240), new Color(0x756969), new Color(0xA65B5B), (Runnable) this::pressConfirm));
        confirm.setIcon(Resource.loadImageIconIn("img/icons/add-to-cart.png"));
        confirm.setFont(new Font("Tahoma", Font.PLAIN, 16));
        roundPanel[4].add(confirm);
    }

    public void minusPress() {
        int quantity = Integer.parseInt(label[5].getText());
        if (quantity > 1) {
            quantity--;
            label[5].setText(Integer.toString(quantity));
        }
    }

    public void plusPress() {
        int quantity = Integer.parseInt(label[5].getText());
        if (quantity < 100) {
            quantity++;
            label[5].setText(Integer.toString(quantity));
        }
    }

    private void minimize() {
        setState(HomeGUI.ICONIFIED);
    }

    private void exit() {
        this.dispose();
    }

    public void selectSize() {
        for (int i = 0; i < sizeList.size(); i++) {
            if (comboBoxProductSize.getSelectedItem().toString().equals(sizeList.get(i))) {
                // this.price = (String) costList.get(i);
                label[4].setText(costList.get(i));
                break;
            }
        }
    }

    private Product checkOrderExits(Product product) {
        for (int i = 0; i < saleGUI.getListDetailBill().size(); i++) {
            if (product.getName().equals(saleGUI.getListDetailBill().get(i).getName()) &&
                product.getSized().equals(saleGUI.getListDetailBill().get(i).getSized())) {
                return saleGUI.getListDetailBill().get(i);
            }
        }
        return null;
    }

    public void pressConfirm() {
        getProduct = new ProductBLL()
            .searchProducts("NAME = '" + newProduct.getName() + "'", "SIZED = '" + comboBoxProductSize.getSelectedItem().toString() + "'")
            .get(0);
        int quantity = Integer.parseInt(label[5].getText());
        System.out.println(getProduct.toString());

        if (checkOrderExits(getProduct) != null) {
            int location = saleGUI.getListDetailBill().indexOf(checkOrderExits(getProduct));
            updateIngredient(getProduct.getProductID(), saleGUI.getListQuantityChoice().get(location));
        }

        if (availableQuantity(getProduct.getProductID(), quantity)) {
            if (checkOrderExits(getProduct) != null) {
                System.out.println("updating");
                //Cập nhật quantity
                int location = saleGUI.getListDetailBill().indexOf(checkOrderExits(getProduct));
                saleGUI.getListQuantityChoice().set(location, quantity);
            } else {
                System.out.println("add new");
                saleGUI.getListDetailBill().add(getProduct);
                saleGUI.getListQuantityChoice().add(quantity);
            }

            saleGUI.getRoundPanel9().removeAll();
            saleGUI.addProductToBill(saleGUI.getListDetailBill(), saleGUI.getListQuantityChoice());
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Chỉ còn " + countProduct + " sản phẩm", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean availableQuantity(String product_ID, int quantity) {
        List<Recipe> recipeList = recipeBLL.findRecipes("PRODUCT_ID", product_ID);
        List<String> ingredients = new ArrayList<>();
        List<Double> quantityIngredients = new ArrayList<>();
        List<Double> massIngredients = new ArrayList<>();
        List<Integer> list = new ArrayList<>();

        for (Recipe recipe : recipeList) {
            ingredients.add(recipe.getIngredientID());
            massIngredients.add(recipe.getMass());
        }

        for (Ingredient ingredient : ingredientList) {
            if (ingredients.contains(ingredient.getIngredientID())) {
                quantityIngredients.add(ingredient.getQuantity());
            }
        }

        for (int i = 0; i < quantityIngredients.size(); i++) {
            if (quantityIngredients.get(i) < massIngredients.get(i)) {
                break;
            } else {
                list.add((int) (quantityIngredients.get(i) / massIngredients.get(i)));
            }
        }
        Collections.sort(list);
        countProduct = list.get(0);
        if (countProduct > quantity) {
            for (int i = 0; i < quantityIngredients.size(); i++) {
                quantityIngredients.set(i, quantityIngredients.get(i) - massIngredients.get(i) * quantity);
            }
            int i = 0;
            for (Ingredient ingredient : ingredientList) {
                if (ingredients.contains(ingredient.getIngredientID())) {
                    ingredient.setQuantity(quantityIngredients.get(i));
                    i++;
                }
            }
            System.out.println(countProduct);
            return true;
        }
        return false;
    }

    public void updateIngredient(String product_ID, int quantity) {
        List<Recipe> recipeList = recipeBLL.findRecipes("PRODUCT_ID", product_ID);
        for (Recipe recipe : recipeList) {
            for (Ingredient ingredient : ingredientList) {
                if (Objects.equals(recipe.getIngredientID(), ingredient.getIngredientID())) {
                    ingredient.setQuantity(ingredient.getQuantity() + recipe.getMass() * quantity);
                }
            }
        }
    }
}
