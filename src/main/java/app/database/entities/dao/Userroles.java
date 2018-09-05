package app.database.entities.dao;

import app.database.entities.Roles;
import app.database.entities.Users;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "USERROLES")
public class Userroles implements Serializable {

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Roles role;

    @ManyToOne(optional = false)
    @JoinColumn(name = "USERNAME", nullable = false)
    protected Users user;

    public Users getUser() {
        return user;
    }

    public void setUser(Users username) {
        this.user = username;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    @Id
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
