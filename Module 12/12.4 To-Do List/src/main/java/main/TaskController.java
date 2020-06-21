package main;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import response.Task;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class TaskController
{
    @GetMapping(value = "/tasks/")
    public List<Task> getTasks()
    {
        return TaskStorage.getTasks();
    }

    @PostMapping(value = "/tasks/")
    public AtomicInteger addTask(Task task, String name)
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

    @DeleteMapping(value = "/tasks/{id}")
    public void removeTask(@PathVariable("id") Integer id)
    {
        try
        {
            getTasks().remove(id - 1);
        }
        catch (IndexOutOfBoundsException e)
        {
            System.out.println("Вы пытаетесь удалить несуществующий элемент");
        }

    }

}
