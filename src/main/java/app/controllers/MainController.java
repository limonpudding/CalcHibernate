package app.controllers;

import app.database.JDBC;
import app.pages.logic.Page;
import app.utils.PageNamesConstants;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController extends AbstractController {

    private final HttpServletRequest req;
    private final JDBC jdbc;
    private final Page getAbout;
    private final Page getHome;
    private final Page getTables;
    private final Page getAnswer;
    private final Page getError;
    private final Page getCalc;
    private final Page getOpHistory;
    private final Logger rootLogger;

    @Autowired
    public MainController(HttpServletRequest req, JDBC jdbc, Page getAbout, Page getHome, Page getTables, Page getAnswer, Page getError, Page getCalc, Page getOpHistory, Logger rootLogger) {
        this.req = req;
        this.jdbc = jdbc;
        this.getAbout = getAbout;
        this.getHome = getHome;
        this.getTables = getTables;
        this.getAnswer = getAnswer;
        this.getError = getError;
        this.getCalc = getCalc;
        this.getOpHistory = getOpHistory;
        this.rootLogger = rootLogger;
    }

    //TODO привязать через Autowired и Qualifier реализации созданного абстрактного класса для каждого представления свою.

    @RequestMapping(path = PageNamesConstants.ROOT_PAGE)
    public ModelAndView getHome() throws Exception {
        init();
        return getHome.build();
    }

    @RequestMapping(path = PageNamesConstants.CALC_PAGE)
    public ModelAndView getCalc() throws Exception {
        init();
        return getCalc.build();
    }

    @RequestMapping(path = PageNamesConstants.OPHISTORY_PAGE)
    public ModelAndView getOperationHistory() throws Exception {
        init();
        return getOpHistory.build();
    }

    @RequestMapping(path = PageNamesConstants.ANSWER_PAGE)
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

    @RequestMapping(path = PageNamesConstants.TABLES_PAGE)
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

    @RequestMapping(path = PageNamesConstants.ABOUT_PAGE)
    public ModelAndView getAbout() throws Exception {
        init();
        return getAbout.build();
    }

    @RequestMapping(path = PageNamesConstants.PAGE_NOT_FOUND_PAGE)
    public ModelAndView getError() throws Exception {
        init();
        return getError.build();
    }

}