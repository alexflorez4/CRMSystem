package com.vpp.dataaccess;

import com.vpp.exceptions.RecordNotFoundException;
import com.vpp.domain.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public class ActionDaoImpl implements ActionDao
{
    public static final String CREATE_TABLE_ACTION = "CREATE TABLE ACTION (ACTION_ID integer generated by default as identity (start with 1), DETAILS VARCHAR(255), COMPLETE BOOLEAN, OWNING_USER VARCHAR(20), REQUIRED_BY DATE)";
    public static final String INSERT_ACTION = "INSERT INTO ACTION (DETAILS, COMPLETE, OWNING_USER, REQUIRED_BY) VALUES (?,?,?,?)";
    public static final String GET_INCOMPLETE_ACTIONS = "SELECT ACTION_ID, DETAILS, COMPLETE, OWNING_USER, REQUIRED_BY FROM ACTION WHERE OWNING_USER=? AND COMPLETE=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ActionDaoImpl(){}

    public ActionDaoImpl(JdbcTemplate template)
    {
        this.jdbcTemplate = template;
    }

    @Override
    public void create(Action action)
    {
        jdbcTemplate.update(INSERT_ACTION, action.getDetails(), action.isComplete(), action.getOwningUser(), action.getRequiredBy());
    }

    @Override
    public List<Action> getIncompleteAction(String requiredUser)
    {
        return jdbcTemplate.query(GET_INCOMPLETE_ACTIONS, new ActionRowMapper(), requiredUser, false);
    }

    @PostConstruct
    public void createTables()
    {
        try
        {
            this.jdbcTemplate.update(CREATE_TABLE_ACTION);
        }
        catch (org.springframework.jdbc.BadSqlGrammarException e)
        {
            System.out.println("Assuming the Action table exists");
        }
    }

    @Override
    public void update(Action actionToUpdate) throws RecordNotFoundException {

    }

    @Override
    public void delete(Action oldAction) throws RecordNotFoundException {

    }
}

class ActionRowMapper implements RowMapper<Action>
{
    @Override
    public Action mapRow(ResultSet resultSet, int rowNumber) throws SQLException
    {
        int actionId = resultSet.getInt(1);
        String details = resultSet.getString(2);
        boolean complete = resultSet.getBoolean(3);
        String owningUser = resultSet.getString(4);
        Date requiredBy = resultSet.getDate(5);

        Calendar requiredByCal = new java.util.GregorianCalendar();
        requiredByCal.setTime(requiredBy);

        return new Action("" + actionId,details, requiredByCal, owningUser, complete);
    }
}