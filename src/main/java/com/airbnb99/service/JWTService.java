package com.airbnb99.service;


import com.airbnb99.entity.PropertyUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${expiryDuration.key}")
    private int expiryTime;
    private Algorithm algorithm;
    private final static String USER_NAME = "username";

    @PostConstruct
    public void postConstruct() {
//        System.out.println(algorithmKey);
//        System.out.println(issuer);
//        System.out.println(expiryTime);
        algorithm = Algorithm.HMAC256(algorithmKey);

    }

    public String generateToken(PropertyUser propertyUser) {
        return JWT.create()
                .withClaim(USER_NAME, propertyUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiryTime))
                .withIssuer(issuer)
                .sign(algorithm);
    }


    public String getUsername(String token) {
        DecodedJWT verify = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return verify.getClaim(USER_NAME).asString();


    }
}
