package app.database.entities;

public interface Operation {

    String getId();

    void setId(String id);

    String getAnswer();

    void setAnswer(String answer);

    String getIdsession();

    void setIdsession(String idsession);

    java.sql.Timestamp getTime();

    void setTime(java.sql.Timestamp time);
}
