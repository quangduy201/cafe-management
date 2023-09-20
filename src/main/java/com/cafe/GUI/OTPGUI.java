package com.cafe.GUI;

import com.cafe.BLL.AccountBLL;
import com.cafe.BLL.StaffBLL;
import com.cafe.DTO.Account;
import com.cafe.DTO.Staff;
import com.cafe.utils.OTP;
import com.cafe.utils.Password;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.util.Map;

public class OTPGUI extends JFrame {
    private Account account;
    private String email;
    private JPanel otpEnterEmail;
    private JPanel otpConfirmPanel;
    private JPanel otpChangePassword;
    private int step;

    public OTPGUI() {
        super("Quên mật khẩu");
        setSize(500, 300);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        otpEnterEmail = new JPanel(new FlowLayout(FlowLayout.CENTER));
        otpConfirmPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        otpChangePassword = new JPanel(new FlowLayout(FlowLayout.CENTER));
        account = new Account();
        email = "";
        toStep(step = 1);
        setVisible(true);
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void showEnterEmail() {
        otpEnterEmail.removeAll();

        JLabel label = new JLabel("Nhập email của bạn");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setPreferredSize(new Dimension(500, 100));
        label.setHorizontalAlignment(JLabel.CENTER);
        otpEnterEmail.add(label);

        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setPreferredSize(new Dimension(300, 40));
        if (!this.email.isEmpty())
            textField.setText(this.email);
        textField.requestFocusInWindow();
        otpEnterEmail.add(textField);

        JLabel nothing = new JLabel();
        nothing.setPreferredSize(new Dimension(700, 50));
        otpEnterEmail.add(nothing);

        JButton[] buttons = new JButton[2];
        buttons[0] = new JButton();
        buttons[0].setText("Tiếp tục");
        buttons[0].setFont(new Font("Arial", Font.PLAIN, 12));
        buttons[0].addActionListener(e -> this.validateStep1(textField.getText()));
        otpEnterEmail.add(buttons[0]);

        buttons[1] = new JButton();
        buttons[1].setText("Hủy");
        buttons[1].setFont(new Font("Arial", Font.PLAIN, 12));
        buttons[1].addActionListener(e -> this.dispose());
        otpEnterEmail.add(buttons[1]);
    }

    public void showConfirmPanel() {
        otpConfirmPanel.removeAll();

        JLabel username = new JLabel(account.getUsername());
        username.setFont(new Font("Arial", Font.BOLD, 20));
        username.setPreferredSize(new Dimension(500, 32));
        username.setHorizontalAlignment(JLabel.CENTER);
        otpConfirmPanel.add(username);

        JLabel label1 = new JLabel("Hệ thống vừa gửi mã OTP đến email của bạn.");
        label1.setFont(new Font("Arial", Font.BOLD, 14));
        label1.setPreferredSize(new Dimension(500, 32));
        label1.setHorizontalAlignment(JLabel.CENTER);
        otpConfirmPanel.add(label1);

        JLabel label2 = new JLabel("Vui lòng nhập mã vào ô bên dưới.");
        label2.setFont(new Font("Arial", Font.BOLD, 14));
        label2.setPreferredSize(new Dimension(500, 32));
        label2.setHorizontalAlignment(JLabel.CENTER);
        otpConfirmPanel.add(label2);

        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.BOLD, 20));
        textField.setPreferredSize(new Dimension(90, 40));
        textField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField textField = (JTextField) input;
                String text = textField.getText();
                return text.matches("\\d{1,6}"); // Only allow 1 to 6 digits
            }
        });
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                currentText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

                if (currentText.matches("\\d{0,6}")) { // Only allow 0 to 6 digits
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
        otpConfirmPanel.add(textField);

        JLabel nothing = new JLabel();
        nothing.setPreferredSize(new Dimension(700, 50));
        otpConfirmPanel.add(nothing);

        JButton[] buttons = new JButton[4];
        buttons[0] = new JButton();
        buttons[0].setText("Gửi lại");
        buttons[0].setFont(new Font("Arial", Font.PLAIN, 12));
        buttons[0].addActionListener(e -> {
            sendOTP();
            toStep(step);
        });
        otpConfirmPanel.add(buttons[0]);

        buttons[1] = new JButton();
        buttons[1].setText("Quay lại");
        buttons[1].setFont(new Font("Arial", Font.PLAIN, 12));
        buttons[1].addActionListener(e -> {
            toStep(--step);
        });
        otpConfirmPanel.add(buttons[1]);

        buttons[2] = new JButton();
        buttons[2].setText("Tiếp tục");
        buttons[2].setFont(new Font("Arial", Font.PLAIN, 12));
        buttons[2].addActionListener(e -> {
            validateStep2(textField.getText());
        });
        otpConfirmPanel.add(buttons[2]);

        buttons[3] = new JButton();
        buttons[3].setText("Hủy");
        buttons[3].setFont(new Font("Arial", Font.PLAIN, 12));
        buttons[3].addActionListener(e -> this.dispose());
        otpConfirmPanel.add(buttons[3]);
    }

    public void showChangePassword() {
        JLabel title = new JLabel("Vui lòng đổi lại mật khẩu");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setPreferredSize(new Dimension(500, 100));
        title.setHorizontalAlignment(JLabel.CENTER);
        otpChangePassword.add(title);

        JLabel label1 = new JLabel("Nhập mật khẩu mới");
        label1.setFont(new Font("Arial", Font.BOLD, 14));
        label1.setPreferredSize(new Dimension(200, 32));
        label1.setHorizontalAlignment(JLabel.CENTER);
        otpChangePassword.add(label1);

        JPasswordField passwordField1 = new JPasswordField();
        passwordField1.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField1.setPreferredSize(new Dimension(200, 32));
        otpChangePassword.add(passwordField1);

        JLabel label2 = new JLabel("Nhập lại mật khẩu");
        label2.setFont(new Font("Arial", Font.BOLD, 14));
        label2.setPreferredSize(new Dimension(200, 32));
        label2.setHorizontalAlignment(JLabel.CENTER);
        otpChangePassword.add(label2);

        JPasswordField passwordField2 = new JPasswordField();
        passwordField2.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField2.setPreferredSize(new Dimension(200, 32));
        otpChangePassword.add(passwordField2);

        JButton[] buttons = new JButton[2];
        buttons[0] = new JButton();
        buttons[0].setText("Xác nhận");
        buttons[0].setFont(new Font("Arial", Font.PLAIN, 12));
        buttons[0].addActionListener(e -> {
            String password = new String(passwordField1.getPassword());
            String confirm = new String(passwordField2.getPassword());
            validateStep3(password, confirm);
        });
        otpChangePassword.add(buttons[0]);

        buttons[1] = new JButton();
        buttons[1].setText("Hủy");
        buttons[1].setFont(new Font("Arial", Font.PLAIN, 12));
        buttons[1].addActionListener(e -> this.dispose());
        otpChangePassword.add(buttons[1]);
    }

    private void validateStep1(String email) {
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập email của bạn.");
            return;
        }
        java.util.List<Staff> foundStaffs = new StaffBLL().findStaffsBy(Map.of("EMAIL", email));
        if (foundStaffs.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy tài khoản.");
            return;
        }
        java.util.List<Account> foundAccounts = new AccountBLL().findAccountsBy(Map.of("STAFF_ID", foundStaffs.get(0).getStaffID()));
        if (foundAccounts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy tài khoản.");
            return;
        }
        account = foundAccounts.get(0);
        this.email = email;
        sendOTP();
        toStep(++step);
    }

    private void sendOTP() {
        String otp = OTP.generateNumericOTP();
        new Thread(() -> OTP.sendOTP(email, otp)).start();
        int minute = 3;
        int time = minute * 60 * 1000;
        new Thread(() -> OTP.countdown(otp, time)).start();
        OTP.setOTP(account, otp);
    }

    private void validateStep2(String otp) {
        if (otp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã OTP.");
            return;
        }
        if (otp.length() != OTP.OTP_LENGTH) {
            JOptionPane.showMessageDialog(this, "Mã OTP gồm 6 chữ số.");
            return;
        }
        int index = OTP.getIndexOTP(account);
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Mã OTP đã hết hạn.");
            return;
        }
        String activeOTP = OTP.activeOTPs.get(index).getValue();
        if (!activeOTP.equals(otp)) {
            JOptionPane.showMessageDialog(this, "Mã OTP không đúng. Vui lòng thử lại");
            return;
        }
        OTP.removeOTP(account);
        toStep(++step);
    }

    private void validateStep3(String password, String confirm) {
        if (password.isEmpty() || confirm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
            return;
        }
        if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Nhập lại mật khẩu không trùng khớp với mật khẩu mới.");
            return;
        }
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[^\\s]{3,32}$")) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được chứa khoảng trắng.\nMật khẩu phải chứa ít nhất 1 chữ cái thường, 1 chữ cái hoa and 1 chữ số");
            return;
        }
        String hashedPassword = Password.hashPassword(password);
        account.setPassword(hashedPassword);
        if (!new AccountBLL().updateAccountPassword(account)) {
            return;
        }
        JOptionPane.showMessageDialog(this, "Thay đổi mật khẩu thành công.");
        this.dispose();
    }

    public void toStep(int step) {
        JPanel panel = new JPanel();
        switch (step) {
            case 1 -> { showEnterEmail(); panel = otpEnterEmail; }
            case 2 -> { showConfirmPanel(); panel = otpConfirmPanel; }
            case 3 -> { showChangePassword(); panel = otpChangePassword; }
        }
        setContentPane(panel);
        repaint();
        revalidate();
    }
}
