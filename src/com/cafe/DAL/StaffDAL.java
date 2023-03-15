package com.cafe.DAL;
import com.cafe.DTO.Staff;
import com.cafe.utils.Day;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaffDAL extends Manager{
    public StaffDAL() throws SQLException {
        super("staff", new ArrayList<>(
            List.of("STAFF_ID",
                "NAME",
                "GENDER",
                "DOB",
                "ADDRESS",
                "PHONE",
                "EMAIL",
                "SALARY",
                "DOENTRY",
                "DELETED")
        ));
    }

    public List<Staff> convertToStaffs(List<List<String>> data) {
        return convert(data, row -> {
            try {
                return new Staff(
                    row.get(0), // staffID
                    row.get(1), // name
                    row.get(2), // gender
                    Day.parseDay(row.get(3)), // dateOfBirth
                    row.get(4), // address
                    row.get(5), // phone
                    row.get(6), // email
                    Double.parseDouble(row.get(7)), // salary
                    Day.parseDay(row.get(8)), // dateOfEntry
                    Boolean.parseBoolean(row.get(9)) // deleted
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public int insertStaff(Staff staff) {
        try {
            return create(staff.getStaffID(),
                staff.getName(),
                staff.getGender(),
                staff.getDateOfBirth(),
                staff.getAdddress(),
                staff.getPhone(),
                staff.getEmail(),
                staff.getSalary(),
                staff.getDateOfEntry(),
                false
            ); // staff khi tạo mặc định deleted = 0
        } catch (Exception e) {
            System.out.println("Error occurred in StaffDAL.insertStaff(): " + e.getMessage());
        }
        return 0;
    }

    public int updateStaff(Map<String, Object> updateValues, String... conditions) {
        try {
            return update(updateValues, conditions);
        } catch (Exception e) {
            System.out.println("Error occurred in StaffDAL.updateStaff(): " + e.getMessage());
        }
        return 0;
    }

    public int deleteStaff(String... conditions) {
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("DELETED", 1);
            return update(updateValues, conditions);
        } catch (Exception e) {
            System.out.println("Error occurred in StaffDAL.deleteStaff(): " + e.getMessage());
        }
        return 0;
    }

    public List<Staff> searchStaffs(String... conditions) {
        try {
            return convertToStaffs(read(conditions));
        } catch (Exception e) {
            System.out.println("Error occurred in StaffDAL.searchStaffs(): " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public String getAutoID() {
        try {
            return getAutoID("ST", 3);
        } catch (Exception e) {
            System.out.println("Error occurred in StaffDAL.getAutoID(): " + e.getMessage());
        }
        return "";
    }
}