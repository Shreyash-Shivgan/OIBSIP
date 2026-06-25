package com.oibsip.stopwatch;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView tvTimeDisplay, tvMsDisplay, tvEmptyLaps;
    private RecyclerView rvLaps;
    private MaterialButton btnLeft, btnRight; // btnLeft = Lap/Reset, btnRight = Start/Pause

    private LapAdapter adapter;
    private final ArrayList<LapModel> lapList = new ArrayList<>();

    private long startTime = 0L;
    private long timeBuff = 0L;
    private long updateTime = 0L;
    private long lastLapTime = 0L; // To calculate lap intervals
    private boolean isRunning = false;

    private final android.os.Handler handler = new android.os.Handler();
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            long millisecondTime = SystemClock.elapsedRealtime() - startTime;
            updateTime = timeBuff + millisecondTime;

            tvTimeDisplay.setText(TimeUtils.formatTime(updateTime));
            tvMsDisplay.setText(TimeUtils.formatMillisFraction(updateTime));

            handler.postDelayed(this, 10);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupRecyclerView();

        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        } else {
            updateButtonStates();
        }
    }

    private void initViews() {
        tvTimeDisplay = findViewById(R.id.tvTimeDisplay);
        tvMsDisplay = findViewById(R.id.tvMsDisplay);
        tvEmptyLaps = findViewById(R.id.tvEmptyLaps);
        rvLaps = findViewById(R.id.rvLaps);
        btnLeft = findViewById(R.id.btnLeft);
        btnRight = findViewById(R.id.btnRight);

        btnLeft.setOnClickListener(v -> handleLeftButtonClick());
        btnRight.setOnClickListener(v -> handleRightButtonClick());
    }

    private void setupRecyclerView() {
        rvLaps.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LapAdapter(lapList);
        rvLaps.setAdapter(adapter);
    }

    private void handleRightButtonClick() {
        if (!isRunning) {
            startTimer();
        } else {
            pauseTimer();
        }
    }

    private void handleLeftButtonClick() {
        if (isRunning) {
            recordLap();
        } else {
            resetTimer();
        }
    }

    private void startTimer() {
        isRunning = true;
        startTime = SystemClock.elapsedRealtime();
        handler.postDelayed(runnable, 0);
        updateButtonStates();
    }

    private void pauseTimer() {
        isRunning = false;
        timeBuff += SystemClock.elapsedRealtime() - startTime;
        handler.removeCallbacks(runnable);
        updateButtonStates();
    }

    private void resetTimer() {
        isRunning = false;
        startTime = 0L;
        timeBuff = 0L;
        updateTime = 0L;
        lastLapTime = 0L;
        handler.removeCallbacks(runnable);

        tvTimeDisplay.setText(R.string.default_time);
        tvMsDisplay.setText(R.string.default_ms);

        lapList.clear();
        adapter.notifyDataSetChanged();

        updateButtonStates();
        checkEmptyLapsState();
    }

    private void recordLap() {
        int lapNumber = lapList.size() + 1;
        long currentLapDuration = updateTime - lastLapTime;
        lastLapTime = updateTime;

        String duration = "+" + TimeUtils.formatTime(currentLapDuration) + TimeUtils.formatMillisFraction(currentLapDuration);
        String cumulative = TimeUtils.formatTime(updateTime) + TimeUtils.formatMillisFraction(updateTime);

        LapModel newLap = new LapModel(lapNumber, duration, cumulative);
        // Insert at index 0 to show latest lap first
        lapList.add(0, newLap);
        adapter.notifyItemInserted(0);
        rvLaps.scrollToPosition(0);

        checkEmptyLapsState();
    }

    private void updateButtonStates() {
        if (isRunning) {
            btnRight.setText(R.string.btn_pause);
            btnRight.setBackgroundTintList(getColorStateList(R.color.btn_pause));

            btnLeft.setText(R.string.btn_lap);
            btnLeft.setBackgroundTintList(getColorStateList(R.color.btn_lap));
            btnLeft.setEnabled(true);
        } else {
            btnRight.setText(R.string.btn_start);
            btnRight.setBackgroundTintList(getColorStateList(R.color.btn_start));

            btnLeft.setText(R.string.btn_reset);
            btnLeft.setBackgroundTintList(getColorStateList(R.color.btn_reset));
            // Disable reset if time is already zero
            btnLeft.setEnabled(updateTime > 0);
        }
    }

    private void checkEmptyLapsState() {
        if (lapList.isEmpty()) {
            tvEmptyLaps.setVisibility(View.VISIBLE);
            rvLaps.setVisibility(View.GONE);
        } else {
            tvEmptyLaps.setVisibility(View.GONE);
            rvLaps.setVisibility(View.VISIBLE);
        }
    }

    private void restoreState(Bundle savedInstanceState) {
        isRunning = savedInstanceState.getBoolean("isRunning");
        timeBuff = savedInstanceState.getLong("timeBuff");
        startTime = savedInstanceState.getLong("startTime");
        lastLapTime = savedInstanceState.getLong("lastLapTime");
        updateTime = savedInstanceState.getLong("updateTime");

        ArrayList<LapModel> savedLaps = (ArrayList<LapModel>) savedInstanceState.getSerializable("lapList");
        if (savedLaps != null) {
            lapList.addAll(savedLaps);
            adapter.notifyDataSetChanged();
        }

        checkEmptyLapsState();

        if (isRunning) {
            // Re-sync starting point to handle elapsed real time correctly
            long realElapsed = SystemClock.elapsedRealtime() - startTime;
            startTime = SystemClock.elapsedRealtime();
            timeBuff += realElapsed;
            handler.postDelayed(runnable, 0);
        } else {
            tvTimeDisplay.setText(TimeUtils.formatTime(updateTime));
            tvMsDisplay.setText(TimeUtils.formatMillisFraction(updateTime));
        }

        updateButtonStates();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isRunning", isRunning);
        outState.putLong("timeBuff", timeBuff);
        outState.putLong("startTime", startTime);
        outState.putLong("lastLapTime", lastLapTime);
        outState.putLong("updateTime", updateTime);
        outState.putSerializable("lapList", lapList);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop updates in background but maintain internal timers
        if (isRunning) {
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Resume UI updates if running
        if (isRunning) {
            startTime = SystemClock.elapsedRealtime();
            handler.postDelayed(runnable, 0);
        }
    }
}
