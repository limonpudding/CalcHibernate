package app.database.entities.dto;

import app.database.entities.BinaryOperation;
import app.database.entities.Operation;
import app.database.entities.OperationKind;
import app.math.LongArithmeticImplList;

import java.io.IOException;
import java.sql.Timestamp;

public class BinaryOperationDto implements OperationDto {
    protected String operationKind;
    protected String id;
    protected String firstOperand;
    protected String secondOperand;
    protected String answer;
    protected String idsession;
    protected java.sql.Timestamp time;

    public BinaryOperationDto(String operationKind, String id, String firstOperand, String secondOperand, String answer, String idsession, Timestamp time) {
        this.operationKind = operationKind;
        this.id = id;
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
        this.answer = answer;
        this.idsession = idsession;
        this.time = time;
    }

    @Override
    public Operation toOperation() throws IOException {
        return new BinaryOperation(
                OperationKind.getOperationKind(operationKind),
                id,
                new LongArithmeticImplList(answer),
                idsession,
                new LongArithmeticImplList(firstOperand),
                new LongArithmeticImplList(secondOperand)
        );
    }

    public String getOperationKind() {
        return operationKind;
    }

    public String getId() {
        return id;
    }

    public String getFirstOperand() {
        return firstOperand;
    }

    public String getSecondOperand() {
        return secondOperand;
    }

    public String getAnswer() {
        return answer;
    }

    public String getIdsession() {
        return idsession;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setOperationKind(String operationKind) {
        this.operationKind = operationKind;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstOperand(String firstOperand) {
        this.firstOperand = firstOperand;
    }

    public void setSecondOperand(String secondOperand) {
        this.secondOperand = secondOperand;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setIdsession(String idsession) {
        this.idsession = idsession;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
