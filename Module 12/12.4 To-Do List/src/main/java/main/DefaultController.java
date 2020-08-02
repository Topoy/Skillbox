package main;

import main.model.Task;
import main.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Controller
public class DefaultController
{
    @Autowired
    private TaskRepository taskRepository;

    @RequestMapping("/")
    public String index(Model model)
   {
       Iterable<Task> taskIterator = taskRepository.findAll();
       List<Task> tasks = new ArrayList<>();
       for (Task task : taskIterator)
       {
           tasks.add(task);
       }
       model.addAttribute("tasksAmount", tasks.size());
       model.addAttribute("writeSomething", 1);
       return "indexWithoutJS";
   }
}
