package com.beyou.admin.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
    @Test
    public void testEncodePassword(){
        //using Spring Security, we can create a new instance of the BCryptPasswordEncoder directly..
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String rawPassword="nam2020";
        String encodedPassword=passwordEncoder.encode(rawPassword);

        System.out.println(encodedPassword);
        // we can verify the raw password against the encoded password using the matches() method of the password
        boolean matches=passwordEncoder.matches(rawPassword, encodedPassword);

    }
}
