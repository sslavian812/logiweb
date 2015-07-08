package ru.tsystems.shalamov.web;

import ru.tsystems.shalamov.ApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by viacheslav on 08.07.2015.
 */
public class LoginFilter implements Filter {

    //private static final Logger LOG = Logger.getLogger(LoginFilter.class);

    private static final String LOGIN_PAGE = "/login";


    public void doFilter(ServletRequest request,  ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();

        boolean loggedIn = httpRequest.getSession().getAttribute(ApplicationContext.ROLE) != null;
        String pathLoginPage = httpRequest.getContextPath() + LOGIN_PAGE;

        if (loggedIn) {
            chain.doFilter(request, response);
        } else {
            try {
                httpResponse.sendRedirect(httpRequest.getContextPath() + LOGIN_PAGE);
            } catch (IOException e) {
                //todo handle
            }
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
