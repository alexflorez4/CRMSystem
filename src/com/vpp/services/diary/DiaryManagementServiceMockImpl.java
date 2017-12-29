package com.vpp.services.diary;

import com.vpp.domain.Action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class DiaryManagementServiceMockImpl implements DiaryManagementService
{
    private Set<Action> allActions = new HashSet<Action>();

    @Override
    public void recordAction(Action action)
    {
        allActions.add(action);
    }

    @Override
    public List<Action> getAllIncompleteAction(String requiredUser)
    {
        List<Action> results = new ArrayList<>();
        for(Action next : allActions)
        {
            if(next.getOwningUser().equals(requiredUser) && !next.isComplete() )
            {
                results.add(next);
            }
        }
        return results;
    }
}
