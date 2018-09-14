package app.pages.logic;

import app.database.JDBC;
import app.database.entities.*;
import app.math.Fibonacci;
import app.math.LongArithmethic;
import app.math.LongArithmeticImplList;
import app.math.LongArithmeticMath;
import app.utils.Log;
import app.utils.OperationBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import static app.utils.Log.CALC_FIB_LOG;

@Service("getAnswer")
public class Answer extends Page {

    private static Logger logger = LogManager.getLogger(Answer.class);
    @Autowired
    JDBC jdbc;
    @Autowired
    Logger rootLogger;
    @Autowired
    HttpServletRequest req;
    @Autowired
    SessionFactory sessionFactory;

    @Transactional
    public ModelAndView build(Map params) throws Exception {
        String a = (String) params.get("a");
        String b = (String) params.get("b");
        String operation = (String) params.get("operation");

        String ans = calc(a, b, operation);

        OperationsHistory operationsHistory = new OperationsHistory();
        HttpSession session = (HttpSession) params.get("session");
        operationsHistory.getHistory(session);
        Operation operNew;
        OperationBuilder builder = new OperationBuilder();
        builder.setAnswer(ans);
        builder.setFirstOperand(a);
        builder.setSecondOperand(b);
        builder.setOperationKind(OperationKind.getOperationKind(operation));
        builder.setSession(sessionFactory.getCurrentSession().get(Sessions.class,req.getSession().getId()));
        operNew=builder.build().toOperation();
        jdbc.putOperation(operNew);

        operationsHistory.addOperation(operNew);

        session.getServletContext().setAttribute(session.getId(), operationsHistory.getHistory());
        ModelAndView mav = new ModelAndView();
        mav.setViewName("answer");
        mav.addObject("operationsHistory", operationsHistory.getHistory());
        mav.addObject("answer", ans);
        return mav;
    }

    public static String calc(String strA, String strB, String operation) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        LongArithmethic res;
        LongArithmethic a = new LongArithmeticImplList();
        LongArithmethic b = new LongArithmeticImplList();
        a.setValue(strA);
        if (strB != null) {
            b.setValue(strB);
        }

        if ("fib".equals(operation) && Integer.parseInt(strA) > 50000) {
            ThrowExceptionIfAccessDenied(auth,"ROLE_ADMIN");
            Log.print(logger, Level.WARN, CALC_FIB_LOG, Integer.parseInt(strA));
        }
        switch (operation) {
            case "sum":
                ThrowExceptionIfAccessDenied(auth,"ROLE_SUM_SUB");
                res = LongArithmeticMath.sum(a, b);
                break;
            case "sub":
                ThrowExceptionIfAccessDenied(auth,"ROLE_SUM_SUB");
                res = LongArithmeticMath.sub(a, b);
                break;
            case "mul":
                ThrowExceptionIfAccessDenied(auth,"ROLE_MATH");
                res = LongArithmeticMath.mul(a, b);
                break;
            case "div":
                ThrowExceptionIfAccessDenied(auth,"ROLE_MATH");
                res = LongArithmeticMath.div(a, b);
                break;
            case "fib":
                ThrowExceptionIfAccessDenied(auth,"ROLE_MATH");
                res = new Fibonacci(Integer.parseInt(strA)).number;
                break;
            default:
                throw new IOException("Unexpected operation!");
        }
        return res.toString();
    }

    private static void ThrowExceptionIfAccessDenied(Authentication authentication, String role){
        if(!authentication.getAuthorities().contains(new SimpleGrantedAuthority(role)))
            throw new AccessDeniedException("Доступ запрещён");
    }


}
