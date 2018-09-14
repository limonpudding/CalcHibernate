package app.controllers;

import app.database.JDBC;
import app.database.entities.Constants;
import app.database.entities.OperationKind;
import app.database.entities.Sessions;
import app.database.entities.dao.OperationDao;
import app.math.LongArithmeticImplList;
import app.pages.logic.Answer;
import app.rest.Key;
import app.rest.UpdatePost;
import app.utils.OperationBuilder;
import app.utils.PageNamesConstants;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static app.utils.Log.*;

@RestController
public class JsonController extends AbstractController {
    private final HttpServletRequest req;
    private final JDBC jdbc;
    private final Logger rootLogger;
    private Logger logger = LogManager.getLogger(JsonController.class);
    private final String regex = "^[-+]?[0-9]+$";

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    public JsonController(HttpServletRequest req, JDBC jdbc, Logger rootLogger) {
        this.req = req;
        this.jdbc = jdbc;
        this.rootLogger = rootLogger;
    }

    @RequestMapping(path = PageNamesConstants.REST_CALC_PAGE, method = RequestMethod.GET)
    public @ResponseBody
    String calc(
            @RequestParam(value = "a") String a,
            @RequestParam(value = "b", required = false) String b,
            @RequestParam(value = "operation") String operation) throws Exception {
        init();
        if (!a.matches(regex)) {
            a = jdbc.getConstantValueDB(a);
        }
        if (b != null && !b.matches(regex)) {
            b = jdbc.getConstantValueDB(b);
        }
        print(logger, Level.INFO, CALC_LOG, req.getRemoteAddr(), operation);
        String ans = Answer.calc(a, b, operation);
        OperationDao operationDao;
        OperationBuilder builder = new OperationBuilder();
        builder.setAnswer(ans);
        builder.setFirstOperand(a);
        builder.setSecondOperand(b);
        builder.setOperationKind(OperationKind.getOperationKind(operation));
        builder.setSession(sessionFactory.getCurrentSession().get(Sessions.class,req.getSession().getId()));
        operationDao=builder.build();
        jdbc.putOperation(operationDao.toOperation());
        return operationDao.getAnswer().toString();
    }

    @RequestMapping(path = PageNamesConstants.REST_PAGE, method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateConst(@RequestBody UpdatePost post) {
        init();
        if (post.getKeyNew().matches(regex))
            print(logger, Level.WARN, INCORRECT_UPDATE_KEY);
        if (!post.getValue().matches(regex))
            print(logger, Level.WARN, INCORRECT_VALUE);
        jdbc.updatePostDB(post);
        print(logger, Level.INFO, UPDATE_CONST_LOG, req.getRemoteAddr(), post.getKeyOld(), post.getKeyNew(), post.getValue());
    }

    @RequestMapping(path = PageNamesConstants.REST_PAGE, method = RequestMethod.PUT)
    public @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void putConst(@RequestBody Constants constant) {
        init();
        if (constant.getKey().matches(regex))
            print(logger, Level.WARN, INCORRECT_ADD_KEY);
        if (!constant.getValue().matches(regex))
            print(logger, Level.WARN, INCORRECT_VALUE);
        jdbc.putConstInDB(constant);
        print(logger, Level.INFO, PUT_CONST_LOG, req.getRemoteAddr(), constant.getKey(), constant.getValue());
    }

    @RequestMapping(path = PageNamesConstants.REST_PAGE, method = RequestMethod.PATCH)
    public @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateValue(@RequestBody Constants constant) {
        init();
        jdbc.updatePatchDB(constant);
        print(logger, Level.INFO, UPDATE_VALUE_LOG, req.getRemoteAddr(), constant.getKey(), constant.getValue());
    }

    @RequestMapping(path = PageNamesConstants.REST_PAGE, method = RequestMethod.DELETE)
    public @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteConst(@RequestBody Key key) {
        init();
        jdbc.deleteConstantDB(key);
        print(logger, Level.INFO, DELETE_CONST_LOG, req.getRemoteAddr(), key);
    }

    @RequestMapping(path = PageNamesConstants.REST_PAGE, method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity getConstants() {
        init();
        print(logger, Level.INFO, GET_CONSTANTS_LOG, req.getRemoteAddr());
        return new ResponseEntity<>(jdbc.getConstantsDB(), HttpStatus.OK);
    }
}
