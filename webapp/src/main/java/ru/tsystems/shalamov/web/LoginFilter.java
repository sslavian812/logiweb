package ru.tsystems.shalamov.web;

import ru.tsystems.shalamov.ApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by viacheslav on 08.07.2015.
 */
public class LoginFilter implements Filter {

    //private static final Logger LOG = Logger.getLogger(LoginFilter.class);

    private static final String LOGIN_PAGE = "/login";


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);


        boolean loggedIn = false;
        if (session != null)
            loggedIn = ApplicationContext.ROLE_MANAGER.equals(session.getAttribute(ApplicationContext.ROLE));


        if (loggedIn) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(LOGIN_PAGE);
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
