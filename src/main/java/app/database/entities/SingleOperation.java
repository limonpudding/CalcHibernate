package app.database.entities;

public interface SingleOperation extends Operation {
    String getFirstoperand();

    void setFirstoperand(String firstoperand);
}
