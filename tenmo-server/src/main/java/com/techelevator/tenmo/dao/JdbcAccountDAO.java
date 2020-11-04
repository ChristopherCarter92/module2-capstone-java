package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcAccountDAO implements AccountDAO {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getUserAccount(int id) {
        Account account;
        String query = "SELECT account_id, user_id, balance FROM accounts WHERE user_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query, id);
        if (rowSet.next()) {
            account = mapRowToAccount(rowSet);
        } else {
            account = null;
        }
        return account;
    }

    @Override
    public Account update(Account account) {
        Account result;
        String query = "UPDATE accounts SET balance = ? WHERE user_id = ?;";
        jdbcTemplate.update(query, account.getBalance(), account.getUserId());
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT account_id, user_id, balance FROM accounts WHERE user_id = ?;", account.getUserId());
        if (rowSet.next()) {
            result = mapRowToAccount(rowSet);
        } else {
            result = null;
        }
        return result;
    }

    private Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();
        account.setAccountId(rowSet.getInt("account_id"));
        account.setUserId(rowSet.getInt("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));
        return account;
    }

}
