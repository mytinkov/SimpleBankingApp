package com.skypro.bankingapp.dto.request;

public record ChangePasswordRequest(String oldPassword, String newPassword, String newPasswordRepeat) {}
