package com.cafe.GUI;

import com.cafe.BLL.AccountBLL;
import com.cafe.BLL.DecentralizationBLL;
import com.cafe.BLL.StaffBLL;
import com.cafe.DTO.Account;
import com.cafe.DTO.Decentralization;
import com.cafe.DTO.Staff;
import com.cafe.custom.*;
import com.cafe.custom.Button;
import com.cafe.custom.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HomeGUI extends JFrame {
    private Account account;
    private Staff staff;
    private Decentralization decentralization;
    private int[] mang = new int[14];
    private RoundPanel home;
    private Header north;
    private JPanel center;
    private RoundPanel east;
    private RoundPanel west;
    private RoundPanel info;
    private RoundPanel cate_frame;
    private RoundPanel cate;
    private RoundPanel function;
    private ProductPanel currentBtn;
    private Button exit;
    private Button minimize;
    private JLabel lbName;
    private JLabel lbRole;
    private JLabel lbTime;
    private JLabel[] jLabel = new JLabel[15];
    private ProductPanel[] roundPanel = new ProductPanel[15];
    private ImageAvatar[] imageAvatar = new ImageAvatar[15];
    private ToggleSwitch themeButton;

    public HomeGUI(Account account) {
        this.account = account;
        getUser();
        initComponents();
        changeTheme();
        setTime();
    }

    public static void main(String[] args) {
        AccountBLL accountBLL = new AccountBLL();
        List<Account> accountList = accountBLL.searchAccounts("USERNAME = 'admin'", "PASSWD = 'admin'");
        Account account = accountList.get(0);
        new HomeGUI(account).setVisible(true);
    }

    private void getUser() {
        decentralization = new DecentralizationBLL()
            .searchDecentralization("DECENTRALIZATION_ID = '" + account.getDecentralizationID() + "'")
            .get(0);
        staff = new StaffBLL()
            .searchStaffs("STAFF_ID = '" + account.getStaffID() + "'")
            .get(0);
//        TODO: khi nào có nhà cung cấp thì đổi lại khúc này
//        String[] decentralizationString = decentralization.toString().split(" \\| ");
//        for (int i = 1; i < decentralizationString.length - 1; i++) {
//            mang[i] = Integer.parseInt(decentralizationString[i]);
//        }
        mang[1] = decentralization.getIsSale();
        mang[2] = decentralization.getIsProduct();
        mang[3] = decentralization.getIsCategory();
        mang[4] = decentralization.getIsRecipe();
        mang[5] = decentralization.getIsImport();
        mang[6] = decentralization.getIsBill();
        mang[7] = decentralization.getIsWarehouses();
        mang[8] = decentralization.getIsDecentralization();
        mang[9] = decentralization.getIsAccount();
        mang[10] = decentralization.getIsStaff();
        mang[11] = decentralization.getIsCustomer();
        mang[12] = decentralization.getIsDiscount();
        mang[13] = decentralization.getIsDecentralization();
    }

    private void initComponents() {
        home = new RoundPanel();
        north = new Header();
        lbTime = new JLabel();
        themeButton = new ToggleSwitch();
        minimize = new Button();
        exit = new Button();

        center = new JPanel();
        west = new RoundPanel();
        east = new RoundPanel();

        info = new RoundPanel();
        lbName = new JLabel();
        lbRole = new JLabel();

        cate_frame = new RoundPanel();
        cate = new RoundPanel();
        function = new RoundPanel();

        for (int i = 0; i < roundPanel.length; i++) {
            roundPanel[i] = new ProductPanel();
            imageAvatar[i] = new ImageAvatar();
            jLabel[i] = new JLabel();
        }

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
//        setBackground(new Color(35, 166, 97));

        home.setLayout(new BorderLayout(10, 10));
        setContentPane(home);

        // home.north
        north.setLayout(null);
        north.setPreferredSize(new Dimension(1300, 50));
        home.add(north, BorderLayout.NORTH);

        lbTime.setBounds(80, 10, 200, 30);
        lbTime.setFont(new Font("Public Sans", Font.PLAIN, 15));
        lbTime.setForeground(new Color(255, 255, 255));
        lbTime.setText("jLabel2");
        north.add(lbTime);

        themeButton.setBounds(1150, 10, 66, 34);
        themeButton.addActionListener(e -> changeTheme());
        themeButton.setSelected(true);
        themeButton.setOnColor(new Color(0xFCE797));
        themeButton.setOffColor(new Color(0x504C4C));
        themeButton.setOnIcon(new ImageIcon("img/sun.png"));
        themeButton.setOffIcon(new ImageIcon("img/moon.png"));
        north.add(themeButton);

        minimize.setText("-");
        minimize.setBackground(new Color(0xF3F0F0));
        minimize.setBounds(1235, 10, 50, 30);
        minimize.setBorderPainted(false);
        minimize.setFocusPainted(false);
        minimize.setFont(new Font("Public Sans", Font.BOLD, 15));
        minimize.setColor(new Color(0xF3F0F0));
        minimize.setColorOver(new Color(0xC4BDBD));
        minimize.setColorClick(new Color(0x676161));
        minimize.setRadius(15);
        minimize.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                minimize();
            }
        });
        north.add(minimize);

        exit.setText("X");
        exit.setBackground(new Color(0xFD1111));
        exit.setBounds(1290, 10, 50, 30);
        exit.setBorderPainted(false);
        exit.setFocusPainted(false);
        exit.setFont(new Font("Public Sans", Font.BOLD, 15));
        exit.setColor(new Color(0xFD1111));
        exit.setColorOver(new Color(0xB04848));
        exit.setColorClick(new Color(0xE79292));
        exit.setRadius(15);
        exit.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                exit();
            }
        });
        north.add(exit);

        // home.center
        center.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        center.setPreferredSize(new Dimension(1350, 710));
        home.add(center, BorderLayout.CENTER);

        west.setLayout(new BorderLayout(0, 10));
        west.setPreferredSize(new Dimension(300, 700));
        center.add(west);

        east.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        east.setPreferredSize(new Dimension(1020, 700));
        east.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(east);

        // home.center.west
        info.setLayout(null);
        info.setPreferredSize(new Dimension(300, 80));
        info.setAlignmentX(Component.CENTER_ALIGNMENT);
        west.add(info, BorderLayout.NORTH);

        imageAvatar[0].setForeground(new Color(255, 255, 255));
        imageAvatar[0].setBorderSize(2);
        imageAvatar[0].setBounds(20, 10, 60, 60);
        imageAvatar[0].setIcon(new ImageIcon("img/bell-boy.png"));
        info.add(imageAvatar[0]);

        lbName.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lbName.setForeground(new Color(255, 255, 255));
        lbName.setBounds(100, 15, 200, 20);
        lbName.setText("Tên: " + staff.getName());
        info.add(lbName);

        lbRole.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lbRole.setBounds(100, 45, 150, 20);
        lbRole.setForeground(new Color(255, 255, 255));
        lbRole.setText("Vai Trò: " + decentralization.getDecentralizationName());
        info.add(lbRole);

        cate_frame.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        cate_frame.setPreferredSize(new Dimension(300, 600));
        cate_frame.setAlignmentX(Component.CENTER_ALIGNMENT);
        west.add(cate_frame, BorderLayout.CENTER);

        cate.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        cate.setPreferredSize(new Dimension(280, 590));
        cate.setAlignmentX(Component.CENTER_ALIGNMENT);
        cate_frame.add(cate);

        for (int i = 1; i < roundPanel.length - 1; i++) {
            if (mang[i] != 0) {
                roundPanel[i].setLayout(new FlowLayout(FlowLayout.LEFT, 30, 5));
                roundPanel[i].setPreferredSize(new Dimension(270, 40));
                roundPanel[i].setAutoscrolls(true);
                roundPanel[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
                int index = i;
                roundPanel[i].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        roundPanelMousePressed(index);
                    }
                });
                cate.add(roundPanel[i]);
            }
        }

        for (int i = 1; i < imageAvatar.length - 1; i++) {
            if (mang[i] != 0) {
                imageAvatar[i].setPreferredSize(new Dimension(30, 30));
                imageAvatar[i].setBorderSize(2);
                imageAvatar[i].setIcon(new ImageIcon("img/0" + i + ".png"));
                roundPanel[i].add(imageAvatar[i]);

                jLabel[i].setFont(new Font("Times New Roman", Font.PLAIN, 18));
                jLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
                jLabel[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
                roundPanel[i].add(jLabel[i]);
            }
        }

        jLabel[1].setText("Bán hàng");
        jLabel[2].setText("Sản phẩm");
        jLabel[3].setText("Loại sản phẩm");
        jLabel[4].setText("Công thức");
        jLabel[5].setText("Nhập hàng");
        jLabel[6].setText("Hóa đơn");
        jLabel[7].setText("Nhà kho");
        jLabel[8].setText("Thống kê");
        jLabel[9].setText("Tài khoản");
        jLabel[10].setText("Nhân viên");
        jLabel[11].setText("Khách hàng");
        jLabel[12].setText("Giảm giá");
        jLabel[13].setText("Phân quyền");

        // home.center.east
        function.setPreferredSize(new Dimension(1005, 680));
        function.setBackground(new Color(70, 67, 67));
        pack();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
        setLocationRelativeTo(null);
    }

    private void changeTheme() {
        Color roundPanelBG, roundPanelColor, roundPanelColorOver, imageAvatarFG, labelBG, labelFG, currentBtnBG;
        if (themeButton.isSelected()) {
            home.setBackground(new Color(240, 240, 240));
            north.setBackground(new Color(35, 166, 97));
            center.setBackground(new Color(240, 240, 240));
            west.setBackground(new Color(240, 240, 240));
            east.setBackground(new Color(70, 67, 67));
            info.setBackground(new Color(79, 194, 53));
            cate_frame.setBackground(new Color(79, 194, 53));
            cate.setBackground(new Color(79, 194, 53));
            roundPanelBG = new Color(240, 240, 240);
            roundPanelColor = new Color(240, 240, 240);
            roundPanelColorOver = new Color(68, 150, 60);
            imageAvatarFG = new Color(225, 200, 73, 255);
            labelBG = new Color(51, 51, 51);
            labelFG = new Color(25, 25, 25);
            currentBtnBG = new Color(68, 150, 60);
        } else {
            home.setBackground(new Color(35, 166, 97));
            north.setBackground(new Color(70, 67, 67));
            center.setBackground(new Color(35, 166, 97));
            west.setBackground(new Color(35, 166, 97));
            east.setBackground(new Color(70, 67, 67));
            info.setBackground(new Color(70, 67, 67));
            cate_frame.setBackground(new Color(70, 67, 67));
            cate.setBackground(new Color(70, 67, 67));
            roundPanelBG = new Color(70, 67, 67);
            roundPanelColor = new Color(70, 67, 67);
            roundPanelColorOver = new Color(35, 166, 97);
            imageAvatarFG = new Color(240, 240, 240, 255);
            labelBG = new Color(51, 51, 51);
            labelFG = new Color(240, 240, 240);
            currentBtnBG = new Color(35, 166, 97);
        }
        for (int i = 1; i < jLabel.length; i++) {
            roundPanel[i].setBackground(roundPanelBG);
            roundPanel[i].setColor(roundPanelColor);
            roundPanel[i].setColorOver(roundPanelColorOver);
            imageAvatar[i].setForeground(imageAvatarFG);
            jLabel[i].setBackground(labelBG);
            jLabel[i].setForeground(labelFG);
        }
        if (currentBtn != null)
            currentBtn.setBackground(currentBtnBG);
    }

    private void roundPanelMousePressed(int index) {
        Active(roundPanel[index]);
        JPanel panel = switch (index) {
            case 1 -> new SaleGUI(account.getStaffID());
            case 2 -> new ProductGUI(decentralization.getIsProduct());
            case 3 -> new CategoryGUI(decentralization.getIsCategory());
            case 4 -> new RecipeGUI(decentralization.getIsRecipe());
//            case 5 -> new ImportGUI(decentralization.getIsImport());
//            TODO: khi nào có Nhà cung cấp thì đổi lại case 6, uncomment case 5 và tăng các case bên dưới lên 1 đơn vị
            case 5 -> new SupplierGUI(decentralization.getIsSupplier());
            case 6 -> new BillGUI();
            case 7 -> new WarehousesGUI(decentralization.getIsWarehouses());
            case 8 -> new StatisticGUI();
            case 9 -> new AccountGUI(decentralization.getIsAccount());
            case 10 -> new StaffGUI(decentralization.getIsStaff());
            case 11 -> new CustomerGUI(decentralization.getIsCustomer());
            case 12 -> new DiscountGUI(decentralization.getIsDiscount());
            case 13 -> new DecentralizationGUI(decentralization.getIsDecentralization());
            default -> null;
        };
        OpenChildForm(panel);
    }

    private void Disable() {
        if (currentBtn != null) {
            currentBtn.setPressover(false);
            if (themeButton.isSelected()) {
                currentBtn.setBackground(new Color(240, 240, 240));
            } else {
                currentBtn.setBackground(new Color(70, 67, 67));
            }
        }
    }

    private void Active(ProductPanel btn) {
        Disable();
        currentBtn = btn;
        currentBtn.setPressover(true);
        if (themeButton.isSelected())
            currentBtn.setBackground(new Color(68, 150, 60));
        else
            currentBtn.setBackground(new Color(35, 166, 97));
    }

    private void OpenChildForm(JPanel panel) {
        function.removeAll();
        function.add(panel);
        function.repaint();
        function.revalidate();
        east.add(function);
    }

    private void minimize() {
        setState(HomeGUI.ICONIFIED);
    }

    private void exit() {
        int message = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thoát?", "Quit", JOptionPane.YES_NO_OPTION);
        if (message == JOptionPane.YES_OPTION) {
            this.dispose();
            new LoginGUI();
        }
    }

    private void setTime() {
        SimpleDateFormat formatter = new SimpleDateFormat();
        formatter.applyPattern("dd-MM-yyyy : hh:mm:ss aa");
        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    lbTime.setText(formatter.format(new Date()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}
