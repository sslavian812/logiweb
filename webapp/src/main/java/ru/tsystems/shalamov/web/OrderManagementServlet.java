package ru.tsystems.shalamov.web;

import ru.tsystems.shalamov.ApplicationContext;
import ru.tsystems.shalamov.entities.CargoEntity;
import ru.tsystems.shalamov.entities.OrderEntity;
import ru.tsystems.shalamov.entities.statuses.CargoStatus;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.OrderManagementService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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

        String path = request.getRequestURI();
        if (path == null || path.isEmpty()) {
            doGet(request, response);
        }

        if (path.endsWith("showOrders")) {
            doGet(request, response);
        }

        if (path.endsWith("addOrder")) {
            String identifier = request.getParameter("orderIdentifier");
            String denomination = request.getParameter("denomination");
            int weignt = Integer.parseInt(request.getParameter("weight"));

            List<CargoEntity> cargoes = new ArrayList<>();
            OrderEntity order = new OrderEntity(identifier);
            cargoes.add(new CargoEntity(denomination, weignt, CargoStatus.PREPARED, order));
            try {
                orderManagementService.createOrderWithCargoes(order, cargoes);
                doGet(request, response);
            } catch (ServiceLayerException e) {
                //todo log!!!!
                request.setAttribute("message", "fail to add order " + identifier);
                getServletContext().getRequestDispatcher("/WEB-INF/views/jsp/fail.jsp").forward(request, response);
            }
        }

        if (path.endsWith("deleteOrder")) {
            String orderIdentifier = request.getParameter("orderIdentifier");
//            try {
                //orderManagementService.deleteOrderByOrderIdentifier(orderIdentifier);
                // todo add deletion if not assigned
                doGet(request, response);
//            } catch (ServiceLayerException e) {
//                //todo log!!!!
//                request.setAttribute("message", "fail to delete order " + orderIdentifier);
//                getServletContext().getRequestDispatcher("/WEB-INF/views/jsp/fail.jsp").forward(request, response);
//            }

            response.sendRedirect("/secure/orders.jsp");
        }
    }

}

