package com.cafe.GUI;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.awt.Color;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


    public class LoginGUI extends javax.swing.JFrame {
        public LoginGUI() {
            initComponents();
            setBackground(new Color(0,0,0,0));
            setVisible(true);
        }

        private void initComponents() {

            roundPanel1 = new com.cafe.Custom.RoundPanel();
            jPanel1 = new javax.swing.JPanel();
            jLabel2 = new javax.swing.JLabel();
            jPanel2 = new javax.swing.JPanel();
            jTextField6 = new javax.swing.JTextField();
            jPasswordField1 = new javax.swing.JPasswordField();
            button1 = new com.cafe.Custom.Button();
            button2 = new com.cafe.Custom.Button();
            button3 = new com.cafe.Custom.Button();

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setBackground(new java.awt.Color(186, 255, 116));
            setLocation(new java.awt.Point(400, 200));
            setUndecorated(true);

            roundPanel1.setBackground(new java.awt.Color(0, 0, 0));


            jTextField6.setBackground(new java.awt.Color(0, 0, 0));
            jTextField6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
            jTextField6.setForeground(new java.awt.Color(153, 153, 153));
            jTextField6.setText("Tên đăng nhập");
            jTextField6.setAlignmentX(1.0F);

            jPasswordField1.setBackground(new java.awt.Color(0, 0, 0));
            jPasswordField1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
            jPasswordField1.setForeground(new java.awt.Color(153, 153, 153));
            jPasswordField1.setText("password");
            jPasswordField1.addFocusListener(new java.awt.event.FocusAdapter() {


            });

            button1.setBorder(null);
            button1.setText("X");
            button1.setFocusPainted(false);
            button1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
            button1.setRadius(15);
            button1.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    button1MouseClicked(evt);
                }
            });


            button2.setBorder(null);
            button2.setText("-");
            button2.setFocusPainted(false);
            button2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
            button2.setMaximumSize(new java.awt.Dimension(43, 25));
            button2.setMinimumSize(new java.awt.Dimension(43, 25));
            button2.setRadius(15);
            button2.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    button2MouseClicked(evt);
                }
            });



            button3.setBackground(new java.awt.Color(250, 110, 240));
            button3.setBorder(null);
            button3.setText("Đăng nhập");
            button3.setFocusPainted(false);
            button3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
            button3.setRadius(10);
            button3.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    button3MouseClicked(evt);
                }
            });

        }

        private void button1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button1MouseClicked
            System.exit(0);
        }//GEN-LAST:event_button1MouseClicked

        private void button2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button2MouseClicked
            setState(LoginGUI.ICONIFIED);
        }//GEN-LAST:event_button2MouseClicked

        private void button3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button3MouseClicked
            if(jTextField6.getText().equals("") || jPasswordField1.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Vui lòng không để trống!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents


        public static void main(String args[]) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new LoginGUI().setVisible(true);
                }
            });
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables

        private com.cafe.Custom.Button button1;
        private com.cafe.Custom.Button button2;
        private com.cafe.Custom.Button button3;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JPasswordField jPasswordField1;
        private javax.swing.JTextField jTextField6;
        private com.cafe.Custom.RoundPanel roundPanel1;
        // End of variables declaration//GEN-END:variables
        private int mouseX,mouseY;
    }


