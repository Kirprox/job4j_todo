package ru.job4j.todo.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserException extends RuntimeException {
    public UserException(String message, Throwable cause) {
        super(message, cause);
        log.error(message, cause);
    }

    public UserException(String message) {
        super(message);
        log.error(message);
    }
}
