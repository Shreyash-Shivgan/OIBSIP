package com.oibsip.todoapp.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.oibsip.todoapp.R;
import com.oibsip.todoapp.database.DatabaseHelper;
import com.oibsip.todoapp.models.Task;
import com.oibsip.todoapp.session.SessionManager;

public class AddTaskActivity extends AppCompatActivity {

    private TextView tvTitle;
    private TextInputLayout titleInputLayout, notesInputLayout;
    private TextInputEditText etTaskTitle, etTaskNotes;
    private MaterialButton btnSaveTask;

    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;
    private Task taskToEdit = null;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        initViews();
        checkIntentData();
        setupListeners();
    }

    private void initViews() {
        tvTitle = findViewById(R.id.tvTitle);
        titleInputLayout = findViewById(R.id.titleInputLayout);
        notesInputLayout = findViewById(R.id.notesInputLayout);
        etTaskTitle = findViewById(R.id.etTaskTitle);
        etTaskNotes = findViewById(R.id.etTaskNotes);
        btnSaveTask = findViewById(R.id.btnSaveTask);
    }

    private void checkIntentData() {
        if (getIntent().hasExtra("task_to_edit")) {
            taskToEdit = (Task) getIntent().getSerializableExtra("task_to_edit");
            if (taskToEdit != null) {
                isEditMode = true;
                tvTitle.setText(R.string.title_edit_task);
                etTaskTitle.setText(taskToEdit.getTitle());
                etTaskNotes.setText(taskToEdit.getNotes());
                btnSaveTask.setText("Update Task");
            }
        }
    }

    private void setupListeners() {
        btnSaveTask.setOnClickListener(v -> saveTask());
    }

    private void saveTask() {
        String title = etTaskTitle.getText() != null ? etTaskTitle.getText().toString().trim() : "";
        String notes = etTaskNotes.getText() != null ? etTaskNotes.getText().toString().trim() : "";

        if (TextUtils.isEmpty(title)) {
            titleInputLayout.setError("Task title is required");
            return;
        }

        titleInputLayout.setError(null);

        if (isEditMode) {
            taskToEdit.setTitle(title);
            taskToEdit.setNotes(notes);
            int rowsAffected = dbHelper.updateTask(taskToEdit);

            if (rowsAffected > 0) {
                Toast.makeText(this, "Task updated successfully", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Failed to update task", Toast.LENGTH_SHORT).show();
            }
        } else {
            Task newTask = new Task();
            newTask.setUserId(sessionManager.getUserId());
            newTask.setTitle(title);
            newTask.setNotes(notes);
            newTask.setCompleted(false);

            long rowId = dbHelper.insertTask(newTask);

            if (rowId != -1) {
                Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Failed to add task", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
