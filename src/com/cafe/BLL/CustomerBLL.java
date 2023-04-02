package com.cafe.BLL;

import com.cafe.DAL.CustomerDAL;
import com.cafe.DTO.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class CustomerBLL {
    private CustomerDAL customerDAL;
    private List<Customer> customerList;
    public CustomerBLL() {
        try {
            customerDAL = new CustomerDAL();
            customerList = searchCustomers();
            readCustomer();
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

    public boolean insertCustomer(Customer customer) {
        if (searchCustomers("PHONE = '" + customer.getPhone() + "'").size() != 0) {
            System.out.println("Can't insert new customer. Phone already exists.");
            return false;
        }
        customerList.add(customer);
        return customerDAL.insertCustomer(customer) != 0;
    }

    public boolean updateCustomer(Customer customer) {
        customerList.set(getIndex(customer, "CUSTOMER_ID"), customer);
        return customerDAL.updateCustomer(customer) != 0;
    }

    public boolean removeCustomer(String id) {
        List<Customer> list = new ArrayList<>();
        for (Customer customer : customerList){
            if (!customer.getCustomerID().contains(id)){
                list.add(customer);
            }
        }
        customerList = list;
        return customerDAL.deleteCustomer("CUSTOMER_ID = '" + id + "'") != 0;
    }

    public List<Customer> searchCustomers(String... conditions) {
        return customerDAL.searchCustomers(conditions);
    }

    public Object[][] getData (){
        Object [][] data = new Object[customerList.size()][];
        for (int i=0; i<data.length; i++){
            data[i] = customerList.get(i).toString().split(" \\| ");
        }
        return data;
    }

    public int getIndex(Customer customer, String key) {
        return IntStream.range(0, customerList.size())
            .filter(i -> Objects.equals(getValueByKey(customerList.get(i), key), getValueByKey(customer, key)))
            .findFirst()
            .orElse(-1);
    }

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

    public void readCustomer(){
        List<Customer> list = new ArrayList<>();
        for (Customer customer : customerList){
            if (!customer.isDeleted()){
                list.add(customer);
            }
        }
        customerList = list;
    }

    public List<Customer> findCustomers(String key, Object value){
        List<Customer> list = new ArrayList<>();
        for (Customer customer : customerList){
            if (value instanceof Boolean) {
                if (getValueByKey(customer, key) == value){
                    list.add(customer);
                }
            } else {
                if (getValueByKey(customer, key).toString().toLowerCase().contains(value.toString().toLowerCase())){
                    list.add(customer);
                }
            }

        }
        return list;
    }

    public String getAutoID() {
        return customerDAL.getAutoID();
    }
}
