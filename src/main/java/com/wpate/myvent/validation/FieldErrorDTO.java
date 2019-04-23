package com.wpate.myvent.validation;

import lombok.Getter;

public class FieldErrorDTO {

    @Getter
    private String field;

    @Getter
    private String message;

    public FieldErrorDTO(String field, String message) {
        this.field = field;
        this.message = message;
    }

}
