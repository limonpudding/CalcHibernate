package app.database.entities.dto;

import app.database.entities.Operation;
import app.database.entities.OperationKind;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.IOException;

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
    abstract String getOperationKind();
    public abstract Operation toOperation() throws IOException;
}
