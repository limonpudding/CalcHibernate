package app.database.entities.dto;

import app.database.entities.Operation;
import app.database.entities.OperationKind;
import app.database.entities.SingleOperation;
import app.math.LongArithmethic;
import app.math.LongArithmeticImplList;

import java.io.IOException;
import java.sql.Timestamp;

public class SingleOperationDto implements OperationDto {
    protected String operationKind;
    protected String id;
    protected String firstOperand;
    protected String answer;
    protected String idsession;
    protected java.sql.Timestamp time;

    public SingleOperationDto(String operationKind, String id, String firstOperand, String answer, String idsession, Timestamp time) {
        this.operationKind = operationKind;
        this.id = id;
        this.firstOperand = firstOperand;
        this.answer = answer;
        this.idsession = idsession;
        this.time = time;
    }

    @Override
    public Operation toOperation() throws IOException {
        return new SingleOperation(
                OperationKind.getOperationKind(operationKind),
                id,
                new LongArithmeticImplList(answer),
                idsession,
                new LongArithmeticImplList(firstOperand)
        );
    }

    public String getOperationKind() {
        return operationKind;
    }

    public void setOperationKind(String operationKind) {
        this.operationKind = operationKind;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstOperand() {
        return firstOperand;
    }

    public void setFirstOperand(String firstOperand) {
        this.firstOperand = firstOperand;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getIdsession() {
        return idsession;
    }

    public void setIdsession(String idsession) {
        this.idsession = idsession;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
