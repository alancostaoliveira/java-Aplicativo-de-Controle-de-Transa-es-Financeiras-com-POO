package com.bank.model;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String name;
    private String cpf;
    private String email;
    private String phone;

    @Override
    public String toString() {
        return String.format("Cliente: %s | CPF: %s | Email: %s | Telefone: %s",
                name, cpf, email, phone);
    }
}
