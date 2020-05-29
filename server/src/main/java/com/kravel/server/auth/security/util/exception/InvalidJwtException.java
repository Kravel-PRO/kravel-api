package com.kravel.server.auth.security.util.exception;

public class InvalidJwtException extends RuntimeException {

    public InvalidJwtException(String msg) {
        super(msg);
    }
}
