package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
@Slf4j
public class HbmTaskRepository implements TaskRepository {
    private final SessionFactory sf;

    @Override
    public Task save(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error("Ошибка при сохранении задачи: {}", task, e);
        } finally {
            session.close();
        }
        return task;
    }

    @Override
    public void update(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("UPDATE Task WHERE id = fId", Task.class)
                    .setParameter("fId", task.getId()).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error("Ошибка при обновлении задачи по id: {}", task.getId(), e);
        } finally {
            session.close();
        }
    }

    @Override
    public Task findById(int id) {
        Session session = sf.openSession();
        Task task = null;
        try {
            session.beginTransaction();
            task = session.createQuery("FROM Task WHERE id = :fId", Task.class)
                    .setParameter("fId", id).uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error("Ошибка при получении задачи по id: {}", id, e);
        } finally {
            session.close();
        }
        return task;
    }

    @Override
    public List<Task> findAllDone() {
        Session session = sf.openSession();
        List<Task> result = new ArrayList<>();
        try {
            session.beginTransaction();
            result = session.createQuery("FROM Task WHERE done = true", Task.class).list();
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error("Ошибка при получении завершенных задач", e);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<Task> findAllNew() {
        Session session = sf.openSession();
        List<Task> result = new ArrayList<>();
        try {
            session.beginTransaction();
            result = session.createQuery("FROM Task WHERE done = false", Task.class).list();
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error("Ошибка при получении новых задач", e);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<Task> findAll() {
        Session session = sf.openSession();
        List<Task> result = new ArrayList<>();
        try {
            session.beginTransaction();
            result = session.createQuery("FROM Task", Task.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error("Ошибка при получении списка задач");
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public void deleteById(int id) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE FROM Task WHERE id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error("Ошибка при удалении задачи под id: {}", id, e);
        } finally {
            session.close();
        }
    }
}
