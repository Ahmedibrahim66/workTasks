package com.example.demo.services;

import com.example.demo.models.Account;
import com.example.demo.models.User;

import java.util.List;

public interface AdminService {
    List<User> getAllUsers();
    List<Account> getUserAccount(int userId);
}
