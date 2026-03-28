package com.bank.model;

import com.bank.enums.AccountType;
import com.bank.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public abstract class Account {
    private String id;
    private String accountNumber;
    private Customer customer;
    private BigDecimal balance;
    private AccountType accountType;
    private List<Transaction> transactions;

    protected Account(Customer customer, AccountType accountType) {
        this.id = UUID.randomUUID().toString();
        this.accountNumber = generateAccountNumber();
        this.customer = customer;
        this.balance = BigDecimal.ZERO;
        this.accountType = accountType;
        this.transactions = new ArrayList<>();
    }

    private String generateAccountNumber() {
        int number = (int)(Math.random() * 900000) + 100000;
        return String.valueOf(number);
    }

    public void deposit(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser maior que zero.");
        }
        this.balance = this.balance.add(amount);
        Transaction transaction = new Transaction(
                TransactionType.DEPOSITO,
                amount,
                "Depósito em conta",
                this.balance
        );
        this.transactions.add(transaction);
        System.out.printf("Depósito de R$ %.2f realizado com sucesso! Saldo atual: R$ %.2f%n", amount, this.balance);
    }

    public abstract void withdraw(BigDecimal amount);

    public void printStatement() {
        System.out.println("=".repeat(80));
        System.out.printf("Extrato da Conta - %s%n", accountType.getDescricao());
        System.out.printf("Titular: %s | Número da Conta: %s%n", customer.getName(), accountNumber);
        System.out.printf("Saldo atual: R$ %.2f%n", balance);
        System.out.println("-".repeat(80));
        if (transactions.isEmpty()) {
            System.out.println("Nenhuma transação encontrada.");
        } else {
            transactions.forEach(System.out::println);
        }
        System.out.println("=".repeat(80));
    }

    @Override
    public String toString() {
        return String.format("Conta %s | Número: %s | Titular: %s | Saldo: R$ %.2f",
                accountType.getDescricao(), accountNumber, customer.getName(), balance);
    }
}
