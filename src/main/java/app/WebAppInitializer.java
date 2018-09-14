package app;

import config.Config;
import config.SecurityConfig;
import config.WebConfig;
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
        return new Class[]{Config.class, SecurityConfig.class,WebConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}