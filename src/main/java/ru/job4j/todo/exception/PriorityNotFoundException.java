package ru.job4j.todo.exception;

public class PriorityNotFoundException extends RuntimeException {
    public PriorityNotFoundException(String message) {
        super(message);
    }
}
