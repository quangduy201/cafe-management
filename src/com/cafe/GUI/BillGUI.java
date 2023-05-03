package com.cafe.GUI;

import com.cafe.BLL.*;
import com.cafe.DTO.*;
import com.cafe.custom.BillDetailPanel;
import com.cafe.custom.Button;
import com.cafe.custom.DataTable;
import com.cafe.custom.RoundPanel;
import com.cafe.utils.Day;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BillGUI extends JPanel {
    private RoundPanel[] roundPanel;
    private JLabel[] label;
    private JScrollPane[] jScrollPane;
    private DataTable dataTable;
    private Button[] button;
    private BillBLL billBLL = new BillBLL();
    private List<String> billColumnNames = billBLL.getBillDAL().getColumnNames();
    private List<Receipt> receiptList = new ArrayList<>();
    private List<Bill> billList = new ArrayList<>();
    private BillDetailsBLL billDetailsBLL;
    private ReceiptDetailsBLL receiptDetailsBLL = new ReceiptDetailsBLL();
    private Customer customer;
    private JScrollPane scrollPane;
    private ReceiptBLL receiptBLL = new ReceiptBLL();
    private List<String> receiptColumnNames = receiptBLL.getReceiptDAL().getColumnNames();
    private ArrayList<BillDetails> listDetailBill = new ArrayList<>();
    private ArrayList<ReceiptDetails> listReceiptDetails = new ArrayList<>();
    private com.toedter.calendar.JDateChooser jdateChooser1;
    private JTextField textField;
    private com.toedter.calendar.JDateChooser jdateChooser2;
    private JTextField textField2;
    private boolean checklist = true;

    public BillGUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(70, 67, 67));
        initComponents();
    }

    public void initComponents() {
        roundPanel = new RoundPanel[25];
        label = new JLabel[25];
        button = new Button[4];
        jScrollPane = new JScrollPane[2];
        button[0] = new Button();
        button[1] = new Button();
        button[2] = new Button();
        button[3] = new Button();
        jdateChooser1 = new com.toedter.calendar.JDateChooser();
        jdateChooser2 = new com.toedter.calendar.JDateChooser();
        textField = ((JTextField) jdateChooser1.getDateEditor().getUiComponent());
        textField2 = ((JTextField) jdateChooser2.getDateEditor().getUiComponent());
        for (int i = 0; i < roundPanel.length; i++) {
            roundPanel[i] = new RoundPanel();
            label[i] = new JLabel();
        }

        roundPanel[0].setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        roundPanel[0].setBackground(new Color(70, 67, 67));
        this.add(roundPanel[0], BorderLayout.CENTER);

        jScrollPane[0] = new JScrollPane(roundPanel[16]);
        jScrollPane[1] = new JScrollPane();

        roundPanel[1].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        roundPanel[1].setPreferredSize(new Dimension(560, 670));
        roundPanel[1].setBackground(new Color(70, 67, 67));
        roundPanel[1].setAutoscrolls(true);
        roundPanel[0].add(roundPanel[1]);

        roundPanel[2].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
//        roundPanel[2].setBackground(new Color(145, 0, 0));
        roundPanel[2].setPreferredSize(new Dimension(410, 670));
        roundPanel[2].setAutoscrolls(true);
        roundPanel[0].add(roundPanel[2]);

        roundPanel[3].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        roundPanel[3].setPreferredSize(new Dimension(560, 40));
        roundPanel[3].setBackground(new Color(70, 67, 67));
        roundPanel[3].setAutoscrolls(true);
        roundPanel[1].add(roundPanel[3]);

        roundPanel[4].setPreferredSize(new Dimension(560, 530));
        roundPanel[4].setAutoscrolls(true);
        roundPanel[1].add(roundPanel[4]);

        roundPanel[5].setLayout(new BorderLayout(240, 0));
        roundPanel[5].setPreferredSize(new Dimension(560, 40));
        roundPanel[5].setBackground(new Color(70, 67, 67));
        roundPanel[5].setAutoscrolls(true);
        roundPanel[1].add(roundPanel[5]);

        roundPanel[6].setPreferredSize(new Dimension(410, 40));
        roundPanel[6].setAutoscrolls(true);
        roundPanel[2].add(roundPanel[6]);

        roundPanel[7].setPreferredSize(new Dimension(410, 30));
        //  roundPanel[7].setLayout(new );
        roundPanel[7].setAutoscrolls(true);
        roundPanel[2].add(roundPanel[7]);

        roundPanel[8].setPreferredSize(new Dimension(410, 30));
        roundPanel[8].setAutoscrolls(true);
        roundPanel[2].add(roundPanel[8]);

//        roundPanel[9].setPreferredSize(new Dimension(410, 30));
//        roundPanel[9].setAutoscrolls(true);
//        roundPanel[2].add(roundPanel[9]);
//
//        roundPanel[10].setPreferredSize(new Dimension(410, 30));
//        roundPanel[10].setAutoscrolls(true);
//        roundPanel[2].add(roundPanel[10]);

        roundPanel[11].setPreferredSize(new Dimension(410, 400));
        roundPanel[11].setAutoscrolls(true);
        roundPanel[2].add(roundPanel[11]);

        roundPanel[12].setPreferredSize(new Dimension(410, 25));
        roundPanel[12].setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        roundPanel[12].setAutoscrolls(true);
        roundPanel[2].add(roundPanel[12]);

        roundPanel[13].setPreferredSize(new Dimension(410, 25));
        roundPanel[13].setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        roundPanel[13].setAutoscrolls(true);
        roundPanel[2].add(roundPanel[13]);

        roundPanel[14].setPreferredSize(new Dimension(410, 25));
        roundPanel[14].setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        roundPanel[14].setAutoscrolls(true);
        roundPanel[2].add(roundPanel[14]);

        label[0].setFont(new Font("Times New Roman", Font.BOLD, 20));
        label[0].setHorizontalAlignment(JLabel.CENTER);
        label[0].setText("Danh Sách Phiếu Bán Hàng");
        label[0].setPreferredSize(new Dimension(400, 50));
        label[0].setBackground(new Color(255, 0, 0));
        label[0].setForeground(Color.white);
        label[0].setAutoscrolls(true);
        roundPanel[3].add(label[0]);

        roundPanel[15].setLayout(new BorderLayout(5, 0));
        roundPanel[15].setBackground(new Color(70, 67, 67));
        roundPanel[15].setPreferredSize(new Dimension(170, 40));
        roundPanel[15].setAutoscrolls(true);
        roundPanel[5].add(roundPanel[15], BorderLayout.WEST);

        button[0].setPreferredSize(new Dimension(80, 40));
        button[0].setBorderPainted(false);
        button[0].setRadius(15);
        button[0].setFocusPainted(false);
        button[0].setFont(new Font("Times New Roman", Font.PLAIN, 14));
        button[0].setColor(new Color(0x70E149));
        button[0].setColorOver(new Color(0x5EFF00));
        button[0].setColorClick(new Color(0x8AD242));
        button[0].setBorderColor(new Color(70, 67, 67));
        button[0].setText("Bán");
        button[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                try {
                    pressSale();
                } catch (Exception ignored) {

                }
            }
        });
        roundPanel[15].add(button[0], BorderLayout.WEST);

        button[1].setPreferredSize(new Dimension(80, 40));
        button[1].setBorderPainted(false);
        button[1].setRadius(15);
        button[1].setFocusPainted(false);
        button[1].setFont(new Font("Times New Roman", Font.PLAIN, 14));
        button[1].setColor(new Color(0x70E149));
        button[1].setColorOver(new Color(0x5EFF00));
        button[1].setColorClick(new Color(0x8AD242));
        button[1].setBorderColor(new Color(70, 67, 67));
        button[1].setText("Nhập");
        button[1].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                try {
                    pressImport();
                } catch (Exception ignored) {

                }
            }
        });
        roundPanel[15].add(button[1], BorderLayout.EAST);


        button[2].setPreferredSize(new Dimension(160, 40));
        button[2].setBorderPainted(false);
        button[2].setRadius(15);
        button[2].setFocusPainted(false);
        button[2].setFont(new Font("Times New Roman", Font.PLAIN, 14));
        button[2].setColor(new Color(240, 240, 240));
        button[2].setColorOver(new Color(0x5EFF00));
        button[2].setColorClick(new Color(0x8AD242));
        button[2].setBorderColor(new Color(70, 67, 67));
        button[2].setIcon(new ImageIcon("img/folder.png"));
        button[2].setText("Xuất Excel");
        button[2].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                try {
                    pressExcel();
                } catch (Exception ignored) {

                }
            }
        });
        roundPanel[5].add(button[2], BorderLayout.EAST);

        label[1].setFont(new Font("Times New Roman", Font.BOLD, 30));
        label[1].setHorizontalAlignment(JLabel.CENTER);
        label[1].setText("HÓA ĐƠN");
        label[1].setPreferredSize(new Dimension(400, 40));
        label[1].setAutoscrolls(true);
        roundPanel[6].add(label[1]);

        label[2].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[2].setHorizontalAlignment(JLabel.LEFT);
        label[2].setText("Mã hóa đơn:");
        label[2].setPreferredSize(new Dimension(110, 25));
        label[2].setAutoscrolls(true);
        roundPanel[7].add(label[2]);

        label[3].setFont(new Font("Times New Roman", Font.BOLD, 12));
        label[3].setHorizontalAlignment(JLabel.LEFT);
        label[3].setBackground(new Color(0x232364));
        label[3].setPreferredSize(new Dimension(140, 30));
        label[3].setAutoscrolls(true);
        roundPanel[7].add(label[3]);


        label[8].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[8].setHorizontalAlignment(JLabel.LEFT);
        label[8].setText("Ngày:");
        label[8].setPreferredSize(new Dimension(40, 25));
        label[8].setAutoscrolls(true);
        roundPanel[7].add(label[8]);

        label[9].setFont(new Font("Times New Roman", Font.BOLD, 12));
        label[9].setHorizontalAlignment(JLabel.LEFT);
        label[9].setPreferredSize(new Dimension(90, 30));
        label[9].setAutoscrolls(true);
        roundPanel[7].add(label[9]);

        label[4].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[4].setHorizontalAlignment(JLabel.LEFT);
        label[4].setText("Tên khách hàng:");
        label[4].setPreferredSize(new Dimension(105, 25));
        label[4].setAutoscrolls(true);
        roundPanel[8].add(label[4]);

        label[5].setFont(new Font("Times New Roman", Font.BOLD, 11));
        label[5].setHorizontalAlignment(JLabel.LEFT);
        label[5].setPreferredSize(new Dimension(145, 30));
        label[5].setAutoscrolls(true);
        roundPanel[8].add(label[5]);

        label[6].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[6].setHorizontalAlignment(JLabel.LEFT);
        label[6].setText("Mã nhân viên:");
        label[6].setPreferredSize(new Dimension(90, 25));
        label[6].setAutoscrolls(true);
        roundPanel[8].add(label[6]);

        label[7].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[7].setHorizontalAlignment(JLabel.LEFT);
        label[7].setPreferredSize(new Dimension(40, 30));
        label[7].setAutoscrolls(true);
        roundPanel[8].add(label[7]);


        jScrollPane[0].setPreferredSize(new Dimension(400, 380));
        jScrollPane[0].setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane[0].setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane[0].setBorder(BorderFactory.createEmptyBorder());
        roundPanel[16].setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        roundPanel[16].setBackground(new Color(240, 240, 240));
        roundPanel[16].setPreferredSize(new Dimension(jScrollPane[0].getWidth(), 390));
        roundPanel[11].add(jScrollPane[0]);


        label[10].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[10].setHorizontalAlignment(JLabel.LEFT);
        label[10].setText("Tổng tiền:");
        label[10].setPreferredSize(new Dimension(100, 25));
        label[10].setAutoscrolls(true);
        roundPanel[12].add(label[10]);

        label[11].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[11].setHorizontalAlignment(JLabel.RIGHT);
        label[11].setPreferredSize(new Dimension(100, 25));
        label[11].setAutoscrolls(true);
        roundPanel[12].add(label[11]);

        label[12].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[12].setHorizontalAlignment(JLabel.LEFT);
        label[12].setText("Tiền nhận:");
        label[12].setPreferredSize(new Dimension(100, 25));
        label[12].setAutoscrolls(true);
        roundPanel[13].add(label[12]);

        label[13].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[13].setHorizontalAlignment(JLabel.RIGHT);
        label[13].setPreferredSize(new Dimension(100, 25));
        label[13].setAutoscrolls(true);
        roundPanel[13].add(label[13]);

        label[14].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[14].setHorizontalAlignment(JLabel.LEFT);
        label[14].setText("Tiền thối:");
        label[14].setPreferredSize(new Dimension(100, 25));
        label[14].setAutoscrolls(true);
        roundPanel[14].add(label[14]);

        label[15].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[15].setHorizontalAlignment(JLabel.RIGHT);
        label[15].setPreferredSize(new Dimension(100, 25));
        label[15].setAutoscrolls(true);
        roundPanel[14].add(label[15]);

        button[3].setPreferredSize(new Dimension(150, 40));
        button[3].setBorderPainted(false);
        button[3].setRadius(15);
        button[3].setFocusPainted(false);
        button[3].setFont(new Font("Times New Roman", Font.PLAIN, 14));
        button[3].setColor(new Color(240, 240, 240));
        button[3].setColorOver(new Color(0x5EFF00));
        button[3].setColorClick(new Color(0x8AD242));
        button[3].setBorderColor(new Color(70, 67, 67));
        button[3].setIcon(new ImageIcon("img/folder.png"));
        button[3].setText("Xuất Excel");
        button[3].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                try {
                    pressExcel();
                } catch (Exception ignored) {

                }
            }
        });
        roundPanel[2].add(button[3]);

        roundPanel[17].setLayout(new FlowLayout(FlowLayout.LEFT));
        // roundPanel[17].setBackground(new Color(70, 67, 67));
        roundPanel[17].setPreferredSize(new Dimension(540, 50));
        roundPanel[17].setAutoscrolls(true);
        roundPanel[4].add(roundPanel[17]);

        roundPanel[18].setLayout(new BorderLayout(5, 0));
        roundPanel[18].setBackground(new Color(70, 67, 67));
        roundPanel[18].setPreferredSize(new Dimension(540, 465));
        roundPanel[18].setAutoscrolls(true);
        roundPanel[4].add(roundPanel[18]);

        label[16].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[16].setText("Từ ngày:");
        label[16].setHorizontalAlignment(JLabel.LEFT);
        label[16].setPreferredSize(new Dimension(70, 50));
        label[16].setAutoscrolls(true);
        roundPanel[17].add(label[16]);

        roundPanel[19].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15));
        roundPanel[19].setPreferredSize(new Dimension(190, 50));
