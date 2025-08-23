package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.PriorityRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class SimplePriorityService implements PriorityService {
    private final PriorityRepository priorityRepository;

    @Override
    public Priority save(Priority priority) {
        return priorityRepository.save(priority);
    }

    @Override
    public void update(Priority priority) {
        priorityRepository.update(priority);
    }

    @Override
    public Priority getPriorityById(int id) {
        return priorityRepository.findById(id);
    }

    @Override
    public Collection<Priority> getPriorities() {
        return priorityRepository.findAll();
    }

    @Override
    public void deleteById(int id) {
        priorityRepository.deleteById(id);
    }
}
