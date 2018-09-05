package app.database.entities;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "USERS")
public class Users {
    @Id
    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Userroles> userroles = new LinkedList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public List<Userroles> getUserroles() {
        return userroles;
    }

    public Boolean isInRole(Roles role) {
        Userroles userrole = new Userroles();
        userrole.setUser(this);
        userrole.setRole(role);
        return this.userroles.contains(userrole);
    }

    public void addUserrole(Roles role) {
        Userroles userrole = new Userroles();
        userrole.setUser(this);
        userrole.setRole(role);
        this.userroles.add(userrole);
    }

    public void deleteUserrole(Roles role) {
        Userroles userrole = new Userroles();
        userrole.setUser(this);
        userrole.setRole(role);
        this.userroles.remove(userrole);
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