//        roundPanel[19].setBackground(new Color(250,250,250));
        roundPanel[19].setAutoscrolls(true);
        roundPanel[17].add(roundPanel[19]);

        label[17].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[17].setText("Đến ngày:");
        label[17].setHorizontalAlignment(JLabel.LEFT);
        label[17].setPreferredSize(new Dimension(70, 50));
        label[17].setAutoscrolls(true);
        roundPanel[17].add(label[17]);

        roundPanel[20].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 15));
//        roundPanel[20].setBackground(new Color(250, 250, 250));
        roundPanel[20].setPreferredSize(new Dimension(190, 50));
        roundPanel[20].setAutoscrolls(true);
        roundPanel[17].add(roundPanel[20]);

        textField.setFont(new Font("Tahoma", Font.BOLD, 12));
        textField.setHorizontalAlignment(JTextField.CENTER);
        jdateChooser1.setPreferredSize(new Dimension(150, 20));
        jdateChooser1.setDateFormatString("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 1, 1);
        jdateChooser1.setDate(calendar.getTime());
        jdateChooser1.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                changeCalender();
            }
        });
        textField.addActionListener(e -> {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            format.setLenient(false);
            try {
                Date date = format.parse(textField.getText());
                jdateChooser1.setDate(date);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Invalid date", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        roundPanel[19].add(jdateChooser1);

        textField2.setFont(new Font("Tahoma", Font.BOLD, 12));
        textField2.setHorizontalAlignment(JTextField.CENTER);
        jdateChooser2.setPreferredSize(new Dimension(150, 20));
        jdateChooser2.setDateFormatString("yyyy-MM-dd");
        jdateChooser2.setDate(new Date());
        jdateChooser2.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                changeCalender();
            }
        });
        textField2.addActionListener(e -> {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            format.setLenient(false);
            try {
                Date date = format.parse(textField2.getText());
                jdateChooser2.setDate(date);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Invalid date", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        roundPanel[20].add(jdateChooser2);


        dataTable = new DataTable(billBLL.getData(), billColumnNames.subList(0, billColumnNames.size() - 1).toArray(), e -> fillForm());
        scrollPane = new JScrollPane(dataTable);
        roundPanel[18].add(scrollPane);

//        Calendar cal = Calendar.getInstance(); // Lấy đối tượng Calendar hiện tại
//        cal.set(Calendar.YEAR, 2023);
//        cal.set(Calendar.MONTH, -5);
//        cal.set(Calendar.DAY_OF_MONTH, 32);
//        jdateChooser1.setCalendar(cal); // Đặt ngày cho JCalendar
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String start = sdf.format(jdateChooser1.getDate());
//        System.out.println(start);
        billDetailsBLL = new BillDetailsBLL();
//
//        receiptDetailsBLL = new ReceiptDetailsBLL();
//        for (ReceiptDetails receiptDetails : receiptDetailsBLL.getReceiptDetailsList()) {
//            listReceipDetails.add(receiptDetails);
//        }


    }

    public void fillForm() {
        listDetailBill.clear();
        roundPanel[16].removeAll();
        roundPanel[16].repaint();
        roundPanel[16].revalidate();
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        Object[] rowData = model.getDataVector().elementAt(dataTable.getSelectedRow()).toArray();
        String[] data = new String[rowData.length];
        for (int i = 0; i < rowData.length; i++) {
            data[i] = rowData[i].toString();
        }
        String[] bill = String.join(" | ", data).split(" \\| ");
        label[3].setText(bill[0]);
        label[9].setText(bill[3]);
        customer = new CustomerBLL()
            .searchCustomers("CUSTOMER_ID = '" + bill[1] + "'")
            .get(0);
        label[5].setText(customer.getName());
        label[7].setText(bill[2]);
        for (BillDetails billDetails : billDetailsBLL.getBillDetailsList()) {
            if (billDetails.getBillID().equals(bill[0])) {
                listDetailBill.add(billDetails);
                BillDetailPanel billDetailPanel = new BillDetailPanel();
                billDetailPanel.setbill(billDetails);
                roundPanel[16].add(billDetailPanel);
            }
        }
        if (listDetailBill.size() >= 5) {
            int tall = 75 * this.listDetailBill.size();
            roundPanel[16].setPreferredSize(new Dimension(jScrollPane[0].getWidth(), tall));
        } else {
            roundPanel[16].setPreferredSize(new Dimension(jScrollPane[0].getWidth(), 390));
        }
        label[11].setText(bill[4]);
        label[13].setText(bill[5]);
        label[15].setText(bill[6]);
    }

    public void fillForm1() {
        listDetailBill.clear();
        roundPanel[16].removeAll();
        roundPanel[16].repaint();
        roundPanel[16].revalidate();
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        Object[] rowData = model.getDataVector().elementAt(dataTable.getSelectedRow()).toArray();
        String[] data = new String[rowData.length];
        for (int i = 0; i < rowData.length; i++) {
            data[i] = rowData[i].toString();
        }
        String[] receipt = String.join(" | ", data).split(" \\| ");
        label[3].setText(receipt[0]);
        label[9].setText(receipt[2]);
        label[5].setText(receipt[1]);
        for (ReceiptDetails receiptDetails : receiptDetailsBLL.getReceiptDetailsList()) {
            if (receiptDetails.getReceiptID().equals(receipt[0])) {
                listReceiptDetails.add(receiptDetails);
                BillDetailPanel receiptDetailPanel = new BillDetailPanel();
                receiptDetailPanel.setreceipt(receiptDetails);
                roundPanel[16].add(receiptDetailPanel);
            }
        }
        if (listDetailBill.size() >= 5) {
            int tall = 75 * this.listDetailBill.size();
            roundPanel[16].setPreferredSize(new Dimension(jScrollPane[0].getWidth(), tall));
        } else {
            roundPanel[16].setPreferredSize(new Dimension(jScrollPane[0].getWidth(), 390));
        }
        label[11].setText(receipt[3]);
    }

    private void changeCalender() {
        Day start = new Day(jdateChooser1.getDate());
        Day end = new Day(jdateChooser2.getDate());

        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);

        if (checklist) {
            roundPanel[16].removeAll();
            roundPanel[16].repaint();
            roundPanel[16].revalidate();
            for (Bill bill : billBLL.getBillList()) {
                if (bill.getDateOfPurchase().compareDates(start) && end.compareDates(bill.getDateOfPurchase()))
                    model.addRow(new Object[]{bill.getBillID(), bill.getCustomerID(), bill.getStaffID(), bill.getDateOfPurchase(), bill.getTotal(), bill.getReceived(), bill.getExcess()});
            }
        } else {
            roundPanel[16].removeAll();
            roundPanel[16].repaint();
            for (Receipt recipe : receiptBLL.getReceiptList()) {
                if (recipe.getDor().compareDates(start) && end.compareDates(recipe.getDor()))
                    model.addRow(new Object[]{recipe.getReceiptID(), recipe.getStaffID(), recipe.getDor(), recipe.getGrandTotal()});
            }
        }
    }

    public void pressSale() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2020);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        jdateChooser1.setCalendar(calendar);
        jdateChooser2.setDate(new Date());
        label[0].setText("Danh Sách Phiếu Bán Hàng");
        label[1].setText("HÓA ĐƠN");
        label[2].setText("Mã hóa đơn:");
        label[8].setText("Ngày:");
        label[4].setText("Tên khách hàng:");
        label[6].setText("Mã nhân viên:");
        label[12].setText("Tiền nhận:");
        label[14].setText("Tiền thối:");
        label[5].setFont(new Font("Times New Roman", Font.BOLD, 11));
        label[3].setText("");
        label[9].setText("");
        label[5].setText("");
        label[7].setText("");
        label[13].setText("");
        label[15].setText("");
        label[11].setText("");
        roundPanel[16].removeAll();
        roundPanel[16].repaint();
        roundPanel[16].revalidate();
        roundPanel[18].remove(scrollPane);
        roundPanel[18].repaint();
        roundPanel[18].revalidate();
        checklist = true;
        dataTable = new DataTable(billBLL.getData(), billColumnNames.subList(0, billColumnNames.size() - 1).toArray(), e -> fillForm());
        scrollPane = new JScrollPane(dataTable);
        roundPanel[18].add(scrollPane);
    }

    public void pressImport() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2020);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        jdateChooser1.setCalendar(calendar);
        jdateChooser2.setDate(new Date());
        label[0].setText("Danh Sách Phiếu Nhập Hàng");
        label[1].setText("PHIẾU NHẬP HÀNG");
        label[2].setText("Mã nhập hàng:");
        label[8].setText("Ngày:");
        label[4].setText("Mã nhân viên");
        label[5].setFont(new Font("Times New Roman", Font.BOLD, 14));
        label[6].setText("");
        label[12].setText("");
        label[14].setText("");
        label[3].setText("");
        label[9].setText("");
        label[5].setText("");
        label[7].setText("");
        label[13].setText("");
        label[15].setText("");
        label[11].setText("");
        roundPanel[16].removeAll();
        roundPanel[16].repaint();
        roundPanel[16].revalidate();
        roundPanel[18].remove(scrollPane);
        roundPanel[18].repaint();
        roundPanel[18].revalidate();
        checklist = false;
        dataTable = new DataTable(receiptBLL.getData(), receiptColumnNames.subList(0, receiptColumnNames.size() - 1).toArray(), e -> fillForm1());
        scrollPane = new JScrollPane(dataTable);
        roundPanel[18].add(scrollPane);
    }

    public void pressExcel() {

    }
}
