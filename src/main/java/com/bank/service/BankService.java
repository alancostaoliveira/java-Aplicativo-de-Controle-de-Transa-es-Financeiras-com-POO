package com.bank.service;

import com.bank.enums.InvestmentType;
import com.bank.enums.PixKeyType;
import com.bank.enums.TransactionType;
import com.bank.model.*;
import com.bank.repository.AccountRepository;
import com.bank.repository.CustomerRepository;
import com.bank.repository.InvestmentRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class BankService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final InvestmentRepository investmentRepository;

    public BankService() {
        this.customerRepository = new CustomerRepository();
        this.accountRepository = new AccountRepository();
        this.investmentRepository = new InvestmentRepository();
    }

    public Customer createCustomer(String name, String cpf, String email, String phone) {
        if (customerRepository.existsByCpf(cpf)) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este CPF.");
        }
        Customer customer = Customer.builder()
                .name(name)
                .cpf(cpf)
                .email(email)
                .phone(phone)
                .build();
        customerRepository.save(customer);
        System.out.printf("Cliente %s cadastrado com sucesso! ID: %s%n", customer.getName(), customer.getId());
        return customer;
    }

    public CheckingAccount createCheckingAccount(String cpf) {
        Customer customer = findCustomerByCpfOrThrow(cpf);
        CheckingAccount account = new CheckingAccount(customer);
        accountRepository.save(account);
        System.out.printf("Conta Corrente criada! Número: %s%n", account.getAccountNumber());
        return account;
    }

    public SavingsAccount createSavingsAccount(String cpf) {
        Customer customer = findCustomerByCpfOrThrow(cpf);
        SavingsAccount account = new SavingsAccount(customer);
        accountRepository.save(account);
        System.out.printf("Conta Poupança criada! Número: %s%n", account.getAccountNumber());
        return account;
    }

    public void deposit(String accountNumber, BigDecimal amount) {
        Account account = findAccountOrThrow(accountNumber);
        account.deposit(amount);
    }

    public void withdraw(String accountNumber, BigDecimal amount) {
        Account account = findAccountOrThrow(accountNumber);
        account.withdraw(amount);
    }

    public void pixTransfer(String sourceAccountNumber, String pixKey, BigDecimal amount) {
        Account sourceAccount = findAccountOrThrow(sourceAccountNumber);
        Optional<PixKey> pixKeyOpt = accountRepository.findPixKeyByKey(pixKey);
        if (pixKeyOpt.isEmpty()) {
            throw new IllegalArgumentException("Chave PIX não encontrada: " + pixKey);
        }
        Account destinationAccount = pixKeyOpt.get().getAccount();
        if (sourceAccount.getAccountNumber().equals(destinationAccount.getAccountNumber())) {
            throw new IllegalArgumentException("Não é possível transferir para a mesma conta.");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser maior que zero.");
        }

        sourceAccount.withdraw(amount);
        // Override last transaction description for PIX
        int lastIdx = sourceAccount.getTransactions().size() - 1;
        sourceAccount.getTransactions().set(lastIdx, new Transaction(
                TransactionType.TRANSFERENCIA_PIX,
                amount,
                String.format("PIX enviado para %s (chave: %s)", destinationAccount.getCustomer().getName(), pixKey),
                sourceAccount.getBalance()
        ));

        // Credit destination
        destinationAccount.setBalance(destinationAccount.getBalance().add(amount));
        destinationAccount.getTransactions().add(new Transaction(
                TransactionType.TRANSFERENCIA_PIX,
                amount,
                String.format("PIX recebido de %s (chave: %s)", sourceAccount.getCustomer().getName(), pixKey),
                destinationAccount.getBalance()
        ));

        System.out.printf("PIX de R$ %.2f enviado com sucesso para %s!%n", amount, destinationAccount.getCustomer().getName());
    }

    public PixKey registerPixKey(String accountNumber, String key, PixKeyType keyType) {
        Account account = findAccountOrThrow(accountNumber);
        if (accountRepository.pixKeyExists(key)) {
            throw new IllegalArgumentException("Esta chave PIX já está cadastrada.");
        }
        PixKey pixKey = new PixKey(key, keyType, account);
        accountRepository.savePixKey(pixKey);
        System.out.printf("Chave PIX '%s' cadastrada com sucesso!%n", key);
        return pixKey;
    }

    public Investment createInvestment(String cpf, String accountNumber, InvestmentType type, BigDecimal amount, int daysToMaturity) {
        Customer customer = findCustomerByCpfOrThrow(cpf);
        Account account = findAccountOrThrow(accountNumber);
        if (!account.getCustomer().getCpf().equals(cpf)) {
            throw new IllegalArgumentException("A conta não pertence a este cliente.");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do investimento deve ser maior que zero.");
        }
        if (amount.compareTo(account.getBalance()) > 0) {
            throw new IllegalStateException(
                String.format("Saldo insuficiente para investir. Saldo disponível: R$ %.2f", account.getBalance()));
        }

        account.setBalance(account.getBalance().subtract(amount));
        account.getTransactions().add(new Transaction(
                TransactionType.INVESTIMENTO,
                amount,
                String.format("Aplicação em %s", type.getDescricao()),
                account.getBalance()
        ));

        Investment investment = new Investment(customer, type, amount, daysToMaturity);
        investmentRepository.save(investment);
        System.out.printf("Investimento de R$ %.2f em %s criado com sucesso! Vencimento: %s%n",
                amount, type.getDescricao(), investment.getMaturityDate());
        return investment;
    }

    public void redeemInvestment(String investmentId, String accountNumber) {
        Optional<Investment> investmentOpt = investmentRepository.findById(investmentId);
        if (investmentOpt.isEmpty()) {
            throw new IllegalArgumentException("Investimento não encontrado: " + investmentId);
        }
        Investment investment = investmentOpt.get();
        if (investment.isRedeemed()) {
            throw new IllegalStateException("Este investimento já foi resgatado.");
        }
        Account account = findAccountOrThrow(accountNumber);
        BigDecimal currentValue = investment.calculateCurrentValue();
        account.setBalance(account.getBalance().add(currentValue));
        account.getTransactions().add(new Transaction(
                TransactionType.RESGATE_INVESTIMENTO,
                currentValue,
                String.format("Resgate de %s | Valor inicial: R$ %.2f | Rendimento: R$ %.2f",
                        investment.getType().getDescricao(),
                        investment.getInitialAmount(),
                        currentValue.subtract(investment.getInitialAmount())),
                account.getBalance()
        ));
        investment.redeem();
        System.out.printf("Investimento resgatado! Valor creditado: R$ %.2f%n", currentValue);
    }

    public void printStatement(String accountNumber) {
        Account account = findAccountOrThrow(accountNumber);
        account.printStatement();
    }

    public List<Account> getCustomerAccounts(String cpf) {
        Customer customer = findCustomerByCpfOrThrow(cpf);
        return accountRepository.findByCustomerId(customer.getId());
    }

    public List<Investment> getCustomerInvestments(String cpf) {
        Customer customer = findCustomerByCpfOrThrow(cpf);
        return investmentRepository.findByCustomerId(customer.getId());
    }

    public List<PixKey> getAccountPixKeys(String accountNumber) {
        return accountRepository.findPixKeysByAccountNumber(accountNumber);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    private Customer findCustomerByCpfOrThrow(String cpf) {
        return customerRepository.findByCpf(cpf)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com CPF: " + cpf));
    }

    private Account findAccountOrThrow(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada: " + accountNumber));
    }
}
