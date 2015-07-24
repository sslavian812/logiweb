import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.tsystems.shalamov.dao.api.CargoDao;

/**
 * Created by viacheslav on 26.06.2015.
 */
public class Main {

    private Main() {
    }

    public static void main(String[] args) {
        /*SessionFactory sessions = new Configuration().configure().buildSessionFactory();

        Session session = sessions.openSession();
        session.beginTransaction();
        session.getTransaction().commit();*/
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("WEB-INF/applicationContext.xml");
        CargoDao cargoDao = ctx.getBean(CargoDao.class);
//        System.out.println(((CargoDaoImpl)cargoDao).getEntityManager());
    }
}
