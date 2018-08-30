package app.database.entities.dto;

import app.database.entities.Operation;
import app.database.entities.OperationKind;
import app.database.entities.Sessions;
import app.database.entities.SingleOperation;
import app.math.LongArithmeticImplList;

import javax.persistence.*;
import java.io.IOException;
import java.sql.Timestamp;

@Entity
@Table(name = "SINGLEOPERATION")
public class SingleOperationDto extends OperationDto {

    protected String firstOperand;

    public SingleOperationDto(String operationKind, String id, String answer, Timestamp time, Sessions session, String firstOperand) {
        super(operationKind, id, answer, time, session);
        this.firstOperand = firstOperand;
    }

    public SingleOperationDto() {
    }

    @Override
    public Operation toOperation() throws IOException {
        return new SingleOperation(
                OperationKind.getOperationKind(operationKind),
                id,
                new LongArithmeticImplList(answer),
                session,
                new LongArithmeticImplList(firstOperand)
        );
    }

    @Override
    public String getSecondOperand() {
        return "";
    }

    @Override
    public String getFirstOperand() {
        return firstOperand;
    }

    public void setFirstOperand(String firstOperand) {
        this.firstOperand = firstOperand;
    }
}
