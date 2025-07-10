package com.polarday.example.common.service;

import com.polarday.example.common.model.User;

public interface UserService {
    User getUser(User user);

    default short getNumber() {
        return 1;
    }
}
