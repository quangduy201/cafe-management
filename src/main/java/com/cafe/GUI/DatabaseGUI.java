package com.cafe.GUI;

import com.cafe.main.CafeManagement;
import com.cafe.utils.Password;
import com.cafe.utils.Resource;
import com.cafe.utils.Settings;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class DatabaseGUI extends JFrame {
    private Properties properties;
    private JLabel lbPort;
    private JLabel lbDatabase;
    private JLabel lbUsername;
    private JLabel lbPassword;
    private JTextField txtPort;
    private JTextField txtDatabase;
    private JTextField txtUsername;
    private JTextField txtPassword;

    public DatabaseGUI(Properties properties) {
        super("Cấu hình database");
        this.properties = properties;
        initComponents();
    }

    public void initComponents() {
        JPanel panel = new JPanel(new MigLayout());

        lbPort = new JLabel("Port");
        lbPort.setFont(new Font("Arial", Font.BOLD, 14));
        lbPort.setHorizontalAlignment(JLabel.LEFT);
        txtPort = new JTextField();
        txtPort.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPort.setText(properties.getProperty("db.port"));
        txtPort.addActionListener(e -> confirm());

        lbDatabase = new JLabel("Tên database");
        lbDatabase.setFont(new Font("Arial", Font.BOLD, 14));
        lbDatabase.setHorizontalAlignment(JLabel.LEFT);
        txtDatabase = new JTextField();
        txtDatabase.setFont(new Font("Arial", Font.PLAIN, 14));
        txtDatabase.setText(properties.getProperty("db.database"));
        txtDatabase.addActionListener(e -> confirm());

        lbUsername = new JLabel("Username");
        lbUsername.setFont(new Font("Arial", Font.BOLD, 14));
        lbUsername.setHorizontalAlignment(JLabel.LEFT);
        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        txtUsername.setText(properties.getProperty("db.username"));
        txtUsername.addActionListener(e -> confirm());

        lbPassword = new JLabel("Password");
        lbPassword.setFont(new Font("Arial", Font.BOLD, 14));
        lbPassword.setHorizontalAlignment(JLabel.LEFT);
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPassword.addActionListener(e -> confirm());

        JButton btnConfirm = new JButton("Xác nhận");
        btnConfirm.setFont(new Font("Arial", Font.PLAIN, 12));
        btnConfirm.addActionListener(e -> confirm());

        JButton btnCancel = new JButton("Hủy");
        btnCancel.setFont(new Font("Arial", Font.PLAIN, 12));
        btnCancel.addActionListener(e -> cancel());

        panel.add(lbPort);
        panel.add(txtPort, "wrap,growx");
        panel.add(lbDatabase);
        panel.add(txtDatabase, "wrap,growx");
        panel.add(lbUsername);
        panel.add(txtUsername, "wrap,growx");
        panel.add(lbPassword);
        panel.add(txtPassword, "wrap,growx");
        panel.add(btnConfirm, "cell 1 4");
        panel.add(btnCancel, "cell 1 4");

        setSize(300, 250);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        setContentPane(panel);
        repaint();
        revalidate();
        setVisible(true);
    }

    public void confirm() {
        try {
            Files.createDirectories(Paths.get(Resource.getResourcePath("settings", false)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        try (OutputStream outputStream = new FileOutputStream(Resource.getResourcePath(Settings.DATABASE_FILE, false))) {
            properties.setProperty("db.port", txtPort.getText());
            properties.setProperty("db.database", txtDatabase.getText());
            properties.setProperty("db.username", txtUsername.getText());
            String encryptedPassword = Password.encrypt(txtPassword.getText(), txtDatabase.getText());
            properties.setProperty("db.password", encryptedPassword);
            properties.store(outputStream, "Database configuration");
            setEnabled(false);
            dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void cancel() {
        String[] options = new String[]{"Cấu hình lại", "Thoát chương trình"};
        int choice = JOptionPane.showOptionDialog(null, "Chương trình không thể chạy nếu không thể kết nối được cơ sở dữ liệu.",
            "Lỗi", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
        if (choice == 1)
            CafeManagement.exit(1);
    }
}
