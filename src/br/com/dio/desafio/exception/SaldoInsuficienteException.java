package br.com.dio.desafio.exception;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException(double saldo, double valor) {
        super(String.format("Saldo insuficiente: R$ %.2f. Tentativa de operação: R$ %.2f", saldo, valor));
    }
}