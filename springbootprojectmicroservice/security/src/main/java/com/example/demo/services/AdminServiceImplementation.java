package com.example.demo.services;

import com.example.demo.data.AccountRepository;
import com.example.demo.data.UserRepository;
import com.example.demo.models.Account;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImplementation implements AdminService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(userRepository.findAll());
    }

    @Override
    public List<Account> getUserAccount(int userId) {
        return userRepository.getUserByUserId(userId).getAccounts();
    }


}
