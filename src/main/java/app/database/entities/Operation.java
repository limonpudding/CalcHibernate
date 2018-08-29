package app.database.entities;

import app.math.LongArithmethic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

public abstract class Operation {
    protected Logger logger;

    protected OperationKind operationKind;
    protected String id;
    protected LongArithmethic answer;
    protected String idsession;
    protected java.sql.Timestamp time;

    protected Operation(OperationKind name, String id, LongArithmethic answer, String idsession) {
        this.operationKind = name;
        this.id = id;
        this.answer = answer;
        this.idsession = idsession;
        logger = LogManager.getLogger(this.getClass());
        time = new Timestamp(new Date().getTime());
    }

    protected Operation(){}

    public OperationKind getOperationKind() {
        return operationKind;
    }

    public void setOperationKind(OperationKind name) {
        this.operationKind = name;
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

    public String getIdsession() {
        return idsession;
    }

    public void setIdsession(String idsession) {
        this.idsession = idsession;
    }

    public java.sql.Timestamp getTime() {
        return time;
    }

    public void setTime(java.sql.Timestamp time) {
        this.time = time;
    }

    @Override
    public abstract String toString();
}
