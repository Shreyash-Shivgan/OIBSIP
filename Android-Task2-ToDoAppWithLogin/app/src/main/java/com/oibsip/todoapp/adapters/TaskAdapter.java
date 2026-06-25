package com.oibsip.todoapp.adapters;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import androidx.core.content.ContextCompat;

import com.oibsip.todoapp.R;
import com.oibsip.todoapp.models.Task;

import java.util.List;

/**
 * RecyclerView adapter for binding tasks to user-specific list items.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private final List<Task> taskList;
    private final OnTaskActionListener listener;

    public interface OnTaskActionListener {
        void onTaskStatusChanged(Task task, boolean isChecked);
        void onTaskEdit(Task task);
        void onTaskDelete(Task task);
    }

    public TaskAdapter(List<Task> taskList, OnTaskActionListener listener) {
        this.taskList = taskList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.bind(task, listener);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox cbCompleted;
        private final TextView tvTaskTitle;
        private final TextView tvTaskNotes;
        private final ImageButton btnEdit;
        private final ImageButton btnDelete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            cbCompleted = itemView.findViewById(R.id.cbCompleted);
            tvTaskTitle = itemView.findViewById(R.id.tvTaskTitle);
            tvTaskNotes = itemView.findViewById(R.id.tvTaskNotes);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        public void bind(final Task task, final OnTaskActionListener listener) {
            tvTaskTitle.setText(task.getTitle());
            tvTaskNotes.setText(task.getNotes());

            // Clear listeners before checking state to avoid recursive callbacks
            cbCompleted.setOnCheckedChangeListener(null);
            cbCompleted.setChecked(task.isCompleted());

            // Apply strike-through for completed tasks
            if (task.isCompleted()) {
                tvTaskTitle.setPaintFlags(tvTaskTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                tvTaskTitle.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.text_secondary));
            } else {
                tvTaskTitle.setPaintFlags(tvTaskTitle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                tvTaskTitle.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.text_primary));
            }

            cbCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (listener != null) {
                    listener.onTaskStatusChanged(task, isChecked);
                }
            });

            btnEdit.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onTaskEdit(task);
                }
            });

            btnDelete.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onTaskDelete(task);
                }
            });
        }
    }
}
