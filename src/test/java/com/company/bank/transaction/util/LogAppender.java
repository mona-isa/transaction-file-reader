package com.company.bank.transaction.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.slf4j.LoggerFactory;

public class LogAppender extends ListAppender<ILoggingEvent> {

    private LogAppender() {
    }

    public static LogAppender startLogAppenderForClass(Class<?> clazz) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        LogAppender memoryAppender = new LogAppender();
        loggerContext.getLogger(clazz).addAppender(memoryAppender);
        memoryAppender.start();
        return memoryAppender;
    }

    public void reset() {
        this.list.clear();
    }

    public boolean contains(String string, Level level) {
        return this.list.stream()
                .anyMatch(event -> event.toString().contains(string)
                        && event.getLevel().equals(level));
    }

}
