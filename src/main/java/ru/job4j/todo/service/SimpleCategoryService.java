package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryRepository;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class SimpleCategoryService implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void update(Category category) {
        categoryRepository.update(category);
    }

    @Override
    public Category getCategoryById(int id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> getCategoriesById(List<Integer> ids) {
        return categoryRepository.findByIds(ids);
    }

    @Override
    public Collection<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void deleteById(int id) {
        categoryRepository.deleteById(id);
    }
}
