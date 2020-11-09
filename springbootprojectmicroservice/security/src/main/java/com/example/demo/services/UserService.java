package com.example.demo.services;

import com.example.demo.models.*;

import java.util.List;


public interface UserService {

    public List<Account> getUserAccounts(String token);
}
