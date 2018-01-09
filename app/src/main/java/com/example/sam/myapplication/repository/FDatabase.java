package com.example.sam.myapplication.repository;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Sam on 09.01.2018.
 */

public class FDatabase {
    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        return mDatabase;
    }
}
