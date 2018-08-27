package app.database.entities;

public interface BinaryOperation extends SingleOperation {

    String getSecondoperand();

    void setSecondoperand(String secondoperand);
}
