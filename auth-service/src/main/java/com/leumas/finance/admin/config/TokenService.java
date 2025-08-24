package com.leumas.finance.admin.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.leumas.finance.admin.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class TokenService {
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("id", user.getId())
                .withClaim("name", user.getName())
                .withClaim("role", user.getRole().name())
                .withExpiresAt(Instant.now().plusSeconds(3600 * 24))
                .withIssuedAt(Instant.now())
                .withIssuer("Finance Manager API")
                .sign(algorithm);
    }

    public Optional<JWTUserData> verifyToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT jwt = JWT.require(algorithm).build().verify(token);

            return Optional.of(JWTUserData.builder()
                    .id(jwt.getClaim("id").asLong())
                    .email(jwt.getSubject())
                    .role(jwt.getClaim("role").asString())
                    .build());
        }catch (JWTVerificationException e){
            return Optional.empty();
        }
    }
}
