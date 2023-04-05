package com.skypro.bankingapp.dto;

import com.skypro.bankingapp.model.User;

public record UserDTO(String username, String firstname, String lastname) {
    public static UserDTO fromUser(User user) {
        return
    }
}
