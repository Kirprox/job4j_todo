package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.exception.CategoryNotFoundException;
import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class HbmCategoryRepository implements CategoryRepository {
    private final CrudRepository crudRepository;

    @Override
    public Category save(Category category) {
        crudRepository.run(session -> session.save(category));
        return category;
    }

    @Override
    public void update(Category category) {
        crudRepository.run(session -> session.update(category));
    }

    @Override
    public Category findById(int id) {
        return crudRepository.optional(
                "FROM Category WHERE id = :fId", Category.class,
                Map.of("fId", id)
        ).orElseThrow(() -> new CategoryNotFoundException(
                String.format("Категория с id %d не найдена", id)
        ));
    }

    @Override
    public List<Category> findByIds(List<Integer> ids) {
        return crudRepository.query(
                "FROM Category WHERE id IN (:fIds)", Category.class,
                Map.of("fIds", ids)
        );
    }

    @Override
    public List<Category> findAll() {
        return crudRepository.query(
                "FROM Category", Category.class
        );
    }

    @Override
    public void deleteById(int id) {
        crudRepository.run(
                "DELETE FROM Category WHERE id = :fId",
                Map.of("fId", id)
        );
    }
}
