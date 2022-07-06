package com.example.bankingapplication.entiry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * @Created 05/07/2022
 * @Author monir.hossain
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue
    private Long accountId;

    @Column(name = "account_number", nullable = false)
    @NotEmpty
    @Size(min=2,message = "Account number must have size at least 2")
    String accountNumber;

    @Min(1)
    BigDecimal currentBalance;

}