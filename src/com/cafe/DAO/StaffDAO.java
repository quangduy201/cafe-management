package com.cafe.DAO;

import com.cafe.DTO.CustomerDTO;
import com.cafe.DTO.Product;
import com.cafe.DTO.Recipe;
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

    public List<Staff> getStaffs(){
        List<Staff> staffList = new ArrayList<Staff>() {
        };
        try{
            StaffDAO staffDAO = new StaffDAO();
            List<List<String>> staffs = staffDAO.read();
            for (List<String> row : staffs){
                String staff_ID, name, gender, address, phone, email;
                double salary;
                Day dateOfBirth, dateOfEntry;
                boolean deleted;
                staff_ID = row.get(0);
                name = row.get(1);
                if (row.get(2).indexOf("0") != -1){
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
                if (row.get(9).indexOf("0") != -1){
                    deleted = false;
                }else {
                    deleted = true;
                }
                Staff staff = new Staff(staff_ID, name, gender, address, phone, email, salary, dateOfBirth, dateOfEntry, deleted);
                staffList.add(staff);
            }
        } catch(Exception e){

        }
        return staffList;
    }

    public Staff readByPrimaryKey(String staff_ID){
        try {
            for (Staff staff : getStaffs()){
                if (staff.getStaff_ID().indexOf(staff_ID) != -1){
                    return staff;
                }
            }
        } catch(Exception e){
            return null;
        }
        return null;
    }

    public List<Staff> readByName(String name){
        List<Staff> result = new ArrayList<Staff>();
        try {
            for (Staff staff : getStaffs()){
                if (staff.getName().indexOf(name) != -1){
                    result.add(staff);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<Staff> readByGender(String gender){
        List<Staff> result = new ArrayList<Staff>();
        try {
            for (Staff staff : getStaffs()){
                if (staff.getGender().indexOf(gender) != -1){
                    result.add(staff);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<Staff> readByAge(String compare, int age){
        List<Staff> result = new ArrayList<Staff>();
        try {
            if (compare.indexOf("<") != -1){
                for (Staff staff : getStaffs() ) {
                    if (2023 - staff.getDateOfBirth().getYear() < age){
                        result.add(staff);
                    }
                }
            } else if (compare.indexOf("<=") != -1) {
                for (Staff staff : getStaffs() ) {
                    if (2023 - staff.getDateOfBirth().getYear() <= age){
                        result.add(staff);
                    }
                }
            } else if (compare.indexOf(">") != -1) {
                for (Staff staff : getStaffs() ) {
                    if (2023 - staff.getDateOfBirth().getYear() > age){
                        result.add(staff);
                    }
                }
            } else if (compare.indexOf(">=") != -1) {
                for (Staff staff : getStaffs() ) {
                    if (2023 - staff.getDateOfBirth().getYear() >= age){
                        result.add(staff);
                    }
                }
            } else if (compare.indexOf("=") != -1) {
                for (Staff staff : getStaffs() ) {
                    if (2023 - staff.getDateOfBirth().getYear() == age){
                        result.add(staff);
                    }
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<Staff> readByDOENTRY(Day DOENTRY){
        List<Staff> result = new ArrayList<Staff>();
        try {
            for (Staff staff: getStaffs()) {
                if (staff.getDateOfEntry().equals(DOENTRY)){
                    result.add(staff);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<Staff> readByDOENTRY(Day DOENTRY1, Day DOENTRY2){
        List<Staff> result = new ArrayList<Staff>();
        try {
            for (Staff staff: getStaffs()) {
                if (staff.getDateOfEntry().compare(DOENTRY1, DOENTRY2)){
                    result.add(staff);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<Staff> readBySalary(String compare, double salary){
        List<Staff> result = new ArrayList<Staff>();
        try {
            if (compare.indexOf("<") != -1){
                for (Staff staff : getStaffs() ) {
                    if (staff.getSalary() < salary){
                        result.add(staff);
                    }
                }
            } else if (compare.indexOf("<=") != -1) {
                for (Staff staff : getStaffs() ) {
                    if (staff.getSalary() <= salary){
                        result.add(staff);
                    }
                }
            } else if (compare.indexOf(">") != -1) {
                for (Staff staff : getStaffs() ) {
                    if (staff.getSalary() > salary){
                        result.add(staff);
                    }
                }
            } else if (compare.indexOf(">=") != -1) {
                for (Staff staff : getStaffs() ) {
                    if (staff.getSalary() >= salary){
                        result.add(staff);
                    }
                }
            } else if (compare.indexOf("=") != -1) {
                for (Staff staff : getStaffs() ) {
                    if (staff.getSalary() == salary){
                        result.add(staff);
                    }
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<Staff> readBySalary(double salary1, double salary2){
        List<Staff> result = new ArrayList<Staff>();
        try {
            for (Staff staff : getStaffs()){
                if (staff.getSalary() >= salary1 && staff.getSalary() <= salary2){
                    result.add(staff);
                }
            }
        } catch(Exception e){
        }
        return result;
    }

    public List<Staff> readByDeleted(boolean deleted){
        List<Staff> result = new ArrayList<Staff>();
        try {
            for (Staff staff : getStaffs()){
                if (staff.isDeleted() == deleted){
                    result.add(staff);
                }
            }
        } catch(Exception e){
        }
        return result;
    }

    public int create(String staff_ID, String name, String gender, String address, String phone, String email, double salary, Day dateOfBirth, Day dateOfEntry){
        if (readByPrimaryKey(staff_ID) != null){
            return 0;
        }
        try {
            return super.create(staff_ID, name, gender, dateOfBirth, address, phone, email, salary, dateOfEntry, 0);
        } catch (Exception e){
            return 0;
        }
    }

    public int update(String staff_ID, String name, String gender, String address, String phone, String email, Day dateOfBirth, Day dateOfEntry){
        // Những giá trị là _ID sẽ được combobox cho user chọn
        try {
            Map<String, Object> updateValues = new HashMap<>();
            if (name != null){
                updateValues.put("NAME", name);
            }
            if (gender != null){
                if (gender.indexOf("Nữ") != -1){
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

    public int update(String staff_ID , double salary){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("SALARY", salary);
            return super.update(updateValues, "STAFF_ID = '" + staff_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }

    public int delete(String staff_ID){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("DELETED", 1);
            return super.update(updateValues, "STAFF_ID = '" + staff_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }
}
