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
    private final String password = "abacaba";

    DriverManagementService driverManagementService;

    public void init() throws ServletException {
        driverManagementService = ApplicationContext.getInstance().getDriverManagementService();
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
                Cookie userName = new Cookie("user", lg);
                userName.setMaxAge(60 * 60);
                response.addCookie(userName);
                getServletContext().getRequestDispatcher("/WEB-INF/views/jsp/manager.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "fail to login as manager");
                getServletContext().getRequestDispatcher("/WEB-INF/views/jsp/fail.jsp").forward(request, response);
            }
        } else {
            RequestDispatcher rd;

            boolean existsDriver = false;

            try {
                existsDriver = driverManagementService.checkDriverExistence(lg);
                // either existsDriver indicates driver existence or remains false in case of error
            } catch (ServiceLayerException e) {
                // todo distinct such case with absence of drivers case;
            }

            if (!existsDriver) {
                request.setAttribute("message", "no driver found with personal number " + lg);
                rd = getServletContext().getRequestDispatcher("/fail.jsp");
            } else {
                rd = getServletContext().getRequestDispatcher("/driver.jsp");
                DriverAssignmentService driverAssignmentService = ApplicationContext.getInstance().getDriverAssignmentService();
                DriverAssignment assignment;
                try {
                    assignment = driverAssignmentService.getDriverAssignmentByPersonalNumber(lg);
                } catch (ServiceLayerException e) {
                    assignment = null;
                }

                if (!checkAssignment(assignment)) {
                    request.setAttribute("message", "no assignments found for driver " + lg);
                    rd = getServletContext().getRequestDispatcher("/fail.jsp");
                }
                setAllData(request, assignment);
            }
            rd.include(request, response);
        }
    }

    private boolean checkAssignment(DriverAssignment assignment) {
        if (assignment == null)
            return false;
        if (assignment.getOrderIdentifier() == null || assignment.getOrderIdentifier().isEmpty())
            return false;
        if (assignment.getTruckRegistrationNumber() == null || assignment.getTruckRegistrationNumber().isEmpty())
            return false;
        return true;
    }

    private void setAllData(HttpServletRequest request, DriverAssignment assignment) {
        request.setAttribute("driver", assignment.getDriverPersonalNumber());
        request.setAttribute("truck", assignment.getTruckRegistrationNumber());
        request.setAttribute("order", assignment.getOrderIdentifier());

        request.setAttribute("codrivers", assignment.getCoDrivers());
        request.setAttribute("cargos", assignment.getCargos());
    }

    public void destroy() {
        // do nothing.
    }
}
