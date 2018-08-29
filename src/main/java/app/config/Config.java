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
        hibernateProperties.setProperty(
                "hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        return hibernateProperties;
    }

    @Bean
    Logger rootLogger() {
        return LogManager.getRootLogger();
    }

    @Bean
    DataSource getDataSource() throws NamingException, SQLException {
        String dbName = context.getInitParameter("dbName");
        DataSource dataSource = (DataSource) new InitialContext().lookup("java:comp/env/" + dbName);
        if (dataSource.getConnection().getMetaData().getDatabaseProductName().toUpperCase().contains("H2")) {

            try (Connection connection = dataSource.getConnection()) {
                Statement statement = connection.createStatement();
                statement.execute("" +
                        "create table BINARYOPERATION\n" +
                        "(\n" +
                        "  ID             NVARCHAR2(40) not null\n" +
                        "    primary key,\n" +
                        "  NAME           NVARCHAR2(40),\n" +
                        "  FIRSTOPERAND   CLOB,\n" +
                        "  SECONDOPERAND CLOB,\n" +
                        "  ANSWER         CLOB,\n" +
                        "  IDSESSION      NVARCHAR2(40),\n" +
                        "  TIME           TIMESTAMP(6)\n" +
                        ");" +
                        "create table SINGLEOPERATION\n" +
                        "(\n" +
                        "  ID           NVARCHAR2(40) not null\n" +
                        "    primary key,\n" +
                        "  NAME         NVARCHAR2(40),\n" +
                        "  FIRSTOPERAND CLOB,\n" +
                        "  ANSWER       CLOB,\n" +
                        "  IDSESSION    NVARCHAR2(40),\n" +
                        "  TIME         TIMESTAMP(6)\n" +
                        ");" +
                        "create table CONSTANTS\n" +
                        "(\n" +
                        "  KEY            NVARCHAR2(40) default NULL not null\n" +
                        "    primary key,\n" +
                        "  VALUE  CLOB" +
                        ");"+
                        "create table SESSIONS\n" +
                        "(\n" +
                        "  ID        NVARCHAR2(40) default NULL not null\n" +
                        "    primary key,\n" +
                        "  IP        NVARCHAR2(25),\n" +
                        "  TIMESTART TIMESTAMP,\n" +
                        "  TIMEEND   TIMESTAMP\n" +
                        ");" +
                        "create view HISTORY as\n" +
                        "  SELECT\n" +
                        "    SESSIONS.id,\n" +
                        "    SESSIONS.ip,\n" +
                        "    SESSIONS.TIMESTART,\n" +
                        "    SESSIONS.TIMEEND,\n" +
                        "    BINARYOPERATION.NAME,\n" +
                        "    BINARYOPERATION.FIRSTOPERAND,\n" +
                        "    BINARYOPERATION.SECONDOPERAND,\n" +
                        "    BINARYOPERATION.ANSWER,\n" +
                        "    BINARYOPERATION.TIME\n" +
                        "  FROM BINARYOPERATION\n" +
                        "    join SESSIONS on BINARYOPERATION.IDSESSION = SESSIONS.ID\n" +
                        "  union all\n" +
                        "  SELECT\n" +
                        "    SESSIONS.id,\n" +
                        "    SESSIONS.ip,\n" +
                        "    SESSIONS.TIMESTART,\n" +
                        "    SESSIONS.TIMEEND,\n" +
                        "    SINGLEOPERATION.NAME,\n" +
                        "    SINGLEOPERATION.FIRSTOPERAND,\n" +
                        "    null        as SECONDOPERAND,\n" +
                        "    SINGLEOPERATION.ANSWER,\n" +
                        "    SINGLEOPERATION.TIME\n" +
                        "  FROM SINGLEOPERATION\n" +
                        "    join SESSIONS on SINGLEOPERATION.IDSESSION = SESSIONS.ID"
                );

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            return dataSource;
        } else if (dataSource.getConnection().getMetaData().getDatabaseProductName().toUpperCase().contains("ORACLE")) {
            return dataSource;
        } else {
            return null;
        }
    }

}