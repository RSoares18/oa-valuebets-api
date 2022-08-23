package com.oa.api.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "RGB01")
public class RegisteredBetDTO implements Serializable {

    @Id
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RegisteredBetDTO{" +
                "id=" + id +
                '}';
    }
}

