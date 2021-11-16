package com.gyl.library.services;

import com.gyl.library.entities.CustomerEntity;

import java.util.List;

public interface CustomerService {

    public List<CustomerEntity> getAllCustomersOrderByName() throws Exception;
    public void createCustomer(Long dni, String firstName, String lastName, String phone) throws Exception;
    public void updateCustomer(Integer id_customer, Long dni, String firstName, String lastName, String phone, Boolean activate) throws Exception;
    public void deleteCustomer(Integer id_customer) throws Exception;
    public CustomerEntity findCustomerById(Integer id_customer) throws Exception;
    public List<CustomerEntity> getAllCustomersActivated() throws Exception;
    public List<CustomerEntity> getAllCustomersNoActivated() throws Exception;
    public CustomerEntity findCustomerByLastNameAndFistName(String firstName, String lastName) throws Exception;
    public CustomerEntity findCustomerByDni(Long dni) throws Exception;

}