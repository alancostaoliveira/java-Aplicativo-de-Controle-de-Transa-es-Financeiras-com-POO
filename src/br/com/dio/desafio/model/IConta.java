package br.com.dio.desafio.model;

import br.com.dio.desafio.enums.TipoInvestimento;

public interface IConta {
    void sacar(double valor);
    void depositar(double valor);
    void transferirPix(Conta destino, double valor);
    void aplicarInvestimento(double valor, TipoInvestimento tipo);
    void imprimirExtrato();
}