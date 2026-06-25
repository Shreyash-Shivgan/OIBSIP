package com.oibsip.todoapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.oibsip.todoapp.R;
import com.oibsip.todoapp.adapters.TaskAdapter;
import com.oibsip.todoapp.database.DatabaseHelper;
import com.oibsip.todoapp.models.Task;
import com.oibsip.todoapp.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class MainTaskActivity extends AppCompatActivity implements TaskAdapter.OnTaskActionListener {

    private static final int REQUEST_CODE_ADD_EDIT = 100;

    private TextView tvWelcome, tvEmptyTasks;
    private RecyclerView rvTasks;
    private FloatingActionButton fabAddTask;
    private View btnLogout;

    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;
    private TaskAdapter adapter;
    private final List<Task> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_task);

        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        // Session check
        if (!sessionManager.isLoggedIn()) {
            startActivity(new Intent(MainTaskActivity.this, LoginActivity.class));
            finish();
            return;
        }

        initViews();
        setupRecyclerView();
        setupListeners();
        loadTasks();
    }

    private void initViews() {
        tvWelcome = findViewById(R.id.tvWelcome);
        tvEmptyTasks = findViewById(R.id.tvEmptyTasks);
        rvTasks = findViewById(R.id.rvTasks);
        fabAddTask = findViewById(R.id.fabAddTask);
        btnLogout = findViewById(R.id.btnLogout);

        String welcomeText = "Hello, " + sessionManager.getUserName();
        tvWelcome.setText(welcomeText);
    }

    private void setupRecyclerView() {
        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(taskList, this);
        rvTasks.setAdapter(adapter);
    }

    private void setupListeners() {
        fabAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(MainTaskActivity.this, AddTaskActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_EDIT);
        });

        btnLogout.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Logout", (dialog, which) -> {
                        sessionManager.logoutUser();
                        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainTaskActivity.this, LoginActivity.class));
                        finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    private void loadTasks() {
        taskList.clear();
        List<Task> dbTasks = dbHelper.getTasksByUser(sessionManager.getUserId());
        taskList.addAll(dbTasks);
        adapter.notifyDataSetChanged();

        if (taskList.isEmpty()) {
            tvEmptyTasks.setVisibility(View.VISIBLE);
            rvTasks.setVisibility(View.GONE);
        } else {
            tvEmptyTasks.setVisibility(View.GONE);
            rvTasks.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_EDIT && resultCode == RESULT_OK) {
            loadTasks();
        }
    }

    @Override
    public void onTaskStatusChanged(Task task, boolean isChecked) {
        task.setCompleted(isChecked);
        dbHelper.updateTask(task);
        loadTasks(); // reload list to update formatting and sorting
    }

    @Override
    public void onTaskEdit(Task task) {
        Intent intent = new Intent(MainTaskActivity.this, AddTaskActivity.class);
        intent.putExtra("task_to_edit", task);
        startActivityForResult(intent, REQUEST_CODE_ADD_EDIT);
    }

    @Override
    public void onTaskDelete(Task task) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    dbHelper.deleteTask(task.getId());
                    Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show();
                    loadTasks();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
