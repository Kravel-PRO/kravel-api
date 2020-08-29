package com.kravel.server.auth.security.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kravel.server.auth.model.MemberContext;
import com.kravel.server.auth.security.util.exception.InvalidJwtException;
import com.kravel.server.enums.RoleType;
import com.kravel.server.model.member.Member;
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

    public MemberContext decodedJwt(String token) {
        DecodedJWT decodedJWT = isValideToken(token).orElseThrow(() -> new InvalidJwtException("🔥 error: is not invalid token"));

        String loginEmail = decodedJWT.getClaim("login_email").asString();
        String role = decodedJWT.getClaim("role_type").asString();
        String speech = decodedJWT.getClaim("speech").asString();
        String gender = decodedJWT.getClaim("gender").asString();
        long memberId = decodedJWT.getClaim("member_id").asLong();

        Member member = Member.builder()
                .id(memberId)
                .loginEmail(loginEmail)
                .loginPw("UNUSED_CREDENTIALS")
                .speech(speech)
                .gender(gender)
                .roleType(RoleType.getRoleByName(role))
                .build();

        return MemberContext.fromMemberModel(member);
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
