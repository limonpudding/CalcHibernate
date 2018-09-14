package app.database.entities;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "USERROLES")
public class Userroles implements Serializable {

    public int getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Roles role;

    protected String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }


    @Override
    public boolean equals(Object obj) {
        Userroles o = (Userroles) obj;
        return role.equals(o.role) && username.equals(o.getUsername());
    }
}
