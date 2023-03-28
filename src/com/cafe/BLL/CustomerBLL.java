package com.cafe.BLL;

import com.cafe.DAL.CustomerDAL;
import com.cafe.DTO.Customer;

import java.util.List;

public class CustomerBLL {
    private CustomerDAL customerDAL;

    public CustomerBLL() {
        try {
            customerDAL = new CustomerDAL();
        } catch (Exception ignored) {

        }
    }

    public boolean insertCustomer(Customer customer) {
        if (searchCustomers("PHONE = '" + customer.getPhone() + "'").size() != 0) {
            System.out.println("Can't insert new customer. Phone already exists.");
            return false;
        }
        return customerDAL.insertCustomer(customer) != 0;
    }

    public boolean updateCustomer(Customer customer) {
        return customerDAL.updateCustomer(customer) != 0;
    }

    public boolean removeCustomer(String id) {
        return customerDAL.deleteCustomer("CUSTOMER_ID = '" + id + "'") != 0;
    }

    public List<Customer> searchCustomers(String... conditions) {
        return customerDAL.searchCustomers(conditions);
    }

    public String getAutoID() {
        return customerDAL.getAutoID();
    }
}
