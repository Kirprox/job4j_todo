package ru.job4j.todo.service;

import ru.job4j.todo.model.Category;

import java.util.Collection;
import java.util.List;

public interface CategoryService {
    Category save(Category category);

    void update(Category category);

    Category getCategoryById(int id);

    List<Category> getCategoriesById(List<Integer> categoryList);

    Collection<Category> getCategories();

    void deleteById(int id);
}
