package app.database.entities;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;

import javax.persistence.*;

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

    @Transient
//    @Formula("(select case when count(*) >= 1 then 'true' else 'false' end from (\n" +
//            "    select 'true' as operation from SESSIONS join BINARYOPERATION on SESSIONS.ID = BINARYOPERATION.IDSESSION where rownum = 1\n" +
//            "    union all\n" +
//            "    select 'true' as operation from SESSIONS join SINGLEOPERATION on SESSIONS.ID = SINGLEOPERATION.IDSESSION where rownum = 1\n" +
//            ") where rownum = 1)")
    @Formula("(select 'false' from dual)")
    private String operation;

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
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
