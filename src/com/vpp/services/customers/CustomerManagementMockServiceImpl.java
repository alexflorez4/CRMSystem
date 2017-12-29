package com.vpp.services.customers;

import com.vpp.domain.Call;
import com.vpp.domain.Customer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomerManagementMockServiceImpl implements CustomerManagementService
{
    private HashMap<String, Customer> customerMap;

    public CustomerManagementMockServiceImpl()
    {
        customerMap = new HashMap<String, Customer>();
        customerMap.put("CS03939", new Customer("CS03939", "Acme Ltd", "Some notes."));
        customerMap.put("CS03940", new Customer("CS03940", "Virtual PP", "Some notes."));
        customerMap.put("CS03941", new Customer("CS03941", "Microsoft", "Some notes."));
    }

    @Override
    public void newCustomer(Customer newCustomer)
    {
        customerMap.put(newCustomer.getCustomerId(), newCustomer);
    }

    @Override
    public void updateCustomer(Customer changedCustomer)
    {
        customerMap.put(changedCustomer.getCustomerId(), changedCustomer);
    }

    @Override
    public void deleteCustomer(Customer oldCustomer)
    {
        customerMap.remove(oldCustomer.getCustomerId());
    }

    @Override
    public Customer findCustomerById(String customerId) throws CustomerNotFoundException
    {
        Customer foundCustomer = customerMap.get(customerId);
        if (foundCustomer == null)
        {
            throw new CustomerNotFoundException();
        }
        return foundCustomer;
    }

    @Override
    public List<Customer> findCustomerByName(String name)
    {
        List<Customer> results = new ArrayList<Customer>();
        for(Customer nextCustomer : customerMap.values())
        {
            if(nextCustomer.getCompanyName().equals(name))
            {
                results.add(nextCustomer);
            }
        }
        return results;
    }

    @Override
    public List<Customer> getAllCustomers()
    {
        return new ArrayList<Customer>(customerMap.values());
    }

    @Override
    public Customer getFullCustomerDetails(String customerId) throws CustomerNotFoundException
    {
        return this.findCustomerById(customerId);
    }

    @Override
    public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException {
        Customer customer = this.getFullCustomerDetails(customerId);
        customer.addCall(callDetails);
    }
}
