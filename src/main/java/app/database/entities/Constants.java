package app.database.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "CONSTANTS")
public class Constants {

    @Id
    @Column(length = 40)
    private String key;
    @Type(type = "clob")
    private String value;

    public Constants() {}

    public Constants(String key, String value) {
        this.key = key;
        this.value = value;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
