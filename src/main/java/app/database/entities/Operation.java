package app.database.entities;

import app.math.LongArithmethic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.Date;

public abstract class Operation {
    protected Logger logger;

    protected Oper name;
    protected String id;
    protected LongArithmethic answer;
    protected String idsession;
    protected java.sql.Timestamp time;

    protected Operation(Oper name, String id, LongArithmethic answer, String idsession) {
        this.name = name;
        this.id = id;
        this.answer = answer;
        this.idsession = idsession;
        logger = LogManager.getLogger(this.getClass());
        time = new Timestamp(new Date().getTime());
    }

    public Oper getName() {
        return name;
    }

    public void setName(Oper name) {
        this.name = name;
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

    public String getIdsession() {
        return idsession;
    }

    public void setIdsession(String idsession) {
        this.idsession = idsession;
    }

    public java.sql.Timestamp getTime() {
        return time;
    }

    public void setTime(java.sql.Timestamp time) {
        this.time = time;
    }
}
