package com.kravel.server.auth.security.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kravel.server.auth.model.AccountContext;
import com.kravel.server.auth.security.util.exception.InvalidJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JwtDecoder {

    private static final Logger log = LoggerFactory.getLogger(JwtDecoder.class);

    @Value("${keys.jwt.secret-key}")
    private String secretKey;

    public AccountContext decodedJwt(String token) {
        DecodedJWT decodedJWT = isValideToken(token).orElseThrow(() -> new InvalidJwtException("IS NOT VALID TOKEN"));

        String loginEmail = decodedJWT.getClaim("LOGIN_EMAIL").asString();
        String userRole = decodedJWT.getClaim("USER_ROLE").asString();

        return new AccountContext(loginEmail, "UNUSED_CREDENTIALS", userRole);
    }

    private Optional<DecodedJWT> isValideToken(String token) {

        DecodedJWT jwt = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).build();

            jwt = verifier.verify(token);

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return Optional.ofNullable(jwt);
    }
}