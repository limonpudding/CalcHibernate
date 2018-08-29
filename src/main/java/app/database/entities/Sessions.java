package app.database.entities;

import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Entity
@Table(name = "SESSIONS")
public class Sessions {

    private String id;
    private String ip;
    private java.sql.Timestamp timeStart;
    private java.sql.Timestamp timeEnd;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "timestart")
    public java.sql.Timestamp getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(java.sql.Timestamp timestart) {
        this.timeStart = timestart;
    }

    @Basic
    @Column(name = "timeend")
    public java.sql.Timestamp getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(java.sql.Timestamp timeend) {
        this.timeEnd = timeend;
    }

    @Formula("EXIST (select TOP(1) * from SESSIONS join BINARYOPERATION on SESSION.ID=BINARYOPERATION.IDSESSION" +
            " UNION ALL " +
            "select TOP(1) * from SESSIONS join SINGLEOPERATION on SESSION.ID=SINGLEOPERATION.IDSESSION)")
    public String operation;
}
