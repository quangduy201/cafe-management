package com.cafe.GUI;

import com.cafe.BLL.AccountBLL;
import com.cafe.BLL.DecentralizationBLL;
import com.cafe.BLL.DiscountBLL;
import com.cafe.BLL.StaffBLL;
import com.cafe.DTO.Account;
import com.cafe.DTO.Decentralization;
import com.cafe.DTO.Staff;
import com.cafe.custom.Button;
import com.cafe.custom.*;
import com.cafe.main.CafeManagement;
import com.cafe.utils.Day;
import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class HomeGUI extends JFrame {
    private Account account;
    private Staff staff;
    private Decentralization decentralization;
    private int[] mang = new int[15];
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
    private RoundPanel[] rpContent = new RoundPanel[15];
    private int numberContent = -1;
    private int addContent;
    private JLabel[] labelName = new JLabel[15];
    private ProductPanel[] roundPanel = new ProductPanel[15];
    private ImageAvatar[] imageAvatar = new ImageAvatar[15];
    private ImageAvatar[] imageIcon = new ImageAvatar[3];
    private int numberjpane;
    private ToggleSwitch themeButton;

    private String string = "img/icons/down.png";

    private int[] listCount = new int[3];
    private int totalHeight;
    private Color imageAvatarIcon;

    public HomeGUI(Account account) {
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        this.account = account;
        getUser();
        initComponents();
        changeTheme();
        setTime();
        checkDiscountNotAvailable();
    }

    public static void main(String[] args) {
        AccountBLL accountBLL = new AccountBLL();
        List<Account> accountList = accountBLL.searchAccounts("USERNAME = 'admin'", "PASSWD = 'admin'");
        Account account = accountList.get(0);
        new HomeGUI(account).setVisible(true);
    }

    public void setAccount(Account account) {
        this.account = account;
        getUser();
        System.gc();
        initLeftMenu();
    }

    private void getUser() {
        decentralization = new DecentralizationBLL()
            .searchDecentralization("DECENTRALIZATION_ID = '" + account.getDecentralizationID() + "'")
            .get(0);
        staff = new StaffBLL()
            .searchStaffs("STAFF_ID = '" + account.getStaffID() + "'")
            .get(0);
        mang[1] = decentralization.getIsSale();
        mang[2] = decentralization.getIsImport();
        mang[3] = decentralization.getIsBill();
        mang[4] = decentralization.getIsWarehouses();
        mang[5] = decentralization.getIsProduct();
        mang[6] = decentralization.getIsCategory();
        mang[7] = decentralization.getIsRecipe();
        mang[8] = decentralization.getIsDiscount();
        mang[9] = decentralization.getIsDecentralization();
        mang[10] = decentralization.getIsCustomer();
        mang[11] = decentralization.getIsStaff();
        mang[12] = decentralization.getIsAccount();
        mang[13] = decentralization.getIsDecentralization();
        mang[14] = decentralization.getIsSupplier();
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

        for (int i = 0; i < rpContent.length; i++) {
            rpContent[i] = new RoundPanel();
            labelName[i] = new JLabel();
        }

        for (int i = 0; i < imageIcon.length; i++) {
            imageIcon[i] = new ImageAvatar();
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
        themeButton.setOnIcon(new ImageIcon("img/icons/sun.png"));
        themeButton.setOffIcon(new ImageIcon("img/icons/moon.png"));
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

        initLeftMenu();

        // home.center.east
        function.setPreferredSize(new Dimension(1005, 680));
        function.setBackground(new Color(70, 67, 67));
        pack();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
        setLocationRelativeTo(null);
    }

    public void initLeftMenu() {
        listCount[0] = 0;
        listCount[1] = 0;
        listCount[2] = 0;

        west.removeAll();
        west.revalidate();
        west.repaint();
        cate_frame.removeAll();
        cate_frame.revalidate();
        cate_frame.repaint();
        cate.removeAll();
        cate.revalidate();
        cate.repaint();

        info.setLayout(null);
        info.setPreferredSize(new Dimension(300, 80));
        info.setAlignmentX(Component.CENTER_ALIGNMENT);
        west.add(info, BorderLayout.NORTH);

        imageAvatar[0].setForeground(new Color(255, 255, 255));
        imageAvatar[0].setBorderSize(2);
        imageAvatar[0].setBounds(20, 10, 60, 60);
        imageAvatar[0].setIcon(new ImageIcon("img/icons/bell-boy.png"));
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

        cate_frame.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        cate_frame.setPreferredSize(new Dimension(300, 600));
        cate_frame.setAlignmentX(Component.CENTER_ALIGNMENT);
        west.add(cate_frame, BorderLayout.CENTER);

        cate.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        cate.setPreferredSize(new Dimension(290, 600));
        cate.setAlignmentX(Component.CENTER_ALIGNMENT);
        cate_frame.add(cate);

        for (int i = 1; i < 5; i++) {
            if (mang[i] != 0) {
                roundPanel[i].setLayout(new FlowLayout(FlowLayout.LEFT, 35, 7));
                roundPanel[i].setPreferredSize(new Dimension(280, 45));
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

        for (int i = 5; i <= 14; i++) {
            if (mang[i] != 0) {
                switch (i) {
                    case 5, 6, 7 -> listCount[0]++;
                    case 8, 9 -> listCount[1]++;
                    case 10, 11, 12, 13, 14 -> listCount[2]++;
                    default -> {
                    }
                }
            }
        }

        if (listCount[0] != 0) {
            rpContent[0].setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            rpContent[0].setPreferredSize(new Dimension(280, 45));
            rpContent[0].setAutoscrolls(true);
            rpContent[0].setCursor(new Cursor(Cursor.HAND_CURSOR));
            cate.add(rpContent[0]);

            rpContent[2].setLayout(new FlowLayout(FlowLayout.LEFT, 0, 7));
            rpContent[2].setPreferredSize(new Dimension(280, 45));
            rpContent[2].setAutoscrolls(true);
            rpContent[2].setCursor(new Cursor(Cursor.HAND_CURSOR));
            rpContent[2].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (numberContent != -1 && numberjpane != 0) {
//                    string = "img/icons/up.png";
                        imageIcon[numberjpane].setIcon(null);
                        imageIcon[numberjpane].setIcon(new ImageIcon(string));
                        pressDelay(numberContent, rpContent[numberContent].getHeight(), addContent);
                    }
                    totalHeight = (listCount[0] * 50) / 5;
                    if (rpContent[0].getHeight() == 45) {
//                    string = string;
                        numberjpane = 0;
                        pressDelay(0, rpContent[0].getHeight(), totalHeight);
                        numberContent = 0;
                        addContent = -totalHeight;
                    } else {
                        pressDelay(0, rpContent[0].getHeight(), -totalHeight);
                        numberContent = -1;
                    }
                }
            });
            rpContent[0].add(rpContent[2]);

            labelName[0].setPreferredSize(new Dimension(230, 30));
            labelName[0].setFont(new Font("Times New Roman", Font.PLAIN, 18));
            labelName[0].setText("-----*Quản lý sản phẩm");
            labelName[0].setHorizontalAlignment(SwingConstants.CENTER);
            labelName[0].setCursor(new Cursor(Cursor.HAND_CURSOR));
            rpContent[2].add(labelName[0]);

            rpContent[3].setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 5));
            rpContent[3].setPreferredSize(new Dimension(280, listCount[0] * 50));
            rpContent[3].setAutoscrolls(true);
            rpContent[3].setCursor(new Cursor(Cursor.HAND_CURSOR));
            rpContent[0].add(rpContent[3]);

            imageIcon[0].setPreferredSize(new Dimension(30, 30));
            imageIcon[0].setBorderSize(2);
            imageIcon[0].setIcon(new ImageIcon(string));
            rpContent[2].add(imageIcon[0]);

            for (int i = 5; i < 8; i++) {
                if (mang[i] != 0) {
                    roundPanel[i].setLayout(new FlowLayout(FlowLayout.LEFT, 35, 7));
                    roundPanel[i].setPreferredSize(new Dimension(280, 45));
                    roundPanel[i].setAutoscrolls(true);
                    roundPanel[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
                    int index = i;
                    roundPanel[i].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            roundPanelMousePressed(index);
                        }
                    });
                    rpContent[3].add(roundPanel[i]);
                }
            }
        }

        if (listCount[1] != 0) {
            rpContent[4].setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            rpContent[4].setPreferredSize(new Dimension(280, 45));
            rpContent[4].setAutoscrolls(true);
            rpContent[4].setCursor(new Cursor(Cursor.HAND_CURSOR));
            cate.add(rpContent[4]);

            rpContent[5].setLayout(new FlowLayout(FlowLayout.LEFT, 0, 7));
            rpContent[5].setPreferredSize(new Dimension(280, 45));
            rpContent[5].setAutoscrolls(true);
            rpContent[5].setCursor(new Cursor(Cursor.HAND_CURSOR));
            rpContent[5].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (numberContent != -1 && numberjpane != 1) {
//                    string = "img/icons/up.png";
                        imageIcon[numberjpane].setIcon(null);
                        imageIcon[numberjpane].setIcon(new ImageIcon(string));
                        pressDelay(numberContent, rpContent[numberContent].getHeight(), addContent);
                    }
                    totalHeight = (listCount[1] * 50) / 5;
                    if (rpContent[4].getHeight() == 45) {
                        numberjpane = 1;
                        pressDelay(4, rpContent[4].getHeight(), totalHeight);
                        numberContent = 4;
                        addContent = -totalHeight;
                    } else {
                        pressDelay(4, rpContent[4].getHeight(), -totalHeight);
                        numberContent = -1;
                    }
                }
            });
            rpContent[4].add(rpContent[5]);

            labelName[1].setPreferredSize(new Dimension(230, 30));
            labelName[1].setFont(new Font("Times New Roman", Font.PLAIN, 18));
            labelName[1].setText("-----*Quản lý số liệu");
            labelName[1].setHorizontalAlignment(SwingConstants.CENTER);
            labelName[1].setCursor(new Cursor(Cursor.HAND_CURSOR));
            rpContent[5].add(labelName[1]);

            rpContent[6].setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 5));
            rpContent[6].setPreferredSize(new Dimension(280, 50 * listCount[1]));
            rpContent[6].setAutoscrolls(true);
            rpContent[6].setCursor(new Cursor(Cursor.HAND_CURSOR));
            rpContent[4].add(rpContent[6]);

            imageIcon[1].setPreferredSize(new Dimension(30, 30));
            imageIcon[1].setBorderSize(2);
            imageIcon[1].setIcon(new ImageIcon(string));
            rpContent[5].add(imageIcon[1]);

            for (int i = 8; i < 10; i++) {
                if (mang[i] != 0) {
                    roundPanel[i].setLayout(new FlowLayout(FlowLayout.LEFT, 35, 7));
                    roundPanel[i].setPreferredSize(new Dimension(280, 45));
                    roundPanel[i].setAutoscrolls(true);
                    roundPanel[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
                    int index = i;
                    roundPanel[i].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            roundPanelMousePressed(index);
                        }
                    });
                    rpContent[6].add(roundPanel[i]);
                }
            }
        }

        if (listCount[2] != 0) {
            rpContent[7].setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            rpContent[7].setPreferredSize(new Dimension(280, 45));
            rpContent[7].setAutoscrolls(true);
            rpContent[7].setCursor(new Cursor(Cursor.HAND_CURSOR));
            cate.add(rpContent[7]);

            rpContent[8].setLayout(new FlowLayout(FlowLayout.LEFT, 0, 7));
            rpContent[8].setPreferredSize(new Dimension(280, 45));
            rpContent[8].setAutoscrolls(true);
            rpContent[8].setCursor(new Cursor(Cursor.HAND_CURSOR));
            rpContent[8].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (numberContent != -1 && numberjpane != 2) {
                        imageIcon[numberjpane].setIcon(null);
                        imageIcon[numberjpane].setIcon(new ImageIcon(string));
                        pressDelay(numberContent, rpContent[numberContent].getHeight(), addContent);
                    }
                    totalHeight = (listCount[2] * 50) / 5;
                    if (rpContent[7].getHeight() == 45) {
//                    string = "img/icons/down.png";
                        numberjpane = 2;
                        pressDelay(7, rpContent[7].getHeight(), totalHeight);
                        numberContent = 7;
                        addContent = -totalHeight;
                    } else {
                        pressDelay(7, rpContent[7].getHeight(), -totalHeight);
                        numberContent = -1;
                    }
                }
            });
            rpContent[7].add(rpContent[8]);

            labelName[2].setPreferredSize(new Dimension(230, 30));
            labelName[2].setFont(new Font("Times New Roman", Font.PLAIN, 18));
            labelName[2].setText("-----*Quản lý thông tin");
            labelName[2].setHorizontalAlignment(SwingConstants.CENTER);
            labelName[2].setCursor(new Cursor(Cursor.HAND_CURSOR));
            rpContent[8].add(labelName[2]);

            rpContent[9].setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 5));
            rpContent[9].setPreferredSize(new Dimension(280, listCount[2] * 50));
            rpContent[9].setAutoscrolls(true);
            rpContent[9].setCursor(new Cursor(Cursor.HAND_CURSOR));
            rpContent[7].add(rpContent[9]);

            imageIcon[2].setPreferredSize(new Dimension(30, 30));
            imageIcon[2].setBorderSize(2);
            imageIcon[2].setIcon(new ImageIcon("img/icons/down.png"));
            rpContent[8].add(imageIcon[2]);

            for (int i = 10; i < 15; i++) {
                if (mang[i] != 0) {
                    roundPanel[i].setLayout(new FlowLayout(FlowLayout.LEFT, 35, 7));
                    roundPanel[i].setPreferredSize(new Dimension(280, 45));
                    roundPanel[i].setAutoscrolls(true);
                    roundPanel[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
                    int index = i;
                    roundPanel[i].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            roundPanelMousePressed(index);
                        }
                    });
                    rpContent[9].add(roundPanel[i]);
                }
            }
        }

        for (int i = 1; i < roundPanel.length; i++) {
            if (mang[i] != 0) {
                imageAvatar[i].setPreferredSize(new Dimension(30, 30));
                imageAvatar[i].setBorderSize(2);
                imageAvatar[i].setIcon(new ImageIcon(String.format("img/icons/%02d.png", i)));
                roundPanel[i].add(imageAvatar[i]);

                jLabel[i].setFont(new Font("Times New Roman", Font.PLAIN, 18));
                jLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
                jLabel[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
                roundPanel[i].add(jLabel[i]);
            }
        }

        jLabel[1].setText("Bán hàng");
        jLabel[2].setText("Nhập hàng");
        jLabel[3].setText("Hóa đơn");
        jLabel[4].setText("Nhà kho");

        jLabel[5].setText("Sản phẩm");
        jLabel[6].setText("Loại sản phẩm");
        jLabel[7].setText("Công thức");

        jLabel[8].setText("Giảm giá");
        jLabel[9].setText("Thống kê");

        jLabel[10].setText("Khách hàng");
        jLabel[11].setText("Nhân viên");
        jLabel[12].setText("Tài khoản");
        jLabel[13].setText("Phân quyền");
        jLabel[14].setText("Nhà cung cấp");
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
            //       cate.setBackground(new Color(0, 0, 0));
            rpContent[0].setBackground(new Color(79, 194, 53));
            rpContent[3].setBackground(new Color(79, 194, 53));
            rpContent[4].setBackground(new Color(79, 194, 53));
            rpContent[6].setBackground(new Color(79, 194, 53));
            rpContent[7].setBackground(new Color(79, 194, 53));
            rpContent[9].setBackground(new Color(79, 194, 53));
            roundPanelBG = new Color(240, 240, 240);
            roundPanelColor = new Color(240, 240, 240);
            roundPanelColorOver = new Color(68, 150, 60);
            imageAvatarFG = new Color(225, 200, 73, 255);
            labelBG = new Color(51, 51, 51);
            labelFG = new Color(25, 25, 25);
            currentBtnBG = new Color(68, 150, 60);
            imageAvatarIcon = new Color(79, 194, 53);
        } else {
            home.setBackground(new Color(35, 166, 97));
            north.setBackground(new Color(70, 67, 67));
            center.setBackground(new Color(35, 166, 97));
            west.setBackground(new Color(35, 166, 97));
            east.setBackground(new Color(70, 67, 67));
            info.setBackground(new Color(70, 67, 67));
            rpContent[0].setBackground(new Color(70, 67, 67));
            rpContent[3].setBackground(new Color(70, 67, 67));
            rpContent[4].setBackground(new Color(70, 67, 67));
            rpContent[6].setBackground(new Color(70, 67, 67));
            rpContent[7].setBackground(new Color(70, 67, 67));
            rpContent[9].setBackground(new Color(70, 67, 67));
            cate_frame.setBackground(new Color(70, 67, 67));
            cate.setBackground(new Color(70, 67, 67));
            roundPanelBG = new Color(70, 67, 67);
            roundPanelColor = new Color(70, 67, 67);
            roundPanelColorOver = new Color(35, 166, 97);
            imageAvatarFG = new Color(240, 240, 240, 255);
            labelBG = new Color(51, 51, 51);
            labelFG = new Color(240, 240, 240);
            currentBtnBG = new Color(35, 166, 97);
            imageAvatarIcon = new Color(240, 240, 240, 255);
        }
        for (int i = 1; i < jLabel.length; i++) {
            roundPanel[i].setBackground(roundPanelBG);
            roundPanel[i].setColor(roundPanelColor);
            roundPanel[i].setColorOver(roundPanelColorOver);
            imageAvatar[i].setForeground(imageAvatarFG);
            jLabel[i].setBackground(labelBG);
            jLabel[i].setForeground(labelFG);
        }
