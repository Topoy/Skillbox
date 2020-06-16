package main;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import response.Task;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class TaskController
{
    @RequestMapping(value = "/tasks/", method = RequestMethod.GET)
    public List<Task> getTasks()
    {
        return TaskStorage.getTasks();
    }

    @RequestMapping(value = "/tasks/", method = RequestMethod.POST)
    public int addTask(Task task, String name)
    {
        return TaskStorage.addTask(task, name);
    }

    @GetMapping("/tasks/{id}")
    public Task getTask(@PathVariable("id") Integer id)
    {
        return TaskStorage.getTasks().get(id - 1);
    }

    @PostMapping(value = "/tasks/{id}/")
    public void setDate(@RequestParam(name = "deadline") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Calendar deadline,
                     @PathVariable("id") Integer id)
    {
       Task task = getTask(id);
       task.setDeadline(deadline);
    }

    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.DELETE)
    public void removeTask(@PathVariable("id") Integer id)
    {
        getTasks().remove(id - 1);
    }

}
