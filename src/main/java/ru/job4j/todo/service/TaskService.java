package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {
    Task save(Task task);

    void update(Task task);

    Optional<Task> getTaskById(int id);

    Collection<Task> getAllTasks();

    Collection<Task> getDoneTasks();

    Collection<Task> getNewTasks();

    void deleteById(int id);
}
