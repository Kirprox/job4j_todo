package ru.job4j.todo.repository;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task save(Task task);

    void update(Task task);

    Optional<Task> findById(int id);

    List<Task> findAll();

    List<Task> findAllDone();

    List<Task> findAllNew();

    void deleteById(int id);

}
