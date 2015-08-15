package ru.tsystems.shalamov.web;

import org.springframework.web.servlet.ModelAndView;
import ru.tsystems.shalamov.entities.statuses.CargoStatus;
import ru.tsystems.shalamov.entities.statuses.DriverStatus;
import ru.tsystems.shalamov.entities.statuses.OrderStatus;
import ru.tsystems.shalamov.entities.statuses.TruckStatus;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by viacheslav on 19.07.2015.
 */
public class ControllerUtil {
    public static ModelAndView fail(String message, String cause) {
        ModelAndView mav = new ModelAndView("fail");
        mav.addObject("message", message);
        mav.addObject("cause", cause);
        return mav;
    }

    public static ModelAndView fail(HttpServletResponse response, String message, String cause) {
        response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        ModelAndView mav = new ModelAndView("fail");
        mav.addObject("message", message);
        mav.addObject("cause", cause);
        return mav;
    }


    public static String mapDriverStatusToColor(DriverStatus status) {
        if (status.equals(DriverStatus.UNASSIGNED))
            return "success";
        else if (status.equals(DriverStatus.REST))
            return "danger";
        else
            return "info";
    }

    public static String mapTruckStatusToColor(TruckStatus status) {
        if (status.equals(TruckStatus.INTACT))
            return "success";
        else if (status.equals(TruckStatus.BROKEN))
            return "danger";
        else
            return "info";
    }

    public static String mapOrderStatusToColor(OrderStatus status) {
        if (status.equals(OrderStatus.COMPLETED))
            return "success";
        else if (status.equals(OrderStatus.UNASSIGNED))
            return "danger";
        else
            return "info";
    }

    public static String mapCargoStatusToColor(CargoStatus status) {
        if (status.equals(CargoStatus.PREPARED))
            return "warning";
        else if (status.equals(CargoStatus.DELIVERED))
            return "success";
        else
            return "info";
    }


}
