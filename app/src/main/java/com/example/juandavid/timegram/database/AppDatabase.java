package com.example.juandavid.timegram.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.juandavid.timegram.pojo.Event;

/**
 * Created by juandavid on 11/06/18.
 */
@Database(entities = {Event.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract EventDao eventDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context,
                    AppDatabase.class, "FeedReader.db")
                    .build();

        return instance;
    }
}