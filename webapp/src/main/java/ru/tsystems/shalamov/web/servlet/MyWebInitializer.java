package ru.tsystems.shalamov.web.servlet;


import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import ru.tsystems.shalamov.web.config.SpringWebConfig;


/**
 * Created by viacheslav on 01.07.2015.
 */
public class MyWebInitializer extends
        AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringWebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

}
