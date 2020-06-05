package com.kravel.server.common.util.exception;

import org.springframework.http.HttpStatus;

public abstract class AbstractBaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AbstractBaseException() {
        super();
    }

    public AbstractBaseException(String msg) {
        super(msg);
    }

    public AbstractBaseException(Throwable e) {
        super(e);
    }

    public AbstractBaseException(String errorMessge, Throwable e) {
        super(errorMessge, e);
    }

    public abstract HttpStatus getHttpStatus();

}