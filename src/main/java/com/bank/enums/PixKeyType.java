package com.bank.enums;

public enum PixKeyType {
    CPF("CPF"),
    EMAIL("E-mail"),
    TELEFONE("Telefone"),
    CHAVE_ALEATORIA("Chave Aleatória");

    private final String descricao;

    PixKeyType(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
