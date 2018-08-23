package app.config;

import app.database.JDBC;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

@Service
public class ServiceListener implements ServletRequestListener {

    @Autowired
    JDBC jdbc;


    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        jdbc.updateSessionEndTime((HttpServletRequest) sre.getServletRequest());
        //System.out.println(sre.getServletContext().getContext().getInitParameter().getInitParameter("DBName");
        System.out.println("Я сервайс");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {

    }
}
