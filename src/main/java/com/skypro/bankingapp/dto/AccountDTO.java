package com.skypro.bankingapp.dto;

import com.skypro.bankingapp.model.Account;
import com.skypro.bankingapp.model.Currency;

public record AccountDTO(String accountNumber, Currency currency, double amount) {
    public static AccountDTO fromAccount(Account account) {
        return new AccountDTO(account.getAccountNumber(), account.getCurrency(), account.getBalance());
    }
}
