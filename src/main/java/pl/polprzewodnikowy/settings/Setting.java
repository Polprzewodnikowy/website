package pl.polprzewodnikowy.settings;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "setting")
public class Setting {

    @Id
    @Column(name = "setting_id", length = 64)
    private String id;

    @Column(name = "setting_value")
    private String value;

    public Setting() {

    }

    public Setting(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
