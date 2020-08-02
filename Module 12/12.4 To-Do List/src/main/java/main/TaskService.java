package main;

import main.model.Task;
import main.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class TaskService
{
    @Autowired
    private TaskRepository taskRepository;


    public void addTask(@RequestBody Task task)
    {
        Task newTask = taskRepository.save(task);
        //return newTask.getId();
    }

    public ResponseEntity<Task> getTask(@PathVariable ("id") Integer id)
    {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (!optionalTask.isPresent())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(optionalTask.get(), HttpStatus.OK);
    }

    public Iterable<Task> getTasks()
    {
        Iterable<Task> taskIterable = taskRepository.findAll();
        return taskIterable;
    }

    public void setDate(@RequestParam(name = "deadline") @DateTimeFormat(pattern = "yyyy-MM-dd")
                                Date deadline, @PathVariable("id") Integer id)
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

    @DeleteMapping(value = "/tasks/{id}")
    public void removeTask(@PathVariable("id") Integer id)
    {
        if (taskRepository.findById(id).isPresent())
        {
            taskRepository.deleteById(id);
        }
        else
        {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }



}
