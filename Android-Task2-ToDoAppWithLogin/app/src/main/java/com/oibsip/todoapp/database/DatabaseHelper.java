package com.oibsip.todoapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.oibsip.todoapp.models.Task;
import com.oibsip.todoapp.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLite Database open helper for managing Users and Tasks tables.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "secure_todo.db";
    private static final int DATABASE_VERSION = 1;

    // Users Table constants
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD_HASH = "passwordHash";

    // Tasks Table constants
    private static final String TABLE_TASKS = "tasks";
    private static final String COLUMN_TASK_ID = "id";
    private static final String COLUMN_TASK_USER_ID = "userId";
    private static final String COLUMN_TASK_TITLE = "title";
    private static final String COLUMN_TASK_NOTES = "notes";
    private static final String COLUMN_TASK_COMPLETED = "completed";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Users Table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_USER_EMAIL + " TEXT UNIQUE,"
                + COLUMN_USER_PASSWORD_HASH + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        // Create Tasks Table
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TASK_USER_ID + " INTEGER,"
                + COLUMN_TASK_TITLE + " TEXT,"
                + COLUMN_TASK_NOTES + " TEXT,"
                + COLUMN_TASK_COMPLETED + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_TASK_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + ")" + ")";
        db.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // --- USER CRUD OPERATIONS ---

    /**
     * Registers a new user inside the SQLite database.
     */
    public long registerUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD_HASH, user.getPasswordHash());

        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;
    }

    /**
     * Checks if email already exists in DB.
     */
    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_USER_ID},
                COLUMN_USER_EMAIL + "=?", new String[]{email}, null, null, null);

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    /**
     * Validates user credentials.
     */
    public User loginUser(String email, String passwordHash) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + "=? AND " + COLUMN_USER_PASSWORD_HASH + "=?";
        String[] selectionArgs = {email, passwordHash};

        Cursor cursor = db.query(TABLE_USERS, null, selection, selectionArgs, null, null, null);

        User user = null;
        if (cursor.moveToFirst()) {
            user = new User();
            user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)));
            user.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_NAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_EMAIL)));
            user.setPasswordHash(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_PASSWORD_HASH)));
        }
        cursor.close();
        db.close();
        return user;
    }

    // --- TASK CRUD OPERATIONS ---

    /**
     * Inserts a new task associated with a user.
     */
    public long insertTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_USER_ID, task.getUserId());
        values.put(COLUMN_TASK_TITLE, task.getTitle());
        values.put(COLUMN_TASK_NOTES, task.getNotes());
        values.put(COLUMN_TASK_COMPLETED, task.isCompleted() ? 1 : 0);

        long id = db.insert(TABLE_TASKS, null, values);
        db.close();
        return id;
    }

    /**
     * Updates an existing task details/status.
     */
    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_TITLE, task.getTitle());
        values.put(COLUMN_TASK_NOTES, task.getNotes());
        values.put(COLUMN_TASK_COMPLETED, task.isCompleted() ? 1 : 0);

        int rows = db.update(TABLE_TASKS, values, COLUMN_TASK_ID + "=?", new String[]{String.valueOf(task.getId())});
        db.close();
        return rows;
    }

    /**
     * Deletes a task by ID.
     */
    public int deleteTask(int taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_TASKS, COLUMN_TASK_ID + "=?", new String[]{String.valueOf(taskId)});
        db.close();
        return rows;
    }

    /**
     * Fetches all tasks created by a specific user.
     */
    public List<Task> getTasksByUser(int userId) {
        List<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TASKS, null, COLUMN_TASK_USER_ID + "=?",
                new String[]{String.valueOf(userId)}, null, null, COLUMN_TASK_ID + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TASK_ID)));
                task.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TASK_USER_ID)));
                task.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK_TITLE)));
                task.setNotes(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK_NOTES)));
                task.setCompleted(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TASK_COMPLETED)) == 1);
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }
}
