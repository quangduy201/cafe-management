package com.cafe.BLL;

import com.cafe.DAL.CustomerDAL;
import com.cafe.DTO.Customer;

import java.util.List;
import java.util.Map;

public abstract class CustomerBLL extends Manager<Customer> {
    private CustomerDAL customerDAL;
    private List<Customer> customerList;

    public CustomerBLL() {
        try {
            customerDAL = new CustomerDAL();
            customerList = searchCustomers();
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

    public Object[][] getDate() {
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
        customer.setDeleted(true);
        customerList.set(getIndex(customer, "CUSTOMER_ID", customerList), customer);
        return customerDAL.deleteCustomer("CUSTOMER_ID = '" + customer.getCustomerID() + "'") != 0;
    }

    public List<Customer> searchCustomers(String... conditions) {
        return customerDAL.searchCustomers(conditions);
    }

    public List<Customer> findCustomerBy(Map<String, Object> conditions) {
        List<Customer> customers = customerList;
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            customers = findObjectsBy(entry.getKey(), entry.getValue(), customers);
        return customers;
    }

    public String getAutoID() {
        try {
            return getAutoID("CUS", 3, customerList);
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
            case "GENDER" -> customer.getGender();
            case "DOB" -> customer.getDateOfBirth();
            case "PHONE" -> customer.getPhone();
            case "MEMBERSHIP" -> customer.isMembership();
            case "DOSUP" -> customer.getDateOfSup();
            default -> null;
        };
    }
}
