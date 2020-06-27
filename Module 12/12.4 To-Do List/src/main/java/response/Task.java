package response;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

public class Task
{
    private String name;
    private int id;
    private Calendar deadline;

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

    public Calendar getDeadline() {
        return deadline;
    }

    public void setDeadline(Calendar deadline) {
        this.deadline = deadline;
    }
}
