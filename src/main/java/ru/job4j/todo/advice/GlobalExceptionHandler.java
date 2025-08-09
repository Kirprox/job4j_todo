package ru.job4j.todo.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.job4j.todo.exception.TaskException;
import ru.job4j.todo.exception.TaskNotFoundException;
import ru.job4j.todo.exception.TaskSaveException;
import ru.job4j.todo.exception.TaskUpdateException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({TaskNotFoundException.class,
            TaskSaveException.class, TaskUpdateException.class})
    public String handleTaskNotFoundException(TaskException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "errors/404";
    }
}
