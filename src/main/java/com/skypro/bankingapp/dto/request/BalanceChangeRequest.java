package com.skypro.bankingapp.dto.request;

public record BalanceChangeRequest(String username, String account, double amount) {

}
