package com.cafe.DTO;

import com.cafe.utils.Day;

public class Staff {
    private String staff_ID, name, gender, address, phone, email; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi gender thành kiểu String
    private double salary;
    private Day dateOfBirth, dateOfEntry;
    private boolean deleted; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi thành kiểu boolean

    public Staff() {
    }

    public Staff(String staff_ID, String name, String gender, String address, String phone, String email, double salary, Day dateOfBirth, Day dateOfEntry, boolean deleted) {
        this.staff_ID = staff_ID;
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.salary = salary;
        this.dateOfBirth = dateOfBirth;
        this.dateOfEntry = dateOfEntry;
        this.deleted = deleted;
    }

    public String getStaff_ID() {
        return staff_ID;
    }

    public void setStaff_ID(String staff_ID) {
        this.staff_ID = staff_ID;
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

    public String getAdddress() {
        return address;
    }

    public void setAdddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Day getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Day dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Day getDateOfEntry() {
        return dateOfEntry;
    }

    public void setDateOfEntry(Day dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
