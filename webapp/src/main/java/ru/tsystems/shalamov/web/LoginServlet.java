package ru.tsystems.shalamov.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by viacheslav on 04.07.2015.
 */
public class LoginServlet extends HttpServlet {

    private static final String password = "abacaba";


    public void init() throws ServletException {
    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {

        // Set response content type
        response.setContentType("text/html");


        String pw = request.getParameter("password");
        String lg = request.getParameter("login");


        String res =  pw.equals(password)? "OK" : "NO";
        request.setAttribute("check",res);

        getServletContext().getRequestDispatcher("/WEB-INF/views/jsp/login.jsp").forward(request, response);
    }

    public void destroy() {
        // do nothing.
    }
}
