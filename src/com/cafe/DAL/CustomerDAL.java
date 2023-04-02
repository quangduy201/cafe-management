package com.cafe.DAL;

import com.cafe.DTO.Customer;
import com.cafe.utils.Day;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAL extends Manager {
    public CustomerDAL() throws SQLException {
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

    public List<Customer> convertToCustomers(List<List<String>> data) {
        return convert(data, row -> {
            row.set(2, row.get(2).equals("0") ? "false" : "true");
            row.set(5, row.get(5).equals("0") ? "false" : "true");
            row.set(row.size() - 1, row.get(row.size() - 1).equals("0") ? "false" : "true");
            try {
                return new Customer(
                    row.get(0), // customerID
                    row.get(1), // name
                    Boolean.parseBoolean(row.get(2)), // gender
                    Day.parseDay(row.get(3)), // dateOfBirth
                    row.get(4), // phone
                    Boolean.parseBoolean(row.get(5)), // membership
                    Day.parseDay(row.get(6)), // dateOfSup
                    Boolean.parseBoolean(row.get(7)) // deleted
                );
            } catch (Exception e) {
                System.out.println("Error occurred in DAL.convertTos(): " + e.getMessage());
            }
            return new Customer();
        });
    }

    public int addCustomer(Customer customer) {
        try {
            return create(customer.getCustomerID(),
                customer.getName(),
                customer.isGender(),
                customer.getDateOfBirth(),
                customer.getPhone(),
                customer.isMembership(),
                customer.getDateOfSup(),
                false
            ); // customer khi tạo mặc định deleted = 0
        } catch (Exception e) {
            System.out.println("Error occurred in CustomerDAL.addCustomer(): " + e.getMessage());
        }
        return 0;
    }

    public int updateCustomer(Customer customer) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(customer.getCustomerID());
            updateValues.add(customer.getName());
            updateValues.add(customer.isGender());
            updateValues.add(customer.getDateOfBirth());
            updateValues.add(customer.getPhone());
            updateValues.add(customer.isMembership());
            updateValues.add(customer.getDateOfSup());
            updateValues.add(customer.isDeleted());
            return update(updateValues, "CUSTOMER_ID = '" + customer.getCustomerID() + "'");
        } catch (Exception e) {
            System.out.println("Error occurred in CustomerDAL.updateCustomer(): " + e.getMessage());
        }
        return 0;
    }

    public int deleteCustomer(String... conditions) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(true);
            return update(updateValues, conditions);
        } catch (Exception e) {
            System.out.println("Error occurred in CustomerDAL.deleteCustomer(): " + e.getMessage());
        }
        return 0;
    }

    public List<Customer> searchCustomers(String... conditions) {
        try {
            return convertToCustomers(read(conditions));
        } catch (Exception e) {
            System.out.println("Error occurred in CustomerDAL.searchCustomers(): " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
