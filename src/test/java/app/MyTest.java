package app;

import app.database.entities.Constants;
import config.SecurityConfig;
import config.WebConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;
import testconfig.TestConfig;
import testconfig.TestSecurityConfig;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.Resource;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, TestConfig.class, TestSecurityConfig.class})
public class MyTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    DataSource dataSource;
    private Connection connection;


    @Before
    public void init() throws SQLException {
        connection = dataSource.getConnection();
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    private ResultActions prepareConstants() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .put("/rest")
                .contentType(MediaType.APPLICATION_JSON_VALUE)//"Content-Type", "application/json"
                .characterEncoding("UTF-8")
                .content("{\"key\":\"one\",\"value\":\"1\"}");//om.writeValueAsString(new Constants("one","1"))
        return this.mockMvc.perform(builder);
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
    @WithMockUser//(roles = {"ROLE_ADMIN"})
    @Transactional
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

    @WithMockUser(roles = {"SUM_SUB"})
    @Test
    @Transactional
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
    public void testMyMvcControllerRestCalc() throws Exception {
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
    @WithUserDetails("admin")
    @Transactional
    public void testMyMvcControllerAccountsManager() throws Exception {
        ResultMatcher ok = status().isOk();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/accountsManager");
        this.mockMvc.perform(builder)
                .andExpect(view().name("accountsManager"))
                .andExpect(ok);
    }

    @Test
    @WithMockUser(roles = {"MATH"})
    @Transactional
    public void testRestPutConstant() throws Exception {
        ResultActions request = prepareConstants();
        request.andExpect(MockMvcResultMatchers.status().isNoContent());
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/rest")
                .characterEncoding("UTF-8");
        this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.content().string("[{\"key\":\"one\",\"value\":\"1\"}]"));
    }

    @Test
    @Transactional
    public void testRestPatchConstant() throws Exception {
        prepareConstants();
        MockHttpServletRequestBuilder builder2 = MockMvcRequestBuilders
                .patch("/rest")
                .content("{\"key\":\"one\",\"value\":\"2\"}")
                .header("Content-Type", "application/json")
                .characterEncoding("UTF-8");
        this.mockMvc.perform(builder2);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/rest")
                .characterEncoding("UTF-8");
        this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.content().string("[{\"key\":\"one\",\"value\":\"2\"}]"));
    }

    @Test
    @Transactional
    public void testRestDeleteConstant() throws Exception {
        prepareConstants();
        MockHttpServletRequestBuilder builder2 = MockMvcRequestBuilders
                .delete("/rest")
                .content("{\"key\":\"one\"}")
                .header("Content-Type", "application/json")
                .characterEncoding("UTF-8");
        this.mockMvc.perform(builder2);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/rest")
                .characterEncoding("UTF-8");
        this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    @Transactional
    public void testRestPostConstant() throws Exception {
        prepareConstants();
        MockHttpServletRequestBuilder builder2 = MockMvcRequestBuilders
                .post("/rest")
                .content("{\"keyOld\":\"one\",\"keyNew\":\"two\",\"value\":\"2\"}")
                .header("Content-Type", "application/json")
                .characterEncoding("UTF-8");
        this.mockMvc.perform(builder2);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/rest")
                .characterEncoding("UTF-8");
        this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.content().string("[{\"key\":\"two\",\"value\":\"2\"}]"));
    }

    @Test
    @WithMockUser(roles = {"SUM_SUB"})
    @Transactional
    public void testRestCalcSum() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/rest/calc?a=123&b=123&operation=sum")
                .characterEncoding("UTF-8");
        this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.content().string("246"));
    }

    @Test
    @WithMockUser(roles = {"SUM_SUB"})
    @Transactional
    public void testRestCalcSub() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/rest/calc?a=322&b=23&operation=sub")
                .characterEncoding("UTF-8");
        this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.content().string("299"));
    }

    @Test
    @WithMockUser(roles = {"MATH"})
    @Transactional
    public void testRestCalcMul() throws Exception {
        prepareConstants();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/rest/calc?a=123&b=123&operation=mul")
                .characterEncoding("UTF-8");
        this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.content().string("15129"));
    }

    @Test
    @WithMockUser(roles = {"MATH"})
    @Transactional
    public void testRestCalcMulWithConst() throws Exception {
        prepareConstants();
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/rest/calc?a=one&b=123&operation=mul")
                .characterEncoding("UTF-8");
        this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.content().string("123"));
    }

    @Test
    @WithMockUser(roles = {"MATH"})
    @Transactional
    public void testRestCalcDiv() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/rest/calc?a=573&b=100&operation=div")
                .characterEncoding("UTF-8");
        this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.content().string("5"));
    }

    @Test
    @WithMockUser(roles = {"MATH"})
    @Transactional
    public void testRestCalcDivByZero() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/rest/calc?a=573&b=0&operation=div")
                .characterEncoding("UTF-8");
        Throwable cause = null;
        try {
            this.mockMvc.perform(builder);
        } catch (NestedServletException e){
            cause = e.getCause();
        }

        assertNotNull(cause);
        assertEquals(ArithmeticException.class, cause.getClass());
        assertTrue(cause.getLocalizedMessage().toLowerCase().contains("division by zero"));
    }

    @Test
    @WithMockUser(roles = {"MATH"})
    @Transactional
    public void testRestCalcFib() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/rest/calc?a=10&operation=fib")
                .characterEncoding("UTF-8");
        this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.content().string("55"));
    }
}