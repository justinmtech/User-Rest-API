package com.justinmtech.user_api.controllers;

import com.justinmtech.user_api.entities.User;
import com.justinmtech.user_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/getUsers")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/getUser/{email}")
    public ResponseEntity<User> getUser(@PathVariable(name = "email") String email) {
        Optional<User> user = userService.getUser(email);
        if (user.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(user.get());
    }

    @PostMapping("/saveUser")
    public User saveUser(@Validated @RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Validated @RequestBody User user) {
        Optional<User> account = userService.getUser(user.getEmail());
        String passwordEntered = user.getPassword();
        if (account.isEmpty()) return ResponseEntity.notFound().build();
        boolean authenticated = BCrypt.checkpw(passwordEntered.getBytes(), account.get().getPassword());
        if (!authenticated) return ResponseEntity.badRequest().build();
        return ResponseEntity.accepted().build();
    }
}
