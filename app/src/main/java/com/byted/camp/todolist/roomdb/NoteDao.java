package com.byted.camp.todolist.roomdb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note ORDER BY priority DESC, date ASC")
    List<Note> getAll();

    @Update()
    int update(Note note);

    @Delete
    int delete(Note note);

    @Insert
    long insert(Note note);
}
