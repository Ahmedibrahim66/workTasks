package com.example.demo.data;

import com.example.demo.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionsRepository extends JpaRepository<Transaction, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM transactions WHERE from_account = :accountId OR to_account= :accountId")
    List<Transaction> getAccountTransactions(@Param("accountId") int id);

}
