package app.controllers;

import app.database.entities.Sessions;

public class SessionJsonView {
    private int operationsCount;
    private String id;
    private String ip;
    private String timeStart;
    private String timeEnd;

    public SessionJsonView() {
    }

    public SessionJsonView(int operationsCount, String id, String ip, String timeStart, String timeEnd) {
        this.operationsCount = operationsCount;
        this.id = id;
        this.ip = ip;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public SessionJsonView(Sessions session) {
        this.id = session.getId();
        this.ip = session.getIp();
        this.timeStart = session.getTimeStart().toString();
        this.timeEnd = session.getTimeEnd().toString();
        this.operationsCount = session.getOperations().size();
    }

    public int getOperationsCount() {
        return operationsCount;
    }

    public void setOperationsCount(int operationsCount) {
        this.operationsCount = operationsCount;
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

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }
}
