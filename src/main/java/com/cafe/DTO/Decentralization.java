package com.cafe.DTO;

import java.util.ArrayList;
import java.util.List;

public class Decentralization {
    private String decentralizationID;
    private String decentralizationName;
    private boolean deleted;

    public Decentralization() {
    }

    public Decentralization(String decentralizationID, String decentralizationName, boolean deleted) {
        this.decentralizationID = decentralizationID;
        this.decentralizationName = decentralizationName;
        this.deleted = deleted;
    }

    public String getDecentralizationID() {
        return decentralizationID;
    }

    public void setDecentralizationID(String decentralizationID) {
        this.decentralizationID = decentralizationID;
    }

    public String getDecentralizationName() {
        return decentralizationName;
    }

    public void setDecentralizationName(String decentralizationName) {
        this.decentralizationName = decentralizationName;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return decentralizationID + " | " +
            decentralizationName + " | " +
            deleted +  " | ";
    }
}
