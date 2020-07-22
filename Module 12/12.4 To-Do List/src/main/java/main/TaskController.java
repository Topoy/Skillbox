package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.model.Task;

import java.util.*;

@RestController
public class TaskController
{
    @Autowired
    private TaskService taskService;

    @GetMapping(value = "/tasks/")
    public Iterable<Task> getTasks()
    {
        return taskService.getTasks();
    }

    @PostMapping(value = "/tasks/")
    public int addTask(@RequestBody Task task)
    {
        return taskService.addTask(task);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTask(@PathVariable("id") Integer id)
    {
        return taskService.getTask(id);
    }

    @PostMapping(value = "/tasks/{id}/")
    public void setDate(@RequestParam(name = "deadline") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Calendar deadline,
                     @PathVariable("id") Integer id)
    {
        taskService.setDate(deadline, id);
    }

    @DeleteMapping(value = "/tasks/{id}")
    public void removeTask(@PathVariable("id") Integer id)
    {
        taskService.removeTask(id);
    }



}
