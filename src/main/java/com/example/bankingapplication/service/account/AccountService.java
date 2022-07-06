package com.example.bankingapplication.service.account;

import com.example.bankingapplication.entiry.Account;
import com.example.bankingapplication.entiry.Transaction;
import com.example.bankingapplication.entiry.TransactionStatement;
import com.example.bankingapplication.exception.NotFoundException;
import com.example.bankingapplication.request.TransferBalanceRequest;

import java.util.List;

/**
 * @Created 05/07/2022
 * @Author monir.hossain
 */
public interface AccountService {
    List<Account> findAll();
    List<Transaction> allTransactionByTypeList(String transactionType) throws NotFoundException;
    Account save(Account account);
    Transaction deposit(Account account) throws NotFoundException;
    Transaction withdraw(Account account) throws NotFoundException;
    Transaction sendMoney( TransferBalanceRequest transferBalanceRequest) throws NotFoundException;
    TransactionStatement getStatement(String accountNumber) throws NotFoundException;
    Account findByAccountNumber(String accountNumber);

}