package com.kravel.server.common.util.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends AbstractBaseException {

    private static final long serialVersionUID = 1L;

    public NotFoundException() {
        super();
    }

    public NotFoundException(Throwable e) {
        super(e);
    }

    public NotFoundException(String errorMessge) {
        super(errorMessge);
    }

    public NotFoundException(String errorMessge, Throwable e) {
        super(errorMessge, e);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
