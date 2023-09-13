package com.cafe.GUI;

import com.cafe.BLL.DecentralizationBLL;
import com.cafe.BLL.DecentralizationDetailBLL;
import com.cafe.BLL.StaffBLL;
import com.cafe.DTO.Account;
import com.cafe.DTO.Decentralization;
import com.cafe.DTO.DecentralizationDetail;
import com.cafe.DTO.Staff;
import com.cafe.custom.Button;
import com.cafe.custom.*;
import com.cafe.main.CafeManagement;
import com.cafe.utils.Day;
import com.cafe.utils.Resource;
import com.cafe.utils.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.function.BiConsumer;

public class HomeGUI extends JFrame {
    int currentPanel = 1;
    private Account account;
    private Staff staff;
    private Decentralization decentralization;
    private java.util.List<DecentralizationDetail> decentralizationDetails;
    private int[] mang;
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
    private int numberContent;
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
        this.account = account;
        getUser();
        initComponents();
        changeTheme();
    }

    public static void main(String[] args) {
        CafeManagement.start();
        CafeManagement.loginGUI.dispose();
        CafeManagement.homeGUI.setVisible(true);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
        getUser();
        System.gc();
        initLeftMenu();
        selectRoundPanel(1);
        changeTheme();
    }


    private void getUser() {
        decentralizationDetails = new DecentralizationDetailBLL()
            .searchDecentralizationDetail("DECENTRALIZATION_ID = '" + account.getDecentralizationID() + "'");
        decentralization = new DecentralizationBLL()
            .searchDecentralization("DECENTRALIZATION_ID = '" + account.getDecentralizationID() + "'")
            .get(0);
        staff = new StaffBLL()
            .searchStaffs("STAFF_ID = '" + account.getStaffID() + "'")
            .get(0);
        mang = new int[15];
        for (int i = 0; i < 13; i++) {
            if(decentralizationDetails.get(i).isCanADD() ||
                decentralizationDetails.get(i).isCanEDIT() ||
                decentralizationDetails.get(i).isCanREMOVE()) {
                System.out.println(i);
                switch (decentralizationDetails.get(i).getModuleID()) {
                    case "MOD01" ->  mang[1] = 1;
                    case "MOD02" ->  mang[2] = 1;
                    case "MOD03" ->  mang[3] = 1;
                    case "MOD04" ->  mang[4] = 1;
                    case "MOD05" ->  mang[5] = 1;
                    case "MOD06" ->  mang[6] = 1;
                    case "MOD07" ->  mang[7] = 1;
                    case "MOD08" ->  mang[8] = 1;
                    case "MOD09" ->  mang[10] = 1;
                    case "MOD10" ->  mang[11] = 1;
                    case "MOD11" ->  mang[12] = 1;
                    case "MOD12" ->  {
                        mang[13] = 1;
                        mang[9] = 1;
                    }
                    case "MOD13" ->  mang[14] = 1;
                }
            }
        }
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

        roundPanel = new ProductPanel[15];
        for (int i = 0; i < roundPanel.length; i++) {
            roundPanel[i] = new ProductPanel();
            imageAvatar[i] = new ImageAvatar();
            jLabel[i] = new JLabel();
        }

        rpContent = new RoundPanel[15];
        for (int i = 0; i < rpContent.length; i++) {
            rpContent[i] = new RoundPanel();
            labelName[i] = new JLabel();
        }

        imageIcon = new ImageAvatar[15];
        for (int i = 0; i < imageIcon.length; i++) {
            imageIcon[i] = new ImageAvatar();
        }

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
        setUndecorated(true);

        home.setLayout(new BorderLayout(10, 10));
        setContentPane(home);

        // home.north
        north.setLayout(null);
        north.setPreferredSize(new Dimension(1300, 50));
        home.add(north, BorderLayout.NORTH);

        lbTime.setBounds(80, 10, 200, 30);
        lbTime.setFont(new Font("Public Sans", Font.PLAIN, 15));
        lbTime.setForeground(new Color(255, 255, 255));
        north.add(lbTime);

        Settings.theme = Settings.getTheme(Settings.loadTheme());
        assert Settings.theme != null;
        if (!Settings.theme.isDark())
            themeButton.setSelected(true);
        themeButton.setBounds(1150, 10, 66, 34);
        themeButton.addActionListener(e -> {
            changeTheme();
            Settings.saveTheme();
        });
        themeButton.setOnColor(new Color(0xFCE797));
        themeButton.setOffColor(new Color(0x504C4C));
        themeButton.setOnIcon(Resource.loadImageIcon("img/icons/sun.png"));
        themeButton.setOffIcon(Resource.loadImageIcon("img/icons/moon.png"));
        north.add(themeButton);

        BiConsumer<Button, java.util.List<Object>> configButton = (button, properties) -> {
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setFont(new Font("Public Sans", Font.BOLD, 15));
            button.setRadius(15);
            button.setBorderColor(Color.BLACK);
            button.setForeground(Color.BLACK);
            button.setText((String) properties.get(0));
            button.setBounds((Integer) properties.get(1), (Integer) properties.get(2), 50, 30);
            button.setColor((Color) properties.get(3));
            button.setColorOver((Color) properties.get(4));
            button.setColorClick((Color) properties.get(5));
            button.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    ((Runnable) properties.get(6)).run();
                }
            });
            north.add(button);
        };
        configButton.accept(minimize, java.util.List.of("-", 1235, 10, new Color(0xF3F0F0), new Color(0xC4BDBD), new Color(0x676161), (Runnable) this::minimize));
        configButton.accept(exit, java.util.List.of("X", 1290, 10, new Color(0xFD1111), new Color(0xB04848), new Color(0xE79292), (Runnable) this::exit));

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
    private boolean isListening;

    public void initLeftMenu() {
        listCount[0] = 0;
        listCount[1] = 0;
        listCount[2] = 0;
        numberContent = -1;
        isListening = true;

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

        roundPanel = new ProductPanel[15];
        for (int i = 0; i < roundPanel.length; i++) {
            roundPanel[i] = new ProductPanel();
            imageAvatar[i] = new ImageAvatar();
            jLabel[i] = new JLabel();
        }

        rpContent = new RoundPanel[15];
        for (int i = 0; i < rpContent.length; i++) {
            rpContent[i] = new RoundPanel();
            labelName[i] = new JLabel();
        }

        imageIcon = new ImageAvatar[15];
        for (int i = 0; i < imageIcon.length; i++) {
            imageIcon[i] = new ImageAvatar();
        }


        imageAvatar[0].setForeground(new Color(255, 255, 255));
        imageAvatar[0].setBorderSize(2);
        imageAvatar[0].setBounds(20, 10, 60, 60);
        imageAvatar[0].setIcon(Resource.loadImageIcon("img/icons/bell-boy.png"));
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
                        selectRoundPanel(index);
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
                    if (isListening) {
                        isListening = false;
                        if (numberContent != -1 && numberjpane != 0) {
//                    string = "img/icons/up.png";
                            imageIcon[numberjpane].setIcon(null);
                            imageIcon[numberjpane].setIcon(Resource.loadImageIcon(string));
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
                        isListening = true;
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
            imageIcon[0].setIcon(Resource.loadImageIcon(string));
            rpContent[2].add(imageIcon[0]);

            for (int i = 5; i < 8; i++) {
                if ((decentralizationDetails.get(i - 1).isCanADD() ||
                    decentralizationDetails.get(i - 1).isCanEDIT() ||
                    decentralizationDetails.get(i - 1).isCanREMOVE())) {
                    roundPanel[i].setLayout(new FlowLayout(FlowLayout.LEFT, 35, 7));
                    roundPanel[i].setPreferredSize(new Dimension(280, 45));
                    roundPanel[i].setAutoscrolls(true);
                    roundPanel[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
                    int index = i;
                    roundPanel[i].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            selectRoundPanel(index);
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
                    if (isListening) {
                        isListening = false;
                        if (numberContent != -1 && numberjpane != 1) {
//                    string = "img/icons/up.png";
                            imageIcon[numberjpane].setIcon(null);
                            imageIcon[numberjpane].setIcon(Resource.loadImageIcon(string));
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
                        isListening = true;
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
            imageIcon[1].setIcon(Resource.loadImageIcon(string));
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
                            selectRoundPanel(index);
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
                    if (isListening) {
                        isListening = false;
                        if (numberContent != -1 && numberjpane != 2) {
                            imageIcon[numberjpane].setIcon(null);
                            imageIcon[numberjpane].setIcon(Resource.loadImageIcon(string));
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
                        isListening = true;
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
            imageIcon[2].setIcon(Resource.loadImageIcon("img/icons/down.png"));
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
                            selectRoundPanel(index);
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
                imageAvatar[i].setIcon(Resource.loadImageIcon(String.format("img/icons/%02d.png", i)));
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
        String theme;
        Color rpContentBG, rpFolderBG, rpFolderFG, roundPanelBG, roundPanelColor, roundPanelColorOver, imageAvatarFG, labelBG, labelFG, currentBtnBG;
        if (themeButton.isSelected()) {
            theme = "light";
            home.setBackground(new Color(240, 240, 240));
            north.setBackground(new Color(35, 166, 97));
            center.setBackground(new Color(240, 240, 240));
            west.setBackground(new Color(240, 240, 240));
            east.setBackground(new Color(70, 67, 67));
            info.setBackground(new Color(79, 194, 53));
            cate_frame.setBackground(new Color(79, 194, 53));
            cate.setBackground(new Color(79, 194, 53));
            rpContentBG = new Color(79, 194, 53);
            rpFolderBG = new Color(240, 240, 240);
            rpFolderFG = new Color(25, 25, 25);
            roundPanelBG = new Color(240, 240, 240);
            roundPanelColor = new Color(240, 240, 240);
            roundPanelColorOver = new Color(68, 150, 60);
            imageAvatarFG = new Color(225, 200, 73, 255);
            labelBG = new Color(51, 51, 51);
            labelFG = new Color(25, 25, 25);
            currentBtnBG = new Color(68, 150, 60);
            imageAvatarIcon = new Color(79, 194, 53);
        } else {
            theme = "dark";
            home.setBackground(new Color(35, 166, 97));
            north.setBackground(new Color(70, 67, 67));
            center.setBackground(new Color(35, 166, 97));
            west.setBackground(new Color(35, 166, 97));
            east.setBackground(new Color(70, 67, 67));
            info.setBackground(new Color(70, 67, 67));
            cate_frame.setBackground(new Color(70, 67, 67));
            cate.setBackground(new Color(70, 67, 67));
            rpContentBG = new Color(70, 67, 67);
            rpFolderBG = new Color(60, 63, 65);
            rpFolderFG = new Color(240, 240, 240);
            roundPanelBG = new Color(70, 67, 67);
            roundPanelColor = new Color(70, 67, 67);
            roundPanelColorOver = new Color(35, 166, 97);
            imageAvatarFG = new Color(240, 240, 240, 255);
            labelBG = new Color(51, 51, 51);
            labelFG = new Color(240, 240, 240);
            currentBtnBG = new Color(35, 166, 97);
            imageAvatarIcon = new Color(240, 240, 240, 255);
        }
        Settings.applyTheme(theme);
        selectRoundPanel(currentPanel);
        for (int i = 1; i < jLabel.length; i++) {
            roundPanel[i].setBackground(roundPanelBG);
            roundPanel[i].setColor(roundPanelColor);
            roundPanel[i].setColorOver(roundPanelColorOver);
            imageAvatar[i].setForeground(imageAvatarFG);
            jLabel[i].setBackground(labelBG);
            jLabel[i].setForeground(labelFG);
        }
//        for (int i = 0; i < 3; i++) {
//            imageIcon[i].setBackground(Color.WHITE);
//            imageIcon[i].setOpaque(true);
//        }
        rpContent[0].setBackground(rpContentBG);
        rpContent[3].setBackground(rpContentBG);
        rpContent[4].setBackground(rpContentBG);
        rpContent[6].setBackground(rpContentBG);
        rpContent[7].setBackground(rpContentBG);
        rpContent[9].setBackground(rpContentBG);
        rpContent[2].setBackground(rpFolderBG);
        rpContent[5].setBackground(rpFolderBG);
        rpContent[8].setBackground(rpFolderBG);
        labelName[0].setForeground(rpFolderFG);
        labelName[1].setForeground(rpFolderFG);
        labelName[2].setForeground(rpFolderFG);
        if (currentBtn != null)
            currentBtn.setBackground(currentBtnBG);
    }

    private void selectRoundPanel(int index) {
        Active(roundPanel[index]);
        JPanel panel = switch (index) {
            case 1 -> new SaleGUI(account.getStaffID());
            case 2 -> new IngredientGUI(decentralizationDetails.get(1), account.getStaffID());
            case 3 -> new BillGUI();
            case 4 -> new WarehousesGUI(decentralizationDetails.get(3));
            case 5 -> new ProductGUI(decentralizationDetails.get(4));
            case 6 -> new CategoryGUI(decentralizationDetails.get(5));
            case 7 -> new RecipeGUI(decentralizationDetails.get(6));
            case 8 -> new DiscountGUI(decentralizationDetails.get(7));
            case 9 -> new StatisticGUI();
            case 10 -> new CustomerGUI(decentralizationDetails.get(8));
            case 11 -> new StaffGUI(decentralizationDetails.get(9));
            case 12 -> new AccountGUI(decentralizationDetails.get(10));
            case 13 -> new DecentralizationGUI(decentralizationDetails.get(11));
            case 14 -> new SupplierGUI(decentralizationDetails.get(12));
            default -> null;
        };
        OpenChildForm(panel);
        currentPanel = index;
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
            for (int i = 0; i < 14; ++i) {
                roundPanel[i].removeAll();
                roundPanel[i].repaint();
                roundPanel[i].revalidate();
            }
            for (int i = 0; i < 10; ++i) {
                rpContent[i].removeAll();
                rpContent[i].repaint();
                rpContent[i].revalidate();
            }
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
                imageIcon[numberjpane].setIcon(rotateIcon(Resource.loadImageIcon(string), angle));
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
}
