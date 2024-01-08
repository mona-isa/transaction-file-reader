package com.company.bank.transaction.exception;

import com.company.bank.transaction.exception.entity.ErrorType;
import lombok.Getter;

@Getter
public class IllegalRequestParamException extends RuntimeException {

    private final String field;
    private final String message;
    private final ErrorType errorType;

    public IllegalRequestParamException(String field, String message, ErrorType errorType) {
        super(message);
        this.field = field;
        this.message = message;
        this.errorType = errorType;
    }
}
