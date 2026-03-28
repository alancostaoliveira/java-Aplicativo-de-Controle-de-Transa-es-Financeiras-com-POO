package com.bank.repository;

import com.bank.model.Account;
import com.bank.model.PixKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountRepository {
    private final List<Account> accounts = new ArrayList<>();
    private final List<PixKey> pixKeys = new ArrayList<>();

    public void save(Account account) {
        accounts.add(account);
    }

    public Optional<Account> findByAccountNumber(String accountNumber) {
        return accounts.stream()
                .filter(a -> a.getAccountNumber().equals(accountNumber))
                .findFirst();
    }

    public List<Account> findByCustomerId(String customerId) {
        return accounts.stream()
                .filter(a -> a.getCustomer().getId().equals(customerId))
                .toList();
    }

    public List<Account> findAll() {
        return new ArrayList<>(accounts);
    }

    public void savePixKey(PixKey pixKey) {
        pixKeys.add(pixKey);
    }

    public Optional<PixKey> findPixKeyByKey(String key) {
        return pixKeys.stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst();
    }

    public List<PixKey> findPixKeysByAccountNumber(String accountNumber) {
        return pixKeys.stream()
                .filter(p -> p.getAccount().getAccountNumber().equals(accountNumber))
                .toList();
    }

    public boolean pixKeyExists(String key) {
        return pixKeys.stream().anyMatch(p -> p.getKey().equals(key));
    }
}
