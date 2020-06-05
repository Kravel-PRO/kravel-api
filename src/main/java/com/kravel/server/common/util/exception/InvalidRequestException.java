package com.kravel.server.common.util.exception;

import org.springframework.http.HttpStatus;

public  class InvalidRequestException extends AbstractBaseException {

    private static final long serialVersionUID = 1L;

    public InvalidRequestException() {
        super();
    }

    public InvalidRequestException(Throwable e) {
        super(e);
    }

    public InvalidRequestException(String errorMessge) {
        super(errorMessge);
    }

    public InvalidRequestException(String errorMessge, Throwable e) {
        super(errorMessge, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}


