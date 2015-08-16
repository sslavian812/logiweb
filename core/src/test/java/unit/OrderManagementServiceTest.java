package unit;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.CargoDao;
import ru.tsystems.shalamov.dao.api.OrderDao;
import ru.tsystems.shalamov.entities.CargoEntity;
import ru.tsystems.shalamov.entities.OrderEntity;
import ru.tsystems.shalamov.entities.statuses.CargoStatus;
import ru.tsystems.shalamov.entities.statuses.OrderStatus;
import ru.tsystems.shalamov.model.CargoModel;
import ru.tsystems.shalamov.model.OrderModel;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.OrderManagementService;
import ru.tsystems.shalamov.services.impl.OrderManagementServiceImpl;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by viacheslav on 06.08.2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class OrderManagementServiceTest {

    OrderManagementService orderManagementService;

    @Mock
    private OrderDao orderDao;

    @Mock
    private CargoDao cargoDao;

    @Before
    public void setup() {
        orderManagementService =
                new OrderManagementServiceImpl(orderDao, cargoDao);
    }

    @Test
    public void testCreateFindAll() {
        try {
            doNothing().when(orderDao).create(Mockito.any(OrderEntity.class));
            when(orderDao.findByOrderIdentifier(Mockito.any(String.class))).thenReturn(null);

            Set<OrderEntity> orders = new HashSet<>();

            OrderEntity orderEntity1 = new OrderEntity("myOrder1");
            CargoEntity cargoEntity1 = new CargoEntity("", 1, CargoStatus.PREPARED, orderEntity1, "cargo1");
            orderEntity1.setCargoEntities(Arrays.asList(cargoEntity1));
            OrderEntity orderEntity2 = new OrderEntity("myOrder2");
            CargoEntity cargoEntity2 = new CargoEntity("", 1, CargoStatus.PREPARED, orderEntity2, "cargo2");
            orderEntity2.setCargoEntities(Arrays.asList(cargoEntity2));

            orders.add(orderEntity1);
            orders.add(orderEntity2);


            List<OrderModel> orderModelList = orders.stream()
                    .map(d -> new OrderModel(d))
                    .collect(Collectors.toList());

            when(orderDao.findAll()).thenReturn(orders.stream()
                    .collect(Collectors.toList()));

            for (OrderEntity d : orders) {
                orderManagementService.createOrderWithCargoes(new OrderModel(d),
                        Arrays.asList(new CargoModel(d.getCargoEntities().get(0))));
            }

            List<OrderModel> received = orderManagementService.findAllOrders();

            received.removeAll(orderModelList);
            Assert.assertEquals(received.size(), 0);

        } catch (ServiceLayerException | DataAccessLayerException e) {
            Assert.fail();
        }
    }

    @Test
    public void testCreateFindByOI() {
        try {
            doNothing().when(orderDao).create(Mockito.any(OrderEntity.class));
            doNothing().when(cargoDao).create(Mockito.any(CargoEntity.class));
            when(orderDao.findByOrderIdentifier(Mockito.any(String.class))).thenReturn(null);

            OrderEntity orderEntity = new OrderEntity("myOrder1");
            List<CargoModel> cargoes = new ArrayList<>();
            CargoModel cargoModel = new CargoModel("den", 1, CargoStatus.PREPARED, orderEntity.getOrderIdentifier());
            cargoes.add(cargoModel);

            orderManagementService.createOrderWithCargoes(new OrderModel(orderEntity), cargoes);
            when(orderDao.findByOrderIdentifier(Mockito.any(String.class))).thenReturn(orderEntity);

            OrderModel orderModel = orderManagementService
                    .findOrderModelByOrderIdentifier(orderEntity.getOrderIdentifier());

            Assert.assertEquals(orderModel, new OrderModel(orderEntity));
        } catch (ServiceLayerException | DataAccessLayerException e) {
            Assert.fail();
        }
    }


    @Test
    public void testAddUpdateFindByOI() {
        try {
            doNothing().when(orderDao).create(Mockito.any(OrderEntity.class));
            doNothing().when(orderDao).update(Mockito.any(OrderEntity.class));
            when(orderDao.findByOrderIdentifier(Mockito.any(String.class))).thenReturn(null);

            OrderEntity orderEntity1 = new OrderEntity("myOrder1");
            CargoEntity cargoEntity1 = new CargoEntity("", 1, CargoStatus.PREPARED, orderEntity1, "cargo1");
            orderManagementService.createOrderWithCargoes(new OrderModel(orderEntity1),Arrays.asList(new CargoModel(cargoEntity1)));
            when(orderDao.findByOrderIdentifier(Mockito.any(String.class))).thenReturn(orderEntity1);

            String oldOI = orderEntity1.getOrderIdentifier();
            orderEntity1.setStatus(OrderStatus.COMPLETED);

            orderManagementService.updateOrder(new OrderModel(orderEntity1), oldOI);

            when(orderDao.findByOrderIdentifier(Mockito.any(String.class))).thenReturn(orderEntity1);

            Assert.assertEquals(orderManagementService
                            .findOrderModelByOrderIdentifier(
                                    orderEntity1.getOrderIdentifier()),
                    new OrderModel(orderEntity1));

        } catch (ServiceLayerException | DataAccessLayerException e) {
            Assert.fail();
        }
    }

    @Test(expected = ServiceLayerException.class)
    public void testAlreadyInUse() throws ServiceLayerException {
        try {
            doNothing().when(orderDao).create(Mockito.any(OrderEntity.class));
            OrderEntity order = new OrderEntity("myOrder1");
            when(orderDao.findByOrderIdentifier(Mockito.any(String.class))).thenReturn(order);

            orderManagementService.createOrderWithCargoes(new OrderModel(order), new ArrayList<>());

        } catch (DataAccessLayerException e) {
            Assert.fail();
        }
    }

    @Test(expected = ServiceLayerException.class)
    public void testUpdateNotExistingOrder() throws ServiceLayerException {
        try {
            doNothing().when(orderDao).update(Mockito.any(OrderEntity.class));
            OrderEntity order = new OrderEntity("myOrder1");
            when(orderDao.findByOrderIdentifier(Mockito.any(String.class))).thenReturn(null);

            orderManagementService.updateOrder(new OrderModel(order), order.getOrderIdentifier());

        } catch (DataAccessLayerException e) {
            Assert.fail();
        }
    }

    @Test(expected = ServiceLayerException.class)
    public void testOICollisionOnUpdate() throws ServiceLayerException {
        try {
            doNothing().when(orderDao).update(Mockito.any(OrderEntity.class));
            OrderEntity order1 = new OrderEntity("myOrder1");
            OrderEntity order2 = new OrderEntity("myOrder2");
            when(orderDao.findByOrderIdentifier(order1.getOrderIdentifier())).thenReturn(order1);
            when(orderDao.findByOrderIdentifier(order2.getOrderIdentifier())).thenReturn(order2);

            // truing to change oi "1" to "2" for order 1
            orderManagementService.updateOrder(new OrderModel(order2), order1.getOrderIdentifier());
        } catch (DataAccessLayerException e) {
            Assert.fail();
        }
    }


    @Test(expected = ServiceLayerException.class)
    public void testDeletingNotExistingOrder() throws ServiceLayerException {
        try {
            doNothing().when(orderDao).delete(Mockito.any(OrderEntity.class));
            OrderEntity order = new OrderEntity("myOrder1");
            when(orderDao.findByOrderIdentifier(Mockito.any(String.class))).thenReturn(null);
            orderManagementService.deleteOrderByOrderIdentifierIfNotAssigned(order.getOrderIdentifier());
        } catch (DataAccessLayerException e) {
            Assert.fail();
        }
    }

    @Test(expected = ServiceLayerException.class)
    public void testDeletingAssignedOrder() throws ServiceLayerException {
        try {
            doNothing().when(orderDao).delete(Mockito.any(OrderEntity.class));

            OrderEntity order = new OrderEntity("myOrder1");
            order.setStatus(OrderStatus.IN_PROGRESS);

            when(orderDao.findByOrderIdentifier(order.getOrderIdentifier())).thenReturn(order);

            orderManagementService.deleteOrderByOrderIdentifierIfNotAssigned(order.getOrderIdentifier());
        } catch (DataAccessLayerException e) {
            Assert.fail();
        }
    }

    @Test
    public void testDeletingOrder() {
        try {
            doNothing().when(orderDao).delete(Mockito.any(OrderEntity.class));

            OrderEntity order = new OrderEntity("myOrder1");
            order.setStatus(OrderStatus.UNASSIGNED);

            when(orderDao.findByOrderIdentifier(order.getOrderIdentifier())).thenReturn(order);

            orderManagementService.deleteOrderByOrderIdentifierIfNotAssigned(order.getOrderIdentifier());
        } catch (ServiceLayerException | DataAccessLayerException e) {
            Assert.fail();
        }
    }

    @Test(expected = ServiceLayerException.class)
    public void testAddCargoToMissingOrder() throws ServiceLayerException{
        try {
            when(orderDao.findByOrderIdentifier(Mockito.anyString())).thenReturn(null);
            orderManagementService.addCargoToOrder("order", new CargoModel("den", 1, CargoStatus.PREPARED, "order"));
        } catch (DataAccessLayerException e) {
            Assert.fail();
        }
    }

    @Test(expected = ServiceLayerException.class)
    public void testAddCargoToAssignedOrder() throws ServiceLayerException{
        try {
            OrderEntity orderEntity = new OrderEntity("order");
            orderEntity.setStatus(OrderStatus.IN_PROGRESS);
            when(orderDao.findByOrderIdentifier(Mockito.anyString())).thenReturn(orderEntity);

            orderManagementService.addCargoToOrder("order", new CargoModel("den", 1, CargoStatus.PREPARED, "order"));

        } catch (DataAccessLayerException e) {
            Assert.fail();
        }
    }


    @Test
    public void testAddCargoToOrder() {
        try {
            doNothing().when(orderDao).update(Mockito.any(OrderEntity.class));
            doNothing().when(cargoDao).create(Mockito.any(CargoEntity.class));

            OrderEntity orderEntity = new OrderEntity("order");
            orderEntity.setCargoEntities(new ArrayList<>());
            when(orderDao.findByOrderIdentifier(Mockito.anyString())).thenReturn(orderEntity);
            CargoModel cargoModel = new CargoModel("den", 1, CargoStatus.PREPARED, "order");
            orderManagementService.addCargoToOrder("order", cargoModel);

            Assert.assertEquals(orderEntity.getCargoEntities().get(0).getCargoIdentifier(), cargoModel.getCargoIdentifier());
        } catch (ServiceLayerException | DataAccessLayerException e) {
            Assert.fail();
        }
    }



    @Test(expected = ServiceLayerException.class)
    public void testDeleteMissingCargo() throws ServiceLayerException{
        try {
            when(cargoDao.findCargoByCargoIdentifier(Mockito.anyString())).thenReturn(null);
            orderManagementService.deleteCargo("cargo");
        } catch (DataAccessLayerException e) {
            Assert.fail();
        }
    }

    @Test(expected = ServiceLayerException.class)
    public void testDeleteCargoAssignedOrder() throws ServiceLayerException{
        try {
            OrderEntity orderEntity = new OrderEntity("order");
            orderEntity.setStatus(OrderStatus.IN_PROGRESS);
            CargoEntity cargoEntity = new CargoEntity("den", 1, CargoStatus.PREPARED, orderEntity, "cargo");
            when(orderDao.findByOrderIdentifier(Mockito.anyString())).thenReturn(orderEntity);
            when(cargoDao.findCargoByCargoIdentifier(Mockito.anyString())).thenReturn(cargoEntity);

            orderManagementService.deleteCargo(cargoEntity.getCargoIdentifier());
        } catch (DataAccessLayerException e) {
            Assert.fail();
        }
    }


    @Test
    public void testDeleteCargo() {
        try {
            doNothing().when(orderDao).update(Mockito.any(OrderEntity.class));
            doNothing().when(cargoDao).delete(Mockito.any(CargoEntity.class));

            OrderEntity orderEntity = new OrderEntity("order");
            CargoEntity cargoEntity = new CargoEntity("den", 1, CargoStatus.PREPARED, orderEntity, "cargo");
            CargoEntity cargoEntity2 = new CargoEntity("den2", 1000, CargoStatus.PREPARED, orderEntity, "cargo");
            orderEntity.setCargoEntities(new ArrayList<>());
            orderEntity.getCargoEntities().add(cargoEntity);
            orderEntity.getCargoEntities().add(cargoEntity2);

            when(orderDao.findByOrderIdentifier(Mockito.anyString())).thenReturn(orderEntity);
            when(cargoDao.findCargoByCargoIdentifier(Mockito.anyString())).thenReturn(cargoEntity);

            orderManagementService.deleteCargo(cargoEntity.getCargoIdentifier());
            Assert.assertTrue(orderEntity.getCargoEntities().size() == 1);
        } catch (ServiceLayerException | DataAccessLayerException e) {
            Assert.fail();
        }
    }


    @Test(expected = ServiceLayerException.class)
    public void testDeleteLastCargo()  throws ServiceLayerException{
        try {
            doNothing().when(orderDao).update(Mockito.any(OrderEntity.class));
            doNothing().when(cargoDao).delete(Mockito.any(CargoEntity.class));

            OrderEntity orderEntity = new OrderEntity("order");
            CargoEntity cargoEntity = new CargoEntity("den", 1, CargoStatus.PREPARED, orderEntity, "cargo");
            CargoEntity cargoEntity2 = new CargoEntity("den2", 1000, CargoStatus.PREPARED, orderEntity, "cargo");
            orderEntity.setCargoEntities(new ArrayList<>());
            orderEntity.getCargoEntities().add(cargoEntity);
            orderEntity.getCargoEntities().add(cargoEntity2);

            when(orderDao.findByOrderIdentifier(Mockito.anyString())).thenReturn(orderEntity);
            when(cargoDao.findCargoByCargoIdentifier(Mockito.anyString())).thenReturn(cargoEntity);

            orderManagementService.deleteCargo(cargoEntity.getCargoIdentifier());
            Assert.assertTrue(orderEntity.getCargoEntities().size() == 1);

            orderManagementService.deleteCargo(cargoEntity2.getCargoIdentifier());
            Assert.assertTrue(orderEntity.getCargoEntities().isEmpty());

        } catch (DataAccessLayerException e) {
            Assert.fail();
        }
    }
}
