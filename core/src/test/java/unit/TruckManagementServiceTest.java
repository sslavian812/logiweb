package unit;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.TruckDao;
import ru.tsystems.shalamov.entities.DriverStatusEntity;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;
import ru.tsystems.shalamov.entities.statuses.TruckStatus;
import ru.tsystems.shalamov.model.TruckModel;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.TruckManagementService;
import ru.tsystems.shalamov.services.impl.TruckManagementServiceImpl;

import java.util.ArrayList;
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
public class TruckManagementServiceTest {

    TruckManagementService truckManagementService;

    @Mock
    private TruckDao truckDao;

    @Before
    public void setup() {
        truckManagementService =
                new TruckManagementServiceImpl(truckDao);
    }

    @Test
    public void testCreateFindAll() {
        try {
            doNothing().when(truckDao).create(Mockito.any(TruckEntity.class));
            when(truckDao.findByRegistrationNumber(Mockito.any(String.class))).thenReturn(null);

            Set<TruckEntity> trucks = new HashSet<>();

            trucks.add(new TruckEntity(1, "xx11111", 1000, TruckStatus.INTACT));
            trucks.add(new TruckEntity(2, "xx22222", 2000, TruckStatus.INTACT));

            List<TruckModel> truckModelList = trucks.stream()
                    .map(d -> new TruckModel(d))
                    .collect(Collectors.toList());

            when(truckDao.findAll()).thenReturn(trucks.stream()
                    .collect(Collectors.toList()));

            for (TruckEntity d : trucks) {
                truckManagementService.addTruck(new TruckModel(d));
            }

            List<TruckModel> received = truckManagementService.findAllTrucks();

            received.removeAll(truckModelList);
            Assert.assertEquals(received.size(), 0);

        } catch (ServiceLayerException | DataAccessLayerException e) {
            Assert.fail();
        }
    }


    @Test
    public void testCreateFindByRN() {
        try {
            doNothing().when(truckDao).create(Mockito.any(TruckEntity.class));
            when(truckDao.findByRegistrationNumber(Mockito.any(String.class))).thenReturn(null);

            TruckEntity truck = new TruckEntity(1, "xx11111", 1000, TruckStatus.INTACT);
            truckManagementService.addTruck(new TruckModel(truck));

            when(truckDao.findByRegistrationNumber(Mockito.any(String.class))).thenReturn(truck);


            Assert.assertEquals(truckManagementService
                            .findTruckModelByRegistrationNumber(
                                    truck.getRegistrationNumber()),
                    new TruckModel(truck));

        } catch (ServiceLayerException | DataAccessLayerException e) {
            Assert.fail();
        }
    }


    @Test
    public void testAddUpdateFindByRN() {
        try {
            doNothing().when(truckDao).create(Mockito.any(TruckEntity.class));
            doNothing().when(truckDao).update(Mockito.any(TruckEntity.class));
            when(truckDao.findByRegistrationNumber(Mockito.any(String.class))).thenReturn(null);

            TruckEntity truck = new TruckEntity(1, "xx11111", 1000, TruckStatus.INTACT);
            truckManagementService.addTruck(new TruckModel(truck));
            when(truckDao.findByRegistrationNumber(Mockito.any(String.class))).thenReturn(truck);

            String oldRN = truck.getRegistrationNumber();
            truck.setCapacity(20000);

            truckManagementService.updateTruck(new TruckModel(truck), oldRN);

            Assert.assertEquals(truckManagementService
                            .findTruckModelByRegistrationNumber(
                                    truck.getRegistrationNumber()),
                    new TruckModel(truck));

        } catch (ServiceLayerException | DataAccessLayerException e) {
            Assert.fail();
        }
    }

    @Test
    public void testUpdateAssigned() {
        try {
            doNothing().when(truckDao).create(Mockito.any(TruckEntity.class));
            doNothing().when(truckDao).update(Mockito.any(TruckEntity.class));
            when(truckDao.findByRegistrationNumber(Mockito.any(String.class))).thenReturn(null);

            TruckEntity truck = new TruckEntity(1, "xx11111", 1000, TruckStatus.ASSIGNED);
            truckManagementService.addTruck(new TruckModel(truck));
            when(truckDao.findByRegistrationNumber(Mockito.any(String.class))).thenReturn(truck);

            String oldRN = truck.getRegistrationNumber();
            truck.setCapacity(20000);

            truckManagementService.updateTruck(new TruckModel(truck), oldRN);

            Assert.assertEquals(truckManagementService
                            .findTruckModelByRegistrationNumber(
                                    truck.getRegistrationNumber()),
                    new TruckModel(truck));

        } catch (ServiceLayerException | DataAccessLayerException e) {
            Assert.fail();
        }
    }

