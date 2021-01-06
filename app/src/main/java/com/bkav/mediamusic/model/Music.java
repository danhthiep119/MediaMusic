package com.bkav.mediamusic.model;

import java.io.Serializable;

public class Music implements Serializable {
    private String name;
    private String author;
    private long duration;
    private String path;

    public Music() {
    }

    public Music(String name, String author, long duration,String path) {
        this.name = name;
        this.author = author;
        this.duration = duration;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
