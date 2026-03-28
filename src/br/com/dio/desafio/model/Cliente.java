package br.com.dio.desafio.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cliente {
    private String nome;
    private String cpf;
    private List<Conta> contas = new ArrayList<>();

    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }
}