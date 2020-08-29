package com.kravel.server.common.util.exception;

import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends AbstractBaseException {

    private static final long serialVersionUID = 1L;

    public UnAuthorizedException() {
        super();
    }

    public UnAuthorizedException(Throwable e) {
        super(e);
    }

    public UnAuthorizedException(String errorMessge) {
        super(errorMessge);
    }

    public UnAuthorizedException(String errorMessge, Throwable e) {
        super(errorMessge, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
