package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.exception.TaskNotFoundException;
import ru.job4j.todo.model.Task;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class HbmTaskRepository implements TaskRepository {
    private final CrudRepository crudRepository;

    @Override
    public Task save(Task task) {
        crudRepository.run(session -> session.save(task));
        return task;
    }

    @Override
    public void update(Task task) {
        crudRepository.run(session -> session.update(task));
    }

    @Override
    public Task findById(int id) {
        return crudRepository.optional(
                "FROM Task f JOIN FETCH f.priority WHERE f.id = :fId", Task.class,
                Map.of("fId", id)
        ).orElseThrow(() -> new TaskNotFoundException(
                String.format("Задача с id %d не найдена", id)
        ));
    }

    @Override
    public List<Task> findAllDone() {
        return crudRepository.query(
                "FROM Task f JOIN FETCH f.priority WHERE done = true", Task.class
        );
    }

    @Override
    public List<Task> findAllNew() {
        return crudRepository.query(
                "FROM Task f JOIN FETCH f.priority WHERE done = false", Task.class
        );
    }

    @Override
    public List<Task> findAll() {
        return crudRepository.query(
                "FROM Task f JOIN FETCH f.priority", Task.class
        );
    }

    @Override
    public void deleteById(int id) {
        crudRepository.run(
                "DELETE FROM Task WHERE id = :fId",
                Map.of("fId", id)
        );
    }
}
