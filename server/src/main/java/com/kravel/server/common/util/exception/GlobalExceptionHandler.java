package com.kravel.server.common.util.exception;

import com.kravel.server.common.util.message.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseMessage inValidExceptionHandler(HttpServletRequest req, Exception e) {
        log.error(req.getRequestURL().toString());
        return new ResponseMessage(new InvalidRequestException(e.getMessage(), e), req.getRequestURL().toString());
    }
}
