package Quiz.src.main.java.servlets;


import Quiz.src.main.java.models.DBConn;
import Quiz.src.main.java.models.User;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

@WebListener
public class ContextListener implements ServletContextListener, HttpSessionListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        DBConn acc = new DBConn();
        servletContextEvent.getServletContext().setAttribute("manacc", acc);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().setAttribute("daloginda", false);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }
}
