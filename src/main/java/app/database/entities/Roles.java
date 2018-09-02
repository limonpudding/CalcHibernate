package app.database.entities;

public enum Roles {
    ROLE_ADMIN("Админимтратор"),
    ROLE_USER("Пользователь"),
    ROLE_ANONYMOUS("Не авторизированный");
    String name;

    public String getName() {
        return name;
    }

    Roles(String name) {
        this.name = name;
    }
}
