package com.example.demo.services;

import com.example.demo.utils.JwtUtil;
import com.example.demo.data.UserRepository;
import com.example.demo.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtil jwtUtil;


    @Override
    public List<Account> getUserAccounts(String token) {
        //get the token from the header
        String jwt = token.substring(7);
        try{
            //get the username from the token
            String username = jwtUtil.extractUsername(jwt);
            User user = userRepository.findUserByUserName(username);
            return user.getAccounts();
        }catch (Exception e){
            return null;
        }
    }


}
