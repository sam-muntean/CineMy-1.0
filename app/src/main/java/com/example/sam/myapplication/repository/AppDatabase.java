package com.example.sam.myapplication.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.sam.myapplication.database.Movie;
import com.example.sam.myapplication.database.MovieDao;

/**
 * Created by Sam on 19.12.2017.
 */


@Database(entities = {Movie.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}
