package com.skypro.bankingapp.controller;

import com.skypro.bankingapp.dto.AccountDTO;
import com.skypro.bankingapp.dto.request.BalanceChangeRequest;
import com.skypro.bankingapp.dto.request.TransferRequest;
import com.skypro.bankingapp.service.AccountService;
import com.skypro.bankingapp.service.Operation;
import com.skypro.bankingapp.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;
    private final TransferService transferService;

    public AccountController(AccountService accountService, TransferService transferService) {
        this.accountService = accountService;
        this.transferService = transferService;
    }

    @GetMapping("/")
    public AccountDTO getBalance(
            @RequestParam("username") String username, @RequestParam("account") String account) {
        return accountService.getAccount(username, account);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transferMoney(@RequestBody TransferRequest transferRequest) {
        transferService.transferMoney(transferRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> depositMoney(@RequestBody BalanceChangeRequest balanceChangeRequest) {
        accountService.changeBalance(
                balanceChangeRequest.username(),
                balanceChangeRequest.account(),
                Operation.DEPOSIT,
                balanceChangeRequest.amount());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdrawMoney(@RequestBody BalanceChangeRequest balanceChangeRequest) {
        accountService.changeBalance(
                balanceChangeRequest.username(),
                balanceChangeRequest.account(),
                Operation.WITHDRAW,
                balanceChangeRequest.amount());
        return ResponseEntity.noContent().build();
    }
}
