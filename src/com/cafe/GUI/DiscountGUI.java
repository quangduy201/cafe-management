package com.cafe.GUI;

import com.cafe.BLL.DiscountBLL;
import com.cafe.BLL.ProductBLL;
import com.cafe.custom.Button;
import com.cafe.custom.DataTable;
import com.cafe.custom.RoundPanel;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class DiscountGUI extends JPanel {
    private DiscountBLL discountBLL = new DiscountBLL();
    private ProductBLL productBLL = new ProductBLL();
    private int decentralizationMode;
    private JPanel discount;
    private RoundPanel[] roundPanel;
    private JLabel[] label;
    private JTextField[] jTextFields;
    private DataTable[] dataTable;
    private JDateChooser jDateChooser1;
    private JDateChooser jDateChooser2;
    private JTextField DateTextField1;
    private JTextField DateTextField2;
    private JComboBox<String> jComboBox;
    private RoundPanel mode;
    private Button btAdd;
    private Button btUpd;
    private Button btDel;
    private Button btRef;
    private JTextField search1;
    private JTextField search2;
    private Button btSearch1;
    private Button btSearch2;
    private JComboBox<String> jComboBox1;
    private JComboBox<String> jComboBox2;
    private JLabel labelimg;
    private JScrollPane scrollPane1;
    private JScrollPane scrollPane2;
    public DiscountGUI(int decentralizationMode) {
        this.decentralizationMode = decentralizationMode;
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(70, 67, 67));
        initComponents();
    }

    public void initComponents() {
        jComboBox = new JComboBox<>();
        roundPanel = new RoundPanel[15];
        label = new JLabel[10];
        jTextFields = new JTextField[10];
        dataTable = new DataTable[2];
        discount = new JPanel();
        labelimg = new JLabel();
        jDateChooser1 = new JDateChooser();
        jDateChooser2 = new JDateChooser();
        DateTextField1 = ((JTextField) jDateChooser1.getDateEditor().getUiComponent());
        DateTextField2 = ((JTextField) jDateChooser2.getDateEditor().getUiComponent());
        search1 = new JTextField();
        search2 = new JTextField();
        btSearch1 = new Button();
        btSearch2 = new Button();
        jComboBox1 = new JComboBox<>();
        jComboBox2 = new JComboBox<>();

        mode = new RoundPanel();
        btAdd = new Button();
        btUpd = new Button();
        btDel = new Button();
        btRef = new Button();

//        dataTable[1] = new DataTable();
//        dataTable[2] = new DataTable();

        for (int i = 0; i < roundPanel.length; i++) {
            roundPanel[i] = new RoundPanel();
        }

        for (int i = 0; i < label.length; i++) {
            label[i] = new JLabel();
            jTextFields[i] = new JTextField();
        }

        discount.setLayout(new BorderLayout(10, 10));
        discount.setBackground(new Color(70, 67, 67));
        this.add(discount, BorderLayout.CENTER);

        roundPanel[0].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roundPanel[0].setBackground(new Color(255, 255, 255));
        roundPanel[0].setPreferredSize(new Dimension(380, 670));
        roundPanel[0].setAutoscrolls(true);
        discount.add(roundPanel[0], BorderLayout.WEST);

        roundPanel[1].setLayout(new BorderLayout(0, 10));
        roundPanel[1].setBackground(new Color(70, 67, 67));
        roundPanel[1].setPreferredSize(new Dimension(600, 670));
        roundPanel[1].setAutoscrolls(true);
        discount.add(roundPanel[1], BorderLayout.CENTER);

        roundPanel[2].setBackground(new Color(255, 255, 255));
        roundPanel[2].setPreferredSize(new Dimension(600, 310));
        roundPanel[2].setAutoscrolls(true);
        roundPanel[1].add(roundPanel[2], BorderLayout.NORTH);

        roundPanel[3].setBackground(new Color(255, 255, 255));
        roundPanel[3].setPreferredSize(new Dimension(600, 350));
        roundPanel[3].setAutoscrolls(true);
        roundPanel[1].add(roundPanel[3], BorderLayout.CENTER);

        roundPanel[4].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        roundPanel[4].setBackground(new Color(255, 255, 255));
        roundPanel[4].setPreferredSize(new Dimension(380, 200));
        roundPanel[4].setAutoscrolls(true);
        roundPanel[0].add(roundPanel[4]);

        roundPanel[5].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15));
        roundPanel[5].setBackground(new Color(255, 255, 255));
        roundPanel[5].setPreferredSize(new Dimension(380, 340));
        roundPanel[5].setAutoscrolls(true);
        roundPanel[0].add(roundPanel[5]);

        roundPanel[6].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roundPanel[6].setBackground(new Color(255, 255, 255));
        roundPanel[6].setPreferredSize(new Dimension(380, 130));
        roundPanel[6].setAutoscrolls(true);
        roundPanel[0].add(roundPanel[6]);

        roundPanel[7].setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        roundPanel[7].setBackground(new Color(255, 255, 255));
        roundPanel[7].setPreferredSize(new Dimension(600, 40));
        roundPanel[7].setAutoscrolls(true);
        roundPanel[2].add(roundPanel[7]);

        roundPanel[8].setLayout(new BorderLayout(0, 0));
