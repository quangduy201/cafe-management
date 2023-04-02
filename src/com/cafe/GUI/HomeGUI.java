package com.cafe.GUI;

import com.cafe.BLL.AccountBLL;
import com.cafe.BLL.DecentralizationBLL;
import com.cafe.BLL.StaffBLL;
import com.cafe.DTO.Account;
import com.cafe.DTO.Decentralization;
import com.cafe.DTO.Staff;
import com.cafe.custom.Button;
import com.cafe.custom.Header;
import com.cafe.custom.ImageAvatar;
import com.cafe.custom.RoundPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class HomeGUI extends JFrame {
    private Account account;
    private Staff staff;
    private Decentralization decentralization;
    private int mang[] = new int[14];
    private JPanel currentBtn;
    private Button exit;
    private Button minimize;
    private Header header2;
    private RoundPanel home;
    private RoundPanel fram_cate;
    private Header north;
    private RoundPanel west;
    private RoundPanel east;
    private RoundPanel info;
    private RoundPanel cate;
    private RoundPanel function;
    private JPanel center;
    private JLabel jLabel[] = new JLabel[16];
    private JLabel lb_Time;
    private RoundPanel roundPanel[] = new RoundPanel[15];
    private ImageAvatar imageAvatar[] = new ImageAvatar[15];
    private int mouseX, mouseY;

    public HomeGUI(Account account) {
        this.account = account;
        decentralization = new DecentralizationBLL()
            .searchDecentralization("DECENTRALIZATION_ID = '" + this.account.getDecentralizationID() + "'")
            .get(0);
        staff = new StaffBLL()
            .searchStaffs("STAFF_ID = '" + this.account.getStaffID() + "'")
            .get(0);
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
        initComponents();
        set_Time();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
        jLabel[14].setText("Tên: " + staff.getName());
        jLabel[15].setText("Vai Trò: " + decentralization.getDecentralizationName());
    }

    public static void main(String[] args) {
        AccountBLL accountBLL = new AccountBLL();
        List<Account> accountList = accountBLL.searchAccounts("USERNAME = '" + "dungboi" + "'", "PASSWD = '" + "123" + "'");
        Account account = accountList.get(0);
        new HomeGUI(account).setVisible(true);
    }

    private void initComponents() {

        home = new RoundPanel();
        north = new Header();
        west = new RoundPanel();
        east = new RoundPanel();
        info = new RoundPanel();
        cate = new RoundPanel();
        center = new JPanel();

        fram_cate = new RoundPanel();
        function = new RoundPanel();

        header2 = new Header();
        exit = new Button();
        minimize = new Button();
        lb_Time = new JLabel();

        for (int i = 1; i < roundPanel.length; i++) {
            roundPanel[i] = new RoundPanel();
            imageAvatar[i] = new ImageAvatar();
            jLabel[i] = new JLabel();
        }
        jLabel[15] = new JLabel();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setBackground(new Color(35, 166, 97));

        home.setLayout(new BorderLayout(10, 10));
        home.setBackground(new Color(35, 166, 97));
        setContentPane(home);
//        header2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
//            public void mouseDragged(java.awt.event.MouseEvent evt) {
//                header2MouseDragged(evt);
//            }
//        });
//        header2.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mousePressed(java.awt.event.MouseEvent evt) {
//                header2MousePressed(evt);
//            }
//        });
        north.setLayout(null);
        north.setPreferredSize(new Dimension(1300, 50));
        north.setBackground(new Color(51, 51, 51));
        home.add(north, BorderLayout.NORTH);

        center.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        center.setPreferredSize(new Dimension(1350, 710));
        center.setBackground(new Color(35, 166, 97));
        home.add(center, BorderLayout.CENTER);

        west.setLayout(new BorderLayout(0, 10));
        west.setPreferredSize(new Dimension(300, 700));
        west.setBackground(new Color(35, 166, 97));
        center.add(west);

        east.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        east.setPreferredSize(new Dimension(1020, 700));
        east.setBackground(new Color(51, 51, 51));
        east.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(east);


        info.setLayout(null);
        info.setPreferredSize(new Dimension(300, 80));
        info.setBackground(new Color(51, 51, 51));
        info.setAlignmentX(Component.CENTER_ALIGNMENT);
        west.add(info, BorderLayout.NORTH);


        fram_cate.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 25));
        fram_cate.setPreferredSize(new Dimension(300, 600));
        fram_cate.setBackground(new Color(51, 51, 51));
        fram_cate.setAlignmentX(Component.CENTER_ALIGNMENT);
        west.add(fram_cate, BorderLayout.CENTER);


        cate.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 3));
        cate.setPreferredSize(new Dimension(280, 560));
        cate.setBackground(new Color(51, 51, 51));
        cate.setAlignmentX(Component.CENTER_ALIGNMENT);
        fram_cate.add(cate);

        exit.setBorderPainted(false);
        exit.setText("X");
        exit.setBounds(1290, 10, 50, 30);
        exit.setFocusPainted(false);
        exit.setBackground(new Color(0xFD1111));
        exit.setFont(new Font("Public Sans", Font.BOLD, 15));
        exit.setRadius(15);
        north.add(exit);
        exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                exit();
            }
        });
        exit.setColor(new Color(0xFD1111));
        exit.setColorOver(new Color(0xB04848));
        exit.setColorClick(new Color(0xE79292));


        minimize.setBorderPainted(false);
        minimize.setText("-");
        minimize.setBounds(1235, 10, 50, 30);
        minimize.setFocusPainted(false);
        minimize.setBackground(new Color(0xF3F0F0));
        minimize.setFont(new Font("Public Sans", Font.BOLD, 15));
        minimize.setRadius(15);
        north.add(minimize);
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                minimize();
            }
        });
        minimize.setColor(new Color(0xF3F0F0));
        minimize.setColorOver(new Color(0xC4BDBD));
        minimize.setColorClick(new Color(0x676161));

        lb_Time.setBounds(80, 10, 200, 30);
        lb_Time.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lb_Time.setForeground(new Color(255, 255, 255));
        lb_Time.setText("jLabel2");
        north.add(lb_Time);

        imageAvatar[14].setForeground(new Color(255, 255, 255));
        imageAvatar[14].setBorderSize(2);
        imageAvatar[14].setBounds(20, 10, 60, 60);
        imageAvatar[14].setIcon(new ImageIcon(("img/bell-boy.png")));
        info.add(imageAvatar[14]);

        jLabel[14].setFont(new Font("Tahoma", Font.PLAIN, 12));
        jLabel[14].setForeground(new Color(255, 255, 255));
        jLabel[14].setBounds(100, 15, 200, 20);
        jLabel[14].setText("Name");
        info.add(jLabel[14]);

        jLabel[15].setFont(new Font("Tahoma", Font.PLAIN, 12));
        jLabel[15].setBounds(100, 45, 150, 20);
        jLabel[15].setForeground(new Color(255, 255, 255));
        jLabel[15].setText("Vai trò:");
        info.add(jLabel[15]);


        for (int i = 1; i < roundPanel.length - 1; i++) {
            if (mang[i] != 0) {
                roundPanel[i].setLayout(new FlowLayout(FlowLayout.LEFT, 30, 5));
                roundPanel[i].setPreferredSize(new Dimension(270, 40));
                roundPanel[i].setBackground(new Color(51, 51, 51));
                roundPanel[i].setAutoscrolls(true);
                cate.add(roundPanel[i]);
            }
        }

        roundPanel[1].addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                roundPanel1MouseClicked();
            }
        });

        roundPanel[2].addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                roundPanel2MouseClicked();
            }
        });

        roundPanel[3].addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                roundPanel3MouseClicked();
            }
        });

        roundPanel[4].addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                roundPanel4MouseClicked();
            }
        });

        roundPanel[5].addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                roundPanel5MouseClicked();
            }
        });

        roundPanel[6].addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                roundPanel6MouseClicked();
            }
        });

        roundPanel[7].addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                roundPanel7MouseClicked();
            }
        });

        roundPanel[8].addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                roundPanel8MouseClicked();
            }
        });


        roundPanel[9].addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                roundPanel9MouseClicked();
            }
        });


        roundPanel[10].addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                roundPanel10MouseClicked();
            }
        });


        roundPanel[11].addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                roundPanel11MouseClicked();
            }
        });

        roundPanel[12].addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                roundPanel12MouseClicked();
            }
        });

        roundPanel[13].addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                roundPanel13MouseClicked();
            }
        });

        for (int i = 1; i < imageAvatar.length - 1; i++) {
            if (mang[i] != 0) {
                imageAvatar[i].setForeground(new Color(255, 255, 255));
                imageAvatar[i].setPreferredSize(new Dimension(30, 30));
                imageAvatar[i].setBorderSize(2);
                imageAvatar[i].setIcon(new javax.swing.ImageIcon(("img/0" + i + ".png")));
                roundPanel[i].add(imageAvatar[i]);

                jLabel[i].setBackground(new Color(51, 51, 51));
                jLabel[i].setFont(new Font("Times New Roman", Font.PLAIN, 18));
                jLabel[i].setForeground(new Color(255, 255, 255));
                jLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
                jLabel[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
                roundPanel[i].add(jLabel[i]);
            }
        }

        jLabel[1].setText("Bán hàng");
        jLabel[2].setText("Sản phẩm");
        jLabel[3].setText("Loại sản phẩm");
        jLabel[4].setText("Công Thức");
        ;
        jLabel[5].setText("Nhập hàng");
        jLabel[6].setText("Hóa đơn");
        jLabel[7].setText("Nhà kho");
        ;
        jLabel[8].setText("Thống kê");
        jLabel[9].setText("Tài khoản");
        jLabel[10].setText("Nhân viên");
        jLabel[11].setText("Khách hàng");
        jLabel[12].setText("Giảm giá");
        jLabel[13].setText("Phân quyền");


        function.setPreferredSize(new Dimension(1005, 680));
        function.setBackground(new Color(51, 51, 51));
        pack();
        setLocationRelativeTo(null);
    }

    private void roundPanel1MouseClicked() {
        roundPanel[1].setBackground(new Color(35, 166, 97));
        Active(roundPanel[1]);
        OpenChildForm(new SaleGUI());
    }

    private void roundPanel2MouseClicked() {
        roundPanel[2].setBackground(new Color(35, 166, 97));
        Active(roundPanel[2]);
        OpenChildForm(new ProductGUI());
    }

    private void roundPanel3MouseClicked() {
        roundPanel[3].setBackground(new Color(35, 166, 97));
        Active(roundPanel[3]);
        OpenChildForm(new CategoryGUI());
    }

    private void roundPanel4MouseClicked() {
        roundPanel[4].setBackground(new Color(25, 25, 25));
        Active(roundPanel[4]);
        OpenChildForm(new RecipeGUI());
    }

    private void roundPanel5MouseClicked() {
        roundPanel[5].setBackground(new Color(35, 166, 97));
        Active(roundPanel[5]);
//        OpenChildForm(new ImportGUI());
        OpenChildForm(new SupplierGUI());
    }

    private void roundPanel6MouseClicked() {
        roundPanel[6].setBackground(new Color(35, 166, 97));
        Active(roundPanel[6]);
        OpenChildForm(new BillGUI());
    }

    private void roundPanel7MouseClicked() {
        roundPanel[7].setBackground(new Color(35, 166, 97));
        Active(roundPanel[7]);
        OpenChildForm(new WarehousesGUI());
    }

    private void roundPanel8MouseClicked() {
        roundPanel[8].setBackground(new Color(35, 166, 97));
        Active(roundPanel[8]);
        OpenChildForm(new StatisticGUI());
    }

    private void roundPanel9MouseClicked() {
        roundPanel[9].setBackground(new Color(35, 166, 97));
        Active(roundPanel[9]);
        OpenChildForm(new AccountGUI());
    }

    private void roundPanel10MouseClicked() {
        roundPanel[10].setBackground(new Color(35, 166, 97));
        Active(roundPanel[10]);
        OpenChildForm(new StaffGUI());
    }

    private void roundPanel11MouseClicked() {
        roundPanel[11].setBackground(new Color(35, 166, 97));
        Active(roundPanel[11]);
        OpenChildForm(new CustomerGUI());
    }

    private void roundPanel12MouseClicked() {
        roundPanel[12].setBackground(new Color(35, 166, 97));
        Active(roundPanel[12]);
        OpenChildForm(new DiscountGUI());
    }

    private void roundPanel13MouseClicked() {
        roundPanel[13].setBackground(new Color(35, 166, 97));
        Active(roundPanel[13]);
        OpenChildForm(new DecentralizationGUI());
    }

    private void Disable() {
        if (currentBtn != null)
            currentBtn.setBackground(new Color(51, 51, 51));
    }

    private void Active(JPanel btn) {
        Disable();
        currentBtn = btn;
        currentBtn.setBackground(new Color(35, 166, 97));
    }

    private void OpenChildForm(JPanel panel) {
        function.removeAll();
//        function.setLayout(new BorderLayout());
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

    private void set_Time() {
        LocalDate date = LocalDate.now(); // Lấy ngày tháng năm hiện tại
        String dateString = String.valueOf(date);
        String[] dateArray = dateString.split("-");
        String day = dateArray[2];
        String month = dateArray[1];
        String year = dateArray[0];
        lb_Time.setText(day + "-" + month + "-" + year + " : ");
        Thread a = new Thread(() -> {
            try {
                while (true) {
                    Date now = new Date();
                    SimpleDateFormat format = new SimpleDateFormat();
                    format.applyPattern("hh:mm:ss aa");
                    String time = format.format(now);
                    lb_Time.setText(day + "-" + month + "-" + year + " : " + time);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        a.start();
    }
}
