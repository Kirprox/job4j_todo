package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.exception.PriorityNotFoundException;
import ru.job4j.todo.model.Priority;

import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class HbmPriorityRepotitory implements PriorityRepository {
    private final CrudRepository crudRepository;

    @Override
    public Priority save(Priority priority) {
        crudRepository.run(session -> session.save(priority));
        return priority;
    }

    @Override
    public void update(Priority priority) {
        crudRepository.run(session -> session.update(priority));

    }

    @Override
    public Priority findById(int id) {
        return crudRepository.optional(
                "FROM Priority WHERE id = :fId", Priority.class,
                Map.of("fId", id)
        ).orElseThrow(() -> new PriorityNotFoundException(
                String.format("Приоритет с id %d не найден", id)
        ));
    }

    @Override
    public List<Priority> findAll() {
        return crudRepository.query(
                "FROM Priority", Priority.class
        );
    }

    @Override
    public void deleteById(int id) {
        crudRepository.run(
                "DELETE FROM Priority WHERE id = :fId",
                Map.of("fId", id)
        );
    }
}
