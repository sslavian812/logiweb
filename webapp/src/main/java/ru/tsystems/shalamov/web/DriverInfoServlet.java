package ru.tsystems.shalamov.web;

import ru.tsystems.shalamov.ApplicationContext;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.services.DriverAssignment;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.DriverAssignmentService;
import ru.tsystems.shalamov.services.api.DriverManagementService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by viacheslav on 11.07.2015.
 */
public class DriverInfoServlet extends HttpServlet {

    DriverAssignmentService driverAssignmentService;
    DriverManagementService driverManagementService;

    @Override
    public void init() throws ServletException {
        driverAssignmentService = ApplicationContext.getInstance().getDriverAssignmentService();
        driverManagementService = ApplicationContext.getInstance().getDriverManagementService();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String driverPersonalNumber = request.getParameter("driver");
            if (driverPersonalNumber == null)
                fail(request, response, "unable to display driver's information", "no personal Number provided");

            DriverEntity driver = driverManagementService.findDriverByPersonalNumber(driverPersonalNumber);
            if (driver == null)
                fail(request, response, "unable to display driver's information",
                        "no driver with personal number " + driverPersonalNumber);

            DriverAssignment assignment = driverAssignmentService.getDriverAssignmentByPersonalNumber(driverPersonalNumber);

            if (assignment == null) {
                fail(request, response, "unable to display driver's information", "no information found");
            }

            request.setAttribute("assignment", assignment);
            getServletContext().getRequestDispatcher("/driver.jsp").forward(request, response);
        } catch (ServiceLayerException e) {
            fail(request, response, "unable to get driver information", e.getMessage());
        }

    }

//    @Override
//    public void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        //todo setattrigute
//        doGet(request, response);
//    }

    private void fail(HttpServletRequest request, HttpServletResponse response, String message, String cause)
            throws ServletException, IOException {
        request.setAttribute("message", message);
        request.setAttribute("cause", cause);
        getServletContext().getRequestDispatcher("/WEB-INF/views/jsp/fail.jsp").forward(request, response);
    }
}
