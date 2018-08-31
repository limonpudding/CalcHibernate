package app.database.entities.dao;

import app.database.entities.Operation;
import app.database.entities.OperationKind;
import app.database.entities.Sessions;
import app.database.entities.SingleOperation;
import app.database.entities.dao.converters.ConverterLongArithmetic;
import app.math.LongArithmethic;

import javax.persistence.*;
import java.io.IOException;
import java.sql.Timestamp;

@Entity
@Table(name = "SINGLEOPERATION")
public class SingleOperationDao extends OperationDao {

    @Convert(converter = ConverterLongArithmetic.class)
    protected LongArithmethic firstOperand;

    public SingleOperationDao(OperationKind operationKind,
                              String id,
                              LongArithmethic answer,
                              Timestamp time,
                              Sessions session,
                              LongArithmethic firstOperand) {
        super(operationKind, id, answer, time, session);
        this.firstOperand = firstOperand;
    }

    public SingleOperationDao() {
    }

    @Override
    public Operation toOperation() throws IOException {
        return new SingleOperation(
                operationKind,
                id,
                answer,
                session,
                firstOperand
        );
    }

    @Override
    public LongArithmethic getSecondOperand() {
        return null;
    }

    @Override
    public LongArithmethic getFirstOperand() {
        return firstOperand;
    }

    public void setFirstOperand(LongArithmethic firstOperand) {
        this.firstOperand = firstOperand;
    }
}
