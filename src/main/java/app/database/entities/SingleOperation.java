package app.database.entities;

import app.database.entities.dto.OperationDto;
import app.database.entities.dto.SingleOperationDto;
import app.math.LongArithmethic;

public class SingleOperation extends Operation {

    private LongArithmethic firstOperand;

    public SingleOperation(OperationKind name, String id, LongArithmethic answer, Sessions session, LongArithmethic firstOperand) {
        super(name, id, answer, session);
        this.firstOperand = firstOperand;
    }

    public SingleOperation(){}

    @Override
    public OperationDto toDto() {
        return new SingleOperationDto(
                operationKind.getKind(),
                id,
                answer.toString(),
                time,
                session,
                firstOperand.toString()
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
