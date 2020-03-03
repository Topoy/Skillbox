import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LinkedPurchaseListPK implements Serializable
{
    @Column(name = "student_id")
    private int studentId;

    @Column(name = "course_id")
    private int courseId;

    public LinkedPurchaseListPK(int studentId, int courseId)
    {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public int getStudentId()
    {
        return studentId;
    }
    public void setStudentId(int studentId)
    {
        this.studentId = studentId;
    }
    public int getCourseId()
    {
        return courseId;
    }
    public void setCourseId( int courseId)
    {
        this.courseId = courseId;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getStudentId(), getCourseId());
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof LinkedPurchaseListPK))
        {
            return false;
        }
        LinkedPurchaseListPK that = (LinkedPurchaseListPK) o;
        return Objects.equals(getStudentId(), that.studentId) && Objects.equals(getCourseId(), that.courseId);
    }


}
