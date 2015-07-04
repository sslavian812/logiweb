import junit.framework.TestCase;
import ru.tsystems.shalamov.ApplicationContext;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.services.api.DriverManagementService;

import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by viacheslav on 05.07.2015.
 */
public class DriverManagementServiceTest extends TestCase {
    @Override
    protected void setUp() throws Exception {
    }

    @Override
    protected void tearDown() throws Exception {
    }

    public void testBasicUsage() {
        DriverManagementService driverManagementService =
                ApplicationContext.INSTANCE.getDriverManagementService();

        driverManagementService.addDriver(new DriverEntity("vasia", "vaskov", "1111"));
        driverManagementService.addDriver(new DriverEntity("ivan", "ivanov", "2222"));
        driverManagementService.addDriver(new DriverEntity("pavel", "pavlov", "3333"));
        driverManagementService.addDriver(new DriverEntity("petr", "petrov", "4444"));

        List<DriverEntity> drivers = driverManagementService.listDrivers();
        assertEquals(drivers.size(), 4);

        DriverEntity driver = drivers.get(0);
        drivers.remove(driver);
        driverManagementService.deleteDriverByPersonalDriver(driver.getPersonalNumber());

        List<DriverEntity> remaining = driverManagementService.listDrivers();

        assertFalse(remaining.contains(driver));
    }
}
