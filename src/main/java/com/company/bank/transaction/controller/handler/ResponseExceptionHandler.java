package com.company.bank.transaction.controller.handler;

import com.company.bank.transaction.exception.IllegalRequestParamException;
import com.company.bank.transaction.exception.entity.ErrorType;
import com.company.bank.transaction.exception.entity.ErrorVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ResponseExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ResponseExceptionHandler.class);

    @ExceptionHandler(IllegalRequestParamException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorVO handleIllegalRequestParamException(IllegalRequestParamException ex) {
        logger.error("IllegalRequestParamException occurred", ex);
        return getErrorVO(ex.getField(), ex.getMessage(), ex.getErrorType());
    }

    private ErrorVO getErrorVO(String field, String message, ErrorType errorType) {
        ErrorVO errorResponse = new ErrorVO();
        errorResponse.setField(field);
        errorResponse.setMessage(message);
        errorResponse.setErrorType(errorType);
        return errorResponse;
    }

}
