import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by viacheslav on 26.06.2015.
 */
public class Main {
    public static void main(String[] args) {
        SessionFactory sessions = new Configuration().configure().buildSessionFactory();

        Session session = sessions.openSession();
        session.beginTransaction();
        session.getTransaction().commit();
    }
}
