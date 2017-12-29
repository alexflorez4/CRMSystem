package com.vpp.client;

import com.vpp.domain.Action;
import com.vpp.domain.Call;
import com.vpp.domain.Customer;
import com.vpp.services.calls.CallHandlingService;
import com.vpp.services.customers.CustomerManagementService;
import com.vpp.services.customers.CustomerNotFoundException;
import com.vpp.services.diary.DiaryManagementService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

public class ClientApplication
{
    public static void main(String[] args) throws CustomerNotFoundException {
        ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application-jpa.xml");

        try {
            /*CustomerManagementService customerService = container.getBean("customerService", CustomerManagementService.class);

            customerService.newCustomer(new Customer("1001", "XYZ Company", "pepito@mail.com", "954123854", "new Customer 1"));
            List<Customer> allCustomers = customerService.getAllCustomers();
            for(Customer next : allCustomers)
            {
                System.out.println(next);
            }*/


            CustomerManagementService customerManagementService = container.getBean(CustomerManagementService.class);
            customerManagementService.newCustomer(new Customer("1010", "XYZ Company", "pepito@mail.com", "954123854", "new Customer 1"));
            //customerManagementService.newCustomer(new Customer("CS03939", "Acme", "Possible Client"));
            Customer tempCustomer = customerManagementService.findCustomerById("1001");
            System.out.println(tempCustomer + " " + tempCustomer.getEmail() + " Phone Number: " + tempCustomer.getTelephone());
            //tempCustomer.setEmail("info@acme.com");
            //tempCustomer.setTelephone("98712368524");
            //customerManagementService.updateCustomer(tempCustomer);
            //System.out.println(tempCustomer + " " + tempCustomer.getEmail() + " Phone Number: " + tempCustomer.getTelephone());

            CallHandlingService callService = container.getBean(CallHandlingService.class);
            DiaryManagementService diaryService = container.getBean(DiaryManagementService.class);

            Call newCall = new Call("Larry Wall Called from Acme Corp");
            Action action1 = new Action("Call back to check how things are going", new GregorianCalendar(2016, 0, 0), "af");
            Action action2 = new Action("Make sure Larry is being tracked", new GregorianCalendar(2016, 0, 0), "af");

            List<Action> actions = new ArrayList<Action>();
            actions.add(action1);
            actions.add(action2);

            try { callService.recordCall("1010", newCall, actions);
            } catch (CustomerNotFoundException e) {
                System.out.println("That customer does not exists.");
            }

            System.out.println("Here are the outstanding actions:");
            Collection<Action> incompleteActions = diaryService.getAllIncompleteAction("af");
            for (Action next : incompleteActions) {
                System.out.println(next.toString());
            }
        }
        finally {
            container.close();
        }



    }
}
