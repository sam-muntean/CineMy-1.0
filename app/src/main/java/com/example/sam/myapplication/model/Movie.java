package com.example.sam.myapplication.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Sam on 06.11.2017.
 */

@IgnoreExtraProperties
public class Movie {
    private int id;
    private String name;
    private Integer score;
    private String description;

    public Movie() {
    }

    public Movie(String name, Integer score, String description) {
        this.name = name;
        this.score = score;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", score='" + score + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
