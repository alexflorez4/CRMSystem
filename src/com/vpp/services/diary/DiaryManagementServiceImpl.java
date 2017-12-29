package com.vpp.services.diary;

import com.vpp.dataaccess.ActionDao;
import com.vpp.dataaccess.CustomerDao;
import com.vpp.domain.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class DiaryManagementServiceImpl implements DiaryManagementService
{
    @Autowired
    private ActionDao dao;

    public DiaryManagementServiceImpl(){}

    public DiaryManagementServiceImpl(ActionDao actionDao)
    {
        this.dao = actionDao;
    }

    @Override
    public void recordAction(Action action)
    {
        dao.create(action);
    }

    @Override
    public List<Action> getAllIncompleteAction(String requiredUser)
    {
        return dao.getIncompleteAction(requiredUser);
    }
}
