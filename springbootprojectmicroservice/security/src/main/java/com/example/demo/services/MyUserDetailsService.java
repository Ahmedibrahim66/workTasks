package com.example.demo.services;

import com.example.demo.data.UserRepository;
import com.example.demo.models.MyUserDetails;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = repository.findUserByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Not found " + userName);
        }
        return new MyUserDetails(user);
    }
}
