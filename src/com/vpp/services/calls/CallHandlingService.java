package com.vpp.services.calls;

import com.vpp.domain.Action;
import com.vpp.domain.Call;
import com.vpp.services.customers.CustomerNotFoundException;

import java.util.Collection;

/**
 * Created by alexf on 7/12/2017.
 */
public interface CallHandlingService
{
    /**
     * Records a call with the customer management service, and also records any action
     * in the dairy service
     */
    public void recordCall(String customerId, Call newCall, Collection<Action> action) throws CustomerNotFoundException;
}
