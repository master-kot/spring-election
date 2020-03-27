package com.nikolay.springmimimimetr.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "picture")
    private String picture;

    @OneToMany(mappedBy = "candidate")
    private List<Vote> votes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public Candidate() {}

    public Candidate(Integer id, String name, String picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
    }

    @Override
    public String toString() {
        return String.format("Candidate: [id = %d, name = %s, picture = %s]", id, name, picture);
    }
}