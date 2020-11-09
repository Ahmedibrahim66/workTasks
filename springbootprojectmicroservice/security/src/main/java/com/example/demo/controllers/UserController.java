package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.services.AccountService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "user/withdraw", method = RequestMethod.POST)
    public ResponseEntity<?> userWithdraw(@RequestHeader("Authorization") String token, @RequestBody WithdrawModel withdrawModel) {
        return ResponseEntity.ok(accountService.withdrawFromAccount(withdrawModel, token));
    }

    @RequestMapping(value = "user/deposit", method = RequestMethod.POST)
    public ResponseEntity<?> userDeposit(@RequestHeader("Authorization") String token, @RequestBody DepositModel depositModel) {
        return ResponseEntity.ok(accountService.depositIntoAccount(depositModel, token));
    }

    @RequestMapping(value = "user/transfer", method = RequestMethod.POST)
    public Transaction userTransfer(@RequestHeader("Authorization") String token,
                                    @RequestBody TransferPaymentModel transferPaymentModel) {
        return accountService.transferIntoAccount(transferPaymentModel, token);
    }

    @RequestMapping(value = "user/accounts", method = RequestMethod.GET)
    public List<Account> userAccounts(@RequestHeader("Authorization") String token) {
        return userService.getUserAccounts(token);
    }

    @RequestMapping(value = "user/transactions/{accountId}", method = RequestMethod.GET)
    public List<Transaction> userAccountTransactions(@RequestHeader("Authorization") String token, @PathVariable int accountId) {
        return accountService.getAccountTransactions(token, accountId);
    }


}
