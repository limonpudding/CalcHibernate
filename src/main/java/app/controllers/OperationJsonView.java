package app.controllers;

import app.database.entities.dao.BinaryOperationDao;
import app.database.entities.dao.OperationDao;

public class OperationJsonView {
    private String operationKind;
    private String firstOperand;
    private String secondOperand;
    private String answer;
    private String time;

    public OperationJsonView() {
    }

    public OperationJsonView(OperationDao op) {
        this.operationKind = op.getOperationKind().getFancyName();
        this.firstOperand = op.getFirstOperand().toString();
        if (op instanceof BinaryOperationDao)
            this.secondOperand = op.getSecondOperand().toString();
        this.answer = op.getAnswer().toString();
        this.time = op.getTime().toString();
    }

    public String getOperationKind() {
        return operationKind;
    }

    public void setOperationKind(String operationKind) {
        this.operationKind = operationKind;
    }

    public String getFirstOperand() {
        return firstOperand;
    }

    public void setFirstOperand(String firstOperand) {
        this.firstOperand = firstOperand;
    }

    public String getSecondOperand() {
        return secondOperand;
    }

    public void setSecondOperand(String secondOperand) {
        this.secondOperand = secondOperand;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
