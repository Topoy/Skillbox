package main;

import response.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskStorage
{
    private static List<Task> taskList = Collections.synchronizedList(new ArrayList<>());
    private static AtomicInteger atomicId = new AtomicInteger(0);

    public static AtomicInteger addTask(Task task, String name)
    {
        int id = atomicId.incrementAndGet();
        Calendar deadline = Calendar.getInstance();
        deadline.add(Calendar.DAY_OF_MONTH, 1);
        taskList.add(task);
        task.setId(id);
        task.setName(name);
        task.setDeadline(deadline);
        return atomicId;
    }

    public static List<Task> getTasks()
    {
        return taskList;
    }

}
