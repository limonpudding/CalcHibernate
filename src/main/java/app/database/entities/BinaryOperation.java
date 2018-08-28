package app.database.entities;

import app.math.LongArithmethic;

public class BinaryOperation extends Operation {

    private LongArithmethic firstOperand;
    private LongArithmethic secondOperand;

    protected BinaryOperation(OperationKind name, String id, LongArithmethic answer, String idsession, LongArithmethic firstOperand, LongArithmethic secondOperand) {
        super(name, id, answer, idsession);
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
    }

    public BinaryOperation(){}

    public LongArithmethic getFirstoperand() {
        return firstOperand;
    }

    public void setFirstoperand(LongArithmethic firstoperand) {
        this.firstOperand = firstoperand;
    }

    public LongArithmethic getSecondOperand() {
        return secondOperand;
    }

    public void setSecondOperand(LongArithmethic secondOperand) {
        this.secondOperand = secondOperand;
    }
}
