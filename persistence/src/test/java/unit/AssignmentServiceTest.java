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
import ru.tsystems.shalamov.model.AvailableToAssignModel;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.AssignmentService;
import ru.tsystems.shalamov.services.impl.AssignmentServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

/**
 * Created by viacheslav on 10.08.2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class AssignmentServiceTest {

    AssignmentService assignmentService;

    @Mock
    DriverDao driverDao;
    @Mock
    OrderDao orderDao;
    @Mock
    TruckDao truckDao;
    @Mock
    DriverStatusDao driverStatusDao;
    @Mock
    CargoDao cargoDao;


    @Before
    public void setup() {
        assignmentService = new AssignmentServiceImpl(driverDao, orderDao, truckDao, driverStatusDao, cargoDao);
    }

    @Test(expected = ServiceLayerException.class)
    public void findAvailableForMissingOrder() throws ServiceLayerException {
        try {
            when(orderDao.findByOrderIdentifier(Mockito.any(String.class))).thenReturn(null);
            assignmentService.findAvailableToAssign("order");
        } catch (DataAccessLayerException e) {
            throw new ServiceLayerException(e);
        }
    }


    @Test(expected = ServiceLayerException.class)
    public void findAvailableForAlreadyAssigned() throws ServiceLayerException {
        try {
            OrderEntity order = new OrderEntity("order");
            order.setStatus(OrderStatus.IN_PROGRESS);
            when(orderDao.findByOrderIdentifier(Mockito.any(String.class))).thenReturn(order);
            assignmentService.findAvailableToAssign(order.getOrderIdentifier());
        } catch (DataAccessLayerException e) {
            throw new ServiceLayerException(e);
        }
    }

    @Test
    public void findAvailableForOrder() throws ServiceLayerException {
        try {
            OrderEntity order = new OrderEntity("order");
            order.setStatus(OrderStatus.UNASSIGNED);
            when(orderDao.findByOrderIdentifier(Mockito.any(String.class))).thenReturn(order);
            List<CargoEntity> cargoes = new ArrayList<>();
            cargoes.add(new CargoEntity("bricks", 500, CargoStatus.PREPARED, order, "bricks500"));
            order.setCargoEntities(cargoes);

            List<TruckEntity> availableTrucks = new ArrayList<>();
            availableTrucks.add(new TruckEntity(1, "xx11111", 1000, TruckStatus.INTACT));
            when(truckDao.findByMinCapacityWhereStatusOkAndNotAssignedToOrder(Mockito.anyInt()))
                    .thenReturn(availableTrucks);

            List<DriverEntity> availableDrivers = new ArrayList<>();
            availableDrivers.add(new DriverEntity("vv", "vv", "vv"));
            availableDrivers.add(new DriverEntity("dd", "dd", "dd"));
            when(driverDao.findByMaxWorkingHoursWhereNotAssignedToOrder()).thenReturn(availableDrivers);

            AvailableToAssignModel available = assignmentService.findAvailableToAssign(order.getOrderIdentifier());

            Assert.assertEquals(available.getAvailableTrucks().get(0).getRegistrationNumber(),
                    availableTrucks.get(0).getRegistrationNumber());
            Assert.assertEquals(available.getAvailableDrivers().size(), availableDrivers.size());

            Assert.assertEquals(available.getOrderIdentifier(), order.getOrderIdentifier());
        } catch (DataAccessLayerException e) {
            throw new ServiceLayerException(e);
        }
    }


    @Test(expected = ServiceLayerException.class)
    public void assignToOrderMissingTruck() throws ServiceLayerException {
        try {
            when(truckDao.findByRegistrationNumber(Mockito.any())).thenReturn(null);
            AvailableToAssignModel model = new AvailableToAssignModel("order", "truck", new ArrayList<>());
            assignmentService.assignDriversAndTruckToOrder(model);
        } catch (DataAccessLayerException e) {
            throw new ServiceLayerException(e);
        }
    }

    @Test(expected = ServiceLayerException.class)
    public void assignToOrderBusyTruck() throws ServiceLayerException {
        try {
            TruckEntity truck = new TruckEntity(1, "xx11111", 1000, TruckStatus.INTACT);
            DriverEntity driver = new DriverEntity("dd", "dd", "dd");
            DriverStatusEntity status = new DriverStatusEntity(driver);
            List<DriverStatusEntity> driverStatusEntities = new ArrayList<>();
            driverStatusEntities.add(status);
            truck.setDriverStatusEntities(driverStatusEntities);

            when(truckDao.findByRegistrationNumber(Mockito.any())).thenReturn(truck);
            AvailableToAssignModel model = new AvailableToAssignModel("order", truck.getRegistrationNumber(), new ArrayList<>());
            assignmentService.assignDriversAndTruckToOrder(model);
        } catch (DataAccessLayerException e) {
            throw new ServiceLayerException(e);
        }
    }

    @Test(expected = ServiceLayerException.class)
    public void assignToOrderInsufficientCapacity() throws ServiceLayerException {
        try {
            TruckEntity truck = new TruckEntity(1, "xx11111", 1000, TruckStatus.INTACT);
            truck.setDriverStatusEntities(new ArrayList<>());

            OrderEntity orderEntity = new OrderEntity("order");
            CargoEntity cargoEntity = new CargoEntity("bricks", 2000, CargoStatus.PREPARED, orderEntity, "bricks,2000");
            List<CargoEntity> cargoes = new ArrayList<>();
            cargoes.add(cargoEntity);
            orderEntity.setCargoEntities(cargoes);

            when(truckDao.findByRegistrationNumber(Mockito.any())).thenReturn(truck);
            when(orderDao.findByOrderIdentifier(Mockito.any())).thenReturn(orderEntity);
            AvailableToAssignModel model = new AvailableToAssignModel(
                    orderEntity.getOrderIdentifier(),
                    truck.getRegistrationNumber(),
                    new ArrayList<>());
            assignmentService.assignDriversAndTruckToOrder(model);
        } catch (DataAccessLayerException e) {
            throw new ServiceLayerException(e);
        }
    }

    @Test(expected = ServiceLayerException.class)
    public void assignToOrderInsufficientCrew() throws ServiceLayerException {
        try {
            TruckEntity truck = new TruckEntity(2, "xx11111", 3000, TruckStatus.INTACT);
            truck.setDriverStatusEntities(new ArrayList<>());

            OrderEntity orderEntity = new OrderEntity("order");
            CargoEntity cargoEntity = new CargoEntity("bricks", 2000, CargoStatus.PREPARED, orderEntity, "bricks,2000");
            List<CargoEntity> cargoes = new ArrayList<>();
            cargoes.add(cargoEntity);
            orderEntity.setCargoEntities(cargoes);

            when(truckDao.findByRegistrationNumber(Mockito.any())).thenReturn(truck);
            when(orderDao.findByOrderIdentifier(Mockito.any())).thenReturn(orderEntity);
            AvailableToAssignModel model = new AvailableToAssignModel(
                    orderEntity.getOrderIdentifier(),
                    truck.getRegistrationNumber(),
                    new ArrayList<>());

            List<DriverEntity> drivers = new ArrayList<>();
            DriverEntity driver1 = new DriverEntity("d1", "d1", "d1");
            DriverStatusEntity status1 = new DriverStatusEntity(driver1);
            status1.setStatus(DriverStatus.UNASSIGNED);
            driver1.setDriverStatusEntity(status1);
            DriverEntity driver2 = new DriverEntity("d2", "d2", "d2");
            DriverStatusEntity status2 = new DriverStatusEntity(driver2);
            status1.setStatus(DriverStatus.PRIMARY);
            driver2.setDriverStatusEntity(status2);
            drivers.add(driver1);
            drivers.add(driver2);

            model.setChosenDriverPersonalNumbers(drivers.stream()
                    .map(d -> d.getPersonalNumber()).collect(Collectors.toList()));

            when(driverDao.findByPersonalNumber(driver1.getPersonalNumber())).thenReturn(driver1);
            when(driverDao.findByPersonalNumber(driver2.getPersonalNumber())).thenReturn(driver2);

            assignmentService.assignDriversAndTruckToOrder(model);
        } catch (DataAccessLayerException e) {
            throw new ServiceLayerException(e);
        }
    }


    @Test
    public void assignToOrder() throws ServiceLayerException {
        try {
            TruckEntity truck = new TruckEntity(2, "xx11111", 3000, TruckStatus.INTACT);
            truck.setDriverStatusEntities(new ArrayList<>());

            OrderEntity orderEntity = new OrderEntity("order");
            CargoEntity cargoEntity = new CargoEntity("bricks", 2000, CargoStatus.PREPARED, orderEntity, "bricks,2000");
            List<CargoEntity> cargoes = new ArrayList<>();
            cargoes.add(cargoEntity);
            orderEntity.setCargoEntities(cargoes);

            when(truckDao.findByRegistrationNumber(Mockito.any())).thenReturn(truck);
            when(orderDao.findByOrderIdentifier(Mockito.any())).thenReturn(orderEntity);
            AvailableToAssignModel model = new AvailableToAssignModel(
                    orderEntity.getOrderIdentifier(),
                    truck.getRegistrationNumber(),
                    new ArrayList<>());

            List<DriverEntity> drivers = new ArrayList<>();
            DriverEntity driver1 = new DriverEntity("d1", "d1", "d1");
            DriverStatusEntity status1 = new DriverStatusEntity(driver1);
            status1.setStatus(DriverStatus.UNASSIGNED);
            driver1.setDriverStatusEntity(status1);
            DriverEntity driver2 = new DriverEntity("d2", "d2", "d2");
            DriverStatusEntity status2 = new DriverStatusEntity(driver2);
            status1.setStatus(DriverStatus.UNASSIGNED);
            driver2.setDriverStatusEntity(status2);
            drivers.add(driver1);
            drivers.add(driver2);

            model.setChosenDriverPersonalNumbers(drivers.stream()
                    .map(d -> d.getPersonalNumber()).collect(Collectors.toList()));

            when(driverDao.findByPersonalNumber(driver1.getPersonalNumber())).thenReturn(driver1);
            when(driverDao.findByPersonalNumber(driver2.getPersonalNumber())).thenReturn(driver2);

            assignmentService.assignDriversAndTruckToOrder(model);

            InOrder inOrder = inOrder(driverDao, truckDao, orderDao, driverStatusDao);

            inOrder.verify(truckDao).findByRegistrationNumber(Mockito.any());
            inOrder.verify(orderDao).findByOrderIdentifier(Mockito.any());
            inOrder.verify(driverDao, times(2)).findByPersonalNumber(Mockito.any());
            inOrder.verify(orderDao).update(Mockito.any());
            inOrder.verify(driverStatusDao, times(2)).update(Mockito.any());
        } catch (DataAccessLayerException e) {
            throw new ServiceLayerException(e);
        }
    }
}
