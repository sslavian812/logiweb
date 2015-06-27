import junit.framework.TestCase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.entities.TruckStatus;

import java.util.List;

/**
 * Created by viacheslav on 27.06.2015.
 */
public class HibernateSessionTest extends TestCase {
    SessionFactory sessionFactory;

    @Override
    protected void setUp() throws Exception {
        sessionFactory = new Configuration()
                .configure() // configures settings from hibernate.cfg.xml
                .buildSessionFactory();
    }

    @Override
    protected void tearDown() throws Exception {
        sessionFactory.close();
    }

    public void testBasicUsage() {
        deleteAll();
        addSome();
        showAll();
    }

    private void deleteAll() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete from TruckEntity ").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    private void addSome() {
        Session session;
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(new TruckEntity());
        session.save(new TruckEntity(1, "n1", 1000, TruckStatus.ACTUVE));
        session.save(new TruckEntity(2, "n2", 5000, TruckStatus.BROKEN));
        session.save(new TruckEntity(2, "n3", 10000, TruckStatus.ACTUVE));
        session.getTransaction().commit();
        session.close();
    }

    private void showAll() {
        Session session;
        session = sessionFactory.openSession();
        session.beginTransaction();
        List<TruckEntity> result = session.createQuery("from TruckEntity").list();
        System.out.println("active only:");
        for (TruckEntity entity : result) {
            if(entity.getStatus() == TruckStatus.ACTUVE)
                System.out.println("Truck: " + entity.getRegistrationNumber() + "[" + entity.getCapacity() + "]");
        }
        session.getTransaction().commit();
        session.close();
    }
}
