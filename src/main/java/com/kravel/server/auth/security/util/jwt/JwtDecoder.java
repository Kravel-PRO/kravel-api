package com.kravel.server.auth.security.util.jwt;

import com.kravel.server.auth.model.MemberContext;
import com.kravel.server.auth.security.util.exception.InvalidJwtException;
import com.kravel.server.enums.RoleType;
import com.kravel.server.model.member.Member;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtDecoder {

    private static final Logger log = LoggerFactory.getLogger(JwtDecoder.class);

    @Value("${keys.jwt.secret-key}")
    private String secretKey;

    public MemberContext decodedJwt(String token) {
        Jws<Claims> decodedJWT = isValideToken(token);

        String loginEmail = decodedJWT.getBody().get("login_email", String.class);

        String role = decodedJWT.getBody().get("role_type", String.class);
        String speech = decodedJWT.getBody().get("speech", String.class);
        String gender = decodedJWT.getBody().get("gender", String.class);
        long memberId = decodedJWT.getBody().get("member_id", Long.class);

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

    private Jws<Claims> isValideToken(String token) {

        try {
            Jws<Claims> decodedJWT = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token);
            return decodedJWT;

        } catch(MissingClaimException mce) {
            throw new InvalidJwtException("🔥 error: missing claim exception ->" + mce.getMessage());

        } catch(IncorrectClaimException ice) {
            throw new InvalidJwtException("🔥 error: incorrect claim exception ->" + ice.getMessage());
        }
    }
}
