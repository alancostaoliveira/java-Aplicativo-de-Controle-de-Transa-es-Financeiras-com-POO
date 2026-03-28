package com.bank.enums;

public enum TransactionType {
    DEPOSITO("Depósito"),
    SAQUE("Saque"),
    TRANSFERENCIA_PIX("Transferência PIX"),
    INVESTIMENTO("Investimento"),
    RESGATE_INVESTIMENTO("Resgate de Investimento");

    private final String descricao;

    TransactionType(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
