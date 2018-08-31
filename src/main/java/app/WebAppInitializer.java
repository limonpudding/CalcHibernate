package app;

import app.config.Config;
import app.config.SecurityConfig;
import app.config.ServiceListener;
import app.config.WebConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    public void onStartup(ServletContext container) {
        AnnotationConfigWebApplicationContext rootContext =
                new AnnotationConfigWebApplicationContext();
        rootContext.register(Config.class);
        rootContext.register(SecurityConfig.class);
        FilterRegistration.Dynamic filterRegistration = container.addFilter("springSecurityFilterChain", new org.springframework.web.filter.DelegatingFilterProxy());
        filterRegistration.setInitParameter("encoding", "UTF-8");
        filterRegistration.setInitParameter("forceEncoding", "true");
        filterRegistration.addMappingForUrlPatterns(null, true, "/*");
        container.addListener(new ContextLoaderListener(rootContext));
        // Manage the lifecycle of the root application context
        container.addListener(org.apache.tiles.extras.complete.CompleteAutoloadTilesListener.class);
        container.addListener(ServiceListener.class);

        //container.addFilter("springSecurityFilterChain", org.springframework.web.filter.DelegatingFilterProxy.class);
        container.setInitParameter("dbName", "H2/db");
        rootContext.setServletContext(container);




        // Create the dispatcher servlet's Spring application context
        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(WebConfig.class);

        // Register and map the dispatcher servlet
        ServletRegistration.Dynamic dispatcher =
                container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
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
//
//    @Override
//    protected Filter[] getServletFilters() {
//        return new Filter[] { new org.springframework.web.filter.DelegatingFilterProxy() };
//    }

}