package com.vpp.dataaccess;

import com.vpp.exceptions.RecordNotFoundException;
import com.vpp.domain.Call;
import com.vpp.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class CustomerDaoImpl implements CustomerDao
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String CREATE_CUSTOMER_TABLE_SQL = "CREATE TABLE CUSTOMER(CUSTOMER_ID VARCHAR(20), COMPANY_NAME VARCHAR(50), EMAIL VARCHAR(50), TELEPHONE VARCHAR(20), NOTES VARCHAR(255))";
    private static final String CREATE_CALL_TABLE_SQL = "CREATE TABLE TBL_CALL(NOTES VARCHAR(255), TIME_AND_DATE DATE, CUSTOMER_ID VARCHAR(20))";
    private static final String INSERT_CUSTOMER_SQL = "INSERT INTO CUSTOMER (CUSTOMER_ID, COMPANY_NAME, EMAIL, TELEPHONE, NOTES) VALUES (?,?,?,?,?)";
    private static final String SELECT_CUSTOMER_BY_ID_SQL = "SELECT * FROM CUSTOMER WHERE CUSTOMER_ID = ?";
    private static final String SELECT_CUSTOMER_BY_COMPANY_NAME = "SELECT * FROM CUSTOMER WHERE COMPANY_NAME = ?";
    private static final String UPDATE_CUSTOMER_SQL = "UPDATE CUSTOMER SET COMPANY_NAME=?, EMAIL=?, TELEPHONE=?, NOTES=? WHERE CUSTOMER_ID = ?";
    private static final String DELETE_CUSTOMER = "DELETE FROM CUSTOMER WHERE CUSTOMER_ID = ?";
    private static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM CUSTOMER";
    private static final String SELECT_TBL_CUSTOMER_ID = "SELECT * FROM TBL_CALL WHERE CUSTOMER_ID = ?";
    private static final String INSERT_CALL = "INSERT INTO TBL_CALL(NOTES, TIME_AND_DATE, CUSTOMER_ID) VALUES(?, ?, ?)";

    public CustomerDaoImpl()
    {}
    public CustomerDaoImpl(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    private void createTables()
    {
        try
        {
            jdbcTemplate.update(CREATE_CUSTOMER_TABLE_SQL);
        }
        catch (BadSqlGrammarException e)
        {
            System.out.println("Assuming customer table already exists.");
        }

        try
        {
            jdbcTemplate.update(CREATE_CALL_TABLE_SQL);
        }
        catch (BadSqlGrammarException e)
        {
            System.out.println("Assuming customer table already exists.");
        }

    }

    @Override
    public void create(Customer customer)
    {
        jdbcTemplate.update(INSERT_CUSTOMER_SQL, customer.getCustomerId(), customer.getCompanyName(), customer.getEmail(), customer.getTelephone(), customer.getNotes());
    }

    @Override
    public Customer getById(String customerId) throws RecordNotFoundException
    {
        try
        {
           return jdbcTemplate.queryForObject(SELECT_CUSTOMER_BY_ID_SQL, new CustomerRowMapper(), customerId);
        }
        catch (IncorrectResultSizeDataAccessException e)
        {
            throw new RecordNotFoundException();
        }
    }

    @Override
    public List<Customer> getByName(String companyName)
    {
        return jdbcTemplate.query(SELECT_CUSTOMER_BY_COMPANY_NAME, new CustomerRowMapper(), companyName);
    }

    @Override
    public void update(Customer customerToUpdate) throws RecordNotFoundException
    {
        int rowsUpdated = jdbcTemplate.update(UPDATE_CUSTOMER_SQL, customerToUpdate.getCompanyName(), customerToUpdate.getEmail(), customerToUpdate.getTelephone(), customerToUpdate.getNotes(),
                                               customerToUpdate.getCustomerId());

        if (rowsUpdated != 1)
        {
            throw new RecordNotFoundException();
        }
    }

    @Override
    public void delete(Customer oldCustomer) throws RecordNotFoundException
    {
        int deletedRow = jdbcTemplate.update(DELETE_CUSTOMER, oldCustomer.getCustomerId());

        if(deletedRow != 1)
        {
            throw new RecordNotFoundException();
        }
    }

    @Override
    public List<Customer> getAllCustomers()
    {
        return jdbcTemplate.query(SELECT_ALL_CUSTOMERS, new CustomerRowMapper());
    }

    @Override
    public Customer getFullCustomerDetail(String customerId) throws RecordNotFoundException
    {
        Customer customer = this.getById(customerId);
        List<Call> allCallsForTheCustomer = jdbcTemplate.query(SELECT_TBL_CUSTOMER_ID, new CallRowMapper(), customerId);
        customer.setCalls(allCallsForTheCustomer);
        return customer;
    }

    @Override
    public void addCall(Call newCall, String customerId) throws RecordNotFoundException
    {
        Customer foundCustomer = this.getById(customerId);
        jdbcTemplate.update(INSERT_CALL, newCall.getNotes(), newCall.getTimeAndDate(), customerId);
    }
}

class CustomerRowMapper implements RowMapper<Customer>
{
    @Override
    public Customer mapRow(ResultSet resultSet, int rowNumber) throws SQLException
    {
        String customerId = resultSet.getString("CUSTOMER_ID");
        String companyName = resultSet.getString("COMPANY_NAME");
        String email = resultSet.getString("EMAIL");
        String telephone = resultSet.getString("TELEPHONE");
        String notes = resultSet.getString("NOTES");
        return new Customer(customerId, companyName, email,telephone,notes);
    }
}

class CallRowMapper implements RowMapper<Call>
{
    @Override
    public Call mapRow(ResultSet resultSet, int rowNumber) throws SQLException
    {
        Date timeAndDate = resultSet.getDate("TIME_AND_DATE");
        String notes = resultSet.getString("NOTES");
        return new Call(notes, timeAndDate);
    }
}