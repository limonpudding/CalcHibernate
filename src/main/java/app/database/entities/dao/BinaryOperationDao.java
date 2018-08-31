package app.database.entities.dao;

import app.database.entities.*;
import app.database.entities.dao.converters.ConverterLongArithmetic;
import app.math.LongArithmethic;

import javax.persistence.*;
import java.io.IOException;
import java.sql.Timestamp;

@Entity
@Table(name = "BINARYOPERATION")
public class BinaryOperationDao extends OperationDao {

    @Convert(converter = ConverterLongArithmetic.class)
    protected LongArithmethic firstOperand;

    @Convert(converter = ConverterLongArithmetic.class)
    protected LongArithmethic secondOperand;

    public BinaryOperationDao(OperationKind operationKind,
                              String id,
                              LongArithmethic answer,
                              Timestamp time,
                              Sessions session,
                              LongArithmethic firstOperand,
                              LongArithmethic secondOperand) {
        super(operationKind, id, answer, time, session);
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
    }

    public BinaryOperationDao() {
    }

    @Override
    public Operation toOperation() throws IOException {
        return new BinaryOperation(
                operationKind,
                id,
                answer,
                session,
                firstOperand,
                secondOperand
        );
    }

    @Override
    public LongArithmethic getFirstOperand() {
        return firstOperand;
    }

    @Override
    public LongArithmethic getSecondOperand() {
        return secondOperand;
    }

    public void setFirstOperand(LongArithmethic firstOperand) {
        this.firstOperand = firstOperand;
    }

    public void setSecondOperand(LongArithmethic secondOperand) {
        this.secondOperand = secondOperand;
    }
}
