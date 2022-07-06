package com.example.bankingapplication.controller;

import com.example.bankingapplication.entiry.Account;
import com.example.bankingapplication.entiry.Transaction;
import com.example.bankingapplication.exception.NotFoundException;
import com.example.bankingapplication.request.TransferBalanceRequest;
import com.example.bankingapplication.response.Response;
import com.example.bankingapplication.service.account.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Created 05/07/2022
 * @Author monir.hossain
 */

@RequestMapping("api/v1/account")
@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<List<Account>> create(@Valid @RequestBody Account account) {
           accountService.save(account);
        return new ResponseEntity<>(accountService.findAll(), HttpStatus.CREATED);
    }

    @PostMapping(path = "/deposit")
    public Transaction deposit(@Valid @RequestBody Account account) throws NotFoundException{
        Transaction transaction = accountService.deposit(account);
        if(transaction != null)
            return transaction;
        else
            throw new NotFoundException("No information found for account: "+account.getAccountNumber());
    }

    @PostMapping(path = "/withdraw")
    public Transaction withdraw(@Valid @RequestBody Account account) throws NotFoundException {
        Transaction transaction = accountService.withdraw(account);
        if(transaction != null)
        return transaction;
        else
            throw new NotFoundException("No information found for account: "+account.getAccountNumber());
    }

    @GetMapping(path="/all")
    public List<Account> all() {
        return accountService.findAll();
    }

    @GetMapping(path="/transactionByType/{transactionType}")
    public List<Transaction> transactionByType(@PathVariable("transactionType") String transactionType) throws NotFoundException {
        if(transactionType != null)
            return accountService.allTransactionByTypeList(transactionType);
        else
            throw new NotFoundException("Transaction type not found.");
    }

    @PostMapping(path="/transfer")
    public Response sendMoney(@RequestBody TransferBalanceRequest transferBalanceRequest) throws NotFoundException {
        return Response.ok().setPayload(accountService.sendMoney(transferBalanceRequest)
        );
    }
    @GetMapping("/statement/{accountNumber}")
    public Response getStatement( @Valid @PathVariable("accountNumber") String accountNumber) throws NotFoundException {
        if(accountNumber!=null)
            return Response.ok().setPayload(accountService.getStatement(accountNumber));
        else
            return null;

    }


}
