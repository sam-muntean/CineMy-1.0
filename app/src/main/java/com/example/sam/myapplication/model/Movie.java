package com.example.sam.myapplication.model;

/**
 * Created by Sam on 06.11.2017.
 */

public class Movie {
    private String name;
    private Integer score;
    private String director;
    private String description;
    private String image;

    public Movie() {
    }

    public Movie(String name, Integer score, String director, String description, String url) {
        this.name = name;
        this.score = score;
        this.director = director;
        this.description = description;
        this.image = url;
    }

    public String getName() {
        return name;
    }

    public Integer getScore() {
        return score;
    }

    public String getDirector() {
        return director;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}
