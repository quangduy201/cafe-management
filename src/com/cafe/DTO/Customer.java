package com.cafe.DTO;

import com.cafe.utils.Day;

public class Customer {
    private String customerID, name, gender, phone; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi gender thành kiểu String
    private Day dateOfBirth, dateOfSup;
    private boolean membership, deleted; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi thành kiểu boolean

    public Customer() {
    }

    public Customer(String customerID, String name, String gender, Day dateOfBirth, String phone, boolean membership, Day dateOfSup, boolean deleted) {
        this.customerID = customerID;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.membership = membership;
        this.dateOfSup = dateOfSup;
        this.deleted = deleted;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Day getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Day dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isMembership() {
        return membership;
    }

    public void setMembership(boolean membership) {
        this.membership = membership;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Day getDateOfSup() {
        return dateOfSup;
    }

    public void setDateOfSup(Day dateOfSup) {
        this.dateOfSup = dateOfSup;
    }

    @Override
    public String toString() {
        return customerID + "\t" +
            name + "\t" +
            gender + "\t" +
            dateOfBirth + "\t" +
            phone + "\t" +
            membership + "\t" +
            dateOfSup;
    }
}
