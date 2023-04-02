package com.cafe.BLL;

import com.cafe.DAL.StaffDAL;
import com.cafe.DTO.Staff;

import java.util.List;
import java.util.Map;

public class StaffBLL extends Manager<Staff> {
    private StaffDAL staffDAL;
    private List<Staff> staffList;

    public StaffBLL() {
        try {
            staffDAL = new StaffDAL();
            staffList = searchStaffs();
        } catch (Exception ignored) {

        }
    }

    public StaffDAL getStaffDAL() {
        return staffDAL;
    }

    public void setStaffDAL(StaffDAL staffDAL) {
        this.staffDAL = staffDAL;
    }

    public List<Staff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;
    }

    public Object[][] getData() {
        return getData(staffList);
    }

    public boolean addStaff(Staff staff) {
        if (getIndex(staff, "PHONE", staffList) != -1) {
            System.out.println("Can't add new staff. Phone already exists.");
            return false;
        }
        staffList.add(staff);
        return staffDAL.addStaff(staff) != 0;
    }

    public boolean updateStaff(Staff staff) {
        staffList.set(getIndex(staff, "STAFF_ID", staffList), staff);
        return staffDAL.updateStaff(staff) != 0;
    }

    public boolean deleteStaff(Staff staff) {
        staff.setDeleted(true);
        staffList.set(getIndex(staff, "STAFF_ID", staffList), staff);
        return staffDAL.deleteStaff("STAFF_ID = '" + staff.getStaffID() + "'") != 0;
    }

    public List<Staff> searchStaffs(String... conditions) {
        return staffDAL.searchStaffs(conditions);
    }

    public List<Staff> findStaffsBy(Map<String, Object> conditions) {
        List<Staff> staffs = staffList;
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            staffs = findObjectsBy(entry.getKey(), entry.getValue(), staffs);
        return staffs;
    }

    public String getAutoID() {
        try {
            return getAutoID("ST", 2, staffList);
        } catch (Exception e) {
            System.out.println("Error occurred in StaffBLL.getAutoID(): " + e.getMessage());
        }
        return "";
    }

    @Override
    public Object getValueByKey(Staff staff, String key) {
        return switch (key) {
            case "CATEGORY_ID" -> staff.getStaffID();
            case "NAME" -> staff.getName();
            case "GENDER" -> staff.getGender();
            case "DOB" -> staff.getDateOfBirth();
            case "ADDRESS" -> staff.getAddress();
            case "PHONE" -> staff.getPhone();
            case "EMAIL" -> staff.getEmail();
            case "SALARY" -> staff.getSalary();
            case "DOENTRY" -> staff.getDateOfEntry();
            default -> null;
        };
    }
}
