package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDAO implements TransferDAO {

    private JdbcTemplate jdbcTemplate;
    private JdbcUserDAO userDAO;

    public JdbcTransferDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addTransfer(Account depositAccount, Account withdrawalAccount, BigDecimal amount) {
        String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql, 2, 2, withdrawalAccount.getAccountId(), depositAccount.getAccountId(), amount);
    }

    @Override
    public List<Transfer> getAllTransfers(int id) {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfers JOIN accounts ON transfers.account_from = accounts.account_id " +
                "WHERE user_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        while (rowSet.next()) {
            transfers.add(mapRowToTransfer(rowSet));
        }
        return transfers;
    }

    @Override
    public Transfer getTransferFromId(int id) {
        Transfer transfer;
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
                "FROM transfers WHERE transfer_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        if (rowSet.next()) {
            transfer = mapRowToTransfer(rowSet);
        } else {
            transfer = null;
        }
        return transfer;
    }

    @Override
    public boolean checksBeforeTransfer(Account depositAccount, Account withdrawalAccount, BigDecimal amount) {
        boolean result = false;

        boolean balanceIsEnough = withdrawalAccount.getBalance().compareTo(amount) >= 0;
        boolean depositAccountExists = false;
        boolean withdrawalAccountExists = false;

        List<User> users = userDAO.findAll();
        for (User user : users) {
            if (user.getId() == depositAccount.getUserId()) {
                depositAccountExists = true;
            }
            if (user.getId() == withdrawalAccount.getUserId()) {
                withdrawalAccountExists = true;
            }
        }

        if (balanceIsEnough && depositAccountExists && withdrawalAccountExists) {
            result = true;
        }

        return result;
    }

    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rowSet.getInt("transfer_id"));
        transfer.setTransferTypeId(rowSet.getInt("transfer_type_id"));
        transfer.setTransferStatusId(rowSet.getInt("transfer_status_id"));
        transfer.setAccountFrom(rowSet.getInt("account_from"));
        transfer.setAccountTo(rowSet.getInt("account_to"));
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        return transfer;
    }

}
