package app.database.entities.dto;

import app.database.entities.Operation;
import app.database.entities.OperationKind;
import app.database.entities.SingleOperation;
import app.math.LongArithmeticImplList;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.IOException;
import java.sql.Timestamp;

@Entity
@Table(name = "SINGLEOPERATION")
public class SingleOperationDto extends OperationDto {

    protected String operationKind;

    protected String firstOperand;

    public SingleOperationDto(){}

    public SingleOperationDto(String operationKind, String id, String firstOperand, String answer, String idSession, Timestamp time) {
        this.operationKind = operationKind;
        this.id = id;
        this.firstOperand = firstOperand;
        this.answer = answer;
        this.idSession = idSession;
        this.time = time;
    }

    @Override
    public Operation toOperation() throws IOException {
        return new SingleOperation(
                OperationKind.getOperationKind(operationKind),
                id,
                new LongArithmeticImplList(answer),
                idSession,
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
