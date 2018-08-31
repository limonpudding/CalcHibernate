package app.database.entities;

import app.database.entities.dao.OperationDao;
import app.database.entities.dao.SingleOperationDao;
import app.math.LongArithmethic;

public class SingleOperation extends Operation {

    private LongArithmethic firstOperand;

    public SingleOperation(OperationKind name, String id, LongArithmethic answer, Sessions session, LongArithmethic firstOperand) {
        super(name, id, answer, session);
        this.firstOperand = firstOperand;
    }

    public SingleOperation(){}

    @Override
    public OperationDao toDto() {
        return new SingleOperationDao(
                operationKind,
                id,
                answer,
                time,
                session,
                firstOperand
        );
    }

    public LongArithmethic getFirstOperand() {
        return firstOperand;
    }

    public void setFirstOperand(LongArithmethic firstOperand) {
        this.firstOperand = firstOperand;
    }

    public String toString() {
        return time + " : " + operationKind.getKind() + "(" + firstOperand + ") = " + answer;
    }
}
