package com.cafe.utils;

import com.cafe.main.CafeManagement;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import javax.swing.*;
import java.util.Properties;

public class Email {
    public static void sendEmail(String toEmail, String emailSubject, String emailBody) {
        Properties properties = Resource.loadProperties(Settings.MAIL_FILE);
        int times = 5;
        String email = properties.getProperty("mail.email");
        String password = properties.getProperty("mail.password");
        String host = properties.getProperty("mail.host");
        String port = properties.getProperty("mail.port");

        properties = new Properties();
        properties.put("mail.smtp.host", Password.decrypt(host, email, times));
        properties.put("mail.smtp.port", Password.decrypt(port, email, times));
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, Password.decrypt(password, email, times));
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(emailSubject, "utf-8");
            message.setText(emailBody, "utf-8");
            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            JOptionPane.showMessageDialog(CafeManagement.loginGUI, "Hệ thống bị lỗi, vui lòng thử lại sau.");
        }
    }
}
