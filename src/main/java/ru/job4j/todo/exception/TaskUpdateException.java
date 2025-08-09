package ru.job4j.todo.exception;

public class TaskUpdateException extends TaskException {
    public TaskUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
