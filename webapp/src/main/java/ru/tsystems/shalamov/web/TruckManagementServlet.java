package ru.tsystems.shalamov.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.tsystems.shalamov.entities.TruckEntity;
import ru.tsystems.shalamov.entities.statuses.TruckStatus;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.TruckManagementService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by viacheslav on 10.07.2015.
 */
@Controller
public class TruckManagementServlet {
    @Autowired
    private TruckManagementService truckManagementService;


    @RequestMapping(value = "secure/showTrucks", method = RequestMethod.GET)
    public ModelAndView showTrucks(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<TruckEntity> trucks = truckManagementService.listTrucks();
            ModelAndView mav = new ModelAndView("/secure/trucks");
            mav.addObject("trucks", trucks);
            return mav;
        } catch (ServiceLayerException e) {
            return Util.fail(response, "fail list all trucks", e.getMessage());
        }
    }

    @RequestMapping(value = "secure/addTruck", method = RequestMethod.POST)
    public ModelAndView addTruck(HttpServletRequest request, HttpServletResponse response) {
        int crewSize = Integer.parseInt(request.getParameter("crewSize"));
        String registrationNumber = request.getParameter("registrationNumber");
        int capacity = Integer.parseInt(request.getParameter("capacity"));

        try {
            truckManagementService.addTruck(new TruckEntity(crewSize,
                    registrationNumber, capacity, TruckStatus.INTACT));
            return new ModelAndView("redirect:/secure/showTrucks");
        } catch (ServiceLayerException e) {
            return Util.fail(response, "fail to add truck ", e.getMessage());
        }
    }

    @RequestMapping(value = "secure/deleteTruck", method = RequestMethod.GET)
    public ModelAndView deleteTruck(HttpServletRequest request, HttpServletResponse response) {
        String registrationNumber = request.getParameter("registrationNumber");
        try {
            truckManagementService.deleteTruckByRegistrationNumber(registrationNumber);
            return new ModelAndView("redirect:/secure/showTrucks");
        } catch (ServiceLayerException e) {
            return Util.fail(response, "fail to delete truck " + registrationNumber, e.getMessage());
        }
    }
}
