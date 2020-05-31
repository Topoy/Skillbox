package main;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class DefaultController
{
    @RequestMapping("/")
    public String toDoList()
    {
        return "Дата просмотра страницы: " + new Date();
    }
}
