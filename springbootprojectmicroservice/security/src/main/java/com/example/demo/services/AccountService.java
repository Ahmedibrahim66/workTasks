package com.example.demo.services;

import com.example.demo.models.*;

import java.util.List;

public interface AccountService {

    List<Transaction> getAccountTransactions(String token, int accountId);

    Transaction withdrawFromAccount(WithdrawModel withdrawModel, String token);

    Transaction depositIntoAccount(DepositModel depositModel, String token);

    Transaction transferIntoAccount(TransferPaymentModel transferPaymentModel, String token);

}
