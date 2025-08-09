package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.exception.UserActionException;
import ru.job4j.todo.exception.UserLoginException;
import ru.job4j.todo.model.User;

import java.util.Collection;
import java.util.List;

@Repository
@AllArgsConstructor
public class HbmUserRepository implements UserRepository {
    private final SessionFactory sf;

    @Override
    public User save(User user) {
        if (existsByLogin(user.getLogin())) {
            throw new UserActionException("Пользователь с такой почтой уже существует");
        }
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new UserActionException("Ошибка при сохранении пользователя", e);
        }
        return user;
    }

    @Override
    public boolean existsByLogin(String login) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            Long count = session.createQuery(
                            "SELECT COUNT(u) FROM User u WHERE u.login = :login", Long.class)
                    .setParameter("login", login).uniqueResult();
            session.getTransaction().commit();
            return count != null && count > 0;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new UserActionException("Ошибка при проверке существования пользователя", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteById(int id) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE FROM User WHERE id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new UserActionException(
                    String.format("Ошибка при удалении пользователя c id: %d", id), e);
        }
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        Session session = sf.openSession();
        User user = null;
        try {
            session.beginTransaction();
            user = session.createQuery(
                            "FROM User WHERE login = :fLogin AND password = :fPassword", User.class)
                    .setParameter("fLogin", login)
                    .setParameter("fPassword", password)
                    .uniqueResult();
            session.getTransaction().commit();
            if (user == null) {
                throw new UserLoginException("Неверный логин или пароль");
            }
        } catch (UserLoginException e) {
            throw e;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new UserActionException("Ошибка при попытке входаdd", e);
        }
        return user;
    }

    @Override
    public Collection<User> findAll() {
        Session session = sf.openSession();
        List<User> result;
        try {
            session.beginTransaction();
            result = session.createQuery("FROM User", User.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new UserActionException("Ошибка при получении списка пользователей", e);
        }
        return result;
    }
}
