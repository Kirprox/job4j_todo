package ru.job4j.todo.exception;

public class TaskNotFoundException extends TaskException {
    public TaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskNotFoundException(String message) {
        super(message);
    }
}
