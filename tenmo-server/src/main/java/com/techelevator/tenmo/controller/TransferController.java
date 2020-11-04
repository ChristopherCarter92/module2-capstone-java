package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.security.jwt.TokenProvider;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class TransferController {

    private final TokenProvider tokenProvider;
    private TransferDAO transferDAO;
    private UserDAO userDAO;

    public TransferController(TokenProvider tokenProvider, TransferDAO transferDAO) {
        this.tokenProvider = tokenProvider;
        this.transferDAO = transferDAO;
    }

    @RequestMapping(value = "/transfers", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers(Principal principal) {
        List<Transfer> result;
        int id = userDAO.findIdByUsername(principal.getName());
        result = transferDAO.getAllTransfers(id);
        return result;
    }

}
