package main;

import main.model.Task;
import main.model.TaskRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskDatabase
{
    private static Map<Integer, Task> taskDatabaseMap = new ConcurrentHashMap<>();
    private static AtomicInteger atomicId = new AtomicInteger(0);



    public static int addTask(@RequestBody Task task, TaskRepository taskRepository)
    {
        Task newTask = taskRepository.save(task);
        return newTask.getId();
    }

    public static ResponseEntity getTask(@PathVariable ("id") Integer id, TaskRepository taskRepository)
    {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(optionalTask.get(), HttpStatus.OK);
    }

    public static Map<Integer, Task> getTasks(TaskRepository taskRepository)
    {
        int id;
        Iterable<Task> taskIterable = taskRepository.findAll();
        for (Task task : taskIterable)
        {
            if (taskDatabaseMap.containsKey(task.getId()))
            {
                continue;
            }
            id = atomicId.incrementAndGet();
            taskDatabaseMap.put(id, task);
        }
        return taskDatabaseMap;
    }

    public static void setDate(@RequestParam(name = "deadline") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                       Calendar deadline, @PathVariable("id") Integer id, TaskRepository taskRepository)
    {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent())
        {
            return;
        }
        Task task = optionalTask.get();
        task.setDeadline(deadline);
        taskRepository.save(task);
    }



}
