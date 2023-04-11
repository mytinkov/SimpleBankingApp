package com.skypro.bankingapp.service;

import com.skypro.bankingapp.dto.UserDTO;
import com.skypro.bankingapp.dto.request.CreateUserRequest;
import com.skypro.bankingapp.exception.InvalidPasswordException;
import com.skypro.bankingapp.exception.UserAlreadyExistsException;
import com.skypro.bankingapp.exception.UserNotFoundException;
import com.skypro.bankingapp.model.Account;
import com.skypro.bankingapp.model.Currency;
import com.skypro.bankingapp.model.User;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
@Service
public class UserService {

    private final Map<String, User> users = new HashMap<>();

    public UserDTO addUser(CreateUserRequest request) {
        if (users.containsKey(request.username())) {
            throw new UserAlreadyExistsException();
        }
        validateUser(request);
        User user = request.toUser();
        users.put(user.getUsername(), user);
        return UserDTO.fromUser(createNewUserAccounts(user));
    }

    private void validateUser(CreateUserRequest request) {
    }

    public User updateUser(String username, String firstName, String lastName) {
        if (!users.containsKey(username)) {
            throw new UserNotFoundException();
        }
        User user = users.get(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        return user;
    }

    public void updatePassword(String username, String password, String newPassword) {
        if (!users.containsKey(username)) {
            throw new UserNotFoundException();
        }

        User user = users.get(username);
        if (!user.getPassword().equals(password)) {
            throw new InvalidPasswordException();
        }

        user.setPassword(newPassword);
    }

    public void removeUser(String username) {
        if (!users.containsKey(username)) {
            throw new UserNotFoundException();
        }
        users.remove(username);
    }

    public User getUser(String username) {
        if (!users.containsKey(username)) {
            throw new UserNotFoundException();
        }
        return users.get(username);
    }

    public Collection<UserDTO> getAllUsers() {
        return users.values().stream().map(UserDTO::fromUser).collect(Collectors.toList());
    }

    private User createNewUserAccounts(User user) {
        user.addAccount(new Account(UUID.randomUUID().toString(), 0.0, Currency.RUB));
        user.addAccount(new Account(UUID.randomUUID().toString(), 0.0, Currency.EUR));
        user.addAccount(new Account(UUID.randomUUID().toString(), 0.0, Currency.USD));

        return user;
    }
}
