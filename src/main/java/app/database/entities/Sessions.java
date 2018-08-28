package app.database.entities;

import javax.persistence.*;

@Entity
@Table(name = "SESSIONS")
public class Sessions {

  private String id;
  private String ip;
  private java.sql.Timestamp timestart;
  private java.sql.Timestamp timeend;

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
  public java.sql.Timestamp getTimestart() {
    return timestart;
  }

  public void setTimestart(java.sql.Timestamp timestart) {
    this.timestart = timestart;
  }

  @Basic
  @Column(name = "timeend")
  public java.sql.Timestamp getTimeend() {
    return timeend;
  }

  public void setTimeend(java.sql.Timestamp timeend) {
    this.timeend = timeend;
  }

}
