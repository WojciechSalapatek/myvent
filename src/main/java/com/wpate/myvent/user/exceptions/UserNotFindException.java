package com.wpate.myvent.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFindException extends RuntimeException{

    public UserNotFindException(String message) {
        super(message);
    }
}
