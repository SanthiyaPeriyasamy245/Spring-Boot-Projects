package com.todo.todo_app.controller;


import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.todo.todo_app.model.Task;
import com.todo.todo_app.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {

     private final TaskService taskService;


    public TaskController(TaskService taskServicer) {
        this.taskService = taskService;
     
    }


    @GetMapping
    public String getTask(Model model) {

        List<Task> task_list = taskService.getAllTasks();
        model.addAttribute("tasks", task_list);
        return "tasks";
    }
    @PostMapping
    public String addTask(String title) {

        taskService.createTask(title);
        return "redirect:/task";
    }
    @GetMapping("/{id}")
    public String deleteTask(@PathVariable long id) {

        taskService.deleteTaskByUid(id);
        return "redirect:/task";
    }
    @GetMapping("/toggle/{id}")
    public String toggleTask(@PathVariable long id) {

        taskService.toggleTaskByid(id);
        return "redirect:/task";
    }

}
