package com.kravel.server.common.util.exception;

import org.springframework.http.HttpStatus;

public class InternalServerException extends AbstractBaseException {

    private static final long serialVersionUID = 1L;

    public InternalServerException() {
        super();
    }

    public InternalServerException(Throwable e) {
        super(e);
    }

    public InternalServerException(String errorMessge) {
        super(errorMessge);
    }

    public InternalServerException(String errorMessge, Throwable e) {
        super(errorMessge, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
