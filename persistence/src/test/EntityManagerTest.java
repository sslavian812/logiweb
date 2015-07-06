import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.TestCase;
import ru.tsystems.shalamov.dao.EntityManagerUtil;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.entities.statuses.TruckStatus;

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
        deleteAll();
        addSome();
        showAll();
    }

    private void deleteAll() {
        EntityManager entityManager = EntityManagerUtil.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.createQuery("delete from TruckEntity").executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private void addSome() {
        EntityManager entityManager = EntityManagerUtil.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(new TruckEntity(1, "n1", 1000, TruckStatus.INTACT));
        entityManager.persist(new TruckEntity(2, "n2", 5000, TruckStatus.BROKEN));
        entityManager.persist(new TruckEntity(2, "n3", 10000, TruckStatus.INTACT));

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private void showAll() {
        EntityManager entityManager = EntityManagerUtil.createEntityManager();
        entityManager.getTransaction().begin();

        List<TruckEntity> result = entityManager.createQuery("from TruckEntity").getResultList();
        System.out.println("active only:");
        for (TruckEntity entity : result) {
            if(entity.getStatus() == TruckStatus.INTACT)
                System.out.println("Truck: " + entity.getRegistrationNumber() + "[" + entity.getCapacity() + "]");
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
