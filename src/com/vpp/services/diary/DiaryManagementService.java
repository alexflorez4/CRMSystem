package com.vpp.services.diary;

import com.vpp.domain.Action;
import java.util.List;

public interface DiaryManagementService
{
    public void recordAction(Action action);
    public List<Action> getAllIncompleteAction(String requiredUser);
}
