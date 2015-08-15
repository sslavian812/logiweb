package ru.tsystems.shalamov.web;

import org.springframework.web.servlet.ModelAndView;

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
}
