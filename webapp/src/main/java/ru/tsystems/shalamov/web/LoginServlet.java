package ru.tsystems.shalamov.web;

import ru.tsystems.shalamov.ApplicationContext;
import ru.tsystems.shalamov.dao.api.DriverDao;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.services.DriverAssignment;
import ru.tsystems.shalamov.services.api.DriverAssignmentService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Driver;

/**
 * Created by viacheslav on 04.07.2015.
 */
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final String managerID = "manager";
    private final String password = "abacaba";

    public void init() throws ServletException {
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        String lg = request.getParameter("login");
        String pw = request.getParameter("password");


        if (managerID.equals(lg)) {
            if (password.equals(pw)) {

                HttpSession session = request.getSession();
                session.setAttribute("manager", lg);

                //setting session to expiry in 30 mins
                session.setMaxInactiveInterval(30 * 60);
                Cookie userName = new Cookie("user", lg);
                userName.setMaxAge(30 * 60);
                response.addCookie(userName);
                getServletContext().getRequestDispatcher("/WEB-INF/views/jsp/manager.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "fail to login as manager");
                getServletContext().getRequestDispatcher("/WEB-INF/views/jsp/fail.jsp").forward(request, response);
            }
        } else {
            DriverDao driverDao = ApplicationContext.INSTANCE.getDriverDao();
            DriverEntity driver = driverDao.findByPersonalNumber(lg);
            RequestDispatcher rd;
            if (driver == null) {
                request.setAttribute("message", "no driver found with personal number " + lg);
                rd = getServletContext().getRequestDispatcher("/fail.jsp");
            } else {
                rd = getServletContext().getRequestDispatcher("/driver.jsp");
                DriverAssignmentService driverAssignmentService = ApplicationContext.INSTANCE.getDriverAssignmentService();
                DriverAssignment assignment = driverAssignmentService.findDriverAssignmentByPersonalNumber(lg);
                if (driverAssignmentService == null) {
                    request.setAttribute("message", "no assignments found for driver " + lg);
                    rd = getServletContext().getRequestDispatcher("/fail.jsp");
                }

                setAllData(request, assignment);
            }
            rd.include(request, response);
        }
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
