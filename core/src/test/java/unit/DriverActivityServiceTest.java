package unit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.*;
import ru.tsystems.shalamov.entities.*;
import ru.tsystems.shalamov.entities.statuses.CargoStatus;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;
import ru.tsystems.shalamov.entities.statuses.OrderStatus;
import ru.tsystems.shalamov.entities.statuses.TruckStatus;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.DriverActivityService;
import ru.tsystems.shalamov.services.impl.DriverActivityServiceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.*;

/**
 * Created by viacheslav on 09.08.2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class DriverActivityServiceTest {

    DriverActivityService driverActivityService;

    @Mock
    private DriverDao driverDao;
    @Mock
    private ShiftDao shiftDao;
    @Mock
    private DriverStatusDao driverStatusDao;
    @Mock
    private CargoDao cargoDao;
    @Mock
    private OrderDao orderDao;

    @Before
    public void setup() {
        driverActivityService = new DriverActivityServiceImpl(driverDao, shiftDao, driverStatusDao, cargoDao, orderDao);
    }

    @Test(expected = ServiceLayerException.class)
    public void beginShiftNullDriver() throws ServiceLayerException {
        try {
            when(driverDao.findByPersonalNumber(Mockito.any(String.class))).thenReturn(null);
            driverActivityService.beginShift("driver");
        } catch (DataAccessLayerException e) {
            Assert.fail();
        }
    }

    @Test(expected = ServiceLayerException.class)
    public void beginShiftForBusyDriver() throws ServiceLayerException {
        try {
            DriverEntity driver = new DriverEntity("vasia", "vasia", "vasia");
            ShiftEntity shiftEntity = new ShiftEntity();
            when(driverDao.findByPersonalNumber(Mockito.anyString())).thenReturn(driver);
            when(shiftDao.findActiveShiftByDriver(Mockito.any(DriverEntity.class))).thenReturn(shiftEntity);

            driverActivityService.beginShift(driver.getPersonalNumber());
        } catch (DataAccessLayerException e) {
            Assert.fail();
        }
    }

    @Test
    public void beginShiftForDriver() {
        try {
            DriverEntity driver = new DriverEntity("vasia", "vasia", "vasia");
            DriverStatusEntity status = new DriverStatusEntity(driver);
            driver.setDriverStatusEntity(status);
            when(driverDao.findByPersonalNumber(Mockito.anyString())).thenReturn(driver);
            when(shiftDao.findActiveShiftByDriver(Mockito.any(DriverEntity.class))).thenReturn(null);

            doNothing().when(shiftDao).create(Mockito.any(ShiftEntity.class));
            doNothing().when(driverStatusDao).update((Mockito.any(DriverStatusEntity.class)));

            driverActivityService.beginShift(driver.getPersonalNumber());

            InOrder inOrder = inOrder(driverDao, shiftDao, driverStatusDao);
            inOrder.verify(driverDao).findByPersonalNumber(Mockito.any());
            inOrder.verify(shiftDao).findActiveShiftByDriver(Mockito.any(DriverEntity.class));
            inOrder.verify(shiftDao).create(Mockito.any(ShiftEntity.class));
            inOrder.verify(driverStatusDao).update(Mockito.any(DriverStatusEntity.class));
        } catch (DataAccessLayerException | ServiceLayerException e) {
            Assert.fail();
        }
    }


    @Test(expected = ServiceLayerException.class)
    public void endShiftNullDriver() throws ServiceLayerException {
        try {
            when(driverDao.findByPersonalNumber(Mockito.any(String.class))).thenReturn(null);
            driverActivityService.endShift("driver");
        } catch (DataAccessLayerException e) {
            Assert.fail();
        }
    }

    @Test(expected = ServiceLayerException.class)
    public void endShiftForFreeDriver() throws ServiceLayerException {
        try {
            DriverEntity driver = new DriverEntity("vasia", "vasia", "vasia");
            DriverStatusEntity status = new DriverStatusEntity(driver);
            driver.setDriverStatusEntity(status);

            when(driverDao.findByPersonalNumber(Mockito.anyString())).thenReturn(driver);
            when(shiftDao.findActiveShiftByDriver(Mockito.any(DriverEntity.class))).thenReturn(null);

            driverActivityService.endShift(driver.getPersonalNumber());
        } catch (DataAccessLayerException e) {
            Assert.fail();
        }
    }

    @Test
    public void endShiftForDriver() throws ServiceLayerException {
        try {
            DriverEntity driver = new DriverEntity("vasia", "vasia", "vasia");
            DriverStatusEntity status = new DriverStatusEntity(driver);
            driver.setDriverStatusEntity(status);
            ShiftEntity shiftEntity = new ShiftEntity();
            shiftEntity.setDriverEntity(driver);
            shiftEntity.setShiftBegin(new Timestamp((new Date()).getTime()));

            when(driverDao.findByPersonalNumber(Mockito.anyString())).thenReturn(driver);
            when(shiftDao.findActiveShiftByDriver(Mockito.any(DriverEntity.class))).thenReturn(shiftEntity);

            doNothing().when(shiftDao).update(Mockito.any(ShiftEntity.class));
            doNothing().when(driverStatusDao).update((Mockito.any(DriverStatusEntity.class)));

            driverActivityService.endShift(driver.getPersonalNumber());

            InOrder inOrder = inOrder(driverDao, shiftDao, driverStatusDao);
            inOrder.verify(driverDao).findByPersonalNumber(Mockito.any());
            inOrder.verify(shiftDao).findActiveShiftByDriver(Mockito.any(DriverEntity.class));
            inOrder.verify(shiftDao).update(Mockito.any(ShiftEntity.class));
            inOrder.verify(driverStatusDao).update(Mockito.any(DriverStatusEntity.class));
        } catch (DataAccessLayerException | ServiceLayerException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(expected = ServiceLayerException.class)
    public void changeStatusForMissingDriver() throws ServiceLayerException {
        try {
            when(driverDao.findByPersonalNumber(Mockito.any(String.class))).thenReturn(null);
            driverActivityService.driverStatusChanged("driver", DriverStatus.PRIMARY);
        } catch (DataAccessLayerException e) {
            Assert.fail();
        }
    }

    @Test(expected = ServiceLayerException.class)
    public void changeStatusToRest() throws ServiceLayerException {
        driverActivityService.driverStatusChanged("driver", DriverStatus.REST);
    }

    @Test(expected = ServiceLayerException.class)
    public void changeStatusFromRest() throws ServiceLayerException {
        try {
            DriverEntity driver = new DriverEntity("vasia", "vasia", "vasia");
            DriverStatusEntity status = new DriverStatusEntity(driver);
            status.setStatus(DriverStatus.REST);

            when(driverDao.findByPersonalNumber(Mockito.any(String.class))).thenReturn(driver);
            driverActivityService.driverStatusChanged("driver", DriverStatus.PRIMARY);
        } catch (DataAccessLayerException e) {
            Assert.fail();
        }
    }

    @Test
    public void changeStatus() {
        try {
            DriverEntity driver = new DriverEntity("vasia", "vasia", "vasia");
            DriverStatusEntity status = new DriverStatusEntity(driver);
            status.setStatus(DriverStatus.AUXILIARY);

            when(driverDao.findByPersonalNumber(Mockito.any(String.class))).thenReturn(driver);
            doNothing().when(driverStatusDao).update(Mockito.any());

            driverActivityService.driverStatusChanged("driver", DriverStatus.PRIMARY);

            InOrder inOrder = inOrder(driverDao, shiftDao, driverStatusDao);
            inOrder.verify(driverDao).findByPersonalNumber(Mockito.any());
            inOrder.verify(driverStatusDao).update(Mockito.any());
        } catch (DataAccessLayerException | ServiceLayerException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(expected = ServiceLayerException.class)
    public void changeStatusForMissingCargo() throws ServiceLayerException {
        try {
            when(cargoDao.findCargoByCargoIdentifier(Mockito.any(String.class))).thenReturn(null);
            driverActivityService.cargoStatusChanged("cargo", CargoStatus.DELIVERED);
        } catch (DataAccessLayerException e) {
            Assert.fail();
        }
    }

    @Test
    public void changeCargoStatus() throws ServiceLayerException {
        try {
            doNothing().when(cargoDao).update(Mockito.any());
            CargoEntity cargo = new CargoEntity("bricks", 500, CargoStatus.SHIPPED, null, "bricks");
            when(cargoDao.findCargoByCargoIdentifier(Mockito.any(String.class))).thenReturn(cargo);
            driverActivityService.cargoStatusChanged("cargo", CargoStatus.DELIVERED);

            InOrder inOrder = inOrder(cargoDao);
            inOrder.verify(cargoDao).findCargoByCargoIdentifier(Mockito.any());
            inOrder.verify(cargoDao).update(Mockito.any());
        } catch (DataAccessLayerException e) {
            Assert.fail();
        }
    }


    @Test(expected = ServiceLayerException.class)
    public void testCompleteMissingOrder() throws ServiceLayerException {
        try {
            when(orderDao.findByOrderIdentifier(Mockito.anyString())).thenReturn(null);
            driverActivityService.completeOrder("order");
        } catch (DataAccessLayerException e) {
            Assert.fail();
        }
    }


    @Test(expected = ServiceLayerException.class)
    public void testCompleteNotInProgressOrder() throws ServiceLayerException {
        try {
            OrderEntity orderEntity = new OrderEntity("order");
            orderEntity.setStatus(OrderStatus.COMPLETED);
            when(orderDao.findByOrderIdentifier(Mockito.anyString())).thenReturn(orderEntity);
            driverActivityService.completeOrder("order");
        } catch (DataAccessLayerException e) {
            Assert.fail();
        }
    }


    @Test(expected = ServiceLayerException.class)
    public void testCompleteOrder() throws ServiceLayerException {
        try {
            OrderEntity orderEntity = new OrderEntity("order");
            orderEntity.setCargoEntities(Arrays.asList(
                    new CargoEntity("", 1, CargoStatus.DELIVERED,
                            orderEntity,orderEntity.getOrderIdentifier())));
            orderEntity.setStatus(OrderStatus.IN_PROGRESS);
            when(orderDao.findByOrderIdentifier(Mockito.anyString())).thenReturn(orderEntity);

            TruckEntity truckEntity = new TruckEntity(1, "xx11111", 1000, TruckStatus.ASSIGNED);
            orderEntity.setTruckEntity(truckEntity);
            DriverEntity driverEntity = new DriverEntity("f", "l", "d");
            DriverStatusEntity statusEntity = new DriverStatusEntity(driverEntity);
            statusEntity.setStatus(DriverStatus.AUXILIARY);
            statusEntity.setTruckEntity(truckEntity);
            driverEntity.setDriverStatusEntity(statusEntity);
            truckEntity.setDriverStatusEntities(Arrays.asList(statusEntity));

            DriverActivityService driverActivityServiceSpy = Mockito.spy(driverActivityService);
            doNothing().when(driverActivityServiceSpy).endShift(Mockito.anyString());
            driverActivityServiceSpy.completeOrder(orderEntity.getOrderIdentifier());

            driverActivityService.completeOrder("order");
            Assert.assertEquals(orderEntity.getStatus(), OrderStatus.COMPLETED);
        } catch (DataAccessLayerException e) {
            Assert.fail();
        }
    }

}
