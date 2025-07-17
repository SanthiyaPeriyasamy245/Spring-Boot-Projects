package com.todo.todo_app.repository;

import com.todo.todo_app.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;


public interface Repository extends JpaRepository<Task, Long> {


}
