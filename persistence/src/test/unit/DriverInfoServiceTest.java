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
import ru.tsystems.shalamov.dao.api.OrderDao;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.entities.DriverStatusEntity;
import ru.tsystems.shalamov.entities.OrderEntity;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;
import ru.tsystems.shalamov.entities.statuses.TruckStatus;
import ru.tsystems.shalamov.model.DriverAssignmentModel;
import ru.tsystems.shalamov.model.DriverModel;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.DriverInfoService;
import ru.tsystems.shalamov.services.impl.DriverInfoServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by viacheslav on 08.08.2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class DriverInfoServiceTest {
    DriverInfoService driverInfoService;

    @Mock
    private DriverDao driverDao;
    @Mock
    private OrderDao orderDao;

    @Before
    public void setup() {
        driverInfoService = new DriverInfoServiceImpl(driverDao, orderDao);
    }

    @Test(expected = ServiceLayerException.class)
    public void testInformationForAbsentDriver() throws ServiceLayerException {
        try {
            when(driverDao.findByPersonalNumber(Mockito.any(String.class))).thenReturn(null);
            driverInfoService.getPossibleInformationForDriver("vasia");

        } catch (DataAccessLayerException e) {
            throw new ServiceLayerException(e);
        }
    }

    @Test
    public void testInformationForDriverWithoutTruck() {
        try {
            DriverEntity driver = new DriverEntity("vasia", "vaskov", "1111");
            DriverStatusEntity status = new DriverStatusEntity(driver);
            status.setStatus(DriverStatus.REST);
            status.setTruckEntity(null);
            driver.setDriverStatusEntity(status);
            when(driverDao.findByPersonalNumber(Mockito.any(String.class))).thenReturn(driver);

            DriverAssignmentModel assignmentModel =
                    driverInfoService.getPossibleInformationForDriver(driver.getPersonalNumber());
            Assert.assertEquals(assignmentModel.getDriverPersonalNumber(), driver.getPersonalNumber());
            Assert.assertEquals(assignmentModel.getOrderIdentifier(), DriverInfoServiceImpl.NONE);
            Assert.assertEquals(assignmentModel.getTruckRegistrationNumber(), DriverInfoServiceImpl.NONE);

        } catch (ServiceLayerException | DataAccessLayerException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(expected = ServiceLayerException.class)
    public void testDriverHasTruckButNoOrder() throws ServiceLayerException {
        try {
            DriverEntity driver = new DriverEntity("vasia", "vaskov", "1111");
            TruckEntity truck = new TruckEntity(2, "xx11111", 2000, TruckStatus.INTACT);
            DriverStatusEntity status = new DriverStatusEntity(driver);
            status.setStatus(DriverStatus.REST);
            status.setTruckEntity(truck);
            driver.setDriverStatusEntity(status);

            when(driverDao.findByPersonalNumber(Mockito.any(String.class))).thenReturn(driver);
            when(orderDao.findByTruckId(Mockito.any(Integer.class))).thenReturn(null);

            driverInfoService.getPossibleInformationForDriver(driver.getPersonalNumber());
        } catch (DataAccessLayerException e) {
            throw new ServiceLayerException(e);
        }
    }

    @Test
    public void testInformationForDriver() {
        try {
            doNothing().when(driverDao).create(Mockito.any(DriverEntity.class));
            when(driverDao.findByPersonalNumber(Mockito.any(String.class))).thenReturn(null);

            List<DriverEntity> drivers = new ArrayList<>();

            TruckEntity truck = new TruckEntity(2, "xx11111", 2000, TruckStatus.INTACT);
            OrderEntity orderEntity = new OrderEntity("myOrder");
            orderEntity.setCargoEntities(new ArrayList<>());

            DriverEntity driver1 = new DriverEntity("vasia", "vaskov", "1111");
            DriverStatusEntity status1 = new DriverStatusEntity(driver1);
            status1.setStatus(DriverStatus.REST);
            status1.setTruckEntity(truck);
            DriverEntity driver2 = new DriverEntity("ivan", "ivanov", "2222");
            DriverStatusEntity status2 = new DriverStatusEntity(driver2);
            status2.setStatus(DriverStatus.REST);
            status2.setTruckEntity(truck);


            when(driverDao.findByPersonalNumber(Mockito.any(String.class))).thenReturn(driver1);
            when(orderDao.findByTruckId(Mockito.any(Integer.class))).thenReturn(orderEntity);


            when(driverDao.findByCurrentTruck(Mockito.any(Integer.class))).thenReturn(drivers);

            DriverAssignmentModel assignmentModel =
                    driverInfoService.getPossibleInformationForDriver(driver1.getPersonalNumber());
            Assert.assertEquals(assignmentModel.getDriverPersonalNumber(), driver1.getPersonalNumber());
            Assert.assertEquals(assignmentModel.getOrderIdentifier(), orderEntity.getOrderIdentifier());
            Assert.assertEquals(assignmentModel.getTruckRegistrationNumber(), truck.getRegistrationNumber());

            Assert.assertEquals(assignmentModel.getCargoes().size(), 2);

        } catch (ServiceLayerException | DataAccessLayerException e) {
            throw new RuntimeException(e);
        }
    }
    

    // todo findAllDriverAssignments  (simple one call)
    // todo findDriverAssignmentModelByPersonalNumber (simple one call)
    // todo findDriverAssignmentModelByOrderIdentifier (3 test cases like before)

}
