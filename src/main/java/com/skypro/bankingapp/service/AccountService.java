package com.skypro.bankingapp.service;

import com.skypro.bankingapp.dto.AccountDTO;
import com.skypro.bankingapp.exception.AccountNotFoundException;
import com.skypro.bankingapp.exception.InsufficientFundsException;
import com.skypro.bankingapp.exception.InvalidChangeAmountException;
import com.skypro.bankingapp.model.Account;
import com.skypro.bankingapp.model.Currency;
import com.skypro.bankingapp.model.User;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final UserService userService;

    public AccountService(UserService userService) {
        this.userService = userService;
    }

    //Изменение баланса
    public Account changeBalance(String username, String accountNumber, Operation operation, double amount) {
        if (amount <= 0) {
            throw new InvalidChangeAmountException();
        }
        User user = userService.getUser(username);
//      User user = userService.getUser(username).orElseThrow(UserNotFoundException::new); если getUser - Optional<User>
        Account account = user.getAccounts().stream()
                .filter(acc -> acc.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElseThrow(AccountNotFoundException::new);
        if (operation.equals(Operation.DEPOSIT)) {
            return depositFromAccount(account, amount);
        } else {
            return withdrawFromAccount(account, amount);
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

    //Получение денег
    private Account withdrawFromAccount(Account account, double amount) {
        if (account.getBalance() < amount) {
            throw new InsufficientFundsException();
        }
        account.setBalance(account.getBalance() - amount);
        return account;
    }

    //Пополнение
    private Account depositFromAccount(Account account, double amount) {
        account.setBalance(account.getBalance() + amount);
        return account;
    }

}
