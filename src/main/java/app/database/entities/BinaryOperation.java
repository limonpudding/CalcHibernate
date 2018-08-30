package app.database.entities;

import app.database.entities.dto.BinaryOperationDto;
import app.database.entities.dto.OperationDto;
import app.math.LongArithmethic;

public class BinaryOperation extends Operation {

    private LongArithmethic firstOperand;
    private LongArithmethic secondOperand;

    public BinaryOperation(OperationKind name, String id, LongArithmethic answer, Sessions session, LongArithmethic firstOperand, LongArithmethic secondOperand) {
        super(name, id, answer, session);
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
    }

    public OperationDto toDto() {
        return new BinaryOperationDto(
                operationKind.getKind(),
                id,
                answer.toString(),
                time,
                session,
                firstOperand.toString(),
                secondOperand.toString()
        );
    }

    public BinaryOperation() {
    }

    public LongArithmethic getFirstOperand() {
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

    public String toString() {
        return time + " : " + firstOperand + " " + operationKind.getKind() + " " + secondOperand + " = " + answer;
    }
}
