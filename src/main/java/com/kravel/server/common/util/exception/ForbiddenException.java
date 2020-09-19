package com.kravel.server.common.util.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends AbstractBaseException {


    private static final long serialVersionUID = 1L;

    public ForbiddenException() {
        super();
    }

    public ForbiddenException(Throwable e) {
        super(e);
    }

    public ForbiddenException(String errorMessge) {
        super(errorMessge);
    }

    public ForbiddenException(String errorMessge, Throwable e) {
        super(errorMessge, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
