package main;

import org.springframework.web.bind.annotation.RequestBody;
import response.Task;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskStorage
{
    private static List<Task> taskList = Collections.synchronizedList(new ArrayList<>());
    private static AtomicInteger atomicId = new AtomicInteger(0);
    private static Map<Integer, Task> taskMap = new ConcurrentHashMap<>();

    public static int addTask(@RequestBody Task task)
    {
        int id = atomicId.incrementAndGet();
        Calendar deadline = Calendar.getInstance();
        deadline.add(Calendar.DAY_OF_MONTH, 1);
        taskMap.put(id, task);
        task.setId(id);
        task.setDeadline(deadline);
        return id;
    }

    public static List<Task> getTasks()
    {
        return taskList;
    }

    public static Map<Integer, Task> getTaskMap()
    {
        return taskMap;
    }


}
