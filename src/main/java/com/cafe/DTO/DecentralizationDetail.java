package com.cafe.DTO;

public class DecentralizationDetail {
    private String decentralizationID;
    private String moduleID;
    private boolean canVIEW;
    private boolean canADD;
    private boolean canEDIT;
    private boolean canREMOVE;
    private boolean deleted; // khi đọc dữ liệu từ database sẽ so sánh rồi đổi thành kiểu boolean

    public DecentralizationDetail() {
    }

    public DecentralizationDetail(String decentralizationID, String moduleID, boolean canVIEW, boolean canADD, boolean canEDIT, boolean canREMOVE, boolean deleted) {
        this.decentralizationID = decentralizationID;
        this.moduleID =  moduleID;
        this.canVIEW = canVIEW;
        this.canADD = canADD;
        this.canEDIT = canEDIT;
        this.canREMOVE = canREMOVE;
        this.deleted = deleted;
    }

    public boolean isCanVIEW() {
        return canVIEW;
    }

    public void setCanVIEW(boolean canVIEW) {
        this.canVIEW = canVIEW;
    }

    public boolean isCanADD() {
        return canADD;
    }

    public void setCanADD(boolean canADD) {
        this.canADD = canADD;
    }

    public String getDecentralizationID() {
        return decentralizationID;
    }

    public void setDecentralizationID(String decentralizationID) {
        this.decentralizationID = decentralizationID;
    }

    public String getModuleID() {
        return moduleID;
    }

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }

    public boolean isCanEDIT() {
        return canEDIT;
    }

    public void setCanEDIT(boolean canEDIT) {
        this.canEDIT = canEDIT;
    }

    public boolean isCanREMOVE() {
        return canREMOVE;
    }

    public void setCanREMOVE(boolean canREMOVE) {
        this.canREMOVE = canREMOVE;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return  decentralizationID + " | "  +
             moduleID + " | " +
             canVIEW + " | " +
             canADD + " | " +
             canEDIT + " | " +
             canREMOVE;
    }
}
