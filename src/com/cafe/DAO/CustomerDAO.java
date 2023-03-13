package com.cafe.DAO;
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

    public List<CustomerDTO> convertToCustomer(List<List<String>> data){
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        try {
            String customer_ID, name, gender, phone;
            Day dateOfBirth, dateOfSub;
            boolean membership, deleted;
            for (List<String> row : data) {
                customer_ID = row.get(0);
                name = row.get(1);
                if (row.get(2).contains("0")){
                    gender = "Nữ";
                }else {
                    gender = "Nam";
                }
                dateOfBirth = Day.parseDay(row.get(3));
                phone = row.get(4);
                if (row.get(5).contains("0")){
                    membership = false;
                    dateOfSub = null;
                }else {
                    membership = true;
                    dateOfSub = Day.parseDay(row.get(6));
                }
                deleted = !row.get(7).contains("0");
                CustomerDTO customer = new CustomerDTO(customer_ID, name, gender, phone, dateOfBirth, dateOfSub, membership, deleted);
                customerDTOList.add(customer);
            }
        } catch (Exception ignored){

        }
        return customerDTOList;
    }

    public List<CustomerDTO> readCustomers(String[] conditions){
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        try {
            customerDTOList = convertToCustomer(read(conditions));
        } catch (Exception ignored){

        }
        return customerDTOList;
    }

    public int createCustomer(String customer_ID, String name, String gender, String phone, Day dateOfBirth, Day dateOfSup, boolean membership){
        if (readCustomers(new String[]{"CUSTOMER_ID = '" + customer_ID + "'"}).size() != 0){
            return 0;
        }
        try {
            return super.create(customer_ID, name, gender, dateOfBirth, phone, membership, dateOfSup, 0);
        } catch (Exception e){
            return 0;
        }
    }

    public int updateCustomer(String customer_ID, String name, String gender, String phone, Day dateOfBirth, Day dateOfSup){
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

    public int updateCustomer(String customer_ID , boolean membership){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("MEMBERSHIP", membership);
            return super.update(updateValues, "CUSTOMER_ID = '" + customer_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }

    public int deleteCustomer(String customer_ID){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("DELETED", 1);
            return super.update(updateValues, "CUSTOMER_ID = '" + customer_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }
}
