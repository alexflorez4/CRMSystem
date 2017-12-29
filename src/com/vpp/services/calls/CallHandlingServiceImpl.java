package com.vpp.services.calls;

import com.vpp.domain.Action;
import com.vpp.domain.Call;
import com.vpp.services.customers.CustomerManagementService;
import com.vpp.services.customers.CustomerNotFoundException;
import com.vpp.services.diary.DiaryManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional
@Service
public class CallHandlingServiceImpl implements CallHandlingService
{
    @Autowired
    private CustomerManagementService customerService;
    @Autowired
    private DiaryManagementService diaryService;

    public CallHandlingServiceImpl(){}

    //Constructor injection
    public CallHandlingServiceImpl(CustomerManagementService cms, DiaryManagementService dms)
    {
        this.customerService = cms;
        this.diaryService = dms;
    }

    @Override
    public void recordCall(String customerId, Call newCall, Collection<Action> actions) throws CustomerNotFoundException
    {
        //1. Call the customer service to record the call
        customerService.recordCall(customerId, newCall);
        //2. Call the diary service to record the actions
        for(Action nextAction : actions)
        {
            diaryService.recordAction(nextAction);
        }

       // throw new NullPointerException();
    }
}
