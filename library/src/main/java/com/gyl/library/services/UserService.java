package com.gyl.library.services;

import com.gyl.library.entities.UserEntity;

public interface UserService {

    void createUser(String username, String password);
    UserEntity findByUsername(String username);

}
