package com.example.sam.myapplication.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Sam on 19.12.2017.
 */

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies")
    List<Movie> getAll();

    @Query("SELECT COUNT(*) from movies")
    int count();

    @Query("SELECT * FROM movies LIMIT 1 OFFSET :id")
    Movie findByID(int id);

    @Update
    void update(Movie movie);

    @Insert
    void insert(Movie movie);

    @Delete
    void delete(Movie movie);

    @Query("DELETE FROM movies")
    void deleteAll();



}
