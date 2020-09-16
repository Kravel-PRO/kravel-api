package com.kravel.server.auth.security.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kravel.server.auth.dto.TokenDTO;
import com.kravel.server.auth.model.MemberContext;
import com.kravel.server.auth.security.util.jwt.JwtFactory;
import com.kravel.server.auth.security.token.PostAuthorizationToken;
import com.kravel.server.common.util.message.Message;
import com.kravel.server.model.member.RememberMe;
import com.kravel.server.model.member.RememberMeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
@RequiredArgsConstructor
public class FormLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtFactory factory;
    private final RememberMeRepository rememberMeRepository;

    @Autowired @Lazy
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException, ServletException {

        MemberContext context = ((PostAuthorizationToken) auth).getMemberContext();

        String token = factory.generateToken(context);
        RememberMe rememberMe = RememberMe.builder()
                .member(context.getMember())
                .token(token)
                .build();
        rememberMeRepository.save(rememberMe);

        processResponse(res, token);
    }

    private TokenDTO writeDTO(String token) {
        return new TokenDTO(token);
    }

    private void processResponse(HttpServletResponse res, String token) throws JsonProcessingException, IOException {

        Message responseMessage = new Message("login is succeed");

        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setStatus(HttpStatus.OK.value());
        res.addHeader("Authorization", "Bearer " + token);
        res.getWriter().write(objectMapper.writeValueAsString(responseMessage));
    }
}
