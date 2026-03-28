package br.com.dio.desafio.model;

import br.com.dio.desafio.enums.TipoTransacao;
import java.time.LocalDateTime;

public record Transacao(TipoTransacao tipo, double valor, LocalDateTime dataHora) {
    public Transacao(TipoTransacao tipo, double valor) {
        this(tipo, valor, LocalDateTime.now());
    }
}