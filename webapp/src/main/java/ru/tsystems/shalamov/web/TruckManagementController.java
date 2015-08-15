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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

            Stream<TruckModel> stream = Stream.concat(
                    trucks.stream().filter(t -> t.getStatus().equals(TruckStatus.INTACT)),
                    Stream.concat(trucks.stream().filter(t -> t.getStatus().equals(TruckStatus.ASSIGNED)),
                            trucks.stream().filter(t -> t.getStatus().equals(TruckStatus.BROKEN))));
            List<TruckModel> sortedTrucks = stream.collect(Collectors.toList());

            ModelAndView mav = new ModelAndView("/secure/trucks");
            mav.addObject("trucks", sortedTrucks);
            return mav;
        } catch (ServiceLayerException e) {
            return ControllerUtil.fail("fail list all trucks", e.getMessage());
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
            return ControllerUtil.fail("fail to add truck ", e.getMessage());
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public ModelAndView deleteTruck(@PathVariable("id") String registrationNumber) {
        try {
            truckManagementService.deleteTruckByRegistrationNumber(registrationNumber);
            return new ModelAndView("redirect:/secure/trucks");
        } catch (ServiceLayerException e) {
            return ControllerUtil.fail("fail to delete truck " + registrationNumber, e.getMessage());
        }
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editTruck(@PathVariable("id") String registrationNumber) {
        try {
            TruckModel truck = truckManagementService.findTruckModelByRegistrationNumber(registrationNumber);
            if (truck == null) {
                throw new ServiceLayerException("No such truck.");
            }
            ModelAndView mav = new ModelAndView("secure/truckEdit");
            mav.addObject("truck", truck);
            return mav;
        } catch (ServiceLayerException e) {
            return ControllerUtil.fail("fail to edit truck", e.getMessage());
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView updateTruck(HttpServletRequest request) {
        try {
            String oldRegistrationNumber = request.getParameter("oldRegistrationNumber");
            String registrationNumber = request.getParameter("registrationNumber");
            String crewSize = request.getParameter("crewSize");
            String capacity = request.getParameter("capacity");
            String status = request.getParameter("status");

            TruckModel truckModel = new TruckModel(registrationNumber,
                    Integer.parseInt(crewSize),
                    Integer.parseInt(capacity),
                    TruckStatus.valueOf(status));

            truckManagementService.updateTruck(truckModel, oldRegistrationNumber);
            return new ModelAndView("redirect:/secure/trucks/");
        } catch (ServiceLayerException e) {
            return ControllerUtil.fail("fail to edit driver", e.getMessage());
        }
    }
}
