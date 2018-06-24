package com.example.juandavid.timegram.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.juandavid.timegram.pojo.Event;

import java.util.List;

/**
 * Created by juandavid on 11/06/18.
 */
@Dao
public interface EventDao {
    @Query("SELECT ID, DATE, OBJECTIVE, REALTIME, DESCRIPTION, CATEGORY FROM EVENTS")
    List<Event> getAll();

    @Query("SELECT ID, DATE, OBJECTIVE, REALTIME, DESCRIPTION, CATEGORY FROM EVENTS WHERE REALTIME IS NULL")
    List<Event> getComingAppointments();

    @Query("SELECT ID, DATE, OBJECTIVE, REALTIME, DESCRIPTION, CATEGORY FROM EVENTS WHERE REALTIME IS NOT NULL")
    List<Event> getDoneAppointments();

    @Update
    void update(Event item);

    @Insert
    void insert(Event item);

    @Query("DELETE FROM EVENTS WHERE id = :id")
    void deleteFromId(int id);
}