//        roundPanel[8].setBackground(new Color(182, 24, 24));
        roundPanel[8].setPreferredSize(new Dimension(590, 255));
        roundPanel[8].setAutoscrolls(true);
        roundPanel[8].add(new JScrollPane(dataTable[0]), BorderLayout.CENTER);
        roundPanel[2].add(roundPanel[8]);

        roundPanel[9].setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        // roundPanel[9].setBackground(new Color(182, 24, 24));
        roundPanel[9].setPreferredSize(new Dimension(600, 40));
        roundPanel[9].setAutoscrolls(true);
        roundPanel[3].add(roundPanel[9]);

        roundPanel[10].setLayout(new BorderLayout(0, 0));
//        roundPanel[10].setBackground(new Color(182, 24, 24));
        roundPanel[10].setPreferredSize(new Dimension(590, 295));
        roundPanel[10].add(new JScrollPane(dataTable[1]), BorderLayout.CENTER);
        roundPanel[10].setAutoscrolls(true);
        roundPanel[3].add(roundPanel[10]);

        labelimg.setIcon(new ImageIcon(new ImageIcon("img/black-friday.png").getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
        labelimg.setFocusable(false);
        labelimg.setPreferredSize(new Dimension(150, 150));
        roundPanel[4].add(labelimg);

//        for (int i = 0 ; i < 6; i++) {
//            label[i].setFont(new Font("Times New Roman", Font.BOLD, 15));
//            label[i].setHorizontalAlignment(JLabel.LEFT);
//            label[i].setPreferredSize(new Dimension(130, 40));
//            label[i].setAutoscrolls(true);
//            roundPanel[5].add(label[i]);
//
//            jTextFields[i].setFont(new Font("Times New Roman", Font.BOLD, 15));
//            jTextFields[i].setPreferredSize(new Dimension(200, 30));
//            jTextFields[i].setAutoscrolls(true);
//            roundPanel[5].add(jTextFields[i]);
//        }

        label[0].setFont(new Font("Times New Roman", Font.BOLD, 15));
        label[0].setHorizontalAlignment(JLabel.LEFT);
        label[0].setPreferredSize(new Dimension(130, 40));
        label[0].setAutoscrolls(true);
        roundPanel[5].add(label[0]);

        jTextFields[0].setFont(new Font("Times New Roman", Font.BOLD, 15));
        jTextFields[0].setHorizontalAlignment(JLabel.CENTER);
        jTextFields[0].setBorder(BorderFactory.createEmptyBorder());
        jTextFields[0].setEditable(false);
        jTextFields[0].setFocusable(false);
        jTextFields[0].setPreferredSize(new Dimension(200, 30));
        jTextFields[0].setAutoscrolls(true);
        roundPanel[5].add(jTextFields[0]);

        label[1].setFont(new Font("Times New Roman", Font.BOLD, 15));
        label[1].setHorizontalAlignment(JLabel.LEFT);
        label[1].setPreferredSize(new Dimension(130, 40));
        label[1].setAutoscrolls(true);
        roundPanel[5].add(label[1]);

        jTextFields[1].setFont(new Font("Times New Roman", Font.BOLD, 15));
        jTextFields[1].setHorizontalAlignment(JLabel.LEFT);
        jTextFields[1].setBorder(BorderFactory.createLineBorder(Color.black));
        jTextFields[1].setPreferredSize(new Dimension(200, 30));
        jTextFields[1].setAutoscrolls(true);
        roundPanel[5].add(jTextFields[1]);

        label[2].setFont(new Font("Times New Roman", Font.BOLD, 15));
        label[2].setHorizontalAlignment(JLabel.LEFT);
        label[2].setPreferredSize(new Dimension(130, 40));
        label[2].setAutoscrolls(true);
        roundPanel[5].add(label[2]);

        DateTextField1.setFont(new Font("Tahoma", Font.BOLD, 14));
        jDateChooser1.setPreferredSize(new Dimension(200, 30));
        jDateChooser1.addPropertyChangeListener("date", evt -> changeCalender());
        DateTextField1.addActionListener(e -> {
            String dateString = DateTextField1.getText();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            format.setLenient(false);
            try {
                Date date = format.parse(dateString);
                jDateChooser1.setDate(date);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Invalid date", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        roundPanel[5].add(jDateChooser1);

        label[3].setFont(new Font("Times New Roman", Font.BOLD, 15));
        label[3].setHorizontalAlignment(JLabel.LEFT);
        label[3].setPreferredSize(new Dimension(130, 40));
        label[3].setAutoscrolls(true);
        roundPanel[5].add(label[3]);

        DateTextField2.setFont(new Font("Tahoma", Font.BOLD, 14));
        jDateChooser2.setPreferredSize(new Dimension(200, 30));
        jDateChooser2.addPropertyChangeListener("date", evt -> changeCalender());
        DateTextField2.addActionListener(e -> {
            String dateString = DateTextField2.getText();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            format.setLenient(false);
            try {
                Date date = format.parse(dateString);
                jDateChooser2.setDate(date);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Invalid date", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        roundPanel[5].add(jDateChooser2);

        label[4].setFont(new Font("Times New Roman", Font.BOLD, 15));
        label[4].setHorizontalAlignment(JLabel.LEFT);
        label[4].setPreferredSize(new Dimension(130, 40));
        label[4].setAutoscrolls(true);
        roundPanel[5].add(label[4]);

        Vector<String> comboBoxItems = new Vector<>();
        comboBoxItems.add("Ngừng áp dụng");
        comboBoxItems.add("Đang áp dụng");
        jComboBox.setModel(new DefaultComboBoxModel<>(comboBoxItems));

        jComboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
        jComboBox.setMaximumRowCount(2);//so luong
        jComboBox.setPreferredSize(new Dimension(200, 30));
        jComboBox.setBorder(null);
        jComboBox.setFocusable(false);
        roundPanel[5].add(jComboBox);

        label[0].setText("Mã khuyến mãi:");
        label[1].setText("Giá trị (%):");
        label[2].setText("Ngày bắt đầu:");
        label[3].setText("Ngày kết thúc:");
        label[4].setText("Trạng thái:");

        mode.setLayout(new GridLayout(2, 2, 40, 20));
        mode.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mode.setBackground(new Color(0xFFFFFF));
        mode.setPreferredSize(new Dimension(380, 130));
        roundPanel[6].add(mode);

        btAdd.setBackground(new Color(35, 166, 97));
        btAdd.setBorder(null);
        btAdd.setIcon(new ImageIcon("img/plus.png"));
        btAdd.setText("  Add");
        btAdd.setColor(new Color(240, 240, 240));
        btAdd.setColorClick(new Color(141, 222, 175));
        btAdd.setColorOver(new Color(35, 166, 97));
        btAdd.setFocusPainted(false);
        btAdd.setRadius(20);
        btAdd.setEnabled(true);
        btAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (btAdd.isEnabled()) {
                    addDiscount();
                }
            }
        });
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
        btUpd.setEnabled(true);
        btUpd.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (btUpd.isEnabled()) {
                    updateDiscount();
                }
            }
        });
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
        btDel.setEnabled(true);
        btDel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (btDel.isEnabled()) {
                    deleteDiscount();
                }
            }
        });
        mode.add(btDel);

        btRef.setBackground(new Color(35, 166, 97));
        btRef.setBorder(null);
        btRef.setIcon(new ImageIcon("img/refresh.png"));
        btRef.setText("  Refresh");
        btRef.setColor(new Color(240, 240, 240));
        btRef.setColorClick(new Color(141, 222, 175));
        btRef.setColorOver(new Color(35, 166, 97));
        btRef.setFocusPainted(false);
        btRef.setRadius(20);
        btRef.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                refreshForm();
            }
        });
        mode.add(btRef);


        roundPanel[11].setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
        roundPanel[11].setPreferredSize(new Dimension(440, 35));
        roundPanel[11].setAutoscrolls(true);
        roundPanel[7].add(roundPanel[11]);


        search1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        search1.setPreferredSize(new Dimension(300, 35));
        search1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        search1.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                loadDataTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                loadDataTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                loadDataTable();
            }
        });
        roundPanel[11].add(search1);

        btSearch1.setBackground(new Color(240, 240, 240));
        btSearch1.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btSearch1.setPreferredSize(new Dimension(35, 35));
        btSearch1.setIcon(new ImageIcon("img/search.png"));
        btSearch1.setFocusPainted(false);
        roundPanel[11].add(btSearch1);

        Vector<String> comboBoxItem = new Vector<>();
        comboBoxItem.add("Tất cả");
        comboBoxItem.add("Đang áp dụng");
        comboBoxItem.add("Ngừng áp dụng");
        jComboBox1.setModel(new DefaultComboBoxModel<>(comboBoxItem));

        jComboBox1.setFont(new Font("Dialog", Font.PLAIN, 15));
        jComboBox1.setMaximumRowCount(5);//so luong
        jComboBox1.setPreferredSize(new Dimension(150, 35));
        jComboBox1.setBorder(null);
        jComboBox1.setFocusable(false);
        jComboBox1.addActionListener(evt -> pressComboBox1());
        roundPanel[7].add(jComboBox1);

        roundPanel[12].setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
        roundPanel[12].setPreferredSize(new Dimension(440, 35));
        roundPanel[12].setAutoscrolls(true);
        roundPanel[9].add(roundPanel[12]);


        search2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        search2.setPreferredSize(new Dimension(300, 35));
        search2.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        search2.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                loadDataTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                loadDataTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                loadDataTable();
            }
        });
        roundPanel[12].add(search2);

        btSearch2.setBackground(new Color(240, 240, 240));
        btSearch2.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btSearch2.setPreferredSize(new Dimension(35, 35));
        btSearch2.setIcon(new ImageIcon("img/search.png"));
        btSearch2.setFocusPainted(false);
        roundPanel[12].add(btSearch2);

        Vector<String> ComboBox = new Vector<>();
        jComboBox2.setModel(new DefaultComboBoxModel<>(ComboBox));

        jComboBox2.setFont(new Font("Dialog", Font.PLAIN, 15));
        jComboBox2.setMaximumRowCount(10);//so luong
        jComboBox2.setPreferredSize(new Dimension(150, 35));
        jComboBox2.setBorder(null);
        jComboBox2.setFocusable(false);
        jComboBox2.addActionListener(evt -> pressComboBox2());
        roundPanel[9].add(jComboBox2);

        scrollPane1 = new JScrollPane();
        scrollPane2 = new JScrollPane();

        List<String> columnNames = discountBLL.getDiscountDAL().getColumnNames();
        dataTable[0] = new DataTable(discountBLL.getData(), columnNames.subList(0, columnNames.size() - 1).toArray(), e -> fillForm1());
        scrollPane1 = new JScrollPane(dataTable[0]);
        roundPanel[8].add(scrollPane1);

        columnNames = productBLL.getProductDAL().getColumnNames();
        dataTable[1] = new DataTable(productBLL.getData(), columnNames.subList(0, columnNames.size() - 2).toArray(), e -> fillForm2());
        scrollPane2 = new JScrollPane(dataTable[1]);
        roundPanel[10].add(scrollPane2);
    }

    public void fillForm1() {
        DefaultTableModel model = (DefaultTableModel) dataTable[0].getModel();
        Object[] rowData = model.getDataVector().elementAt(dataTable[0].getSelectedRow()).toArray();
        String[] data = new String[rowData.length];
        for (int i = 0; i < rowData.length; i++) {
            data[i] = rowData[i].toString();
        }
        String[] discount = String.join(" | ", data).split(" \\| ");
        jTextFields[0].setText(discount[0]);
        jTextFields[1].setText(discount[1]);
        DateTextField1.setText(discount[2]);
        DateTextField2.setText(discount[3]);
        String dateString1 = DateTextField1.getText();
        String dateString2 = DateTextField2.getText();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);
        try {
            Date date = format.parse(dateString1);
            jDateChooser1.setDate(date);
            date = format.parse(dateString2);
            jDateChooser2.setDate(date);
        } catch (ParseException ignored) {

        }
        jComboBox.setSelectedIndex(Integer.parseInt(discount[4]));
    }

    public void fillForm2() {

    }

    public void changeCalender() {

    }

    public void addDiscount() {

    }

    public void deleteDiscount() {

    }

    public void updateDiscount() {

    }

    public void refreshForm() {

    }

    public void loadDataTable() {

    }

    public void pressComboBox1() {

    }

    public void pressComboBox2() {

    }
}
