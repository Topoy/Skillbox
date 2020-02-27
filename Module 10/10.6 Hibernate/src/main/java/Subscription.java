import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Subscriptions")
public class Subscription
{
    @EmbeddedId
    private SubscriptionPK subscriptionPK;

    @Column(name = "subscription_date")
    private Date subscriptionDate;

    @ManyToOne
    @JoinColumn(name = "student_id", insertable = false, updatable = false)
    private Student students;

    @ManyToOne
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    private Course course;

    public SubscriptionPK getSubscriptionPK()
    {
        return subscriptionPK;
    }

    public void setSubscriptionPK(SubscriptionPK subscriptionPK)
    {
        this.subscriptionPK = subscriptionPK;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }
}
