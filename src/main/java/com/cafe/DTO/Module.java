package com.cafe.DTO;

public class Module {
    private String moduleID;
    private String moduleName;
    private boolean deleted;

    public Module() {
    }

    public Module(String moduleID, String moduleName, boolean deleted) {
        this.moduleID = moduleID;
        this.moduleName = moduleName;
        this.deleted = deleted;
    }

    public String getModuleID() {
        return moduleID;
    }

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return  moduleID + " | "  +
            moduleName + " | " +
            deleted +  " | ";
    }
}
