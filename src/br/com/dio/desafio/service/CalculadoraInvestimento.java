package br.com.dio.desafio.service;

import br.com.dio.desafio.enums.TipoInvestimento;

public class CalculadoraInvestimento {
    public static double calcular(double valor, TipoInvestimento tipo) {
        return switch (tipo) {
            case CDB -> valor * 0.12;
            case LCIA -> valor * 0.10;
            case POUPANCA -> valor * 0.05;
        };
    }
}