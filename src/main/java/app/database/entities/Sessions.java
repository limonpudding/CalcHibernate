package app.database.entities;

import app.database.entities.dto.BinaryOperationDto;
import app.database.entities.dto.OperationDto;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SESSIONS")
public class Sessions {

    @Id
    @Column(length = 40)
    private String id;
    @Column(length = 40)
    private String ip;
    private java.sql.Timestamp timeStart;
    private java.sql.Timestamp timeEnd;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "BINARYOPERATION",
            joinColumns = @JoinColumn(name = "IDSESSION"))
    @Column(name = "ID")
    private Set<String> singleOperations = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "SINGLEOPERATION",
            joinColumns = @JoinColumn(name = "IDSESSION"))
    @Column(name = "ID")
    private Set<String> binaryOperations = new HashSet<>();

    public boolean isOperationsExist() {
        return (singleOperations.size() + binaryOperations.size() != 0);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    public java.sql.Timestamp getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(java.sql.Timestamp timestart) {
        this.timeStart = timestart;
    }

    public java.sql.Timestamp getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(java.sql.Timestamp timeend) {
        this.timeEnd = timeend;
    }
}
