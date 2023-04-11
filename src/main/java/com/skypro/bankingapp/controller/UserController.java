package com.skypro.bankingapp.controller;


import com.skypro.bankingapp.dto.UserDTO;
import com.skypro.bankingapp.dto.request.ChangePasswordRequest;
import com.skypro.bankingapp.dto.request.CreateUserRequest;
import com.skypro.bankingapp.service.UserService;
import java.util.Collection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<UserDTO> getAll() {
        return this.userService.getAllUsers();
    }

    @PostMapping
    public UserDTO createUser(@RequestBody CreateUserRequest userRequest) {
        return this.userService.addUser(userRequest);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest changePasswordRequest) {
        this.userService.updatePassword("", changePasswordRequest.oldPassword(),
                changePasswordRequest.newPassword());
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable("username") String username) {
        this.userService.removeUser(username);
        return ResponseEntity.noContent().build();
    }
}

