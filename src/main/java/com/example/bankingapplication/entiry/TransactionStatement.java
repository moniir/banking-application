package com.example.bankingapplication.entiry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Created 05/07/2022
 * @Author monir.hossain
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionStatement {
    BigDecimal currentBalance;
    List<Transaction> transactionHistory;
}
