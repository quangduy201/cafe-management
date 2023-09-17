package com.cafe.GUI;

import com.cafe.BLL.*;
import com.cafe.DTO.*;
import com.cafe.custom.BillDetailPanel;
import com.cafe.custom.Button;
import com.cafe.custom.DataTable;
import com.cafe.custom.RoundPanel;
import com.cafe.main.CafeManagement;
import com.cafe.utils.*;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;

public class BillGUI extends JPanel {
    private RoundPanel[] roundPanel;
    private JLabel[] label;
    private JScrollPane[] jScrollPane;
    private JScrollPane scrollPane;
    private DataTable dataTable;
    private Button[] button;
    private BillBLL billBLL = new BillBLL();
    private List<String> billColumnNames = billBLL.getBillDAL().getColumnNames();
    private BillDetailsBLL billDetailsBLL = new BillDetailsBLL();
    private ReceiptBLL receiptBLL = new ReceiptBLL();
    private List<String> receiptColumnNames = receiptBLL.getReceiptDAL().getColumnNames();
    private ReceiptDetailsBLL receiptDetailsBLL = new ReceiptDetailsBLL();
    private ArrayList<BillDetails> listBillDetails = new ArrayList<>();
    private ArrayList<ReceiptDetails> listReceiptDetails = new ArrayList<>();
    private Button btnCancel = new Button();
    private JDateChooser[] jDateChooser;
    private JTextField[] jTextField;
    private boolean inSaleMode = true;
    private Button btFaceSignUp;

    public BillGUI() {
        System.gc();
        setLayout(new BorderLayout());
        setBackground(new Color(70, 67, 67));
        initComponents();
        changeMode("SALE");
    }

