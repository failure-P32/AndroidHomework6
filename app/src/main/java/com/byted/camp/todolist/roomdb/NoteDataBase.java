package com.byted.camp.todolist.roomdb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDataBase extends RoomDatabase {
    public abstract NoteDao noteDao();
}
