package br.com.dio.desafio.model;

import br.com.dio.desafio.enums.TipoConta;
import br.com.dio.desafio.enums.TipoInvestimento;
import br.com.dio.desafio.enums.TipoTransacao;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public abstract class Conta {
    private static int SEQUENCIAL = 1;

    protected int numero;
    protected double saldo;
    protected Cliente titular;
    protected TipoConta tipoConta;
    protected List<Transacao> historicoTransacoes;

    public Conta(Cliente titular, TipoConta tipoConta) {
        this.numero = SEQUENCIAL++;
        this.titular = titular;
        this.tipoConta = tipoConta;
        this.historicoTransacoes = new ArrayList<>();
        this.saldo = 0.0;
    }

    public void depositar(double valor) {
        if (valor <= 0) throw new IllegalArgumentException("Valor de depósito deve ser positivo.");
        this.saldo += valor;
        registrarTransacao(TipoTransacao.DEPOSITO, valor);
    }

    public void sacar(double valor) {
        if (valor <= 0) throw new IllegalArgumentException("Valor de saque deve ser positivo.");
        if (this.saldo < valor) throw new IllegalArgumentException("Saldo insuficiente.");
        this.saldo -= valor;
        registrarTransacao(TipoTransacao.SAQUE, valor);
    }

    public void transferirPix(Conta destino, double valor) {
        this.sacar(valor);
        destino.depositar(valor);
        registrarTransacao(TipoTransacao.PIX, valor);
        destino.registrarTransacao(TipoTransacao.PIX_RECEBIDO, valor);
    }

    protected void registrarTransacao(TipoTransacao tipo, double valor) {
        this.historicoTransacoes.add(new Transacao(tipo, valor));
    }

    public abstract void aplicarInvestimento(double valor, TipoInvestimento tipo);
}