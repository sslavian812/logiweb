package ru.tsystems.shalamov.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.tsystems.shalamov.entities.statuses.TruckStatus;
import ru.tsystems.shalamov.model.TruckModel;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.TruckManagementService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by viacheslav on 10.07.2015.
 */
@Controller
@RequestMapping("/secure/trucks")
public class TruckManagementController {
    @Autowired
    private TruckManagementService truckManagementService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showTrucks() {
        try {
            List<TruckModel> trucks = truckManagementService.findAllTrucks();
            ModelAndView mav = new ModelAndView("/secure/trucks");
            mav.addObject("trucks", trucks);
            return mav;
        } catch (ServiceLayerException e) {
            return Util.fail("fail list all trucks", e.getMessage());
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addTruck(HttpServletRequest request) {

        int crewSize = Integer.parseInt(request.getParameter("crewSize"));
        String registrationNumber = request.getParameter("registrationNumber");
        int capacity = Integer.parseInt(request.getParameter("capacity"));

        try {
            truckManagementService.addTruck(new TruckModel(registrationNumber, crewSize,
                    capacity, TruckStatus.INTACT));
            return new ModelAndView("redirect:/secure/trucks/");
        } catch (ServiceLayerException e) {
            return Util.fail("fail to add truck ", e.getMessage());
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteTruck(@PathVariable("id") String registrationNumber) {
        try {
            truckManagementService.deleteTruckByRegistrationNumber(registrationNumber);
            return new ModelAndView("redirect:/secure/trucks");
        } catch (ServiceLayerException e) {
            return Util.fail("fail to delete truck " + registrationNumber, e.getMessage());
        }
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public ModelAndView editTruck(@PathVariable("id") String registrationNumber) {
        return Util.fail("not implemented", "");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView updateTruck(HttpServletRequest request) {
        return Util.fail("not implemented", "");
    }
}
