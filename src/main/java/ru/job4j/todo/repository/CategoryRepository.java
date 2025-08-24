package ru.job4j.todo.repository;

import ru.job4j.todo.model.Category;
import java.util.List;

public interface CategoryRepository {
    Category save(Category category);

    void update(Category category);

    Category findById(int id);

    List<Category> findByIds(List<Integer> categories);

    List<Category> findAll();

    void deleteById(int id);
}
