package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.controller.dto.TransferDto;
import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    List<Transfer> findAllTransfers();

    Transfer findByTransferId(Long transferID);

    Transfer create(TransferDto transferDto);

    List<Transfer> findAllTransfersForSelf(Long accountFrom);

    List<Transfer> findPendingTransfersForSelf(Long accountFrom);

    Transfer createTransferRequest(TransferDto transferDto);

    public Transfer approveTransfer(Transfer approvedTransfer);

    public Transfer rejectTransfer(Transfer rejectedTransfer);
}
