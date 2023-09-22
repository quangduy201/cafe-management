package com.cafe.GUI;

import com.cafe.BLL.AccountBLL;
import com.cafe.DTO.Account;
import com.cafe.custom.RoundPanel;
import com.cafe.main.CafeManagement;
import com.cafe.utils.Password;
import com.cafe.utils.Resource;
import com.cafe.utils.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class LoginGUI extends JFrame {
    ImageIcon logo = Resource.loadImageIcon("img/logo_cafe.png");
    JPanel contentPane = new JPanel();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JPanel panel4 = new JPanel();
    JPanel panel5 = new JPanel();
    JPanel panel6 = new JPanel();
    JPanel panel7 = new JPanel();
    JTextField textField = new JTextField(20);
    JPasswordField passwordField = new JPasswordField(20);
    JLabel brandName = new JLabel();
    JLabel jLabel1 = new JLabel(logo);
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JButton button = new JButton();

    JLabel lbPassWord = new JLabel();

    RoundPanel rpPassword = new RoundPanel();
    JButton exit = new JButton();
    JButton minimize = new JButton();

    public LoginGUI() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);

        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(new Color(0xFFFFFF));
        setContentPane(contentPane);

        panel1.setLayout(null);
        panel1.setPreferredSize(new Dimension(700, 80));
        panel1.setBackground(new Color(0xFFFFFF));
        panel1.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, new Color(35, 166, 97)));
        contentPane.add(panel1, BorderLayout.NORTH);

        panel2.setLayout(new GridBagLayout());
        panel2.setPreferredSize(new Dimension(300, 410));
        panel2.setBackground(new Color(35, 166, 97));
        panel2.setBorder(BorderFactory.createMatteBorder(10, 0, 0, 0, new Color(0xFFFFFF)));
        contentPane.add(panel2, BorderLayout.WEST);

        panel3.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel3.setPreferredSize(new Dimension(400, 410));
        panel3.setBackground(new Color(0xFFFFFF));
        panel3.setBorder(BorderFactory.createMatteBorder(50, 0, 200, 0, new Color(0xFFFFFF)));
        panel3.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(panel3, BorderLayout.EAST);

        panel4.setLayout(new GridBagLayout());
        panel4.setPreferredSize(new Dimension(300, 100));
        panel4.setMaximumSize(new Dimension(300, 100));
        panel4.setMinimumSize(new Dimension(300, 150));
        panel4.setBackground(new Color(0xFFFFFF));
        panel3.add(panel5);
        panel3.add(panel4);

        panel5.setPreferredSize(new Dimension(300, 70));
        panel5.setMaximumSize(new Dimension(300, 70));
        panel5.setMinimumSize(new Dimension(300, 70));
        panel5.setBackground(new Color(0xFFFFFF));
        panel5.add(jLabel2);


        panel6.setLayout(new BoxLayout(panel6, BoxLayout.Y_AXIS));
        panel6.setPreferredSize(new Dimension(200, 40));
        panel6.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 0, new Color(0xFFFFFF)));
        panel6.add(textField);

        panel7.setLayout(new BoxLayout(panel7, BoxLayout.Y_AXIS));
        panel7.setPreferredSize(new Dimension(200, 40));
        panel7.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 0, new Color(0xFFFFFF)));
        panel7.add(passwordField);

        brandName.setText("King Cafe");
        brandName.setHorizontalAlignment(JLabel.CENTER);
        brandName.setFont(new Font("open sans", Font.BOLD, 50));
        brandName.setForeground(new Color(35, 166, 97));
        brandName.setBounds(200, 0, 300, 70);
        panel1.add(brandName);

        Image imgScaled = logo.getImage().getScaledInstance(273, 267, Image.SCALE_DEFAULT | Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScaled);
        jLabel1.setIcon(scaledIcon);
        panel2.add(jLabel1);

        jLabel2.setText("Login");
        jLabel2.setHorizontalAlignment(JLabel.CENTER);
        jLabel2.setFont(new Font("open sans", Font.BOLD, 40));
        jLabel2.setForeground(new Color(35, 166, 97));

        jLabel3.setText("Username");
        jLabel3.setHorizontalAlignment(JLabel.CENTER);
        jLabel3.setFont(new Font("open sans", Font.BOLD, 18));

        jLabel4.setText("Password");
        jLabel4.setHorizontalAlignment(JLabel.CENTER);
        jLabel4.setFont(new Font("open sans", Font.BOLD, 18));

        textField.setText("Username");
        textField.setBackground(new Color(0xffffff));
        textField.setForeground(null);
        textField.setFont(new Font("open sans", Font.PLAIN, 15));
        textField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent ignoredEvt) {
                textFieldFocusGained(ignoredEvt);
            }

            public void focusLost(FocusEvent ignoredEvt) {
                textFieldFocusLost(ignoredEvt);
            }
        });
        textField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    login();
                }
            }
        });

        passwordField.setText("Password");
        passwordField.setBackground(new Color(0xffffff));
        passwordField.setForeground(null);
        passwordField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent ignoredEvt) {
                passwordFieldFocusGained(ignoredEvt);
            }

            public void focusLost(FocusEvent ignoredEvt) {
                passwordFieldFocusLost(ignoredEvt);
            }
        });
        passwordField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    login();
                }
            }
        });

        button.setText("Login");
        button.setBackground(new Color(44, 119, 44));
        button.setForeground(null);
        button.setPreferredSize(new Dimension(100, 40));
        button.setForeground(new Color(0xFFFFFF));
        button.setFocusPainted(false);
        button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                login();
            }
        });


        rpPassword.setPreferredSize(new Dimension(400, 40));
        rpPassword.setBackground(new Color(0xFFFFFF));
        panel3.add(rpPassword);

        lbPassWord.setText("Forgot password?");
        lbPassWord.setPreferredSize(new Dimension(122, 20));
        lbPassWord.setForeground(new Color(44, 119, 44));
        rpPassword.add(lbPassWord);
        panel3.add(button);
        lbPassWord.setFont(lbPassWord.getFont().deriveFont(Font.PLAIN, 15));
        lbPassWord.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Set the initial label border to empty
        lbPassWord.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        lbPassWord.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Add a bottom border to underline the text
                lbPassWord.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(44, 119, 44)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Remove the bottom border when the mouse exits
                lbPassWord.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            }
            public void mouseClicked(MouseEvent e) {
                forgotPassword();
            }
        });

        exit.setText("X");
        exit.setFont(new Font("Public Sans", Font.BOLD, 15));
        exit.setBounds(650, 0, 50, 30);
        exit.setBackground(new Color(0xFD1111));
        exit.setForeground(new Color(0xFFFFFF));
        exit.setFocusPainted(false);
        exit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                exit();
            }
        });
        exit.setBorderPainted(false);
        panel1.add(exit);

        minimize.setText("—");
        minimize.setFont(new Font("Public Sans", Font.BOLD, 15));
        minimize.setBounds(600, 0, 50, 30);
        minimize.setBackground(new Color(0x676161));
        minimize.setForeground(new Color(0xFFFFFF));
        minimize.setFocusPainted(false);
        minimize.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                minimize();
            }
        });
        minimize.setBorderPainted(false);
        panel1.add(minimize);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel4.add(jLabel3, gbc);
        gbc.gridx++;
        panel4.add(panel6, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel4.add(jLabel4, gbc);
        gbc.gridx++;
        panel4.add(panel7, gbc);
    }

    private void textFieldFocusGained(FocusEvent ignoredEvt) {
        if (textField.getText().equals("Username")) {
            textField.setText("");
        }
    }

    private void textFieldFocusLost(FocusEvent ignoredEvt) {
        if (textField.getText().isEmpty()) {
            textField.setText("Username");
        }
    }

    private void passwordFieldFocusGained(FocusEvent ignoredEvt) {
        String password = new String(passwordField.getPassword());
        if (password.equals("Password")) {
            passwordField.setText("");
        }
    }

    private void passwordFieldFocusLost(FocusEvent ignoredEvt) {
        String password = new String(passwordField.getPassword());
        if (password.isEmpty()) {
            passwordField.setText("Password");
        }
    }

    private void forgotPassword() {
        new OTPGUI();
    }

    private void login() {
        String userName, passWord;
        userName = textField.getText();
        passWord = new String(passwordField.getPassword());
        AccountBLL accountBLL = new AccountBLL();
        List<Account> accountList = accountBLL.searchAccounts("USERNAME = '" + userName + "'", "DELETED = 0");
        if (accountList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên tài khoản không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String hashedPassword = accountList.get(0).getPassword();
        boolean first = false;
        if (hashedPassword.startsWith("first")) {
            first = true;
            hashedPassword = hashedPassword.substring(5);
        }
        if (!Password.verifyPassword(passWord, hashedPassword)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Account account = accountList.get(0);
        try {
            Thread thread = new Thread(() -> CafeManagement.homeGUI.setAccount(account));
            thread.start();
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            thread.join();
        } catch (Exception ignored) {

        }
        dispose();
        CafeManagement.homeGUI.setVisible(true);
        if (first) {
            CafeManagement.homeGUI.setEnabled(false);
            OTPGUI otpgui = new OTPGUI();
            otpgui.setAccount(account);
            otpgui.toStep(3);
            otpgui.toFront();
            otpgui.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    CafeManagement.homeGUI.setEnabled(true);
                    CafeManagement.homeGUI.toFront();
                }
            });
        }
    }

    private void exit() {
        CafeManagement.exit(0);
    }

    private void minimize() {
        setState(Frame.ICONIFIED);
    }

    @Override
    public void setVisible(boolean b) {
        Theme.applyTheme("light");
        textField.setText("Username");
        passwordField.setText("Password");
        System.gc();
        super.setVisible(b);
    }
}
