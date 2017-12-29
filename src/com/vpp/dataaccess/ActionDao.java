package com.vpp.dataaccess;

import com.vpp.exceptions.RecordNotFoundException;
import com.vpp.domain.Action;

import java.util.List;

public interface ActionDao
{
    public void create(Action action);
    public List<Action> getIncompleteAction(String requiredUser);
    public void update(Action actionToUpdate) throws RecordNotFoundException;
    public void delete(Action oldAction) throws RecordNotFoundException;
}
