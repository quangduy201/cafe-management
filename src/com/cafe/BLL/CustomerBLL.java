package com.cafe.BLL;

import com.cafe.DAL.CustomerDAL;
import com.cafe.DTO.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerBLL extends Manager<Customer> {
    private CustomerDAL customerDAL;
    private List<Customer> customerList;

    public CustomerBLL() {
        try {
            customerDAL = new CustomerDAL();
            customerList = searchCustomers("DELETED = 0");
        } catch (Exception ignored) {

        }
    }

    public CustomerDAL getCustomerDAL() {
        return customerDAL;
    }

    public void setCustomerDAL(CustomerDAL customerDAL) {
        this.customerDAL = customerDAL;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public Object[][] getData() {
        return getData(customerList);
    }

    public boolean addCustomer(Customer customer) {
        if (getIndex(customer, "PHONE", customerList) != -1) {
            System.out.println("Can't add new customer. Phone already exists.");
            return false;
        }
        customerList.add(customer);
        return customerDAL.addCustomer(customer) != 0;
    }

    public boolean updateCustomer(Customer customer) {
        customerList.set(getIndex(customer, "CUSTOMER_ID", customerList), customer);
        return customerDAL.updateCustomer(customer) != 0;
    }

    public boolean deleteCustomer(Customer customer) {
        customerList.remove(getIndex(customer, "CUSTOMER_ID", customerList));
        return customerDAL.deleteCustomer("CUSTOMER_ID = '" + customer.getCustomerID() + "'") != 0;
    }

    public List<Customer> searchCustomers(String... conditions) {
        return customerDAL.searchCustomers(conditions);
    }

    public List<Customer> findCustomers(String key, Object value) {
        List<Customer> list = new ArrayList<>();
        for (Customer customer : customerList) {
            if (value instanceof Boolean) {
                if (getValueByKey(customer, key) == value) {
                    list.add(customer);
                }
            } else {
                if (getValueByKey(customer, key).toString().toLowerCase().contains(value.toString().toLowerCase())) {
                    list.add(customer);
                }
            }

        }
        return list;
    }

    public List<Customer> findCustomerBy(Map<String, Object> conditions) {
        List<Customer> customers = customerList;
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            customers = findObjectsBy(entry.getKey(), entry.getValue(), customers);
        return customers;
    }

    public String getAutoID() {
        try {
            return getAutoID("CUS", 3, searchCustomers());
        } catch (Exception e) {
            System.out.println("Error occurred in CustomerBLL.getAutoID(): " + e.getMessage());
        }
        return "";
    }

    @Override
    public Object getValueByKey(Customer customer, String key) {
        return switch (key) {
            case "CUSTOMER_ID" -> customer.getCustomerID();
            case "NAME" -> customer.getName();
            case "GENDER" -> customer.isGender();
            case "DOB" -> customer.getDateOfBirth();
            case "PHONE" -> customer.getPhone();
            case "MEMBERSHIP" -> customer.isMembership();
            case "DOSUP" -> customer.getDateOfSup();
            default -> null;
        };
    }
}
