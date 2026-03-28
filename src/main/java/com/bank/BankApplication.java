package com.bank;

import com.bank.enums.InvestmentType;
import com.bank.enums.PixKeyType;
import com.bank.model.*;
import com.bank.service.BankService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class BankApplication {
    private static final BankService bankService = new BankService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("  Bem-vindo ao Sistema de Controle Financeiro - DIO Bank");
        System.out.println("=".repeat(60));

        boolean running = true;
        while (running) {
            printMainMenu();
            int option = readInt("Escolha uma opção: ");
            switch (option) {
                case 1 -> manageCustomers();
                case 2 -> manageAccounts();
                case 3 -> manageTransactions();
                case 4 -> managePix();
                case 5 -> manageInvestments();
                case 0 -> {
                    System.out.println("Obrigado por usar o DIO Bank. Até logo!");
                    running = false;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Gerenciar Clientes");
        System.out.println("2. Gerenciar Contas");
        System.out.println("3. Transações (Depósito/Saque/Extrato)");
        System.out.println("4. PIX");
        System.out.println("5. Investimentos");
        System.out.println("0. Sair");
    }

    private static void manageCustomers() {
        System.out.println("\n--- GERENCIAR CLIENTES ---");
        System.out.println("1. Cadastrar novo cliente");
        System.out.println("2. Listar todos os clientes");
        System.out.println("0. Voltar");
        int option = readInt("Opção: ");
        switch (option) {
            case 1 -> {
                String name = readString("Nome completo: ");
                String cpf = readString("CPF (somente números): ");
                String email = readString("E-mail: ");
                String phone = readString("Telefone: ");
                try {
                    bankService.createCustomer(name, cpf, email, phone);
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
            case 2 -> {
                List<Customer> customers = bankService.getAllCustomers();
                if (customers.isEmpty()) {
                    System.out.println("Nenhum cliente cadastrado.");
                } else {
                    System.out.println("\n--- CLIENTES ---");
                    customers.forEach(System.out::println);
                }
            }
            case 0 -> {}
            default -> System.out.println("Opção inválida.");
        }
    }

    private static void manageAccounts() {
        System.out.println("\n--- GERENCIAR CONTAS ---");
        System.out.println("1. Abrir Conta Corrente");
        System.out.println("2. Abrir Conta Poupança");
        System.out.println("3. Listar contas do cliente");
        System.out.println("4. Listar todas as contas");
        System.out.println("0. Voltar");
        int option = readInt("Opção: ");
        switch (option) {
            case 1 -> {
                String cpf = readString("CPF do cliente: ");
                try {
                    bankService.createCheckingAccount(cpf);
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
            case 2 -> {
                String cpf = readString("CPF do cliente: ");
                try {
                    bankService.createSavingsAccount(cpf);
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
            case 3 -> {
                String cpf = readString("CPF do cliente: ");
                try {
                    List<Account> accounts = bankService.getCustomerAccounts(cpf);
                    if (accounts.isEmpty()) {
                        System.out.println("Nenhuma conta encontrada para este cliente.");
                    } else {
                        accounts.forEach(System.out::println);
                    }
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
            case 4 -> {
                List<Account> accounts = bankService.getAllAccounts();
                if (accounts.isEmpty()) {
                    System.out.println("Nenhuma conta cadastrada.");
                } else {
                    accounts.forEach(System.out::println);
                }
            }
            case 0 -> {}
            default -> System.out.println("Opção inválida.");
        }
    }

    private static void manageTransactions() {
        System.out.println("\n--- TRANSAÇÕES ---");
        System.out.println("1. Depositar");
        System.out.println("2. Sacar");
        System.out.println("3. Ver extrato");
        System.out.println("0. Voltar");
        int option = readInt("Opção: ");
        switch (option) {
            case 1 -> {
                String accountNumber = readString("Número da conta: ");
                BigDecimal amount = readBigDecimal("Valor do depósito: R$ ");
                try {
                    bankService.deposit(accountNumber, amount);
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
            case 2 -> {
                String accountNumber = readString("Número da conta: ");
                BigDecimal amount = readBigDecimal("Valor do saque: R$ ");
                try {
                    bankService.withdraw(accountNumber, amount);
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
            case 3 -> {
                String accountNumber = readString("Número da conta: ");
                try {
                    bankService.printStatement(accountNumber);
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
            case 0 -> {}
            default -> System.out.println("Opção inválida.");
        }
    }

    private static void managePix() {
        System.out.println("\n--- PIX ---");
        System.out.println("1. Cadastrar chave PIX");
        System.out.println("2. Realizar transferência PIX");
        System.out.println("3. Ver chaves PIX da conta");
        System.out.println("0. Voltar");
        int option = readInt("Opção: ");
        switch (option) {
            case 1 -> {
                String accountNumber = readString("Número da conta: ");
                System.out.println("Tipo de chave:");
                System.out.println("1. CPF");
                System.out.println("2. E-mail");
                System.out.println("3. Telefone");
                System.out.println("4. Chave Aleatória");
                int keyTypeOption = readInt("Tipo: ");
                PixKeyType keyType = switch (keyTypeOption) {
                    case 1 -> PixKeyType.CPF;
                    case 2 -> PixKeyType.EMAIL;
                    case 3 -> PixKeyType.TELEFONE;
                    case 4 -> PixKeyType.CHAVE_ALEATORIA;
                    default -> throw new IllegalArgumentException("Tipo de chave inválido.");
                };
                String key = readString("Valor da chave: ");
                try {
                    bankService.registerPixKey(accountNumber, key, keyType);
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
            case 2 -> {
                String sourceAccount = readString("Número da conta de origem: ");
                String pixKey = readString("Chave PIX de destino: ");
                BigDecimal amount = readBigDecimal("Valor da transferência: R$ ");
                try {
                    bankService.pixTransfer(sourceAccount, pixKey, amount);
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
            case 3 -> {
                String accountNumber = readString("Número da conta: ");
                try {
                    List<PixKey> keys = bankService.getAccountPixKeys(accountNumber);
                    if (keys.isEmpty()) {
                        System.out.println("Nenhuma chave PIX cadastrada para esta conta.");
                    } else {
                        keys.forEach(System.out::println);
                    }
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
            case 0 -> {}
            default -> System.out.println("Opção inválida.");
        }
    }

    private static void manageInvestments() {
        System.out.println("\n--- INVESTIMENTOS ---");
        System.out.println("1. Criar investimento");
        System.out.println("2. Ver investimentos do cliente");
        System.out.println("3. Resgatar investimento");
        System.out.println("0. Voltar");
        int option = readInt("Opção: ");
        switch (option) {
            case 1 -> {
                String cpf = readString("CPF do cliente: ");
                String accountNumber = readString("Número da conta para débito: ");
                System.out.println("Tipo de investimento:");
                System.out.println("1. CDB (12,5% a.a.)");
                System.out.println("2. LCI (10,0% a.a.)");
                System.out.println("3. Tesouro Direto Selic (11,75% a.a.)");
                int typeOption = readInt("Tipo: ");
                InvestmentType type = switch (typeOption) {
                    case 1 -> InvestmentType.CDB;
                    case 2 -> InvestmentType.LCI;
                    case 3 -> InvestmentType.TESOURO_DIRETO;
                    default -> throw new IllegalArgumentException("Tipo inválido.");
                };
                BigDecimal amount = readBigDecimal("Valor a investir: R$ ");
                int days = readInt("Prazo (dias): ");
                try {
                    bankService.createInvestment(cpf, accountNumber, type, amount, days);
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
            case 2 -> {
                String cpf = readString("CPF do cliente: ");
                try {
                    List<Investment> investments = bankService.getCustomerInvestments(cpf);
                    if (investments.isEmpty()) {
                        System.out.println("Nenhum investimento encontrado.");
                    } else {
                        System.out.println("\n--- INVESTIMENTOS ---");
                        for (int i = 0; i < investments.size(); i++) {
                            System.out.printf("%d. %s%n", i + 1, investments.get(i));
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
            case 3 -> {
                String investmentId = readString("ID do investimento: ");
                String accountNumber = readString("Número da conta para crédito: ");
                try {
                    bankService.redeemInvestment(investmentId, accountNumber);
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
            case 0 -> {}
            default -> System.out.println("Opção inválida.");
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número inteiro válido.");
            }
        }
    }

    private static BigDecimal readBigDecimal(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                // Handle Brazilian format (e.g. "1.000,50") and standard format (e.g. "1000.50")
                boolean hasDotAndComma = input.contains(".") && input.contains(",");
                if (hasDotAndComma) {
                    // Brazilian thousands + decimal: remove dots then replace comma with dot
                    input = input.replace(".", "").replace(",", ".");
                } else {
                    // Replace comma-as-decimal with dot (e.g. "100,50" → "100.50")
                    input = input.replace(",", ".");
                }
                return new BigDecimal(input);
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um valor monetário válido (ex: 100.00 ou 100,00).");
            }
        }
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
