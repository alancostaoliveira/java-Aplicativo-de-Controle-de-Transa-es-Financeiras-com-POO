package com.bank.model;

import com.bank.enums.AccountType;
import com.bank.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SavingsAccount extends Account {
    private static final int MAX_MONTHLY_WITHDRAWALS = 5;
    private int monthlyWithdrawals;

    public SavingsAccount(Customer customer) {
        super(customer, AccountType.POUPANCA);
        this.monthlyWithdrawals = 0;
    }

    @Override
    public void withdraw(BigDecimal amount) {
        if (monthlyWithdrawals >= MAX_MONTHLY_WITHDRAWALS) {
            throw new IllegalStateException(
                String.format("Limite de %d saques mensais atingido para conta poupança.", MAX_MONTHLY_WITHDRAWALS));
        }
        validateAndDeductBalance(amount);
        monthlyWithdrawals++;
        Transaction transaction = new Transaction(
                TransactionType.SAQUE,
                amount,
                String.format("Saque em poupança (%d/%d saques no mês)", monthlyWithdrawals, MAX_MONTHLY_WITHDRAWALS),
                getBalance()
        );
        getTransactions().add(transaction);
        System.out.printf("Saque de R$ %.2f realizado com sucesso! Saldo atual: R$ %.2f (%d/%d saques no mês)%n",
                amount, getBalance(), monthlyWithdrawals, MAX_MONTHLY_WITHDRAWALS);
    }

    public void resetMonthlyWithdrawals() {
        this.monthlyWithdrawals = 0;
    }
}
