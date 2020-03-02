import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Main
{
    public static void main(String[] args)
    {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();

        try (SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
             Session session = sessionFactory.openSession()) {

            Teachers teachers = session.get(Teachers.class, 1);
            System.out.println(teachers.getName());
            Student student = session.get(Student.class, 2);
            System.out.println(student.getName());
            Course course = session.get(Course.class, 1);
            System.out.println(course.getName());
            Subscription subscription = session.get(Subscription.class, new SubscriptionPK(1, 2));
            System.out.println(subscription.getSubscriptionDate());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
