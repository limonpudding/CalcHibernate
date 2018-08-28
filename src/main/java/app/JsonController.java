package app;

import app.database.JDBC;
import app.database.entities.Constants;
import app.database.entities.Operation;
import app.database.entities.OperationKind;
import app.database.entities.dto.BinaryOperationDto;
import app.database.entities.dto.OperationDto;
import app.database.entities.dto.SingleOperationDto;
import app.pages.logic.Answer;
import app.rest.Key;
import app.rest.UpdatePost;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static app.utils.Log.*;

@RestController
public class JsonController extends AbstractController{
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
//        app.database.entities.SingleOperation operationObject = new SingleOperation(OperationKind.FIB,UUID.randomUUID().toString(),new LongArithmeticImplList(ans),req.getSession().getId(),new LongArithmeticImpl(a));
//        jdbc.putOperation(operationObject);
//        return new ResponseEntity<>(operationObject, HttpStatus.OK);
//    }

    @RequestMapping(path = "/rest/calc", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<OperationDto> calc(
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
        OperationDto operationDto;
        if (OperationKind.getOperationKind(operation)==OperationKind.FIB) {
            operationDto = new SingleOperationDto(
                    operation,
                    UUID.randomUUID().toString(),
                    a,
                    ans,
                    req.getSession().getId(),
                    new Timestamp(new Date().getTime())
            );
        } else {
            operationDto = new BinaryOperationDto(
                    operation,
                    UUID.randomUUID().toString(),
                    a,
                    b,
                    ans,
                    req.getSession().getId(),
                    new Timestamp(new Date().getTime())
            );
        }
        Operation operationObject = operationDto.toOperation();
        jdbc.putOperation(operationObject);
        return new ResponseEntity<>(operationDto, HttpStatus.OK);
    }

    @RequestMapping(path = "/rest", method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateConst(@RequestBody UpdatePost post) {
        init();
        String keyOld = post.getKeyOld();
        String keyNew = post.getKeyNew();
        String value = post.getValue();
        Constants constant = new Constants(keyNew, value);
        if (keyNew.matches(regex))
            print(logger, Level.WARN, "Попытка переименовать константу в вид, содержащий только число. Её использование будет не возможно, до изменения");
        if (!value.matches(regex))
            print(logger, Level.WARN, "Попытка присвоить значение константы, не представляющее собой число");

        jdbc.updatePostDB(keyOld, constant);
        print(logger, Level.INFO, UPDATE_CONST_LOG, req.getRemoteAddr(), keyOld, keyNew, value);
    }

    @RequestMapping(path = "/rest", method = RequestMethod.PUT)
    public @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void putConst(@RequestBody Constants constant) {
        init();
        if (constant.getKey().matches(regex))
            print(logger, Level.WARN, "Попытка добавить константу, состоящую только из числа. Её использование будет не возможно, до изменения");
        if (!constant.getValue().matches(regex))
            print(logger, Level.WARN,"Попытка присвоить значение константы, не представляющее собой число");
        jdbc.putConstInDB(constant);
        print(logger, Level.INFO, PUT_CONST_LOG, req.getRemoteAddr(), constant.getKey(), constant.getValue());
    }

    @RequestMapping(path = "/rest", method = RequestMethod.PATCH)
    public @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateValue(@RequestBody Constants constant) {
        init();
        jdbc.updatePatchDB(constant);
        print(logger, Level.INFO, UPDATE_VALUE_LOG, req.getRemoteAddr(), constant.getKey(), constant.getValue());
    }

    @RequestMapping(path = "/rest", method = RequestMethod.DELETE)
    public @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteConst(@RequestBody Key key) {
        init();
        jdbc.deleteConstantDB(key.getKey());
        print(logger, Level.INFO, DELETE_CONST_LOG, req.getRemoteAddr(), key);
    }

    @RequestMapping(path = "/rest", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<Constants>> getConstants() {
        init();
        List<Constants> constants = jdbc.getConstantsDB();
        print(logger, Level.INFO, GET_CONSTANTS_LOG, req.getRemoteAddr());
        return new ResponseEntity<>(constants, HttpStatus.OK);
    }
}
