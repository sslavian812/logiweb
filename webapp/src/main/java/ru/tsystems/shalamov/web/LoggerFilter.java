package ru.tsystems.shalamov.web;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by viacheslav on 04.07.2015.
 */
public class LoggerFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(LoggerFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        Enumeration<String> params = req.getParameterNames();

        while (params.hasMoreElements()) {
            String name = params.nextElement();
            String value = servletRequest.getParameter(name);
            LOG.debug(req.getRemoteAddr() + "::Request Params::{" + name + "=" + value + "}");
        }

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                LOG.debug(req.getRemoteAddr() + "::Cookie::{" + cookie.getName() + "," + cookie.getValue() + "}");
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
