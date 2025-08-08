package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import java.util.Collection;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public String getTaskById(Model model, @PathVariable int id) {
        Optional<Task> task = taskService.getTaskById(id);
        if (task.isEmpty()) {
            model.addAttribute("message", "Задача с указанным id не найдена");
            return "errors/404";
        }
        model.addAttribute("task", task.get());
        return "tasks/one";
    }

    @GetMapping("/create")
    public String getCreationPage() {
        return "tasks/create";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task, Model model) {
        try {
            taskService.save(task);
            return "redirect:/tasks";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/edit/{id}")
    public String editTask(Model model, @PathVariable int id) {
        Optional<Task> task = null;
        try {
            task = taskService.getTaskById(id);
            model.addAttribute("task", task.get());
            return "tasks/edit";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task, Model model) {
        try {
            taskService.update(task);
            return "redirect:/tasks";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        try {
            taskService.deleteById(id);
            return "redirect:/tasks";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
