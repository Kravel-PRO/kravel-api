package com.kravel.server.auth.security.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kravel.server.auth.model.MemberContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class JwtFactory {

    @Value("${keys.jwt.secret-key}")
    private String secretKey;

    private static final Logger log = LoggerFactory.getLogger(JwtFactory.class);

    public String generateToken(MemberContext context) {

        String token = null;

        try {
            token = JWT.create()
                    .withIssuer("ooeunz")
                    .withClaim("login_email", context.getMember().getLoginEmail())
                    .withClaim("role_type", context.getMember().getRoleType().getRoleName())
                    .withClaim("speech", context.getMember().getSpeech())
                    .withClaim("gender", context.getMember().getGender())
                    .withClaim("member_id", context.getMember().getMemberId())
                    .sign(generateAlgorithm());

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return token;
    }

    private Algorithm generateAlgorithm() throws UnsupportedEncodingException {
        return Algorithm.HMAC256(secretKey);
    }
}
