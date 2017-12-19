package com.example.sam.myapplication.repository;

import com.example.sam.myapplication.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 06.11.2017.
 */

public class Repository {
    private List<Movie> movies;

    public Repository() {
        movies = new ArrayList<>();
        init();
    }

    public void init(){
    }

    public void setScore(int score, String name){
        for(Movie m : movies){
            if(m.getName().equals(name)){
                Movie mm = new Movie(name, score, m.getDirector(), m.getDescription(), m.getImage());
                movies.set(movies.indexOf(m), mm);
                break;
            }
        }
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
