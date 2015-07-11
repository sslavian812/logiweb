package ru.tsystems.shalamov.web;

import ru.tsystems.shalamov.ApplicationContext;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.entities.OrderEntity;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.services.DriverAssignment;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.DriverAssignmentService;
import ru.tsystems.shalamov.services.api.DriverManagementService;
import ru.tsystems.shalamov.services.api.OrderManagementService;
import ru.tsystems.shalamov.services.api.TruckManagementService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by viacheslav on 10.07.2015.
 */
public class AssignmentServlet extends HttpServlet {

    DriverAssignmentService driverAssignmentService;
    OrderManagementService orderManagementService;
    TruckManagementService truckManagementService;
    DriverManagementService driverManagementService;

    @Override
    public void init() throws ServletException {
        driverAssignmentService = ApplicationContext.getInstance().getDriverAssignmentService();
        orderManagementService = ApplicationContext.getInstance().getOrderManagementService();
        truckManagementService = ApplicationContext.getInstance().getTruckManagementService();
        driverManagementService = ApplicationContext.getInstance().getDriverManagementService();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<DriverAssignment> assignments = driverAssignmentService.findAllDriverAssignments();
            request.setAttribute("assignments", assignments);
            getServletContext().getRequestDispatcher("/secure/assignments.jsp").forward(request, response);
        } catch (ServiceLayerException e) {
            //todo log
            fail(request, response, "fail to list all assignments.", e.getMessage());
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        if (path == null || path.isEmpty()) {
            doGet(request, response);
            return;
        }

        if (path.endsWith("showAssignments")) {
            doGet(request, response);
        }

        if (path.endsWith("constructAssignment")) {

            try {
                String orderIdentifier = request.getParameter("orderIdentifier");
                OrderEntity order = orderManagementService.findOrderByOrderIdentifier(orderIdentifier);

                List<TruckEntity> availableTrucks = orderManagementService.findTrucksForOrder(order);
                List<DriverEntity> availableDrivers = orderManagementService.findDriversForOrder(order);

                availableTrucks = availableTrucks.subList(0, Math.min(10, availableTrucks.size()));
                availableDrivers = availableDrivers.subList(0, Math.min(10, availableDrivers.size()));

                request.setAttribute("trucks", availableTrucks);
                request.setAttribute("drivers", availableDrivers);
                request.setAttribute("orderIdentifier", orderIdentifier);
                getServletContext().getRequestDispatcher("/secure/constructAssignment.jsp").forward(request, response);

            } catch (ServiceLayerException e) {
                fail(request, response, "unable to show assignments", e.getMessage());
            }
        }

        if (path.endsWith("assign")) {

            try {
                String orderIdentifier = request.getParameter("orderIdentifier");

                String[] driversPN = request.getParameterValues("driver");
                String truckRN = request.getParameter("truck");

                if (driversPN == null || driversPN.length == 0) {
                    fail(request, response, "fail to assign", "no drivers provided");
                    return;
                }

                if (truckRN == null || truckRN.isEmpty()) {
                    fail(request, response, "fail to assign", "no truck provided");
                    return;
                }

                int availableCrew = driversPN.length;

                TruckEntity truck = truckManagementService.findTruckByRegistrationNumber(truckRN);
                if (truck == null) {
                    fail(request, response, "unable to assign", "no such truck");
                    // todo log
                }

                int currentCrewSize = truck.getCrewSize();
                if (currentCrewSize < availableCrew) {
                    // todo log
                    fail(request, response, "unable to assign", "Need more drivers for thi truck.");
                }

                OrderEntity order = orderManagementService.findOrderByOrderIdentifier(orderIdentifier);
                if (truck.getCapacity() < order.getTotalweight()) {
                    fail(request, response, "unable to assign", "insufficient capacity");
                    // todo log
                }

                List<DriverEntity> drivers = new ArrayList<>(currentCrewSize);
                for (int i = 0; i < driversPN.length; ++i) {
                    DriverEntity driver = driverManagementService.findDriverByPersonalNumber(driversPN[i]);
                    drivers.add(driver);
                }
                orderManagementService.assignDriversAndTruckToOrder(drivers, truck, order);

                doGet(request, response);
            } catch (ServiceLayerException e) {
                fail(request, response, "unable to show assignments", e.getMessage());
            }
        }
    }

    private void fail(HttpServletRequest request, HttpServletResponse response, String message, String cause)
            throws ServletException, IOException {
        request.setAttribute("message", message);
        request.setAttribute("cause", cause);
        getServletContext().getRequestDispatcher("/WEB-INF/views/jsp/fail.jsp").forward(request, response);
    }
}
