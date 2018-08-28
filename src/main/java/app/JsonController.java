package app;

import app.database.JDBC;
import app.database.entities.Oper;
import app.database.entities.SingleOperation;
import app.math.LongArithmeticImpl;
import app.math.LongArithmeticImplList;
import app.pages.logic.Answer;
import app.pages.logic.Operation;
import app.rest.Constant;
import app.rest.Key;
import app.rest.UpdatePost;
import app.utils.Log;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static app.utils.Log.*;

@RestController
public class JsonController {
    private final HttpServletRequest req;
    private final JDBC jdbc;
    private final Logger rootLogger;
    private Logger logger = LogManager.getLogger(JsonController.class);
    private final String regex = "^[-+]?[0-9]+$";

    @Autowired
    public JsonController(HttpServletRequest req, JDBC jdbc, Logger rootLogger) {
        this.req = req;
        this.jdbc = jdbc;
        this.rootLogger = rootLogger;
    }

//    @RequestMapping(path = "/rest/test", method = RequestMethod.GET)
//    public @ResponseBody
//    ResponseEntity<app.database.entities.Operation> testEntity(
//            @RequestParam(value = "a") String a,
//            @RequestParam(value = "b") String b,
//            @RequestParam(value = "operation") String operation) throws Exception {
//        String ans = Answer.calc(a, b, operation);
//        app.database.entities.SingleOperation operationObject = new SingleOperation(Oper.FIB,UUID.randomUUID().toString(),new LongArithmeticImplList(ans),req.getSession().getId(),new LongArithmeticImpl(a));
//        jdbc.putOperation(operationObject);
//        return new ResponseEntity<>(operationObject, HttpStatus.OK);
//    }

    @RequestMapping(path = "/rest/calc", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Operation> calc(
            @RequestParam(value = "a") String a,
            @RequestParam(value = "b") String b,
            @RequestParam(value = "operation") String operation) throws Exception {

        if (!a.matches(regex)) {
            a = jdbc.getConstantValueDB(a);
        }
        if (!b.matches(regex)) {
            b = jdbc.getConstantValueDB(b);
        }
        print(logger, Level.INFO, CALC_LOG, req.getRemoteAddr(), operation);
        String ans = Answer.calc(a, b, operation);
        Operation operationObject = new Operation(new Date(), a, b, operation, ans, UUID.randomUUID().toString());
        jdbc.putDataInBD(operationObject);
        return new ResponseEntity<>(operationObject, HttpStatus.OK);
    }

    @RequestMapping(path = "/rest", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateConst(@RequestBody UpdatePost post) {
        String keyOld = post.getKeyOld();
        String keyNew = post.getKeyNew();
        String value = post.getValue();
        Constant constant = new Constant(keyNew, value);
        if (keyNew.matches(regex))
            logger.warn("Попытка переименовать константу в вид, содержащий только число. Её использование будет не возможно, до изменения");
        if (!value.matches(regex))
            logger.warn("Попытка присвоить значение константы, не представляющее собой число");

        jdbc.updatePostDB(keyOld, constant);
        print(logger, Level.INFO, UPDATE_CONST_LOG, req.getRemoteAddr(), keyOld, keyNew, value);
    }

    @RequestMapping(path = "/rest", method = RequestMethod.PUT)
    public @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void putConst(@RequestBody Constant constant) {
        if (constant.getKey().matches(regex))
            logger.warn("Попытка добавить константу, состоящую только из числа. Её использование будет не возможно, до изменения");
        if (!constant.getValue().matches(regex))
            logger.warn("Попытка присвоить значение константы, не представляющее собой число");
        jdbc.putConstInDB(constant);
        print(logger, Level.INFO, PUT_CONST_LOG, req.getRemoteAddr(), constant.getKey(), constant.getValue());
    }

    @RequestMapping(path = "/rest", method = RequestMethod.PATCH)
    public @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateValue(@RequestBody Constant constant) {
        jdbc.updatePatchDB(constant);
        print(logger, Level.INFO, UPDATE_VALUE_LOG, req.getRemoteAddr(), constant.getKey(), constant.getValue());
    }

    @RequestMapping(path = "/rest", method = RequestMethod.DELETE)
    public @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteConst(@RequestBody Key key) {
        jdbc.deleteConstantDB(key.getKey());
        print(logger, Level.INFO, DELETE_CONST_LOG, req.getRemoteAddr(), key);
    }

    @RequestMapping(path = "/rest", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<Constant>> getConstants() {
        List<Constant> constants = jdbc.getConstantsDB();
        print(logger, Level.INFO, GET_CONSTANTS_LOG, req.getRemoteAddr());
        return new ResponseEntity<>(constants, HttpStatus.OK);
    }
}
