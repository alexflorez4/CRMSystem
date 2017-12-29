package com.vpp.domain.test;


import com.vpp.domain.Action;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class TestAction
{
    @Test
    public void futureActionAreNotOverdue()
    {
        //1. We'll set the required by date to be some time tomorrow
        GregorianCalendar requiredBy = new GregorianCalendar();
        requiredBy.add(Calendar.DAY_OF_WEEK, 1);
        Action testAction = new Action("A Test Action", requiredBy, null);
        boolean overdue = testAction.isOverdue();
        assertFalse(overdue);
    }

    @Test
    public void pastActionsAreOverdue()
    {
        GregorianCalendar requiredBy = new GregorianCalendar(1980,1,1,1,1,1);
        Action testAction = new Action("A test Action", requiredBy, null);
        boolean overdue = testAction.isOverdue();
        assertTrue(overdue);
    }
}
