package br.com.dio.desafio.exception;

public class ContaNaoEncontradaException extends RuntimeException {
    public ContaNaoEncontradaException(int numero) {
        super("Conta de número " + numero + " não foi encontrada no sistema.");
    }
}