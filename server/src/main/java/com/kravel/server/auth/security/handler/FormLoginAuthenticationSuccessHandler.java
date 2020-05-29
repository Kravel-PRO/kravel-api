package com.kravel.server.auth.security.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kravel.server.auth.dto.TokenDTO;
import com.kravel.server.auth.model.AccountContext;
import com.kravel.server.auth.security.factory.JwtFactory;
import com.kravel.server.auth.security.token.PostAuthorizationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FormLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtFactory factory;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException, ServletException {

        AccountContext context = ((PostAuthorizationToken) auth).getAccountContext();

        String tokenString = factory.generateToken(context);
        processResponse(res, writeDTO(tokenString));
    }

    private TokenDTO writeDTO(String token) {
        return new TokenDTO(token);
    }

    private void processResponse(HttpServletResponse res, TokenDTO dto) throws JsonProcessingException, IOException {

        System.out.println("token:" + objectMapper.writeValueAsString(dto));

        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setStatus(HttpStatus.OK.value());
        res.getWriter().write(objectMapper.writeValueAsString(dto));
    }
}
