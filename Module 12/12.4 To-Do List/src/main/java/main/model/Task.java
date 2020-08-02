package main.model;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "task")
public class Task
{
    @Column(name = "name")
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    //private String stringDeadline;
    @Column(name = "deadline")
    private String deadline;

    //private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public synchronized void setId(int id) {
        this.id = id;
    }

    public Date getDeadline() throws Exception {
        try
        {
            return new SimpleDateFormat("yyyy-MM-dd").parse(deadline);
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        return new Date();
    }

    public void setDeadline(Date deadline) {
        this.deadline = new SimpleDateFormat("yyyy-MM-dd").format(deadline);
      /*  SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            this.deadline = format.parse(stringDeadline);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
    }

}
