package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.controller.dto.TransferDto;
import com.techelevator.tenmo.model.Transfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;
    private Logger log = LoggerFactory.getLogger(getClass());

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transfer> findAllTransfers() {
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }

    @Override
    public List<Transfer> findAllTransfersForSelf(Long accountFrom) {
        List<Transfer> transfers = new ArrayList<>();
        String sql =
                "SELECT tr.transfer_id, tr.transfer_type_id, tt.transfer_type_desc, " +
                        "tr.transfer_status_id, ts.transfer_status_desc, tr.account_from, tr.account_to, tr.amount " +
                        "FROM transfer AS tr JOIN transfer_type AS tt ON tr.transfer_type_id = tt.transfer_type_id " +
                        "JOIN transfer_status AS ts ON tr.transfer_status_id = ts.transfer_status_id " +
                        "WHERE account_from = ? OR account_to = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountFrom, accountFrom);
        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }

    @Override
    public List<Transfer> findPendingTransfersForSelf(Long accountFrom) {
        List<Transfer> transfers = new ArrayList<>();
        String sql =
                "SELECT tr.transfer_id, tr.transfer_type_id, tt.transfer_type_desc, " +
                        "tr.transfer_status_id, ts.transfer_status_desc, tr.account_from, tr.account_to, tr.amount " +
                        "FROM transfer AS tr JOIN transfer_type AS tt ON tr.transfer_type_id = tt.transfer_type_id " +
                        "JOIN transfer_status AS ts ON tr.transfer_status_id = ts.transfer_status_id " +
                        "WHERE account_to = ? AND tr.transfer_status_id = 1;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountFrom);
        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }

    @Override
    public Transfer findByTransferId(Long transferId) {
        Transfer transfer = null;
        String sql = "SELECT tr.transfer_id, tr.transfer_type_id, tt.transfer_type_desc, " +
                "tr.transfer_status_id, ts.transfer_status_desc, tr.account_from, tr.account_to, tr.amount " +
                "FROM transfer AS tr JOIN transfer_type AS tt ON tr.transfer_type_id = tt.transfer_type_id " +
                "JOIN transfer_status AS ts ON tr.transfer_status_id = ts.transfer_status_id " +
                "WHERE transfer_id = ? ";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if (results.next()) {
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }

    @Override
    @Transactional
    public Transfer create(TransferDto transferDto) {
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (" +
                "(SELECT transfer_type_id FROM transfer_type WHERE transfer_type_desc = 'Send'), " +
                "(SELECT transfer_status_id FROM transfer_status WHERE transfer_status_desc = 'Approved'), " +
                "(SELECT account_id FROM account WHERE account_id = ?), " +
                "(SELECT account_id FROM account WHERE account_id = ?), " +
                "?" +
                ") " +
                "RETURNING transfer_id;";
        String updateAccountFrom =
                "UPDATE account " +
                        "SET balance = balance - (SELECT amount FROM transfer WHERE transfer_id =?) " +
                        "WHERE account_id = ?;";
        String updateAccountTo =
                "UPDATE account " +
                        "SET balance = balance + (SELECT amount FROM transfer WHERE transfer_id = ?) " +
                        "WHERE account_id = ?;";
        Long newId = jdbcTemplate.queryForObject(sql, Long.class,
                transferDto.getAccountFromId(),
                transferDto.getAccountToId(),
                transferDto.getTransferAmount());
        jdbcTemplate.update(updateAccountFrom, newId, transferDto.getAccountFromId());
        jdbcTemplate.update(updateAccountTo, newId, transferDto.getAccountToId());
        return findByTransferId(newId);
    }

    @Override
    public Transfer createTransferRequest(TransferDto transferDto) {
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (1, 1, ?, ?, ?) " +
                "RETURNING transfer_id;";
        Long newId = jdbcTemplate.queryForObject(sql, Long.class, transferDto.getAccountFromId(),
                transferDto.getAccountToId(),
                transferDto.getTransferAmount());
        return findByTransferId(newId);
    }

    @Override
    public Transfer approveTransfer(Transfer approvedTransfer) {
        String updateTransferStatus = "UPDATE transfer SET transfer_status_id = 2 WHERE transfer_id = ? RETURNING transfer_id";
        String updateAccountFrom =
                "UPDATE account " +
                        "SET balance = balance + (SELECT amount FROM transfer WHERE transfer_id =?) " +
                        "WHERE account_id = ?;";
        String updateAccountTo =
                "UPDATE account " +
                        "SET balance = balance - (SELECT amount FROM transfer WHERE transfer_id = ?) " +
                        "WHERE account_id = ?;";
        Long newId = jdbcTemplate.queryForObject(updateTransferStatus, Long.class,
                approvedTransfer.getTransferId());
        jdbcTemplate.update(updateAccountFrom, newId, approvedTransfer.getAccountFrom());
        jdbcTemplate.update(updateAccountTo, newId, approvedTransfer.getAccountTo());
        return findByTransferId(newId);
    }

    @Override
    public Transfer rejectTransfer(Transfer rejectedTransfer) {
        String updateTransferStatus =
                "UPDATE transfer SET transfer_status_id = 3 WHERE transfer_id = ? RETURNING transfer_id";
        Long newId = jdbcTemplate.queryForObject(updateTransferStatus, Long.class,
                rejectedTransfer.getTransferId());
        return findByTransferId(newId);
    }


    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rowSet.getLong("transfer_id"));
        transfer.setTransferTypeId(rowSet.getInt("transfer_type_id"));
        transfer.setTransferTypeDesc(rowSet.getString("transfer_type_desc"));
        transfer.setTransferStatusId(rowSet.getInt("transfer_status_id"));
        transfer.setTransferStatusDesc(rowSet.getString("transfer_status_desc"));
        transfer.setAccountFrom(rowSet.getInt("account_from"));
        transfer.setAccountTo(rowSet.getInt("account_to"));
        transfer.setTransferAmount(rowSet.getBigDecimal("amount"));
        return transfer;
    }

}
