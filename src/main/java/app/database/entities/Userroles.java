package app.database.entities;

import app.database.entities.Roles;
import app.database.entities.Users;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "USERROLES")
public class Userroles implements Serializable {

    @Id
    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Roles role;

    @Id
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


    @Override
    public boolean equals(Object obj) {
        Userroles o = (Userroles)obj;
        return role.equals(o.role)&&user.getUsername().equals(o.getUser().getUsername());
    }
}
