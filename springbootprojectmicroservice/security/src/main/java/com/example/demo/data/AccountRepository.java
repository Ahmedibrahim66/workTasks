package com.example.demo.data;

import com.example.demo.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {


    Account getAccountByAccountId(int id);

}
