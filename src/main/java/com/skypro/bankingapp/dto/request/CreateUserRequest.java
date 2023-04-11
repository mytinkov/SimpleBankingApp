package com.skypro.bankingapp.dto.request;

import com.skypro.bankingapp.model.User;

public record CreateUserRequest(String firstName, String lastName, String username, String password,
                                String repeatPassword) {

    public User toUser() {
        return new User(this.firstName, this.lastName, this.username, this.password);
    }
}
