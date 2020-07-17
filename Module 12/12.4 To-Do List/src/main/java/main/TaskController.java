package main;

import main.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.model.Task;

import java.util.*;

@RestController
public class TaskController
{
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping(value = "/tasks/")
    public Map<Integer, Task> getTasks()
    {
        return TaskDatabase.getTasks(taskRepository);
    }

    @PostMapping(value = "/tasks/")
    public int addTask(@RequestBody Task task)
    {
        return TaskDatabase.addTask(task, taskRepository);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity getTask(@PathVariable("id") Integer id)
    {
        return TaskDatabase.getTask(id, taskRepository);
    }

    @PostMapping(value = "/tasks/{id}/")
    public void setDate(@RequestParam(name = "deadline") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Calendar deadline,
                     @PathVariable("id") Integer id)
    {
        TaskDatabase.setDate(deadline, id, taskRepository);
    }

    @DeleteMapping(value = "/tasks/{id}")
    public void removeTask(@PathVariable("id") Integer id)
    {
        TaskStorage.removeTask(id);
        taskRepository.deleteById(id);
    }



}
