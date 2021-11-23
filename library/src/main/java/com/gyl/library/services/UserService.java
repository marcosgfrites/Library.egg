package com.gyl.library.services;


import com.gyl.library.entities.RolEntity;
import com.gyl.library.entities.UserEntity;

import java.util.List;

public interface UserService {

    void createUser(String username, String mail, String password) throws Exception;
    List<UserEntity> getAllUsersActivated() throws Exception;

}
