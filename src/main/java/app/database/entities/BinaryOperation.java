package app.database.entities;

public class BinaryOperation extends Operation {

    private String firstoperand;
    private String secondoperand;

    public String getFirstoperand() {
        return firstoperand;
    }

    public void setFirstoperand(String firstoperand) {
        this.firstoperand = firstoperand;
    }

    public String getSecondoperand() {
        return secondoperand;
    }

    public void setSecondoperand(String secondoperand) {
        this.secondoperand = secondoperand;
    }
}
