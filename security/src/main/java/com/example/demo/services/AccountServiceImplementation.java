package com.example.demo.services;

import com.example.demo.utils.JwtUtil;
import com.example.demo.data.AccountRepository;
import com.example.demo.data.TransactionsRepository;
import com.example.demo.data.UserRepository;
import com.example.demo.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class AccountServiceImplementation implements AccountService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    TransactionsRepository transactionsRepository;


    @Override
    public List<Transaction> getAccountTransactions(String token, int accountId) {

        Account account = validateAccountFromHeader(token, accountId);

        if(account != null) {
            return transactionsRepository.getAccountTransactions(accountId);
        }else {
            throw new EntityNotFoundException("Error in account");
        }
    }

    @Override
    public Transaction withdrawFromAccount(WithdrawModel withdrawModel , String token) {

        Account account = validateAccountFromHeader(token, withdrawModel.getAccountId());

        ///check if account exists and if the amount is not negative
        if(account != null && withdrawModel.getAmount() > 0){
            //check if balance is available
            if(account.getBalance() >= withdrawModel.getAmount()){
                account.setBalance(account.getBalance() - withdrawModel.getAmount());

                //create a withdraw transaction type
                Transaction transaction = new Transaction(account.getAccountId(),account.getAccountId(),
                        withdrawModel.getAmount(), "Withdraw");
                transaction.setAccount(account);
                account.getTransactions().add(transaction);
                accountRepository.save(account);
                transactionsRepository.save(transaction);
                return transaction;

            }
        }
        throw new EntityNotFoundException("Error in withdraw");
    }

    @Override
    public Transaction depositIntoAccount(DepositModel depositModel, String token) {

        Account account = validateAccountFromHeader(token, depositModel.getAccountId());

        ///check if account exists
        if(account != null  && depositModel.getAmount() > 0 ){

            account.setBalance(account.getBalance() + depositModel.getAmount());

            //create a deposit transaction type
            Transaction transaction = new Transaction(account.getAccountId(), account.getAccountId(),
                    depositModel.getAmount(), "Deposit");
            transaction.setAccount(account);
            account.getTransactions().add(transaction);
            accountRepository.save(account);
            transactionsRepository.save(transaction);
            return transaction;
        }

        throw new EntityNotFoundException("Error in deposit");


    }

    @Override
    public Transaction transferIntoAccount(TransferPaymentModel transferPaymentModel, String token) {

        Account account = validateAccountFromHeader(token, transferPaymentModel.getFromAccount());
        Account accountTo = accountRepository.getAccountByAccountId(transferPaymentModel.getToAccount());

        ///check if account exists
        if(account != null) {

            //check if account send to and user send to is available
            if ( accountTo != null && transferPaymentModel.getAmount() > 0 ){

                //check if balance is available
                if(account.getBalance() >= transferPaymentModel.getAmount()) {
                    account.setBalance(account.getBalance() - transferPaymentModel.getAmount());
                    accountTo.setBalance(accountTo.getBalance() + transferPaymentModel.getAmount());

                    //create a withdraw transaction type deposit
                    Transaction transaction = new Transaction(account.getAccountId(), accountTo.getAccountId(),
                            transferPaymentModel.getAmount(), "Transfer");

                    transaction.setAccount(account);
                    account.getTransactions().add(transaction);
                    accountRepository.save(account);
                    transactionsRepository.save(transaction);
                    return transaction;
                }
            }
        }
        throw new EntityNotFoundException("Error in transfer");
    }


    /**
     * validate that the account belongs to the user, if true will return
     * the account else will return null
     */
    public Account validateAccountFromHeader(String token , int accountId ){
        //get the token from the header
        String jwt = token.substring(7);

        //get the username from the token
        String username = jwtUtil.extractUsername(jwt);
        User user = userRepository.findUserByUserName(username);

        // get the user account
        List<Account> userAccounts = user.getAccounts();

        //get the user account
        final Account[] account = new Account[1];
        userAccounts.forEach(x-> {
            if(x.getAccountId() == accountId)
                account[0] = x;
        });
        return account[0];
    }
}
