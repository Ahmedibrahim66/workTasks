package com.example.demo.controllers;

import com.example.demo.services.AdminService;
import com.example.demo.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/admin/users/{userId}" , method = RequestMethod.GET)
    public List<Account> addUserAccount(@PathVariable int userId){
       return adminService.getUserAccount(userId);
    }



}
