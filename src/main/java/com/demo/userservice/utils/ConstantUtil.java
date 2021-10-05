package com.demo.userservice.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class ConstantUtil {

    public static final String SECRET_ALGORITHM = "secret";
    public static final String BEARER_MORE_SPACE = "Bearer ";
    public static final String ROLES = "roles";

    public static final Date TIME_SESSION_10_MIN = new Date(System.currentTimeMillis() + 10 * 60 * 1000);
    public static final Date TIME_SESSION_30_MIN = new Date(System.currentTimeMillis() + 30 * 60 * 1000);

    public static DecodedJWT decodedJWT(String authorizationHeader) {
        String token = getToken(authorizationHeader);
        JWTVerifier verifier = JWT.require(getAlgorithm()).build();
        return verifier.verify(token);
    }

    public static Algorithm getAlgorithm() {
        return Algorithm.HMAC256(SECRET_ALGORITHM.getBytes());
    }

    public static String getToken(String authorizationHeader) {
        return authorizationHeader.substring(BEARER_MORE_SPACE.length());
    }
}
