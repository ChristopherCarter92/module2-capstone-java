package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.security.jwt.TokenProvider;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class AccountController {

    private final TokenProvider tokenProvider;
    private AccountDAO accountDAO;

    public AccountController(TokenProvider tokenProvider, AccountDAO accountDAO) {
        this.tokenProvider = tokenProvider;
        this.accountDAO = accountDAO;
    }

    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.GET)
    public Account getAccountByUserId(@PathVariable int id) {
        Account result = accountDAO.getUserAccount(id);
        return result;
    }

    @PutMapping(value = "/accounts/{id}")
    public Account update(@PathVariable int id, @RequestBody Account updatedAccount) {
        return accountDAO.update(updatedAccount);
    }

}
