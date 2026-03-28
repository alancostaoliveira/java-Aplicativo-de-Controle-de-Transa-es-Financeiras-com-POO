package com.bank.model;

import com.bank.enums.AccountType;
import com.bank.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CheckingAccount extends Account {
    private BigDecimal overdraftLimit;

    public CheckingAccount(Customer customer) {
        super(customer, AccountType.CORRENTE);
        this.overdraftLimit = new BigDecimal("500.00");
    }

    public CheckingAccount(Customer customer, BigDecimal overdraftLimit) {
        super(customer, AccountType.CORRENTE);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser maior que zero.");
        }
        BigDecimal availableBalance = getBalance().add(overdraftLimit);
        if (amount.compareTo(availableBalance) > 0) {
            throw new IllegalStateException(
                String.format("Saldo insuficiente. Saldo disponível (com limite): R$ %.2f", availableBalance));
        }
        setBalance(getBalance().subtract(amount));
        Transaction transaction = new Transaction(
                TransactionType.SAQUE,
                amount,
                "Saque em conta corrente",
                getBalance()
        );
        getTransactions().add(transaction);
        System.out.printf("Saque de R$ %.2f realizado com sucesso! Saldo atual: R$ %.2f%n", amount, getBalance());
    }
}
