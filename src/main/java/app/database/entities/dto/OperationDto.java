package app.database.entities.dto;

import app.database.entities.Operation;

import java.io.IOException;

public interface OperationDto {
    String getOperationKind();
    Operation toOperation() throws IOException;
}
