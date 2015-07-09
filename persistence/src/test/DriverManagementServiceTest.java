import org.junit.Test;
import org.testng.Assert;
import ru.tsystems.shalamov.ApplicationContext;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.DriverManagementService;

import java.util.List;

/**
 * Created by viacheslav on 05.07.2015.
 */
public class DriverManagementServiceTest {

    @Test
    public void testBasicUsage() {
        try {
            DriverManagementService driverManagementService =
                    ApplicationContext.getInstance().getDriverManagementService();

            // clear te database:
            List<DriverEntity> somedrivers = driverManagementService.listDrivers();
            for (DriverEntity d : somedrivers) {
                driverManagementService.deleteDriverByPersonalNumber(d.getPersonalNumber());
            }


            // add some drivers:
            driverManagementService.addDriver(new DriverEntity("vasia", "vaskov", "1111"));
            driverManagementService.addDriver(new DriverEntity("ivan", "ivanov", "2222"));
            driverManagementService.addDriver(new DriverEntity("pavel", "pavlov", "3333"));
            driverManagementService.addDriver(new DriverEntity("petr", "petrov", "4444"));

            // check drivers count:
            List<DriverEntity> drivers = driverManagementService.listDrivers();
            Assert.assertEquals(drivers.size(), 4);

            // remove one driver:
            DriverEntity driver = drivers.get(0);
            drivers.remove(driver);
            driverManagementService.deleteDriverByPersonalNumber(driver.getPersonalNumber());

            // check absence of removed driver:
            List<DriverEntity> remaining = driverManagementService.listDrivers();
            Assert.assertFalse(remaining.contains(driver));

            // clear the database:
            for (DriverEntity d : remaining) {
                driverManagementService.deleteDriverByPersonalNumber(d.getPersonalNumber());
            }

            // check database is empty:
            List<DriverEntity> empty = driverManagementService.listDrivers();
            assert (empty.isEmpty());

        } catch (ServiceLayerException e) {
            throw new RuntimeException(e);
        }
    }
}
