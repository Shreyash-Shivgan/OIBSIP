# Project Report: Task 5 - Precision Stopwatch

## Overview
The Precision Stopwatch application handles high-frequency UI updates while preserving accurate time measurements and managing device lifecycle states (such as rotations or running in the background).

## Architecture & Code Design
- **`TimeUtils.java`**: Formats system clock milliseconds to standard `HH:MM:SS` (Hours, Minutes, Seconds) and `.MS` (hundredths of a second) strings.
- **`LapModel.java`**: Encapsulates data for recorded laps: lap number, lap duration, and cumulative time.
- **`LapAdapter.java`**: Adapter that binds lap logs into a scrolling RecyclerView.
- **`MainActivity.java`**: The core controller. Manages timer thread scheduling using `Handler.postDelayed(runnable, 10)` to update UI labels at 100fps.

## Lifecycle Safety
- Standard stopwatches using simple `System.currentTimeMillis()` can drift or fail when timezone updates occur, or pause when the device sleeps. This implementation utilizes `SystemClock.elapsedRealtime()` which counts milliseconds since boot (including sleep).
- When the screen rotates, the activity is recreated. This project implements `onSaveInstanceState` and `restoreState` callbacks, saving the paused time buffering state. When rotating while running, the stopwatch automatically resynchronizes the differential elapsed time and continues counting seamlessly.
- During `onPause` (e.g. app goes to background), UI updates are removed to save processor resources. On `onResume`, the system resynchronizes the elapsed timer with the current `elapsedRealtime()` offset, continuing without any loss of accuracy.

## Testing & Validation
- Checked background accuracy: Started stopwatch, sent app to background for 10 seconds, returned, and verified stopwatch synchronized exactly to the real elapsed time.
- Screen rotation verification: Started stopwatch, rotated screen, and verified clock continued ticking from the correct time with lap history preserved.
- Dynamic lapping: Verified that newest laps are inserted at the top of the history list.
