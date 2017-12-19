package com.example.sam.myapplication.repository;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.sam.myapplication.database.Movie;

import java.util.List;

/**
 * Created by Sam on 19.12.2017.
 */

public class MovieRepository {

    private final AppDatabase db;

    public MovieRepository(Context context) {
         db = Room.
                databaseBuilder(context, AppDatabase.class, "movie-database")
                // allow queries on the main thread.
                // Don't do this on a real app! See PersistenceBasicSample for an example.
                .allowMainThreadQueries()
                .build();
    }

    public void init() {
        //Movie m1 = new Movie("Star Wars 1", 87, "Adrian All", "A war in the stars...", "https://vignette.wikia.nocookie.net/starwars/images/7/75/EPI_TPM_poster.png/revision/latest?cb=20130822171446");

        Movie m2 = new Movie("Godfather 2", 99, "Steven S", "Family and life...", "http://www.sobur.co/wp-content/uploads/the-godfather-ii.jpg");

        Movie m3 = new Movie("Fight Club", 92, "Sergiu N", "Psychology...", "https://images-na.ssl-images-amazon.com/images/M/MV5BZGY5Y2RjMmItNDg5Yy00NjUwLThjMTEtNDc2OGUzNTBiYmM1XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_UX182_CR0,0,182,268_AL_.jpg");


        // "https://vignette.wikia.nocookie.net/starwars/images/7/75/EPI_TPM_poster.png/revision/latest?cb=20130822171446",
        insert("Star Wars 1", "Adrian All", "A war in the stars...", 87);
        db.movieDao().insert(m2);
        db.movieDao().insert(m3);
    }


    public Movie findOne(int ID){
        return db.movieDao().findByID(ID);
    }

    public Movie getMovieByTitle(String title){
        for (Movie m :
             getMovies()) {
            if (m.getName().equals(title))
                return m;
        }
        return null;
    }

    public void insert(String name, String director, String description, Integer score){
        Movie movie = new Movie(name, score, director, description, "-");

        //System.out.println("###############################IN REPO##############"+movie);
        db.movieDao().insert(movie);
    }

    public List<Movie> getMovies() {
        return db.movieDao().getAll();
    }


    public void updateMovie (Movie movie){
        db.movieDao().update(movie);
    }

    public void delete(Movie movie){
        db.movieDao().delete(movie);
    }
}
