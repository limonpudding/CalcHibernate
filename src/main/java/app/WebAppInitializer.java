package app;

import app.config.Config;
import app.config.SecurityConfig;
import app.config.WebConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    public void onStartup(ServletContext container) throws ServletException {
        container.addListener(org.apache.tiles.extras.complete.CompleteAutoloadTilesListener.class);
        container.setInitParameter("dbName", "H2/db");
        super.onStartup(container);
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        // TODO Auto-generated method stub
        return new Class[]{Config.class, SecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        // TODO Auto-generated method stub
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        // TODO Auto-generated method stub
        return new String[]{"/"};
    }
}