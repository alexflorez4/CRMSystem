package com.vpp.services.customers;

import com.vpp.domain.Call;
import com.vpp.domain.Customer;
import org.springframework.stereotype.Component;

import java.util.List;

public interface CustomerManagementService
{
    public void newCustomer(Customer newCustomer);
    public void updateCustomer(Customer changedCustomer) throws CustomerNotFoundException;
    public void deleteCustomer(Customer oldCustomer) throws CustomerNotFoundException;
    public Customer findCustomerById(String customerId) throws CustomerNotFoundException;
    public List<Customer> findCustomerByName(String name);
    public List<Customer> getAllCustomers();
    public Customer getFullCustomerDetails(String customerId) throws CustomerNotFoundException;
    public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException;
}
