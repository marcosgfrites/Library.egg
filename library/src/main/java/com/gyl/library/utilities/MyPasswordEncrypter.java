package com.gyl.library.utilities;

import lombok.var;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MyPasswordEncrypter {

    public static void main(String[] args) {
        var password = "ContraseñaAEncriptar";
        System.out.println("Password: " + password);
        System.out.println("Encriptado: " + encriptarPassword(password));
    }

    public static String encriptarPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

}
