package com.cafe.utils;

import com.cafe.BLL.BillBLL;
import com.cafe.BLL.CustomerBLL;
import com.cafe.DTO.Bill;
import com.cafe.DTO.Customer;
import com.cafe.custom.FrameCustomer;
import com.cafe.recognition.Recorder;
import com.cafe.recognition.Scanner;
import com.cafe.recognition.Trainer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.opencv.opencv_java;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.util.Objects;

public class Tasks {
    Recorder recorder;
    Trainer trainer;
    Scanner scanner;

    public Tasks(String cameraName) {
        recorder = new Recorder(cameraName);
        trainer = new Trainer();
        scanner = new Scanner(cameraName);
    }

    public void recordAndTrain(String customerID, double percentToSuccess) {
        recorder.record(customerID);
        String imageDirPath = new File(Resource.getAbsolutePath(Trainer.FACE_DIRECTORY), customerID).getPath();
        int numberOfImages;
        try {
            numberOfImages = Objects.requireNonNull(new File(imageDirPath).list()).length;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Không có hình ảnh của khách hàng " + customerID, "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (numberOfImages != Recorder.NUMBER_OF_PICTURES) {
            JOptionPane.showMessageDialog(null, "Không đủ hình ảnh của khách hàng " + customerID, "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        trainer.train(customerID, percentToSuccess);
    }

    public void detectAndRecognize(double confidence, DefaultTableModel model, String object) {
        boolean found = false;
        if (object.equals("BILL")) {
            BillBLL billBLL = new BillBLL();
            found = scanner.scan(confidence, (customer) -> {
                model.setRowCount(0);
                for (Bill bill : billBLL.getBillList()) {
                    if (bill.getCustomerID().equals(customer.getCustomerID())) {
                        model.addRow(new Object[]{bill.getBillID(), bill.getCustomerID(), bill.getStaffID(), bill.getDateOfPurchase(), bill.getTotal(), bill.getReceived(), bill.getExcess()});
                    }
                }
                return null;
            });
        } else if (object.equals("CUSTOMER")) {
            CustomerBLL customerBLL = new CustomerBLL();
            found = scanner.scan(confidence, (foundCustomer) -> {
                model.setRowCount(0);
                for (Customer customer : customerBLL.getCustomerList()) {
                    if (customer.getCustomerID().equals(foundCustomer.getCustomerID())) {
                        String gender;
                        String member;
                        gender = customer.isGender() ? "Nam" : "Nữ";
                        member = customer.isMembership() ? "Có" : "Không";
                        model.addRow(new Object[]{customer.getCustomerID(), customer.getName(), gender, customer.getDateOfBirth(), customer.getPhone(), member, customer.getDateOfSup()});
                    }
                }
                return null;
            });
        }
        if (!found) {
            if (JOptionPane.showOptionDialog(null,
                "Không tìm thấy khách hàng. Tạo khách hàng mới?",
                "Failed",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Tạo", "Hủy"},
                "Tạo") == JOptionPane.YES_OPTION) {
                new FrameCustomer("").setVisible(true);
            }
        }
    }

    public void detectAndRecognize(double confidence, JTextField textFieldCustomerName) {
        boolean found = scanner.scan(confidence, (customer) -> {
            textFieldCustomerName.setText(customer.getName());
            return null;
        });
        if (!found) {
            if (JOptionPane.showOptionDialog(null,
                "Không tìm thấy khách hàng. Tạo khách hàng mới?",
                "Failed",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Tạo", "Hủy"},
                "Tạo") == JOptionPane.YES_OPTION) {
                new FrameCustomer("").setVisible(true);
            }
        }
    }

    static {
        Loader.load(opencv_java.class);
    }
}
