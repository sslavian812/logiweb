package unit;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.DriverDao;
import ru.tsystems.shalamov.dao.api.DriverStatusDao;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.entities.DriverStatusEntity;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;
import ru.tsystems.shalamov.model.DriverModel;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.DriverManagementService;
import ru.tsystems.shalamov.services.impl.DriverManagementServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by viacheslav on 06.08.2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class DriverManagementServiceTest {

    DriverManagementService driverManagementService;

    @Mock
    private DriverDao driverDao;
    @Mock
    private DriverStatusDao driverStatusDao;


    @Before
    public void setup() {
        driverManagementService =
                new DriverManagementServiceImpl(driverDao, driverStatusDao);
    }


    private boolean compareDriverEntities(DriverEntity a, DriverEntity b) {
        if (a.getFirstName() != null ? !a.getFirstName().equals(b.getFirstName()) : b.getFirstName() != null)
            return false;
        if (a.getLastName() != null ? !a.getLastName().equals(b.getLastName()) : b.getLastName() != null)
            return false;
        if (a.getPersonalNumber() != null ? !a.getPersonalNumber().equals(b.getPersonalNumber()) : b.getPersonalNumber() != null)
            return false;

        return true;
    }


    @Test
    public void testCreateFindAll() {
        try {
            doNothing().when(driverDao).create(Mockito.any(DriverEntity.class));
            when(driverDao.findByPersonalNumber(Mockito.any(String.class))).thenReturn(null);

            Set<DriverEntity> drivers = new HashSet<>();

            drivers.add(new DriverEntity("vasia", "vaskov", "1111"));
            drivers.add(new DriverEntity("ivan", "ivanov", "2222"));
            drivers.add(new DriverEntity("pavel", "pavlov", "3333"));
            drivers.add(new DriverEntity("petr", "petrov", "4444"));

            List<DriverModel> driverModelList = drivers.stream()
                    .map(d -> new DriverModel(d))
                    .collect(Collectors.toList());

            when(driverDao.findAll()).thenReturn(drivers.stream()
                    .collect(Collectors.toList()));

            for (DriverEntity d : drivers) {
                driverManagementService.addDriver(new DriverModel(d));
            }

            List<DriverModel> received = driverManagementService.findAllDrivers();

            received.removeAll(driverModelList);
            Assert.assertEquals(received.size(), 0);

        } catch (ServiceLayerException | DataAccessLayerException e) {
            Assert.fail();
        }
    }


    @Test
    public void testCreateFindByPN() {
        try {
            doNothing().when(driverDao).create(Mockito.any(DriverEntity.class));
            when(driverDao.findByPersonalNumber(Mockito.any(String.class))).thenReturn(null);

            DriverEntity driver = new DriverEntity("vasia", "vaskov", "1111");
            driverManagementService.addDriver(new DriverModel(driver));

            when(driverDao.findByPersonalNumber(Mockito.any(String.class))).thenReturn(driver);


            Assert.assertEquals(driverManagementService
                            .findDriverModelByPersonalNumber(
                                    driver.getPersonalNumber()),
                    new DriverModel(driver));

        } catch (ServiceLayerException | DataAccessLayerException e) {
            Assert.fail();
        }
    }


    @Test
    public void testAddUpdateFindByPN() {
        try {
            doNothing().when(driverDao).create(Mockito.any(DriverEntity.class));
            doNothing().when(driverDao).update(Mockito.any(DriverEntity.class));
            when(driverDao.findByPersonalNumber(Mockito.any(String.class))).thenReturn(null);

            DriverEntity driver = new DriverEntity("vasia", "vaskov", "1111");
            driverManagementService.addDriver(new DriverModel(driver));
            when(driverDao.findByPersonalNumber(Mockito.any(String.class))).thenReturn(driver);

            String oldPN = driver.getPersonalNumber();
            driver.setFirstName("max");

            driverManagementService.updateDriver(new DriverModel(driver), oldPN);

            Assert.assertEquals(driverManagementService
                            .findDriverModelByPersonalNumber(
                                    driver.getPersonalNumber()),
                    new DriverModel(driver));

        } catch (ServiceLayerException | DataAccessLayerException e) {
            Assert.fail();
        }
    }

    @Test(expected = ServiceLayerException.class)
    public void testAlreadyInUse() throws ServiceLayerException{
        try {
            doNothing().when(driverDao).create(Mockito.any(DriverEntity.class));
            DriverEntity driver = new DriverEntity("vasia", "vaskov", "1111");
            when(driverDao.findByPersonalNumber(Mockito.any(String.class))).thenReturn(driver);

            driverManagementService.addDriver(new DriverModel(driver));

        } catch (ServiceLayerException | DataAccessLayerException e) {
            Assert.fail();
        }
    }

    @Test(expected = ServiceLayerException.class)
    public void testUpdateNotExistingDriver() throws ServiceLayerException{
        try {
            doNothing().when(driverDao).update(Mockito.any(DriverEntity.class));
            DriverEntity driver = new DriverEntity("vasia", "vaskov", "1111");
            when(driverDao.findByPersonalNumber(Mockito.any(String.class))).thenReturn(null);

            driverManagementService.updateDriver(new DriverModel(driver), driver.getPersonalNumber());

        } catch (ServiceLayerException | DataAccessLayerException e) {
            Assert.fail();
        }
    }

    @Test(expected = ServiceLayerException.class)
    public void testPNCollisionOnUpdate() throws ServiceLayerException{
        try {
            doNothing().when(driverDao).update(Mockito.any(DriverEntity.class));
            DriverEntity driver1 = new DriverEntity("vasia", "vaskov", "1111");
            DriverEntity driver2 = new DriverEntity("vasia", "vaskov", "2222");
            when(driverDao.findByPersonalNumber(driver1.getPersonalNumber())).thenReturn(driver1);
            when(driverDao.findByPersonalNumber(driver2.getPersonalNumber())).thenReturn(driver2);

            // truing to change pn "1111" to "2222" for driver 1
            driverManagementService.updateDriver(new DriverModel(driver2), driver1.getPersonalNumber());
        } catch (ServiceLayerException | DataAccessLayerException e) {
            Assert.fail();
        }
    }


    @Test(expected = ServiceLayerException.class)
    public void testDeletingNotExistingDriver() throws ServiceLayerException{
        try {
            doNothing().when(driverDao).delete(Mockito.any(DriverEntity.class));
            DriverEntity driver = new DriverEntity("vasia", "vaskov", "1111");
            when(driverDao.findByPersonalNumber(Mockito.any(String.class))).thenReturn(null);
            driverManagementService.deleteDriverByPersonalNumber(driver.getPersonalNumber());
        } catch (ServiceLayerException | DataAccessLayerException e) {
            Assert.fail();
        }
    }


    @Test(expected = ServiceLayerException.class)
    public void testDeletingBusyDriver() throws ServiceLayerException{
        try {
            doNothing().when(driverDao).delete(Mockito.any(DriverEntity.class));

            DriverEntity driver = new DriverEntity("vasia", "vaskov", "1111");
            DriverStatusEntity status = new DriverStatusEntity(driver);
            status.setStatus(DriverStatus.PRIMARY);
            driver.setDriverStatusEntity(status);

            when(driverDao.findByPersonalNumber(driver.getPersonalNumber())).thenReturn(driver);

            driverManagementService.deleteDriverByPersonalNumber(driver.getPersonalNumber());
        } catch (ServiceLayerException | DataAccessLayerException e) {
            Assert.fail();
        }
    }


    @Test
    public void testDeletingDriver(){
        try {
            doNothing().when(driverDao).delete(Mockito.any(DriverEntity.class));
            doNothing().when(driverStatusDao).delete(Mockito.any(DriverStatusEntity.class));

            DriverEntity driver = new DriverEntity("vasia", "vaskov", "1111");
            DriverStatusEntity status = new DriverStatusEntity(driver);
            status.setStatus(DriverStatus.UNASSIGNED);
            driver.setDriverStatusEntity(status);

            when(driverDao.findByPersonalNumber(driver.getPersonalNumber())).thenReturn(driver);

            driverManagementService.deleteDriverByPersonalNumber(driver.getPersonalNumber());
        } catch (ServiceLayerException | DataAccessLayerException e) {
            Assert.fail();
        }
    }


    @Test(expected = ServiceLayerException.class)
    public void testValidationFirsrNameFail1() throws ServiceLayerException{
            DriverEntity driver = new DriverEntity(null, "vasko", "1111");
            driverManagementService.addDriver(new DriverModel(driver));
    }

    @Test(expected = ServiceLayerException.class)
    public void testValidationLastNameFail1() throws ServiceLayerException{
            DriverEntity driver = new DriverEntity("vasia", null, "1111");
            driverManagementService.addDriver(new DriverModel(driver));
    }

    @Test(expected = ServiceLayerException.class)
    public void testValidationPNFail1() throws ServiceLayerException{
            DriverEntity driver = new DriverEntity("vasia", "vasko", null);
            driverManagementService.addDriver(new DriverModel(driver));
    }
    @Test(expected = ServiceLayerException.class)
    public void testValidationFirsrNameFail2() throws ServiceLayerException{
        DriverEntity driver = new DriverEntity("", "vasko", "1111");
        driverManagementService.addDriver(new DriverModel(driver));
    }

    @Test(expected = ServiceLayerException.class)
    public void testValidationLastNameFail2() throws ServiceLayerException{
        DriverEntity driver = new DriverEntity("vasia", "", "1111");
        driverManagementService.addDriver(new DriverModel(driver));
    }

    @Test(expected = ServiceLayerException.class)
    public void testValidationPNFail2() throws ServiceLayerException{
        DriverEntity driver = new DriverEntity("vasia", "vasko", "");
        driverManagementService.addDriver(new DriverModel(driver));
    }
}
