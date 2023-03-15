package com.cafe.GUI;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


    public class LoginGUI extends javax.swing.JFrame {
        public LoginGUI() {
            setBackground(new Color(0,0,0,0));
            setVisible(true);
        }

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents

        private void jTextField6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField6FocusGained
            if(jTextField6.getText().equals("Tên đăng nhập")){
                jTextField6.setText("");
                jTextField6.setForeground( new Color(255,255,255));
            }
        }//GEN-LAST:event_jTextField6FocusGained

        private void jTextField6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField6FocusLost
            if(jTextField6.getText().equals("")){
                jTextField6.setText("Tên đăng nhập");
                jTextField6.setForeground( new Color(153,153,153));
            }
        }//GEN-LAST:event_jTextField6FocusLost

        private void jPasswordField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordField1FocusGained
            if(jPasswordField1.getText().equals("password")){
                jPasswordField1.setText("");
                jPasswordField1.setForeground( new Color(255,255,255));
            }
        }//GEN-LAST:event_jPasswordField1FocusGained

        private void jPasswordField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPasswordField1FocusLost
            if(jPasswordField1.getText().equals("")){
                jPasswordField1.setText("password");
                jPasswordField1.setForeground( new Color(153,153,153));
            }
        }//GEN-LAST:event_jPasswordField1FocusLost

        private void button1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button1MouseClicked
            System.exit(0);
        }//GEN-LAST:event_button1MouseClicked

        private void button2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button2MouseClicked
            setState(LoginGUI.ICONIFIED);
        }//GEN-LAST:event_button2MouseClicked

        private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed
            mouseX = evt.getX();
            mouseY = evt.getY();
        }//GEN-LAST:event_jPanel1MousePressed

        private void jPanel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseDragged
            super.setLocation(super.getX() + evt.getX() - mouseX, super.getY() + evt.getY() - mouseY);
        }//GEN-LAST:event_jPanel1MouseDragged



        public static void main(String args[]) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new LoginGUI().setVisible(true);
                }
            });
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables

        private javax.swing.JLabel jLabel2;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JPasswordField jPasswordField1;
        private javax.swing.JTextField jTextField6;

        // End of variables declaration//GEN-END:variables
        private int mouseX,mouseY;
    }


