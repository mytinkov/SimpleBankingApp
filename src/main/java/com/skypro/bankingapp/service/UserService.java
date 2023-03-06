package com.skypro.bankingapp.service;

import com.skypro.bankingapp.exception.InvalidPasswordException;
import com.skypro.bankingapp.exception.UserAlreadyExistsException;
import com.skypro.bankingapp.exception.UserNotFoundException;
import com.skypro.bankingapp.model.Account;
import com.skypro.bankingapp.model.Currency;
import com.skypro.bankingapp.model.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final Map<String, User> users = new HashMap<>();

    public User addUser(User user) {
        if (users.containsKey(user.getUsername())) {
            throw new UserAlreadyExistsException();
        }
        users.put(user.getUsername(), user);
        return createNewUserAccounts(user);
    }

    public User updateUser(String userName, String firstName, String lastName) {
        if (!users.containsKey(userName)) {
            throw new UserNotFoundException();
        }
        User user = users.get(userName);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        return user;
    }

    public void updatePassword(String username, String password, String newPassword) {
        if (!users.containsKey(username)) {
            throw new UserNotFoundException();
        }
        User user = users.get(username);
        if (user.getPassword().equals(newPassword)) {
            throw new InvalidPasswordException();
        }
        user.setPassword(newPassword);
    }

    public User removeUser(String userName) {
        if (!users.containsKey(userName)) {
            throw new UserNotFoundException();
        }
        return users.remove(userName);
    }

    public User getUser(String username) {
        if (users.containsKey(username)) {
            throw new UserNotFoundException();
        }
        return users.get(username);
    }

    public Collection<User> getAllUser() {
        return users.values();
    }

    private User createNewUserAccounts(User user) {
        user.addAccount(new Account(UUID.randomUUID().toString(), 0.0, Currency.RUB));
        user.addAccount(new Account(UUID.randomUUID().toString(), 0.0, Currency.EUR));
        user.addAccount(new Account(UUID.randomUUID().toString(), 0.0, Currency.USD));
        return user;
    }
}
