package com.kravel.server.auth.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kravel.server.common.util.exception.InvalidRequestException;
import com.kravel.server.common.util.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FormLoginFailureHandler implements AuthenticationFailureHandler {

    @Autowired @Lazy
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res, AuthenticationException exception) throws IOException, ServletException {

        Message responseMessage = new Message(new InvalidRequestException("🔥 error: is not correct login information. please check your principal or credentials"), req.getRequestURL().toString());

        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.getWriter().write(objectMapper.writeValueAsString(responseMessage));
        res.setStatus(401);
    }
}
