package br.com.dio.desafio.model;

import br.com.dio.desafio.enums.TipoConta;
import br.com.dio.desafio.enums.TipoInvestimento;
import br.com.dio.desafio.service.CalculadoraInvestimento;

public class ContaCorrente extends Conta {
    public ContaCorrente(Cliente titular) {
        super(titular, TipoConta.CORRENTE);
    }

    @Override
    public void aplicarInvestimento(double valor, TipoInvestimento tipo) {
        if (valor > this.saldo) throw new IllegalArgumentException("Saldo insuficiente para investir.");
        this.sacar(valor);
        double rendimento = CalculadoraInvestimento.calcular(valor, tipo);
        System.out.printf("Investimento de R$ %.2f realizado. Rendimento previsto: R$ %.2f%n", valor, rendimento);
    }
}