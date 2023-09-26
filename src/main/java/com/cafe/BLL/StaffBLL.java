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
        staffDAL = new StaffDAL();
        staffList = searchStaffs("DELETED = 0", "STAFF_ID != 'ST00'");
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

    public boolean exists(Staff staff) {
        return !findStaffsBy(Map.of(
            "NAME", staff.getName(),
            "GENDER", staff.isGender(),
            "DOB", staff.getDateOfBirth(),
            "ADDRESS", staff.getAddress(),
            "PHONE", staff.getPhone(),
            "EMAIL", staff.getEmail(),
            "SALARY", staff.getSalary(),
            "DOENTRY", staff.getDateOfEntry()
        )).isEmpty();
    }

    public boolean exists(Map<String, Object> conditions) {
        return !findStaffsBy(conditions).isEmpty();
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
