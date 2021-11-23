package com.gyl.library.services;

import com.gyl.library.entities.RolEntity;
import com.gyl.library.entities.UserEntity;
import com.gyl.library.repositories.RolRepository;
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
    RolRepository rolRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private final String message = "El usuario ingresado no existe. %s";

    @Override
    @Transactional
    public void createUser(String username, String mail, String password){
        UserEntity user = new UserEntity();
        user.setUsername(username.toUpperCase());
        user.setMail(mail.toUpperCase());
        user.setPassword(passwordEncoder.encode(password));
        user.setActivate(true);

        RolEntity rol = new RolEntity();
        rol.setNamerol("USER");
        rol.setUser(userRepository.save(user));
        rol.setActivate(true);
        rolRepository.save(rol);

        emailService.sendMail(username, mail);
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

    public boolean usernameExist(String username) {
        if (userRepository.findByUsernameIgnoreCase(username).orElse(null) != null) {
            return true;
        }
        return false;
    }

    public boolean mailExist(String mail) {
        if (userRepository.findByMailIgnoreCase(mail).orElse(null) != null) {
            return true;
        }
        return false;
    }

    public void validateFormAndCreate(String username, String mail, String password, String repassword) throws Exception {
        if (this.usernameExist(username)) {
            throw new Exception("Ya existe un usuario registrado con el mismo username.");
        }
        if (this.mailExist(mail)) {
            throw new Exception("Ya existe un usuario registrado con el mismo mail.");
        }
        if (!(password.equals(repassword))) {
            throw new Exception("La password y su confirmación no coinciden.");
        }
        this.createUser(username, mail, password);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserEntity> getAllUsersActivated() {
        return userRepository.findByActivateTrueOrderByUsernameAsc();
    }

}
