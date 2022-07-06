package com.example.bankingapplication.repository;

import com.example.bankingapplication.entiry.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Created 05/07/2022
 * @Author monir.hossain
 */
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByAccountNumberEquals(String accountNumber);
    List<Transaction> findAllByTransactionTypeContainingIgnoreCase(String type);
}
