package com.byted.camp.todolist.roomdb;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Color;

@Entity(tableName = "note")
public class Note {

    public static final int PRIORITY_HIGH = 2;
    public static final int PRIORITY_MEDIUM = 1;
    public static final int PRIORITY_LOW = 0;

    public static final int STATE_TODO = 0;
    public static final int STATE_DONE = 1;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;

    @ColumnInfo(name = "date")
    private long date;

    @ColumnInfo(name = "state")
    private int state;

    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "priority")
    private int priority;

    public Note() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPriority() {
        return priority;
    }

    public int getPriorityColor() {
        if (priority == PRIORITY_LOW) {
            return Color.WHITE;
        } else if (priority == PRIORITY_MEDIUM) {
            return Color.YELLOW;
        } else {
            return Color.RED;
        }
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
