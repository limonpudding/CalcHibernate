package app.pages.logic;

import app.database.JDBC;
import app.database.entities.*;
import app.math.*;
import app.utils.Log;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import static app.utils.Log.*;

@Service("getAnswer")
public class Answer extends Page {

    private static Logger logger = LogManager.getLogger(Answer.class);
    @Autowired
    JDBC jdbc;
    @Autowired
    Logger rootLogger;
    @Autowired
    HttpServletRequest req;

    public ModelAndView build() throws Exception {
        String a = (String) getParams().get("a");
        String b = (String) getParams().get("b");
        String operation = (String) getParams().get("operation");

        String ans = calc(a, b, operation);

        OperationsHistory operationsHistory = new OperationsHistory();
        HttpSession session = (HttpSession) getParams().get("session");
        operationsHistory.getHistory(session);
        Operation operNew;
        if (OperationKind.getOperationKind(operation) == OperationKind.FIB) {
            operNew = new SingleOperation(OperationKind.getOperationKind(operation), UUID.randomUUID().toString(), new LongArithmeticImplList(ans), new Sessions(req.getSession().getId(),req.getRemoteAddr(),req.getSession().getCreationTime(),req.getSession().getLastAccessedTime()), new LongArithmeticImplList(a));
        } else {
            operNew = new BinaryOperation(OperationKind.getOperationKind(operation), UUID.randomUUID().toString(), new LongArithmeticImplList(ans), new Sessions(req.getSession().getId(),req.getRemoteAddr(),req.getSession().getCreationTime(),req.getSession().getLastAccessedTime()), new LongArithmeticImplList(a), new LongArithmeticImplList(b));
        }

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
            if(!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
                throw new AccessDeniedException("Доступ запрещён");
            Log.print(logger, Level.WARN, CALC_FIB_LOG, Integer.parseInt(strA));
        }
        switch (operation) {
            case "sum":
                if(!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SUM_SUB")))
                    throw new AccessDeniedException("Доступ запрещён");
                res = LongArithmeticMath.sum(a, b);
                break;
            case "sub":
                if(!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_SUM_SUB")))
                    throw new AccessDeniedException("Доступ запрещён");
                res = LongArithmeticMath.sub(a, b);
                break;
            case "mul":
                if(!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MATH")))
                    throw new AccessDeniedException("Доступ запрещён");
                res = LongArithmeticMath.mul(a, b);
                break;
            case "div":
                if(!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MATH")))
                    throw new AccessDeniedException("Доступ запрещён");
                res = LongArithmeticMath.div(a, b);
                break;
            case "fib":
                if(!auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MATH")))
                    throw new AccessDeniedException("Доступ запрещён");
                res = new Fibonacci(Integer.parseInt(strA)).number;
                break;
            default:
                throw new IOException("Unexpected operation!");
        }
        return res.toString();
    }


}
