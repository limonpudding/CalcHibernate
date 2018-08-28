package app.database.entities;

import app.math.LongArithmethic;

public class SingleOperation extends Operation {

    private LongArithmethic firstOperand;

    public SingleOperation(OperationKind name, String id, LongArithmethic answer, String idsession, LongArithmethic firstOperand) {
        super(name, id, answer, idsession);
        this.firstOperand = firstOperand;
    }

    public SingleOperation(){}

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
