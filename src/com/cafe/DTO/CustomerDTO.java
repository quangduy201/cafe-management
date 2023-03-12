package com.cafe.DTO;

import com.cafe.utils.Day;

public class CustomerDTO {
    private String customer_ID, name, gender, phone; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi gender thành kiểu String
    private Day dateOfBirth, dateOfSup;
    private boolean membership, deleted; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi thành kiểu boolean

    public CustomerDTO() {
    }

    public CustomerDTO(String customer_ID, String name, String gender, String phone, Day dateOfBirth, Day dateOfSup, boolean membership, boolean deleted) {
        this.customer_ID = customer_ID;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.dateOfSup = dateOfSup;
        this.membership = membership;
        this.deleted = deleted;
    }

    public String getCustomer_ID() {
        return customer_ID;
    }

    public void setCustomer_ID(String customer_ID) {
        this.customer_ID = customer_ID;
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
}
