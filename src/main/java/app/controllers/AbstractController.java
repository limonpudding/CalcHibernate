package app.controllers;

import app.database.JDBC;
import app.utils.Log;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import static app.utils.Log.USER_CONNECTED_LOG;

public abstract class AbstractController {
    @Autowired
    private HttpServletRequest req;
    @Autowired
    private JDBC jdbc;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private Logger rootLogger;
    void init() {
        if (req.getSession().isNew()) {
            jdbc.putSession();
            Log.print(rootLogger, Level.INFO, USER_CONNECTED_LOG, req.getRemoteAddr());
        } else {
            jdbc.updateSession();
        }
    }
}
