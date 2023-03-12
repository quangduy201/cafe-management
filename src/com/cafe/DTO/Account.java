package com.cafe.DTO;

public class Account {
    private String userName, passWord, decentralization_ID, staff_ID;
    private boolean deleted; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi thành kiểu boolean

    public Account() {
    }

    public Account(String userName, String passWord, String decentralization_ID, String staff_ID, boolean deleted) {
        this.userName = userName;
        this.passWord = passWord;
        this.decentralization_ID = decentralization_ID;
        this.staff_ID = staff_ID;
        this.deleted = deleted;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getDecentralization_ID() {
        return decentralization_ID;
    }

    public void setDecentralization_ID(String decentralization_ID) {
        this.decentralization_ID = decentralization_ID;
    }

    public String getStaff_ID() {
        return staff_ID;
    }

    public void setStaff_ID(String staff_ID) {
        this.staff_ID = staff_ID;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String toString (){
        return getUserName() + "\t" + getPassWord() + "\t" + getDecentralization_ID() + "\t" + getStaff_ID() + "\t" + isDeleted();
    }
}
