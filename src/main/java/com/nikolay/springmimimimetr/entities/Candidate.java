package com.nikolay.springmimimimetr.entities;

import java.util.ArrayList;
import java.util.List;

public class Candidate {

    private int id;
    private String name;
    private String picture;

    private List<Elector> seenElectors;

    private List<Elector> votedElectors;

    public Candidate() {}

    public Candidate(int id, String name, String picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.seenElectors = new ArrayList<Elector>();
        this.votedElectors = new ArrayList<Elector>();
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public void voteForCandidate(Elector elector) {
        if (!isCandidateVoted(elector)) votedElectors.add(elector);
    }

    public void showCandidate(Elector elector) {
        if (!isCandidateSeen(elector)) seenElectors.add(elector);
    }

    public boolean isCandidateSeen(Elector elector) {
        return seenElectors.contains(elector);
    }

    public boolean isCandidateVoted(Elector elector) {
        return votedElectors.contains(elector);
    }

    public int amountOfVotes() {
        return votedElectors.size();
    }
}
