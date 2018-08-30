package app.database.entities;

import app.database.entities.dto.OperationDto;
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
    protected Sessions session;
    protected java.sql.Timestamp time;

    protected Operation(OperationKind name, String id, LongArithmethic answer, Sessions session) {
        this.operationKind = name;
        this.id = id;
        this.answer = answer;
        this.session = session;
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

    public Sessions getSession() {
        return session;
    }

    public void setSession(Sessions session) {
        this.session = session;
    }

    public java.sql.Timestamp getTime() {
        return time;
    }

    public void setTime(java.sql.Timestamp time) {
        this.time = time;
    }

    public abstract OperationDto toDto();

    @Override
    public abstract String toString();
}
