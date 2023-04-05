package com.cafe.BLL;

import com.cafe.DAL.StaffDAL;
import com.cafe.DTO.Staff;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StaffBLL extends Manager<Staff> {
    private StaffDAL staffDAL;
    private List<Staff> staffList;

    public StaffBLL() {
        try {
            staffDAL = new StaffDAL();
            staffList = searchStaffs("DELETED = 0");
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
        staffList.remove(getIndex(staff, "STAFF_ID", staffList));
        return staffDAL.deleteStaff("STAFF_ID = '" + staff.getStaffID() + "'") != 0;
    }

    public List<Staff> searchStaffs(String... conditions) {
        return staffDAL.searchStaffs(conditions);
    }

    public List<Staff> findStaffs(String key, Object value) {
        List<Staff> list = new ArrayList<>();
        for (Staff staff : staffList) {
            if (value instanceof Boolean) {
                if (getValueByKey(staff, key) == value) {
                    list.add(staff);
                }
            } else {
                if (getValueByKey(staff, key).toString().toLowerCase().contains(value.toString().toLowerCase())) {
                    list.add(staff);
                }
            }

        }
        return list;
    }

    public List<Staff> findStaffsBy(Map<String, Object> conditions) {
        List<Staff> staffs = staffList;
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            staffs = findObjectsBy(entry.getKey(), entry.getValue(), staffs);
        return staffs;
    }

    public String getAutoID() {
        return getAutoID("ST", 2, searchStaffs());
    }

    @Override
    public Object getValueByKey(Staff staff, String key) {
        return switch (key) {
            case "STAFF_ID" -> staff.getStaffID();
            case "NAME" -> staff.getName();
            case "GENDER" -> staff.isGender();
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
