package com.company.bank.transaction.exception.entity;

import lombok.Data;

@Data
public class ErrorVO {
    private String field;
    private String message;
    private ErrorType errorType;
}
