package com.nikolay.springmimimimetr.entities;

public class Elector {

    private int id;
    private String name;
    private String password;

    public Elector() {}

    public Elector(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}