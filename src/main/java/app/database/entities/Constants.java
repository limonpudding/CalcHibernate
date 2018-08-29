package app.database.entities;

import javax.persistence.*;

@Entity
@Table(name = "CONSTANTS")
public class Constants {

    private String key;

    private String value;

    public Constants() {}

    public Constants(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Id
    @Column(name = "key")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Basic
    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
