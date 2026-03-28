package com.bank.repository;

import com.bank.model.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerRepository {
    private final List<Customer> customers = new ArrayList<>();

    public void save(Customer customer) {
        customers.add(customer);
    }

    public Optional<Customer> findByCpf(String cpf) {
        return customers.stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst();
    }

    public Optional<Customer> findById(String id) {
        return customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    public List<Customer> findAll() {
        return new ArrayList<>(customers);
    }

    public boolean existsByCpf(String cpf) {
        return customers.stream().anyMatch(c -> c.getCpf().equals(cpf));
    }
}
