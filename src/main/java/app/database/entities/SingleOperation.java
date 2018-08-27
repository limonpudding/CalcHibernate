package app.database.entities;

import app.math.LongArithmethic;

public class SingleOperation extends Operation {

    private LongArithmethic firstOperand;

    protected SingleOperation(Oper name, String id, String answer, String idsession, LongArithmethic firstOperand) {
        super(name, id, answer, idsession);
        this.firstOperand = firstOperand;
    }

    public LongArithmethic getFirstOperand() {
        return firstOperand;
    }

    public void setFirstOperand(LongArithmethic firstOperand) {
        this.firstOperand = firstOperand;
    }


}
