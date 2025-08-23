package ru.job4j.todo.service;

import ru.job4j.todo.model.Priority;

import java.util.Collection;

public interface PriorityService {
    Priority save(Priority priority);

    void update(Priority priority);

    Priority getPriorityById(int id);

    Collection<Priority> getPriorities();

    void deleteById(int id);
}
