package com.cafe.utils;

import com.cafe.DTO.Account;
import com.cafe.main.CafeManagement;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import javafx.util.Pair;

import javax.swing.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class OTP {
    public static final List<Pair<Account, String>> activeOTPs = new ArrayList<>();
    public static final int OTP_LENGTH = 6;

    public OTP() {

    }

    public static String generateNumericOTP() {
        String numericChars = "0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < OTP_LENGTH; i++) {
            int index = random.nextInt(numericChars.length());
            otp.append(numericChars.charAt(index));
        }

        return otp.toString();
    }

    public static int getIndexOTP(Account account) {
        for (int i = 0; i < activeOTPs.size(); ++i) {
            Account activeAccount = activeOTPs.get(i).getKey();
            if (activeAccount.getAccountID().equals(account.getAccountID()))
                return i;
        }
        return -1;
    }

    public static void setOTP(Account account, String otp) {
        int index = getIndexOTP(account);
        if (index == -1) {
            activeOTPs.add(new Pair<>(account, otp));
        } else {
            activeOTPs.set(index, new Pair<>(account, otp));
        }
    }

    public static void removeOTP(Account account) {
        int index = getIndexOTP(account);
        if (index != -1)
            activeOTPs.remove(index);
    }

    public static void sendEmail(String toEmail, String emailSubject, String emailBody) {
        // Email configuration
        String email = "dinhduy2012003@gmail.com";
        String password = "vmkfsxyivzhthkxj";

        // Email properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Create a Session with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(emailSubject);
            message.setText(emailBody);
            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            JOptionPane.showMessageDialog(CafeManagement.loginGUI, "Hệ thống bị lỗi, vui lòng thử lại sau.");
        }
    }

    public static void sendOTP(String toEmail, String otp) {
        String emailSubject = "Đặt lại mật khẩu King Cafe";
        String emailBody = "Mã OTP để thiết lập lại mật khẩu của bạn là: " + otp;
        sendEmail(toEmail, emailSubject, emailBody);
    }

    public static void sendPassword(String toEmail, String password) {
        String emailSubject = "Mật khẩu mặc định King Cafe";
        String emailBody = "Không được cung cấp mật khẩu này cho bất cứ ai: " + password;
        sendEmail(toEmail, emailSubject, emailBody);
    }

    public static void countdown(String otp, int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        for (int i = 0; i < activeOTPs.size(); ++i) {
            if (activeOTPs.get(i).getValue().equals(otp)) {
                activeOTPs.remove(i);
                String[] options = new String[]{"Gửi lại", "Hủy"};
                JOptionPane.showOptionDialog(CafeManagement.loginGUI, "Gửi lại mã OTP khác?", "Mã OTP đã hết hạn",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                break;
            }
        }
    }
}
