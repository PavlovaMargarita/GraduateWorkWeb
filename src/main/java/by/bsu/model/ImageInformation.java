package by.bsu.model;

import javax.persistence.*;

@Entity
@Table
public class ImageInformation {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String url;

    @Column
    private String hash;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
