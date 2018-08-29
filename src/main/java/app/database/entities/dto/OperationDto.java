package app.database.entities.dto;

import app.database.entities.Operation;
import app.database.entities.OperationKind;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.io.IOException;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class OperationDto {
    protected String operationKind;
    @Id
    protected String id;
    protected String answer;
    protected String idSession;
    protected java.sql.Timestamp time;
    abstract String getOperationKind();
    abstract Operation toOperation() throws IOException;
}
