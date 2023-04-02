package com.cafe.DTO;

import com.cafe.utils.Day;

public class Customer {
    private String customerID;
    private String name;
    private boolean gender;
    private Day dateOfBirth;
    private String phone; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi gender thành kiểu String
    private boolean membership;
    private Day dateOfSup;
    private boolean deleted; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi thành kiểu boolean

    public Customer() {
    }

    public Customer(String customerID, String name, boolean gender, Day dateOfBirth, String phone, boolean membership, Day dateOfSup, boolean deleted) {
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

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Day getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Day dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isMembership() {
        return membership;
    }

    public void setMembership(boolean membership) {
        this.membership = membership;
    }

    public Day getDateOfSup() {
        return dateOfSup;
    }

    public void setDateOfSup(Day dateOfSup) {
        this.dateOfSup = dateOfSup;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        String gender1 = gender ? "Nam" : "Nữ";
        String member = membership ? "Có" : "Không";
        return customerID + " | " +
            name + " | " +
            gender1 + " | " +
            dateOfBirth + " | " +
            phone + " | " +
            member + " | " +
            dateOfSup;
    }
}
