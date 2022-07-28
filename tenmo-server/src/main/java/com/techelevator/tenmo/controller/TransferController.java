package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.controller.dto.TransferDto;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.exception.AccountNotFoundException;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private TransferDao dao;
    private JdbcTemplate jdbcTemplate;

    public TransferController(TransferDao transferDao) {
        this.dao = transferDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public Transfer newTransfer(@RequestBody TransferDto transferDto) throws AccountNotFoundException {
        return dao.create(transferDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/transfer/{id}", method = RequestMethod.GET)
    public Transfer getTransfer(@PathVariable("id") Long accountId) {
        return dao.findByTransferId(accountId);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/transfer/{id}/showtransfers", method = RequestMethod.GET)
    public List<Transfer> findAllTransfersForSelf(@PathVariable("id") Long accountId) {
        return dao.findAllTransfersForSelf(accountId);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/transfer/{id}/showpendingtransfers", method = RequestMethod.GET)
    public List<Transfer> findPendingTransfersForSelf(@PathVariable("id") Long accountId) {
        return dao.findPendingTransfersForSelf(accountId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfer/request", method = RequestMethod.POST)
    public Transfer createTransferRequest(@RequestBody TransferDto transferDto) throws AccountNotFoundException {
        return dao.createTransferRequest(transferDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/transfer/approved", method = RequestMethod.PUT)
    public Transfer approveTransfer(@RequestBody Transfer transfer) {
        return dao.approveTransfer(transfer);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/transfer/rejected", method = RequestMethod.PUT)
    public Transfer rejectTransfer(@RequestBody Transfer transfer) {
        return dao.rejectTransfer(transfer);
    }
}
