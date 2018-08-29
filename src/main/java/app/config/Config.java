package app.config;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

@EnableTransactionManagement
@Configuration
public class Config implements WebMvcConfigurer {

    @Autowired
    ServletContext context;

    @Bean
    @Autowired
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("app.database.entities");
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    @Autowired
    public PlatformTransactionManager hibernateTransactionManager(DataSource dataSource) {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory(dataSource).getObject());
        return transactionManager;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
//        hibernateProperties.setProperty(
//                "hibernate.hbm2ddl.auto", "create");
        hibernateProperties.setProperty(
                "hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        hibernateProperties.setProperty(
                "datatypeFactory", "org.dbunit.ext.oracle.Oracle10DataTypeFactory");
        return hibernateProperties;
    }

    @Bean
    Logger rootLogger() {
        return LogManager.getRootLogger();
    }

    @Bean
    DataSource getDataSource() throws NamingException {
        return (DataSource) new InitialContext().lookup("java:comp/env/" + context.getInitParameter("dbName"));
    }

}