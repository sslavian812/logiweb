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
            return;
        }

        if (path.endsWith("showDrivers")) {
            doGet(request, response);
        }

        String personalNumber = request.getParameter("personalNumber");

        if (path.endsWith("addDriver")) {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");

            try {
                driverManagementService.addDriver(new DriverEntity(firstName, lastName, personalNumber));
            } catch (ServiceLayerException e) {
                fail(request, response, "fail to add driver " + personalNumber, e.getMessage());
            }
            doGet(request, response);
        }

        if (path.endsWith("deleteDriver")) {
            try {
                driverManagementService.deleteDriverByPersonalNumber(personalNumber);
            } catch (ServiceLayerException e) {
                fail(request, response, "fail to delete driver ", e.getMessage());
            }
            doGet(request, response);
        }

        if (path.endsWith("editDriver")) {
            try {
                DriverEntity driver = driverManagementService.findDriverByPersonalNumber(personalNumber);
                if (driver == null) {
                    fail(request, response, "fail to edit driver", "no such driver");
                    return;
                }
                request.setAttribute("driver", driver);
                getServletContext().getRequestDispatcher("/secure/driverEdit.jsp").forward(request, response);
            } catch (ServiceLayerException e) {
                fail(request, response, "fail to edit driver", e.getMessage());
            }
        }

        if (path.endsWith("updateDriver")) {
            try {
                String oldPersonalNumber = request.getParameter("oldPersonalNumber");
                String firstName = request.getParameter("firstName");
                String lastName = request.getParameter("lastName");

                DriverEntity driver = driverManagementService.findDriverByPersonalNumber(oldPersonalNumber);
                if (driver == null) {
                    fail(request, response, "fail to edit driver", "no such driver");
                    return;
                }

                if ((!oldPersonalNumber.equals(personalNumber))
                        && driverManagementService.checkDriverExistence(personalNumber)) {
                    fail(request, response, "fail to update driver",
                            "driver with new personal number already exists");
                    return;
                }

                driver.setFirstName(firstName);
                driver.setLastName(lastName);
                driver.setPersonalNumber(personalNumber);
                driverManagementService.updateDriver(driver);
            } catch (ServiceLayerException e) {
                fail(request, response, "fail to edit driver", e.getMessage());
            }
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
