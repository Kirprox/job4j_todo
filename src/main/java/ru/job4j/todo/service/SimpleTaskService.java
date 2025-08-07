package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;

import java.util.Collection;

@Service
public class SimpleTaskService implements TaskService {
    private final TaskRepository taskRepository;

    public SimpleTaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void update(Task task) {
        taskRepository.update(task);
    }

    @Override
    public Task saveDone(Task task) {
        task.setDone(true);
        return save(task);
    }

    @Override
    public Task getTaskById(int id) {
        return taskRepository.findById(id);
    }

    @Override
    public Collection<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Collection<Task> getDoneTasks() {
        return taskRepository.findAllDone();
    }

    @Override
    public Collection<Task> getNewTasks() {
        return taskRepository.findAllNew();
    }

    @Override
    public void deleteById(int id) {
        taskRepository.deleteById(id);
    }
}
