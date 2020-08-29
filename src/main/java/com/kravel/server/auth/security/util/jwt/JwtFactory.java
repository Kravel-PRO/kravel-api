package com.kravel.server.auth.security.util.jwt;

import com.kravel.server.auth.model.MemberContext;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtFactory {

    @Value("${keys.jwt.secret-key}")
    private String secretKey;

    private static final Logger log = LoggerFactory.getLogger(JwtFactory.class);

    public String generateToken(MemberContext context) {

        String token = null;

        try {
            Map<String, Object> headers = new HashMap<>();
            headers.put("typ", "JWT");
            headers.put("alg", "HS256");

            Map<String, Object> payloads = new HashMap<>();
            Long expiredTime = 1000 * 6000l; // 만료기간 10시간
            Date now = new Date();

            now.setTime(now.getTime() + expiredTime);
            payloads.put("exp", now);
            payloads.put("login_email", context.getMember().getLoginEmail());
            payloads.put("role_type", context.getMember().getRoleType().getRoleName());
            payloads.put("speech", context.getMember().getSpeech());
            payloads.put("gender", context.getMember().getGender());
            payloads.put("member_id", context.getMember().getId());

            token = Jwts.builder()
                    .setHeader(headers)
                    .setClaims(payloads)
                    .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                    .compact();

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return token;
    }
}
