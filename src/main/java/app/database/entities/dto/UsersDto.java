package app.database.entities.dto;

import app.database.entities.Roles;

import java.util.HashSet;
import java.util.Set;

public class UsersDto {
    private String username;
    private String password;
    private Set<Roles> userroles = new HashSet<>();

    public UsersDto() {
    }

    public UsersDto(String username, String password, Set<Roles> userroles) {
        this.username = username;
        this.password = password;
        this.userroles = userroles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public Set<Roles> getUserroles() {
        return userroles;
    }

    public void setUserroles(Set<Roles> userroles) {
        this.userroles = userroles;
    }

    public Boolean isInRole(Roles role) {
        return this.userroles.contains(role);
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
