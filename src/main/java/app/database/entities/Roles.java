package app.database.entities;

public enum Roles {
    ROLE_ADMIN("Админимтратор"),
    ROLE_USER("Пользователь");
    String name;

    public String getName() {
        return name;
    }

    Roles(String name) {
        this.name = name;
    }
}
