package com.example.demo.models;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class TransferPaymentModel {

    int amount;
    int fromAccount;
    int toAccount;

}
