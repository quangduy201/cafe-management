package com.cafe.BLL;

import com.cafe.DAL.StaffDAL;
import com.cafe.DTO.Staff;

import java.util.List;

public class StaffBLL {
    private StaffDAL staffDAL;

    public StaffBLL() {
        try {
            staffDAL = new StaffDAL();
        } catch (Exception ignored) {

        }
    }

    public boolean insertStaff(Staff staff) {
        if (searchStaffs("PHONE = '" + staff.getPhone() + "'").size() != 0) {
            System.out.println("Can't insert new staff. Phone already exists.");
            return false;
        }
        return staffDAL.insertStaff(staff) != 0;
    }

    public boolean updateStaff(Staff staff) {
        return staffDAL.updateStaff(staff) != 0;
    }

    public boolean removeStaff(String id) {
        return staffDAL.deleteStaff("STAFF_ID = '" + id + "'") != 0;
    }

    public List<Staff> searchStaffs(String... conditions) {
        return staffDAL.searchStaffs(conditions);
    }

    public String getAutoID() {
        return staffDAL.getAutoID();
    }
}
