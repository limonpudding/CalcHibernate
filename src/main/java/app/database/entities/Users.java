package app.database.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class Users {
    @Id
    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "USERROLES",
            joinColumns = @JoinColumn(name = "USERNAME"))
    @Column(name = "ROLE")
    private Set<Roles> userroles = new HashSet<>();

    public void deleteUserrole(Roles role){
        userroles.remove(role);
    }

    public void addUserrole(Roles role){
        userroles.add(role);
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
