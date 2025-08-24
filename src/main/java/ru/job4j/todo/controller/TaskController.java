package ru.job4j.todo.controller;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final CategoryService categoryService;
    private final PriorityService priorityService;

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
    public String getCreationPage(Model model) {
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("priorities", priorityService.getPriorities());
        return "tasks/create";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task, Model model, HttpSession session,
                             @RequestParam(required = false) List<Integer> categoryIds) {
        User user = (User) session.getAttribute("user");
        task.setUser(user);
        if (categoryIds != null) {
            List<Category> categories = categoryService.getCategoriesById(categoryIds);
            task.setCategories(categories);
        }
        taskService.save(task);
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String editTask(Model model, @PathVariable int id) {
        Task task = taskService.getTaskById(id);
        model.addAttribute("task", task);
        model.addAttribute("categories", categoryService.getCategories());
        return "tasks/edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Task task,
                         @RequestParam(required = false, name = "categoryIds")
                         List<Integer> categoryIds, Model model) {
        var resultTask = taskService.getTaskById(task.getId());

        resultTask.setDescription(task.getDescription());
        resultTask.setDone(task.isDone());
        resultTask.getCategories().clear();
        if (categoryIds != null) {
            for (Integer categoryId : categoryIds) {
                Category category = categoryService.getCategoryById(categoryId);
                    resultTask.getCategories().add(category);
            }
        }
        taskService.update(resultTask);
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        taskService.deleteById(id);
        return "redirect:/tasks";
    }
}
