package com.demo.userservice.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;

import static com.demo.userservice.utils.ConstantUtil.*;

public class TestUtils {

    public static String userToken = getUserToker("admin");
    private static ObjectMapper mapper = new ObjectMapper();


    private static String getUserToker(String username) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_ALGORITHM.getBytes());

        String token = BEARER_MORE_SPACE + JWT.create()
                .withSubject(username)
                .withExpiresAt(TIME_SESSION_10_MIN)
                .withClaim(ROLES, Arrays.asList("ROLE_ADMIN"))
                .sign(algorithm);

        return token;
    }

    public static String objectToJson(Object value) throws Exception {
        return mapper.writeValueAsString(value);
    }


}
