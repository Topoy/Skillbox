package main.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "deadline")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime deadline;

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

    public LocalDateTime getDeadline()
    {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
       this.deadline = deadline;
    }

}
