package com.oibsip.todoapp.models;

import java.io.Serializable;

/**
 * Model class representing a task item. Implements Serializable to pass between activities.
 */
public class Task implements Serializable {
    private int id;
    private int userId;
    private String title;
    private String notes;
    private boolean completed;

    public Task() {}

    public Task(int id, int userId, String title, String notes, boolean completed) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.notes = notes;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
