package com.bank.model;

import com.bank.enums.PixKeyType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PixKey {
    private String key;
    private PixKeyType keyType;
    private Account account;

    @Override
    public String toString() {
        return String.format("Chave PIX | Tipo: %s | Chave: %s | Titular: %s",
                keyType.getDescricao(), key, account.getCustomer().getName());
    }
}
