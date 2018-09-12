package app;

import config.SecurityConfig;
import config.WebConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import testconfig.TestConfig;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, TestConfig.class, SecurityConfig.class})
public class MyTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    @Resource
    private SessionFactory sessionFactory;

    private Session session;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
        session = sessionFactory.openSession();
        initDB();
    }

    private void initDB() {
        session.createSQLQuery("" +
                "" +
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
                ");");
    }

    @Test
    public void testMyMvcControllerHome() throws Exception {
        ResultMatcher ok = status().isOk();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/");
        this.mockMvc.perform(builder)
                .andExpect(view().name("home"))
                .andExpect(ok);
    }

    @Test
    public void testMyMvcControllerAbout() throws Exception {
        ResultMatcher ok = status().isOk();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/about");
        this.mockMvc.perform(builder)
                .andExpect(view().name("about"))
                .andExpect(ok);
    }

    @Test
    public void testMyMvcControllerCalc() throws Exception {
        ResultMatcher ok = status().isOk();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/calc");
        this.mockMvc.perform(builder)
                .andExpect(view().name("input"))
                .andExpect(ok);
    }

    @Test
    @WithMockUser//(roles = {"ROLE_ADMIN"})
    public void testMyMvcControllerOphistory() throws Exception {
        ResultMatcher ok = status().isOk();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/ophistory");
        this.mockMvc.perform(builder)
                .andExpect(view().name("ophistory"))
                .andExpect(ok);
    }

    @Test
    public void testMyMvcControllerOphistoryLOGIN() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/ophistory");
        this.mockMvc.perform(builder)
                .andExpect(status().is(302));//редирект на страницу логина
    }

    @WithMockUser(roles = {"ADMIN"})
    @Test
    public void testMyMvcControllerOphistoryDENIED() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/ophistory");
        this.mockMvc.perform(builder)
                .andExpect(status().is(403));
    }

    @Test
    @WithMockUser(roles = {"MATH"})
    public void testMyMvcControllerAnswer() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/answer?a=1&b=2&operation=mul");
        this.mockMvc.perform(builder)
                .andExpect(view().name("answer"));
    }

    @Test
    public void testMyMvcControllerRegister() throws Exception {
        ResultMatcher ok = status().isOk();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/register");
        this.mockMvc.perform(builder)
                .andExpect(view().name("register"))
                .andExpect(ok);
    }

    @Test
    public void testMyMvcControllerLogin() throws Exception {
        ResultMatcher ok = status().isOk();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/login");
        this.mockMvc.perform(builder)
                .andExpect(view().name("login"))
                .andExpect(ok);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testMyMvcControllerAccountsManager() throws Exception {
        ResultMatcher ok = status().isOk();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/accountsManager");
        this.mockMvc.perform(builder)
                .andExpect(view().name("accountsManager"))
                .andExpect(ok);
    }
}