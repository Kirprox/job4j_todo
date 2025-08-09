package ru.job4j.todo.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class TaskException extends RuntimeException {
    public TaskException(String message, Throwable cause) {
        super(message, cause);
        log.error(message, cause);
    }
}
