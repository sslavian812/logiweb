package ru.tsystems.shalamov.web;

import ru.tsystems.shalamov.ApplicationContext;
import ru.tsystems.shalamov.services.DriverAssignment;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.DriverAssignmentService;
import ru.tsystems.shalamov.services.api.DriverManagementService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by viacheslav on 04.07.2015.
 */
public class LoginServlet extends HttpServlet {

    private static final String PASSWORD = "abacaba";

    private DriverManagementService driverManagementService;
    private DriverAssignmentService driverAssignmentService;

    public void init() throws ServletException {
        driverManagementService = ApplicationContext.getInstance().getDriverManagementService();
        driverAssignmentService = ApplicationContext.getInstance().getDriverAssignmentService();
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        String lg = request.getParameter("login");
        String pw = request.getParameter("password");

        if (lg == null || pw == null) {
            fail(request, response, "fail to log in", "incorrect login or password");
            return;
        }


        if (lg.equals(ApplicationContext.ROLE_MANAGER)) {
            if (PASSWORD.equals(pw)) {

                HttpSession session = request.getSession(true);
                session.setAttribute(ApplicationContext.ROLE, ApplicationContext.ROLE_MANAGER);

                //setting session to expiry in 60 mins
                session.setMaxInactiveInterval(60 * 60);
                Cookie userName = new Cookie(ApplicationContext.ROLE, ApplicationContext.ROLE_MANAGER);
                userName.setMaxAge(60 * 60);
                response.addCookie(userName);
                getServletContext().getRequestDispatcher("/").forward(request, response);
            } else {
                fail(request, response, "fail to login as manager", "incorrect password");
            }
        } else if (lg.equals(ApplicationContext.ROLE_DRIVER)) {
            try {
                if (!driverManagementService.checkDriverExistence(pw)) {
                    fail(request, response, "fail to get information about driver",
                            "no driver with personal number " + pw);
                    return;
                }
                DriverAssignment assignment = driverAssignmentService.getDriverAssignmentByPersonalNumber(pw);
                if (assignment == null) {
                    fail(request, response, "fail to get information", "not found");
                    return;
                }
                request.setAttribute("assignment", assignment);
                getServletContext().getRequestDispatcher("/driver.jsp").forward(request, response);
            } catch (ServiceLayerException e) {
                fail(request, response, "fail to get information", e.getMessage());
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
