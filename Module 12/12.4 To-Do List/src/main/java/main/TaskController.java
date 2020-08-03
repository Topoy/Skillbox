package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import main.model.Task;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/")
public class TaskController
{
    @Autowired
    private TaskService taskService;

    @GetMapping(value = "/")
    public String getTasks(Model model)
    {
        ArrayList<Task> tasks = new ArrayList<>();
        Iterable<Task> taskIterable = taskService.getTasks();
        for (Task task : taskIterable)
        {
            tasks.add(task);
        }
        model.addAttribute("task", tasks);
        return "indexWithoutJS";
    }

    @PostMapping("/edit")
    public String addTask(@ModelAttribute(value = "task") Task task)
    {
        taskService.addTask(task);
        return "redirect:/";
    }

    @GetMapping("/add")
    public String showAddTaskForm(Model model)
    {
        Task task = new Task();
        model.addAttribute("task", task);
        return "edit";
    }

    @GetMapping("/show/{id}")
    public String getTask(Model model, @PathVariable("id") Integer id)
    {
        Task task = taskService.getTaskById(id);
        model.addAttribute("task", task);
        return "task-page";
    }

    @PostMapping(value = "/tasks/{id}/")
    public void setDate(@RequestParam(name = "deadline") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deadline,
                     @PathVariable("id") Integer id)
    {
        taskService.setDate(deadline, id);
    }

    @GetMapping(value = "/edit/{id}")
    public String editTask(Model model, @PathVariable("id") Integer id)
    {
        Task task = taskService.getTaskById(id);
        model.addAttribute("task", task);
        return "edit";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteTask(Model model, @PathVariable("id") Integer id)
    {
        taskService.removeTask(id);
        return "redirect:/";
    }


    @DeleteMapping(value = "/tasks/{id}")
    public void removeTask(@PathVariable("id") Integer id)
    {
        taskService.removeTask(id);
    }



}
