package com.nikolay.springmimimimetr.entities;

import javax.persistence.*;

@Entity
@Table(name = "candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "picture")
    private String picture;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Candidate() {}

    public Candidate(Long id, String name, String picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
    }

    @Override
    public String toString() {
        return String.format("Candidate: [id = %d, name = %s, picture = %s]", id, name, picture);
    }
}