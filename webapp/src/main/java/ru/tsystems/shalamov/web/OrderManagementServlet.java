package ru.tsystems.shalamov.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.tsystems.shalamov.entities.CargoEntity;
import ru.tsystems.shalamov.entities.OrderEntity;
import ru.tsystems.shalamov.entities.statuses.CargoStatus;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.OrderManagementService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by viacheslav on 09.07.2015.
 */
@Controller
public class OrderManagementServlet {
    @Autowired
    private OrderManagementService orderManagementService;


    @RequestMapping(value = "secure/showOrders", method = RequestMethod.GET)
    public ModelAndView showOrders(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<OrderEntity> orders = orderManagementService.listOrders();
            ModelAndView mav = new ModelAndView("/secure/orders");
            mav.addObject("orders", orders);
            return mav;
        } catch (ServiceLayerException e) {
            return Util.fail(response, "fail list all orders.", e.getMessage());
        }
    }

    @RequestMapping(value = "secure/addOrder", method = RequestMethod.POST)
    public ModelAndView addOrder(HttpServletRequest request, HttpServletResponse response) {
        String orderIdentifier = request.getParameter("orderIdentifier");
        String denomination = request.getParameter("denomination");
        int weight = Integer.parseInt(request.getParameter("weight"));

        List<CargoEntity> cargoes = new ArrayList<>();
        OrderEntity order = new OrderEntity(orderIdentifier);
        cargoes.add(new CargoEntity(denomination, weight, CargoStatus.PREPARED,
                order, orderIdentifier + denomination + weight));
        try {
            orderManagementService.createOrderWithCargoes(order, cargoes);
            return new ModelAndView("redirect:/secure/showOrders");
        } catch (ServiceLayerException | NumberFormatException e) {
            return Util.fail(response, "fail to add order " + orderIdentifier, e.getMessage());
        }
    }

    @RequestMapping(value = "secure/deleteOrder", method = RequestMethod.POST)
    public ModelAndView deleteOrder(HttpServletRequest request, HttpServletResponse response) {
        String orderIdentifier = request.getParameter("order");
        try {
            orderManagementService.deleteOrderByOrderIdentifierIfNotAssigned(orderIdentifier);
            return new ModelAndView("redirect:/secure/showOrders");
        } catch (ServiceLayerException e) {
            return Util.fail(response, "fail to delete order " + orderIdentifier, e.getMessage());
        }
    }
}

