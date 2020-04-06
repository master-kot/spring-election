package com.nikolay.election.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "views")
public class View {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public View() {
    }

    public View(Candidate candidate) {
        this.candidate = candidate;
    }

    public View(User user, Candidate candidate) {
        this.user = user;
        this.candidate = candidate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof View)) return false;
        View view = (View) o;
        return (user.equals(view.user)) && (Objects.equals(candidate, view.candidate));
    }
}