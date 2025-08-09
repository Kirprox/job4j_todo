package ru.job4j.todo.repository;

import ru.job4j.todo.model.User;

import java.util.Collection;

public interface UserRepository {
    User save(User user);

    boolean existsByLogin(String login);

    void deleteById(int id);

    User findByLoginAndPassword(String login, String password);

    Collection<User> findAll();
}
