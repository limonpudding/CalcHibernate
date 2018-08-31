package app.database.entities.dao;

import app.database.entities.Operation;
import app.database.entities.OperationKind;
import app.database.entities.Sessions;
import app.database.entities.dao.converters.ConverterLongArithmetic;
import app.math.LongArithmethic;

import javax.persistence.*;
import java.io.IOException;
import java.sql.Timestamp;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class OperationDao {
    @Enumerated(EnumType.STRING)
    protected OperationKind operationKind;
    @Id
    protected String id;
    @Convert(converter = ConverterLongArithmetic.class)
    protected LongArithmethic answer;
    protected java.sql.Timestamp time;

    @ManyToOne(optional = false)
    @JoinColumn(name = "IDSESSION", nullable = false)
    protected Sessions session;

    public OperationDao(OperationKind operationKind, String id, LongArithmethic answer, Timestamp time, Sessions session) {
        this.operationKind = operationKind;
        this.id = id;
        this.answer = answer;
        this.time = time;
        this.session = session;
    }

    protected OperationDao() {
    }

    public abstract LongArithmethic getSecondOperand();

    public abstract LongArithmethic getFirstOperand();

    public abstract Operation toOperation() throws IOException;

    public OperationKind getOperationKind() {
        return operationKind;
    }

    public void setOperationKind(OperationKind operationKind) {
        this.operationKind = operationKind;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LongArithmethic getAnswer() {
        return answer;
    }

    public void setAnswer(LongArithmethic answer) {
        this.answer = answer;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
