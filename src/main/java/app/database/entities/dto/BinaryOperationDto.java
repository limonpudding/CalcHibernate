package app.database.entities.dto;

import app.database.entities.*;
import app.math.LongArithmeticImplList;

import javax.persistence.*;
import java.io.IOException;
import java.sql.Timestamp;

@Entity
@Table(name = "BINARYOPERATION")
public class BinaryOperationDto extends OperationDto {

    protected String firstOperand;

    protected String secondOperand;

    public BinaryOperationDto(String operationKind, String id, String answer, Timestamp time, Sessions session, String firstOperand, String secondOperand) {
        super(operationKind, id, answer, time, session);
        this.firstOperand=firstOperand;
        this.secondOperand=secondOperand;
    }

    public BinaryOperationDto() {
    }

    @Override
    public Operation toOperation() throws IOException {
        return new BinaryOperation(
                OperationKind.getOperationKind(operationKind),
                id,
                new LongArithmeticImplList(answer),
                session,
                new LongArithmeticImplList(firstOperand),
                new LongArithmeticImplList(secondOperand)
        );
    }

    @Override
    public String getFirstOperand() {
        return firstOperand;
    }

    @Override
    public String getSecondOperand() {
        return secondOperand;
    }

    public void setFirstOperand(String firstOperand) {
        this.firstOperand = firstOperand;
    }

    public void setSecondOperand(String secondOperand) {
        this.secondOperand = secondOperand;
    }
}
