package com.vpp.services.customers;

import com.vpp.exceptions.RecordNotFoundException;
import com.vpp.dataaccess.CustomerDao;
import com.vpp.domain.Call;
import com.vpp.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CustomerManagementServiceImpl implements CustomerManagementService
{
    @Autowired
    private CustomerDao dao;

    public CustomerManagementServiceImpl(){}

    public CustomerManagementServiceImpl(CustomerDao customerDao)
    {
        this.dao = customerDao;
    }

    @Override
    public void newCustomer(Customer newCustomer)
    {
        dao.create(newCustomer);
    }

    @Override
    public void updateCustomer(Customer changedCustomer) throws CustomerNotFoundException {
        try
        {
            dao.update(changedCustomer);
        }
        catch (RecordNotFoundException e)
        {
            throw new CustomerNotFoundException();
        }
    }

    @Override
    public void deleteCustomer(Customer oldCustomer) throws CustomerNotFoundException {
        try
        {
            dao.delete(oldCustomer);
        }
        catch (RecordNotFoundException e)
        {
            throw new CustomerNotFoundException();
        }
    }

    @Override
    public Customer findCustomerById(String customerId) throws CustomerNotFoundException
    {
        try
        {
            return dao.getById(customerId);
        }
        catch (RecordNotFoundException e)
        {
            throw new CustomerNotFoundException();
        }
    }

    @Override
    public List<Customer> findCustomerByName(String name)
    {
        return dao.getByName(name);
    }

    @Override
    public List<Customer> getAllCustomers()
    {
        return dao.getAllCustomers();
    }

    @Override
    public Customer getFullCustomerDetails(String customerId) throws CustomerNotFoundException
    {
        try
        {
            return dao.getFullCustomerDetail(customerId);
        }
        catch (RecordNotFoundException e)
        {
            throw new CustomerNotFoundException();
        }
    }

    @Override
    public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException
    {
        try
        {
            dao.addCall(callDetails, customerId);
        }
        catch (RecordNotFoundException e)
        {
            throw new CustomerNotFoundException();
        }
    }
}
