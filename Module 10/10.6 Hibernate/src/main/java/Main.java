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

//////////////////////////////////////////////Create LinkedPurchaseList ////////////////////////////////////////////////
            String query = "INSERT INTO " + LinkedPurchaseList.class.getSimpleName() + "(student_id, course_id, student_name, " +
                    "course_name, subscription_date, price) " +
                    "SELECT students.id as student_id, courses.id as course_id, student_name, course_name, subscription_date, " +
                    "purchaselist.price FROM purchaselist " +
                    "JOIN courses on courses.name = course_name " +
                    "JOIN students on students.name = student_name";
            session.beginTransaction();
            int rows = session.createSQLQuery(query).executeUpdate();
            System.out.println(rows);
            LinkedPurchaseList purchaseList = session.get(LinkedPurchaseList.class, new LinkedPurchaseListPK(1,10));
            System.out.println(purchaseList.getCourseName() + " - " + purchaseList.getStudentName());

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
