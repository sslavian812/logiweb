package ru.tsystems.shalamov.web;

import ru.tsystems.shalamov.ApplicationContext;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.entities.statuses.TruckStatus;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.TruckManagementService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by viacheslav on 10.07.2015.
 */
public class TruckManagementServlet extends HttpServlet {

    TruckManagementService truckManagementService;

    @Override
    public void init() throws ServletException {
        truckManagementService = ApplicationContext.getInstance().getTruckManagementService();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<TruckEntity> trucks = truckManagementService.getAllTrucks();
            request.setAttribute("trucks", trucks);
            getServletContext().getRequestDispatcher("/secure/trucks.jsp").forward(request, response);
        } catch (ServiceLayerException e) {
            //todo log
            fail(request, response, "fail list all trucks", e.getMessage());
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

        if (path.endsWith("showTrucks")) {
            doGet(request, response);
        }

        if (path.endsWith("addTruck")) {

            int crewSize = Integer.parseInt(request.getParameter("crewSize"));
            String registrationNumber = request.getParameter("registrationNumber");
            int capacity = Integer.parseInt(request.getParameter("capacity"));

            try {
                truckManagementService.addTruck(new TruckEntity(crewSize, registrationNumber, capacity, TruckStatus.INTACT));
            } catch (ServiceLayerException e) {
                //todo log!!!!
                fail(request, response, "fail to add truck ", e.getMessage());
            }
            doGet(request, response);
        }

        if (path.endsWith("deleteTruck")) {
            String registrationNumber = request.getParameter("registrationNumber");
            try {
                truckManagementService.deleteTruckByRegistrationNumber(registrationNumber);
            } catch (ServiceLayerException e) {
                //todo log!!!!
                fail(request, response, "fail to delete truck " + registrationNumber, e.getMessage());
            }
            response.sendRedirect("/secure/trucks.jsp");
            doGet(request, response);
        }
    }

    private void fail(HttpServletRequest request, HttpServletResponse response, String message, String cause)
            throws ServletException, IOException {
        request.setAttribute("message", message);
        request.setAttribute("cause", cause);
        getServletContext().getRequestDispatcher("/WEB-INF/views/jsp/fail.jsp").forward(request, response);
    }
}
