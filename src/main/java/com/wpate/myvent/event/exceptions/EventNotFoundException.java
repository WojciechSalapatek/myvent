package com.wpate.myvent.event.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(String message) {
        super(message);
    }
}
