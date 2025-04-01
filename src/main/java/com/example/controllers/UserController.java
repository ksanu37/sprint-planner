package com.example.controllers;

import com.example.entities.User;
import com.example.services.UserService;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    public User addUser(final String name, final String email) {
        validateUserAddition(name, email);
        return this.userService.addUser(name, email);
    }


    public void validateUserAddition(final String name, final String email) {
        if(Objects.isNull(name) || Objects.isNull(email)) {
            throw new IllegalArgumentException("User Addition Request is not valid");
        }
    }
}
