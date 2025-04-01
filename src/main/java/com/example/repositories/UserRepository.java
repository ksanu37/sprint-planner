package com.example.repositories;

import com.example.entities.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class UserRepository {
    private Map<UUID, User> userMap;
    private static UserRepository instance;

    public static UserRepository getInstance() {
        if(instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }
    private UserRepository() {
        this.userMap = new HashMap<>();
    }

    public User addUser(User user) {
        this.userMap.put(user.getId(), user);
        return user;
    }
}
