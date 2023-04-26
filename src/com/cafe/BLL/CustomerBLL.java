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
            customerList = searchCustomers("DELETED = 0", "CUSTOMER_ID != 'CUS000'");
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

    public List<Customer> findCustomersBy(Map<String, Object> conditions) {
        List<Customer> customers = customerList;
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            customers = findObjectsBy(entry.getKey(), entry.getValue(), customers);
        return customers;
    }

    public boolean exists(Customer customer) {
        return !findCustomersBy(Map.of(
            "NAME", customer.getName(),
            "GENDER", customer.isGender(),
            "DOB", customer.getDateOfBirth(),
            "PHONE", customer.getPhone(),
            "MEMBERSHIP", customer.isMembership(),
            "DOSUP", customer.getDateOfSup()
        )).isEmpty();
    }

    public boolean exists(Map<String, Object> conditions) {
        return !findCustomersBy(conditions).isEmpty();
    }

    public String getAutoID() {
        return getAutoID("CUS", 3, searchCustomers("CUSTOMER_ID != 'CUS000'"));
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
