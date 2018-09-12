package app;

import config.SecurityConfig;
import config.WebConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;
import testconfig.TestConfig;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;

import java.sql.Connection;
import java.sql.Statement;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, TestConfig.class, SecurityConfig.class})
public class MyTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    @Autowired
    private SessionFactory sessionFactory;


    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
        initDB();
    }

    private void initDB() {
//        sessionFactory.getCurrentSession().createSQLQuery("" +
//                "create table USERS\n" +
//                "(\n" +
//                "  USERNAME             NVARCHAR2(40) not null\n" +
//                "    primary key,\n" +
//                "  PASSWORD           NVARCHAR2(100) not null\n" +
//                ");" +
//                "INSERT INTO USERS\n" +
//                "(USERNAME,PASSWORD)\n" +
//                "VALUES  ('admin', '$2a$10$5a6vv3yJZuAbpUSU04vAce2d6MACeDHJeDspyulKzbR2.tAu5W2Tm');\n" +
//                "create table USERROLES\n" +
//                "(\n" +
//                "  ID int auto_increment primary key, \n" +
//                "  USERNAME             NVARCHAR2(40) not null,\n" +
//                "  ROLE           NVARCHAR2(40) not null\n" +
//                "); " +
//                "INSERT INTO USERROLES\n" +
//                "(USERNAME,ROLE)\n" +
//                "VALUES  ('admin', 'ROLE_ADMIN');\n" +
//                "create table BINARYOPERATION\n" +
//                "(\n" +
//                "  ID             NVARCHAR2(40) not null\n" +
//                "    primary key,\n" +
//                "  OPERATIONKIND           NVARCHAR2(40),\n" +
//                "  FIRSTOPERAND   CLOB,\n" +
//                "  SECONDOPERAND CLOB,\n" +
//                "  ANSWER         CLOB,\n" +
//                "  IDSESSION      NVARCHAR2(40),\n" +
//                "  TIME           TIMESTAMP(6)\n" +
//                ");" +
//                "create table SINGLEOPERATION\n" +
//                "(\n" +
//                "  ID           NVARCHAR2(40) not null\n" +
//                "    primary key,\n" +
//                "  OPERATIONKIND         NVARCHAR2(40),\n" +
//                "  FIRSTOPERAND CLOB,\n" +
//                "  ANSWER       CLOB,\n" +
//                "  IDSESSION    NVARCHAR2(40),\n" +
//                "  TIME         TIMESTAMP(6)\n" +
//                ");" +
//                "create table CONSTANTS\n" +
//                "(\n" +
//                "  KEY            NVARCHAR2(40) default NULL not null\n" +
//                "    primary key,\n" +
//                "  VALUE  CLOB" +
//                ");" +
//                "create table SESSIONS\n" +
//                "(\n" +
//                "  ID        NVARCHAR2(40) default NULL not null\n" +
//                "    primary key,\n" +
//                "  IP        NVARCHAR2(25),\n" +
//                "  TIMESTART TIMESTAMP,\n" +
//                "  TIMEEND   TIMESTAMP\n" +
//                ");");
    }

    @Test
    @Transactional
    public void testMyMvcControllerHome() throws Exception {
        ResultMatcher ok = status().isOk();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/");
        this.mockMvc.perform(builder)
                .andExpect(view().name("home"))
                .andExpect(ok);
    }

    @Test
    @Transactional
    public void testMyMvcControllerAbout() throws Exception {
        ResultMatcher ok = status().isOk();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/about");
        this.mockMvc.perform(builder)
                .andExpect(view().name("about"))
                .andExpect(ok);
    }

    @Test
    @Transactional
    public void testMyMvcControllerCalc() throws Exception {
        ResultMatcher ok = status().isOk();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/calc");
        this.mockMvc.perform(builder)
                .andExpect(view().name("input"))
                .andExpect(ok);
    }

    @Test
    @Transactional
    @WithMockUser//(roles = {"ROLE_ADMIN"})
    public void testMyMvcControllerOphistory() throws Exception {
        ResultMatcher ok = status().isOk();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/ophistory");
        this.mockMvc.perform(builder)
                .andExpect(view().name("ophistory"))
                .andExpect(ok);
    }

    @Test
    @Transactional
    public void testMyMvcControllerOphistoryLOGIN() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/ophistory");
        this.mockMvc.perform(builder)
                .andExpect(status().is(302));//редирект на страницу логина
    }

    @Transactional
    @WithMockUser(roles = {"ADMIN"})
    @Test
    public void testMyMvcControllerOphistoryDENIED() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/ophistory");
        this.mockMvc.perform(builder)
                .andExpect(status().is(403));
    }

    @Test
    @Transactional
    @WithMockUser(roles = {"MATH"})
    public void testMyMvcControllerAnswer() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/answer?a=1&b=2&operation=mul");
        this.mockMvc.perform(builder)
                .andExpect(view().name("answer"));
    }

    @Test
    @Transactional
    public void testMyMvcControllerRegister() throws Exception {
        ResultMatcher ok = status().isOk();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/register");
        this.mockMvc.perform(builder)
                .andExpect(view().name("register"))
                .andExpect(ok);
    }

    @Test
    @Transactional
    public void testMyMvcControllerLogin() throws Exception {
        ResultMatcher ok = status().isOk();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/login");
        this.mockMvc.perform(builder)
                .andExpect(view().name("login"))
                .andExpect(ok);
    }

    @Test
    @Transactional
    @WithMockUser(roles = {"ADMIN"})
    public void testMyMvcControllerAccountsManager() throws Exception {
        ResultMatcher ok = status().isOk();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/accountsManager");
        this.mockMvc.perform(builder)
                .andExpect(view().name("accountsManager"))
                .andExpect(ok);
    }
}