package com.example.services.impl;

import com.example.entities.User;
import com.example.repositories.UserRepository;
import com.example.services.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public User addUser(String name, String email) {
        return this.userRepository.addUser(new User(UUID.randomUUID(), name, email)); //TODO validate already present user
    }
}
