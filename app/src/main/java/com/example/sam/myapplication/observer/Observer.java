package com.example.sam.myapplication.observer;

import com.example.sam.myapplication.repository.MovieRepository;

/**
 * Created by Sam on 09.01.2018.
 */

public abstract class Observer {
    public MovieRepository repo;
    public abstract void update();
}
