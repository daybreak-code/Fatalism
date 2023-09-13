package cn.daycode.fatalism.uaa.repository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public abstract class PasswordHandler {

    private PasswordHandler() {
    }

    public static String encode(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
}
