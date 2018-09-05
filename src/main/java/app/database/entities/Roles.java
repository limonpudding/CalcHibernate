package app.database.entities;

public enum Roles {
    ROLE_ADMIN("Администратор"),
    ROLE_USER("Пользователь"),
    ROLE_SUM_SUB("Арифметик"),
    ROLE_MATH("Математик");
    String name;

    public String getName() {
        return name;
    }

    Roles(String name) {
        this.name = name;
    }
}
