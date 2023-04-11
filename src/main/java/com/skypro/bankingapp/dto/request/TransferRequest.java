package com.skypro.bankingapp.dto.request;

public record TransferRequest(
        String userFrom, String accountFrom, String userTo, String accountTo, double amount) {}
