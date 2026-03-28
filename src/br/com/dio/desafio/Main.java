package br.com.dio.desafio;

import br.com.dio.desafio.model.*;
import br.com.dio.desafio.enums.*;
import br.com.dio.desafio.repository.ContaRepository;
import br.com.dio.desafio.exception.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final ContaRepository repository = new ContaRepository();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n--- Sistema Bancário DIO (Versão Melhorada) ---");
            System.out.println("1. Criar Conta");
            System.out.println("2. Depositar");
            System.out.println("3. Sacar");
            System.out.println("4. Transferir via PIX");
            System.out.println("5. Realizar Investimento");
            System.out.println("6. Ver Extrato");
            System.out.println("7. Buscar Conta por CPF");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // consume newline

            try {
                switch (opcao) {
                    case 1 -> criarConta();
                    case 2 -> depositar();
                    case 3 -> sacar();
                    case 4 -> transferirPix();
                    case 5 -> investir();
                    case 6 -> extrato();
                    case 7 -> buscarPorCpf();
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (SaldoInsuficienteException | ContaNaoEncontradaException e) {
                System.err.println("ERRO DE NEGÓCIO: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("ERRO INESPERADO: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private static void criarConta() {
        System.out.print("Nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        Cliente cliente = new Cliente(nome, cpf);

        System.out.println("Tipo de conta (1-Corrente, 2-Poupança): ");
        int tipo = scanner.nextInt();
        
        Conta conta = (tipo == 1) ? new ContaCorrente(cliente) : new ContaPoupanca(cliente);
        repository.salvar(conta);
        System.out.println("Conta criada com sucesso! Número: " + conta.getNumero());
    }

    private static void depositar() {
        System.out.print("Número da conta: ");
        int numero = scanner.nextInt();
        System.out.print("Valor: ");
        double valor = scanner.nextDouble();
        
        Conta conta = repository.buscarPorNumero(numero).orElseThrow(() -> new ContaNaoEncontradaException(numero));
        conta.depositar(valor);
        System.out.println("Depósito realizado com sucesso!");
    }

    private static void sacar() {
        System.out.print("Número da conta: ");
        int numero = scanner.nextInt();
        System.out.print("Valor: ");
        double valor = scanner.nextDouble();
        
        Conta conta = repository.buscarPorNumero(numero).orElseThrow(() -> new ContaNaoEncontradaException(numero));
        conta.sacar(valor);
        System.out.println("Saque realizado com sucesso!");
    }

    private static void transferirPix() {
        System.out.print("Número da sua conta: ");
        int origemNum = scanner.nextInt();
        System.out.print("Número da conta destino: ");
        int destinoNum = scanner.nextInt();
        System.out.print("Valor: ");
        double valor = scanner.nextDouble();

        Conta origem = repository.buscarPorNumero(origemNum).orElseThrow(() -> new ContaNaoEncontradaException(origemNum));
        Conta destino = repository.buscarPorNumero(destinoNum).orElseThrow(() -> new ContaNaoEncontradaException(destinoNum));
        
        origem.transferirPix(destino, valor);
        System.out.println("Transferência via PIX de R$ " + valor + " realizada com sucesso!");
    }

    private static void investir() {
        System.out.print("Número da conta: ");
        int numero = scanner.nextInt();
        System.out.println("Escolha o investimento (1-CDB, 2-LCIA, 3-POUPANCA): ");
        int tipoInv = scanner.nextInt();
        System.out.print("Valor: ");
        double valor = scanner.nextDouble();

        TipoInvestimento tipo = switch (tipoInv) {
            case 1 -> TipoInvestimento.CDB;
            case 2 -> TipoInvestimento.LCIA;
            default -> TipoInvestimento.POUPANCA;
        };

        Conta conta = repository.buscarPorNumero(numero).orElseThrow(() -> new ContaNaoEncontradaException(numero));
        conta.aplicarInvestimento(valor, tipo);
    }

    private static void extrato() {
        System.out.print("Número da conta: ");
        int numero = scanner.nextInt();
        
        Conta conta = repository.buscarPorNumero(numero).orElseThrow(() -> new ContaNaoEncontradaException(numero));
        conta.imprimirExtrato();
    }

    private static void buscarPorCpf() {
        System.out.print("Digite o CPF para busca: ");
        String cpf = scanner.next();
        List<Conta> contas = repository.buscarPorCpf(cpf);
        
        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta vinculada a este CPF.");
        } else {
            System.out.println("Contas encontradas:");
            contas.forEach(c -> System.out.printf("Conta: %d | Tipo: %s | Saldo: R$ %.2f%n", 
                    c.getNumero(), c.getTipoConta(), c.getSaldo()));
        }
    }
}