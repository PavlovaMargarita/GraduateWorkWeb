package by.bsu.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class ImageInformation implements Serializable, Comparable{

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String url;

    @Column
    private String hash;

    @Column
    private String pHash;

    public ImageInformation() {
    }

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

    public String getpHash() {
        return pHash;
    }

    public void setpHash(String pHash) {
        this.pHash = pHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageInformation that = (ImageInformation) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public int compareTo(Object o) {
        ImageInformation that = (ImageInformation) o;

        return this.id - that.id;
    }
}
