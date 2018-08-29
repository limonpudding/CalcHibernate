package app.database.entities.dto;

import app.database.entities.Operation;
import app.database.entities.OperationKind;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.IOException;
import java.sql.Timestamp;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class OperationDto {
    protected String operationKind;
    @Id
    @Column(length = 40)
    protected String id;
    protected String answer;
    @Column(length = 40)
    protected String idSession;
    protected java.sql.Timestamp time;
    public abstract String getOperationKind();
    public abstract String getSecondOperand();
    public abstract Operation toOperation() throws IOException;
    public abstract String getFirstOperand();
    public abstract String getAnswer();
    public abstract String getIdsession();
    public abstract Timestamp getTime();
    public abstract String getId();
}
