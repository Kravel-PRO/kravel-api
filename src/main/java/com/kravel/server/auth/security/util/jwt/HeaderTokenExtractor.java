package com.kravel.server.auth.security.util.jwt;

import com.kravel.server.auth.security.util.exception.InvalidJwtException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class HeaderTokenExtractor {

    public static final String HEADER_PREFIX = "Bearer ";

    public String extract(String header) {

        if (StringUtils.isEmpty(header) | header.length() < HEADER_PREFIX.length()) {
            throw new InvalidJwtException("🔥 error: is not valid JWT");
        }

        return header.substring(HEADER_PREFIX.length(), header.length());
    }
}
