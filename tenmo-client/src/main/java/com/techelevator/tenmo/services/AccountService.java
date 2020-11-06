package com.techelevator.tenmo.services;

import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountService {

    private String authToken = "";
    private String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();

    public AccountService(String url) {
        this.baseUrl = url;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Account getCurrentBalance(int id) {
        Account result;
        try {
            result = restTemplate.exchange(baseUrl + "accounts/" + id, HttpMethod.GET, makeAuthEntity(), Account.class).getBody();
        } catch (ResourceAccessException | RestClientResponseException e) {
            result = null;
            System.out.println("Could not get current balance.");
        }
        return result;
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try {
            HttpEntity<User> entity = restTemplate.exchange(baseUrl + "users", HttpMethod.GET, makeAuthEntity(), User.class);
//            result = entity.getBody();
        } catch (ResourceAccessException | RestClientResponseException e){

        }

        return result;
    }

    private HttpEntity<Account> makeAccountEntity(Account account) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(account, headers);
    }

    private HttpEntity<?> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

}
