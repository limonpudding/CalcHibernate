package app.database.entities;


public class Fib implements SingleOperation {

  private String id;
  private String firstoperand;
  private String answer;
  private String idsession;
  private java.sql.Timestamp time;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getFirstoperand() {
    return firstoperand;
  }

  public void setFirstoperand(String firstoperand) {
    this.firstoperand = firstoperand;
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
