package ru.job4j.todo.exception;

public class UserActionException extends UserException {
    public UserActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserActionException(String message) {
        super(message);
    }
}
