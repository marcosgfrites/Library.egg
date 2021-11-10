package com.gyl.library.services;

import com.gyl.library.entities.RolEntity;
import com.gyl.library.entities.UserEntity;

import java.util.List;

public interface RolService {

    void createRol(String namerol, UserEntity user);
    RolEntity findRolById(Integer id_rol);
    List<RolEntity> getAllRolesActivated();

}
