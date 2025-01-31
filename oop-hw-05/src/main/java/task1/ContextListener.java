package task1;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        AccountManager acc = new AccountManager();
        servletContextEvent.getServletContext().setAttribute("manacc", acc);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
