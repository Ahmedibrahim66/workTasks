package com.example.demo;

import com.example.demo.data.AccountRepository;
import com.example.demo.data.UserRepository;
import com.example.demo.models.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;


@Transactional
@Component
@Order(0)
public class DatabaseInitializer implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger("DatabaseInitializer");

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public void run(String... args) throws Exception {

        logger.info("Database initialization with data started");
        /*
        this code will initiate two accounts to each user in the database
        with the balance being 1000 and 0, this is to simplify the API
        process, return true if the initiations was done correctly.
         */
        try {
            userRepository.findAll().forEach(x -> {
                Account account = new Account("USD", 1000);
                Account account2 = new Account("NIS", 0);
                x.getAccounts().add(account);
                x.getAccounts().add(account2);
                account.setUser(x);
                account2.setUser(x);
                accountRepository.save(account);
                accountRepository.save(account2);
                userRepository.save(x);
            });

            logger.info("Database initialization with data finished");

        } catch (Exception e) {
            logger.error("Database initialization with data error " + e.getMessage());
        }

    }

}
