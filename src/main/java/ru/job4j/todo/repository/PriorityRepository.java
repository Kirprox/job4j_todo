package ru.job4j.todo.repository;

import ru.job4j.todo.model.Priority;

import java.util.List;

public interface PriorityRepository {
    Priority save(Priority priority);

    void update(Priority priority);

    Priority findById(int id);

    List<Priority> findAll();

    void deleteById(int id);
}
