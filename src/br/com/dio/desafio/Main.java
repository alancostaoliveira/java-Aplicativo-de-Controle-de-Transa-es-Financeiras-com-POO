package br.com.dio.desafio;

import br.com.dio.desafio.model.*;
import br.com.dio.desafio.enums.*;
import br.com.dio.desafio.repository.ContaRepository;

import java.util.Scanner;

public class Main {
    private static final ContaRepository repository = new ContaRepository();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n--- Sistema Bancário DIO ---");
            System.out.println("1. Criar Conta");
            System.out.println("2. Depositar");
            System.out.println("3. Sacar");
            System.out.println("4. Transferir via PIX");
            System.out.println("5. Realizar Investimento");
            System.out.println("6. Ver Extrato");
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
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.err.println("Erro: " + e.getMessage());
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
        
        Conta conta;
        if (tipo == 1) {
            conta = new ContaCorrente(cliente);
        } else {
            conta = new ContaPoupanca(cliente);
        }
        
        repository.salvar(conta);
        System.out.println("Conta criada com sucesso! Número: " + conta.getNumero());
    }

    private static void depositar() {
        System.out.print("Número da conta: ");
        int numero = scanner.nextInt();
        System.out.print("Valor: ");
        double valor = scanner.nextDouble();
        
        repository.buscarPorNumero(numero).ifPresentOrElse(
            conta -> {
                conta.depositar(valor);
                System.out.println("Depósito realizado!");
            },
            () -> System.out.println("Conta não encontrada.")
        );
    }

    private static void sacar() {
        System.out.print("Número da conta: ");
        int numero = scanner.nextInt();
        System.out.print("Valor: ");
        double valor = scanner.nextDouble();
        
        repository.buscarPorNumero(numero).ifPresentOrElse(
            conta -> {
                conta.sacar(valor);
                System.out.println("Saque realizado!");
            },
            () -> System.out.println("Conta não encontrada.")
        );
    }

    private static void transferirPix() {
        System.out.print("Número da sua conta: ");
        int origemNum = scanner.nextInt();
        System.out.print("Número da conta destino: ");
        int destinoNum = scanner.nextInt();
        System.out.print("Valor: ");
        double valor = scanner.nextDouble();

        repository.buscarPorNumero(origemNum).ifPresentOrElse(
            origem -> repository.buscarPorNumero(destinoNum).ifPresentOrElse(
                destino -> {
                    origem.transferirPix(destino, valor);
                    System.out.println("Transferência realizada!");
                },
                () -> System.out.println("Conta destino não encontrada.")
            ),
            () -> System.out.println("Sua conta não foi encontrada.")
        );
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

        repository.buscarPorNumero(numero).ifPresentOrElse(
            conta -> conta.aplicarInvestimento(valor, tipo),
            () -> System.out.println("Conta não encontrada.")
        );
    }

    private static void extrato() {
        System.out.print("Número da conta: ");
        int numero = scanner.nextInt();
        
        repository.buscarPorNumero(numero).ifPresentOrElse(
            conta -> {
                System.out.println("\n=== Extrato ===");
                System.out.println("Titular: " + conta.getTitular().getNome());
                System.out.println("Saldo: R$ " + conta.getSaldo());
                System.out.println("Transações:");
                conta.getHistoricoTransacoes().forEach(t -> 
                    System.out.printf("%s - %s: R$ %.2f%n", t.dataHora(), t.tipo(), t.valor())
                );
            },
            () -> System.out.println("Conta não encontrada.")
        );
    }
}