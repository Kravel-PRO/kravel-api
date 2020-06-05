package com.kravel.server.common.util.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessage {

    private static final long serialVersionUID = 1L;

    private int code;
    private String errorMessage;
    private String referedUrl;

    public ErrorMessage(int code, String errorMessage, String referedUrl) {
        super();
        this.code = code;
        this.errorMessage = errorMessage;
        this.referedUrl = referedUrl;
    }
}
