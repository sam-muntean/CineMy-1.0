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
        movies = new ArrayList<Movie>();
        init();
    }

    public void init(){
        movies.add(new Movie("Star Wars 1", 87, "Adrian All", "A war in the stars...", "https://vignette.wikia.nocookie.net/starwars/images/7/75/EPI_TPM_poster.png/revision/latest?cb=20130822171446"));
        movies.add(new Movie("Godfather 2", 99, "Steven S", "Family and life...", "http://www.sobur.co/wp-content/uploads/the-godfather-ii.jpg"));
        movies.add(new Movie("Fight Club", 92, "Sergiu N", "Psychology...", "https://images-na.ssl-images-amazon.com/images/M/MV5BZGY5Y2RjMmItNDg5Yy00NjUwLThjMTEtNDc2OGUzNTBiYmM1XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_UX182_CR0,0,182,268_AL_.jpg"));
    }

    public void setScore(int score, String name){
        for(Movie m : movies){
            if(m.getName().equals(name)){
                Movie mm = new Movie(name, score, m.getDirector(), m.getDescription(), m.getImage());
                movies.set(movies.indexOf(m), mm);
                System.out.println(mm.getName() + mm.getScore());
                break;
            }
        }
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
