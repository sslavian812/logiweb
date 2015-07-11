package ru.tsystems.shalamov.web;

import ru.tsystems.shalamov.ApplicationContext;
import ru.tsystems.shalamov.services.DriverAssignment;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.DriverAssignmentService;
import ru.tsystems.shalamov.services.api.DriverManagementService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by viacheslav on 04.07.2015.
 */
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final String managerID = "manager";
    private final String driverID = "driver";
    private final String password = "abacaba";

    DriverManagementService driverManagementService;
    DriverAssignmentService driverAssignmentService;

    public void init() throws ServletException {
        driverManagementService = ApplicationContext.getInstance().getDriverManagementService();
        driverAssignmentService = ApplicationContext.getInstance().getDriverAssignmentService();
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) {
        response.setContentType("text/html");
        try {
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            //todo log
            e.printStackTrace();
        }

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        String lg = request.getParameter("login");
        String pw = request.getParameter("password");


        if (managerID.equals(lg)) {
            if (password.equals(pw)) {

                HttpSession session = request.getSession(true);
                session.setAttribute(ApplicationContext.ROLE, ApplicationContext.ROLE_MANAGER);

                //setting session to expiry in 60 mins
                session.setMaxInactiveInterval(60 * 60);
                Cookie userName = new Cookie(ApplicationContext.ROLE, ApplicationContext.ROLE_MANAGER);
                userName.setMaxAge(60 * 60);
                response.addCookie(userName);
                getServletContext().getRequestDispatcher("/secure/manager.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "fail to login as manager");
                getServletContext().getRequestDispatcher("/WEB-INF/views/jsp/fail.jsp").forward(request, response);
            }
        } else if (driverID.equals(lg)) {

            try {
                if (!driverManagementService.checkDriverExistence(pw)) {
                    fail(request, response, "fail to get information about driver", "no driver with personal number " + pw);
                }
                DriverAssignment assignment = driverAssignmentService.getDriverAssignmentByPersonalNumber(pw);
                if(assignment == null)
                {
                    fail(request, response, "fail to get information", "not found");
                }
                request.setAttribute("assignment", assignment);
                getServletContext().getRequestDispatcher("/driver.jsp").forward(request, response);
            } catch (ServiceLayerException e) {
                fail(request, response, "fail to get information", e.getMessage());
            }
        }
    }

//    private boolean checkAssignment(DriverAssignment assignment) {
//        if (assignment == null)
//            return false;
//        if (assignment.getOrderIdentifier() == null || assignment.getOrderIdentifier().isEmpty())
//            return false;
//        if (assignment.getTruckRegistrationNumber() == null || assignment.getTruckRegistrationNumber().isEmpty())
//            return false;
//        return true;
//    }

//    private void setAllData(HttpServletRequest request, DriverAssignment assignment) {
//        request.setAttribute("driver", assignment.getDriverPersonalNumber());
//        request.setAttribute("truck", assignment.getTruckRegistrationNumber());
//        request.setAttribute("order", assignment.getOrderIdentifier());
//
//        request.setAttribute("codrivers", assignment.getCoDrivers());
//        request.setAttribute("cargos", assignment.getCargos());
//    }


    private void fail(HttpServletRequest request, HttpServletResponse response, String message, String cause)
            throws ServletException, IOException {
        request.setAttribute("message", message);
        request.setAttribute("cause", cause);
        getServletContext().getRequestDispatcher("/WEB-INF/views/jsp/fail.jsp").forward(request, response);
    }
}
