package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "accounts")
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;
    private String currency;
    private int balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    @OneToMany(orphanRemoval = false, mappedBy = "account")
    @JsonIgnore
    private List<Transaction> transactions = new ArrayList<>();

    public Account(String currency, int balance) {
        this.currency = currency;
        this.balance = balance;
    }


}
