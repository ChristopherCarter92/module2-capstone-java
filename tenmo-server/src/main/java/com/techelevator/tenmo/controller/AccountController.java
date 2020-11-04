package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.security.jwt.TokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@PreAuthorize("isAuthenticated()")
@RestController
public class AccountController {

    private final TokenProvider tokenProvider;
    private AccountDAO accountDAO;

    public AccountController(TokenProvider tokenProvider, AccountDAO accountDAO) {
        this.tokenProvider = tokenProvider;
        this.accountDAO = accountDAO;
    }

    // maybe not let everyone do this, only logged in user (principal)
    @RequestMapping(value = "/accounts/{id}", method = RequestMethod.GET)
    public Account getAccountByUserId(@PathVariable int id) {
        Account result = accountDAO.getUserAccount(id);
        return result;
    }

}
