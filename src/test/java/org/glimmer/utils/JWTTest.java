package org.glimmer.utils;

import org.glimmer.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class JWTTest {
    @Test
    public void jwttest() {
        String s = "anti";
        String jwt = JwtUtil.createJWT(s);
        System.out.println(s);
        System.out.println(jwt);
        try {
            System.out.println(JwtUtil.parseJWT(jwt));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void getEncodedPasswd() {
        System.out.println(passwordEncoder.encode("1234"));
    }

    @Test
    void matchEncodedPasswd(){
        String encode = passwordEncoder.encode("1234");
        String encode2 = passwordEncoder.encode("1234");
        System.out.println(passwordEncoder.matches("1234",encode));;
        System.out.println(passwordEncoder.matches(encode,encode2));
    }
}
