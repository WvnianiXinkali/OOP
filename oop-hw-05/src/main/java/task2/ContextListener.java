package task2;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class ContextListener implements ServletContextListener, HttpSessionListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        StoreController Controller = new StoreController();
        servletContextEvent.getServletContext().setAttribute("control", Controller);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().setAttribute("cartt", new cart());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }
}

