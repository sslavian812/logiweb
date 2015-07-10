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
            request.setAttribute("message", "fail list all trucks.");
            getServletContext().getRequestDispatcher("/WEB-INF/views/jsp/fail.jsp").forward(request, response);
        }
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path == null || path.isEmpty()) {
            doGet(request, response);
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
                doGet(request, response);
            } catch (ServiceLayerException e) {
                //todo log!!!!
                request.setAttribute("message", "fail to add truck " + registrationNumber);
                getServletContext().getRequestDispatcher("/WEB-INF/views/jsp/fail.jsp").forward(request, response);
            }
        }

        if (path.endsWith("deleteTruck")) {
            String registrationNumber = request.getParameter("registrationNumber");
            try {
                truckManagementService.deleteTruckByRegistrationNumber(registrationNumber);
                doGet(request, response);
            } catch (ServiceLayerException e) {
                //todo log!!!!
                request.setAttribute("message", "fail to delete truck " + registrationNumber);
                getServletContext().getRequestDispatcher("/WEB-INF/views/jsp/fail.jsp").forward(request, response);
            }

            response.sendRedirect("/secure/trucks.jsp");
        }
    }

}
