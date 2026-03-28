package br.com.dio.desafio.repository;

import br.com.dio.desafio.model.Conta;
import java.util.*;
import java.util.stream.Collectors;

public class ContaRepository {
    private final Map<Integer, Conta> contas = new HashMap<>();

    public void salvar(Conta conta) {
        contas.put(conta.getNumero(), conta);
    }

    public Optional<Conta> buscarPorNumero(int numero) {
        return Optional.ofNullable(contas.get(numero));
    }

    public List<Conta> buscarPorCpf(String cpf) {
        return contas.values().stream()
                .filter(c -> c.getTitular().getCpf().equals(cpf))
                .collect(Collectors.toList());
    }

    public List<Conta> listarTodas() {
        return new ArrayList<>(contas.values());
    }
}