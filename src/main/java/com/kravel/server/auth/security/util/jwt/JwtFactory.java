package com.kravel.server.auth.security.util.jwt;

import com.kravel.server.auth.model.MemberContext;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
            Date expiredDate = Date.from(LocalDateTime.now().plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant());

            payloads.put("exp", expiredDate.getTime() / 1000);
            payloads.put("login_email", context.getMember().getLoginEmail());
            payloads.put("role_type", context.getMember().getRoleType().getRoleName());
            payloads.put("speech", context.getMember().getSpeech().name());
            payloads.put("gender", context.getMember().getGender());
            payloads.put("member_id", context.getMember().getId());
            payloads.put("nick_name", context.getMember().getNickName());

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
