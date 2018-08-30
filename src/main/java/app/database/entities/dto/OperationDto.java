package app.database.entities.dto;

import app.database.entities.Operation;
import app.database.entities.Sessions;

import javax.persistence.*;
import java.io.IOException;
import java.sql.Timestamp;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class OperationDto {
    protected String operationKind;
    @Id
    protected String id;
    protected String answer;
    protected String idSession;
    protected java.sql.Timestamp time;

    public abstract String getSecondOperand();

    public abstract String getFirstOperand();

    public abstract Operation toOperation() throws IOException;

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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getIdSession() {
        return idSession;
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
