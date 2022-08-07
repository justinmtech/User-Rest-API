package com.justinmtech.user_api.services;

import com.justinmtech.user_api.entities.User;
import com.justinmtech.user_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getUsers() { return (List<User>) userRepository.findAll(); }

    public Optional<User> getUser(String email) {
        return userRepository.findById(email);
    }

    public User saveUser(User user) {
        String hashPw = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashPw);
        return userRepository.save(user);
    }
}
