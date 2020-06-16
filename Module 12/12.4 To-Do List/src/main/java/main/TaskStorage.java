package main;

import response.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TaskStorage
{
    private static ArrayList<Task> taskList = new ArrayList<Task>();

    public static int addTask(Task task, String name)
    {
        int id = taskList.size() + 1;
        Calendar deadline = Calendar.getInstance();
        deadline.add(Calendar.DAY_OF_MONTH, 1);
        taskList.add(task);
        task.setId(id);
        task.setName(name);
        task.setDeadline(deadline);
        return id;
    }

    public static List<Task> getTasks()
    {
        return taskList;
    }

}
