package com.vpp.integrationtests;

import com.vpp.domain.Action;
import com.vpp.domain.Call;
import com.vpp.domain.Customer;
import com.vpp.services.calls.CallHandlingService;
import com.vpp.services.customers.CustomerManagementService;
import com.vpp.services.customers.CustomerNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/daos.xml", "/services.xml", "/misc-beans.xml", "/datasource-test.xml"})  //the config xml were broken to have more flex for testing.
//@Transactional  //this is for not commiting to the db.  It rolls back the transaction.
public class CustomerManagementIntegrationTests
{
    @Autowired
    private CustomerManagementService customerService;

    @Autowired
    private CallHandlingService callsService;

    @Test
    public void testCreatingCustomerRecord()
    {
        Customer testCustomer = new Customer("9191", "VPP", "email", "123", "customer notes");
        customerService.newCustomer(testCustomer);

        List<Customer> allCustomers = customerService.getAllCustomers();
        int numberOfCustomers = allCustomers.size();

        assertEquals(1, numberOfCustomers);
    }

    @Test
    public void testFindingCustomer()
    {
        Customer testCustomer = new Customer("9191", "VPP", "email", "123", "customer notes");
        customerService.newCustomer(testCustomer);
        try
        {
            Customer foundCustomer = customerService.findCustomerById("9191");
            assertEquals(testCustomer, foundCustomer);
        }
        catch (CustomerNotFoundException e)
        {
            fail("Customer not found");
        }
    }

    @Test
    public void testAddingACallToACustomer()
    {
        Customer testCustomer = new Customer("9191", "VPP", "email", "123", "customer notes");
        customerService.newCustomer(testCustomer);

        Call testCall = new Call("Just a test call");
        List<Action> actions = new ArrayList<Action>();

        try
        {
            callsService.recordCall("9191", testCall, actions);
            Customer foundCustomer = customerService.getFullCustomerDetails("9191");
            Call foundCall = foundCustomer.getCalls().get(0);
            assertEquals(testCall, foundCall);
        }
        catch (CustomerNotFoundException e)
        {
            fail("Customer was not found");
        }
    }
}
