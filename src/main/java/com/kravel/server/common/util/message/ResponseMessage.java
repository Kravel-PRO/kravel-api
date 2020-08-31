package com.kravel.server.common.util.message;


import com.kravel.server.common.util.exception.AbstractBaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
public class ResponseMessage {

    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_KEY = "result";
    private String message = "";
    private String timestamp = "";
    private Map<String, Object> data = new HashMap<>();
    private ErrorMessage error;

    public ResponseMessage(AbstractBaseException ex, String referedUrl) {
        this.data = new HashMap<>();
        this.message = ex.getHttpStatus().getReasonPhrase();
        this.error = new ErrorMessage(ex.getMessage(), referedUrl);
        this.timestamp = convertDateFormat(LocalDateTime.now());
    }

    public ResponseMessage(String message) {
        this.message = message;
        this.timestamp = convertDateFormat(LocalDateTime.now());
    }

    public ResponseMessage(Object result, String message) {
        this.data.put(DEFAULT_KEY, result);
        this.message = message;
        this.timestamp = convertDateFormat(LocalDateTime.now());
    }

    public ResponseMessage(Object result) {
        this.data.put(DEFAULT_KEY, result);
        this.message = "Pengsoo is future";
        this.timestamp = convertDateFormat(LocalDateTime.now());
    }

    public void add(String key, Object result) {
        this.data.put(key, result);
    }

    public void remove(String key) {
        this.data.remove(key);
    }

    private String convertDateFormat(LocalDateTime now) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
}

