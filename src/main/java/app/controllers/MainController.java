package app.controllers;

import app.database.JDBC;
import app.database.entities.Roles;
import app.database.entities.Users;
import app.pages.logic.Page;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static app.utils.PageNamesConstants.*;

@Controller
public class MainController extends AbstractController {

    private final Logger rootLogger;
    private final Page getAbout;
    private final Page getHome;
    private final Page getTables;
    private final Page getAnswer;
    private final Page getError;
    private final Page getCalc;
    private final Page getOpHistory;
    private final Page getRegistration;
    private final Page getReg;
    private final Page getLogin;
    private final Page getAccessDenied;
    private final Page getAccountsManager;
    private final JDBC jdbc;

    @Autowired
    public MainController(Page getAbout, Page getHome, Page getTables, Page getAnswer, Page getError, Page getCalc, Page getOpHistory, Logger rootLogger, Page getRegistration, Page getReg, Page getLogin, Page getAccessDenied, Page getAccountsManager, JDBC jdbc) {
        this.getAbout = getAbout;
        this.getHome = getHome;
        this.getTables = getTables;
        this.getAnswer = getAnswer;
        this.getError = getError;
        this.getCalc = getCalc;
        this.getOpHistory = getOpHistory;
        this.rootLogger = rootLogger;
        this.getRegistration = getRegistration;
        this.getReg = getReg;
        this.getLogin = getLogin;
        this.getAccessDenied = getAccessDenied;
        this.getAccountsManager = getAccountsManager;
        this.jdbc = jdbc;
    }

    //TODO привязать через Autowired и Qualifier реализации созданного абстрактного класса для каждого представления свою.

    @RequestMapping(path = ROOT_PAGE)
    public ModelAndView getHome() throws Exception {
        init();
        return getHome.build();
    }

    @RequestMapping(path = CALC_PAGE)
    public ModelAndView getCalc() throws Exception {
        init();
        return getCalc.build();
    }

    @RequestMapping(path = OPHISTORY_PAGE)
    public ModelAndView getOperationHistory() throws Exception {
        init();
        return getOpHistory.build();
    }

    @RequestMapping(path = ANSWER_PAGE)
    public ModelAndView getAnswer(
            @RequestParam(value = "a") String a,
            @RequestParam(value = "b") String b,
            @RequestParam(value = "operation") String operation,
            HttpSession session) throws Exception {
        init();
        //TODO убрать получение экземпляра напрямую из контекста.
        Map<String, Object> params = new HashMap<>();
        params.put("a", a);
        params.put("b", b);
        params.put("operation", operation);
        params.put("session", session);
        getAnswer.setParams(params);
        return getAnswer.build();
    }

    @RequestMapping(path = TABLES_PAGE)
    @Secured(value = {"ROLE_USER", "ROLE_SUM_SUB", "ROLE_MATH", "ROLE_ADMIN"})
    public ModelAndView getTables(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "mode") String mode,
            @RequestParam(value = "order") String order,
            @RequestParam(value = "table") String table) throws Exception {
        init();
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("mode", mode);
        params.put("order", order);
        params.put("table", table);
        getTables.setParams(params);
        return getTables.build();
    }

    @RequestMapping(path = ABOUT_PAGE)
    public ModelAndView getAbout() throws Exception {
        init();
        return getAbout.build();
    }

    @RequestMapping(path = REGISTRATION_PAGE)
    public ModelAndView getRegistration() throws Exception {
        init();
        return getRegistration.build();
    }

    @RequestMapping(path = LOGIN_PAGE)
    public ModelAndView getLogin() throws Exception {
        init();
        return getLogin.build();
    }

    @RequestMapping(path = REG_PAGE, method = RequestMethod.POST)
    public ModelAndView getReg(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "rpassword") String rpassword) throws Exception {
        init();
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("rpassword", rpassword);
        getReg.setParams(params);
        return getReg.build();
    }

    @RequestMapping(path = PAGE_NOT_FOUND_PAGE)
    public ModelAndView getError() throws Exception {
        init();
        return getError.build();
    }

    @RequestMapping(path = ACCESS_DENIED_PAGE)
    public ModelAndView getAccessDenied() throws Exception {
        init();
        return getAccessDenied.build();
    }

    @RequestMapping(path = ACCOUNTS_MANAGER_PAGE)
    @Secured(value = "ROLE_ADMIN")
    public ModelAndView getAccountsManager() throws Exception {
        init();
        return getAccountsManager.build();
    }

    @Secured(value = "ROLE_ADMIN")
    @RequestMapping(path = ROLE_CHANGE_PAGE, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getRoleChange(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "role") Roles role) throws Exception {
        init();
        jdbc.changeUserRoleInDB(username,role);
    }

}