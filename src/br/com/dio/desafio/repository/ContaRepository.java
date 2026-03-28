package br.com.dio.desafio.repository;

import br.com.dio.desafio.model.Conta;
import java.util.*;

public class ContaRepository {
    private final Map<Integer, Conta> contas = new HashMap<>();

    public void salvar(Conta conta) {
        contas.put(conta.getNumero(), conta);
    }

    public Optional<Conta> buscarPorNumero(int numero) {
        return Optional.ofNullable(contas.get(numero));
    }

    public List<Conta> listarTodas() {
        return new ArrayList<>(contas.values());
    }
}