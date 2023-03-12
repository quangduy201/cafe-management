package com.cafe.DAO;

import com.cafe.DTO.Bill;
import com.cafe.DTO.Category;
import com.cafe.DTO.CustomerDTO;
import com.cafe.utils.Day;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDAO extends Manager{
    public CustomerDAO() throws SQLException{
        super("customer", new ArrayList<>(
            List.of("CUSTOMER_ID",
                    "NAME",
                    "GENDER",
                    "DOB",
                    "PHONE",
                    "MEMBERSHIP",
                    "DOSUP",
                    "DELETED")
        ));
    }

    public List<CustomerDTO> getCustomers(){
        List<CustomerDTO> customerDTOList = new ArrayList<CustomerDTO>();
        try {
            CustomerDAO CustomerDAO = new CustomerDAO();
            String customer_ID, name, gender, phone;
            Day dateOfBirth, dateOfSub;
            boolean membership, deleted;
            List<List<String>> customers = CustomerDAO.read();
            for (List<String> row : customers) {
                customer_ID = row.get(0);
                name = row.get(1);
                if (row.get(2).indexOf("0") != -1){
                    gender = "Nữ";
                }else {
                    gender = "Nam";
                }
                dateOfBirth = Day.parseDay(row.get(3));
                phone = row.get(4);
                if (row.get(5).indexOf("0") != -1){
                    membership = false;
                    dateOfSub = null;
                }else {
                    membership = true;
                    dateOfSub = Day.parseDay(row.get(6));
                }
                if (row.get(7).indexOf("0") != -1){
                    deleted = false;
                }else {
                    deleted = true;
                }
                CustomerDTO customer = new CustomerDTO(customer_ID, name, gender, phone, dateOfBirth, dateOfSub, membership, deleted);
                customerDTOList.add(customer);
            }
        } catch (Exception e){

        }
        return customerDTOList;
    }

    public CustomerDTO readByPrimaryKey(String customer_ID){
        try {
            for (CustomerDTO customerDTO: getCustomers()) {
                if (customerDTO.getCustomer_ID().indexOf(customer_ID) != -1){
                    return customerDTO;
                }
            }
        } catch (Exception e){
            return null;
        }
        return  null;
    }

    public List<CustomerDTO> readByName(String name){
        List<CustomerDTO> result = new ArrayList<CustomerDTO>();
        try {
            for (CustomerDTO customerDTO: getCustomers()) {
                if (customerDTO.getName().indexOf(name) != -1){
                    result.add(customerDTO);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<CustomerDTO> readByGender(String gender){
        List<CustomerDTO> result = new ArrayList<CustomerDTO>();
        try {
            for (CustomerDTO customerDTO: getCustomers()) {
                if (customerDTO.getGender().indexOf(gender) != -1){
                    result.add(customerDTO);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<CustomerDTO> readByAge(String compare, int age){
        List<CustomerDTO> result = new ArrayList<CustomerDTO>();
        try {
            if (compare.indexOf("<") != -1){
                for (CustomerDTO customerDTO : getCustomers() ) {
                    if (2023 - customerDTO.getDateOfBirth().getYear() < age){
                        result.add(customerDTO);
                    }
                }
            } else if (compare.indexOf("<=") != -1) {
                for (CustomerDTO customerDTO : getCustomers() ) {
                    if (2023 - customerDTO.getDateOfBirth().getYear() <= age){
                        result.add(customerDTO);
                    }
                }
            } else if (compare.indexOf(">") != -1) {
                for (CustomerDTO customerDTO : getCustomers() ) {
                    if (2023 - customerDTO.getDateOfBirth().getYear() > age){
                        result.add(customerDTO);
                    }
                }
            } else if (compare.indexOf(">=") != -1) {
                for (CustomerDTO customerDTO : getCustomers() ) {
                    if (2023 - customerDTO.getDateOfBirth().getYear() >= age){
                        result.add(customerDTO);
                    }
                }
            } else if (compare.indexOf("=") != -1) {
                for (CustomerDTO customerDTO : getCustomers() ) {
                    if (2023 - customerDTO.getDateOfBirth().getYear() == age){
                        result.add(customerDTO);
                    }
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<CustomerDTO> readByMemberhip(boolean membership){
        List<CustomerDTO> result = new ArrayList<CustomerDTO>();
        try {
            for (CustomerDTO customerDTO: getCustomers()) {
                if (customerDTO.isMembership() == membership){
                    result.add(customerDTO);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<CustomerDTO> readByDOSUB(Day DOSUB){
        List<CustomerDTO> result = new ArrayList<CustomerDTO>();
        try {
            for (CustomerDTO customerDTO: getCustomers()) {
                if (customerDTO.getDateOfSup().equals(DOSUB)){
                    result.add(customerDTO);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<CustomerDTO> readByDOSUB(Day DOSUB1, Day DOSUB2){
        List<CustomerDTO> result = new ArrayList<CustomerDTO>();
        try {
            for (CustomerDTO customerDTO: getCustomers()) {
                if (customerDTO.getDateOfSup().compare(DOSUB1, DOSUB2)){
                    result.add(customerDTO);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<CustomerDTO> readByDeleted(boolean deleted){
        List<CustomerDTO> result = new ArrayList<CustomerDTO>();
        try {
            for (CustomerDTO customerDTO: getCustomers()) {
                if (customerDTO.isDeleted() == deleted){
                    result.add(customerDTO);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public int create(String customer_ID, String name, String gender, String phone, Day dateOfBirth, Day dateOfSup, boolean membership){
        if (readByPrimaryKey(customer_ID) != null){
            return 0;
        }
        try {
            return super.create(customer_ID, name, gender, dateOfBirth, phone, membership, dateOfSup, 0);
        } catch (Exception e){
            return 0;
        }
    }

    public int update(String customer_ID, String name, String gender, String phone, Day dateOfBirth, Day dateOfSup){
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
            if (phone != null){
                updateValues.put("PHONE",phone);
            }
            if (dateOfSup != null){
                updateValues.put("DOB",dateOfBirth);
            }
            if (dateOfSup != null){
                updateValues.put("DOSUP",dateOfSup);
            }
            return super.update(updateValues, "CUSTOMER_ID = '" + customer_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }

    public int update(String customer_ID , boolean membership){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("MEMBERSHIP", membership);
            return super.update(updateValues, "CUSTOMER_ID = '" + customer_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }

    public int delete(String customer_ID){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("DELETED", 1);
            return super.update(updateValues, "CUSTOMER_ID = '" + customer_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }
}
