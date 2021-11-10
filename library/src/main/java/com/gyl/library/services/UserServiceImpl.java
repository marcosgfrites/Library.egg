package com.gyl.library.services;

import com.gyl.library.entities.RolEntity;
import com.gyl.library.entities.UserEntity;
import com.gyl.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    private final String message = "El usuario ingresado no existe. %s";

    @Override
    @Transactional
    public void createUser(String username, String password){
        UserEntity user = new UserEntity();
        user.setUsername(username.toUpperCase());
        user.setPassword(passwordEncoder.encode(password));
        user.setActivate(true);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity findByUsernameIgnoreCase(String username) {
        return userRepository.findByUsernameIgnoreCase(username.toUpperCase()).get();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(message, username)));

        /* INICIO DE RECUPERO DE USUARIO LOGUEADO */
/*
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);
        session.setAttribute("onlineuser", userEntity);
*/
        /* FIN DE RECUPERO DE USUARIO LOGUEADO */

        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        for (RolEntity rol : userEntity.getRoles()) {
            roles.add(new SimpleGrantedAuthority("ROLE_" + rol.getNamerol()));
        }

        return new User(userEntity.getUsername(), userEntity.getPassword(), roles);
    }

    public boolean userExist(String username) {
        if (userRepository.findByUsernameIgnoreCase(username).orElse(null) != null) {
            return true;
        }
        return false;
    }

    public void validateFormAndCreate(String username, String password, String repassword) throws Exception {
        if (this.userExist(username)) {
            throw new Exception("Ya existe un usuario registrado con el mismo username.");
        }
        if (!(password.equals(repassword))) {
            throw new Exception("La password y su confirmación no coinciden.");
        }
        this.createUser(username, password);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserEntity> getAllUsersActivated() {
        return userRepository.findByActivateTrueOrderByUsernameAsc();
    }
}
