package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.Collection;

public interface TaskService {
    Task save(Task task);

    void update(Task task);

    Task saveDone(Task task);

    Task getTaskById(int id);

    Collection<Task> getAllTasks();

    Collection<Task> getDoneTasks();

    Collection<Task> getNewTasks();

    void deleteById(int id);
}
