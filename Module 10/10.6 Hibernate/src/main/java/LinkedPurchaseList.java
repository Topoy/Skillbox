import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "linkedpurchaselist")
public class LinkedPurchaseList
{
    @EmbeddedId
    private LinkedPurchaseListPK linkedPurchaseListPK;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "subscription_date")
    private Date subscriptionDate;

    private int price;

    public LinkedPurchaseList()
    {
    }
    public LinkedPurchaseList(LinkedPurchaseListPK linkedPurchaseListPK, String studentName, String courseName, Date subscriptionDate, int price)
    {
        this.linkedPurchaseListPK = linkedPurchaseListPK;
        this.studentName = studentName;
        this.courseName = courseName;
        this.subscriptionDate = subscriptionDate;
        this.price = price;
    }

    @ManyToOne
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    private Course course;

    public LinkedPurchaseListPK getLinkedPurchaseListPK() {
        return linkedPurchaseListPK;
    }

    public void setLinkedPurchaseListPK(LinkedPurchaseListPK linkedPurchaseListPK) {
        this.linkedPurchaseListPK = linkedPurchaseListPK;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
