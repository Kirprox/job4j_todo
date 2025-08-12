package ru.job4j.todo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
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

    @GetMapping("/{id}")
    public String getTaskById(Model model, @PathVariable int id) {
        Task task = taskService.getTaskById(id);
        model.addAttribute("task", task);
        return "tasks/one";
    }

    @GetMapping("/create")
    public String getCreationPage() {
        return "tasks/create";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        task.setUser(user);
        taskService.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String editTask(Model model, @PathVariable int id) {
        Task task = taskService.getTaskById(id);
        model.addAttribute("task", task);
        return "tasks/edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task, Model model) {
        taskService.update(task);
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        taskService.deleteById(id);
        return "redirect:/tasks";
    }
}
