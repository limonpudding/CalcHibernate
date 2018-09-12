package testconfig;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
public class TestConfig implements WebMvcConfigurer {

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
        transactionManager.setSessionFactory(sessionFactory(dataSource).getObject());//
        return transactionManager;
    }

    private Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");//Oracle10gDialect
        //hibernateProperties.setProperty("hibernate.hbm2ddl.auto","create");
        return hibernateProperties;
    }

    @Bean
    Logger rootLogger() {
        return LogManager.getRootLogger();
    }

    @Bean
    DataSource getDataSource() throws NamingException, SQLException {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.h2.Driver.class);
        dataSource.setUrl("jdbc:h2:mem:test");
        dataSource.setUsername("");
        dataSource.setPassword("");
        Connection connection;
        try {
            connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "create table USERS\n" +
                            "(\n" +
                            "  USERNAME             NVARCHAR2(40) not null\n" +
                            "    primary key,\n" +
                            "  PASSWORD           NVARCHAR2(100) not null\n" +
                            ");" +
                            "INSERT INTO USERS\n" +
                            "(USERNAME,PASSWORD)\n" +
                            "VALUES  ('admin', '$2a$10$5a6vv3yJZuAbpUSU04vAce2d6MACeDHJeDspyulKzbR2.tAu5W2Tm');\n" +
                            "create table USERROLES\n" +
                            "(\n" +
                            "  ID int auto_increment primary key, \n" +
                            "  USERNAME             NVARCHAR2(40) not null,\n" +
                            "  ROLE           NVARCHAR2(40) not null\n" +
                            "); " +
                            "INSERT INTO USERROLES\n" +
                            "(USERNAME,ROLE)\n" +
                            "VALUES  ('admin', 'ROLE_ADMIN');\n" +
                            "create table BINARYOPERATION\n" +
                            "(\n" +
                            "  ID             NVARCHAR2(40) not null\n" +
                            "    primary key,\n" +
                            "  OPERATIONKIND           NVARCHAR2(40),\n" +
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
                            "  OPERATIONKIND         NVARCHAR2(40),\n" +
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
                            ");" +
                            "create table SESSIONS\n" +
                            "(\n" +
                            "  ID        NVARCHAR2(40) default NULL not null\n" +
                            "    primary key,\n" +
                            "  IP        NVARCHAR2(25),\n" +
                            "  TIMESTART TIMESTAMP,\n" +
                            "  TIMEEND   TIMESTAMP\n" +
                            ");"
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return dataSource;
    }

}