package app.database.entities;

public class SingleOperation extends Operation {

    private String name;
    private String firstoperand;

    public String getFirstoperand() {
        return firstoperand;
    }

    public void setFirstoperand(String firstoperand) {
        this.firstoperand = firstoperand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
