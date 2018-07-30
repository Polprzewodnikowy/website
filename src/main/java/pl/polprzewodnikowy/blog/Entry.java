package pl.polprzewodnikowy.blog;

import pl.polprzewodnikowy.user.UserInfo;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "entry")
public class Entry {

    @Id
    @GeneratedValue
    @Column(name = "entry_id")
    private Integer id;

    @Column(name = "entry_title")
    private String title;

    @Column(name = "entry_body", columnDefinition = "longtext")
    private String body;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "entry_timestamp")
    private Date timestamp;

    @ManyToOne
    private UserInfo author;

    public Entry() {

    }

    public Entry(String title, String body, Date timestamp, UserInfo author) {
        this.title = title;
        this.body = body;
        this.timestamp = timestamp;
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public UserInfo getAuthor() {
        return author;
    }

    public void setAuthor(UserInfo author) {
        this.author = author;
    }

}
