package com.bank.model;

import com.bank.enums.InvestmentType;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.UUID;

@Getter
public class Investment {
    private final String id;
    private final Customer customer;
    private final InvestmentType type;
    private final BigDecimal initialAmount;
    private final LocalDate startDate;
    private final LocalDate maturityDate;
    private final double annualRate;
    private boolean redeemed;

    public Investment(Customer customer, InvestmentType type, BigDecimal amount, int daysToMaturity) {
        this.id = UUID.randomUUID().toString();
        this.customer = customer;
        this.type = type;
        this.initialAmount = amount;
        this.startDate = LocalDate.now();
        this.maturityDate = startDate.plusDays(daysToMaturity);
        this.annualRate = type.getTaxaAnual();
        this.redeemed = false;
    }

    public BigDecimal calculateCurrentValue() {
        long daysInvested = java.time.temporal.ChronoUnit.DAYS.between(startDate, LocalDate.now());
        double dailyRate = Math.pow(1 + annualRate / 100, 1.0 / 365) - 1;
        double factor = Math.pow(1 + dailyRate, daysInvested);
        return initialAmount.multiply(BigDecimal.valueOf(factor)).setScale(2, RoundingMode.HALF_UP);
    }

    public void redeem() {
        this.redeemed = true;
    }

    @Override
    public String toString() {
        return String.format("Investimento: %s | Valor inicial: R$ %.2f | Valor atual: R$ %.2f | Vencimento: %s | Taxa: %.2f%% a.a. | Status: %s",
                type.getDescricao(),
                initialAmount,
                calculateCurrentValue(),
                maturityDate,
                annualRate,
                redeemed ? "Resgatado" : "Ativo");
    }
}
