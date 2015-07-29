package ru.tsystems.shalamov.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.entities.OrderEntity;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.entities.statuses.OrderStatus;
import ru.tsystems.shalamov.services.DriverAssignment;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.DriverAssignmentService;
import ru.tsystems.shalamov.services.api.DriverManagementService;
import ru.tsystems.shalamov.services.api.OrderManagementService;
import ru.tsystems.shalamov.services.api.TruckManagementService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by viacheslav on 10.07.2015.
 */
@Controller
public class AssignmentServlet {

    @Autowired
    private DriverAssignmentService driverAssignmentService;
    @Autowired
    private OrderManagementService orderManagementService;
    @Autowired
    private TruckManagementService truckManagementService;
    @Autowired
    private DriverManagementService driverManagementService;

    private static final Logger LOG = Logger.getLogger(AssignmentServlet.class);

    @RequestMapping(value = "/secure/showAssignments", method = RequestMethod.GET)
    public ModelAndView showAssignments(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<DriverAssignment> assignments = driverAssignmentService.findAllDriverAssignments();
            ModelAndView mav = new ModelAndView("secure/AssignmentsList");
            mav.addObject("assignments", assignments);
            return mav;
        } catch (ServiceLayerException e) {
            LOG.warn(e);
            return Util.fail(response, "fail to edit driver", e.getMessage());
        }
    }

    @RequestMapping(value = "/secure/constructAssignment", method = RequestMethod.POST)
    public ModelAndView constructAssignment(HttpServletRequest request, HttpServletResponse response) {
        try {
            String orderIdentifier = request.getParameter("orderIdentifier");
            if (orderIdentifier == null || orderIdentifier.isEmpty()) {
                return Util.fail(response, "Unable to assign.", "Empty Order Identifier.");
            }
            OrderEntity order = orderManagementService.findOrderByOrderIdentifier(orderIdentifier);

            if (!order.getStatus().equals(OrderStatus.UNASSIGNED)) {
                return Util.fail(response, "Unable to assign.", "Already assigned.");
            }

            List<TruckEntity> availableTrucks = orderManagementService.findTrucksForOrder(order);
            List<DriverEntity> availableDrivers = orderManagementService.findDriversForOrder(order);

            availableTrucks = availableTrucks.subList(0, Math.min(10, availableTrucks.size()));
            availableDrivers = availableDrivers.subList(0, Math.min(10, availableDrivers.size()));

            ModelAndView mav = new ModelAndView("secure/constructAssignment");

            mav.addObject("trucks", availableTrucks);
            mav.addObject("drivers", availableDrivers);
            mav.addObject("orderIdentifier", orderIdentifier);
            return mav;
        } catch (ServiceLayerException e) {
            LOG.warn(e);
            return Util.fail(response, "unable to construct assignment", e.getMessage());
        }
    }

    @RequestMapping(value = "/secure/assign", method = RequestMethod.POST)
    public ModelAndView assign(HttpServletRequest request, HttpServletResponse response) {
        try {
            String orderIdentifier = request.getParameter("orderIdentifier");
            if (orderIdentifier == null || orderIdentifier.isEmpty()) {
                return Util.fail(response, "fail to assign", "empty order identifier");
            }

            String[] driversPN = request.getParameterValues("driver");
            String truckRN = request.getParameter("truck");

            if (driversPN == null || driversPN.length == 0) {
                return Util.fail(response, "fail to assign", "no drivers provided");
            }

            if (truckRN == null || truckRN.isEmpty()) {
                LOG.debug("NOo truck provided.");
                return Util.fail(response, "fail to assign", "no truck provided");
            }

            int availableCrew = driversPN.length;

            TruckEntity truck = truckManagementService.findTruckByRegistrationNumber(truckRN);
            if (truck == null) {
                LOG.warn("invalid truck provided for assignment: " + truckRN);
                return Util.fail(response, "unable to assign", "no such truck");
            }

            int requiredCrewSize = truck.getCrewSize();
            if (requiredCrewSize > availableCrew) {
                LOG.debug("Insufficient crew for truck provided: " +
                        requiredCrewSize + " vs " + truck.getCrewSize());
                return Util.fail(response, "unable to assign", "Need more drivers for the truck.");
            }

            OrderEntity order = orderManagementService.findOrderByOrderIdentifier(orderIdentifier);
            if (truck.getCapacity() < order.getTotalweight()) {
                LOG.warn("insufficient truck capacity: " + truck.getCapacity()
                        + " vs " + order.getTotalweight());
                return Util.fail(response, "unable to assign", "insufficient capacity");
            }

            List<DriverEntity> drivers = new ArrayList<>(requiredCrewSize);

            for (int i = 0; i < requiredCrewSize; ++i) {
                DriverEntity driver = driverManagementService.findDriverByPersonalNumber(driversPN[i]);
                drivers.add(driver);
            }
            orderManagementService.assignDriversAndTruckToOrder(drivers, truck, order);

            return new ModelAndView("redirect:/secure/showAssignments");
        } catch (ServiceLayerException e) {
            LOG.warn(e);
            return Util.fail(response, "unable to show assignments", e.getMessage());
        }
    }
}
