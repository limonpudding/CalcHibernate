package app.database.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Operation {
    protected Logger logger;

    protected String name;
    protected String id;
    protected String answer;
    protected String idsession;
    protected java.sql.Timestamp time;

    protected Operation(String name, String id, String answer, String idsession) {
        this.name = name;
        this.id = id;
        this.answer = answer;
        this.idsession = idsession;
        logger = LogManager.getLogger(this.getClass());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
