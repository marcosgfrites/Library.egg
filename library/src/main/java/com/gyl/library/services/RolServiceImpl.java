package com.gyl.library.services;

import com.gyl.library.entities.RolEntity;
import com.gyl.library.entities.UserEntity;
import com.gyl.library.repositories.RolRepository;
import com.gyl.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;
    private UserRepository userRepository;

    @Override
    @Transactional
    public void createRol(String namerol, UserEntity user){
        RolEntity rol = new RolEntity();
        rol.setNamerol(namerol.toUpperCase());
        rol.setUser(user);
        rol.setActivate(true);
        rolRepository.save(rol);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RolEntity> getAllRolesActivated() {
        return rolRepository.findByActivateTrueOrderByNamerolAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public RolEntity findRolById(Integer id_rol) { return rolRepository.findById(id_rol).get(); }

    public boolean rolExist(String namerol, UserEntity user) {
        for (RolEntity rol : this.getAllRolesActivated()) {
            if (rol.getNamerol().equals(namerol) && rol.getUser().equals(user)) {
                return true;
            }
        }
        return false;
    }

    public void validateFormAndCreate(String namerol, UserEntity user) throws Exception {
        if (this.rolExist(namerol, user)) {
            throw new Exception("Ya se ha asignado el rol deseado al usuario seleccionado.");
        }
        this.createRol(namerol, user);
    }

}
