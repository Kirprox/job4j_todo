package ru.job4j.todo.exception;

public class UserLoginException extends UserException {
    public UserLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserLoginException(String message) {
        super(message);
    }

}
