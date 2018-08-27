package app.database.entities;


public class Sessions {

  private String id;
  private String ip;
  private java.sql.Timestamp timestart;
  private java.sql.Timestamp timeend;


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


  public java.sql.Timestamp getTimestart() {
    return timestart;
  }

  public void setTimestart(java.sql.Timestamp timestart) {
    this.timestart = timestart;
  }


  public java.sql.Timestamp getTimeend() {
    return timeend;
  }

  public void setTimeend(java.sql.Timestamp timeend) {
    this.timeend = timeend;
  }

}
