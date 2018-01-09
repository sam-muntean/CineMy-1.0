package com.example.sam.myapplication.repository;

import com.example.sam.myapplication.MainActivity;
import com.example.sam.myapplication.model.Movie;
import com.example.sam.myapplication.observer.Observer;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 19.12.2017.
 */

public class MovieRepository extends Observer {

    private int movieId;

    private List<Observer> observers = new ArrayList<>();
    private List<Movie> movieList = new ArrayList<>();

    private DatabaseReference db;

    public MovieRepository(DatabaseReference dbR) {
         db = dbR;
         this.attach(this);
    }

    public Movie findOne(int ID){
        return movieList.get(ID);
    }

    public Movie getMovieByTitle(String title){
        for (Movie m :
             getMovies()) {
            if (m.getName().equals(title))
                return m;
        }
        return null;
    }

    public void clear(){
        this.movieList.clear();
    }

    public void setId(int id){
        this.movieId = id;
    }

    public void insert(String name, String description, Integer score){
        Movie movie = new Movie(name, score, description);
        movie.setId(movieId);
        db.child(String.valueOf(movieId)).setValue(movie);
        movieId++;
        notifyAllObservers();
    }

    public void addMovie(Movie movie) {
        this.movieList.add(movie);
    }

    public List<Movie> getMovies() {
        return movieList;
    }

    public void updateMovie (Movie movie){
        db.child(String.valueOf(movie.getId())).setValue(movie);
        notifyAllObservers();
    }

    public void delete(Movie movie){
        db.child(String.valueOf(movie.getId())).removeValue();
    }

    public void attach(Observer observer){
        observers.add(observer);
    }

    public void notifyAllObservers(){
        for (Observer observer : observers) {
            observer.update();
        }
    }

    @Override
    public void update() {
        MainActivity.showRepositoryUpdated();
    }
}
