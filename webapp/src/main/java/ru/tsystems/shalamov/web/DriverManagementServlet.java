package ru.tsystems.shalamov.web;

import ru.tsystems.shalamov.ApplicationContext;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.DriverManagementService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by viacheslav on 09.07.2015.
 */
public class DriverManagementServlet extends HttpServlet {

    DriverManagementService driverManagementService;

    @Override
    public void init() throws ServletException {
        driverManagementService = ApplicationContext.getInstance().getDriverManagementService();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<DriverEntity> drivers = driverManagementService.listDrivers();
            request.setAttribute("drivers", drivers);
            getServletContext().getRequestDispatcher("/secure/drivers.jsp").forward(request, response);
        } catch (ServiceLayerException e) {
            //todo log
            request.setAttribute("message", "fail list all drivers.");
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

        if (path.endsWith("showDrivers")) {
            doGet(request, response);
        }

        if (path.endsWith("addDriver")) {
            String first = request.getParameter("firstName");
            String last = request.getParameter("lastName");
            String personal = request.getParameter("personalNumber");

            try {
                driverManagementService.addDriver(new DriverEntity(first, last, personal));
                doGet(request, response);
            } catch (ServiceLayerException e) {
                //todo log!!!!
                request.setAttribute("message", "fail to add driver " + personal);
                getServletContext().getRequestDispatcher("/WEB-INF/views/jsp/fail.jsp").forward(request, response);
            }
        }

        if (path.endsWith("deleteDriver")) {
            String personalNumber = request.getParameter("driver");
            try {
                driverManagementService.deleteDriverByPersonalNumber(personalNumber);
                doGet(request, response);
            } catch (ServiceLayerException e) {
                //todo log!!!!
                request.setAttribute("message", "fail to delete driver " + personalNumber);
                getServletContext().getRequestDispatcher("/WEB-INF/views/jsp/fail.jsp").forward(request, response);
            }

            response.sendRedirect("/secure/drivers.jsp");
        }
    }

}
