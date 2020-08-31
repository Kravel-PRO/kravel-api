package com.kravel.server.common.util.message;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorMessage {

    private static final long serialVersionUID = 1L;

    private String errorMessage;
    private String path;

    public ErrorMessage(String errorMessage, String path) {
        super();
        this.errorMessage = errorMessage;
        this.path = path;
    }
}
