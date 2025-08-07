package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import java.util.Collection;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String getTasks(@RequestParam(name = "filter",
            required = false, defaultValue = "") String filter, Model model) {
        Collection<Task> tasks = switch (filter) {
            case "done" -> taskService.getDoneTasks();
            case "new" -> taskService.getNewTasks();
            default -> taskService.getAllTasks();
        };
        model.addAttribute("tasks", tasks);
        model.addAttribute("activePage", "tasks");
        model.addAttribute("filter", filter);
        return "tasks/list";
    }


//    @GetMapping("/{id}")
//    public String getTaskById(Model model, @PathVariable int id) {
//todo реализовать переход на любую заметку с возможностью редактирования
//    }
}
