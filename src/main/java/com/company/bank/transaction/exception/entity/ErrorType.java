package com.company.bank.transaction.exception.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorType {
    ERROR("ERROR"), WARNING("WARNING");

    private final String name;
}
