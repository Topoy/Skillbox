package main;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import response.Task;

import java.util.*;

@RestController
public class TaskController
{
    @GetMapping(value = "/tasks/")
    public Map<Integer, Task> getTasks()
    {
        return TaskStorage.getTaskMap();
    }

    @PostMapping(value = "/tasks/")
    public int addTask(@RequestBody Task task)
    {
        return TaskStorage.addTask(task);
    }

    @GetMapping("/tasks/{id}")
    public Task getTask(@PathVariable("id") Integer id)
    {
        return TaskStorage.getTask(id);
    }

    @PostMapping(value = "/tasks/{id}/")
    public void setDate(@RequestParam(name = "deadline") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Calendar deadline,
                     @PathVariable("id") Integer id)
    {
       Task task = getTask(id);
       task.setDeadline(deadline);
    }

    @DeleteMapping(value = "/tasks/{id}")
    public void removeTask(@PathVariable("id") Integer id)
    {
        TaskStorage.removeTask(id);
    }


}
