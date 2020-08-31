package com.kravel.server.common.util.exception;

import com.kravel.server.common.util.message.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Message inValidExceptionHandler(HttpServletRequest req, Exception e) {
        log.error(req.getRequestURL().toString());
        return new Message(new InvalidRequestException(e.getMessage(), e), req.getRequestURL().toString());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public Message notFoundExceptionHandler(HttpServletRequest req, Exception e) {
        log.error(req.getRequestURL().toString());
        return new Message(new InvalidRequestException(e.getMessage(), e), req.getRequestURL().toString());
    }
}
