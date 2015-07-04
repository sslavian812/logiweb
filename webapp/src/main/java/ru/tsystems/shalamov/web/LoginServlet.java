package ru.tsystems.shalamov.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

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
                getServletContext().getRequestDispatcher("/WEB-INF/views/jsp/fail.jsp").forward(request, response);
            }
        } else {
            // todo: check driers here;
            //either
            //RequestDispatcher rd = getServletContext().getRequestDispatcher("/driver.jsp");
            //or
            //RequestDispatcher rd = getServletContext().getRequestDispatcher("/fail.jsp");
            //rd.include(request, response);
        }

//
//        String res = pw.equals(password) ? "OK" : "NO";
//        request.setAttribute("check", res);
//
//        getServletContext().getRequestDispatcher("/WEB-INF/views/jsp/login.jsp").forward(request, response);
    }

    public void destroy() {
        // do nothing.
    }
}
