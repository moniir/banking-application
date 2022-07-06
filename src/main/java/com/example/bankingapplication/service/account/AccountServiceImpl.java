package com.example.bankingapplication.service.account;

import com.example.bankingapplication.entiry.Account;
import com.example.bankingapplication.entiry.Transaction;
import com.example.bankingapplication.entiry.TransactionStatement;
import com.example.bankingapplication.enums.TransactionType;
import com.example.bankingapplication.exception.NotFoundException;
import com.example.bankingapplication.repository.AccountRepository;
import com.example.bankingapplication.repository.TransactionRepository;
import com.example.bankingapplication.request.TransferBalanceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Created 05/07/2022
 * @Author monir.hossain
 */
@Component
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Account save(Account account){
        accountRepository.save(account);
        return accountRepository.findByAccountNumberEquals(account.getAccountNumber());
    }
    @Override
    public List<Account> findAll(){
        return accountRepository.findAll();
    }

    @Override
    public List<Transaction> allTransactionByTypeList(String transactionType) throws NotFoundException {
            List<Transaction> transactionList = transactionRepository.findAllByTransactionTypeContainingIgnoreCase(transactionType);
            if(transactionList.size()>0)
                return transactionList;
            else
                throw new NotFoundException("No record found for type: "+transactionType);
    }

    @Override
    public Account findByAccountNumber(String accountNumber){
        Account account = accountRepository.findByAccountNumberEquals(accountNumber);
        return account;
    }
    @Override
    public Transaction deposit(Account account) throws NotFoundException {
        Account depositAccount = null;
            depositAccount  = accountRepository.findByAccountNumberEquals(account.getAccountNumber());
        if(depositAccount == null){
            throw new NotFoundException("Information not found for account number: "+account.getAccountNumber());
        } else{
            depositAccount.setCurrentBalance(depositAccount.getCurrentBalance().add(account.getCurrentBalance()));
            accountRepository.save(depositAccount);
            Transaction transaction = transactionRepository.save(new Transaction(0L,depositAccount.getAccountNumber(),TransactionType.DEPOSIT.name(),account.getCurrentBalance(),new Timestamp(System.currentTimeMillis())));
            return transaction;
        }
    }

    @Override
    public Transaction withdraw(Account account) throws NotFoundException{
        Account withdrawAccount = null;
            withdrawAccount  = accountRepository.findByAccountNumberEquals(account.getAccountNumber());
        if(withdrawAccount == null){
            throw new NotFoundException("Information not found for account number: "+account.getAccountNumber());
        }
        int result = withdrawAccount.getCurrentBalance().compareTo(account.getCurrentBalance());
        if(result == 1){
            withdrawAccount.setCurrentBalance(withdrawAccount.getCurrentBalance().subtract(account.getCurrentBalance()));
            accountRepository.save(withdrawAccount);
            Transaction transaction = transactionRepository.save(new Transaction(0L,withdrawAccount.getAccountNumber(), TransactionType.WITHDRAW.name(),account.getCurrentBalance(),new Timestamp(System.currentTimeMillis())));
            return transaction;
        } else
            throw new NotFoundException("No available balance to withdraw");
    }

    @Override
    public Transaction sendMoney(TransferBalanceRequest transferBalanceRequest) throws NotFoundException{
        String fromAccountNumber = transferBalanceRequest.getFromAccountNumber();
        String toAccountNumber = transferBalanceRequest.getToAccountNumber();
        BigDecimal amount = transferBalanceRequest.getAmount();
        Account fromAccount = accountRepository.findByAccountNumberEquals(fromAccountNumber);
        Account toAccount = accountRepository.findByAccountNumberEquals(toAccountNumber);
        if(fromAccount.getCurrentBalance().compareTo(BigDecimal.ONE) == 1 && fromAccount.getCurrentBalance().compareTo(amount) == 1){
            fromAccount.setCurrentBalance(fromAccount.getCurrentBalance().subtract(amount));
            accountRepository.save(fromAccount);
            toAccount.setCurrentBalance(toAccount.getCurrentBalance().add(amount));
            accountRepository.save(toAccount);
            Transaction transaction = transactionRepository.save(new Transaction(0L,fromAccountNumber,TransactionType.TRANSFER.name(),amount,new Timestamp(System.currentTimeMillis())));
            return transaction;
        }
        throw new NotFoundException("Please input valid current balance");
    }

    @Override
    public TransactionStatement getStatement(String accountNumber) throws NotFoundException {
        Account account = accountRepository.findByAccountNumberEquals(accountNumber);
        if(account != null)
            return new TransactionStatement(account.getCurrentBalance(),transactionRepository.findByAccountNumberEquals(accountNumber));
        else
            throw new NotFoundException("No Transaction found for account number: "+accountNumber);
    }
}
