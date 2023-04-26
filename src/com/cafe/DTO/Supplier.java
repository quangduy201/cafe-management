package com.cafe.DTO;

public class Supplier {
    private String supplierID;
    private String name;
    private String phone;
    private String address;
    private String email;
    private boolean deleted; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi thành kiểu boolean

    public Supplier() {
    }

    public Supplier(String supplierID, String name, String phone, String address, String email, boolean deleted) {
        this.supplierID = supplierID;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.deleted = deleted;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return supplierID + " | " +
            name + " | " +
            phone + " | " +
            address + " | " +
            email;
    }
}
