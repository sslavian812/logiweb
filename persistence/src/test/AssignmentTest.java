import org.codehaus.plexus.util.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.tsystems.shalamov.entities.CargoEntity;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.entities.OrderEntity;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.entities.statuses.CargoStatus;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;
import ru.tsystems.shalamov.entities.statuses.OrderStatus;
import ru.tsystems.shalamov.entities.statuses.TruckStatus;
import ru.tsystems.shalamov.services.DriverAssignment;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.DriverAssignmentService;
import ru.tsystems.shalamov.services.api.DriverManagementService;
import ru.tsystems.shalamov.services.api.OrderManagementService;
import ru.tsystems.shalamov.services.api.TruckManagementService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by viacheslav on 12.07.2015.
 */
public class AssignmentTest {

    @Autowired
    DriverManagementService driverManagementService;
    @Autowired
    TruckManagementService truckManagementService;
    @Autowired
    OrderManagementService orderManagementService;
    @Autowired
    DriverAssignmentService driverAssignmentService;

    @Test
    public void testBasicUsage() {
        // requires empty database
        try {
            DriverEntity driver1 = new DriverEntity("f1", "l1", "driver1");
            DriverEntity driver2 = new DriverEntity("f2", "l2", "driver2");
            driverManagementService.addDriver(driver1);
            driverManagementService.addDriver(driver2);

            TruckEntity truck = new TruckEntity(2, "xx11111", 5000, TruckStatus.INTACT);
            truckManagementService.addTruck(truck);

            OrderEntity order = new OrderEntity("bricks");
            CargoEntity cargo1 = new CargoEntity("bricks", 1500, CargoStatus.PREPARED, order);
            CargoEntity cargo2 = new CargoEntity("wood", 500, CargoStatus.PREPARED, order);
            List<CargoEntity> cargoes = new ArrayList<>(2);
            cargoes.add(cargo1);
            cargoes.add(cargo2);
            orderManagementService.createOrderWithCargoes(order, cargoes);

            List<DriverEntity> drivers = orderManagementService.findDriversForOrder(order);
            TruckEntity truckEntity = orderManagementService.findTrucksForOrder(order).get(0);

            orderManagementService.assignDriversAndTruckToOrder(drivers, truckEntity, order);

            DriverAssignment assignment = driverAssignmentService
                    .findDriverAssignmentByOrderIdentifier(order.getOrderIdentifier());

            DriverAssignment assignmentByDriver1 = driverAssignmentService
                    .findDriverAssignmentByPersonalNumber(driver1.getPersonalNumber());
            DriverAssignment assignmentByDriver2 = driverAssignmentService
                    .findDriverAssignmentByPersonalNumber(driver2.getPersonalNumber());

            Assert.assertNotNull(assignment);
            Assert.assertNotNull(assignmentByDriver1);
            Assert.assertNotNull(assignmentByDriver2);
            Assert.assertEquals(
                    CollectionUtils.intersection(
                            CollectionUtils.intersection(
                                    assignment.getCoDrivers(),
                                    assignmentByDriver1.getCoDrivers()),
                            assignmentByDriver2.getCoDrivers()).size(),
                    2);

            // update:
            driver1 = driverManagementService.findDriverByPersonalNumber(driver1.getPersonalNumber());
            driver2 = driverManagementService.findDriverByPersonalNumber(driver2.getPersonalNumber());
            order = orderManagementService.findOrderByOrderIdentifier(order.getOrderIdentifier());
            truck = truckManagementService.findTruckByRegistrationNumber(truck.getRegistrationNumber());

            //check both drivers are assigned:
            Assert.assertNotEquals(driver1.getDriverStatusEntity().getStatus(), DriverStatus.REST);
            Assert.assertNotEquals(driver2.getDriverStatusEntity().getStatus(), DriverStatus.REST);

            //check one is PRIMARY and the other is AUXILIARY
            Assert.assertNotEquals(driver1.getDriverStatusEntity().getStatus(),
                    driver2.getDriverStatusEntity().getStatus());

            Assert.assertEquals(order.getStatus(), OrderStatus.IN_PROGRESS);

            Assert.assertTrue(order.getTruckEntity().equals(truck));

        } catch (ServiceLayerException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
