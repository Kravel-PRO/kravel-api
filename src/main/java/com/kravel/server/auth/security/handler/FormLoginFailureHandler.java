package com.kravel.server.auth.security.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FormLoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res, AuthenticationException exception) throws IOException, ServletException {
        switch (exception.getMessage()) {
            case "isNotCorrectPassword":
                res.setContentType(MediaType.APPLICATION_JSON_VALUE);
                res.sendError(401, "is not correct password!");
                break;
            case "isNotExistMember":
                res.setContentType(MediaType.APPLICATION_JSON_VALUE);
                res.sendError(402, "is exist Member!");
                break;
            default:
                res.sendError(500);
        }
    }
}
