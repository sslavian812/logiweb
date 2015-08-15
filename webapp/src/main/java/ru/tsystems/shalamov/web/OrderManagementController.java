package ru.tsystems.shalamov.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.tsystems.shalamov.entities.statuses.CargoStatus;
import ru.tsystems.shalamov.entities.statuses.OrderStatus;
import ru.tsystems.shalamov.model.CargoModel;
import ru.tsystems.shalamov.model.OrderModel;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.Util;
import ru.tsystems.shalamov.services.api.OrderManagementService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by viacheslav on 09.07.2015.
 */
@Controller
@RequestMapping("/secure/orders")
public class OrderManagementController {
    @Autowired
    private OrderManagementService orderManagementService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showOrders() {
        try {
            List<OrderModel> orders = orderManagementService.findAllOrders();
            Stream<OrderModel> stream = Stream.concat(
                    orders.stream().filter(o -> o.getStatus().equals(OrderStatus.UNASSIGNED)),
                    Stream.concat(orders.stream().filter(o -> o.getStatus().equals(OrderStatus.IN_PROGRESS)),
                            orders.stream().filter(o -> o.getStatus().equals(OrderStatus.COMPLETED))));
            List<OrderModel> sortedOrders = stream.collect(Collectors.toList());

            List<String> colors = sortedOrders.stream()
                    .map(o -> ControllerUtil.mapOrderStatusToColor(o.getStatus()))
                    .collect(Collectors.toList());
            ModelAndView mav = new ModelAndView("/secure/orders");
            mav.addObject("orders", sortedOrders);
            mav.addObject("colors", colors);
            mav.addObject("generated1", Util.generateRandomId());
            mav.addObject("generated2", Util.generateRandomId());
            return mav;
        } catch (ServiceLayerException e) {
            return ControllerUtil.fail("fail list all orders.", e.getMessage());
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addOrder(HttpServletRequest request) {

        String orderIdentifier = request.getParameter("orderIdentifier");
        String cargoIdentifier = request.getParameter("cargoIdentifier");

        String denomination = request.getParameter("denomination");
        int weight = Integer.parseInt(request.getParameter("weight"));

        List<CargoModel> cargoes = new ArrayList<>();
        OrderModel orderModel = new OrderModel(orderIdentifier);
        cargoes.add(new CargoModel(cargoIdentifier, denomination, weight, CargoStatus.PREPARED,
                orderIdentifier));
        try {
            orderManagementService.createOrderWithCargoes(orderModel, cargoes);
            return new ModelAndView("redirect:/secure/orders/edit/" + orderIdentifier);
        } catch (ServiceLayerException | NumberFormatException e) {
            return ControllerUtil.fail("fail to add order " + orderModel.getOrderIdentifier(), e.getMessage());
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ModelAndView deleteOrder(@PathVariable("id") String orderIdentifier) {
        try {
            orderManagementService.deleteOrderByOrderIdentifierIfNotAssigned(orderIdentifier);
            return new ModelAndView("redirect:/secure/orders/");
        } catch (ServiceLayerException e) {
            return ControllerUtil.fail("fail to delete order " + orderIdentifier, e.getMessage());
        }
    }

    @RequestMapping(value = "/{order}/cargo/delete/{cargo}", method = RequestMethod.POST)
    public ModelAndView deleteCargo(@PathVariable("order") String orderIdentifier,
                                    @PathVariable("cargo") String cargoIdentifier) {
        try {
            orderManagementService.deleteCargo(cargoIdentifier);
            return new ModelAndView("redirect:/secure/orders/edit/" + orderIdentifier);
        } catch (ServiceLayerException e) {
            return ControllerUtil.fail("fail to delete cargo " + cargoIdentifier, e.getMessage());
        }
    }


    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editOrder(@PathVariable("id") String orderIdentifier) {
        try {
            OrderModel order = orderManagementService.findOrderModelByOrderIdentifier(orderIdentifier);
            if (order == null) {
                throw new ServiceLayerException("No such order");
            }

            List<CargoModel> cargoes = order.getCargoes();
            List<String> colors = cargoes.stream()
                    .map(c -> ControllerUtil.mapCargoStatusToColor(c.getStatus()))
                    .collect(Collectors.toList());
            ModelAndView mav = new ModelAndView("/secure/orderEdit");
            mav.addObject("order", order);
            mav.addObject("cargoes", cargoes);
            mav.addObject("colors", colors);
            mav.addObject("generated", Util.generateRandomId());
            return mav;
        } catch (ServiceLayerException e) {
            return ControllerUtil.fail("fail to edit order", e.getMessage());
        }
    }

    @RequestMapping(value = "/add/{id}/cargo", method = RequestMethod.POST)
    public ModelAndView addCargo(@PathVariable("id") String orderIdentifier,
                                 HttpServletRequest request, HttpServletResponse response) {
        try {
            OrderModel order = orderManagementService.findOrderModelByOrderIdentifier(orderIdentifier);
            if (order == null) {
                throw new ServiceLayerException("No such order");
            }

            String cargoIdentifier = request.getParameter("cargoIdentifier");
            String denomination = request.getParameter("denomination");
            int weight = Integer.parseInt(request.getParameter("weight"));

            orderManagementService.addCargoToOrder(orderIdentifier,
                    new CargoModel(cargoIdentifier,
                            denomination, weight,
                            CargoStatus.PREPARED, orderIdentifier));

            return new ModelAndView("redirect:/secure/orders/edit/" + orderIdentifier);
        } catch (ServiceLayerException e) {
            return ControllerUtil.fail("fail to edit order", e.getMessage());
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView updateOrder(HttpServletRequest request) {
        return ControllerUtil.fail("not implemented", "");
    }
}

