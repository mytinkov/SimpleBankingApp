package com.skypro.bankingapp.service;

import com.skypro.bankingapp.dto.AccountDTO;
import com.skypro.bankingapp.exception.AccountNotFoundException;
import com.skypro.bankingapp.exception.InsufficientFundsException;
import com.skypro.bankingapp.exception.InvalidChangeAmountException;
import com.skypro.bankingapp.model.Account;
import com.skypro.bankingapp.model.User;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final UserService userService;

    public AccountService(UserService userService) {
        this.userService = userService;
    }

    public void changeBalance(
            String username, String accountNumber, Operation operation, double amount) {
        if (amount <= 0) {
            throw new InvalidChangeAmountException();
        }
        User user = userService.getUser(username);
        Account account =
                user.getAccounts().stream()
                        .filter(acc -> acc.getAccountNumber().equals(accountNumber))
                        .findFirst()
                        .orElseThrow(AccountNotFoundException::new);
        if (operation.equals(Operation.DEPOSIT)) {
            depositOnAccount(account, amount);
        } else {
            withdrawFromAccount(account, amount);
        }
    }

    public AccountDTO getAccount(String username, String accountNumber) {
        User user = userService.getUser(username);
        return user.getAccounts().stream()
                .filter(acc -> acc.getAccountNumber().equals(accountNumber))
                .findFirst()
                .map(AccountDTO::fromAccount)
                .orElseThrow(AccountNotFoundException::new);
    }

    private void withdrawFromAccount(Account account, double amount) {
        if (account.getBalance() < amount) {
            throw new InsufficientFundsException();
        }
        account.setBalance(account.getBalance() - amount);
    }

    private void depositOnAccount(Account account, double amount) {
        account.setBalance(account.getBalance() + amount);
    }
}
