package com.oa.api.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "RGB01")
public class RegisteredBetDTO implements Serializable {

    @Id
    private Long id;
    private Long unix;
    private Long bookieId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUnix() {
        return unix;
    }

    public void setUnix(Long unix) {
        this.unix = unix;
    }

    public Long getBookieId() {
        return bookieId;
    }

    public void setBookieId(Long bookieId) {
        this.bookieId = bookieId;
    }

    @Override
    public String toString() {
        return "RegisteredBetDTO{" +
                "id=" + id +
                '}';
    }
}

