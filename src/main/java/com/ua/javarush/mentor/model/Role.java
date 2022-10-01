package com.ua.javaruh.mentor.model;

public class Role {
    private final long id;
    private final String text;

    public Role(long id, String text) {
        this.id = id;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
