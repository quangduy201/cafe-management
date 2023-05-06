package com.cafe.utils;

import com.cafe.BLL.BillBLL;
import com.cafe.BLL.CustomerBLL;
import com.cafe.DTO.Bill;
import com.cafe.DTO.Customer;
import com.cafe.recognition.Recorder;
import com.cafe.recognition.Scanner;
import com.cafe.recognition.Trainer;

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
        String imageDirPath = new File(Trainer.FACE_DIRECTORY, customerID).getPath();
        int numberOfImages = Objects.requireNonNull(new File(imageDirPath).list()).length;
        if (numberOfImages != Recorder.NUMBER_OF_PICTURES) {
            System.out.println("Not trained");
            return;
        }
        trainer.train(customerID, percentToSuccess);
    }

    public void detectAndRecognize(double confidence, DefaultTableModel model, String object) {
        if (object.equals("BILL")) {
            BillBLL billBLL = new BillBLL();
            scanner.scan(confidence, (customer) -> {
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
            scanner.scan(confidence, (foundCustomer) -> {
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
    }

    public void detectAndRecognize(double confidence, JTextField textFieldCustomerName) {
        scanner.scan(confidence, (customer) -> {
            textFieldCustomerName.setText(customer.getName());
            return null;
        });
    }

    static {
        System.loadLibrary("libopencv_java470");
    }
}
