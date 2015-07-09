package ru.tsystems.shalamov.web;

import ru.tsystems.shalamov.ApplicationContext;
import ru.tsystems.shalamov.entities.OrderEntity;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.OrderManagementService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by viacheslav on 09.07.2015.
 */
public class OrderManagementServlet extends HttpServlet {
    OrderManagementService orderManagementService;

    @Override
    public void init() throws ServletException {
        orderManagementService = ApplicationContext.getInstance().getOrderManagementService();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<OrderEntity> orders = orderManagementService.listOrders();
            request.setAttribute("orders", orders);
            getServletContext().getRequestDispatcher("/secure/orders.jsp").forward(request, response);
        } catch (ServiceLayerException e) {
            //todo log
            request.setAttribute("message", "fail list all orders.");
            getServletContext().getRequestDispatcher("/WEB-INF/views/jsp/fail.jsp").forward(request, response);
        }
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getPathInfo();
        if (path == null || path.isEmpty()) {
            response.sendRedirect("/secure/orders.jsp");
        }

        if (path.endsWith("addOrder")) {
            String identifier = request.getParameter("orderIdentifier");

            try {
                orderManagementService.createOrder(new OrderEntity(identifier));
                response.sendRedirect("/secure/orders.jsp");
            } catch (ServiceLayerException e) {
                //todo log!!!!
                request.setAttribute("message", "fail to add order " + identifier);
                getServletContext().getRequestDispatcher("/WEB-INF/views/jsp/fail.jsp").forward(request, response);
            }
        }
    }

}

