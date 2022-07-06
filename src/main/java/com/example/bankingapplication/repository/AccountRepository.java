package com.example.bankingapplication.repository;

import com.example.bankingapplication.entiry.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Created 05/07/2022
 * @Author monir.hossain
 */
public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findByAccountNumberEquals(String accountNumber);
}
