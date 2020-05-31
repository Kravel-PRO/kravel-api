package com.kravel.server.common.util.message;


import com.kravel.server.common.util.exception.AbstractBaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
public class ResponseMessage {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_KEY = "result";
    private boolean status;
    private String message;
    private String timestamp;
    private Map<String, Object> data;
    private ErrorMessage error;

    public ResponseMessage() {
        this(HttpStatus.OK);
    }

    public ResponseMessage(HttpStatus httpStatus) {
        this.data = new HashMap<>();
        this.status = (httpStatus.isError())? false:true;
        this.message = httpStatus.getReasonPhrase();
        this.timestamp = convertDateFormat(new Date());
    }

    public ResponseMessage(AbstractBaseException ex, String referedUrl) {
        HttpStatus httpStatus = ex.getHttpStatus();

        this.data = new HashMap<>();
        this.status = (httpStatus.isError())? false:true;
        this.message = httpStatus.getReasonPhrase();
        this.error = new ErrorMessage(httpStatus.value(), ex.getMessage(), referedUrl);
        this.timestamp = convertDateFormat(new Date());
    }

    public ResponseMessage(HttpStatus status, Object result) {
        this(status);
        this.data.put(DEFAULT_KEY, result);
    }

    public void add(String key, Object result) {
        this.data.put(key, result);
    }

    public void remove(String key) {
        this.data.remove(key);
    }

    private String convertDateFormat(Date date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}

