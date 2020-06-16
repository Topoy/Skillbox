package main;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class DefaultController
{
    @RequestMapping("/tasks/")
    public String toDoList()
    {
        return "Дата просмотра страницы: " + new Date();
    }
    //@RequestMapping(value = "/tasks/", method = RequestMethod.GET)
    public static String getDate()
    {
        return "Дата: " + new Date();
    }

}
