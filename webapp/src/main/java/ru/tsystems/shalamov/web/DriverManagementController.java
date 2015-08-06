package ru.tsystems.shalamov.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.tsystems.shalamov.model.DriverModel;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.DriverManagementService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by viacheslav on 09.07.2015.
 */
@Controller
@RequestMapping("/secure/drivers")
public class DriverManagementController {

    @Autowired
    private DriverManagementService driverManagementService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showDrivers() {
        ModelAndView mav = new ModelAndView();
        try {
            List<DriverModel> drivers = driverManagementService.findAllDrivers();
            mav.setViewName("secure/drivers");
            mav.addObject("drivers", drivers);
            return mav;
        } catch (ServiceLayerException e) {
            return Util.fail("fail list all drivers.", e.getMessage());
        }
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addDriver(HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String personalNumber = request.getParameter("personalNumber");

        try {
            driverManagementService.addDriver(new DriverModel(firstName, lastName, personalNumber));
            return new ModelAndView("redirect:/secure/drivers/");
        } catch (ServiceLayerException e) {
            return Util.fail("fail to add driver " + personalNumber, e.getMessage());
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ModelAndView deleteDriver(@PathVariable("id") String personalNumber) {
        try {
            driverManagementService.deleteDriverByPersonalNumber(personalNumber);
            return new ModelAndView("redirect:/secure/drivers/");
        } catch (ServiceLayerException e) {
            return Util.fail("fail to delete driver", e.getMessage());
        }
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public ModelAndView editDriver(@PathVariable("id") String personalNumber) {
        try {
            DriverModel driver = driverManagementService.findDriverModelByPersonalNumber(personalNumber);
            if (driver == null) {
                throw new ServiceLayerException("No such driver.");
            }
            ModelAndView mav = new ModelAndView("/secure/driverEdit");
            mav.addObject("driver", driver);
            return mav;
        } catch (ServiceLayerException e) {
            return Util.fail("fail to edit driver", e.getMessage());
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView updateDriver(HttpServletRequest request) {
        try {
            String oldPersonalNumber = request.getParameter("oldPersonalNumber");
            String personalNumber = request.getParameter("personalNumber");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");

            DriverModel driverModel = new DriverModel(firstName, lastName, personalNumber);

            driverManagementService.updateDriver(driverModel, oldPersonalNumber);
            return new ModelAndView("redirect:/secure/drivers/");
        } catch (ServiceLayerException e) {
            return Util.fail("fail to edit driver", e.getMessage());
        }
    }
}
