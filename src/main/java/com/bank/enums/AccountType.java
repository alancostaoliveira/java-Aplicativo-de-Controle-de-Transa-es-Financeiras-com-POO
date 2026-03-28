package com.bank.enums;

public enum AccountType {
    CORRENTE("Conta Corrente"),
    POUPANCA("Conta Poupança");

    private final String descricao;

    AccountType(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
