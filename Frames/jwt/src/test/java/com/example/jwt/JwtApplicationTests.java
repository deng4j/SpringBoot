package com.example.jwt;

import com.example.jwt.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class JwtApplicationTests {

    @Test
    void testGetJwt() {
        Map<String, Object> map=new HashMap<>();
        map.put("id",1);
        map.put("username","张三");

        String token = JwtUtils.getToken(map);
        System.out.println(token);
    }

    @Test
    void testGetInfo(){
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE2ODUxMTg2NTUsIm5hbWUiOiLlvKDkuIkiLCJpZCI6MX0.Ukqx3UcpItVgmwnOs1np9SZMb2elOv7MgldCaTR3zE1UlAgLMKZE5v-BjtIa01sx16T0F51nNezgmJjDCI49mg";
        if (JwtUtils.verifyToken(token)){
            Claims claims = JwtUtils.getClaims(token);
            System.out.println(claims);
        }
    }

}
