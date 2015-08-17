package ru.tsystems.shalamov;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

/**
 * Created by viacheslav on 17.08.2015.
 */
public class PasswordEncoderGenerator {

    public static void main(String[] args) {
        String password = "abacaba";

        ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder(256);
        String hashedPassword = passwordEncoder.encodePassword(password, "");
        System.out.println(password);
        System.out.println(hashedPassword);
    }
}
