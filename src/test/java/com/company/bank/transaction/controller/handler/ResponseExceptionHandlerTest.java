package com.company.bank.transaction.controller.handler;

import ch.qos.logback.classic.Level;
import com.company.bank.transaction.exception.IllegalRequestParamException;
import com.company.bank.transaction.exception.entity.ErrorVO;
import com.company.bank.transaction.util.LogAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.company.bank.transaction.exception.entity.ErrorType.ERROR;
import static com.company.bank.transaction.util.LogAppender.startLogAppenderForClass;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ResponseExceptionHandlerTest {

    @InjectMocks
    private ResponseExceptionHandler underTest;

    private final LogAppender logAppender = startLogAppenderForClass(ResponseExceptionHandler.class);

    @BeforeEach
    public void setup() {
        logAppender.reset();
    }

    @Test
    void shouldHandleIllegalRequestParamException() {
        IllegalRequestParamException exception = new IllegalRequestParamException("fieldName", "Error message", ERROR);
        ErrorVO errorVO = underTest.handleIllegalRequestParamException(exception);

        assertEquals("fieldName", errorVO.getField());
        assertEquals("Error message", errorVO.getMessage());
        assertEquals(ERROR, errorVO.getErrorType());

        assertTrue(logAppender.contains("IllegalRequestParamException occurred", Level.ERROR));
    }
}
