package com.cafe.DAO;
import com.cafe.DTO.Staff;
import com.cafe.utils.Day;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaffDAO extends Manager{
    public StaffDAO() throws SQLException {
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

    public List<Staff> convertToStaff(List<List<String>> data){
        List<Staff> staffList = new ArrayList<>();
        try{
            String staff_ID, name, gender, address, phone, email;
            double salary;
            Day dateOfBirth, dateOfEntry;
            boolean deleted;
            for (List<String> row : data){
                staff_ID = row.get(0);
                name = row.get(1);
                if (row.get(2).contains("0")){
                    gender = "Nữ";
                } else {
                    gender = "Nam";
                }
                dateOfBirth = Day.parseDay(row.get(3));
                address = row.get(4);
                phone = row.get(5);
                email = row.get(6);
                salary = Double.parseDouble(row.get(7));
                dateOfEntry = Day.parseDay(row.get(8));
                deleted = !row.get(9).contains("0");
                Staff staff = new Staff(staff_ID, name, gender, address, phone, email, salary, dateOfBirth, dateOfEntry, deleted);
                staffList.add(staff);
            }
        } catch(Exception ignored){

        }
        return staffList;
    }

    public List<Staff> readStaffs(String[] conditions){
        List<Staff> staffList = new ArrayList<>();
        try {
            staffList = convertToStaff(read(conditions));
        } catch (Exception ignored){

        }
        return staffList;
    }

    public int createStaff(String staff_ID, String name, String gender, String address, String phone, String email, double salary, Day dateOfBirth, Day dateOfEntry){
        if (readStaffs(new String[]{"STAFF_ID = '" + staff_ID + "'"}).size() != 0){
            return 0;
        }
        try {
            return super.create(staff_ID, name, gender, dateOfBirth, address, phone, email, salary, dateOfEntry, 0);
        } catch (Exception e){
            return 0;
        }
    }

    public int updateStaff(String staff_ID, String name, String gender, String address, String phone, String email, Day dateOfBirth, Day dateOfEntry){
        // Những giá trị là _ID sẽ được combobox cho user chọn
        try {
            Map<String, Object> updateValues = new HashMap<>();
            if (name != null){
                updateValues.put("NAME", name);
            }
            if (gender != null){
                if (gender.contains("Nữ")){
                    updateValues.put("GENDER", 0);
                }else {
                    updateValues.put("GENDER", 1);
                }
            }
            if (address != null){
                updateValues.put("ADDRESS",address);
            }
            if (phone != null){
                updateValues.put("PHONE",phone);
            }
            if (email != null){
                updateValues.put("EMAIL",email);
            }
            if (dateOfBirth != null){
                updateValues.put("DOB",dateOfBirth);
            }
            if (dateOfEntry != null){
                updateValues.put("DOENTRY",dateOfEntry);
            }
            return super.update(updateValues, "STAFF_ID = '" + staff_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }

    public int updateStaff(String staff_ID , double salary){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("SALARY", salary);
            return super.update(updateValues, "STAFF_ID = '" + staff_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }

    public int deleteStaff(String staff_ID){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("DELETED", 1);
            return super.update(updateValues, "STAFF_ID = '" + staff_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }
}
