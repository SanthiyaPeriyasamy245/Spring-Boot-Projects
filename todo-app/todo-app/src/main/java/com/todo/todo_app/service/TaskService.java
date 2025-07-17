package com.todo.todo_app.service;

import com.todo.todo_app.model.Task;
import com.todo.todo_app.repository.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TaskService {

    private final Repository repository;

    public TaskService(Repository repository) {
        this.repository = repository;
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public void createTask(String title) {
        Task task = new Task();
        task.setTitle(title);
       ;
        repository.save(task);
    }
    public void deleteTaskByUid(long id) {
    repository.deleteById(id);
    }

    public void toggleTaskByid(long id) {
        Task task = repository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setDone(!task.isDone());
        repository.save(task);
    }
}
