package app.database.entities;

import app.database.entities.dao.OperationDao;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

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

    public Sessions(String id, String ip, long timeStart, long timeEnd) {
        this.id = id;
        this.ip = ip;
        this.timeStart = new Timestamp(timeStart);
        this.timeEnd = new Timestamp(timeEnd);
    }

    @OneToMany(mappedBy = "session", fetch = FetchType.EAGER)
    private List<OperationDao> operations;

    public List<OperationDao> getOperations() {
        return operations;
    }

    public void setOperations(List<OperationDao> operations) {
        this.operations = operations;
    }

    public Sessions() {
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
