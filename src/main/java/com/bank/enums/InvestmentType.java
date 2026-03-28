package com.bank.enums;

public enum InvestmentType {
    CDB("CDB - Certificado de Depósito Bancário", 12.5),
    LCI("LCI - Letra de Crédito Imobiliário", 10.0),
    TESOURO_DIRETO("Tesouro Direto - Selic", 11.75);

    private final String descricao;
    private final double taxaAnual;

    InvestmentType(String descricao, double taxaAnual) {
        this.descricao = descricao;
        this.taxaAnual = taxaAnual;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getTaxaAnual() {
        return taxaAnual;
    }
}