    public void initComponents() {
        roundPanel = new RoundPanel[21];
        label = new JLabel[21];
        button = new Button[4];
        jScrollPane = new JScrollPane[2];
        for (int i = 0; i < roundPanel.length; i++) {
            roundPanel[i] = new RoundPanel();
            label[i] = new JLabel();
        }

        roundPanel[0].setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        roundPanel[0].setBackground(new Color(70, 67, 67));
        this.add(roundPanel[0], BorderLayout.CENTER);

        roundPanel[1].setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        roundPanel[1].setPreferredSize(new Dimension(560, 670));
        roundPanel[1].setBackground(new Color(70, 67, 67));
        roundPanel[1].setAutoscrolls(true);
        roundPanel[0].add(roundPanel[1]);

        roundPanel[2].setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
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

        roundPanel[5].setLayout(new BorderLayout(20, 0));
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
        label[0].setForeground(Color.WHITE);
        label[0].setAutoscrolls(true);
        roundPanel[3].add(label[0]);

        roundPanel[15].setLayout(new BorderLayout(5, 0));
        roundPanel[15].setBackground(new Color(70, 67, 67));
        roundPanel[15].setPreferredSize(new Dimension(170, 40));
        roundPanel[15].setAutoscrolls(true);
        roundPanel[5].add(roundPanel[15], BorderLayout.WEST);

        for (int i = 0; i < 4; i++) {
            button[i] = new Button();
            button[i].setBorderPainted(false);
            button[i].setFocusPainted(false);
            button[i].setFont(new Font("Times New Roman", Font.PLAIN, 14));
            button[i].setColor(new Color(0x70E149));
            button[i].setColorOver(new Color(0x5EFF00));
            button[i].setColorClick(new Color(0x8AD242));
            button[i].setBorderColor(Color.BLACK);
            button[i].setForeground(Color.BLACK);
            button[i].setRadius(15);
            button[i].setPreferredSize(new Dimension(80, 40));
            if (i == 2 || i == 3) {
                button[i].setText("Xuất PDF");
                button[i].setColor(new Color(240, 240, 240));
                button[i].setPreferredSize(new Dimension(160, 40));
                button[i].setIcon(Resource.loadImageIconIn("img/icons/folder.png"));
            }
            switch (i) {
                case 0 -> button[i].setText("Bán");
                case 1 -> button[i].setText("Nhập");
            }
            int index = i;
            button[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent mouseEvent) {
                    try {
                        switch (index) {
                            case 0 -> changeMode("SALE");
                            case 1 -> changeMode("IMPORT");
                            case 2 -> pressPDF(true);
                            case 3 -> pressPDF(false);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            });
        }
        roundPanel[15].add(button[0], BorderLayout.WEST);
        roundPanel[15].add(button[1], BorderLayout.EAST);
        roundPanel[5].add(button[2], BorderLayout.EAST);
        roundPanel[2].add(button[3]);

        btFaceSignUp = new Button();
        btFaceSignUp.setBackground(new Color(35, 166, 97));
        btFaceSignUp.setBorder(null);
        btFaceSignUp.setIcon(new ImageIcon(Resource.loadImageIconIn("img/icons/face-scanner.png").getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
        btFaceSignUp.setText("Tìm hóa đơn");
        btFaceSignUp.setColor(new Color(240, 240, 240));
        btFaceSignUp.setColorClick(new Color(141, 222, 175));
        btFaceSignUp.setColorOver(new Color(35, 166, 97));
        btFaceSignUp.setBorderColor(Color.BLACK);
        btFaceSignUp.setForeground(Color.BLACK);
        btFaceSignUp.setFocusPainted(false);
        btFaceSignUp.setRadius(20);
        btFaceSignUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                findBillsByFace();
            }
        });
        roundPanel[5].add(btFaceSignUp, BorderLayout.CENTER);

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

//        btnCancel.setPreferredSize(new Dimension(100, 40));
//        btnCancel.setBorderPainted(false);
//        btnCancel.setRadius(15);
//        btnCancel.setFocusPainted(false);
//        btnCancel.setIcon(Resource.loadImageIconIn("img/icons/remove.png"));
//        btnCancel.setColor(new Color(0xFFBD3737));
//        btnCancel.setColorOver(new Color(0xFF0000));
//        btnCancel.setColorClick(new Color(0xB65858));
//        btnCancel.setText("Hủy");
//        btnCancel.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent mouseEvent) {
//                clickBtnCancel();
//            }
//        });
//        roundPanel[2].add(btnCancel);

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

        jScrollPane[1] = new JScrollPane(roundPanel[16]);
        jScrollPane[0] = new JScrollPane(roundPanel[16]);
        jScrollPane[0].setPreferredSize(new Dimension(400, 380));
        jScrollPane[0].setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane[0].setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane[0].setBorder(BorderFactory.createEmptyBorder());
        roundPanel[16].setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
//        roundPanel[16].setBackground(new Color(240, 240, 240));
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

        jDateChooser = new JDateChooser[2];
        jTextField = new JTextField[2];
        for (int i = 0; i < 2; i++) {
            jDateChooser[i] = new JDateChooser(new Day().toDateSafe());
            jDateChooser[i].setDateFormatString("dd/MM/yyyy");
            jDateChooser[i].setPreferredSize(new Dimension(150, 20));
            jDateChooser[i].setMinSelectableDate(new Day(1, 1, 1000).toDateSafe());
            jDateChooser[i].addPropertyChangeListener("date", evt -> changeCalender());
            jTextField[i] = (JTextField) jDateChooser[i].getDateEditor().getUiComponent();
            jTextField[i].setFont(new Font("Tahoma", Font.BOLD, 12));
            jTextField[i].setHorizontalAlignment(JTextField.CENTER);
            int index = i;
            jTextField[i].addActionListener(e -> {
                try {
                    Day day = Day.parseDay(jTextField[index].getText());
                    jDateChooser[index].setDate(day.toDate());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid date", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
        roundPanel[19].add(jDateChooser[0]);
        roundPanel[20].add(jDateChooser[1]);

        scrollPane = new JScrollPane(dataTable);
        roundPanel[18].add(scrollPane);
    }

    public void fillForm() {
        listBillDetails.clear();
        listReceiptDetails.clear();
        clearDetails();
        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        Object[] rowData = model.getDataVector().elementAt(dataTable.getSelectedRow()).toArray();
        String[] data = new String[rowData.length];
        for (int i = 0; i < rowData.length; i++) {
            data[i] = rowData[i].toString();
        }
        if (inSaleMode)
            fillFormBillDetails(data);
        else
            fillFormReceiptDetails(data);
    }

    public void fillFormBillDetails(String[] data) {
        String[] bill = String.join(" | ", data).split(" \\| ");
        label[3].setText(bill[0]);
        label[9].setText(bill[3]);
        Customer customer = new CustomerBLL()
            .searchCustomers("CUSTOMER_ID = '" + bill[1] + "'")
            .get(0);
        label[5].setText(customer.getName());
        label[7].setText(bill[2]);
        for (BillDetails billDetails : billDetailsBLL.getBillDetailsList()) {
            if (billDetails.getBillID().equals(bill[0])) {
                listBillDetails.add(billDetails);
                BillDetailPanel billDetailPanel = new BillDetailPanel();
                billDetailPanel.setBill(billDetails);
                roundPanel[16].add(billDetailPanel);
            }
        }
        int height = 390;
        if (listBillDetails.size() >= 5)
            height = 75 * this.listBillDetails.size();
        roundPanel[16].setPreferredSize(new Dimension(jScrollPane[0].getWidth(), height));
        label[11].setText(VNString.currency(Double.parseDouble(bill[4])));
        label[13].setText(VNString.currency(Double.parseDouble(bill[5])));
        label[15].setText(VNString.currency(Double.parseDouble(bill[6])));
    }

    public void fillFormReceiptDetails(String[] data) {
        String[] receipt = String.join(" | ", data).split(" \\| ");
        label[3].setText(receipt[0]);
        label[9].setText(receipt[2]);
        Supplier supplier = new SupplierBLL()
            .searchSuppliers("SUPPLIER_ID = '" + receipt[4] + "'")
            .get(0);
        label[5].setText(supplier.getName());
        label[7].setText(receipt[1]);
        for (ReceiptDetails receiptDetails : receiptDetailsBLL.getReceiptDetailsList()) {
            if (receiptDetails.getReceiptID().equals(receipt[0])) {
                listReceiptDetails.add(receiptDetails);
                BillDetailPanel receiptDetailPanel = new BillDetailPanel();
                receiptDetailPanel.setReceipt(receiptDetails);
                roundPanel[16].add(receiptDetailPanel);
            }
        }
        int height = 390;
        if (listReceiptDetails.size() >= 5)
            height = 75 * this.listReceiptDetails.size();
        roundPanel[16].setPreferredSize(new Dimension(jScrollPane[1].getWidth(), height));
        label[11].setText(VNString.currency(Double.parseDouble(receipt[3])));
    }

    private void changeCalender() {
        Day start = new Day(jDateChooser[0].getDate());
        Day end = new Day(jDateChooser[1].getDate());

        DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
        model.setRowCount(0);
        clearDetails();

        if (inSaleMode) {

            for (Bill bill : billBLL.getBillList())
                if (bill.getDateOfPurchase().isBetween(start, end))
                    model.addRow(new Object[]{bill.getBillID(), bill.getCustomerID(), bill.getStaffID(), bill.getDateOfPurchase(), bill.getTotal(), bill.getReceived(), bill.getExcess()});
        } else {
            for (Receipt receipt : receiptBLL.getReceiptList())
                if (receipt.getDor().isBetween(start, end))
                    model.addRow(new Object[]{receipt.getReceiptID(), receipt.getStaffID(), receipt.getDor(), receipt.getGrandTotal(), receipt.getSupplierID()});
        }
    }

    public void changeMode(String mode) {
        switch (mode) {
            case "SALE" -> pressSale();
            case "IMPORT" -> pressImport();
        }
        Day today = new Day();
        jDateChooser[0].setDate(today.before(0, 5, 0).toDateSafe());
        jDateChooser[1].setDate(today.toDateSafe());
        clearDetails();
        roundPanel[18].remove(scrollPane);
        roundPanel[18].repaint();
        roundPanel[18].revalidate();
        scrollPane = new JScrollPane(dataTable);
        roundPanel[18].add(scrollPane);
    }

    public void pressSale() {
        label[0].setText("Danh Sách Phiếu Bán Hàng");
        label[1].setText("HÓA ĐƠN");
        label[2].setText("Mã hóa đơn:");
        label[4].setText("Tên khách hàng:");
        label[12].setText("Tiền nhận:");
        label[14].setText("Tiền thối:");
        label[5].setFont(new Font("Times New Roman", Font.BOLD, 11));
        dataTable = new DataTable(billBLL.getData(), new String[]{"Mã hoá đơn", "Mã khách hàng", "Mã nhân viên", "Ngày mua", "Tổng tiền", "Tiền nhận", "Tiền thừa"}, e -> fillForm());
        inSaleMode = true;
    }

    public void pressImport() {
        label[0].setText("Danh Sách Phiếu Nhập Hàng");
        label[1].setText("PHIẾU NHẬP HÀNG");
        label[2].setText("Mã nhập hàng:");
        label[4].setText("Nhà cung cấp:");
        label[12].setText("");
        label[14].setText("");
        label[5].setFont(new Font("Times New Roman", Font.BOLD, 14));
        dataTable = new DataTable(receiptBLL.getData(), new String[]{"Mã phiếu nhập", "Mã nhân viên", "Ngày nhập", "Tổng tiền", "Mã nhà cung cấp"}, e -> fillForm());
        inSaleMode = false;
    }

    public void pressPDF(boolean all) {
        try {
            Files.createDirectories(Paths.get(Resource.getPathOutsideJAR("transaction/export/bill")));
            Files.createDirectories(Paths.get(Resource.getPathOutsideJAR("transaction/export/receipt")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setCurrentDirectory(new File(Resource.getPathOutsideJAR("transaction/export")));
        if (all) {
            Day from = new Day(jDateChooser[0].getDate());
            Day to = new Day(jDateChooser[1].getDate());
            if (dataTable.getModel().getRowCount() == 0) {
                JOptionPane.showMessageDialog(CafeManagement.homeGUI, "Không thể xuất dữ liệu rỗng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int message = JOptionPane.showOptionDialog(CafeManagement.homeGUI,
                "Xuất dữ liệu từ ngày " + from + " đến " + to + " sang PDF?", "Xuất dữ liệu?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Xuất", "Huỷ"},
                "Xuất");
            if (message == JOptionPane.NO_OPTION || message == JOptionPane.CLOSED_OPTION) {
                return;
            }
            if (fileChooser.showOpenDialog(CafeManagement.homeGUI) != JFileChooser.APPROVE_OPTION) {
                return;
            }
            File selectedFile = fileChooser.getSelectedFile();
            String path = selectedFile.getAbsolutePath();

            DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
            Object[] data = model.getDataVector().toArray();
            if (inSaleMode) {
                List<Bill> bills = new ArrayList<>();
                for (Object row : data) {
                    Vector<?> vector = (Vector<?>) row;
                    bills.add(billBLL.searchBills("BILL_ID = '" + vector.elementAt(0) + "'").get(0));
                }
                if (PDF.exportBillsPDF(bills, from, to, path))
                    JOptionPane.showMessageDialog(CafeManagement.homeGUI, "Đã xuất bảng hóa đơn sang PDF.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(CafeManagement.homeGUI, "Xuất dữ liệu sang PDF thất bại.", "Thất bại", JOptionPane.ERROR_MESSAGE);
            } else {
                List<Receipt> receipts = new ArrayList<>();
                for (Object row : data) {
                    Vector<?> vector = (Vector<?>) row;
                    receipts.add(receiptBLL.searchReceipts("RECEIPT_ID = '" + vector.elementAt(0) + "'").get(0));
                }
                if (PDF.exportReceiptsPDF(receipts, from, to, path))
                    JOptionPane.showMessageDialog(CafeManagement.homeGUI, "Đã xuất bảng phiếu nhập hàng sang PDF.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(CafeManagement.homeGUI, "Xuất dữ liệu sang PDF thất bại.", "Thất bại", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            String id = label[3].getText();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(CafeManagement.homeGUI, "Không thể xuất dữ liệu rỗng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int message = JOptionPane.showOptionDialog(CafeManagement.homeGUI,
                "Xuất dữ liệu của " + id + " sang PDF?",
                "Xuất dữ liệu?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Xuất", "Huỷ"},
                "Xuất");
            if (message == JOptionPane.NO_OPTION || message == JOptionPane.CLOSED_OPTION) {
                return;
            }
            if (fileChooser.showOpenDialog(CafeManagement.homeGUI) != JFileChooser.APPROVE_OPTION) {
                return;
            }
            File selectedFile = fileChooser.getSelectedFile();
            String path = selectedFile.getAbsolutePath();

            if (inSaleMode) {
                Bill bill = billBLL.findBillsBy(Map.of("BILL_ID", id)).get(0);
                if (PDF.exportPDF(bill, path))
                    JOptionPane.showMessageDialog(CafeManagement.homeGUI, "Đã xuất " + id + " sang PDF.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(CafeManagement.homeGUI, "Xuất dữ liệu của hoá đơn " + id + " sang PDF thất bại.", "Thất bại", JOptionPane.ERROR_MESSAGE);
            } else {
                Receipt receipt = receiptBLL.findReceiptsBy(Map.of("RECEIPT_ID", id)).get(0);
                if (PDF.exportPDF(receipt, path))
                    JOptionPane.showMessageDialog(CafeManagement.homeGUI, "Đã xuất " + id + " sang PDF.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(CafeManagement.homeGUI, "Xuất dữ liệu của phiếu nhập hàng " + id + " sang PDF thất bại.", "Thất bại", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void clickBtnCancel() {
//        if (inSaleMode) {
//            Bill bill = billBLL.findBillsBy(Map.of("BILL_ID", id)).get(0);
//            if (Excel.writeToExcel(bill, path))
//                JOptionPane.showMessageDialog(null, "Printed " + id + " to Excel.", "Success", JOptionPane.INFORMATION_MESSAGE);
//            else
//                JOptionPane.showMessageDialog(null, "Xuất dữ liệu của hoá đơn " + id + " sang Excel thất bại.", "Thất bại", JOptionPane.ERROR_MESSAGE);
//        } else {
//            Receipt receipt = receiptBLL.findReceiptsBy(Map.of("RECEIPT_ID", id)).get(0);
//            if (Excel.writeToExcel(receipt, path))
//                JOptionPane.showMessageDialog(null, "Printed " + id + " to Excel.", "Success", JOptionPane.INFORMATION_MESSAGE);
//            else
//                JOptionPane.showMessageDialog(null, "Xuất dữ liệu của phiếu nhập hàng " + id + " sang Excel thất bại.", "Thất bại", JOptionPane.ERROR_MESSAGE);
//        }
    }
    public void clearDetails() {
        for (int i = 3; i <= 15; i += 2) {
            label[i].setText("");
        }
        roundPanel[16].removeAll();
        roundPanel[16].repaint();
        roundPanel[16].revalidate();
    }

    public void findBillsByFace() {
        Tasks tasks = new Tasks("Camera");
        clearDetails();
        new Thread(() -> tasks.detectAndRecognize(50.0, (DefaultTableModel) dataTable.getModel(), "BILL")).start();
    }
}
