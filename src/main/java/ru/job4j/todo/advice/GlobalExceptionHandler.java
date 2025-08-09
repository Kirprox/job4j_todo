package ru.job4j.todo.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.job4j.todo.exception.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({TaskNotFoundException.class,
            TaskSaveException.class, TaskUpdateException.class})
    public String handleTaskNotFoundException(TaskException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "errors/404";
    }

    @ExceptionHandler(UserActionException.class)
    public String handleUserActionException(UserActionException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "errors/404";
    }

    @ExceptionHandler(UserLoginException.class)
    public String handleUserLoginException(UserLoginException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "users/login";
    }

}
