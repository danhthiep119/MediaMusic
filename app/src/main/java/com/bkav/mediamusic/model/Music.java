package com.bkav.mediamusic.model;

import java.io.Serializable;

public class Music implements Serializable {
    private String name;
    private String author;
    private long duration;

    public Music() {
    }

    public Music(String name, String author, long duration) {
        this.name = name;
        this.author = author;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
