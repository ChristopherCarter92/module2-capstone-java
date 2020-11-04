package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.security.jwt.TokenProvider;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private final TokenProvider tokenProvider;
    private UserDAO userDAO;

    public UserController(TokenProvider tokenProvider, UserDAO userDAO) {
        this.tokenProvider = tokenProvider;
        this.userDAO = userDAO;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        users = userDAO.findAll();
        return users;
    }

}