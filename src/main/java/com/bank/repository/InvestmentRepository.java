package com.bank.repository;

import com.bank.model.Investment;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InvestmentRepository {
    private final List<Investment> investments = new ArrayList<>();

    public void save(Investment investment) {
        investments.add(investment);
    }

    public List<Investment> findByCustomerId(String customerId) {
        return investments.stream()
                .filter(i -> i.getCustomer().getId().equals(customerId))
                .toList();
    }

    public Optional<Investment> findById(String id) {
        return investments.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();
    }

    public List<Investment> findAll() {
        return new ArrayList<>(investments);
    }
}
