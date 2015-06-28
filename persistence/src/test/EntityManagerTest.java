import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.TestCase;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.entities.TruckStatus;

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

        entityManager.persist(new TruckEntity(1, "n1", 1000, TruckStatus.INTACT));
        entityManager.persist(new TruckEntity(2, "n2", 5000, TruckStatus.BROKEN));
        entityManager.persist(new TruckEntity(2, "n3", 10000, TruckStatus.INTACT));

        entityManager.getTransaction().commit();
        entityManager.close();


        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        List<TruckEntity> result = entityManager.createQuery("from TruckEntity").getResultList();
        for (TruckEntity entity : result) {
                System.out.println("Truck: " + entity.getRegistrationNumber() + "[" + entity.getCapacity() + "]");
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
