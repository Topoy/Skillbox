package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import main.model.Task;

import java.util.*;

@Controller
@RequestMapping("/")
public class TaskController
{
    @Autowired
    private TaskService taskService;

    @GetMapping(value = "/tasks/")
    public Iterable<Task> getTasks(Model model)
    {
        Iterable<Task> tasks = taskService.getTasks();
        model.addAttribute("task", tasks);
        return tasks;
    }

    @PostMapping("/edit")
    public String addTask(@ModelAttribute(value = "task") Task task)
    {
        //taskService.addTask(task);
        return "redirect:/";
    }

    @GetMapping("/add")
    public String showAddTaskForm(Model model)
    {
        Task task = new Task();
        model.addAttribute("task", task);
        return "edit";
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTask(@PathVariable("id") Integer id)
    {
        return taskService.getTask(id);
    }

    @PostMapping(value = "/tasks/{id}/")
    public void setDate(@RequestParam(name = "deadline") @DateTimeFormat(pattern = "yyyy-MM-dd") Date deadline,
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