//        for (int i = 0; i < 3; i++) {
////            imageIcon[i].setBackground(Color.BLUE);
////            imageIcon[i].setOpaque(true);
//        }
        if (currentBtn != null)
            currentBtn.setBackground(currentBtnBG);
    }

    private void roundPanelMousePressed(int index) {
        Active(roundPanel[index]);
        JPanel panel = switch (index) {
            case 1 -> new SaleGUI(account.getStaffID());
            case 2 -> new IngredientGUI(decentralization.getIsSupplier(), account.getStaffID());
            case 3 -> new BillGUI();
            case 4 -> new WarehousesGUI(decentralization.getIsWarehouses());
            case 5 -> new ProductGUI(decentralization.getIsProduct());
            case 6 -> new CategoryGUI(decentralization.getIsDecentralization());
            case 7 -> new RecipeGUI(decentralization.getIsRecipe());
            case 8 -> new DiscountGUI(decentralization.getIsDiscount());
            case 9 -> new StatisticGUI(decentralization.getIsDecentralization());
            case 10 -> new CustomerGUI(decentralization.getIsCustomer());
            case 11 -> new StaffGUI(decentralization.getIsStaff());
            case 12 -> new AccountGUI(decentralization.getIsAccount());
            case 13 -> new DecentralizationGUI(decentralization.getIsDecentralization());
            case 14 -> new SupplierGUI(decentralization.getIsSupplier());
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
        int message = JOptionPane.showOptionDialog(null,
            "Bạn có chắc chắn muốn thoát?",
            "Thoát",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"Thoát", "Huỷ"},
            "Thoát");
        if (message == JOptionPane.YES_OPTION) {
            exit.setColor(new Color(0xFD1111));
            this.dispose();
            CafeManagement.loginGUI.setVisible(true);
        }
    }

    public void setTime() {
        lbTime.setText(Day.now());
    }

    public void pressDelay(int index, int height, int add) {
        Timer timer = new Timer(1, new ActionListener() {
            private int counter = 0;
            private int angle = 0;
            private int coordinates = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                counter++;
                rpContent[index].setPreferredSize(new Dimension(280, height + counter * add));
                rpContent[index].revalidate();
                rpContent[index].repaint();
                if (add > 0) coordinates = 36;
                else coordinates = 72;
                angle = angle + coordinates;
                imageIcon[numberjpane].setIcon(rotateIcon(new ImageIcon(string), angle));
                if (counter == 5) {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.start();
    }

    private ImageIcon rotateIcon(ImageIcon icon, int angle) {
        int w = icon.getIconWidth();
        int h = icon.getIconHeight();
        int type = BufferedImage.TYPE_INT_ARGB;

        BufferedImage img = new BufferedImage(w, h, type);
        Graphics2D g2 = img.createGraphics();
        g2.rotate(Math.toRadians(angle), (double) w / 2, (double) h / 2);
        icon.paintIcon(null, g2, 0, 0);
        g2.dispose();

        return new ImageIcon(img);
    }

    private void checkDiscountNotAvailable() {
        DiscountBLL discountBLL = new DiscountBLL();
        try {
            discountBLL.checkDateDiscount(Day.parseDay(Day.now().split(" - ")[0]));
        } catch (Exception ignored){

        }
    }

    private void newLoginSession() {
        JOptionPane.showMessageDialog(this, "Phiên đăng nhập hết hạn.\nVui lòng đăng nhập lại.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
        new LoginGUI();
    }
}