    @Test(expected = ServiceLayerException.class)
    public void testUpdateAssignedTruck() throws ServiceLayerException{
        try {
            doNothing().when(truckDao).create(Mockito.any(TruckEntity.class));
            doNothing().when(truckDao).update(Mockito.any(TruckEntity.class));

            TruckEntity truck = new TruckEntity(1, "xx11111", 1000, TruckStatus.ASSIGNED);
            when(truckDao.findByRegistrationNumber(Mockito.any(String.class))).thenReturn(truck);

            String oldRN = truck.getRegistrationNumber();
            TruckModel truckModel = new TruckModel(truck);
            truckModel.setCapacity(2000);
            truckModel.setStatus(TruckStatus.INTACT);

            truckManagementService.updateTruck(truckModel, oldRN);

            Assert.assertEquals(truckManagementService
                            .findTruckModelByRegistrationNumber(
                                    truck.getRegistrationNumber()),
                    new TruckModel(truck));

        } catch (DataAccessLayerException e) {
            Assert.fail();
        }
    }

    @Test(expected = ServiceLayerException.class)
    public void testAlreadyInUse() throws ServiceLayerException {
        try {
            doNothing().when(truckDao).create(Mockito.any(TruckEntity.class));
            TruckEntity truck = new TruckEntity(1, "xx11111", 1000, TruckStatus.INTACT);
            when(truckDao.findByRegistrationNumber(Mockito.any(String.class))).thenReturn(truck);

            truckManagementService.addTruck(new TruckModel(truck));

        } catch (DataAccessLayerException e) {
            Assert.fail();
        }
    }

    @Test
    public void testFindMissingTruck() throws ServiceLayerException {
        try {
            when(truckDao.findByRegistrationNumber(Mockito.anyString())).thenReturn(null);
            Assert.assertNull(truckManagementService.findTruckModelByRegistrationNumber("truck"));
        } catch (ServiceLayerException | DataAccessLayerException e) {
            Assert.fail();
        }
    }

    @Test(expected = ServiceLayerException.class)
    public void testUpdateNotExistingTruck() throws ServiceLayerException {
        try {
            doNothing().when(truckDao).update(Mockito.any(TruckEntity.class));
            TruckEntity truck = new TruckEntity(1, "xx11111", 1000, TruckStatus.INTACT);
            when(truckDao.findByRegistrationNumber(Mockito.any(String.class))).thenReturn(null);

            truckManagementService.updateTruck(new TruckModel(truck), truck.getRegistrationNumber());

        } catch (DataAccessLayerException e) {
            Assert.fail();
        }
    }

    @Test(expected = ServiceLayerException.class)
    public void testRNCollisionOnUpdate() throws ServiceLayerException {
        try {
            doNothing().when(truckDao).update(Mockito.any(TruckEntity.class));
            TruckEntity truck1 = new TruckEntity(1, "xx11111", 1000, TruckStatus.INTACT);
            TruckEntity truck2 = new TruckEntity(1, "xx22222", 1000, TruckStatus.INTACT);
            when(truckDao.findByRegistrationNumber(truck1.getRegistrationNumber())).thenReturn(truck1);
            when(truckDao.findByRegistrationNumber(truck2.getRegistrationNumber())).thenReturn(truck2);

            // truing to change rn "11111" to "22222" for truck 1
            truckManagementService.updateTruck(new TruckModel(truck2), truck1.getRegistrationNumber());
        } catch (DataAccessLayerException e) {
            Assert.fail();
        }
    }


    @Test(expected = ServiceLayerException.class)
    public void testDeletingNotExistingTruck() throws ServiceLayerException {
        try {
            doNothing().when(truckDao).delete(Mockito.any(TruckEntity.class));
            when(truckDao.findByRegistrationNumber(Mockito.any(String.class))).thenReturn(null);
            TruckEntity truck = new TruckEntity(1, "xx11111", 1000, TruckStatus.INTACT);
            truckManagementService.deleteTruckByRegistrationNumber(truck.getRegistrationNumber());
        } catch (DataAccessLayerException e) {
            Assert.fail();
        }
    }


    @Test(expected = ServiceLayerException.class)
    public void testDeletingBusyTruck() throws ServiceLayerException {
        try {
            doNothing().when(truckDao).delete(Mockito.any(TruckEntity.class));

            TruckEntity truck = new TruckEntity(1, "xx11111", 1000, TruckStatus.INTACT);
            DriverStatusEntity status = new DriverStatusEntity();
            status.setTruckEntity(truck);
            List<DriverStatusEntity> driverStatuses = new ArrayList<>();
            driverStatuses.add(status);
            truck.setDriverStatusEntities(driverStatuses);

            when(truckDao.findByRegistrationNumber(truck.getRegistrationNumber())).thenReturn(truck);

            truckManagementService.deleteTruckByRegistrationNumber(truck.getRegistrationNumber());
        } catch (DataAccessLayerException e) {
            Assert.fail();
        }
    }


    @Test
    public void testDeletingTruck() {
        try {
            doNothing().when(truckDao).delete(Mockito.any(TruckEntity.class));

            TruckEntity truck = new TruckEntity(1, "xx11111", 1000, TruckStatus.INTACT);

            when(truckDao.findByRegistrationNumber(truck.getRegistrationNumber())).thenReturn(truck);

            truckManagementService.deleteTruckByRegistrationNumber(truck.getRegistrationNumber());
        } catch (ServiceLayerException | DataAccessLayerException e) {
            Assert.fail();
        }
    }
}
