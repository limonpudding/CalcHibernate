package app.database.entities.dto;

import app.database.entities.BinaryOperation;
import app.database.entities.Operation;
import app.database.entities.OperationKind;
import app.math.LongArithmeticImplList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.io.IOException;
import java.sql.Timestamp;

@Entity
@Table(name = "BINARYOPERATION")
public class BinaryOperationDto extends OperationDto {

    protected String firstOperand;

    protected String secondOperand;

    public BinaryOperationDto(){}

    public BinaryOperationDto(String operationKind, String id, String firstOperand, String secondOperand, String answer, String idSession, Timestamp time) {
        this.operationKind = operationKind;
        this.id = id;
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
        this.answer = answer;
        this.idSession = idSession;
        this.time = time;
    }

    @Override
    public Operation toOperation() throws IOException {
        return new BinaryOperation(
                OperationKind.getOperationKind(operationKind),
                id,
                new LongArithmeticImplList(answer),
                idSession,
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
        return idSession;
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
        this.idSession = idsession;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
