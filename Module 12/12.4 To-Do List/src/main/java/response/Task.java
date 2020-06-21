package response;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

public class Task
{
    private String name;
    private AtomicInteger id;
    private Calendar deadline;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AtomicInteger getId() {
        return id;
    }

    public synchronized void setId(AtomicInteger id) {
        this.id = id;
    }

    public Calendar getDeadline() {
        return deadline;
    }

    public void setDeadline(Calendar deadline) {
        this.deadline = deadline;
    }
}
