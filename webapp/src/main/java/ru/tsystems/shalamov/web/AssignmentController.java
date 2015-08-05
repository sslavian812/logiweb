package ru.tsystems.shalamov.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.tsystems.shalamov.model.AvailableToAssignModel;
import ru.tsystems.shalamov.model.DriverAssignmentModel;
import ru.tsystems.shalamov.services.ServiceLayerException;
import ru.tsystems.shalamov.services.api.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * Created by viacheslav on 10.07.2015.
 */
@Controller
@RequestMapping("/secure/assignmets")
public class AssignmentController {

    @Autowired
    private DriverInfoService driverInfoService;
    @Autowired
    private AssignmentService assignmentService;

    private static final Logger LOG = Logger.getLogger(AssignmentController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showAssignments() {
        try {
            List<DriverAssignmentModel> assignments = driverInfoService.findAllDriverAssignments();
            ModelAndView mav = new ModelAndView("secure/assignments");
            mav.addObject("assignments", assignments);
            return mav;
        } catch (ServiceLayerException e) {
            LOG.warn(e);
            return Util.fail("fail to edit driver", e.getMessage());
        }
    }

    @RequestMapping(value = "/construct/{id}", method = RequestMethod.POST)
    public ModelAndView constructAssignment(@PathVariable("id") String orderIdentifier) {
        try {
            if (orderIdentifier == null || orderIdentifier.isEmpty()) {
                return Util.fail("Unable to assign.", "Empty Order Identifier.");
            }

            AvailableToAssignModel available = assignmentService.findAvailableToAssign(orderIdentifier);
            ModelAndView mav = new ModelAndView("secure/constructAssignment");
            mav.addObject("trucks", available.getAvailableTrucks());
            mav.addObject("drivers", available.getAvailableDrivers());
            mav.addObject("orderIdentifier", orderIdentifier);
            return mav;
        } catch (ServiceLayerException e) {
            LOG.warn(e);
            return Util.fail("unable to construct assignment", e.getMessage());
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView assign(HttpServletRequest request) {
        try {
            String orderIdentifier = request.getParameter("orderIdentifier");
            if (orderIdentifier == null || orderIdentifier.isEmpty()) {
                return Util.fail("fail to assign", "empty order identifier");
            }

            String[] driversPN = request.getParameterValues("driver");
            String truckRN = request.getParameter("truck");

            if (driversPN == null || driversPN.length == 0) {
                return Util.fail("fail to assign", "no drivers provided");
            }

            if (truckRN == null || truckRN.isEmpty()) {
                LOG.debug("NOo truck provided.");
                return Util.fail("fail to assign", "no truck provided");
            }

            AvailableToAssignModel availableToAssignModel = new AvailableToAssignModel(
                    orderIdentifier,
                    truckRN,
                    Arrays.asList(driversPN)
            );

            assignmentService.assignDriversAndTruckToOrder(availableToAssignModel);

            return new ModelAndView("redirect:/secure/assignments");
        } catch (ServiceLayerException e) {
            LOG.warn(e);
            return Util.fail("unable to show assignments", e.getMessage());
        }
    }
}
