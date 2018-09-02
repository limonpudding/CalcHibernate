package app.database.entities;

import javax.persistence.*;

@Entity
@Table(name = "USERS")
public class Users {
    @Id
    @Column(name = "USERNAME")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    protected Roles role;
}
