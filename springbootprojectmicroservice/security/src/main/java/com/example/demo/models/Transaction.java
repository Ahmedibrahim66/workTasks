package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "transactions")
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int TransactionId;

    int fromAccount;
    int toAccount;
    int amount;
    String type;

    @ManyToOne()
    @JoinColumn(name = "accountId")
    @JsonIgnore
    private Account account;


    public Transaction(int fromAccount, int toAccount, int amount, String type) {

        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.type = type;
    }

}
