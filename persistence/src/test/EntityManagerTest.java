import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.TestCase;
import ru.tsystems.shalamov.entities.DriverEntity;

/**
 * Created by viacheslav on 26.06.2015.
 */
public class EntityManagerTest extends TestCase {
    private EntityManagerFactory entityManagerFactory;

    @Override
    protected void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory("logiweb");

    }

    @Override
    protected void tearDown() throws Exception {
        entityManagerFactory.close();
    }

    public void testBasicUsage() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(new DriverEntity("name 1", "surname 1", "number 1"));
        entityManager.persist(new DriverEntity("name 2", "surname 2", "number 2"));
        entityManager.persist(new DriverEntity("name 3", "surname 3", "number 3"));

        entityManager.getTransaction().commit();
        entityManager.close();


        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<DriverEntity> result = entityManager.createQuery("from DriverEntity ", DriverEntity.class).getResultList();
        for (DriverEntity driverEntity : result) {
            System.out.println("Driver:< " + driverEntity.getFirstName() + " >");
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
