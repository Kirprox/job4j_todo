package ru.job4j.todo.exception;

public class TaskSaveException extends TaskException {
    public TaskSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
