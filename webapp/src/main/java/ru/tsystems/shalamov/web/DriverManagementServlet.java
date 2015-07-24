package ru.tsystems.shalamov.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.tsystems.shalamov.entities.DriverEntity;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.DriverManagementService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by viacheslav on 09.07.2015.
 */
@Controller
public class DriverManagementServlet {

    @Autowired
    private DriverManagementService driverManagementService;


    @RequestMapping(value = "secure/showDrivers", method = RequestMethod.GET)
    public ModelAndView showDrivers() {
        ModelAndView mav = new ModelAndView();
        try {
            List<DriverEntity> drivers = driverManagementService.listDrivers();
            mav.setViewName("secure/drivers");
            mav.addObject("drivers", drivers);
            return mav;
        } catch (ServiceLayerException e) {
            return Util.fail("fail list all drivers.", e.getMessage());
        }
    }


    @RequestMapping(value = "secure/addDriver", method = RequestMethod.POST)
    public ModelAndView addDriver(HttpServletRequest request, HttpServletResponse response) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String personalNumber = request.getParameter("personalNumber");

        try {
            driverManagementService.addDriver(new DriverEntity(firstName, lastName, personalNumber));
            return new ModelAndView("redirect:/secure/showDrivers");
        } catch (ServiceLayerException e) {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            return Util.fail("fail to add driver " + personalNumber, e.getMessage());
        }
    }

    @RequestMapping(value = "secure/deleteDriver", method = RequestMethod.POST)
    public ModelAndView deleteDriver(HttpServletRequest request, HttpServletResponse response) {
        try {
            String personalNumber = request.getParameter("personalNumber");
            driverManagementService.deleteDriverByPersonalNumber(personalNumber);
            return new ModelAndView("redirect:/secure/showDrivers");
        } catch (ServiceLayerException e) {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            return Util.fail("fail to delete driver", e.getMessage());
        }
    }

    @RequestMapping(value = "secure/editDriver", method = RequestMethod.POST)
    public ModelAndView editDriver(HttpServletRequest request, HttpServletResponse response) {
        String personalNumber = request.getParameter("personalNumber");
        try {
            DriverEntity driver = driverManagementService.findDriverByPersonalNumber(personalNumber);
            if (driver == null) {
                throw new ServiceLayerException("No such driver.");
            }
            ModelAndView mav = new ModelAndView("/secure/driverEdit.jsp");
            mav.addObject("driver", driver);
            return mav;
        } catch (ServiceLayerException e) {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            return Util.fail("fail to edit driver", e.getMessage());
        }
    }

    @RequestMapping(value = "secure/updateDriver", method = RequestMethod.POST)
    public ModelAndView updateDriver(HttpServletRequest request, HttpServletResponse response) {
        try {
            String oldPersonalNumber = request.getParameter("oldPersonalNumber");
            String personalNumber = request.getParameter("personalNumber");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");

            DriverEntity driver = driverManagementService.findDriverByPersonalNumber(oldPersonalNumber);
            if (driver == null) {
                throw new ServiceLayerException("No such driver.");
            }

            if ((!oldPersonalNumber.equals(personalNumber))
                    && driverManagementService.checkDriverExistence(personalNumber)) {
                throw new ServiceLayerException("Fail to update driver. "
                        + "Driver with new personal number already exists");
            }

            driver.setFirstName(firstName);
            driver.setLastName(lastName);
            driver.setPersonalNumber(personalNumber);
            driverManagementService.updateDriver(driver);
            return new ModelAndView("redirect:/secure/showDrivers");
        } catch (ServiceLayerException e) {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            return Util.fail("fail to edit driver", e.getMessage());
        }
    }
}
