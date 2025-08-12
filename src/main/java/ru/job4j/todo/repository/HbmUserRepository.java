package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.exception.UserActionException;
import ru.job4j.todo.exception.UserLoginException;
import ru.job4j.todo.model.User;

import java.util.Collection;
import java.util.Map;

@Repository
@AllArgsConstructor
public class HbmUserRepository implements UserRepository {
    private final CrudRepository crudRepository;

    @Override
    public User save(User user) {
        if (existsByLogin(user.getLogin())) {
            throw new UserActionException("Пользователь с таким именем уже существует");
        }
        crudRepository.run(session -> session.save(user));
        return user;
    }

    @Override
    public boolean existsByLogin(String login) {
        Long count = crudRepository
                .optional(
                        "SELECT COUNT(u) FROM User u WHERE u.login = :login",
                        Long.class,
                        Map.of("login", login))
                .orElse(0L);
        return count > 0;
    }

    @Override
    public void deleteById(int id) {
        crudRepository.run(
                "DELETE FROM User WHERE id = :fId",
                Map.of("fId", id)
        );
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        User user = crudRepository.optional(
                "FROM User WHERE login = :fLogin AND password = :fPassword",
                User.class,
                Map.of("fLogin", login, "fPassword", password)
        ).orElseThrow(() -> new UserLoginException("Неверный логин или пароль"));
        return user;
    }

    @Override
    public Collection<User> findAll() {
        return crudRepository.query(
                "FROM User",
                User.class
        );
    }
}
