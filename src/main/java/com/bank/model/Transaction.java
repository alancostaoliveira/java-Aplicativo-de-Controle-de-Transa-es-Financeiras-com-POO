package com.bank.model;

import com.bank.enums.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public record Transaction(
        String id,
        TransactionType type,
        BigDecimal amount,
        String description,
        LocalDateTime dateTime,
        BigDecimal balanceAfter
) {
    public Transaction(TransactionType type, BigDecimal amount, String description, BigDecimal balanceAfter) {
        this(UUID.randomUUID().toString(), type, amount, description, LocalDateTime.now(), balanceAfter);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return String.format("[%s] %s | Valor: R$ %.2f | Saldo após: R$ %.2f | Descrição: %s",
                dateTime.format(formatter),
                type.getDescricao(),
                amount,
                balanceAfter,
                description);
    }
}
