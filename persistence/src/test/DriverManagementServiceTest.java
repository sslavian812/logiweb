import org.junit.Test;
import org.testng.Assert;
import ru.tsystems.shalamov.ApplicationContext;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.services.api.DriverManagementService;
import java.util.List;

/**
 * Created by viacheslav on 05.07.2015.
 */
public class DriverManagementServiceTest {

    @Test
    public void testBasicUsage() {
        DriverManagementService driverManagementService =
                ApplicationContext.INSTANCE.getDriverManagementService();

        driverManagementService.addDriver(new DriverEntity("vasia", "vaskov", "1111"));
        driverManagementService.addDriver(new DriverEntity("ivan", "ivanov", "2222"));
        driverManagementService.addDriver(new DriverEntity("pavel", "pavlov", "3333"));
        driverManagementService.addDriver(new DriverEntity("petr", "petrov", "4444"));

        List<DriverEntity> drivers = driverManagementService.listDrivers();
        Assert.assertEquals(drivers.size(), 4);

        DriverEntity driver = drivers.get(0);
        drivers.remove(driver);
        driverManagementService.deleteDriverByPersonalDriver(driver.getPersonalNumber());

        List<DriverEntity> remaining = driverManagementService.listDrivers();

        Assert.assertFalse(remaining.contains(driver));
    }
}
