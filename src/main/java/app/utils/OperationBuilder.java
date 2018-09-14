package app.utils;

import app.database.entities.OperationKind;
import app.database.entities.Sessions;
import app.database.entities.dao.BinaryOperationDao;
import app.database.entities.dao.OperationDao;
import app.database.entities.dao.SingleOperationDao;
import app.math.LongArithmethic;
import app.math.LongArithmeticImplList;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.UUID;

public class OperationBuilder {
    private OperationKind operationKind;
    private String id;
    private LongArithmethic answer;
    private java.sql.Timestamp time;
    private Sessions session;
    private LongArithmethic firstOperand;
    private LongArithmethic secondOperand;

    public OperationBuilder() {
        time = new Timestamp(new java.util.Date().getTime());
        secondOperand = null;
        id = UUID.randomUUID().toString();
    }

    public void setOperationKind(OperationKind operationKind) {
        this.operationKind = operationKind;
    }

    public void setAnswer(LongArithmethic answer) {
        this.answer = answer;
    }

    public void setAnswer(String answer) throws IOException {
        if (answer==null)
            answer="0";
        this.answer = new LongArithmeticImplList(answer);
    }

    public void setFirstOperand(String firstOperand) throws IOException {
        if (firstOperand==null)
            firstOperand="0";
        this.firstOperand = new LongArithmeticImplList(firstOperand);
    }

    public void setSecondOperand(String secondOperand) throws IOException {
        if (secondOperand==null)
            secondOperand="0";
        this.secondOperand = new LongArithmeticImplList(secondOperand);
    }

    public void setSession(Sessions session) {
        this.session = session;
    }

    public void setFirstOperand(LongArithmethic firstOperand) {
        this.firstOperand = firstOperand;
    }

    public void setSecondOperand(LongArithmethic secondOperand) {
        this.secondOperand = secondOperand;
    }

    public OperationDao build(){
        if (operationKind==OperationKind.FIB) {
            return new SingleOperationDao(operationKind,id,answer,time,session,firstOperand);
        } else {
            return new BinaryOperationDao(operationKind,id,answer,time,session,firstOperand,secondOperand);
        }
    }
}
