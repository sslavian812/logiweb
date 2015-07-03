package ru.tsystems.shalamov.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by viacheslav on 01.07.2015.
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView hello(@RequestParam("login") String login,
                              @RequestParam("password") String password) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("login", login);
        modelAndView.addObject("password", password);
        modelAndView.setViewName("login");

        return modelAndView;
    }

}