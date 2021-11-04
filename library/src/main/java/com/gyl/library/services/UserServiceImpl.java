package com.gyl.library.services;

import com.gyl.library.entities.UserEntity;
import com.gyl.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final String message = "El usuario ingresado no existe. %s";

    @Override
    @Transactional
    public void createUser(String username, String password) {
        UserEntity user = new UserEntity();
        user.setUsername(username.toUpperCase());
        user.setPassword(passwordEncoder.encode(password));
        user.setActivate(true);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username);
            return new User(userEntity.getUsername(), userEntity.getPassword(), Collections.emptyList());
        } catch (Exception e) {
            throw new UsernameNotFoundException(String.format(message, username));
        }
    }

    public boolean userExist(String username) {
        if (this.findByUsername(username) != null) {
            return true;
        }
        return false;
    }

}
